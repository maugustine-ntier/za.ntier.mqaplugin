package za.ntier.models;


import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.util.DB;

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

		if (getZZ_License_ID() > 0) {
			setZZ_License_Attached(true);

		} else {
			setZZ_License_Attached(false);
		}

		if (getZZ_ID_Passport_ID() > 0) {
			setZZ_ID_Passport_Attached(true);
		} else {
			setZZ_ID_Passport_Attached(false);
		}
		if (isZZ_ID_Passport_Attached() && isZZ_License_Attached()) {
			setZZ_Is_Valid(true);
		} else {
			setZZ_Is_Valid(false);
		}

		return super.beforeSave(newRecord);
	}
	public static MDriver getDriver(Properties ctx,String zz_ID_Passport_No,String trxName) {
		MDriver mDriver = null;
		if (zz_ID_Passport_No != null) {
			String SQL = "select d.ZZ_Driver_ID from ZZ_Driver d where d.ZZ_ID_Passport_No = ?";
			int zz_Driver_ID = DB.getSQLValue(trxName, SQL, zz_ID_Passport_No.trim());
			if (zz_Driver_ID > 0) {
				mDriver = new MDriver(ctx, zz_Driver_ID, trxName);
			}
		}
		return mDriver;
	}

	public static MDriver createDriver(Properties ctx,String zz_ID_Passport_No,String name, String surname, Timestamp expiryDt, String trxName) {
		MDriver mDriver = new MDriver(ctx, 0, trxName);
		mDriver.setZZ_ID_Passport_No(zz_ID_Passport_No);
		mDriver.setName(name);
		mDriver.setZZ_Surname(surname);
		if (expiryDt == null) {
			mDriver.setZZ_License_Expiry_Date(DB.getSQLValueTS(trxName,"select to_Date('01011960','ddmmyyyy')"));
		}
		if (mDriver.save()) {
			return mDriver;
		}
		return null;
	}

	public static Object[] getDriver(Properties ctx,int c_BPartner_ID,int m_Product_ID,Timestamp LoadingDate,String truckRegNo,String trxName) {
		MDriver mDriver = null;		
		Object objs [] = new Object [2];
		if (LoadingDate != null) {
			MTransporters mTransporters [] = MTransporters.get(ctx, c_BPartner_ID, m_Product_ID, LoadingDate, trxName);
			for (MTransporters mTransporter : mTransporters) {
				String SQL = "select max(tl.ZZ_Driver_ID) from ZZ_Truck_List tl, ZZ_Truck t where tl.ZZ_Transporters_ID = ? and tl.ZZ_Horse_ID = t.ZZ_truck_ID and t.ZZ_Registration_No = ?";
				int zz_Driver_ID = DB.getSQLValue(trxName, SQL, mTransporter.getZZ_Transporters_ID(),truckRegNo);
				if (zz_Driver_ID > 0) {
					mDriver = new MDriver(ctx, zz_Driver_ID, trxName);
					objs[0] = mDriver;
					objs[1] = mTransporter;
					return objs;
				}
			}
		}
		return null;
	}


}
