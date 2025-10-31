package za.co.ntier.wf.process;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.adempiere.base.annotation.Parameter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import za.co.ntier.wf.model.MZZWFHeader;
import za.co.ntier.wf.model.MZZWFLines;
import za.co.ntier.wf.util.ADColumnUtil;
import za.co.ntier.wf.util.MailNoticeUtil;
import za.co.ntier.wf.util.MailNoticeUtil.NotificationFields;

@org.adempiere.base.annotation.Process(name="za.co.ntier.wf.process.ZZ_WF_RunProcess")
public class ZZ_WF_RunProcess extends SvrProcess {
	@Parameter(name="Approve")
	private String pApprove; // 'Y' or 'N' or null
	@Parameter(name="Recommend")
	private String pRecommend;
	@Parameter(name="Comment")
	private String pComment;

	private PO po;
	private String trxName;
	private Properties ctx;
	private Timestamp now;
	List<Map<NotificationFields, Object>> queueNotifis = new ArrayList<>();

	@Override
	protected void prepare() {
		ctx = Env.getCtx();
		trxName = get_TrxName();
		now = new Timestamp(System.currentTimeMillis());
		MTable t = MTable.get(ctx, getTable_ID());
		po = t.getPO(getRecord_ID(), trxName);
	}

	@Override
	protected String doIt() {

		require(po, "ZZ_DocStatus");
		require(po, "ZZ_DocAction");

		String curStatus = po.get_ValueAsString("ZZ_DocStatus");
		String curAction = po.get_ValueAsString("ZZ_DocAction");

		MZZWFHeader hdr = MZZWFHeader.forTable(ctx, po.get_Table_ID(), trxName);
		if (hdr == null || !hdr.isActive())
			throw new AdempiereException("No active workflow found for this table.");

		MZZWFLines currentStep = (curAction != null && !curAction.isBlank())
				? MZZWFLines.findByStatusAndAction(ctx, hdr.get_ID(), curStatus, curAction, trxName)
						: null;

		if (currentStep != null) {
			if ((pApprove == null || pApprove.isBlank()) && (pRecommend == null || pRecommend.isBlank()))
				throw new AdempiereException("Please indicate Approve=Y or Approve=N.");
			boolean approve = ("Y".equalsIgnoreCase(pApprove.trim())) || ("Y".equalsIgnoreCase(pRecommend.trim()));
			doApproveReject(hdr, currentStep, approve, pComment);
		} else {
			doRequest(hdr, curStatus);
		}
		MailNoticeUtil.sentNotify(queueNotifis, po,get_TrxName());
		return "@OK@";
	}

	private void doRequest(MZZWFHeader hdr, String curStatus) {
		MZZWFLines step = MZZWFLines.findFirstByAllowedFromStatus(ctx, hdr.get_ID(), curStatus, trxName);
		if (step == null)
			throw new AdempiereException("No workflow step matches current status: " + curStatus);

		String oldAction = po.get_ValueAsString("ZZ_DocAction");
		po.set_ValueOfColumn("ZZ_DocAction", step.getSetDocAction());
		po.saveEx();

		MailNoticeUtil.requestStepNotifyAll(queueNotifis,step, po, hdr, getTable_ID(),getRecord_ID(),MailNoticeUtil.setPOForMail(step.getMMailText_Approved(),step),ctx, trxName);
		AuditUtil.createAudit(ctx, trxName, po.get_Table_ID(), po.get_ID(), step.get_ID(),
				"REQUEST", curStatus, curStatus, oldAction, step.getSetDocAction(), null,Env.getAD_User_ID(ctx));
	}

