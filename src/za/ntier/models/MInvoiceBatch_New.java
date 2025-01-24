package za.ntier.models;

import java.util.List;
import java.util.Properties;

import org.compiere.model.MInvoiceBatch;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;

public class MInvoiceBatch_New extends MInvoiceBatch implements I_C_InvoiceBatch {

	private static final long serialVersionUID = 1L;

	public MInvoiceBatch_New(Properties ctx, int C_InvoiceBatch_ID, String trxName) {
		super(ctx, C_InvoiceBatch_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setZZ_Status(String ZZ_Status) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getZZ_Status() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		String roles = MSysConfig.getValue("FINACE_ROLES");
		checkRole(roles);
		
		return super.beforeSave(newRecord);
	}

	private boolean checkRole(String roles) {
		if (roles == null || roles.equals("")) {
			log.saveError("Error", "FINACE_ROLES not set up in config. Contact support");
			return false;
		}
		boolean roleFound = false;
		List<String> roleList = List.of(roles.split(", ")); 
		for (String role:roleList) {
			String roleID = Env.getAD_Role_ID(getCtx()) + "";
			if (roleID.equals(role)) {
				roleFound = true;
			}
		}
		return roleFound;
	}
	
	

}
