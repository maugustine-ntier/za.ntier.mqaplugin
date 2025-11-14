package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

public class MZZATRVerification extends X_ZZ_ATRVerification {

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

}
