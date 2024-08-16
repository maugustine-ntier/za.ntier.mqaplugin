package za.ntier.modelvalidator;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_R_Request;
import org.compiere.util.CLogger;

import za.ntier.models.X_M_InOut;
import za.ntier.models.X_ZZ_StockPile;

public class NtierModelValidator implements ModelValidator{
	private static CLogger log = CLogger.getCLogger(NtierModelValidator.class);
	private int m_AD_Client_ID = -1;
	private int m_AD_User_ID = -1;
	private int m_AD_Role_ID = -1;
	private int m_AD_Org_ID  = -1;

	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		if (client != null ) {
			m_AD_Client_ID = client.getAD_Client_ID();
		}
		engine.addModelChange(X_ZZ_StockPile.Table_Name, this);
		engine.addModelChange(X_M_InOut.Table_Name, this);
		engine.addDocValidate(X_M_InOut.Table_Name, this);
		engine.addModelChange(X_R_Request.Table_Name, this);
		
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
		
		return null;
	}

	@Override
	public String docValidate(PO po, int timing) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @Override public String docValidate(PO po, int timing) { log.info("PO: " +
	 * po.toString() + "   - timing : " + timing); if
	 * (po.get_TableName().equals(X_M_InOut.Table_Name )) { if (timing ==
	 * TIMING_AFTER_COMPLETE) { MInOut_New mInOut = new MInOut_New(po.getCtx(),
	 * po.get_ID(), po.get_TrxName()); X_ZZ_StockPile x_ZZ_StockPile = new
	 * X_ZZ_StockPile(po.getCtx(), mInOut.getZZ_StockPile_ID(), po.get_TrxName());
	 * BigDecimal deliveredQty = BigDecimal.ZERO; MInOut_New[] m_InOuts =
	 * mInOut.getMInOutsForStockPile(); for (MInOut_New mInOut_New:m_InOuts) {
	 * MInOutLine[] m_InOutLines = mInOut_New.getLines(); for (MInOutLine
	 * mInOutLine:m_InOutLines) { deliveredQty =
	 * deliveredQty.add(mInOutLine.getMovementQty()); } }
	 * x_ZZ_StockPile.setZZ_Used_Tonnage(deliveredQty); x_ZZ_StockPile.saveEx(); }
	 * 
	 * } return null; }
	 */

}
