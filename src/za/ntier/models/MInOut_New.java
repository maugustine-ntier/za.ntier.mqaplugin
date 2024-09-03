package za.ntier.models;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.DB;

public class MInOut_New extends MInOut implements I_M_InOut{




	private static final long serialVersionUID = 1L;

	public MInOut_New(Properties ctx, int M_InOut_ID, String trxName) {
		super(ctx, M_InOut_ID, trxName);
	}

	public MInOut_New(Properties ctx, int M_InOut_ID, String trxName, String... virtualColumns) {
		super(ctx, M_InOut_ID, trxName, virtualColumns);
	}

	public MInOut_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MInOut_New(MOrder order, int C_DocTypeShipment_ID, Timestamp movementDate) {
		super(order, C_DocTypeShipment_ID, movementDate);
	}

	public MInOut_New(MInvoice invoice, int C_DocTypeShipment_ID, Timestamp movementDate, int M_Warehouse_ID) {
		super(invoice, C_DocTypeShipment_ID, movementDate, M_Warehouse_ID);
	}

	public MInOut_New(MInOut original, int C_DocTypeShipment_ID, Timestamp movementDate) {
		super(original, C_DocTypeShipment_ID, movementDate);
	}

	public MInOut_New(Properties ctx, String M_InOut_UU, String trxName) {
		super(ctx, M_InOut_UU, trxName);
	}





	public MInOut_New[] getMInOutsForStockPile () {
		// Even without the M_Inout_ID, we never get the current MInout record.  Not sure why
		List<MInOut_New> list = new Query(getCtx(), I_M_InOut.Table_Name, "ZZ_StockPile_ID=? and DocStatus in ('CO','CL') and M_InOut_ID <> ?", get_TrxName())
				.setParameters(getZZ_StockPile_ID(),getM_InOut_ID())
				.list();
		return list.toArray(new MInOut_New[list.size()]);
	}

	@Override
	public String completeIt() {
		String msg = super.completeIt();
		if (msg.equals(DocAction.STATUS_Completed))	{
			X_ZZ_StockPile x_ZZ_StockPile = new X_ZZ_StockPile(getCtx(), getZZ_StockPile_ID(), get_TrxName());
			BigDecimal deliveredQty = BigDecimal.ZERO;
			MInOutLine[] m_InOutLines_curr = this.getLines();
			for (MInOutLine mInOutLine:m_InOutLines_curr) {
				deliveredQty = deliveredQty.add(mInOutLine.getMovementQty());
			}
			MInOut_New[] m_InOuts = getMInOutsForStockPile();
			for (MInOut_New mInOut_New:m_InOuts) {
				MInOutLine[] m_InOutLines = mInOut_New.getLines();
				for (MInOutLine mInOutLine:m_InOutLines) {
					deliveredQty = deliveredQty.add(mInOutLine.getMovementQty());
				}
			}
			x_ZZ_StockPile.setZZ_Used_Tonnage(deliveredQty);
			x_ZZ_StockPile.saveEx();
		}
		return msg;
	}

	/** Set Create lines from.
	@param ZZ_CreateLinesFrom Process which will generate a new document lines based on an existing document
	 */
	@Override
	public void setZZ_CreateLinesFrom (String ZZ_CreateLinesFrom)
	{
		set_Value (COLUMNNAME_ZZ_CreateLinesFrom, ZZ_CreateLinesFrom);
	}

	/** Get Create lines from.
	@return Process which will generate a new document lines based on an existing document
	 */
	@Override
	public String getZZ_CreateLinesFrom()
	{
		return (String)get_Value(COLUMNNAME_ZZ_CreateLinesFrom);
	}

	@Override
	public I_ZZ_Driver getZZ_Driver() throws RuntimeException
	{
		return (I_ZZ_Driver)MTable.get(getCtx(), I_ZZ_Driver.Table_ID)
				.getPO(getZZ_Driver_ID(), get_TrxName());
	}

	/** Set Driver.
	@param ZZ_Driver_ID Driver table for Transporter window
	 */
	@Override
	public void setZZ_Driver_ID (int ZZ_Driver_ID)
	{
		if (ZZ_Driver_ID < 1) {
			set_ValueNoCheck (COLUMNNAME_ZZ_Driver_ID, null);
		} else {
			set_ValueNoCheck (COLUMNNAME_ZZ_Driver_ID, Integer.valueOf(ZZ_Driver_ID));
		}
	}
 
