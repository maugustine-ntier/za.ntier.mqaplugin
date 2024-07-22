package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

public class MZZWBTransaction extends X_ZZ_WB_Transaction  {

	private static final long serialVersionUID = 1L;

	public MZZWBTransaction(Properties ctx, int ZZ_WB_Transaction_ID, String trxName) {
		super(ctx, ZZ_WB_Transaction_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZWBTransaction(Properties ctx, int ZZ_WB_Transaction_ID, String trxName, String... virtualColumns) {
		super(ctx, ZZ_WB_Transaction_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZWBTransaction(Properties ctx, String ZZ_WB_Transaction_UU, String trxName) {
		super(ctx, ZZ_WB_Transaction_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZWBTransaction(Properties ctx, String ZZ_WB_Transaction_UU, String trxName, String... virtualColumns) {
		super(ctx, ZZ_WB_Transaction_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZWBTransaction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static int getCount(int wb_TransactionID,String trxName) {
		int cnt = 0;
		String SQL = "select count(*) from ZZ_WB_Transaction m where m.WB_TransactionID = ?";
		cnt = DB.getSQLValue(trxName, SQL,wb_TransactionID );
		return cnt;
		
	}

}
