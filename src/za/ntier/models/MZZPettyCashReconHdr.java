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
	
	public void updateTotals() throws Exception {
		String SQL = "Select sum(ch.amount) from ZZ_Petty_Cash_Claim_Hdr ch where ch.ZZ_Petty_Cash_Recon_Hdr_ID = ?";
		BigDecimal claimTotal = DB.getSQLValueBD(get_TrxName(), SQL, getZZ_Petty_Cash_Recon_Hdr_ID());
		claimTotal = (claimTotal == null) ? BigDecimal.ZERO : claimTotal;
		String SQL2 = "Select sum(ca.amount) from ZZ_Petty_Cash_Advance_Hdr ca where ca.ZZ_Petty_Cash_Recon_Hdr_ID = ?";
		BigDecimal advanceTotal = DB.getSQLValueBD(get_TrxName(), SQL2, getZZ_Petty_Cash_Recon_Hdr_ID());
		advanceTotal = (advanceTotal == null) ? BigDecimal.ZERO : advanceTotal;
		BigDecimal floatAmt = (getZZ_Float_Amt() == null) ? BigDecimal.ZERO : getZZ_Float_Amt();
		BigDecimal calcCashOnHand = floatAmt.subtract(claimTotal).subtract(advanceTotal);
		setZZ_Claim_Total(claimTotal);
		setZZ_Advance_Total(advanceTotal);
		setZZ_Calculated_COH(calcCashOnHand);
		saveEx();
	}

}
