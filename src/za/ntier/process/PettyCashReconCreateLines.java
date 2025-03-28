package za.ntier.process;



import org.compiere.model.MProcessPara;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import za.ntier.models.MZZPettyCashClaimLine;
import za.ntier.models.X_ZZ_Petty_Cash_Advance_Hdr;
import za.ntier.models.X_ZZ_Petty_Cash_Claim_Line;
import za.ntier.models.X_ZZ_Petty_Cash_Recon_Advance;


@org.adempiere.base.annotation.Process
public class PettyCashReconCreateLines extends SvrProcess {	
	

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;			
			else
				MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para[i]);
		}

	}

	@Override
	protected String doIt() throws Exception {
		int zz_Petty_Cash_Recon_Hdr_ID = getRecord_ID();
		int claim_IDs [] = PO.getAllIDs(X_ZZ_Petty_Cash_Claim_Line.Table_Name, "Not exists (Select 'x' from ZZ_Petty_Cash_Claim_Line cl "
				+ " join ZZ_Petty_Cash_Claim_Hdr ch on cl.ZZ_Petty_Cash_Claim_Hdr_ID = cl. ZZ_Petty_Cash_Claim_Hdr_ID where cl.ZZ_Petty_Cash_Advance_ID is null or "
				+ " cl.ZZ_Petty_Cash_Advance_ID <= 0)", get_TrxName());
		for (int claim_ID : claim_IDs) {
			MZZPettyCashClaimLine mZZPettyCashClaimLine = new MZZPettyCashClaimLine(getCtx(), claim_ID, get_TrxName());
			mZZPettyCashClaimLine.setZZ_Petty_Cash_Recon_Hdr_ID(zz_Petty_Cash_Recon_Hdr_ID);
			mZZPettyCashClaimLine.saveEx();
		}
		int advance_IDs [] = PO.getAllIDs(X_ZZ_Petty_Cash_Advance_Hdr.Table_Name, "ZZ_Petty_Cash_Advance_Hdr_ID not in ("
				+ "Select ch.ZZ_Petty_Cash_Advance_Hdr_ID from ZZ_Petty_Cash_Claim_Hdr ch where ch.ZZ_Petty_Cash_Advance_Hdr_ID is not null)", get_TrxName());
		for (int advance_ID : advance_IDs) {
			X_ZZ_Petty_Cash_Recon_Advance x_ZZ_Petty_Cash_Recon_Advance = new X_ZZ_Petty_Cash_Recon_Advance(getCtx(),0,get_TrxName());
			x_ZZ_Petty_Cash_Recon_Advance.setZZ_Petty_Cash_Advance_Hdr_ID(advance_ID);
			x_ZZ_Petty_Cash_Recon_Advance.setZZ_Petty_Cash_Recon_Hdr_ID(zz_Petty_Cash_Recon_Hdr_ID);
			x_ZZ_Petty_Cash_Recon_Advance.saveEx();
		}
		return null;
	}



}
