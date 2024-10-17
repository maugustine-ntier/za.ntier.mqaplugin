package za.ntier.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.compiere.model.MTable;
import org.compiere.model.MUser;
import org.compiere.model.SystemIDs;
import org.compiere.model.X_C_BPartner;
import org.compiere.util.Env;
import org.idempiere.cache.ImmutableIntPOCache;

import za.co.ntier.twilio.models.X_TW_Message;
import za.co.ntier.utils.SendMessage;

public class MUser_New extends MUser implements I_AD_User {

	private static final long serialVersionUID = -4202021985605290293L;
	/**	Cache					*/
	static private ImmutableIntPOCache<Integer,MUser_New> s_cache = new ImmutableIntPOCache<Integer,MUser_New>(I_AD_User.Table_Name, 30, 60);

	public MUser_New(Properties ctx, String AD_User_UU, String trxName) {
		super(ctx, AD_User_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUser_New(Properties ctx, int AD_User_ID, String trxName) {
		super(ctx, AD_User_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUser_New(X_C_BPartner partner) {
		super(partner);
		// TODO Auto-generated constructor stub
	}

	public MUser_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUser_New(MUser copy) {
		super(copy);
		// TODO Auto-generated constructor stub
	}

	public MUser_New(Properties ctx, MUser copy) {
		super(ctx, copy);
		// TODO Auto-generated constructor stub
	}

	public MUser_New(Properties ctx, MUser copy, String trxName) {
		super(ctx, copy, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MUser_New get (Properties ctx, int AD_User_ID)
	{
		Integer key = Integer.valueOf(AD_User_ID);
		MUser_New retValue = s_cache.get(ctx, key, e -> new MUser_New(ctx, e));
		if (retValue == null)
		{
			retValue = new MUser_New (ctx, AD_User_ID, (String)null);
			if (AD_User_ID == SystemIDs.USER_SYSTEM_DEPRECATED)
			{
				String trxName = null;
				retValue.load(trxName);	//	load System Record
			}
			if (retValue.get_ID() == AD_User_ID)
			{
				s_cache.put(key, retValue, e -> new MUser_New(Env.getCtx(), e));
				return retValue;
			}
			return null;
		}
		return retValue;
	}	//	get




	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (getPhone() != null && !(getPhone().equals("")) && is_ValueChanged(I_AD_User.COLUMNNAME_Phone) && !isValidPhoneNumber(getPhone())) {
			log.saveError("Error", "Phone number must be in format +27999999999");
			return false;
		}
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		try {
			String retMess = null;
			if (getPhone() != null && !(getPhone().equals("")) && is_ValueChanged(I_AD_User.COLUMNNAME_Phone)) {
				retMess = SendMessage.sendOptInMessage(getCtx(), Env.getAD_Client_ID(getCtx()), X_TW_Message.TWILIO_MESSAGE_TYPE_Whatsapp, getPhone());
			}
		} catch (Exception e) {
			return false;
		}
		super.afterSave(newRecord, success);
		if (getC_BPartner_ID() != 0 && newRecord) {
			CopyRecordToOtherClients copyRecordToOtherClients = new CopyRecordToOtherClients(getCtx(),get_TrxName(),getAD_Client_ID(),getAD_User_ID(),get_TableName());
		}
		return true;
	}

	public boolean isValidPhoneNumber(String phoneNumber) {
		String regex = "^\\+[1-9]\\d{1,14}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}



	@Override
	public void setName2(String Name2) {
		set_Value (COLUMNNAME_Name2, Name2);

	}

	@Override
	public String getName2() {
		return (String)get_Value(COLUMNNAME_Name2);
	}

	@Override
	public org.compiere.model.I_R_Category getR_Category() throws RuntimeException
	{
		return (org.compiere.model.I_R_Category)MTable.get(getCtx(), org.compiere.model.I_R_Category.Table_ID)
			.getPO(getR_Category_ID(), get_TrxName());
	}

	/** Set Category.
		@param R_Category_ID Request Category
	*/
	@Override
	public void setR_Category_ID (int R_Category_ID)
	{
		if (R_Category_ID < 1) {
			set_ValueNoCheck (COLUMNNAME_R_Category_ID, null);
		} else {
			set_ValueNoCheck (COLUMNNAME_R_Category_ID, Integer.valueOf(R_Category_ID));
		}
	}

	/** Get Category.
		@return Request Category
	  */
	@Override
	public int getR_Category_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Category_ID);
		if (ii == null) {
			return 0;
		}
		return ii.intValue();
	}
	@Override
	public org.compiere.model.I_R_Group getR_Group() throws RuntimeException
	{
		return (org.compiere.model.I_R_Group)MTable.get(getCtx(), org.compiere.model.I_R_Group.Table_ID)
			.getPO(getR_Group_ID(), get_TrxName());
	}

	/** Set Group.
		@param R_Group_ID Request Group
	*/
	@Override
	public void setR_Group_ID (int R_Group_ID)
	{
		if (R_Group_ID < 1) {
			set_ValueNoCheck (COLUMNNAME_R_Group_ID, null);
		} else {
			set_ValueNoCheck (COLUMNNAME_R_Group_ID, Integer.valueOf(R_Group_ID));
		}
	}

	/** Get Group.
		@return Request Group
	  */
	@Override
	public int getR_Group_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Group_ID);
		if (ii == null) {
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_RequestType getR_RequestType() throws RuntimeException
	{
		return (org.compiere.model.I_R_RequestType)MTable.get(getCtx(), org.compiere.model.I_R_RequestType.Table_ID)
			.getPO(getR_RequestType_ID(), get_TrxName());
	}

	/** Set Request Type.
		@param R_RequestType_ID Type of request (e.g. Inquiry, Complaint, ..)
	*/
	@Override
	public void setR_RequestType_ID (int R_RequestType_ID)
	{
		if (R_RequestType_ID < 1) {
			set_Value (COLUMNNAME_R_RequestType_ID, null);
		} else {
			set_Value (COLUMNNAME_R_RequestType_ID, Integer.valueOf(R_RequestType_ID));
		}
	}

	/** Get Request Type.
		@return Type of request (e.g. Inquiry, Complaint, ..)
	  */
	@Override
	public int getR_RequestType_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestType_ID);
		if (ii == null) {
			return 0;
		}
		return ii.intValue();
	}


	@Override
	public void setZZ_Company (String ZZ_Company)
	{

		set_Value (COLUMNNAME_ZZ_Company, ZZ_Company);
	}

	/** Get Company.
		@return Company	  */
	@Override
	public String getZZ_Company()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Company);
	}

