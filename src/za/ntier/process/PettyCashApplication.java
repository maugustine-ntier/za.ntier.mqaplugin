package za.ntier.process;

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
		if (mZZPettyCashApplication.getZZ_DocAction().equals(mZZPettyCashApplication.ZZ_DOCACTION_SubmitToLineManager)) {
			if (mZZPettyCashApplication.getLine_Manager_ID() <= 0) {
				return "Please select a Line Manager";
			}
			mZZPettyCashApplication.setZZ_DocStatus(mZZPettyCashApplication.ZZ_DOCSTATUS_Submitted);
		}
		if (mZZPettyCashApplication.getZZ_DocAction().equals(mZZPettyCashApplication.ZZ_DOCACTION_Approve) && mZZPettyCashApplication.getZZ_DocStatus().equals(mZZPettyCashApplication.ZZ_DOCSTATUS_Submitted)) {
			mZZPettyCashApplication.setZZ_DocStatus(mZZPettyCashApplication.ZZ_DOCSTATUS_InProgress);
		}
		if (mZZPettyCashApplication.getZZ_DocAction().equals(mZZPettyCashApplication.ZZ_DOCACTION_Approve) && mZZPettyCashApplication.getZZ_DocStatus().equals(mZZPettyCashApplication.ZZ_DOCSTATUS_InProgress)) {
			mZZPettyCashApplication.setZZ_DocStatus(mZZPettyCashApplication.ZZ_DOCSTATUS_Approved);
		}
		if (!mZZPettyCashApplication.save()) {
			if (log.isLoggable(Level.FINE)) log.fine("Problem Saving.  Please contact Support");
			return "Problem Saving.  Please contact Support";
		}
		
		return null;
	}


}
