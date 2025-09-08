package za.ntier.process;



import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.util.ProcessUtil;
import org.compiere.model.MProcessPara;
import org.compiere.model.MSysConfig;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import za.ntier.models.MZZPettyCashAdvanceHdr;
import za.ntier.models.MZZPettyCashClaimHdr;
import za.ntier.models.X_ZZ_Petty_Cash_Claim_Hdr;
import za.ntier.models.X_ZZ_Petty_Cash_Claim_Line;
import za.ntier.utils.Notifications;


@org.adempiere.base.annotation.Process
public class PettyCashClaimRequest extends SvrProcess {	
	private static final String YOUR_APPLICATION_WAS_APPROVED_PETTY_CASH_CLAIM_REQUEST = "Your application was approved, Petty Cash Claim Request : ";
	private static final String YOUR_APPLICATION_WAS_REJECTED_PETTY_CASH_CLAIM_REQUEST = "Your application was rejected, Petty Cash Claim Request : ";
	private static final CLogger log = CLogger.getCLogger(ProcessUtil.class);
	private static final String PLEASE_APPROVE_OR_REJECT_THE_PETTY_CASH_CLAIM_REQUEST = "Please Approve or Reject the Petty Cash Claim Request : ";
	private static final String PETTY_CASH_CLAIM_REQUEST = "Petty Cash Claim Request ";
	public final static int MESSAGE_NEW_PETTYCASH_APP = 1000015;
	public final static int MESSAGE_LM_APPROVED_PETTYCASH_APP = 1000010;
	public final static int SNR_ADMIN_FIN_ROLE_ID = 1000003;
	public final static int FROM_EMAIL_USER_ID = MSysConfig.getIntValue("FROM_EMAIL_USER_ID",1000011);
	String p_ZZ_Approve_Rej_LM = "";
	String p_ZZ_Approve_Rej_SAF = "";
	int zz_Petty_Cash_claim_Hdr_ID = 0;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("ZZ_Approve_Rej_LM"))
				p_ZZ_Approve_Rej_LM = (String)para[i].getParameter();
			else if (name.equals("ZZ_Approve_Rej_SAF"))
				p_ZZ_Approve_Rej_SAF = (String)para[i].getParameter();
			else
				MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para[i]);
		}

	}

	@Override
	protected String doIt() throws Exception {
		zz_Petty_Cash_claim_Hdr_ID = getRecord_ID();
		MZZPettyCashClaimHdr mZZPettyCashClaimHdr = new MZZPettyCashClaimHdr(getCtx(),zz_Petty_Cash_claim_Hdr_ID,get_TrxName());
		int x_zz_Petty_Cash_claim_Line_IDs[] = PO.getAllIDs(X_ZZ_Petty_Cash_Claim_Line.Table_Name,MZZPettyCashClaimHdr.COLUMNNAME_ZZ_Petty_Cash_Claim_Hdr_ID + " = " + zz_Petty_Cash_claim_Hdr_ID , null);
		if (x_zz_Petty_Cash_claim_Line_IDs == null || x_zz_Petty_Cash_claim_Line_IDs.length <= 0) {
			return "@Error@Please enter at least one Petty Cash Claim Line";
		}
		if (mZZPettyCashClaimHdr.getZZ_DocAction().equals(MZZPettyCashClaimHdr.ZZ_DOCACTION_SubmitToLineManager)) {
			if (mZZPettyCashClaimHdr.getLine_Manager_ID() <= 0) {
				return "Please select a Line Manager";
			}
			mZZPettyCashClaimHdr.setZZ_DocStatus(MZZPettyCashClaimHdr.ZZ_DOCSTATUS_Submitted);
			mZZPettyCashClaimHdr.setZZ_DocAction(MZZPettyCashClaimHdr.ZZ_DOCACTION_ApproveDoNotApprove);
			mZZPettyCashClaimHdr.setZZ_Date_Submitted(new Timestamp(System.currentTimeMillis()));
			String subject = PETTY_CASH_CLAIM_REQUEST + "awaiting LM Approval";
			String message = PLEASE_APPROVE_OR_REJECT_THE_PETTY_CASH_CLAIM_REQUEST + mZZPettyCashClaimHdr.getDocumentNo();
			int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
			Notifications.sendNotification(mZZPettyCashClaimHdr.getLine_Manager_ID(),zz_Petty_Cash_claim_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Claim_Hdr.Table_ID);
		} else if (mZZPettyCashClaimHdr.getZZ_DocAction().equals(MZZPettyCashClaimHdr.ZZ_DOCACTION_ApproveDoNotApprove) && 
				mZZPettyCashClaimHdr.getZZ_DocStatus().equals(MZZPettyCashClaimHdr.ZZ_DOCSTATUS_Submitted)) {
			if (p_ZZ_Approve_Rej_LM.equals("Y")) {
				mZZPettyCashClaimHdr.setZZ_DocStatus(MZZPettyCashClaimHdr.ZZ_DOCSTATUS_InProgress);
				mZZPettyCashClaimHdr.setZZ_DocAction(MZZPettyCashClaimHdr.ZZ_DOCACTION_FinalApprovalDoNotApprove);
				mZZPettyCashClaimHdr.setZZ_Date_LM_Approved(new Timestamp(System.currentTimeMillis()));
				String subject = PETTY_CASH_CLAIM_REQUEST + "awaiting Finance Approval";
				String message = PLEASE_APPROVE_OR_REJECT_THE_PETTY_CASH_CLAIM_REQUEST + mZZPettyCashClaimHdr.getDocumentNo();
				int ad_Message_ID = MESSAGE_LM_APPROVED_PETTYCASH_APP;
				Notifications.sendNotificationToRole(SNR_ADMIN_FIN_ROLE_ID,zz_Petty_Cash_claim_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Claim_Hdr.Table_ID);
			} else {
				mZZPettyCashClaimHdr.setZZ_DocStatus(MZZPettyCashClaimHdr.ZZ_DOCSTATUS_NotApprovedByLM);
				mZZPettyCashClaimHdr.setZZ_Date_Not_Approved_by_LM(new Timestamp(System.currentTimeMillis()));
				String subject = PETTY_CASH_CLAIM_REQUEST + "Rejected by the LM";
				String message = YOUR_APPLICATION_WAS_REJECTED_PETTY_CASH_CLAIM_REQUEST + mZZPettyCashClaimHdr.getDocumentNo();
				int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
				Notifications.sendNotification(mZZPettyCashClaimHdr.getCreatedBy(),zz_Petty_Cash_claim_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Claim_Hdr.Table_ID);
			}

		} else if (mZZPettyCashClaimHdr.getZZ_DocAction().equals(MZZPettyCashClaimHdr.ZZ_DOCACTION_FinalApprovalDoNotApprove) && 
				mZZPettyCashClaimHdr.getZZ_DocStatus().equals(MZZPettyCashClaimHdr.ZZ_DOCSTATUS_InProgress)) {
			if (p_ZZ_Approve_Rej_SAF.equals("Y")) {
				mZZPettyCashClaimHdr.setZZ_DocStatus(MZZPettyCashClaimHdr.ZZ_DOCSTATUS_Completed);
				mZZPettyCashClaimHdr.setZZ_Date_Completed(new Timestamp(System.currentTimeMillis()));
				mZZPettyCashClaimHdr.setZZ_Snr_Admin_Fin_ID(Env.getAD_User_ID(getCtx()));
				int advanceID = findAdvance(mZZPettyCashClaimHdr.getZZ_Credit_Card_No(),mZZPettyCashClaimHdr.getTotalAmt());
				if (advanceID > 0) {
					mZZPettyCashClaimHdr.setZZ_Petty_Cash_Advance_Hdr_ID(advanceID);  // Link to advance for recons
					MZZPettyCashAdvanceHdr mZZPettyCashAdvanceHdr = new MZZPettyCashAdvanceHdr(getCtx(), advanceID, get_TrxName());
					BigDecimal totalAmtClaim =  getClaimTotalForAdvance(advanceID,mZZPettyCashClaimHdr.getZZ_Petty_Cash_Claim_Hdr_ID());
					BigDecimal totalAmtAdvance = (mZZPettyCashAdvanceHdr.getTotalAmt() != null) ? mZZPettyCashAdvanceHdr.getTotalAmt() : BigDecimal.ZERO;
					totalAmtClaim = totalAmtClaim.add((mZZPettyCashClaimHdr.getTotalAmt() != null) ? mZZPettyCashClaimHdr.getTotalAmt() : BigDecimal.ZERO);
					BigDecimal zz_Advance_Balance = totalAmtAdvance.subtract(totalAmtClaim);
					if (zz_Advance_Balance.compareTo(BigDecimal.ZERO) < 0) {
						zz_Advance_Balance = BigDecimal.ZERO;
					}
					mZZPettyCashClaimHdr.setZZ_Advance_Balance(zz_Advance_Balance);
					mZZPettyCashAdvanceHdr.setZZ_Advance_Balance(zz_Advance_Balance);
					mZZPettyCashAdvanceHdr.saveEx();
				}			
				String subject = PETTY_CASH_CLAIM_REQUEST + "Has been Approved by Finance";
				String message = YOUR_APPLICATION_WAS_APPROVED_PETTY_CASH_CLAIM_REQUEST + mZZPettyCashClaimHdr.getDocumentNo();
				int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
				Notifications.sendNotification(mZZPettyCashClaimHdr.getCreatedBy(),zz_Petty_Cash_claim_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Claim_Hdr.Table_ID);
			} else {
				mZZPettyCashClaimHdr.setZZ_DocStatus(MZZPettyCashClaimHdr.ZZ_DOCSTATUS_NotApprovedBySnrAdminFinance);
				mZZPettyCashClaimHdr.setZZ_Date_Not_Approved_by_Snr_Adm_Fin(new Timestamp(System.currentTimeMillis()));
				String subject = PETTY_CASH_CLAIM_REQUEST + "Rejected by Finance";
				String message = YOUR_APPLICATION_WAS_REJECTED_PETTY_CASH_CLAIM_REQUEST + mZZPettyCashClaimHdr.getDocumentNo();
				int ad_Message_ID = MESSAGE_NEW_PETTYCASH_APP;
				Notifications.sendNotification(mZZPettyCashClaimHdr.getCreatedBy(),zz_Petty_Cash_claim_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Claim_Hdr.Table_ID);
				Notifications.sendNotification(mZZPettyCashClaimHdr.getLine_Manager_ID(),zz_Petty_Cash_claim_Hdr_ID,subject,message,ad_Message_ID,getCtx(),get_TrxName(),X_ZZ_Petty_Cash_Claim_Hdr.Table_ID);
			}
		} 
		if (!mZZPettyCashClaimHdr.save()) {
			if (log.isLoggable(Level.FINE)) log.fine("Problem Saving.  Please contact Support");
			return "Problem Saving.  Please contact Support";
		}


		return null;
	}

	private int findAdvance(String zz_Credit_Card_No,BigDecimal claimAmt) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectQuery = "SELECT ca.ZZ_Petty_Cash_Advance_Hdr_ID,"
				+ " (select ZZ_Petty_Cash_Claim_Hdr_ID from ZZ_Petty_Cash_Claim_Hdr ch where ch.ZZ_Petty_Cash_Advance_Hdr_ID = ca.ZZ_Petty_Cash_Advance_Hdr_ID) as Claim_ID"
				+ " from ZZ_Petty_Cash_Advance_Hdr ca "
				+ " where ca.ZZ_DocStatus = 'CO' and ca.ZZ_Credit_Card_No = ? "
				//	+ " not exists (select 'x' from ZZ_Petty_Cash_Claim_Hdr ch where ch.ZZ_Petty_Cash_Advance_Hdr_ID = ca.ZZ_Petty_Cash_Advance_Hdr_ID)"
				+ " order by ca.ZZ_Date_Completed Desc limit 1"; 
		try {
			pstmt = DB.prepareStatement(selectQuery, get_TrxName());
			pstmt.setString(1, zz_Credit_Card_No);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					String SQL = "Select count(*) from ZZ_Petty_Cash_Recon_Claim rc where rc.ZZ_Petty_Cash_Claim_Hdr_ID = ?";
					int cnt = DB.getSQLValueEx(get_TrxName(), SQL, rs.getInt(1));
					if (cnt > 0) {
						return -1;
					} else {
						BigDecimal advanceBalance = calcAdvanceBalance(rs.getInt(1),zz_Petty_Cash_claim_Hdr_ID);
						if (advanceBalance.compareTo(claimAmt) >= 0) {
							return rs.getInt(1);
						}
					}
				} else {
					rs.getInt(1);
				}
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
		return -1;
	}
	
	// exclude the current claim
	private BigDecimal getClaimTotalForAdvance(int zz_Petty_Cash_Advance_Hdr_ID,int zz_Petty_Cash_Claim_Hdr_ID) throws Exception {
		BigDecimal claimBalance = BigDecimal.ZERO.setScale(2);
		String SQL = "Select sum(cl.totalamt) from zz_Petty_Cash_Claim_Hdr cl where cl.zz_Petty_Cash_Advance_Hdr_ID = ? and cl.zz_Petty_Cash_Claim_Hdr_ID <> ?";
		claimBalance = DB.getSQLValueBDEx(get_TrxName(), SQL, zz_Petty_Cash_Advance_Hdr_ID,zz_Petty_Cash_Claim_Hdr_ID);
		if (claimBalance == null) {
			claimBalance = BigDecimal.ZERO.setScale(1);
		}
		return claimBalance;
	}
	
	// exclude the current claim
	private BigDecimal calcAdvanceBalance(int zz_Petty_Cash_Advance_Hdr_ID,int zz_Petty_Cash_Claim_Hdr_ID) throws Exception {
		MZZPettyCashAdvanceHdr mZZPettyCashAdvanceHdr = new MZZPettyCashAdvanceHdr(getCtx(), zz_Petty_Cash_Advance_Hdr_ID, get_TrxName());
		BigDecimal advanceTot = (mZZPettyCashAdvanceHdr != null) ? mZZPettyCashAdvanceHdr.getTotalAmt() : BigDecimal.ZERO.setScale(2);
		BigDecimal advanceBalance = advanceTot.subtract(getClaimTotalForAdvance(zz_Petty_Cash_Advance_Hdr_ID,zz_Petty_Cash_Claim_Hdr_ID));		
		return advanceBalance;
	}



}
