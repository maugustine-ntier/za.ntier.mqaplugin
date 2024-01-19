package za.ntier.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceBatch;
import org.compiere.model.MInvoiceBatchLine;
import org.compiere.model.MOrder;
import org.compiere.util.DB;

public class MInvoice_New extends MInvoice implements I_C_Invoice  {

	private static final long serialVersionUID = -6131173380884015783L;

	public MInvoice_New(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInvoice_New(MInOut ship, Timestamp invoiceDate) {
		super(ship, invoiceDate);
		// TODO Auto-generated constructor stub
	}

	public MInvoice_New(MInvoice copy) {
		super(copy);
		// TODO Auto-generated constructor stub
	}

	public MInvoice_New(MInvoiceBatch batch, MInvoiceBatchLine line) {
		super(batch, line);
		// TODO Auto-generated constructor stub
	}

	public MInvoice_New(MOrder order, int C_DocTypeTarget_ID, Timestamp invoiceDate) {
		super(order, C_DocTypeTarget_ID, invoiceDate);
		setZZ_Batch_No(1);
	}

	public MInvoice_New(Properties ctx, int C_Invoice_ID, String trxName, String... virtualColumns) {
		super(ctx, C_Invoice_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MInvoice_New(Properties ctx, MInvoice copy, String trxName) {
		super(ctx, copy, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInvoice_New(Properties ctx, MInvoice copy) {
		super(ctx, copy);
		// TODO Auto-generated constructor stub
	}

	public MInvoice_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInvoice_New(Properties ctx, String C_Invoice_UU, String trxName) {
		super(ctx, C_Invoice_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setZZ_Batch_No(int ZZ_Batch_No) {
		System.out.println("In Here");
		set_Value (COLUMNNAME_ZZ_Batch_No, Integer.valueOf(ZZ_Batch_No));
		
	}

	@Override
	public int getZZ_Batch_No() {
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Batch_No);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	public void setZZ_Batch_No() {
		if (getZZ_Batch_No() > 0) {
			return;
		}
		String SQL = "Select max(i.ZZ_Batch_No) from C_Invoice i where i.C_Order_ID = ? ";
		int batch_No = DB.getSQLValue(get_TrxName(),SQL,getC_Order_ID());
		batch_No = (batch_No <= 0) ? 1 : batch_No + 1;	
		setZZ_Batch_No(batch_No);
	}

	@Override
	public void setZZ_CreateLinesFromRGN(String ZZ_CreateLinesFromRGN) {
		set_Value (COLUMNNAME_ZZ_CreateLinesFromRGN, ZZ_CreateLinesFromRGN);
		
	}

	@Override
	public String getZZ_CreateLinesFromRGN() {
		return (String)get_Value(COLUMNNAME_ZZ_CreateLinesFromRGN);
	}

}
