/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package za.ntier.editors;

import static org.compiere.util.DisplayType.Account;
import static org.compiere.util.DisplayType.Location;
import static org.compiere.util.DisplayType.Locator;
import static org.compiere.util.DisplayType.PAttribute;
import static org.compiere.util.DisplayType.Payment;

import org.adempiere.base.ILookupFactory;
import org.compiere.model.GridFieldVO;
import org.compiere.model.InfoColumnVO;
import org.compiere.model.Lookup;
import org.compiere.model.MAccountLookup;
import org.compiere.model.MLocatorLookup;
import org.compiere.model.MLookup;
import org.compiere.model.MPAttributeLookup;
import org.compiere.model.MPaymentLookup;
import org.compiere.util.DisplayType;
import org.osgi.service.component.annotations.Component; 

/**
 * Default {@link ILookupFactory} implementation for core.<br/>
 * Create new {@link Lookup} instance by predefined display type.
 * @author Jan Thielemann - jan.thielemann@evenos.de
 * @author hengsin
 */
@Component(

		 property= {"service.ranking:Integer=200"},
		 service = org.adempiere.base.ILookupFactory.class
		 )  
public class MyLookupFactory implements ILookupFactory{
	
	public static final int RadioGroupListVertical = 1000003;

	@Override
	public Lookup getLookup(GridFieldVO gridFieldVO) {
		Lookup lookup = null;
		if (gridFieldVO.lookupInfo == null && DisplayType.isLookup(gridFieldVO.displayType)) { // IDEMPIERE-913
			gridFieldVO.loadLookupInfo();
		}
		if (gridFieldVO.displayType == Location)   //  not cached
		{
			lookup = new MLocationLookup_New (gridFieldVO.ctx, gridFieldVO.WindowNo);
			//lookup = new MLocationLookup (gridFieldVO.ctx, gridFieldVO.WindowNo);
		}
		else if (gridFieldVO.displayType == DisplayType.Locator)
		{
			lookup = new MLocatorLookup (gridFieldVO.ctx, gridFieldVO.WindowNo, gridFieldVO.ValidationCode);
		}
		else if (gridFieldVO.displayType == Account)    //  not cached
		{
			lookup = new MAccountLookup (gridFieldVO.ctx, gridFieldVO.WindowNo);
		}
		else if (gridFieldVO.displayType == PAttribute)    //  not cached
		{
			lookup = new MPAttributeLookup (gridFieldVO.ctx, gridFieldVO.WindowNo);
		}
		else if (gridFieldVO.displayType == Payment)
		{
			lookup = new MPaymentLookup (gridFieldVO.ctx, gridFieldVO.WindowNo, gridFieldVO.ValidationCode);
		}
		else if ((isLookup(gridFieldVO) || DisplayType.isLookup(gridFieldVO.displayType)) && gridFieldVO.lookupInfo != null)  // Martin added for radio group
		{
			lookup = new MLookup (gridFieldVO.lookupInfo, gridFieldVO.TabNo);
		}
		return lookup;
	}

	@Override
	public boolean isLookup(GridFieldVO gridFieldVO) {
		if (gridFieldVO.displayType == Location
			|| gridFieldVO.displayType == Locator
			|| gridFieldVO.displayType == Account
			|| gridFieldVO.displayType == PAttribute
			|| gridFieldVO.displayType == Payment
			|| gridFieldVO.displayType == RadioGroupListVertical
			|| DisplayType.isLookup(gridFieldVO.displayType)) {
			return true;
		}
				
		return false;
	}

	@Override
	public boolean isLookup(InfoColumnVO infoColumnVO) {
		int displayType = infoColumnVO.getAD_Reference_ID();
		if (displayType == Location
				|| displayType == Locator
				|| displayType == Account
				|| displayType == PAttribute
				|| displayType == Payment
						|| displayType == RadioGroupListVertical
				|| DisplayType.isLookup(displayType)) {
			return true;
		}
					
			return false;
	}

}
