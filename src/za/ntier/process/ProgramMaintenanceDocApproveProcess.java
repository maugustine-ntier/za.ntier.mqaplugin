package za.ntier.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MMailText;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import za.co.ntier.fa.process.api.AbstractDocApproveProcess;
import za.co.ntier.fa.process.api.IDocApprove;
import za.ntier.models.MZZProgramMasterData;
import za.ntier.models.MZZSystemAccessApplication;
import za.ntier.models.X_ZZ_Program_Master_Data;
import za.ntier.models.X_ZZ_System_Access_Application;

@org.adempiere.base.annotation.Process(name="za.ntier.process.ProgramMaintenanceDocApproveProcess")
public class ProgramMaintenanceDocApproveProcess extends AbstractDocApproveProcess<MZZProgramMasterData> {

	
	
	@Override
	protected void initDocApproveObj() {
		docApprove = poObj;
	}

	@Override
	protected void doLogic(){
		
	}

	
	@Override
	protected String doIt() throws Exception {
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

	
	//IT Admin presses button
	protected void doITADminApprove() {
		docApprove.setZZ_IT_Admin_ID(Env.getAD_User_ID(getCtx()));
		if("Y".equals(pApprove_Rej_MFC)){
			docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_Completed);
			docApprove.setZZ_Date_Account_Created(now);
			AbstractDocApproveProcess.queueNotify(queueNotifis, 
					docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), new MMailText(getCtx(), 1000018, get_TrxName()));
		}else{
			// IT Admin does not reject, just creates accounts
		}
	}
	
	protected void doApproval(boolean isBypassLineManage) {
		docApprove.setZZ_DocStatus(X_ZZ_Program_Master_Data.ZZ_DOCSTATUS_Approved);
		docApprove.setZZ_DocAction(null);
		docApprove.setZZ_Date_Manager_Approved(now);
		if (docApprove.getZZ_Date_Submitted() == null)
			docApprove.setZZ_Date_Submitted(now);

		AbstractDocApproveProcess.queueNotifyForRole(queueNotifis, IDocApprove.IT_MGR_ROLE_ID, getTable_ID(), getRecord_ID(), docApprove.getZZMailRequestSnr());
	}

	// Line Manager presses Action button
	@Override
	protected void doLineManageApprove() {
		if("Y".equals(pApproveRejLM)){
			doApproval(false);
			
		}else{
			docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_NotApprovedByLM);
			docApprove.setZZ_Date_Manager_Rejected(now);
			AbstractDocApproveProcess.queueNotify(queueNotifis, docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailLineReject());
		}
	}
	
	
	protected void validateData() {
		//MInventoryLine[] lines = mInventory_New.getLines(false);
		//if (lines.length == 0)		{
		//	throw new AdempiereException("@NoLines@");
		//}
	}

}
