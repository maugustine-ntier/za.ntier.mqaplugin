package za.ntier.modelvalidator;

import java.math.BigDecimal;

import org.compiere.model.MClient;
import org.compiere.model.MInOutLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_M_InOutLine;
import org.compiere.util.CLogger;

import za.ntier.models.MInOut_New;
import za.ntier.models.X_ZZ_StockPile;

public class NtierModelValidator implements ModelValidator{
	private static CLogger log = CLogger.getCLogger(NtierModelValidator.class);
	private int m_AD_Client_ID = -1;
	private int m_AD_User_ID = -1;
	private int m_AD_Role_ID = -1;
	private int m_AD_Org_ID  = -1;

	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		if (client != null ) m_AD_Client_ID = client.getAD_Client_ID();
		engine.addModelChange(X_ZZ_StockPile.Table_Name, this);
		engine.addModelChange(X_M_InOutLine.Table_Name, this);
		
	}

	@Override
	public int getAD_Client_ID() {
		// TODO Auto-generated method stub
		return m_AD_Client_ID;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		m_AD_Org_ID = AD_Org_ID;
		m_AD_Role_ID = AD_Role_ID;
		m_AD_User_ID = AD_User_ID;
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception {
		log.info("PO: " + po.toString() + "   - timing : " + type);
		if (po.get_TableName().equals(X_M_InOutLine.Table_Name )) {
			if (type == TIMING_AFTER_COMPLETE) {
				MInOut_New mInOut = new MInOut_New(po.getCtx(), po.get_ID(), po.get_TrxName());
				X_ZZ_StockPile x_ZZ_StockPile = new X_ZZ_StockPile(po.getCtx(), mInOut.getZZ_StockPile_ID(), po.get_TrxName());
				BigDecimal deliveredQty = BigDecimal.ZERO;
				MInOutLine[] m_InOutLines = mInOut.getLines();
				for (MInOutLine mInOutLine:m_InOutLines) {
					deliveredQty = deliveredQty.add(mInOutLine.getMovementQty());
				}
				x_ZZ_StockPile.setZZ_Used_Tonnage(deliveredQty);
			}
			
		}
		return null;
	}

	@Override
	public String docValidate(PO po, int timing) {
		// TODO Auto-generated method stub
		return null;
	}

}
