package za.ntier.process;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import za.co.ntier.fa.process.api.AbstractDocApproveProcess;
import za.co.ntier.fa.process.api.IDocApprove;
import za.ntier.models.MZZProgramMasterData;
import za.ntier.models.X_ZZ_Program_Master_Data;

@org.adempiere.base.annotation.Process(name="za.ntier.process.ProgramMaintenanceDocApproveProcess")
public class ProgramMaintenanceDocApproveProcess extends AbstractDocApproveProcess<MZZProgramMasterData> {

	// Convenience recipient buckets
	private final List<Integer> broadcastRoleIds = new ArrayList<>();

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
		if(IDocApprove.ZZ_DOCACTION_SubmitToSnrMgrLP.equals(currentDocAction)) {  // User presses Action button
			doSubmitToSnrMgrLP();			
		}else if(    
				IDocApprove.ZZ_DOCACTION_ApproveDoNotApprove.equals(currentDocAction) && 
				IDocApprove.ZZ_DOCSTATUS_Submitted.equals(currentDocStatus)) {		
			doLPApprove();
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


	protected void doApproval(boolean isBypassLineManage) {
		docApprove.setZZ_DocStatus(X_ZZ_Program_Master_Data.ZZ_DOCSTATUS_Approved);
		docApprove.setZZ_DocAction(null);
		docApprove.setZZ_Date_Manager_Approved(now);
		if (docApprove.getZZ_Date_Submitted() == null)
			docApprove.setZZ_Date_Submitted(now);
		AbstractDocApproveProcess.queueNotify(queueNotifis, docApprove.getCreatedBy(), getTable_ID(), getRecord_ID(), docApprove.getZZMailLineApproved());
	}

	// Line Manager presses Action button
	@Override
	protected void doLineManageApprove() {
		if("Y".equals(pApproveRejLM)){
			doApproval(false);

		}
	}

	/** Draft -> Submitted */
	private void doSubmitToSnrMgrLP() {

		// Set state
		docApprove.setZZ_DocStatus(IDocApprove.ZZ_DOCSTATUS_Submitted);
		docApprove.setZZ_DocAction(IDocApprove.ZZ_DOCACTION_ApproveDoNotApprove);
		if (docApprove.getZZ_Date_Submitted() == null)
			docApprove.setZZ_Date_Submitted(now());


		AbstractDocApproveProcess.queueNotifyForRole(queueNotifis, IDocApprove.ROLE_SNR_MANAGER_LP, getTable_ID(), getRecord_ID(),
				docApprove.getZZMailRequestLine());


	}

	private void doLPApprove() {
		docApprove.setZZ_Snr_Mgr_LP_ID(getAD_User_ID());
		docApprove.setZZ_DocStatus(X_ZZ_Program_Master_Data.ZZ_DOCSTATUS_Approved);
		docApprove.setZZ_DocAction(null);
		docApprove.setZZ_Date_Approved(now());

		// Broadcast: all Managers LP, Snr Managers (LP, SPU), Comms
		collectBroadcastAudience();
		for (int roleID : broadcastRoleIds) {
			AbstractDocApproveProcess.queueNotifyForRole(queueNotifis, roleID, getTable_ID(), getRecord_ID(),
					docApprove.getZZMailLineApproved());
		}

	}
	
	/** Gather LP Managers, Snr Managers (LP, SPU), Comms */
	private void collectBroadcastAudience() {
		broadcastRoleIds.add(IDocApprove.ROLE_MANAGER_LP);
		broadcastRoleIds.add(IDocApprove.ROLE_SNR_MGR_SPU);
	}

	private Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}


	protected void validateData() {
		//MInventoryLine[] lines = mInventory_New.getLines(false);
		//if (lines.length == 0)		{
		//	throw new AdempiereException("@NoLines@");
		//}
	}

}
