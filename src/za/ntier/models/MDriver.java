package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

public class MDriver extends X_ZZ_Driver implements I_ZZ_Driver {

	public MDriver(Properties ctx, int ZZ_Driver_ID, String trxName, String... virtualColumns) {
		super(ctx, ZZ_Driver_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MDriver(Properties ctx, int ZZ_Driver_ID, String trxName) {
		super(ctx, ZZ_Driver_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MDriver(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MDriver(Properties ctx, String ZZ_Driver_UU, String trxName, String... virtualColumns) {
		super(ctx, ZZ_Driver_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MDriver(Properties ctx, String ZZ_Driver_UU, String trxName) {
		super(ctx, ZZ_Driver_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (isZZ_ID_Passport_Attached() && isZZ_License_Attached()) {
			//setZZ_Is_Valid(true);
		} else {
			//setZZ_Is_Valid(false);
		}
		return super.beforeSave(newRecord);
	}

}
