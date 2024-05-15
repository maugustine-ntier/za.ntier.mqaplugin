package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Msg;

public class MTruckList extends X_ZZ_Truck_List implements I_ZZ_Truck_List {

	private static final long serialVersionUID = 1L;

	public MTruckList(Properties ctx, int ZZ_Truck_List_ID, String trxName) {
		super(ctx, ZZ_Truck_List_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MTruckList(Properties ctx, int ZZ_Truck_List_ID, String trxName, String... virtualColumns) {
		super(ctx, ZZ_Truck_List_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MTruckList(Properties ctx, String ZZ_Truck_List_UU, String trxName) {
		super(ctx, ZZ_Truck_List_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MTruckList(Properties ctx, String ZZ_Truck_List_UU, String trxName, String... virtualColumns) {
		super(ctx, ZZ_Truck_List_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MTruckList(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (getZZ_Driver_ID() > 0) {
			String SQL = "Select count(*) from ZZ_Truck_List tl where tl.ZZ_Transporters_ID = " + getZZ_Transporters_ID() 
			+ " and tl." + this.COLUMNNAME_ZZ_Driver_ID + " = " + getZZ_Driver_ID() + " and tl.isActive = 'Y' and (" + getZZ_Truck_List_ID() + " <= 0 or tl.ZZ_Truck_List_ID <> " + getZZ_Truck_List_ID() + ")";
			if (DB.getSQLValue(get_TrxName(), SQL) > 0) {
				log.saveError("Error", Msg.getMsg(getCtx(), "CannotHaveMultipleTruckListsWithTheSameDriver")); 
				return false;
			}

		}
		if (getZZ_Trailer1_ID() > 0 && getZZ_Trailer2_ID() > 0 && getZZ_Trailer1_ID() == getZZ_Trailer2_ID()) {
			log.saveError("Error", Msg.getMsg(getCtx(), "Trailers1and2Different")); 
			return false;
		}

		if (getZZ_Horse_ID() > 0) {
			String SQL = "Select count(*) from ZZ_Truck_List tl where tl.ZZ_Transporters_ID = " + getZZ_Transporters_ID() 
			+ " and tl.ZZ_Horse_ID" + " = " + getZZ_Horse_ID()
			+ " and tl.isActive = 'Y' and (" + getZZ_Truck_List_ID() + " <= 0 or tl.ZZ_Truck_List_ID <> " + getZZ_Truck_List_ID() + ")";
			if (DB.getSQLValue(get_TrxName(), SQL) > 0) {
				log.saveError("Error", Msg.getMsg(getCtx(), "HorseHasAlreadyBeenListed")); 
				return false;
			}
		}
		
		if (getZZ_Trailer1_ID() > 0) {
			String SQL = "Select count(*) from ZZ_Truck_List tl where tl.ZZ_Transporters_ID = " + getZZ_Transporters_ID() 
			+ " and (tl.ZZ_Trailer1_ID " + " = " + getZZ_Trailer1_ID() + " OR tl.ZZ_Trailer2_ID " + " = " + getZZ_Trailer1_ID() + ")"
			+ " and tl.isActive = 'Y' and (" + getZZ_Truck_List_ID() + " <= 0 or tl.ZZ_Truck_List_ID <> " + getZZ_Truck_List_ID() + ")";
			if (DB.getSQLValue(get_TrxName(), SQL) > 0) {
				log.saveError("Error", Msg.getMsg(getCtx(), "Trailer1HasAlreadyBeenListed")); 
				return false;
			}
		}
		
		if (getZZ_Trailer2_ID() > 0) {
			String SQL = "Select count(*) from ZZ_Truck_List tl where tl.ZZ_Transporters_ID = " + getZZ_Transporters_ID() 
			+ " and (tl.ZZ_Trailer1_ID " + " = " + getZZ_Trailer2_ID() + " OR tl.ZZ_Trailer2_ID " + " = " + getZZ_Trailer2_ID() + ")"
			+ " and tl.isActive = 'Y' and (" + getZZ_Truck_List_ID() + " <= 0 or tl.ZZ_Truck_List_ID <> " + getZZ_Truck_List_ID() + ")";
			if (DB.getSQLValue(get_TrxName(), SQL) > 0) {
				log.saveError("Error", Msg.getMsg(getCtx(), "Trailer2HasAlreadyBeenListed")); 
				return false;
			}
		}
		return super.beforeSave(newRecord);
	}


}
