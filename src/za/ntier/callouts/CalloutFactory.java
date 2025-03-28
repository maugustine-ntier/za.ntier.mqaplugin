package za.ntier.callouts;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.IColumnCalloutFactory;
import org.compiere.model.MProduct;
import org.osgi.service.component.annotations.Component;

import za.ntier.models.X_ZZ_Driver;
import za.ntier.models.X_ZZ_Petty_Cash_Claim_Hdr;
import za.ntier.models.X_ZZ_StockPile;
import za.ntier.models.X_ZZ_Transporters;
import za.ntier.models.X_ZZ_Truck_List;

@Component(

		 property= {"service.ranking:Integer=2"},
		 service = org.adempiere.base.IColumnCalloutFactory.class
		 )

public class CalloutFactory implements IColumnCalloutFactory {

	@Override
	public IColumnCallout[] getColumnCallouts(String tableName, String columnName) {
		List<IColumnCallout> list = new ArrayList<IColumnCallout>();
		if (tableName.equals(X_ZZ_Petty_Cash_Claim_Hdr.Table_Name))
		{
			list.add(new CalloutFromFactory());
		}
		return list != null ?  list.toArray(new IColumnCallout[list.size()]) : new IColumnCallout[0];
	}

}
