package za.ntier.models;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

public class MZZPettyCashReconHdr extends X_ZZ_Petty_Cash_Recon_Hdr {

	private static final long serialVersionUID = 1L;

	public MZZPettyCashReconHdr(Properties ctx, int ZZ_Petty_Cash_Recon_Hdr_ID, String trxName) {
		super(ctx, ZZ_Petty_Cash_Recon_Hdr_ID, trxName);
	}

	public MZZPettyCashReconHdr(Properties ctx, int ZZ_Petty_Cash_Recon_Hdr_ID, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Petty_Cash_Recon_Hdr_ID, trxName, virtualColumns);
	}

	public MZZPettyCashReconHdr(Properties ctx, String ZZ_Petty_Cash_Recon_Hdr_UU, String trxName) {
		super(ctx, ZZ_Petty_Cash_Recon_Hdr_UU, trxName);
	}

	public MZZPettyCashReconHdr(Properties ctx, String ZZ_Petty_Cash_Recon_Hdr_UU, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Petty_Cash_Recon_Hdr_UU, trxName, virtualColumns);
	}

	public MZZPettyCashReconHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		BigDecimal claimTotal = (getZZ_Claim_Total() == null) ? BigDecimal.ZERO: getZZ_Claim_Total();
		BigDecimal advanceTotal = (getZZ_Advance_Total() == null) ? BigDecimal.ZERO : getZZ_Advance_Total();
		BigDecimal floatAmt = (getZZ_Float_Amt() == null) ? BigDecimal.ZERO : getZZ_Float_Amt();
		BigDecimal calcCashOnHand = floatAmt.subtract(claimTotal).subtract(advanceTotal);
		setZZ_Calculated_COH(calcCashOnHand);
		return super.beforeSave(newRecord);
	}

	public void updateTotals() throws Exception {
		String SQL = "Select sum(cl.amount) from ZZ_Petty_Cash_Claim_Line cl where cl.ZZ_Petty_Cash_Recon_Hdr_ID = ?";
		BigDecimal claimTotal = DB.getSQLValueBD(get_TrxName(), SQL, getZZ_Petty_Cash_Recon_Hdr_ID());
		String SQL2 = "Select sum(ca.amount) from ZZ_Petty_Cash_Recon_Advance ca where ca.ZZ_Petty_Cash_Recon_Hdr_ID = ?";
		BigDecimal advanceTotal = DB.getSQLValueBD(get_TrxName(), SQL2, getZZ_Petty_Cash_Recon_Hdr_ID());
		setZZ_Claim_Total(claimTotal);
		setZZ_Advance_Total(advanceTotal);		
	}

}
