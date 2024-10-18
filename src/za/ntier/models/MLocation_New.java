package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MCountry;
import org.compiere.model.MLocation;
import org.compiere.model.MRegion;
import org.compiere.util.Env;
import org.idempiere.cache.ImmutableIntPOCache;

public class MLocation_New extends MLocation {
	
	private static final long serialVersionUID = 1L;
	private static ImmutableIntPOCache<Integer,MLocation_New> s_cache = new ImmutableIntPOCache<Integer,MLocation_New>(Table_Name, 100, 30);

	public MLocation_New(Properties ctx, String C_Location_UU, String trxName) {
		super(ctx, C_Location_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MLocation_New(Properties ctx, int C_Location_ID, String trxName) {
		super(ctx, C_Location_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MLocation_New(MCountry country, MRegion region) {
		super(country, region);
		// TODO Auto-generated constructor stub
	}

	public MLocation_New(Properties ctx, int C_Country_ID, int C_Region_ID, String city, String trxName) {
		super(ctx, C_Country_ID, C_Region_ID, city, trxName);
		// TODO Auto-generated constructor stub
	}

	public MLocation_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MLocation_New(MLocation copy) {
		super(copy);
		// TODO Auto-generated constructor stub
	}

	public MLocation_New(Properties ctx, MLocation copy) {
		super(ctx, copy);
		// TODO Auto-generated constructor stub
	}

	public MLocation_New(Properties ctx, MLocation copy, String trxName) {
		super(ctx, copy, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		boolean done =  super.afterSave(newRecord, success);
		if (!done) {
			return false;
		}
		if (newRecord) {
			CopyRecordToOtherClients copyRecordToOtherClients = new CopyRecordToOtherClients(getCtx(),get_TrxName(),getAD_Client_ID(),getC_Location_ID(),get_TableName());
		}
		return true;
	}
	
	/**
	 * 	Get Location from Cache (Immutable)
	 *	@param C_Location_ID id
	 *	@param trxName transaction
	 *	@return MLocation
	 */
	public static MLocation_New get (int C_Location_ID, String trxName)
	{
		return get(Env.getCtx(), C_Location_ID, trxName);
	}
	
	/**
	 * 	Get Location from Cache (immutable)
	 *  @param ctx context
	 *	@param C_Location_ID id
	 *	@param trxName transaction
	 *	@return MLocation
	 */
	public static MLocation_New get (Properties ctx, int C_Location_ID, String trxName)
	{
		//	New
		if (C_Location_ID == 0) {
			return new MLocation_New(Env.getCtx(), C_Location_ID, trxName);
		}
		//
		Integer key = Integer.valueOf(C_Location_ID);
		MLocation_New retValue = s_cache.get (ctx, key, e -> new MLocation_New(ctx, e));
		if (retValue != null) {
			return retValue;
		}
		retValue = new MLocation_New (ctx, C_Location_ID, trxName);
		if (retValue.get_ID () == C_Location_ID)		//	found
		{
			s_cache.put (key, retValue, e -> new MLocation_New(Env.getCtx(), e));
			return retValue;
		}
		return null;					//	not found
	}	
	
	/**
	 * Get updateable copy of MLocation from cache
	 * @param ctx context
	 * @param C_Location_ID
	 * @param trxName
	 * @return MLocation
	 */
	public static MLocation_New getCopy(Properties ctx, int C_Location_ID, String trxName)
	{
		MLocation_New loc = get(C_Location_ID, trxName);
		if (loc != null && loc.getC_Location_ID() > 0) {
			loc = new MLocation_New(ctx, loc, trxName);
		}
		return loc;
	}
	
	
	

}
