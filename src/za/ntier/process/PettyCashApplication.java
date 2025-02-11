package za.ntier.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.util.ProcessUtil;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;

import za.ntier.models.MZZPettyCashApplication;


@org.adempiere.base.annotation.Process
public class PettyCashApplication extends SvrProcess {
	private static final CLogger log = CLogger.getCLogger(ProcessUtil.class);

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

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
		} else if (mZZPettyCashApplication.getZZ_DocAction().equals(MZZPettyCashApplication.ZZ_DOCACTION_Approve) && 
				mZZPettyCashApplication.getZZ_DocStatus().equals(MZZPettyCashApplication.ZZ_DOCSTATUS_Submitted)) {
			mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_InProgress);
			mZZPettyCashApplication.setZZ_DocAction(MZZPettyCashApplication.ZZ_DOCACTION_FinalApproval);
			mZZPettyCashApplication.setZZ_Date_LM_Approved(new Timestamp(System.currentTimeMillis()));
		} else if (mZZPettyCashApplication.getZZ_DocAction().equals(MZZPettyCashApplication.ZZ_DOCACTION_FinalApproval) && 
				mZZPettyCashApplication.getZZ_DocStatus().equals(MZZPettyCashApplication.ZZ_DOCSTATUS_InProgress)) {
			mZZPettyCashApplication.setZZ_DocStatus(MZZPettyCashApplication.ZZ_DOCSTATUS_Approved);
			mZZPettyCashApplication.setZZ_DocAction(MZZPettyCashApplication.ZZ_DOCACTION_Complete);
			mZZPettyCashApplication.setZZ_Date_Approved(new Timestamp(System.currentTimeMillis()));
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
