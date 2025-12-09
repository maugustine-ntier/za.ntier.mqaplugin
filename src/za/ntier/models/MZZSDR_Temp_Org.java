package za.ntier.models;

import java.sql.ResultSet;
import org.compiere.util.DB;
import java.util.Properties;

public class MZZSDR_Temp_Org extends X_ZZ_SDR_Temp_Org {

	public MZZSDR_Temp_Org(Properties ctx, int ZZ_SDR_Temp_Org_ID, String trxName) {
		super(ctx, ZZ_SDR_Temp_Org_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZSDR_Temp_Org(Properties ctx, int ZZ_SDR_Temp_Org_ID, String trxName, String... virtualColumns) {
		super(ctx, ZZ_SDR_Temp_Org_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZSDR_Temp_Org(Properties ctx, String ZZ_SDR_Temp_Org_UU, String trxName) {
		super(ctx, ZZ_SDR_Temp_Org_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZSDR_Temp_Org(Properties ctx, String ZZ_SDR_Temp_Org_UU, String trxName, String... virtualColumns) {
		super(ctx, ZZ_SDR_Temp_Org_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZSDR_Temp_Org(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

	    // === 1. Validate Organisation Registration No ===
	    Object orgRegObj = get_Value("ZZ_Organisation_Reg_No");
	    if (orgRegObj != null) {
	        String orgRegNo = orgRegObj.toString().trim();
	        if (!orgRegNo.isEmpty()) {
	            // 1.1 Check temp table
	            String sqlTemp = "SELECT COUNT(*) FROM ZZ_SDR_Temp_Org "
	                    + "WHERE ZZ_Organisation_Reg_No = ? "
	                    + "AND ZZ_SDR_Temp_Org_ID <> ?";
	            int cntTemp = DB.getSQLValueEx(get_TrxName(), sqlTemp,
	                    orgRegNo,
	                    getZZ_SDR_Temp_Org_ID());
	            if (cntTemp > 0) {
	                log.saveError("Error", "Organisation Registration No already exists in temporary registrations.");
	                return false;
	            }
	            // 1.2 Check in BP.ReferenceNo
	            String sqlBP = "SELECT COUNT(*) FROM C_BPartner WHERE ReferenceNo = ?";
	            int cntBP = DB.getSQLValueEx(get_TrxName(), sqlBP, orgRegNo);
	            if (cntBP > 0) {
	                log.saveError("Error", "Organisation Registration No already exists for an existing Business Partner.");
	                return false;
	            }
	        }
	    }

	    // === 2. Validate SDL No ===
	    Object sdlObj = get_Value("ZZ_SDL_No");
	    if (sdlObj != null) {
	        String sdl = sdlObj.toString().trim();
	        if (!sdl.isEmpty()) {
	            // 2.1 Check temp table
	            String sqlTempSDL = "SELECT COUNT(*) FROM ZZ_SDR_Temp_Org "
	                    + "WHERE ZZ_SDL_No = ? "
	                    + "AND ZZ_SDR_Temp_Org_ID <> ?";
	            int cntTempSDL = DB.getSQLValueEx(get_TrxName(), sqlTempSDL,
	                    sdl,
	                    getZZ_SDR_Temp_Org_ID());
	            if (cntTempSDL > 0) {
	                log.saveError("Error", "SDL No already exists in temporary registrations.");
	                return false;
	            }
	            // 2.2 Check C_BPartner.Value
	            String sqlBPValue = "SELECT COUNT(*) FROM C_BPartner WHERE Value = ?";
	            int cntBPValue = DB.getSQLValueEx(get_TrxName(), sqlBPValue, sdl);
	            if (cntBPValue > 0) {
	                log.saveError("Error", "SDL No already exists as a Business Partner Search Key.");
	                return false;
	            }
	        }
	    }

	    return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		return success;
	}

}
