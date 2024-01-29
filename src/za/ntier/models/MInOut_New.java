package za.ntier.models;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.Query;
import org.compiere.process.DocAction;

public class MInOut_New extends MInOut implements I_M_InOut{

	private static final long serialVersionUID = 1L;

	public MInOut_New(Properties ctx, int M_InOut_ID, String trxName) {
		super(ctx, M_InOut_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInOut_New(Properties ctx, int M_InOut_ID, String trxName, String... virtualColumns) {
		super(ctx, M_InOut_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MInOut_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInOut_New(MOrder order, int C_DocTypeShipment_ID, Timestamp movementDate) {
		super(order, C_DocTypeShipment_ID, movementDate);
		// TODO Auto-generated constructor stub
	}

	public MInOut_New(MInvoice invoice, int C_DocTypeShipment_ID, Timestamp movementDate, int M_Warehouse_ID) {
		super(invoice, C_DocTypeShipment_ID, movementDate, M_Warehouse_ID);
		// TODO Auto-generated constructor stub
	}

	public MInOut_New(MInOut original, int C_DocTypeShipment_ID, Timestamp movementDate) {
		super(original, C_DocTypeShipment_ID, movementDate);
		// TODO Auto-generated constructor stub
	}

	public MInOut_New(Properties ctx, String M_InOut_UU, String trxName) {
		super(ctx, M_InOut_UU, trxName);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void setZZ_CreateLinesFrom(String ZZ_CreateLinesFrom) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getZZ_CreateLinesFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Mine_Ticket(String ZZ_Mine_Ticket) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getZZ_Mine_Ticket() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Rom_Type(String ZZ_Rom_Type) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getZZ_Rom_Type() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_StockPile_ID(int ZZ_StockPile_ID) {
		// TODO Auto-generated method stub

	}

	@Override
	public I_ZZ_StockPile getZZ_StockPile() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_StockPile_Ref(String ZZ_StockPile_Ref) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getZZ_StockPile_Ref() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Vehicle_Reg_No(String ZZ_Vehicle_Reg_No) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getZZ_Vehicle_Reg_No() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Wet_Metric_Tons(boolean ZZ_Wet_Metric_Tons) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isZZ_Wet_Metric_Tons() {
		// TODO Auto-generated method stub
		return false;
	}





	@Override
	public int getZZ_StockPile_ID() {
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_StockPile_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public MInOut_New[] getMInOutsForStockPile () {

		List<MInOut_New> list = new Query(getCtx(), I_M_InOut.Table_Name, "ZZ_StockPile_ID=? and DocStatus in ('CO','CL')", get_TrxName())
				.setParameters(getZZ_StockPile_ID())
				.list();
		return list.toArray(new MInOut_New[list.size()]);
	}

	@Override
	public String completeIt() {
		String msg = super.completeIt();
		if (msg.equals(DocAction.STATUS_Completed))	{
			X_ZZ_StockPile x_ZZ_StockPile = new X_ZZ_StockPile(getCtx(), getZZ_StockPile_ID(), get_TrxName());
			BigDecimal deliveredQty = BigDecimal.ZERO;
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






}
