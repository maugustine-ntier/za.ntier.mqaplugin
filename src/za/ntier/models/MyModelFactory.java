package za.ntier.models;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.I_AD_WF_Activity;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_ZZ_Petty_Cash_Advance_Hdr;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.osgi.service.component.annotations.Component;

import za.ntier.wf.MWFActivity;

@Component(

		 property= {"service.ranking:Integer=2"},
		 service = org.adempiere.base.IModelFactory.class
		 )
public class MyModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {	
		if (tableName.equals(I_ZZ_Petty_Cash_Advance_Hdr.Table_Name)) {
			return MZZPettyCashAdvanceHdr.class;
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Application.Table_Name)) {
			return MZZPettyCashApplication.class;
		}
		if (tableName.equals(I_C_InvoiceBatch.Table_Name)) {
			return MInvoiceBatch_New.class;
		}
		if (tableName.equals(I_C_BP_BankAccount.Table_Name)) {
			return MBPBankAccount_New.class;
		}
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		if (tableName.equals(I_ZZ_Petty_Cash_Advance_Hdr.Table_Name)) {
			return new MZZPettyCashAdvanceHdr(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Application.Table_Name)) {
			return new MZZPettyCashApplication(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_C_InvoiceBatch.Table_Name)) {
			return new MInvoiceBatch_New(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_C_BP_BankAccount.Table_Name)) {
			return new MBPBankAccount_New(Env.getCtx(),Record_ID,trxName);
		}
		
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		if (tableName.equals(I_ZZ_Petty_Cash_Advance_Hdr.Table_Name)) {
			return new MZZPettyCashAdvanceHdr(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Application.Table_Name)) {
			return new MZZPettyCashApplication(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_C_InvoiceBatch.Table_Name)) {
			return new MInvoiceBatch_New(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_C_BP_BankAccount.Table_Name)) {
			return new MBPBankAccount_New(Env.getCtx(),rs,trxName);
		}
			
		return null;
	}

}
