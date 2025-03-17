package za.ntier.process;



import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.util.ProcessUtil;
import org.compiere.model.MProcessPara;
import org.compiere.model.MSysConfig;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import za.ntier.models.MZZPettyCashAdvanceHdr;
import za.ntier.models.X_ZZ_Petty_Cash_Advance_Hdr;
import za.ntier.models.X_ZZ_Petty_Cash_Advance_Line;
import za.ntier.utils.Notifications;


@org.adempiere.base.annotation.Process
public class PettyCashAdvanceRequest extends SvrProcess {	
	private static final String YOUR_APPLICATION_WAS_APPROVED_PETTY_CASH_CARD_APPLICATION = "Your application was approved, Petty Cash Advance Request : ";
	private static final String YOUR_APPLICATION_WAS_REJECTED_PETTY_CASH_CARD_APPLICATION = "Your application was rejected, Petty Cash Advance Request : ";
	private static final CLogger log = CLogger.getCLogger(ProcessUtil.class);
	private static final String PLEASE_APPROVE_OR_REJECT_THE_PETTY_CASH_CARD_APPLICATION = "Please Approve or Reject the Petty Cash Advance Request : ";
	private static final String PETTY_CASH_CARD_APPLICATION = "Petty Cash Advance Request ";
	public final static int MESSAGE_NEW_PETTYCASH_APP = 1000009;
	public final static int MESSAGE_LM_APPROVED_PETTYCASH_APP = 1000010;
	public final static int SNR_ADMIN_FIN_ROLE_ID = 1000003;
	public final static int FROM_EMAIL_USER_ID = MSysConfig.getIntValue("FROM_EMAIL_USER_ID",1000011);
	String p_ZZ_Approve_Rej_LM = "";
	String p_ZZ_Approve_Rej_SAF = "";

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("ZZ_Approve_Rej_LM"))
				p_ZZ_Approve_Rej_LM = (String)para[i].getParameter();
			else if (name.equals("ZZ_Approve_Rej_SAF"))
				p_ZZ_Approve_Rej_SAF = (String)para[i].getParameter();
			else
				MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para[i]);
		}

	}

	@Override
	protected String doIt() throws Exception {
		int zz_Petty_Cash_Advance_Hdr_ID = getRecord_ID();
		MZZPettyCashAdvanceHdr mZZPettyCashAdvanceHdr = new MZZPettyCashAdvanceHdr(getCtx(),zz_Petty_Cash_Advance_Hdr_ID,get_TrxName());
		int x_ZZ_Petty_Cash_Advance_Line_IDs[] = PO.getAllIDs(X_ZZ_Petty_Cash_Advance_Line.Table_Name,MZZPettyCashAdvanceHdr.COLUMNNAME_ZZ_Petty_Cash_Advance_Hdr_ID + " = " + zz_Petty_Cash_Advance_Hdr_ID , null);
		if (x_ZZ_Petty_Cash_Advance_Line_IDs == null || x_ZZ_Petty_Cash_Advance_Line_IDs.length <= 0) {
			return "@Error@Please enter at least one Petty Cash Advance Line";
		}
		if (mZZPettyCashAdvanceHdr.getZZ_DocAction().equals(MZZPettyCashAdvanceHdr.ZZ_DOCACTION_SubmitToLineManager)) {
			if (mZZPettyCashAdvanceHdr.getLine_Manager_ID() <= 0) {
				return "Please select a Line Manager";
			}
			mZZPettyCashAdvanceHdr.setZZ_DocStatus(MZZPettyCashAdvanceHdr.ZZ_DOCSTATUS_Submitted);
			mZZPettyCashAdvanceHdr.setZZ_DocAction(MZZPettyCashAdvanceHdr.ZZ_DOCACTION_ApproveDoNotApprove);
			mZZPettyCashAdvanceHdr.setZZ_Date_Submitted(new Timestamp(System.currentTimeMillis()));
			String subject = PETTY_CASH_CARD_APPLICATION + "awaiting LM Approval";
			String message = PLEASE_APPROVE_OR_REJECT_THE_PETTY_CASH_CARD_APPLICATION + mZZPettyCashAdvanceHdr.getDocumentNo();
			int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
			Notifications.sendNotification(mZZPettyCashAdvanceHdr.getLine_Manager_ID(),zz_Petty_Cash_Advance_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Advance_Hdr.Table_ID);
		} else if (mZZPettyCashAdvanceHdr.getZZ_DocAction().equals(MZZPettyCashAdvanceHdr.ZZ_DOCACTION_ApproveDoNotApprove) && 
				mZZPettyCashAdvanceHdr.getZZ_DocStatus().equals(MZZPettyCashAdvanceHdr.ZZ_DOCSTATUS_Submitted)) {
			if (p_ZZ_Approve_Rej_LM.equals("Y")) {
				mZZPettyCashAdvanceHdr.setZZ_DocStatus(MZZPettyCashAdvanceHdr.ZZ_DOCSTATUS_InProgress);
				mZZPettyCashAdvanceHdr.setZZ_DocAction(MZZPettyCashAdvanceHdr.ZZ_DOCACTION_FinalApprovalDoNotApprove);
				mZZPettyCashAdvanceHdr.setZZ_Date_LM_Approved(new Timestamp(System.currentTimeMillis()));
				String subject = PETTY_CASH_CARD_APPLICATION + "awaiting Finance Approval";
				String message = PLEASE_APPROVE_OR_REJECT_THE_PETTY_CASH_CARD_APPLICATION + mZZPettyCashAdvanceHdr.getDocumentNo();
				int ad_Message_ID = MESSAGE_LM_APPROVED_PETTYCASH_APP;
				Notifications.sendNotificationToRole(SNR_ADMIN_FIN_ROLE_ID,zz_Petty_Cash_Advance_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Advance_Hdr.Table_ID);
			} else {
				mZZPettyCashAdvanceHdr.setZZ_DocStatus(MZZPettyCashAdvanceHdr.ZZ_DOCSTATUS_NotApprovedByLM);
				mZZPettyCashAdvanceHdr.setZZ_Date_Not_Approved_by_LM(new Timestamp(System.currentTimeMillis()));
				String subject = PETTY_CASH_CARD_APPLICATION + "Rejected by the LM";
				String message = YOUR_APPLICATION_WAS_REJECTED_PETTY_CASH_CARD_APPLICATION + mZZPettyCashAdvanceHdr.getDocumentNo();
				int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
				Notifications.sendNotification(mZZPettyCashAdvanceHdr.getCreatedBy(),zz_Petty_Cash_Advance_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Advance_Hdr.Table_ID);
			}

		} else if (mZZPettyCashAdvanceHdr.getZZ_DocAction().equals(MZZPettyCashAdvanceHdr.ZZ_DOCACTION_FinalApprovalDoNotApprove) && 
				mZZPettyCashAdvanceHdr.getZZ_DocStatus().equals(MZZPettyCashAdvanceHdr.ZZ_DOCSTATUS_InProgress)) {
			if (p_ZZ_Approve_Rej_SAF.equals("Y")) {
				mZZPettyCashAdvanceHdr.setZZ_DocStatus(MZZPettyCashAdvanceHdr.ZZ_DOCSTATUS_Completed);
				mZZPettyCashAdvanceHdr.setZZ_Date_Completed(new Timestamp(System.currentTimeMillis()));
				mZZPettyCashAdvanceHdr.setZZ_Snr_Admin_Fin_ID(Env.getAD_User_ID(getCtx()));
				String subject = PETTY_CASH_CARD_APPLICATION + "Has been Approved by Finance";
				String message = YOUR_APPLICATION_WAS_APPROVED_PETTY_CASH_CARD_APPLICATION + mZZPettyCashAdvanceHdr.getDocumentNo();
				int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
				Notifications.sendNotification(mZZPettyCashAdvanceHdr.getCreatedBy(),zz_Petty_Cash_Advance_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Advance_Hdr.Table_ID);
			} else {
				mZZPettyCashAdvanceHdr.setZZ_DocStatus(MZZPettyCashAdvanceHdr.ZZ_DOCSTATUS_NotApprovedBySnrAdminFinance);
				mZZPettyCashAdvanceHdr.setZZ_Date_Not_Approved_by_Snr_Adm_Fin(new Timestamp(System.currentTimeMillis()));
				String subject = PETTY_CASH_CARD_APPLICATION + "Rejected by Finance";
				String message = YOUR_APPLICATION_WAS_REJECTED_PETTY_CASH_CARD_APPLICATION + mZZPettyCashAdvanceHdr.getDocumentNo();
				int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
				Notifications.sendNotification(mZZPettyCashAdvanceHdr.getCreatedBy(),zz_Petty_Cash_Advance_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Advance_Hdr.Table_ID);
				Notifications.sendNotification(mZZPettyCashAdvanceHdr.getLine_Manager_ID(),zz_Petty_Cash_Advance_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Advance_Hdr.Table_ID);
			}
		} 
		if (!mZZPettyCashAdvanceHdr.save()) {
			if (log.isLoggable(Level.FINE)) log.fine("Problem Saving.  Please contact Support");
			return "Problem Saving.  Please contact Support";
		}


		return null;
	}



}
