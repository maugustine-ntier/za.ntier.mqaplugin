package za.ntier.callouts;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MYear;
import org.compiere.model.PO;
import org.compiere.util.DB;

import za.ntier.models.X_ZZ_Driver;
import za.ntier.models.X_ZZ_Petty_Cash_Advance_Hdr;
import za.ntier.models.X_ZZ_Petty_Cash_Claim_Hdr;
import za.ntier.models.X_ZZ_StockPile;
import za.ntier.models.X_ZZ_Transporters;
import za.ntier.models.X_ZZ_Truck;
import za.ntier.models.X_ZZ_Truck_List;

public class CalloutFromFactory implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		if (mTab.getTableName().equals(X_ZZ_Petty_Cash_Claim_Hdr.Table_Name) && mField.getColumnName().equals(X_ZZ_Petty_Cash_Claim_Hdr.COLUMNNAME_ZZ_Credit_Card_No)) {
			int ids[] = PO.getAllIDs(X_ZZ_Petty_Cash_Advance_Hdr.Table_Name, "ZZ_Petty_Cash_Advance_Hdr.ZZ_Credit_Card_No = '" + value + "'"  
					+ " and not exists (Select 'x' from ZZ_Petty_Cash_Claim_Hdr cl where cl.ZZ_Credit_Card_No = '" + value + "' order BY created desc)",null);
			if (ids != null && ids.length > 0) {
				mTab.setValue(X_ZZ_Petty_Cash_Claim_Hdr.COLUMNNAME_ZZ_Petty_Cash_Advance_Hdr_ID, ids[0]);
			}
		}		
		return null;
	}

	@Override
	public String toString() {
		return "CalloutFromFactory [toString()=" + super.toString() + "]";
	}

}
