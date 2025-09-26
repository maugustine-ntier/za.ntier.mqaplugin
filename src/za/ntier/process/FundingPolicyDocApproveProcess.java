package za.ntier.process;

import org.compiere.model.MMailText;

import za.co.ntier.fa.process.api.AbstractDocApproveProcess;
import za.co.ntier.fa.process.api.IDocApprove;
import za.ntier.models.X_ZZ_Funding_Policy;

@org.adempiere.base.annotation.Process(name="za.ntier.process.FundingPolicyDocApproveProcess")
public class FundingPolicyDocApproveProcess extends AbstractDocApproveProcess<X_ZZ_Funding_Policy>  {

	

	@Override
	protected String doIt() throws Exception {
		X_ZZ_Funding_Policy x_ZZ_Funding_Policy = new X_ZZ_Funding_Policy(getCtx(),getRecord_ID(),get_TrxName());
		x_ZZ_Funding_Policy.setZZ_DocStatus("CO");
		x_ZZ_Funding_Policy.setZZ_DocAction(null);
		x_ZZ_Funding_Policy.saveEx();
		MMailText mailTemplate = new MMailText(getCtx(), 1000028, get_TrxName());
		mailTemplate.setPO(x_ZZ_Funding_Policy);
		AbstractDocApproveProcess.queueNotifyForRole(queueNotifis, IDocApprove.ROLE_MANAGER_LP_OPS, getTable_ID(), getRecord_ID(),
				mailTemplate);
		AbstractDocApproveProcess.queueNotifyForRole(queueNotifis, IDocApprove.ROLE_SNR_MANAGER_LP_OPS, getTable_ID(), getRecord_ID(),
				mailTemplate);
		sentNotify(queueNotifis, docApprove,get_TrxName());
		return "";
	}

	



}
