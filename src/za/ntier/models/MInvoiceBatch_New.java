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
				if (Roles.checkRole(roles,role_ID)) {
					log.saveError("Error", Msg.getMsg(getCtx(), "MANAGERINVOICEBATCHDRAFT")); //"Only Manager OPS/SDL Roles can change to Completed");
					return false;
				}
			}
		}
		if (getZZ_Status() != null && getZZ_Status().equals(X_C_InvoiceBatch.ZZ_STATUS_InProgress)) {
			if (!isZZ_Account_Reconned() || !isZZ_Auth_PO_Order() || !isZZ_Calcs_Checked() || !isZZ_Cred_Bank_Dets_Verified() || !isZZ_GL_Allocation_Checked()) {
				log.saveError("Error", Msg.getMsg(getCtx(), "FINANCEDEPTCHECKLISTERROR")); 
				return false;
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

	/** Set Policy Procedure Checklist.
	@param ZZ_Policy_Procedure_Ck Policy Procedure Checklist
	 */
	public void setZZ_Policy_Procedure_Ck (Object ZZ_Policy_Procedure_Ck)
	{
		set_Value (COLUMNNAME_ZZ_Policy_Procedure_Ck, ZZ_Policy_Procedure_Ck);
	}

	/** Get Policy Procedure Checklist.
	@return Policy Procedure Checklist	  */
	public Object getZZ_Policy_Procedure_Ck()
	{
		return get_Value(COLUMNNAME_ZZ_Policy_Procedure_Ck);
	}

	/** Set Account Reconciled / O/S Invoices Verified.
	@param ZZ_Account_Reconned Account Reconciled / O/S Invoices Verified
	 */
	public void setZZ_Account_Reconned (boolean ZZ_Account_Reconned)
	{
		set_Value (COLUMNNAME_ZZ_Account_Reconned, Boolean.valueOf(ZZ_Account_Reconned));
	}

	/** Get Account Reconciled / O/S Invoices Verified.
	@return Account Reconciled / O/S Invoices Verified	  */
	public boolean isZZ_Account_Reconned()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Account_Reconned);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Authorized Purchase Order/SLA Attached.
	@param ZZ_Auth_PO_Order Authorized Purchase Order/SLA Attached
	 */
	public void setZZ_Auth_PO_Order (boolean ZZ_Auth_PO_Order)
	{
		set_Value (COLUMNNAME_ZZ_Auth_PO_Order, Boolean.valueOf(ZZ_Auth_PO_Order));
	}

	/** Get Authorized Purchase Order/SLA Attached.
	@return Authorized Purchase Order/SLA Attached	  */
	public boolean isZZ_Auth_PO_Order()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Auth_PO_Order);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Calculation Checked.
	@param ZZ_Calcs_Checked Calculation Checked
	 */
	public void setZZ_Calcs_Checked (boolean ZZ_Calcs_Checked)
	{
		set_Value (COLUMNNAME_ZZ_Calcs_Checked, Boolean.valueOf(ZZ_Calcs_Checked));
	}

	/** Get Calculation Checked.
	@return Calculation Checked	  */
	public boolean isZZ_Calcs_Checked()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Calcs_Checked);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Creditor ID &amp; Banking Details Verified.
	@param ZZ_Cred_Bank_Dets_Verified Creditor ID &amp; Banking Details Verified
	 */
	public void setZZ_Cred_Bank_Dets_Verified (boolean ZZ_Cred_Bank_Dets_Verified)
	{
		set_Value (COLUMNNAME_ZZ_Cred_Bank_Dets_Verified, Boolean.valueOf(ZZ_Cred_Bank_Dets_Verified));
	}

	/** Get Creditor ID &amp; Banking Details Verified.
	@return Creditor ID &amp; Banking Details Verified	  */
	public boolean isZZ_Cred_Bank_Dets_Verified()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Cred_Bank_Dets_Verified);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set GL Allocation Checked.
	@param ZZ_GL_Allocation_Checked GL Allocation Checked
	 */
	public void setZZ_GL_Allocation_Checked (boolean ZZ_GL_Allocation_Checked)
	{
		set_Value (COLUMNNAME_ZZ_GL_Allocation_Checked, Boolean.valueOf(ZZ_GL_Allocation_Checked));
	}

	/** Get GL Allocation Checked.
	@return GL Allocation Checked	  */
	public boolean isZZ_GL_Allocation_Checked()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_GL_Allocation_Checked);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}





}
