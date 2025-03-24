package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

public class MZZPettyCashClaimLine extends X_ZZ_Petty_Cash_Claim_Line {

	public MZZPettyCashClaimLine(Properties ctx, int ZZ_Petty_Cash_Claim_Line_ID, String trxName) {
		super(ctx, ZZ_Petty_Cash_Claim_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZPettyCashClaimLine(Properties ctx, int ZZ_Petty_Cash_Claim_Line_ID, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Petty_Cash_Claim_Line_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZPettyCashClaimLine(Properties ctx, String ZZ_Petty_Cash_Claim_Line_UU, String trxName) {
		super(ctx, ZZ_Petty_Cash_Claim_Line_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZPettyCashClaimLine(Properties ctx, String ZZ_Petty_Cash_Claim_Line_UU, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Petty_Cash_Claim_Line_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZPettyCashClaimLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (success)
		{			
			StringBuilder sql = new StringBuilder("UPDATE ZZ_Petty_Cash_Claim_Hdr h ")
				.append("SET TotalAmt = NVL((SELECT SUM(Amount) FROM ZZ_Petty_Cash_Claim_Line l ")
					.append("WHERE h.ZZ_Petty_Cash_Claim_Hdr_ID=l.ZZ_Petty_Cash_Claim_Hdr_ID AND l.IsActive='Y'),0) ")
				.append("WHERE ZZ_Petty_Cash_Claim_Hdr_ID=").append(getZZ_Petty_Cash_Claim_Hdr_ID());
			DB.executeUpdate(sql.toString(), get_TrxName());
		}
		return super.afterSave(newRecord, success);
	}
	
}
