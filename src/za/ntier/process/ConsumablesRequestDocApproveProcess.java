package za.ntier.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInventoryLine;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;

import za.co.ntier.fa.process.api.AbstractDocApproveProcess;
import za.co.ntier.fa.process.api.IDocApprove;
import za.ntier.models.MInventory_New;

@org.adempiere.base.annotation.Process(name="za.ntier.process.ConsumablesRequestDocApproveProcess")
public class ConsumablesRequestDocApproveProcess extends AbstractDocApproveProcess<MInventory_New> {

	private MInventory_New mInventory_New = null;
	
	@Override
	protected void initDocApproveObj() {
		docApprove = poObj;
	}

	@Override
	protected void doLogic(){
		requestConsumables();
	}

	private void requestConsumables() {
		ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(mInventory_New, DocAction.ACTION_Complete);
		if (pi.isError()) {
			throw new AdempiereException(pi.getSummary()); 
		}
	}

	@Override
	protected String doIt() throws Exception {
		mInventory_New = new MInventory_New(getCtx(), getRecord_ID(), get_TrxName());
		validateData();
		String currentDocAction = docApprove.getZZ_DocAction();
		String currentDocStatus = docApprove.getZZ_DocStatus();
		if(docApprove.isZZ_AllowLineManageApproved() && 
				IDocApprove.ZZ_DOCACTION_SubmitToLineManager.equals(currentDocAction)) {  // User presses Action button
			doSubmitDocForLineManage();			
		}else if(docApprove.isZZ_AllowLineManageApproved() &&     // Line Manager presses Action button	
				IDocApprove.ZZ_DOCACTION_ApproveDoNotApprove.equals(currentDocAction) && 
				IDocApprove.ZZ_DOCSTATUS_Submitted.equals(currentDocStatus)) {		
			doLineManageApprove();
		}else if(IDocApprove.ZZ_DOCACTION_SubmitToSDLFinanceMgr.equals(currentDocAction) &&  // SDL FIn Mgr presses Action Button
				IDocApprove.ZZ_DOCSTATUS_SubmittedToSDLFinanceMgr.equals(currentDocStatus)) {			
			doSDLFinManageApprove();
		}else if(docApprove.isZZ_AllowMgrFinConsumablesApproval() &&
				IDocApprove.ZZ_DOCACTION_ApproveDoNotApprove.equals(currentDocAction) && 
				IDocApprove.ZZ_DOCSTATUS_SubmittedToManagerFinanceConsumables.equals(currentDocStatus)) {  // consumables manager presses button
			doManagerFinConsumablesApprove();
		}
		else {
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
		
		sentNotify(queueNotifis, get_TrxName());
		return null;
	}

	
	//consumables manager presses button
	protected void doManagerFinConsumablesApprove() {
		docApprove.setZZ_Mgr_Fin_Consumables_ID(Env.getAD_User_ID(getCtx()));
		if("Y".equals(pApprove_Rej_MFC)){
			docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_Approved);
			docApprove.setZZ_Date_Approved(now);
			AbstractDocApproveProcess.queueNotify(queueNotifis, 
					docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailSnrApproved());
		}else{
			docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_NotApprovedByManagerFinanceConsumables);
			docApprove.setZZ_Date_MFC_Not_Approved(now);
			AbstractDocApproveProcess.queueNotify(queueNotifis, docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailLineReject());
		}
	}
	
	protected void doSubmitDocForSDLFinanceManage(boolean isBypassLineManage) {
		docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_SubmittedToSDLFinanceMgr);
		docApprove.setZZ_DocAction(IDocApprove.ZZ_DOCACTION_SubmitToSDLFinanceMgr);
		docApprove.setZZ_Date_LM_Approved(now);
		if (docApprove.getZZ_Date_Submitted() == null)
			docApprove.setZZ_Date_Submitted(now);

		AbstractDocApproveProcess.queueNotifyForRole(queueNotifis, IDocApprove.SNR_ADMIN_FIN_ROLE_ID, getTable_ID(), getRecord_ID(), docApprove.getZZMailRequestSnr());
	}

	// Line Manager presses Action button
	@Override
	protected void doLineManageApprove() {
		if("Y".equals(pApproveRejLM)){
			doSubmitDocForSDLFinanceManage(false);
			
		}else{
			docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_NotApprovedByLM);
			docApprove.setZZ_Date_Not_Approved_by_LM(now);
			AbstractDocApproveProcess.queueNotify(queueNotifis, docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailLineReject());
		}
	}
	
	// SDL FIn Mgr presses Action Button
	protected void doSDLFinManageApprove() {
		docApprove.setZZ_SDL_Fin_Mgr_ID(Env.getAD_User_ID(getCtx()));
		if("Y".equals(pApprove_Rej_SDL)){
			doSubmitDocFinConsumeablesMgr();
			
		}else{
			docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_NotApprovedBySDLFinanceMgr);
			docApprove.setZZ_Date_SDL_Not_Approved(now);
			AbstractDocApproveProcess.queueNotify(queueNotifis, docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailLineReject());
		}
	}

	protected void doSubmitDocFinConsumeablesMgr() {
		docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_SubmittedToManagerFinanceConsumables);
		docApprove.setZZ_DocAction(IDocApprove.ZZ_DOCACTION_ApproveDoNotApprove);
		docApprove.setZZ_Date_SDL_Approved(now);
		if (docApprove.getZZ_Date_Submitted() == null)
			docApprove.setZZ_Date_Submitted(now);

		AbstractDocApproveProcess.queueNotifyForRole(queueNotifis, IDocApprove.MANAGER_FIN_CONSUMABLES_ROLE_ID, getTable_ID(), getRecord_ID(), docApprove.getZZMailRequestSnr());
	}

	protected void validateData() {
		MInventoryLine[] lines = mInventory_New.getLines(false);
		if (lines.length == 0)		{
			throw new AdempiereException("@NoLines@");
		}
	}

}