	private void doApproveReject(MZZWFHeader hdr, MZZWFLines step, boolean approve, String comment) {
		int actor = Env.getAD_User_ID(ctx);
		if (!AuthUtil.isActorAuthorized(ctx, trxName, step, po, actor))
			throw new AdempiereException("You are not authorized to approve/reject this step.");

		String curStatus = po.get_ValueAsString("ZZ_DocStatus");
		String curAction = po.get_ValueAsString("ZZ_DocAction");

		String nextStatus = approve ? step.getNextStatusOnApprove() : step.getNextStatusOnReject();
		String nextAction = approve ? step.getNextActionOnApprove() : step.getNextActionOnReject();

		// Persist new state (both fields, as requested)
		po.set_ValueOfColumn("ZZ_DocStatus", nextStatus);
		po.set_ValueOfColumn("ZZ_DocAction", nextAction);
		if (approve) {
			po.set_ValueOfColumn(ADColumnUtil.getColumnName(ctx, step.getZZ_Approved_User_COL_ID(), trxName),actor);
			po.set_ValueOfColumn(ADColumnUtil.getColumnName(ctx, step.getZZ_Approved_TS_COL_ID(), trxName),now);
		} else {
			po.set_ValueOfColumn(ADColumnUtil.getColumnName(ctx, step.getZZ_Rejected_User_COL_ID(), trxName),actor);
			po.set_ValueOfColumn(ADColumnUtil.getColumnName(ctx, step.getZZ_Rejected_TS_COL_ID(), trxName),now);
		}
		po.saveEx();

		// --- Notifications ---
		int requesterUserId = po.getCreatedBy();
		String respColumn = step.getResponsibleColumnName(step.getCtx(), step.get_TrxName());
		int responsibleID = (respColumn != null) ? step.get_ValueAsInt(respColumn) : -1;

		if (!approve) {
			// NEW: Always notify original requester on ANY REJECTION
			int tmplReject = step.getMMailText_Rejected_ID();
			if (requesterUserId > 0 && tmplReject > 0) {
				MailNoticeUtil.queueNotify(queueNotifis, requesterUserId, getTable_ID(), getRecord_ID(), MailNoticeUtil.setPOForMail(step.getMMailText_Rejected(),step));
			}
		} else {
			// On approve: normal “approved step” message (optional)
			if (responsibleID > 0) {
				// Optional: send per-step approval notice (keep if you like)
				MailNoticeUtil.queueNotify(queueNotifis, responsibleID, getTable_ID(), getRecord_ID(), MailNoticeUtil.setPOForMail(step.getMMailText_Approved(),step));
			}

			// NEW: If this approval ends the workflow, notify requester (final-approval template if you have one)
			if (isFinalTransition(hdr, nextStatus, nextAction)) {
				int tmplFinal = hdr.getMMailText_FinalApproved_ID(); // optional column on header
				if (tmplFinal > 0) {
					MailNoticeUtil.queueNotify(queueNotifis, requesterUserId, getTable_ID(), getRecord_ID(), MailNoticeUtil.setPOForMail(hdr.getMMailText_FinalApproved(),step));
					MailNoticeUtil.requestStepNotifyAll(queueNotifis,step, po, hdr, getTable_ID(),getRecord_ID(),
							MailNoticeUtil.setPOForMail(hdr.getMMailText_FinalApproved(),step),
							ctx, trxName);
				}
			}
		}

		// Audit decision
		int currUserID = Env.getAD_User_ID(Env.getCtx());
		AuditUtil.createAudit(ctx, trxName, po.get_Table_ID(), po.get_ID(), step.get_ID(),
				approve ? "APPROVE" : "REJECT",
						curStatus, nextStatus, curAction, nextAction, comment,currUserID);

		// If there is a next action, auto-queue and notify responsible actors for that next step
		if (nextAction != null && !nextAction.isBlank()) {
			MZZWFLines nxt = MZZWFLines.findByStatusAndAction(ctx, hdr.get_ID(), nextStatus, nextAction, trxName);
			if (nxt != null) {				
				AuditUtil.createAudit(ctx, trxName, po.get_Table_ID(), po.get_ID(), nxt.get_ID(),
						"REQUEST", nextStatus, nextStatus, null, nextAction, "Auto-queued next step",currUserID);
			}
			MailNoticeUtil.requestStepNotifyAll(queueNotifis,step, po, hdr, getTable_ID(),getRecord_ID(),
					MailNoticeUtil.setPOForMail(step.getMMailText_Approved(),step),ctx, trxName);
		}
		
	}

	private static void require(PO po, String col) {
		if (po.get_ColumnIndex(col) < 0)
			throw new AdempiereException("Required column missing: " + col);
	}

	private static boolean isFinalTransition(MZZWFHeader hdr, String nextStatus, String nextAction) {
		// If explicit final status configured on header, prefer it
		String finalStatus = hdr.getZZ_FinalStatus();
		if (finalStatus != null && !finalStatus.trim().isEmpty()) {
			if (finalStatus.equals(nextStatus)) return true;
		}
		// Otherwise, treat no next action as final
		return nextAction == null || nextAction.trim().isEmpty();
	}

}
