package za.ntier.models;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;

public class MBPartnerLocation_New extends MBPartnerLocation {


	private static final long serialVersionUID = 1L;



	public MBPartnerLocation_New(Properties ctx, String C_BPartner_Location_UU, String trxName) {
		super(ctx, C_BPartner_Location_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartnerLocation_New(Properties ctx, int C_BPartner_Location_ID, String trxName) {
		super(ctx, C_BPartner_Location_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartnerLocation_New(MBPartner bp) {
		super(bp);
		// TODO Auto-generated constructor stub
	}

	public MBPartnerLocation_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartnerLocation_New(MBPartnerLocation copy) {
		super(copy);
		// TODO Auto-generated constructor stub
	}

	public MBPartnerLocation_New(Properties ctx, MBPartnerLocation copy) {
		super(ctx, copy);
		// TODO Auto-generated constructor stub
	}

	public MBPartnerLocation_New(Properties ctx, MBPartnerLocation copy, String trxName) {
		super(ctx, copy, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartnerLocation_New(Properties ctx, int C_BPartner_Location_ID, String trxName, String... virtualColumns) {
		super(ctx, C_BPartner_Location_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		boolean done =  super.afterSave(newRecord, success);
		if (!done) {
			return false;
		}
		if (getC_BPartner_ID() != 0 ) {
			CopyRecordToOtherClients copyRecordToOtherClients = new CopyRecordToOtherClients(getCtx(),get_TrxName(),getAD_Client_ID(),getC_BPartner_Location_ID(),get_TableName());
		}
		return true;
	}

}
