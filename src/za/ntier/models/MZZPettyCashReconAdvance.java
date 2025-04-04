package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

public class MZZPettyCashReconAdvance extends X_ZZ_Petty_Cash_Recon_Advance {

	private static final long serialVersionUID = 1L;

	public MZZPettyCashReconAdvance(Properties ctx, int ZZ_Petty_Cash_Recon_Advance_ID, String trxName) {
		super(ctx, ZZ_Petty_Cash_Recon_Advance_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZPettyCashReconAdvance(Properties ctx, int ZZ_Petty_Cash_Recon_Advance_ID, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Petty_Cash_Recon_Advance_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZPettyCashReconAdvance(Properties ctx, String ZZ_Petty_Cash_Recon_Advance_UU, String trxName) {
		super(ctx, ZZ_Petty_Cash_Recon_Advance_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZPettyCashReconAdvance(Properties ctx, String ZZ_Petty_Cash_Recon_Advance_UU, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Petty_Cash_Recon_Advance_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZPettyCashReconAdvance(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterDelete(boolean success) {
		try {
			MZZPettyCashReconHdr mZZPettyCashReconHdr = new MZZPettyCashReconHdr(getCtx(), getZZ_Petty_Cash_Recon_Hdr_ID(), get_TrxName());
			mZZPettyCashReconHdr.updateTotals();
			mZZPettyCashReconHdr.saveEx();
		} catch (Exception e) {
			return false;
		}
		return super.afterDelete(success);
	}
	
	@Override
	protected boolean beforeDelete() {
		return super.beforeDelete();
	}

	
}
