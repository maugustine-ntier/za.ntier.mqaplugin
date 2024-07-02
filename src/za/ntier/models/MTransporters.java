package za.ntier.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Msg;

public class MTransporters extends X_ZZ_Transporters implements I_ZZ_Transporters {

	private static final long serialVersionUID = -9220800794049773657L;

	public MTransporters(Properties ctx, int ZZ_Transporters_ID, String trxName, String... virtualColumns) {
		super(ctx, ZZ_Transporters_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MTransporters(Properties ctx, int ZZ_Transporters_ID, String trxName) {
		super(ctx, ZZ_Transporters_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MTransporters(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MTransporters(Properties ctx, String ZZ_Transporters_UU, String trxName, String... virtualColumns) {
		super(ctx, ZZ_Transporters_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MTransporters(Properties ctx, String ZZ_Transporters_UU, String trxName) {
		super(ctx, ZZ_Transporters_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (getC_BPartner_ID() > 0 && getM_Product_ID() > 0 && getZZ_Loading_Date() != null) {
			String SQL = "Select count(*) from ZZ_Transporters tr where "
			+ " tr.C_BPartner_ID" + " = ?" 
			+ " and tr.M_Product_ID" + " = ?" 
			+ " and date(tr.ZZ_Loading_Date)" + " = date(?)" 
			+ " and (" + getZZ_Transporters_ID() + " <= 0 or tr.ZZ_Transporters_ID <> " + getZZ_Transporters_ID() + ")"
			+ " and tr.m_Shipper_ID = ?";
			if (DB.getSQLValueEx(get_TrxName(), SQL, getC_BPartner_ID(),getM_Product_ID(),getZZ_Loading_Date(),getM_Shipper_ID()) > 0) {
				log.saveError("Error", Msg.getMsg(getCtx(), "TransportListAlreadyExistsForThatDate")); 
				return false;
			}
		}
		return super.beforeSave(newRecord);
	}
	
	public static int get(Properties ctx,int c_BPartner_ID,int m_Product_ID,Timestamp LoadingDate,int MShipperID,String trxName) {
		if (c_BPartner_ID > 0 && m_Product_ID > 0 && LoadingDate != null) {
			String SQL = "Select ZZ_Transporters_ID from ZZ_Transporters tr where "
			+ " tr.C_BPartner_ID" + " = ?" 
			+ " and tr.M_Product_ID" + " = ?" 
			+ " and date(tr.ZZ_Loading_Date)" + " = date(?)" 
			+ " and tr.m_Shipper_ID = ?";
			return DB.getSQLValueEx(trxName, SQL, c_BPartner_ID,m_Product_ID,LoadingDate,MShipperID);
		}
		return -1;
	}

}
