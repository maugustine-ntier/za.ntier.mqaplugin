package za.ntier.models;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.osgi.service.component.annotations.Component;

@Component(

		 property= {"service.ranking:Integer=2"},
		 service = org.adempiere.base.IModelFactory.class
		 )
public class MyModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		if (tableName.equals(I_C_BP_BankAccount.Table_Name)) {
			return MBPBankAccount_New.class;
		}
		if (tableName.equals(I_C_BPartner_Location.Table_Name)) {
			return MBPartnerLocation_New.class;
		}
		if (tableName.equals(I_C_Location.Table_Name)) {
			return MLocation_New.class;
		}
		if (tableName.equals(I_C_BPartner.Table_Name)) {
			return MBPartner_New.class;
		}
		if (tableName.equals(I_AD_User.Table_Name)) {
			return MUser_New.class;
		}
		if (tableName.equals(I_C_Invoice.Table_Name)) {
			return MInvoice_New.class;
		}
		if (tableName.equals(X_M_InOut.Table_Name)) {
			return MInOut_New.class;
		}
		if (tableName.equals(X_ZZ_Driver.Table_Name)) {
			return MDriver.class;
		}
		if (tableName.equals(X_ZZ_Truck_List.Table_Name)) {
			return MTruckList.class;
		}
		if (tableName.equals(X_ZZ_Transporters.Table_Name)) {
			return MTransporters.class;
		}
		if (tableName.equals(X_ZZ_Truck.Table_Name)) {
			return MTruck.class;
		}
			
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		if (tableName.equals(I_C_BP_BankAccount.Table_Name)) {
			return new MBPBankAccount_New(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_C_BPartner_Location.Table_Name)) {
			return new MBPartnerLocation_New(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_C_Location.Table_Name)) {
			return new MLocation_New(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_C_BPartner.Table_Name)) {
			return new MBPartner_New(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_AD_User.Table_Name)) {
			return new MUser_New(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_C_Invoice.Table_Name)) {
			return new MInvoice_New(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(X_M_InOut.Table_Name)) {
			return new MInOut_New(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(X_ZZ_Driver.Table_Name)) {
			return new MDriver(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(X_ZZ_Truck_List.Table_Name)) {
			return new MTruckList(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(X_ZZ_Transporters.Table_Name)) {
			return new MTransporters(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(X_ZZ_Truck.Table_Name)) {
			return new MTruck(Env.getCtx(),Record_ID,trxName);
		}
		
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		if (tableName.equals(I_C_BP_BankAccount.Table_Name)) {
			return new MBPBankAccount_New(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_C_BPartner_Location.Table_Name)) {
			return new MBPartnerLocation_New(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_C_Location.Table_Name)) {
			return new MLocation_New(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_C_BPartner.Table_Name)) {
			return new MBPartner_New(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_AD_User.Table_Name)) {
			return new MUser_New(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_C_Invoice.Table_Name)) {
			return new MInvoice_New(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(X_M_InOut.Table_Name)) {
			return new MInOut_New(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(X_ZZ_Driver.Table_Name)) {
			return new MDriver(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(X_ZZ_Truck_List.Table_Name)) {
			return new MTruckList(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(X_ZZ_Transporters.Table_Name)) {
			return new MTransporters(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(X_ZZ_Truck.Table_Name)) {
			return new MTruck(Env.getCtx(),rs,trxName);
		}
			
		return null;
	}

}
