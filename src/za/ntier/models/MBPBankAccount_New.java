package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MBPBankAccount;
import org.compiere.model.MBPartner;
import org.compiere.model.MLocation;
import org.compiere.model.MUser;

public class MBPBankAccount_New extends MBPBankAccount {

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
	protected boolean afterSave(boolean newRecord, boolean success) {
		boolean done =  super.afterSave(newRecord, success);
		if (!done) {
			return false;
		}
		if (getC_BPartner_ID() != 0 && newRecord) {
			CopyRecordToOtherClients copyRecordToOtherClients = new CopyRecordToOtherClients(getCtx(),get_TrxName(),getAD_Client_ID(),getC_BP_BankAccount_ID(),get_TableName());
		}
		return true;
	}
	

}
