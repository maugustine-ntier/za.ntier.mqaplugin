package za.ntier.process;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MUser;
import org.compiere.model.MUserRoles;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import za.co.ntier.fa.process.api.AbstractDocApproveProcess;
import za.co.ntier.fa.process.api.IDocApprove;
import za.ntier.models.MZZProgramMasterData;
import za.ntier.models.X_ZZ_Program_Master_Data;

@org.adempiere.base.annotation.Process(
    name = "za.ntier.process.OpenApplicationDocApproveProcess"
)
public class OpenApplicationDocApproveProcess extends AbstractDocApproveProcess<MZZProgramMasterData> {

    // Convenience recipient buckets
    private final List<Integer> broadcastUserIds = new ArrayList<>();

    @Override
    protected void initDocApproveObj() {
        docApprove = poObj; // required by AbstractDocApproveProcess
    }

    @Override
    protected void doLogic() {
        // Optional: logic to run when final state reached (e.g., auto-create window records, cache invalidation)
        // For this spec, nothing mandatory here.
    }

    @Override
    protected String doIt() throws Exception {
        // 1) general data validation (dates, etc.)
        validateData();

        final String action = docApprove.getZZ_DocAction();
        final String status = docApprove.getZZ_DocStatus();

        if (IDocApprove.ZZ_DOCACTION_SubmitOpenWindow.equals(action)) {
            // From Draft -> Submitted
            if (!IDocApprove.ZZ_DOCSTATUS_Draft.equals(status) && !Util.isEmpty(status, true)) {
                throw wrongState("Submit", status, action);
            }
            doSubmitToRecommender();
        }
        else if (IDocApprove.ZZ_DOCACTION_Recommend.equals(action)) {
            // From Submitted -> Recommended (by Snr Mgr SPU)
            if (!IDocApprove.ZZ_DOCSTATUS_Submitted.equals(status)) {
                throw wrongState("Recommend", status, action);
            }
            doRecommenderRecommend();
        }
        else if (IDocApprove.ZZ_DOCACTION_ApproveExec.equals(action)) {
            // From Recommended -> Approved (by Exec: COO/EMCS)
            if (!"RC".equals(status)) { // ZZ_DOCSTATUS_Recommended
                throw wrongState("Approve", status, action);
            }
            doExecApprove();
        }
        else {
            // Fallback to your existing LM path if you still want to keep it
            throw new AdempiereException(Msg.getMsg(getCtx(), "ZZ_WrongWorkflowState",
                    new Object[]{
                            docApprove.isZZ_AllowLineManageApproved(),
                            docApprove.isZZ_AllowSnrAdminFinanceApproved(),
                            status,
                            action
                    }));
        }

        // Commit UI/state
        doFinalDocState(action);
        poObj.saveEx();

        // Execute any final logic only when we actually reached the configured terminal state
        if (!Util.isEmpty(docApprove.getZZ_FinalWorkflowStateValue(), true)
                && docApprove.getZZ_FinalWorkflowStateValue().equals(docApprove.getZZ_DocStatus())) {
            doLogic();
        }

        // Flush notifications
        sentNotify(queueNotifis, get_TrxName());
        return null;
    }

    /** Draft -> Submitted */
    private void doSubmitToRecommender() {
        // Ensure future dates and ordering already checked in validateData()

        // Set state
        docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_Submitted);
        docApprove.setZZ_DocAction(null);
        Timestamp now = now();
        if (docApprove.getZZ_Date_Submitted() == null)
            docApprove.setZZ_Date_Submitted(now);

        // Identify/validate recommender (Snr Mgr SPU)
        int recommenderId = docApprove.getZZ_Recommender_ID();
        if (recommenderId <= 0) {
            // fallback: pick any user with ROLE_SNR_MGR_SPU if that’s your pattern
            recommenderId = findFirstUserInRole(IDocApprove.ROLE_SNR_MGR_SPU);
            if (recommenderId <= 0)
                throw new AdempiereException("No Recommender (Snr Mgr SPU) found/configured.");
            docApprove.setZZ_Recommender_ID(recommenderId);
        }

        // Validate exec approver selection at submit time (spec: “Choose Exec Approver – (COO, EMCS)”)
        final int execApproverId = docApprove.getZZ_Exec_Approver_ID();
        if (execApproverId <= 0)
            throw new AdempiereException("Please choose an Executive Approver (COO or EMCS) before submitting.");

        // Notify recommender
        AbstractDocApproveProcess.queueNotify(queueNotifis, recommenderId, getTable_ID(), getRecord_ID(),
                getMailOrNull("getZZMailRequestRecommender"));