	/** Get Driver.
	@return Driver table for Transporter window
	 */
	@Override
	public int getZZ_Driver_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Driver_ID);
		if (ii == null) {
			return 0;
		}
		return ii.intValue();
	}

	/** Set Mine Ticket.
	@param ZZ_Mine_Ticket Mine Ticket
	 */
	@Override
	public void setZZ_Mine_Ticket (String ZZ_Mine_Ticket)
	{
		set_Value (COLUMNNAME_ZZ_Mine_Ticket, ZZ_Mine_Ticket);
	}

	/** Get Mine Ticket.
	@return Mine Ticket	  */
	@Override
	public String getZZ_Mine_Ticket()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Mine_Ticket);
	}

	
	/** Set Rom Type.
	@param ZZ_Rom_Type Rom Type
	 */
	@Override
	public void setZZ_Rom_Type (String ZZ_Rom_Type)
	{

		set_Value (COLUMNNAME_ZZ_Rom_Type, ZZ_Rom_Type);
	}

	/** Get Rom Type.
	@return Rom Type	  */
	@Override
	public String getZZ_Rom_Type()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Rom_Type);
	}

	@Override
	public I_ZZ_StockPile getZZ_StockPile() throws RuntimeException
	{
		return (I_ZZ_StockPile)MTable.get(getCtx(), I_ZZ_StockPile.Table_ID)
				.getPO(getZZ_StockPile_ID(), get_TrxName());
	}

	/** Set StockPile .
	@param ZZ_StockPile_ID StockPile 
	 */
	@Override
	public void setZZ_StockPile_ID (int ZZ_StockPile_ID)
	{
		if (ZZ_StockPile_ID < 1) {
			set_ValueNoCheck (COLUMNNAME_ZZ_StockPile_ID, null);
		} else {
			set_ValueNoCheck (COLUMNNAME_ZZ_StockPile_ID, Integer.valueOf(ZZ_StockPile_ID));
		}
	}

	/** Get StockPile .
	@return StockPile 	  */
	@Override
	public int getZZ_StockPile_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_StockPile_ID);
		if (ii == null) {
			return 0;
		}
		return ii.intValue();
	}

	/** Set StockPile Reference.
	@param ZZ_StockPile_Ref StockPile Reference
	 */
	@Override
	public void setZZ_StockPile_Ref (String ZZ_StockPile_Ref)
	{
		set_Value (COLUMNNAME_ZZ_StockPile_Ref, ZZ_StockPile_Ref);
	}

	/** Get StockPile Reference.
	@return StockPile Reference	  */
	@Override
	public String getZZ_StockPile_Ref()
	{
		return (String)get_Value(COLUMNNAME_ZZ_StockPile_Ref);
	}

	/** Set Vehicle Reg No.
	@param ZZ_Vehicle_Reg_No Vehicle Reg No
	 */
	@Override
	public void setZZ_Vehicle_Reg_No (String ZZ_Vehicle_Reg_No)
	{
		set_Value (COLUMNNAME_ZZ_Vehicle_Reg_No, ZZ_Vehicle_Reg_No);
	}

	/** Get Vehicle Reg No.
	@return Vehicle Reg No	  */
	@Override
	public String getZZ_Vehicle_Reg_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Vehicle_Reg_No);
	}

	/** Set Wet Metric Tons.
	@param ZZ_Wet_Metric_Tons Wet Metric Tons
	 */
	@Override
	public void setZZ_Wet_Metric_Tons (boolean ZZ_Wet_Metric_Tons)
	{
		set_Value (COLUMNNAME_ZZ_Wet_Metric_Tons, Boolean.valueOf(ZZ_Wet_Metric_Tons));
	}

	/** Get Wet Metric Tons.
	@return Wet Metric Tons	  */
	@Override
	public boolean isZZ_Wet_Metric_Tons()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Wet_Metric_Tons);
		if (oo != null)
		{
			if (oo instanceof Boolean) {
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public void setWB_TransactionID(int WB_TransactionID) {
		set_Value (COLUMNNAME_WB_TransactionID, Integer.valueOf(WB_TransactionID));
		
	}

	@Override
	public int getWB_TransactionID() {
		Integer ii = (Integer)get_Value(COLUMNNAME_WB_TransactionID);
		if (ii == null) {
			return 0;
		}
		return ii.intValue();
	}
	
	public static int getCount(int wb_TransactionID,int ad_Client_ID, String trxName) {
		int cnt = 0;
		String SQL = "select count(*) from M_InOut m where m.WB_TransactionID = ? and m.AD_Client_ID = ?";
		cnt = DB.getSQLValue(trxName, SQL,wb_TransactionID,ad_Client_ID );
		return cnt;
		
	}


}
