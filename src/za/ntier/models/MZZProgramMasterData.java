package za.ntier.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_AD_User;
import org.compiere.model.PO;

import za.co.ntier.fa.process.api.IDocApprove;

public class MZZProgramMasterData extends X_ZZ_Program_Master_Data implements IDocApprove{

	private static final long serialVersionUID = 1L;

	public MZZProgramMasterData(Properties ctx, int ZZ_Program_Master_Data_ID, String trxName) {
		super(ctx, ZZ_Program_Master_Data_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramMasterData(Properties ctx, int ZZ_Program_Master_Data_ID, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Program_Master_Data_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramMasterData(Properties ctx, String ZZ_Program_Master_Data_UU, String trxName) {
		super(ctx, ZZ_Program_Master_Data_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramMasterData(Properties ctx, String ZZ_Program_Master_Data_UU, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Program_Master_Data_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramMasterData(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setZZ_Date_Submitted(Timestamp submittDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Submitted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_LM_Approved(Timestamp approveDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZZ_Date_Not_Approved_by_LM(Timestamp notApproveDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_Snr_Admin_Fin_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setZZ_Snr_Admin_Fin_ID(int ZZ_Snr_Admin_Fin_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZZ_Date_Approved(Timestamp approveDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZZ_Date_Not_Approved_by_Snr_Adm_Fin(Timestamp ZZ_Date_Not_Approved_by_Snr_Adm_Fin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZZ_Date_Completed(Timestamp completeDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZZ_Date_MFC_Approved(Timestamp ZZ_Date_MFC_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_MFC_Not_Approved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isZZ_AllowMgrFinConsumablesApproval() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setZZ_Date_MFC_Not_Approved(Timestamp ZZ_Date_MFC_Not_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZZ_Date_SDL_Approved(Timestamp ZZ_Date_SDL_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_SDL_Approved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_SDL_Not_Approved(Timestamp ZZ_Date_SDL_Not_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_SDL_Not_Approved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_Account_Created(Timestamp ZZ_Date_Account_Created) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Account_Created() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_IT_Manager_Approved(Timestamp ZZ_Date_IT_Manager_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_IT_Manager_Approved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_IT_Manager_Rejected(Timestamp ZZ_Date_IT_Manager_Rejected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_IT_Manager_Rejected() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_Manager_Approved(Timestamp ZZ_Date_Manager_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Manager_Approved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_Manager_Rejected(Timestamp ZZ_Date_Manager_Rejected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Manager_Rejected() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_IT_Admin_ID(int ZZ_IT_Admin_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_IT_Admin_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_AD_User getZZ_IT_Admin() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_IT_Manager_ID(int ZZ_IT_Manager_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_IT_Manager_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_AD_User getZZ_IT_Manager() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Manager_ID(int ZZ_Manager_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_Manager_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setZZ_Mgr_Fin_Consumables_ID(int ZZ_Mgr_Fin_Consumables_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_Mgr_Fin_Consumables_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_AD_User getZZ_Mgr_Fin_Consumables() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_SDL_Fin_Mgr_ID(int ZZ_SDL_Fin_Mgr_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_SDL_Fin_Mgr_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_AD_User getZZ_SDL_Fin_Mgr() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (newRecord) {
			int ids[] = PO.getAllIDs(X_ZZ_Gen_Rules.Table_Name, null, get_TrxName());
			for (int id:ids) {
				X_ZZ_Gen_Rules X_ZZ_Gen_Rules = new X_ZZ_Gen_Rules(getCtx(),id,get_TrxName());
				MZZProgramGenRules mZZProgramGenRules = new MZZProgramGenRules(getCtx(), 0, get_TrxName());
				mZZProgramGenRules.setZZ_Program_Master_Data_ID(getZZ_Program_Master_Data_ID());
				mZZProgramGenRules.setLine(X_ZZ_Gen_Rules.getLine());
				mZZProgramGenRules.setName(X_ZZ_Gen_Rules.getName());
				mZZProgramGenRules.saveEx();
			}
		}
		return super.afterSave(newRecord, success);
	}

	@Override
	public void setZZ_Recommender_ID(int ZZ_Recommender_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_Recommender_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setZZ_Exec_Approver_ID(int ZZ_Exec_Approver_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_Exec_Approver_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setStartDate(Timestamp StartDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getStartDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEndDate(Timestamp EndDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getEndDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_Recommended(Timestamp ZZ_Date_Recommended) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Recommended() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_Not_Approved(Timestamp ZZ_Date_Not_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Not_Approved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_Not_Recommended(Timestamp ZZ_Date_Not_Recommended) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Not_Recommended() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
