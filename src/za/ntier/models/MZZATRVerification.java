package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.PO;


public class MZZATRVerification extends X_ZZ_ATRVerification {

	private static final long serialVersionUID = 1L;

	public MZZATRVerification(Properties ctx, int ZZ_ATRVerification_ID, String trxName) {
		super(ctx, ZZ_ATRVerification_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZATRVerification(Properties ctx, int ZZ_ATRVerification_ID, String trxName, String... virtualColumns) {
		super(ctx, ZZ_ATRVerification_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZATRVerification(Properties ctx, String ZZ_ATRVerification_UU, String trxName) {
		super(ctx, ZZ_ATRVerification_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZATRVerification(Properties ctx, String ZZ_ATRVerification_UU, String trxName, String... virtualColumns) {
		super(ctx, ZZ_ATRVerification_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZATRVerification(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
	    if (!newRecord || !success)
	        return super.afterSave(newRecord, success);

	    // Load all checklist template rows
	 // Load only active template checklist rows
	    String whereClause = X_ZZ_ATR_Checklist.COLUMNNAME_IsActive + "='Y'";
	    int[] ids = PO.getAllIDs(X_ZZ_ATR_Checklist.Table_Name,  whereClause, get_TrxName()); 
	    for (int id : ids) {

	        // Load template definition
	        X_ZZ_ATR_Checklist checklistDef =
	                new X_ZZ_ATR_Checklist(getCtx(), id, get_TrxName());

	        // Create verification checklist row
	        X_ZZ_ATR_Verification_Checklist vCheck =
	                new X_ZZ_ATR_Verification_Checklist(getCtx(), 0, get_TrxName());

	        // Link to verification header
	        vCheck.setZZ_ATRVerification_ID(getZZ_ATRVerification_ID());

	        // Copy allowed fields
	        vCheck.setName(checklistDef.getName());
	        vCheck.setValue(checklistDef.getValue());

	        // Default Yes/No fields to N
	        vCheck.setZZ_Information_Completed(false);
	        vCheck.setZZ_Information_Submitted(false);

	        // Save
	        vCheck.saveEx();
	    }
		return super.afterSave(newRecord, success);
	}

}
