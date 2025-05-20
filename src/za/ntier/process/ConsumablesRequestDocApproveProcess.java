package za.ntier.process;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MUserRoles;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
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
		}else if(docApprove.isZZ_AllowLineManageApproved() &&
				IDocApprove.ZZ_DOCACTION_ApproveDoNotApprove.equals(currentDocAction) && 
				IDocApprove.ZZ_DOCSTATUS_Submitted.equals(currentDocStatus)) {	// Line Manager presses Action button		
			doLineManageApprove();
		}else if(docApprove.isZZ_AllowMgrFinConsumablesApproval() &&
				IDocApprove.ZZ_DOCACTION_ApproveDoNotApprove.equals(currentDocAction) && 
				IDocApprove.ZZ_DOCSTATUS_SubmittedToManagerFinanceConsumables.equals(currentDocStatus)) {  // consumables manager presses button
			doManagerFinConsumablesApprove();
		}else if(docApprove.isZZ_AllowSnrAdminFinanceApproved() &&  // fin admin presses button
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

	//consumables manager presses button
	protected void doManagerFinConsumablesApprove() {
		if("Y".equals(pApprove_Rej_MFC)){
			if (docApprove.isZZ_AllowSnrAdminFinanceApproved()) {
				doSubmitDocForSnrAdminFinanceManage(false);
			}else {
				docApprove.setZZ_Date_Approved(now);
				docApprove.setZZ_Date_MFC_Approved(now);
				docApprove.setZZ_Date_Completed(now);
				AbstractDocApproveProcess.sendNotification(
						docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailLineApproved(), get_TrxName());
			}
		}else{
			docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_NotApprovedByManagerFinanceConsumables);
			docApprove.setZZ_Date_MFC_Not_Approved(now);
			AbstractDocApproveProcess.sendNotification(docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailLineReject(), get_TrxName());
		}
	}

	protected void doSubmitDocForSnrAdminFinanceManage(boolean isBypassLineManage) {
		docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_InProgress);
		docApprove.setZZ_DocAction(IDocApprove.ZZ_DOCACTION_FinalApprovalDoNotApprove);
		docApprove.setZZ_Date_MFC_Approved(now);
		docApprove.setZZ_Mgr_Fin_Consumables_ID(getAD_User_ID());
		if (isBypassLineManage && docApprove.getZZ_Date_Submitted() == null)
			docApprove.setZZ_Date_Submitted(now);

		Arrays.asList(MUserRoles.getOfRole(getCtx(), IDocApprove.SNR_ADMIN_FIN_ROLE_ID)).forEach(role -> {
			if (role.isActive()) {
				AbstractDocApproveProcess.sendNotification(role.getAD_User_ID(), getTable_ID(), getRecord_ID(), docApprove.getZZMailRequestSnr(), get_TrxName());
			}
		});


	}

	@Override
	protected void doLineManageApprove() {
		if("Y".equals(pApproveRejLM)){
			if (docApprove.isZZ_AllowMgrFinConsumablesApproval()) {
				doSubmitDocFinConsumeablesMgr();
			}else {
				docApprove.setZZ_Date_Approved(now);
				docApprove.setZZ_Date_LM_Approved(now);
				docApprove.setZZ_Date_Completed(now);
				AbstractDocApproveProcess.sendNotification(
						docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailLineApproved(), get_TrxName());
			}
		}else{
			docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_NotApprovedByLM);
			docApprove.setZZ_Date_Not_Approved_by_LM(now);
			AbstractDocApproveProcess.sendNotification(docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailLineReject(), get_TrxName());
		}
	}

	protected void doSubmitDocFinConsumeablesMgr() {
		docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_SubmittedToManagerFinanceConsumables);
		docApprove.setZZ_DocAction(IDocApprove.ZZ_DOCACTION_ApproveDoNotApprove);
		docApprove.setZZ_Date_LM_Approved(now);
		if (docApprove.getZZ_Date_Submitted() == null)
			docApprove.setZZ_Date_Submitted(now);

		Arrays.asList(MUserRoles.getOfRole(getCtx(), IDocApprove.MANAGER_FIN_CONSUMABLES_ROLE_ID)).forEach(role -> {
			if (role.isActive()) {
				AbstractDocApproveProcess.sendNotification(role.getAD_User_ID(), getTable_ID(), getRecord_ID(), docApprove.getZZMailRequestSnr(), get_TrxName());
			}
		});	

	}

	protected void validateData() {
		MInventoryLine[] lines = mInventory_New.getLines(false);
		if (lines.length == 0)		{
			throw new AdempiereException("@NoLines@");
		}
	}

}
