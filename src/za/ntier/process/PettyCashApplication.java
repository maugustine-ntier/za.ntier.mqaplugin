package za.ntier.process;



import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.util.ProcessUtil;
import org.compiere.model.MClient;
import org.compiere.model.MNote;
import org.compiere.model.MProcessPara;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.MUserRoles;
import org.compiere.model.X_AD_User;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import za.ntier.models.MZZPettyCashApplication;
import za.ntier.models.X_ZZ_Petty_Cash_Application;


@org.adempiere.base.annotation.Process
public class PettyCashApplication extends SvrProcess {
	private static final CLogger log = CLogger.getCLogger(ProcessUtil.class);
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
		int zz_Petty_Cash_Application_ID = getRecord_ID();
		MZZPettyCashApplication mZZPettyCashApplication = new MZZPettyCashApplication(getCtx(),zz_Petty_Cash_Application_ID,get_TrxName());
		if (mZZPettyCashApplication.getZZ_DocAction().equals(MZZPettyCashApplication.ZZ_DOCACTION_SubmitToLineManager)) {
			if (mZZPettyCashApplication.getLine_Manager_ID() <= 0) {
				return "Please select a Line Manager";
			}
			if (!mZZPettyCashApplication.isZZ_ID_Copy_Uploaded()) {
				return "Please upload your ID copy and tick the checkbox when done.";
			}
			mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_Submitted);
			mZZPettyCashApplication.setZZ_DocAction(MZZPettyCashApplication.DOCACTION_Approve);
			mZZPettyCashApplication.setZZ_Date_Submitted(new Timestamp(System.currentTimeMillis()));
			String subject = "Petty Cash  Application awaiting LM Approval";
			String message = "Please Approve or Reject the Petty Cash Application : " + mZZPettyCashApplication.getDocumentNo();
			int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
			sendNotification(mZZPettyCashApplication.getLine_Manager_ID(),zz_Petty_Cash_Application_ID,subject,message,ad_Message_ID);
		} else if (mZZPettyCashApplication.getZZ_DocAction().equals(MZZPettyCashApplication.ZZ_DOCACTION_ApproveDoNotApprove) && 
				mZZPettyCashApplication.getZZ_DocStatus().equals(MZZPettyCashApplication.ZZ_DOCSTATUS_Submitted)) {
			if (p_ZZ_Approve_Rej_LM.equals(MZZPettyCashApplication.ZZ_DOCSTATUS_Approved)) {
				mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_InProgress);
				mZZPettyCashApplication.setZZ_DocAction(MZZPettyCashApplication.ZZ_DOCACTION_FinalApprovalDoNotApprove);
				mZZPettyCashApplication.setZZ_Date_LM_Approved(new Timestamp(System.currentTimeMillis()));
				String subject = "Petty Cash  Application awaiting Snr Admin Finance Approval";
				String message = "Please Approve or Reject the Petty Cash Application : " + mZZPettyCashApplication.getDocumentNo();
				int ad_Message_ID = MESSAGE_LM_APPROVED_PETTYCASH_APP;
				sendNotificationToRole(SNR_ADMIN_FIN_ROLE_ID,zz_Petty_Cash_Application_ID,subject,message,ad_Message_ID);
			} else {
				mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_NotApprovedByLM);
				mZZPettyCashApplication.setZZ_Date_Not_Approved_by_LM(new Timestamp(System.currentTimeMillis()));
				String subject = "Petty Cash  Application Rejected by the LM";
				String message = "Your application was rejected Petty Cash Application : " + mZZPettyCashApplication.getDocumentNo();
				int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
				sendNotification(mZZPettyCashApplication.getCreatedBy(),zz_Petty_Cash_Application_ID,subject,message,ad_Message_ID);
			}

		} else if (mZZPettyCashApplication.getZZ_DocAction().equals(MZZPettyCashApplication.ZZ_DOCACTION_FinalApprovalDoNotApprove) && 
				mZZPettyCashApplication.getZZ_DocStatus().equals(MZZPettyCashApplication.ZZ_DOCSTATUS_InProgress)) {
			if (p_ZZ_Approve_Rej_SAF.equals(MZZPettyCashApplication.ZZ_DOCSTATUS_Approved)) {
				mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_Approved);
				mZZPettyCashApplication.setZZ_DocAction(MZZPettyCashApplication.ZZ_DOCACTION_Complete);
				mZZPettyCashApplication.setZZ_Date_Approved(new Timestamp(System.currentTimeMillis()));
				mZZPettyCashApplication.setZZ_Snr_Admin_Fin_ID(Env.getAD_User_ID(getCtx()));
				String subject = "Petty Cash  Application Has been Approved by the Snr Admin Finance";
				String message = "Your application was approved, Petty Cash Application : " + mZZPettyCashApplication.getDocumentNo();
				int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
				sendNotification(mZZPettyCashApplication.getCreatedBy(),zz_Petty_Cash_Application_ID,subject,message,ad_Message_ID);
			} else {
				mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_NotApprovedBySnrAdminFinance);
				mZZPettyCashApplication.setZZ_Date_Not_Approved_by_Snr_Adm_Fin(new Timestamp(System.currentTimeMillis()));
				String subject = "Petty Cash  Application Rejected by the Snr Admin Finance";
				String message = "Your application was rejected, Petty Cash Application : " + mZZPettyCashApplication.getDocumentNo();
				int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
				sendNotification(mZZPettyCashApplication.getCreatedBy(),zz_Petty_Cash_Application_ID,subject,message,ad_Message_ID);
				sendNotification(mZZPettyCashApplication.getLine_Manager_ID(),zz_Petty_Cash_Application_ID,subject,message,ad_Message_ID);
			}
		} else if (mZZPettyCashApplication.getZZ_DocAction().equals(MZZPettyCashApplication.ZZ_DOCACTION_Complete) && 
				mZZPettyCashApplication.getZZ_DocStatus().equals(MZZPettyCashApplication.ZZ_DOCSTATUS_Approved)) {
			if (!mZZPettyCashApplication.isZZ_AOR_Uploaded()) {
				return "Please upload the AOL and tick the checkbox when done.";
			}
			mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_Completed);
			mZZPettyCashApplication.setZZ_Date_Completed(new Timestamp(System.currentTimeMillis()));
		}
		if (!mZZPettyCashApplication.save()) {
			if (log.isLoggable(Level.FINE)) log.fine("Problem Saving.  Please contact Support");
			return "Problem Saving.  Please contact Support";
		}


		return null;
	}

	private void sendNotification(int ad_User_ID,int zz_Petty_Cash_Application_ID,String subject,String message,int ad_Message_ID) {
		MUser mUser = new MUser(getCtx(), ad_User_ID, null);
		if (X_AD_User.NOTIFICATIONTYPE_Notice.equals(mUser.getNotificationType()) ||
				X_AD_User.NOTIFICATIONTYPE_EMailPlusNotice.equals(mUser.getNotificationType())	) {
			MNote note = new MNote(getCtx(), ad_Message_ID, ad_User_ID,
					X_ZZ_Petty_Cash_Application.Table_ID, zz_Petty_Cash_Application_ID, 
					subject, message.toString(), get_TrxName());
			note.saveEx();
		}
		if (X_AD_User.NOTIFICATIONTYPE_EMail.equals(mUser.getNotificationType()) ||
			X_AD_User.NOTIFICATIONTYPE_EMailPlusNotice.equals(mUser.getNotificationType())) {
			MClient client = MClient.get(getCtx());
			MUser from = MUser.get(getCtx(), FROM_EMAIL_USER_ID);
			if (!client.sendEMail(from, mUser, subject, message, null)) {
				if (log.isLoggable(Level.FINE)) log.fine("Problem Sending Email.  Please contact Support");
			}
		}
	}

	private void sendNotificationToRole(int ad_Role_ID,int zz_Petty_Cash_Application_ID,String subject,String message,int ad_Message_ID) {
		MUserRoles [] mUserRoles = MUserRoles.getOfRole(getCtx(), ad_Role_ID);
		for (MUserRoles mUserRole : mUserRoles) {
			if (mUserRole.isActive()) {
				sendNotification(mUserRole.getAD_User_ID(),zz_Petty_Cash_Application_ID,subject,message,ad_Message_ID);
			}
		}
	}


}
