package za.ntier.models;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.osgi.service.component.annotations.Component;

import za.co.ntier.api.model.I_ZZ_Program_Master_Data;
import za.co.ntier.wf.model.MZZWFHeader;
import za.co.ntier.wf.model.MZZWFLineRole;
import za.co.ntier.wf.model.MZZWFLines;

@Component(

		 property= {"service.ranking:Integer=2"},
		 service = org.adempiere.base.IModelFactory.class
		 )
public class MyModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		if (tableName.equals(I_ZZ_WF_Line_Role.Table_Name)) {
			return MZZWFLineRole.class;
		}
		if (tableName.equals(I_ZZ_WF_Lines.Table_Name)) {
			return MZZWFLines.class;
		}
		if (tableName.equals(I_ZZ_WF_Header.Table_Name)) {
			return MZZWFHeader.class;
		}
		if (tableName.equals(I_ZZ_Monthly_Levy_Files.Table_Name)) {
			return X_ZZ_Monthly_Levy_Files.class;
		}
		if (tableName.equals(I_ZZ_Open_Application.Table_Name)) {
			return MZZOpenApplication.class;
		}
		if (tableName.equals(I_ZZ_WSP_ATR_Approvals.Table_Name)) {
			return X_ZZ_WSP_ATR_Approvals.class;
		}
		if (tableName.equals(I_ZZ_Program_Master_Data.Table_Name)) {
			return MZZProgramMasterData.class;
		}
		if (tableName.equals(I_ZZ_System_Access_Application.Table_Name)) {
			return MZZSystemAccessApplication.class;
		}
		if (tableName.equals(I_M_Inventory.Table_Name)) {
			return MInventory_New.class;
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Recon_Advance.Table_Name)) {
			return MZZPettyCashReconAdvance.class;
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Recon_Claim.Table_Name)) {
			return MZZPettyCashReconClaim.class;
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Recon_Hdr.Table_Name)) {
			return MZZPettyCashReconHdr.class;
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Claim_Line.Table_Name)) {
			return MZZPettyCashClaimLine.class;
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Claim_Hdr.Table_Name)) {
			return MZZPettyCashClaimHdr.class;
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Advance_Line.Table_Name)) {
			return MZZPettyCashAdvanceLine.class;
		}
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
		if (tableName.equals(I_ZZ_WF_Line_Role.Table_Name)) {
			return new MZZWFLineRole(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_WF_Lines.Table_Name)) {
			return new MZZWFLines(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_WF_Header.Table_Name)) {
			return new MZZWFHeader(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_Monthly_Levy_Files.Table_Name)) {
			return new X_ZZ_Monthly_Levy_Files(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_Open_Application.Table_Name)) {
			return new MZZOpenApplication(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_WSP_ATR_Approvals.Table_Name)) {
			return new X_ZZ_WSP_ATR_Approvals(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_Program_Master_Data.Table_Name)) {
			return new MZZProgramMasterData(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_System_Access_Application.Table_Name)) {
			return new MZZSystemAccessApplication(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_M_Inventory.Table_Name)) {
			return new MInventory_New(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Recon_Advance.Table_Name)) {
			return new MZZPettyCashReconAdvance(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Recon_Claim.Table_Name)) {
			return new MZZPettyCashReconClaim(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Recon_Hdr.Table_Name)) {
			return new MZZPettyCashReconHdr(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Claim_Line.Table_Name)) {
			return new MZZPettyCashClaimLine(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Claim_Hdr.Table_Name)) {
			return new MZZPettyCashClaimHdr(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Advance_Line.Table_Name)) {
			return new MZZPettyCashAdvanceLine(Env.getCtx(),Record_ID,trxName);
		}
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
		if (tableName.equals(I_ZZ_WF_Line_Role.Table_Name)) {
			return new MZZWFLineRole(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_WF_Lines.Table_Name)) {
			return new MZZWFLines(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_WF_Header.Table_Name)) {
			return new MZZWFHeader(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_Monthly_Levy_Files.Table_Name)) {
			return new X_ZZ_Monthly_Levy_Files(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_Open_Application.Table_Name)) {
			return new MZZOpenApplication(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_WSP_ATR_Approvals.Table_Name)) {
			return new X_ZZ_WSP_ATR_Approvals(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_Program_Master_Data.Table_Name)) {
			return new MZZProgramMasterData(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_System_Access_Application.Table_Name)) {
			return new MZZSystemAccessApplication(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_M_Inventory.Table_Name)) {
			return new MInventory_New(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Recon_Advance.Table_Name)) {
			return new MZZPettyCashReconAdvance(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Recon_Claim.Table_Name)) {
			return new MZZPettyCashReconClaim(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Recon_Hdr.Table_Name)) {
			return new MZZPettyCashReconHdr(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Claim_Line.Table_Name)) {
			return new MZZPettyCashClaimLine(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Claim_Hdr.Table_Name)) {
			return new MZZPettyCashClaimHdr(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(I_ZZ_Petty_Cash_Advance_Line.Table_Name)) {
			return new MZZPettyCashAdvanceLine(Env.getCtx(),rs,trxName);
		}
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
