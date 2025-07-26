package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

public class MZZProgramGenRules extends X_ZZ_Program_Gen_Rules {

	private static final long serialVersionUID = 1L;

	public MZZProgramGenRules(Properties ctx, int ZZ_Program_Gen_Rules_ID, String trxName) {
		super(ctx, ZZ_Program_Gen_Rules_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramGenRules(Properties ctx, int ZZ_Program_Gen_Rules_ID, String trxName, String... virtualColumns) {
		super(ctx, ZZ_Program_Gen_Rules_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramGenRules(Properties ctx, String ZZ_Program_Gen_Rules_UU, String trxName) {
		super(ctx, ZZ_Program_Gen_Rules_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramGenRules(Properties ctx, String ZZ_Program_Gen_Rules_UU, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Program_Gen_Rules_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramGenRules(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
