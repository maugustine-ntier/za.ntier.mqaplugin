package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MBPBankAccount;
import org.compiere.model.MBPartner;
import org.compiere.model.MLocation;
import org.compiere.model.MUser;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MBPBankAccount_New extends MBPBankAccount implements za.ntier.models.I_C_BP_BankAccount{

	private static final long serialVersionUID = 1L;

	public MBPBankAccount_New(Properties ctx, String C_BP_BankAccount_UU, String trxName) {
		super(ctx, C_BP_BankAccount_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPBankAccount_New(Properties ctx, int C_BP_BankAccount_ID, String trxName) {
		super(ctx, C_BP_BankAccount_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPBankAccount_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPBankAccount_New(Properties ctx, MBPartner bp, MUser bpc, MLocation location) {
		super(ctx, bp, bpc, location);
		// TODO Auto-generated constructor stub
	}

	public MBPBankAccount_New(MBPBankAccount copy) {
		super(copy);
		// TODO Auto-generated constructor stub
	}

	public MBPBankAccount_New(Properties ctx, MBPBankAccount copy) {
		super(ctx, copy);
		// TODO Auto-generated constructor stub
	}

	public MBPBankAccount_New(Properties ctx, MBPBankAccount copy, String trxName) {
		super(ctx, copy, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		// Only a Manager - OPS Finance and Manager - SDL Finance can approve/unapprove a created bank record.
		//Snr Admin - Finance and Admin - Finance
		if (getComments() != null && getComments().trim().equals("Initial Import")) {
			return super.beforeSave(newRecord);
		}
		if (newRecord && (Env.getAD_Role_ID(getCtx()) != 1000003 && Env.getAD_Role_ID(getCtx()) != 1000002
				&& Env.getAD_Role_ID(getCtx()) != 1000007)) {
			log.saveError("Error", "Only Snr Admin Finance,Admin Finance or the SDL Officer can create new Bank Accounts ");
			return false;
		}
		if (newRecord && isZZ_Approve()) {
			log.saveError("Error", "Cannot create a approved bank account. ");
			return false;
		}
		if ((!newRecord || (newRecord && isZZ_Approve())) && is_ValueChanged(COLUMNNAME_ZZ_Approve) && Env.getAD_Role_ID(getCtx()) != 1000004 && Env.getAD_Role_ID(getCtx()) != 1000005) {
			log.saveError("Error", "Only SDL/Ops Finance Manager can approve/unapprove a Bank Account Record ");
			return false;
		}
		
		if (isZZ_Approve()) {
			int cnt = DB.getSQLValue(get_TrxName(), "Select count(*) from C_BP_BankAccount a where a.zz_approve = 'Y' and a.C_Bpartner_ID = ?"
					+ " and a.C_BP_BankAccount_ID <> ?",getC_BPartner_ID(),getC_BP_BankAccount_ID());
			if (cnt >= 1) {
				log.saveError("Error", "Cannot have 2 approved bank accounts for a Business Partner.");
				return false;
			}
		}
		
		return super.beforeSave(newRecord);
	}

	@Override
	public void setComments(String Comments) {
		set_Value (COLUMNNAME_Comments, Comments);		
	}

	@Override
	public String getComments() {
		return (String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Approve.
	@param ZZ_Approve Approve
	 */
	public void setZZ_Approve (boolean ZZ_Approve)
	{
		set_Value (COLUMNNAME_ZZ_Approve, Boolean.valueOf(ZZ_Approve));
	}

	/** Get Approve.
	@return Approve	  */
	public boolean isZZ_Approve()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Approve);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Branch Name.
	@param ZZ_Branch_Name Branch Name
	 */
	public void setZZ_Branch_Name (String ZZ_Branch_Name)
	{
		set_Value (COLUMNNAME_ZZ_Branch_Name, ZZ_Branch_Name);
	}

	/** Get Branch Name.
	@return Branch Name	  */
	public String getZZ_Branch_Name()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Branch_Name);
	}

	/** Set Branch Number.
	@param ZZ_Branch_Number Branch Number
	 */
	public void setZZ_Branch_Number (String ZZ_Branch_Number)
	{
		set_Value (COLUMNNAME_ZZ_Branch_Number, ZZ_Branch_Number);
	}

	/** Get Branch Number.
	@return Branch Number	  */
	public String getZZ_Branch_Number()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Branch_Number);
	}





}
