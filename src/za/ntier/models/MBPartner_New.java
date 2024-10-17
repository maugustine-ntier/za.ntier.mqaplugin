package za.ntier.models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;
import org.compiere.model.MPInstance;
import org.compiere.model.Query;
import org.compiere.model.X_I_BPartner;
import org.compiere.util.Env;

public class MBPartner_New extends MBPartner {

	private static final long serialVersionUID = 4154740391812230437L;

	final private static String insertConversionId = "INSERT INTO T_MoveClient (AD_PInstance_ID, TableName, Source_Key, Target_Key,Target_AD_Client_ID) VALUES (?, ?, ?, ?, ?)"; // Martin added target_ad_client_id
	final private static String queryT_MoveClient = "SELECT Target_Key FROM T_MoveClient WHERE AD_PInstance_ID=? AND TableName=? AND Source_Key=? AND Target_AD_Client_ID=?";

	//private Connection externalConn;
	private StringBuffer p_excludeTablesWhere = new StringBuffer();
	private StringBuffer p_whereClient = new StringBuffer();
	private List<String> p_errorList = new ArrayList<String>();
	private List<String> p_tablesVerifiedList = new ArrayList<String>();
	private List<String> p_tablesToPreserveIDsList = new ArrayList<String>();
	private List<String> p_tablesToExcludeList = new ArrayList<String>();
	private List<String> p_columnsVerifiedList = new ArrayList<String>();
	private List<String> p_idSystemConversionList = new ArrayList<String>(); // can consume lot of memory but it helps for performance
	private boolean p_isPreserveAll = false;
	private MPInstance mPInstance = null;
	private String p_ClientsToInclude = null;
	private boolean p_IsCopyClient = false;
	/** New client name when copying from template */
	private String p_ClientName;
	/** New client value when copying from template */
	private String p_ClientValue;
	private List tableList = new ArrayList<String>(); 



	public MBPartner_New(Properties ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, String C_BPartner_UU, String trxName) {
		super(ctx, C_BPartner_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, int C_BPartner_ID, String trxName) {
		super(ctx, C_BPartner_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(X_I_BPartner impBP) {
		super(impBP);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(MBPartner copy) {
		super(copy);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, MBPartner copy) {
		super(ctx, copy);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, MBPartner copy, String trxName) {
		super(ctx, copy, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, int C_BPartner_ID, String trxName, String... virtualColumns) {
		super(ctx, C_BPartner_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public static MBPartner getUpper (Properties ctx, String Value, String trxName)
	{
		Value = Value.toUpperCase();
		if (Value == null || Value.length() == 0) {
			return null;
		}
		final String whereClause = "Upper(Value)=? AND AD_Client_ID=?";
		MBPartner retValue = new Query(ctx, I_C_BPartner.Table_Name, whereClause, trxName)
				.setParameters(Value,Env.getAD_Client_ID(ctx))
				.firstOnly();
		return retValue;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		boolean result = super.afterSave(newRecord, success);
		if (!result) {
			return false;
		}
		if (getAD_Client_ID() != 1000018) {
			return true;
		}
		CopyRecordToOtherClients copyRecordToOtherClients = new CopyRecordToOtherClients(getCtx(),get_TrxName(),getAD_Client_ID(),getC_BPartner_ID());
		return true;
	}	

	

}