	@Override
	public void setOpt_In_Date(Timestamp Opt_In_Date) {
		set_Value (COLUMNNAME_Opt_In_Date, Opt_In_Date);
		
	}

	@Override
	public Timestamp getOpt_In_Date() {
		return (Timestamp)get_Value(COLUMNNAME_Opt_In_Date);
	}

	@Override
	public void setOpt_Out_Date(Timestamp Opt_Out_Date) {
		set_Value (COLUMNNAME_Opt_Out_Date, Opt_Out_Date);
		
	}

	@Override
	public void setIsEmployee(boolean IsEmployee) {
		set_Value (COLUMNNAME_IsEmployee, IsEmployee);
		
	}

	@Override
	public boolean isEmployee() {
		Object oo = get_Value(COLUMNNAME_IsEmployee);
		if (oo != null)
		{
			 if (oo instanceof Boolean) {
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public void setIsSalesRep(boolean IsSalesRep) {
		set_Value (COLUMNNAME_IsSalesRep, IsSalesRep);
		
	}

	@Override
	public boolean isSalesRep() {
		Object oo = get_Value(COLUMNNAME_IsSalesRep);
		if (oo != null)
		{
			 if (oo instanceof Boolean) {
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public Timestamp getOpt_Out_Date() {
		return (Timestamp)get_Value(COLUMNNAME_Opt_Out_Date);
	}

}
