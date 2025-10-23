package za.ntier.process;

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import za.ntier.service.LevyBatchCreationService;

@org.adempiere.base.annotation.Process(name="za.ntier.process.CreateInvoiceBatchFromLevyFiles")
public class CreateInvoiceBatchFromLevyFiles extends SvrProcess {

    private int p_C_Charge_ID = 0;
    private int p_C_DocType_ID = 0;
    private Timestamp p_DateDoc = null;
    private int m_Record_ID = 0;

    @Override
    protected void prepare() {
        for (ProcessInfoParameter p : getParameter()) {
            if (p.getParameter() == null) continue;
            switch (p.getParameterName()) {
                case "C_Charge_ID": p_C_Charge_ID = p.getParameterAsInt(); break;
                case "C_DocType_ID": p_C_DocType_ID = p.getParameterAsInt(); break;
                case "DateDoc": p_DateDoc = (Timestamp) p.getParameter(); break;
                default: /* ignore */
            }
        }
        m_Record_ID = getRecord_ID();
        if (m_Record_ID <= 0) throw new AdempiereException("No ZZ_Monthly_Levy_Files_Hdr record selected.");
        if (p_DateDoc == null) p_DateDoc = new Timestamp(System.currentTimeMillis());
    }

    @Override
    protected String doIt() throws Exception {
        final Properties ctx = getCtx();
        final int adClientId = Env.getAD_Client_ID(ctx);

        // Resolve currency if needed (service also checks/fallbacks)
        MClient client = MClient.get(ctx);

        LevyBatchCreationService svc = new LevyBatchCreationService(
                ctx, get_TrxName(), getAD_User_ID(), client.getC_Currency_ID(),
                p_C_Charge_ID, p_C_DocType_ID, p_DateDoc
        );

        return svc.createBatchesFromHeader(m_Record_ID);
    }
}
