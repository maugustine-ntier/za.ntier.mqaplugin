package za.ntier.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_AD_User;

import za.co.ntier.fa.process.api.IDocApprove;

public class MZZSystemAccessApplication extends X_ZZ_System_Access_Application implements IDocApprove {

	public MZZSystemAccessApplication(Properties ctx, int ZZ_System_Access_Application_ID, String trxName) {
		super(ctx, ZZ_System_Access_Application_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZSystemAccessApplication(Properties ctx, int ZZ_System_Access_Application_ID, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_System_Access_Application_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZSystemAccessApplication(Properties ctx, String ZZ_System_Access_Application_UU, String trxName) {
		super(ctx, ZZ_System_Access_Application_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZSystemAccessApplication(Properties ctx, String ZZ_System_Access_Application_UU, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_System_Access_Application_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZSystemAccessApplication(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLine_Manager_ID() {
		// TODO Auto-generated method stub
		return 0;
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

}
