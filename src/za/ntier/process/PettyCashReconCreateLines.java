package za.ntier.process;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import org.compiere.model.MProcessPara;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import za.ntier.models.MZZPettyCashClaimHdr;
import za.ntier.models.MZZPettyCashClaimLine;
import za.ntier.models.X_ZZ_Petty_Cash_Advance_Hdr;
import za.ntier.models.X_ZZ_Petty_Cash_Claim_Hdr;
import za.ntier.models.X_ZZ_Petty_Cash_Claim_Line;
import za.ntier.models.X_ZZ_Petty_Cash_Recon_Advance;


@org.adempiere.base.annotation.Process
public class PettyCashReconCreateLines extends SvrProcess {	
	
	String start_Date = null;
	String end_Date = null;
	String pattern = "ddMMyyyy";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	


	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("START_DATE")) {
				start_Date = simpleDateFormat.format(para[i].getParameterAsTimestamp());
			} else if (name.equals("END_DATE")) {
				end_Date = simpleDateFormat.format(para[i].getParameterAsTimestamp());
			}
			else
				MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para[i]);
		}

	}

	@Override
	protected String doIt() throws Exception {
		int zz_Petty_Cash_Recon_Hdr_ID = getRecord_ID();
		int claim_IDs [] = PO.getAllIDs(X_ZZ_Petty_Cash_Claim_Line.Table_Name, "Not exists (Select 'x' from ZZ_Petty_Cash_Claim_Line cl "
				+ " join ZZ_Petty_Cash_Claim_Hdr ch on cl.ZZ_Petty_Cash_Claim_Hdr_ID = cl. ZZ_Petty_Cash_Claim_Hdr_ID where cl.ZZ_Petty_Cash_Advance_ID is null or "
				+ " cl.ZZ_Petty_Cash_Advance_ID <= 0) ", get_TrxName());
		for (int claim_ID : claim_IDs) {			
			MZZPettyCashClaimLine mZZPettyCashClaimLine = new MZZPettyCashClaimLine(getCtx(), claim_ID, get_TrxName());
			MZZPettyCashClaimHdr mZZPettyCashClaimHdr = new MZZPettyCashClaimHdr(getCtx(),mZZPettyCashClaimLine.getZZ_Petty_Cash_Claim_Hdr_ID(),get_TrxName());
			if (mZZPettyCashClaimHdr.getZZ_DocStatus().equals(X_ZZ_Petty_Cash_Claim_Hdr.ZZ_DOCSTATUS_Completed)) {
				mZZPettyCashClaimLine.setZZ_Petty_Cash_Recon_Hdr_ID(zz_Petty_Cash_Recon_Hdr_ID);
				mZZPettyCashClaimLine.saveEx();
			}
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectQuery = "SELECT cl.ZZ_Petty_Cash_Claim_Line_ID from ZZ_Petty_Cash_Claim_Line cl join ZZ_Petty_Cash_Claim_Hdr ch on cl.ZZ_Petty_Cash_Claim_Hdr_ID = cl. ZZ_Petty_Cash_Claim_Hdr_ID "
				+ " where ch.ZZ_Petty_Cash_Advance_ID is not null and ch.ZZ_Petty_Cash_Advance_ID > 0 and ch.ZZ_DocStatus = 'CO'";
		try {
			pstmt = DB.prepareStatement(selectQuery, get_TrxName());
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
			}
		} catch (Exception ex)	{
			log.log(Level.SEVERE, selectQuery, ex);
			throw ex;
		}
		finally
		{
			DB.close(rs,pstmt);
			rs = null;pstmt = null;
		}

		int advance_IDs [] = PO.getAllIDs(X_ZZ_Petty_Cash_Advance_Hdr.Table_Name, "ZZ_Petty_Cash_Advance_Hdr_ID not in ("
				+ "Select ch.ZZ_Petty_Cash_Advance_Hdr_ID from ZZ_Petty_Cash_Claim_Hdr ch where ch.ZZ_Petty_Cash_Advance_Hdr_ID is not null)", get_TrxName());
		for (int advance_ID : advance_IDs) {
			X_ZZ_Petty_Cash_Advance_Hdr x_ZZ_Petty_Cash_Advance_Hdr = new X_ZZ_Petty_Cash_Advance_Hdr(getCtx(),advance_ID,get_TrxName());			
			if (x_ZZ_Petty_Cash_Advance_Hdr.getZZ_DocStatus().equals(X_ZZ_Petty_Cash_Advance_Hdr.ZZ_DOCSTATUS_Completed)) {
				X_ZZ_Petty_Cash_Recon_Advance x_ZZ_Petty_Cash_Recon_Advance = new X_ZZ_Petty_Cash_Recon_Advance(getCtx(),0,get_TrxName());
				x_ZZ_Petty_Cash_Recon_Advance.setZZ_Petty_Cash_Advance_Hdr_ID(advance_ID);
				x_ZZ_Petty_Cash_Recon_Advance.setZZ_Petty_Cash_Recon_Hdr_ID(zz_Petty_Cash_Recon_Hdr_ID);
				x_ZZ_Petty_Cash_Recon_Advance.saveEx();
			}
		}
		return null;
	}



}
