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
		processClaims(zz_Petty_Cash_Recon_Hdr_ID);
		processAdvances(zz_Petty_Cash_Recon_Hdr_ID);
		return null;
	}

	private void processClaims(int zz_Petty_Cash_Recon_Hdr_ID) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectQuery = "SELECT cl.ZZ_Petty_Cash_Claim_Line_ID from ZZ_Petty_Cash_Claim_Line cl join ZZ_Petty_Cash_Claim_Hdr ch on cl.ZZ_Petty_Cash_Claim_Hdr_ID = cl. ZZ_Petty_Cash_Claim_Hdr_ID "
				+ " where ch.ZZ_Petty_Cash_Advance_ID is not null and ch.ZZ_Petty_Cash_Advance_ID > 0 and ch.ZZ_DocStatus = 'CO' and "
				+ " ch.ZZ_Date_Completed >= to_date(' " + start_Date + ",'ddmmyyyy') and "
				+ " ch.ZZ_Date_Completed < to_date(' " + end_Date + ",'ddmmyyyy') + 1";
		try {
			pstmt = DB.prepareStatement(selectQuery, get_TrxName());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MZZPettyCashClaimLine mZZPettyCashClaimLine = new MZZPettyCashClaimLine(getCtx(), rs.getInt(1), get_TrxName());
				mZZPettyCashClaimLine.setZZ_Petty_Cash_Recon_Hdr_ID(zz_Petty_Cash_Recon_Hdr_ID);
				mZZPettyCashClaimLine.saveEx();
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
	}

	private void processAdvances(int zz_Petty_Cash_Recon_Hdr_ID) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectQuery = "SELECT ca.ZZ_Petty_Cash_Advance_Hdr_ID from ZZ_Petty_Cash_Advance_Hdr ca "
				+ " where ca.ZZ_DocStatus = 'CO' and "
				+ " not exists (select 'x' from ZZ_Petty_Cash_Claim_Hdr ch where ch.ZZ_Petty_Cash_Advance_Hdr_ID = ?)"; 
		try {
			pstmt = DB.prepareStatement(selectQuery, get_TrxName());
			pstmt.setInt(1, zz_Petty_Cash_Recon_Hdr_ID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MZZPettyCashClaimLine mZZPettyCashClaimLine = new MZZPettyCashClaimLine(getCtx(), rs.getInt(1), get_TrxName());
				mZZPettyCashClaimLine.setZZ_Petty_Cash_Recon_Hdr_ID(zz_Petty_Cash_Recon_Hdr_ID);
				mZZPettyCashClaimLine.saveEx();
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
	}



}
