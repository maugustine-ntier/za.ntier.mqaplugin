package za.ntier.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.wf.MWorkflow;

import za.co.ntier.fa.process.api.AbstractDocApproveProcess;
import za.ntier.models.MInventory_New;

@org.adempiere.base.annotation.Process(name="za.co.ntier.fa.process.ConsumablesRequestDocApproveProcess")
public class ConsumablesRequestDocApproveProcess extends AbstractDocApproveProcess<MInventory_New> {
	
	@Override
	protected void initDocApproveObj() {
		docApprove = poObj;
	}
	
	@Override
	protected void doLogic(){
		requestConsumables();
	}
	
	private void requestConsumables() {
		MInventory_New mInventory_New = new MInventory_New(getCtx(), getRecord_ID(), get_TrxName());
		ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(mInventory_New, DocAction.ACTION_Complete);
		if (pi.isError()) {
			throw new AdempiereException(pi.getSummary()); 
		}
	}
}
