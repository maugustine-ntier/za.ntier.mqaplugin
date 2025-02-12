package za.ntier.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.util.ProcessUtil;
import org.compiere.model.MProcessPara;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;

import za.ntier.models.MZZPettyCashApplication;


@org.adempiere.base.annotation.Process
public class PettyCashApplication extends SvrProcess {
	private static final CLogger log = CLogger.getCLogger(ProcessUtil.class);
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
			mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_Submitted);
			mZZPettyCashApplication.setZZ_DocAction(MZZPettyCashApplication.DOCACTION_Approve);
			mZZPettyCashApplication.setZZ_Date_Submitted(new Timestamp(System.currentTimeMillis()));
		} else if (mZZPettyCashApplication.getZZ_DocAction().equals(MZZPettyCashApplication.ZZ_DOCACTION_ApproveDoNotApprove) && 
				mZZPettyCashApplication.getZZ_DocStatus().equals(MZZPettyCashApplication.ZZ_DOCSTATUS_Submitted)) {
			if (p_ZZ_Approve_Rej_LM.equals(MZZPettyCashApplication.ZZ_DOCSTATUS_Approved)) {
				mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_InProgress);
				mZZPettyCashApplication.setZZ_DocAction(MZZPettyCashApplication.ZZ_DOCACTION_FinalApprovalDoNotApprove);
				mZZPettyCashApplication.setZZ_Date_LM_Approved(new Timestamp(System.currentTimeMillis()));
			} else {
				mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_NotApprovedByLM);
				mZZPettyCashApplication.setZZ_Date_Not_Approved_by_LM(new Timestamp(System.currentTimeMillis()));
			}

		} else if (mZZPettyCashApplication.getZZ_DocAction().equals(MZZPettyCashApplication.ZZ_DOCACTION_FinalApprovalDoNotApprove) && 
				mZZPettyCashApplication.getZZ_DocStatus().equals(MZZPettyCashApplication.ZZ_DOCSTATUS_InProgress)) {
			if (p_ZZ_Approve_Rej_SAF.equals(MZZPettyCashApplication.ZZ_DOCSTATUS_Approved)) {
				mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_Approved);
				mZZPettyCashApplication.setZZ_DocAction(MZZPettyCashApplication.ZZ_DOCACTION_Complete);
				mZZPettyCashApplication.setZZ_Date_Approved(new Timestamp(System.currentTimeMillis()));
			} else {
				mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_NotApprovedBySnrAdminFinance);
				mZZPettyCashApplication.setZZ_Date_Not_Approved_by_Snr_Adm_Fin(new Timestamp(System.currentTimeMillis()));
			}
		} else if (mZZPettyCashApplication.getZZ_DocAction().equals(MZZPettyCashApplication.ZZ_DOCACTION_Complete) && 
				mZZPettyCashApplication.getZZ_DocStatus().equals(MZZPettyCashApplication.ZZ_DOCSTATUS_Approved)) {
			mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_Completed);
			mZZPettyCashApplication.setZZ_Date_Completed(new Timestamp(System.currentTimeMillis()));
		}
		if (!mZZPettyCashApplication.save()) {
			if (log.isLoggable(Level.FINE)) log.fine("Problem Saving.  Please contact Support");
			return "Problem Saving.  Please contact Support";
		}


		return null;
	}


}
