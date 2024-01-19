package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;

public class MInOutLine_New extends MInOutLine {

	public MInOutLine_New(Properties ctx, String M_InOutLine_UU, String trxName) {
		super(ctx, M_InOutLine_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInOutLine_New(Properties ctx, int M_InOutLine_ID, String trxName) {
		super(ctx, M_InOutLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInOutLine_New(Properties ctx, int M_InOutLine_ID, String trxName, String... virtualColumns) {
		super(ctx, M_InOutLine_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MInOutLine_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInOutLine_New(MInOut inout) {
		super(inout);
		// TODO Auto-generated constructor stub
	}

}
