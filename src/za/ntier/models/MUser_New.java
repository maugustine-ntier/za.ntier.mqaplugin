package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.compiere.model.I_R_Category;
import org.compiere.model.I_R_Group;
import org.compiere.model.I_R_RequestType;
import org.compiere.model.MUser;
import org.compiere.model.X_C_BPartner;
import org.compiere.util.Env;

import za.co.ntier.twilio.models.X_TW_Message;
import za.co.ntier.utils.SendMessage;

public class MUser_New extends MUser implements I_AD_User {

	private static final long serialVersionUID = -4202021985605290293L;

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
		return super.afterSave(newRecord, success);
	}

	public boolean isValidPhoneNumber(String phoneNumber) {
		String regex = "^\\+[1-9]\\d{1,14}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}



	@Override
	public void setName2(String Name2) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setR_Category_ID(int R_Category_ID) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getR_Category_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_R_Category getR_Category() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setR_Group_ID(int R_Group_ID) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getR_Group_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_R_Group getR_Group() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setR_RequestType_ID(int R_RequestType_ID) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getR_RequestType_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_R_RequestType getR_RequestType() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Company(String ZZ_Company) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getZZ_Company() {
		// TODO Auto-generated method stub
		return null;
	}

}