        // Optional: also notify submitter for confirmation
        AbstractDocApproveProcess.queueNotify(queueNotifis, docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(),
                getMailOrNull("getZZMailRequestLine")); // reuse if you prefer
    }

    /** Submitted -> Recommended by Snr Mgr SPU */
    private void doRecommenderRecommend() {
        // Verify actor is the configured recommender (optional – depends on how you restrict action button)
        // int ctxUserId = Env.getAD_User_ID(getCtx());
        // if (ctxUserId != docApprove.get_ValueAsInt("ZZ_Recommender_ID")) throw new AdempiereException("Only the configured Recommender may perform this action.");

        docApprove.setZZ_DocStatus("RC"); // ZZ_DOCSTATUS_Recommended
        docApprove.setZZ_DocAction(null);
        docApprove.setZZ_Date_Manager_Approved(now()); // reuse this “manager approved” field for the recommender timestamp

        // Notify the Exec approver (COO/EMCS)
        final int execApproverId = docApprove.getZZ_Exec_Approver_ID();
        if (execApproverId <= 0)
            throw new AdempiereException("Exec Approver is not selected.");

        AbstractDocApproveProcess.queueNotify(queueNotifis, execApproverId, getTable_ID(), getRecord_ID(),
                getMailOrNull("getZZMailNotifyExecApprover"));
    }

    /** Recommended -> Approved by Exec (COO/EMCS) */
    private void doExecApprove() {
        docApprove.setZZ_DocStatus(X_ZZ_Program_Master_Data.ZZ_DOCSTATUS_Approved);
        docApprove.setZZ_DocAction(null);
        docApprove.setZZ_Date_Approved(now());

        // Broadcast: all Managers LP, Snr Managers (LP, SPU), Comms
        collectBroadcastAudience();
        for (int userId : broadcastUserIds) {
            AbstractDocApproveProcess.queueNotify(queueNotifis, userId, getTable_ID(), getRecord_ID(),
                    getMailOrNull("getZZMailWindowApproved"));
        }
    }

    /** Core data validation from the spec */
    protected void validateData() {
        // These getters assume your model has fields with these column names; adjust if needed.
        Timestamp openTs  = (Timestamp) docApprove.getStartDate();
        Timestamp closeTs = (Timestamp) docApprove.getEndDate();

        if (openTs == null || closeTs == null)
            throw new AdempiereException("Please enter both Open and Close date & time.");

        if (!openTs.before(closeTs))
            throw new AdempiereException("Open date/time must be earlier than Close date/time.");

        Timestamp now = now();

        // “Both dates are in the future”
        if (!openTs.after(now) || !closeTs.after(now))
            throw new AdempiereException("Open and Close date/time must be in the future.");

      

        // Optional: validate that excluded programs list is consistent with “approved programs” set.
        // e.g., ensure IDs exist and belong to the approved list for this window.
        // String excluded = docApprove.get_ValueAsString("ZZ_Excluded_Program_Ids");
        // ...
    }

    private AdempiereException wrongState(String what, String status, String action) {
        return new AdempiereException(
                "Wrong workflow state for '" + what + "'. Current Status=" + status + ", Action=" + action);
    }

    private Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    // --- Helpers -------------------------------------------------------------

    /** Best-effort: return first active user in a role */
    private int findFirstUserInRole(int roleId) {
        // Minimal/naive lookup (replace with a proper query util you already use)
        // NOTE: You might already have a helper for this in your codebase.
    	MUserRoles[] users = MUserRoles.getOfRole(getCtx(), roleId);
        if (users != null && users.length > 0) return users[0].getAD_User_ID();
        return 0;
    }

    /** Reflective getter for optional mail templates (keeps compile-safe if not configured everywhere) */
    private org.compiere.model.MMailText getMailOrNull(String method) {
        try {
            return (org.compiere.model.MMailText) IDocApprove.class.getMethod(method).invoke(docApprove);
        } catch (Exception ignore) {
            return null;
        }
    }

    /** Gather LP Managers, Snr Managers (LP, SPU), Comms */
    private void collectBroadcastAudience() {
        // Replace these with your org’s real role IDs
        final int ROLE_MANAGER_LP       = 0;  // TODO: put your AD_Role_ID
        final int ROLE_SNR_MANAGER_LP   = 0;  // TODO
        final int ROLE_SNR_MANAGER_SPU  = IDocApprove.ROLE_SNR_MGR_SPU; // known
        final int ROLE_COMMS            = 0;  // TODO

        addUsersOfRole(ROLE_MANAGER_LP);
        addUsersOfRole(ROLE_SNR_MANAGER_LP);
        addUsersOfRole(ROLE_SNR_MANAGER_SPU);
        addUsersOfRole(ROLE_COMMS);
    }

    private void addUsersOfRole(int roleId) {
        if (roleId <= 0) return;
        MUserRoles[] users = MUserRoles.getOfRole(getCtx(), roleId);
        if (users == null) return;
        for (MUserRoles u : users) {
            if (u.isActive()) broadcastUserIds.add(u.getAD_User_ID());
        }
    }
}
