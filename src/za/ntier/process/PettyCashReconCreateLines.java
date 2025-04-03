package za.ntier.process;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import org.compiere.model.MProcessPara;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import za.ntier.models.MZZPettyCashReconHdr;
import za.ntier.models.X_ZZ_Petty_Cash_Recon_Advance;
import za.ntier.models.X_ZZ_Petty_Cash_Recon_Claim;
import za.ntier.models.X_ZZ_Petty_Cash_Recon_Hdr;


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
		X_ZZ_Petty_Cash_Recon_Hdr x_ZZ_Petty_Cash_Recon_Hdr = new X_ZZ_Petty_Cash_Recon_Hdr(getCtx(),zz_Petty_Cash_Recon_Hdr_ID,get_TrxName());
		start_Date = simpleDateFormat.format(x_ZZ_Petty_Cash_Recon_Hdr.getStartDate());
		end_Date = simpleDateFormat.format(x_ZZ_Petty_Cash_Recon_Hdr.getEndDate());
		processClaims(zz_Petty_Cash_Recon_Hdr_ID);
		processAdvances(zz_Petty_Cash_Recon_Hdr_ID);
		MZZPettyCashReconHdr mZZPettyCashReconHdr = new MZZPettyCashReconHdr(getCtx(), zz_Petty_Cash_Recon_Hdr_ID, get_TrxName());
		mZZPettyCashReconHdr.updateTotals();
		mZZPettyCashReconHdr.saveEx();
		return null;
	}

	private void processClaims(int zz_Petty_Cash_Recon_Hdr_ID) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectQuery = "SELECT cl.ZZ_Petty_Cash_Claim_Line_ID,cl.amount,cl.zz_Petty_Cash_Claim_Hdr_ID from ZZ_Petty_Cash_Claim_Line cl "
				+ " join ZZ_Petty_Cash_Claim_Hdr ch on cl.ZZ_Petty_Cash_Claim_Hdr_ID = ch. ZZ_Petty_Cash_Claim_Hdr_ID "
				+ " where ch.ZZ_DocStatus = 'CO' and "
				+ " ch.ZZ_Date_Completed >= to_date('%s','ddmmyyyy') and "
				+ " ch.ZZ_Date_Completed < to_date('%s','ddmmyyyy') + 1"
				+ " not exists (select 'x' from ZZ_Petty_Cash_Recon_claim ra where ra.ZZ_Petty_Cash_Claim_Line_ID = ca.ZZ_Petty_Cash_Claim_Line_ID"
				+ "   and ra.ZZ_Petty_Cash_Claim_Line_ID = ?)";
		selectQuery = String.format(selectQuery, start_Date,end_Date);
		try {
			pstmt = DB.prepareStatement(selectQuery, get_TrxName());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				X_ZZ_Petty_Cash_Recon_Claim x_ZZ_Petty_Cash_Recon_Claim = new X_ZZ_Petty_Cash_Recon_Claim(getCtx(), 0, get_TrxName());
				x_ZZ_Petty_Cash_Recon_Claim.setZZ_Petty_Cash_Claim_Hdr_ID(rs.getInt(3));
				x_ZZ_Petty_Cash_Recon_Claim.setZZ_Petty_Cash_Claim_Line_ID(rs.getInt(1));
				x_ZZ_Petty_Cash_Recon_Claim.setZZ_Petty_Cash_Recon_Hdr_ID(zz_Petty_Cash_Recon_Hdr_ID);
				x_ZZ_Petty_Cash_Recon_Claim.setAmount(rs.getBigDecimal(2));
				x_ZZ_Petty_Cash_Recon_Claim.saveEx();
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
		String selectQuery = "SELECT ca.ZZ_Petty_Cash_Advance_Hdr_ID,ca.totalAmt from ZZ_Petty_Cash_Advance_Hdr ca "
				+ " where ca.ZZ_DocStatus = 'CO' and "
				+ " not exists (select 'x' from ZZ_Petty_Cash_Claim_Hdr ch where ch.ZZ_Petty_Cash_Advance_Hdr_ID = ca.ZZ_Petty_Cash_Advance_Hdr_ID) and "
				+ " not exists (select 'x' from ZZ_Petty_Cash_Recon_Advance ra where ra.ZZ_Petty_Cash_Advance_Hdr_ID = ca.ZZ_Petty_Cash_Advance_Hdr_ID"
				+ "   and ra.zz_Petty_Cash_Recon_Hdr_ID = ?)"; 
		try {
			pstmt = DB.prepareStatement(selectQuery, get_TrxName());
			pstmt.setInt(1, zz_Petty_Cash_Recon_Hdr_ID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				X_ZZ_Petty_Cash_Recon_Advance x_ZZ_Petty_Cash_Recon_Advance = new X_ZZ_Petty_Cash_Recon_Advance(getCtx(), 0, get_TrxName());
				x_ZZ_Petty_Cash_Recon_Advance.setZZ_Petty_Cash_Advance_Hdr_ID(rs.getInt(1));
				x_ZZ_Petty_Cash_Recon_Advance.setZZ_Petty_Cash_Recon_Hdr_ID(zz_Petty_Cash_Recon_Hdr_ID);
				x_ZZ_Petty_Cash_Recon_Advance.setAmount(rs.getBigDecimal(2));
				x_ZZ_Petty_Cash_Recon_Advance.saveEx();
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
