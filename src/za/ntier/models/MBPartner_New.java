package za.ntier.models;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;
import org.compiere.model.Query;
import org.compiere.model.X_I_BPartner;
import org.compiere.util.Env;

public class MBPartner_New extends MBPartner implements za.ntier.models.I_C_BPartner {

	private static final long serialVersionUID = 4154740391812230437L;





	public MBPartner_New(Properties ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, String C_BPartner_UU, String trxName) {
		super(ctx, C_BPartner_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, int C_BPartner_ID, String trxName) {
		super(ctx, C_BPartner_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(X_I_BPartner impBP) {
		super(impBP);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(MBPartner copy) {
		super(copy);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, MBPartner copy) {
		super(ctx, copy);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, MBPartner copy, String trxName) {
		super(ctx, copy, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, int C_BPartner_ID, String trxName, String... virtualColumns) {
		super(ctx, C_BPartner_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public static MBPartner getUpper (Properties ctx, String Value, String trxName)
	{
		Value = Value.toUpperCase();
		if (Value == null || Value.length() == 0) {
			return null;
		}
		final String whereClause = "Upper(Value)=? AND AD_Client_ID=?";
		MBPartner retValue = new Query(ctx, I_C_BPartner.Table_Name, whereClause, trxName)
				.setParameters(Value,Env.getAD_Client_ID(ctx))
				.firstOnly();
		return retValue;
	}

	public static MBPartner_New get (Properties ctx, String Value) {
		return get(ctx,Value,null);		
	}

	/**
	 * 	Get BPartner with Value in a transaction
	 *	@param ctx context 
	 *	@param Value value
	 * 	@param trxName transaction
	 *	@return BPartner or null
	 */
	public static MBPartner_New get (Properties ctx, String Value, String trxName)
	{
		if (Value == null || Value.length() == 0)
			return null;
		final String whereClause = "Value=? AND AD_Client_ID=?";
		MBPartner_New retValue = new Query(ctx, I_C_BPartner.Table_Name, whereClause, trxName)
				.setParameters(Value,Env.getAD_Client_ID(ctx))
				.firstOnly();
		return retValue;
	}	//	get

	@Override
	public void setZZ_Business_Reg_No(String ZZ_Business_Reg_No) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getZZ_Business_Reg_No() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Is_Employer(boolean ZZ_Is_Employer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isZZ_Is_Employer() {
		// TODO Auto-generated method stub
		return false;
	}

	/** Set MQA Sector.
	@param ZZ_Is_MQA_Sector MQA Sector
	 */
	public void setZZ_Is_MQA_Sector (boolean ZZ_Is_MQA_Sector)
	{
		set_Value (COLUMNNAME_ZZ_Is_MQA_Sector, Boolean.valueOf(ZZ_Is_MQA_Sector));
	}

	/** Get MQA Sector.
	@return MQA Sector	  */
	public boolean isZZ_Is_MQA_Sector()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Is_MQA_Sector);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public void setZZ_Municipality_District(String ZZ_Municipality_District) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getZZ_Municipality_District() {
		// TODO Auto-generated method stub
		return null;
	}

	/** Set Number Of Employees.
	@param ZZ_Number_Of_Employees Number Of Employees
	 */
	public void setZZ_Number_Of_Employees (BigDecimal ZZ_Number_Of_Employees)
	{
		set_Value (COLUMNNAME_ZZ_Number_Of_Employees, ZZ_Number_Of_Employees);
	}

	/** Get Number Of Employees.
	@return Number Of Employees	  */
	public BigDecimal getZZ_Number_Of_Employees()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ZZ_Number_Of_Employees);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}


	@Override
	public void setZZ_SDL_No(String ZZ_SDL_No) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getZZ_SDL_No() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @Override protected boolean afterSave(boolean newRecord, boolean success) {
	 * boolean result = super.afterSave(newRecord, success); if (!result) { return
	 * false; } if (getAD_Client_ID() != 1000018) { return true; } if
	 * (isZZ_Copy_To_Tenants()) { CopyRecordToOtherClients copyRecordToOtherClients
	 * = new CopyRecordToOtherClients(getCtx(),get_TrxName(),getAD_Client_ID(),
	 * getC_BPartner_ID(),get_TableName());
	 * 
	 * if
	 * (is_ValueChanged(za.ntier.models.I_C_BPartner.COLUMNNAME_ZZ_Copy_To_Tenants))
	 * { copyLinkedTableRecords(MUser.Table_Name);
	 * copyLinkedTableRecords(MBPBankAccount_New.Table_Name); List
	 * <MBPartnerLocation> mBPartnerLocations =
	 * MLocation_New.getBPLocation_IDs(getCtx(), getC_BPartner_ID(), get_TrxName());
	 * for (MBPartnerLocation mBPartnerLocation: mBPartnerLocations) {
	 * 
	 * copyRecordToOtherClients = new
	 * CopyRecordToOtherClients(getCtx(),get_TrxName(),getAD_Client_ID(),
	 * mBPartnerLocation.getC_Location_ID(),MLocation_New.Table_Name); }
	 * copyLinkedTableRecords(MBPartnerLocation_New.Table_Name); } } return true; }
	 */






}
