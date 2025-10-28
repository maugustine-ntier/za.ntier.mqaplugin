package za.co.ntier.wf.process;

import java.util.Map;
import java.util.Properties;

import org.adempiere.util.ProcessUtil;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfo;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

/** Run server processes via ProcessUtil + MPInstance (no ProcessCtl). */
public final class ProcessRunner {
    private static final CLogger log = CLogger.getCLogger(ProcessRunner.class);
    private ProcessRunner() {}

    public static void runByValue(Properties ctx, String value, int tableId, int recordId, Map<String,String> params) {
        int adProcessId = DB.getSQLValue(null, "SELECT AD_Process_ID FROM AD_Process WHERE Value=?", value);
        if (adProcessId <= 0) throw new IllegalStateException("Process not found: " + value);
        MProcess proc = MProcess.get(ctx, adProcessId);
        MPInstance pinstance = new MPInstance(proc, recordId);
        pinstance.saveEx();
        if (params != null) {
            for (Map.Entry<String,String> e : params.entrySet()) {
                MPInstancePara p = new MPInstancePara(pinstance,10);
                p.setParameter(e.getKey(),e.getValue());
                p.saveEx();
            }
        }
        ProcessInfo pi = new ProcessInfo(proc.getName(), adProcessId, tableId, recordId);
        pi.setAD_PInstance_ID(pinstance.getAD_PInstance_ID());
        pi.setAD_Client_ID(Env.getAD_Client_ID(ctx));
        pi.setAD_User_ID(Env.getAD_User_ID(ctx));
        pi.setClassName(proc.getClassname());

        Trx trx = Trx.get(Trx.createTrxName("WFRun_" + value), true);
        trx.setDisplayName(ProcessRunner.class.getName()+"#runByValue");
        try {
            boolean ok = ProcessUtil.startJavaProcess(ctx, pi, trx, true);
            if (!ok || pi.isError()) {
                throw new RuntimeException(pi.getSummary());
            }
        } finally {
            // ProcessUtil closes trx when managed=true
        }
    }
}
