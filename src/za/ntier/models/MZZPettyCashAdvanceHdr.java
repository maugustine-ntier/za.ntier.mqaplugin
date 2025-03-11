package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

public class MZZPettyCashAdvanceHdr extends X_ZZ_Petty_Cash_Advance_Hdr {

	private static final long serialVersionUID = 1L;

	public MZZPettyCashAdvanceHdr(Properties ctx, int ZZ_Petty_Cash_Advance_Hdr_ID, String trxName) {
		super(ctx, ZZ_Petty_Cash_Advance_Hdr_ID, trxName);
	}



	public MZZPettyCashAdvanceHdr(Properties ctx, int ZZ_Petty_Cash_Advance_Hdr_ID, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Petty_Cash_Advance_Hdr_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}



	public MZZPettyCashAdvanceHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}



	public MZZPettyCashAdvanceHdr(Properties ctx, String ZZ_Petty_Cash_Advance_Hdr_UU, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Petty_Cash_Advance_Hdr_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}



	public MZZPettyCashAdvanceHdr(Properties ctx, String ZZ_Petty_Cash_Advance_Hdr_UU, String trxName) {
		super(ctx, ZZ_Petty_Cash_Advance_Hdr_UU, trxName);
		// TODO Auto-generated constructor stub
	}



	@Override
	protected boolean beforeDelete() {
		if (!getZZ_DocStatus().equals(ZZ_DOCSTATUS_Draft)) {			
			log.saveError("Error", "Only Drafted Cash Advance Requests can be deleted");
			return false;
		}
		return super.beforeDelete();
	}






}
