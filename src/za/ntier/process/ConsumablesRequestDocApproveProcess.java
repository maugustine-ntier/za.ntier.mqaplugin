package za.ntier.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;

import za.co.ntier.fa.process.api.AbstractDocApproveProcess;
import za.co.ntier.fa.process.api.IDocApprove;
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

	@Override
	protected String doIt() throws Exception {
		validateData();
		String currentDocAction = docApprove.getZZ_DocAction();
		String currentDocStatus = docApprove.getZZ_DocStatus();
		if(docApprove.isZZ_AllowLineManageApproved() && 
				IDocApprove.ZZ_DOCACTION_SubmitToLineManager.equals(currentDocAction)) {

			doSubmitDocForLineManage();
			
		}else if(docApprove.isZZ_AllowLineManageApproved() &&
				IDocApprove.ZZ_DOCACTION_ApproveDoNotApprove.equals(currentDocAction) && 
				IDocApprove.ZZ_DOCSTATUS_Submitted.equals(currentDocStatus)) {
			
			doLineManageApprove();
		}else if(!docApprove.isZZ_AllowLineManageApproved() &&
				docApprove.isZZ_AllowSnrAdminFinanceApproved() &&
				IDocApprove.ZZ_DOCACTION_SubmitToLineManager.equals(currentDocAction)) {
			doSubmitDocForSnrAdminFinanceManage(true);
		}else if(docApprove.isZZ_AllowSnrAdminFinanceApproved() &&
				IDocApprove.ZZ_DOCACTION_FinalApprovalDoNotApprove.equals(currentDocAction) && 
				IDocApprove.ZZ_DOCSTATUS_InProgress.equals(currentDocStatus)) {
			
			doSnrAdminFinanceApprove();
			
		}else {
			throw new AdempiereException(Msg.getMsg(getCtx(), "ZZ_WrongWorkflowState", 
					new Object [] {docApprove.isZZ_AllowLineManageApproved(),
					docApprove.isZZ_AllowSnrAdminFinanceApproved(),
					currentDocStatus,
					currentDocAction}));
		}
		doFinalDocState(currentDocAction);
		poObj.saveEx();

		if (!Util.isEmpty(docApprove.getZZ_FinalWorkflowStateValue(), true) && docApprove.getZZ_FinalWorkflowStateValue().equals(docApprove.getZZ_DocStatus())) {
			doLogic();
		}
		return null;
	}
	
}
