package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

public class MZZSdfOrganisation extends X_ZZSdfOrganisation {

	public MZZSdfOrganisation(Properties ctx, int ZZSdfOrganisation_ID, String trxName) {
		super(ctx, ZZSdfOrganisation_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZSdfOrganisation(Properties ctx, int ZZSdfOrganisation_ID, String trxName, String... virtualColumns) {
		super(ctx, ZZSdfOrganisation_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZSdfOrganisation(Properties ctx, String ZZSdfOrganisation_UU, String trxName) {
		super(ctx, ZZSdfOrganisation_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZSdfOrganisation(Properties ctx, String ZZSdfOrganisation_UU, String trxName, String... virtualColumns) {
		super(ctx, ZZSdfOrganisation_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZSdfOrganisation(Properties ctx, ResultSet rs, String trxName) {
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
		// TODO Auto-generated method stub
		return super.afterSave(newRecord, success);
	}

}
