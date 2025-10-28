package za.co.ntier.wf.callout;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MTable;
import org.compiere.model.PO;

import za.co.ntier.wf.process.ProcessRunner;

/**
 * Column callout for ZZ_DocAction (2-char list / button).
 * Converts UI selection into a process invocation of Value='ZZ_WF_Run'.
 */
public class CalloutRunWorkflow implements IColumnCallout {

    private static final ThreadLocal<Boolean> reentry =
            ThreadLocal.withInitial(() -> Boolean.FALSE);

    @Override
    public String start(Properties ctx, int windowNo, GridTab tab, GridField field,
                        Object value, Object oldValue) {
        if (value == null) return null;
        if (Boolean.TRUE.equals(reentry.get())) return null;
        if (tab.getRecord_ID() <= 0) return null; // not saved yet

        try {
            reentry.set(Boolean.TRUE);

            final int tableId  = tab.getAD_Table_ID();
            final int recordId = tab.getRecord_ID();
            final String sel = value.toString().trim(); // e.g. RQ/AP/RJ

            // Build params for the router process
            Map<String,String> params = null;
            if ("AP".equalsIgnoreCase(sel)) {
                params = new HashMap<>(); params.put("Approve", "Y");
            } else if ("RJ".equalsIgnoreCase(sel)) {
                params = new HashMap<>(); params.put("Approve", "N");
            } // else null => request/advance

            // Run the workflow router process (Value='ZZ_WF_Run')
            ProcessRunner.runByValue(org.compiere.util.Env.getCtx(),
                                     "ZZ_WF_Run", tableId, recordId, params);

            // Refresh the field from DB to avoid stale UI state
            PO po = MTable.get(ctx, tableId).getPO(recordId, null);
            String newAction = po.get_ValueAsString("ZZ_DocAction");
            if (newAction != null && !newAction.equals(value.toString())) {
                tab.setValue("ZZ_DocAction", newAction);
            }
            return null;
        } catch (Exception ex) {
            throw new AdempiereException(ex.getMessage(), ex);
        } finally {
            reentry.set(Boolean.FALSE);
        }
    }
}
