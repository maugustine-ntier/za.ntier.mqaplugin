package za.ntier.process;

import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.util.ProcessUtil;
import org.compiere.process.ProcessInfo;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;

import za.ntier.wf.MWFProcess;
import za.ntier.wf.MWorkflow;


@org.adempiere.base.annotation.Process
public class StartNtierWF extends SvrProcess {
	private static final CLogger log = CLogger.getCLogger(ProcessUtil.class);

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		// int AD_Workflow_ID = MProcess.get(Env.getCtx(),getProcessInfo().getAD_Process_ID()).getAD_Workflow_ID();  

		int AD_Workflow_ID = 1000001;
		startWorkFlow(getCtx(), getProcessInfo(), AD_Workflow_ID);
		return null;
	}

	/**
	 * Start workflow
	 * @param ctx
	 * @param pi
	 * @param AD_Workflow_ID
	 * @return MWFProcess
	 */
	public static MWFProcess startWorkFlow(Properties ctx, ProcessInfo pi, int AD_Workflow_ID) {
		MWorkflow wf = MWorkflow.get (ctx, AD_Workflow_ID);
		MWFProcess wfProcess = wf.start(pi, pi.getTransactionName());
		if (log.isLoggable(Level.FINE)) log.fine(pi.toString());
		return wfProcess;
	}

}
