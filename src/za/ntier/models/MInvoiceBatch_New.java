package za.ntier.models;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MInvoiceBatch;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import za.ntier.utils.Roles;

public class MInvoiceBatch_New extends MInvoiceBatch implements I_C_InvoiceBatch {

	private static final String MANAGER_OPS_SDL_ROLES = "MANAGER_OPS_SDL_ROLES";
	private static final String FINACE_ROLES = "FINACE_ROLES";
	private static final long serialVersionUID = 1L;

	public MInvoiceBatch_New(Properties ctx, int C_InvoiceBatch_ID, String trxName) {
		super(ctx, C_InvoiceBatch_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInvoiceBatch_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	public void setZZ_Status(String ZZ_Status) {
		set_Value (COLUMNNAME_ZZ_Status, ZZ_Status);

	}

	@Override
	public String getZZ_Status() {
		return (String)get_Value(COLUMNNAME_ZZ_Status);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		String roles = MSysConfig.getValue(FINACE_ROLES); 
		if (!checkRoleSetup(roles,FINACE_ROLES)) {
			return false;
		}
		
		int role_ID = Env.getAD_Role_ID(getCtx());
		if (newRecord) {
			if (Roles.checkRole(roles,role_ID)) {
				if (getZZ_Status() == null || !(getZZ_Status().equals(X_C_InvoiceBatch.ZZ_STATUS_Drafted))) {
					log.saveError("Error", Msg.getMsg(getCtx(), "INVOICEBATCHSTATUSDRAFTED")); // "Status must be drafted for New Records");
					return false;
				}
			} else {
				log.saveError("Error", Msg.getMsg(getCtx(), "FINANCEROLESINVOICEBATCHCREATE")); // "Only Finance Roles are allowed to create Invoice Batches.");
				return false;
			}
		} else {
			if (getZZ_Status() != null && is_ValueChanged(COLUMNNAME_ZZ_Status) && getZZ_Status().equals(X_C_InvoiceBatch.ZZ_STATUS_InProgress)) {
				if (!Roles.checkRole(roles,role_ID)) {
					log.saveError("Error", Msg.getMsg(getCtx(), "FINANCEROLESINVOICEBATCHINPROG")); //"Only Finance Roles can change to In Progress");
					return false;
				}
			}
			roles = MSysConfig.getValue(MANAGER_OPS_SDL_ROLES);
			if (!checkRoleSetup(roles,MANAGER_OPS_SDL_ROLES)) {
				return false;
			}
			if (getZZ_Status() != null && is_ValueChanged(COLUMNNAME_ZZ_Status) && getZZ_Status().equals(X_C_InvoiceBatch.ZZ_STATUS_Completed)) {
				if (!Roles.checkRole(roles,role_ID)) {
					log.saveError("Error", Msg.getMsg(getCtx(), "MANAGERINVOICEBATCHCOMPLETED")); //"Only Manager OPS/SDL Roles can change to Completed");
					return false;
				}
			}
			if (getZZ_Status() != null && is_ValueChanged(COLUMNNAME_ZZ_Status) && getZZ_Status().equals(X_C_InvoiceBatch.ZZ_STATUS_Drafted)) {
				if (!Roles.checkRole(roles,role_ID)) {
					log.saveError("Error", Msg.getMsg(getCtx(), "MANAGERINVOICEBATCHDRAFT")); //"Only Manager OPS/SDL Roles can change to Completed");
					return false;
				}
			}
		}
		
		return super.beforeSave(newRecord);
	}

	private boolean checkRoleSetup(String roles,String roleSetup) {
		if (roles == null || roles.equals("")) {
			log.saveError("Error", roleSetup + " not set up in config. Contact support");
			return false;
		}
		return true;
	}


	
	

}
