package za.ntier.process;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MUserRoles;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import za.co.ntier.fa.process.api.AbstractDocApproveProcess;
import za.co.ntier.fa.process.api.IDocApprove;
import za.ntier.models.MZZOpenApplication;
import za.ntier.models.X_ZZ_Program_Master_Data;

@org.adempiere.base.annotation.Process(
		name = "za.ntier.process.OpenApplicationDocApproveProcess"
		)
public class OpenApplicationDocApproveProcess extends AbstractDocApproveProcess<MZZOpenApplication> {

	// Convenience recipient buckets
	private final List<Integer> broadcastRoleIds = new ArrayList<>();

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

		if (IDocApprove.ZZ_DOCACTION_SubmitToRecommender.equals(action)) {
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
			if (!IDocApprove.ZZ_DOCSTATUS_Recommended.equals(status)) { // ZZ_DOCSTATUS_Recommended
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
		// Validate exec approver selection at submit time (spec: “Choose Exec Approver – (COO, EMCS)”)
		final int execApproverId = docApprove.getZZ_Exec_Approver_ID();
		if (execApproverId <= 0)
			throw new AdempiereException("Please choose an Executive Approver (COO or EMCS) before submitting.");

		// Set state
		docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_Submitted);
		docApprove.setZZ_DocAction(IDocApprove.ZZ_DOCACTION_Recommend);
		Timestamp now = now();
		if (docApprove.getZZ_Date_Submitted() == null)
			docApprove.setZZ_Date_Submitted(now);

		// Identify/validate recommender (Snr Mgr SPU)
		/*
		int recommenderId = docApprove.getZZ_Recommender_ID();
		if (recommenderId <= 0) {
			// fallback: pick any user with ROLE_SNR_MGR_SPU if that’s your pattern
			recommenderId = findFirstUserInRole(IDocApprove.ROLE_SNR_MGR_SPU);
			if (recommenderId <= 0)
				throw new AdempiereException("No Recommender (Snr Mgr SPU) found/configured.");
			docApprove.setZZ_Recommender_ID(recommenderId);
		}
		 */



		// Notify recommender
		AbstractDocApproveProcess.queueNotify(queueNotifis, IDocApprove.ROLE_SNR_MGR_SPU, getTable_ID(), getRecord_ID(),
				docApprove.getZZMailRequestLine());

		// Optional: also notify submitter for confirmation
		//AbstractDocApproveProcess.queueNotify(queueNotifis, docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(),
		//		docApprove.getZZ); // reuse if you prefer
	}

	/** Submitted -> Recommended by Snr Mgr SPU */
	private void doRecommenderRecommend() {     
		docApprove.setZZ_Recommender_ID(getAD_User_ID());
		if ("Y".equals(pRecommend_Rej_Snr_SPU)) {
			docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_Recommended); // ZZ_DOCSTATUS_Recommended
			docApprove.setZZ_DocAction(IDocApprove.ZZ_DOCACTION_ApproveExec);
			docApprove.setZZ_Date_Recommended(now);

			// Notify the Exec approver (COO/EMCS)
			final int execApproverId = docApprove.getZZ_Exec_Approver_ID();
			if (execApproverId <= 0)
				throw new AdempiereException("Exec Approver is not selected.");

			AbstractDocApproveProcess.queueNotify(queueNotifis, execApproverId, getTable_ID(), getRecord_ID(),
					docApprove.getZZMailRequestSnr());
		} else {
			docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_NOT_RECOMMENDED);
			docApprove.setZZ_Date_Not_Recommended(now);
			AbstractDocApproveProcess.queueNotify(queueNotifis, docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailLineReject());
		}
	}

	/** Recommended -> Approved by Exec (COO/EMCS) */
	private void doExecApprove() {
		docApprove.setZZ_Exec_Approver_ID(getAD_User_ID());
		if ("Y".equals(pApprove_Rej_COO_EMCS)) {
			docApprove.setZZ_DocStatus(X_ZZ_Program_Master_Data.ZZ_DOCSTATUS_Approved);
			docApprove.setZZ_DocAction(null);
			docApprove.setZZ_Date_Approved(now());

			// Broadcast: all Managers LP, Snr Managers (LP, SPU), Comms
			collectBroadcastAudience();
			for (int roleID : broadcastRoleIds) {
				AbstractDocApproveProcess.queueNotifyForRole(queueNotifis, roleID, getTable_ID(), getRecord_ID(),
						docApprove.getZZMailLineApproved());
			}
		} else {
			docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_NotApproved);
			docApprove.setZZ_Date_Not_Approved(now);
			AbstractDocApproveProcess.queueNotify(queueNotifis, docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailLineReject());
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
		//	if (!openTs.after(now) || !closeTs.after(now))
		//	throw new AdempiereException("Open and Close date/time must be in the future.");



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
		//final int ROLE_COMMS            = 0;  // TODO

		broadcastRoleIds.add(IDocApprove.ROLE_MANAGER_LP);
		broadcastRoleIds.add(IDocApprove.ROLE_SNR_MANAGER_LP);
		broadcastRoleIds.add(IDocApprove.ROLE_SNR_MGR_SPU);
		broadcastRoleIds.add(IDocApprove.ROLE_MANAGER_SPU);
		//addUsersOfRole(IDocApprove.ROLE_COMMS);
	}
}
