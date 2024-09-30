package za.ntier.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import za.co.ntier.utils.CreateMessagingService;

@org.adempiere.base.annotation.Process
public class ZZ_Create_Message_Service extends SvrProcess {


	
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		String sid = CreateMessagingService.create(Env.getAD_Client_ID(getCtx()));
		return "Created SID " + sid;
	}



}
