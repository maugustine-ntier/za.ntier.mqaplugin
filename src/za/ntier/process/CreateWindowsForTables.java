package za.ntier.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ProcessUtil;
import org.compiere.model.MProcess;
import org.compiere.model.MProcessPara;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.model.MWindow;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Trx;
import org.compiere.util.Util;

/**
 * CreateWindowsForTables
 *
 * For each table name in TableNamesCSV:
 *   - If AD_Table exists and AD_Window_ID is empty:
 *       * Call CreateWindowFromTable to create:
 *           - a new window
 *           - a tab + fields
 *           - a menu item
 *   - If AD_Window_ID already set:
 *       * Skip (assume window/menu already exist)
 */
@org.adempiere.base.annotation.Process(name = "za.ntier.process.CreateWindowsForTables")
public class CreateWindowsForTables extends SvrProcess {

    private String p_tableNamesCSV;

    @Override
    protected void prepare() {
        for (ProcessInfoParameter para : getParameter()) {
            String name = para.getParameterName();
            if ("TableNamesCSV".equals(name)) {
                p_tableNamesCSV = para.getParameterAsString();
            } else {
                MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para);
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        if (Util.isEmpty(p_tableNamesCSV, true)) {
            throw new AdempiereException("Parameter TableNamesCSV is empty");
        }

        // Find CreateWindowFromTable process by class name
        MProcess createWindowProcess = new Query(getCtx(), MProcess.Table_Name,
                "ClassName=?", get_TrxName())
                .setParameters("org.compiere.process.CreateWindowFromTable")
                .first();

        if (createWindowProcess == null) {
            throw new AdempiereException("CreateWindowFromTable process (org.compiere.process.CreateWindowFromTable) not found");
        }

        String createWindowClassName = createWindowProcess.getClassname();
        if (Util.isEmpty(createWindowClassName, true)) {
            throw new AdempiereException("CreateWindowFromTable process has empty ClassName");
        }

        int createWindowProcessId = createWindowProcess.getAD_Process_ID();

        List<String> createdWindows = new ArrayList<>();
        List<String> skippedTables  = new ArrayList<>();
        List<String> errorTables    = new ArrayList<>();

        // Split CSV, keep user casing (ZZ_Learning_Programme_Ref etc.)
        List<String> tableNames = new ArrayList<>();
        Arrays.stream(p_tableNamesCSV.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .forEach(tableNames::add);

        for (String tableName : tableNames) {
            String tName = tableName;

            // Lookup AD_Table
            MTable table = new Query(getCtx(), MTable.Table_Name,
                    "TableName=?", get_TrxName())
                    .setParameters(tName)
                    .first();

            if (table == null) {
                addLog("Table " + tName + " not found in AD_Table, skipping");
                skippedTables.add(tName + " (no AD_Table)");
                continue;
            }

            // If table already linked to a window, skip creating a new one
            if (table.getAD_Window_ID() > 0) {
                MWindow existingWindow = new MWindow(getCtx(), table.getAD_Window_ID(), get_TrxName());
                String winName = existingWindow.get_ID() > 0 ? existingWindow.getName() : "ID=" + table.getAD_Window_ID();
                addLog("Table " + tName + " already has AD_Window_ID=" + table.getAD_Window_ID()
                        + " (" + winName + "), skipping");
                skippedTables.add(tName + " (window already exists: " + winName + ")");
                continue;
            }

            try {
                // Build parameters for CreateWindowFromTable
                List<ProcessInfoParameter> params = new ArrayList<>();

                // WindowType = 'M' (Maintain)
                params.add(new ProcessInfoParameter("WindowType", MWindow.WINDOWTYPE_Maintain, null, null, null));
                // Not a sales transaction window
                params.add(new ProcessInfoParameter("IsSOTrx", "N", null, null, null));
                // New window
                params.add(new ProcessInfoParameter("IsNewWindow", "Y", null, null, null));
                // No existing window ID
                params.add(new ProcessInfoParameter("AD_Window_ID", Integer.valueOf(0), null, null, null));
                // Header tab level
                params.add(new ProcessInfoParameter("TabLevel", Integer.valueOf(0), null, null, null));
                // Create a menu entry
                params.add(new ProcessInfoParameter("IsCreateMenu", "Y", null, null, null));
                // No link column (header tab)
                params.add(new ProcessInfoParameter("AD_Column_ID", Integer.valueOf(0), null, null, null));

                ProcessInfoParameter[] piParams = params.toArray(new ProcessInfoParameter[0]);

                ProcessInfo pi = new ProcessInfo("CreateWindowFromTable-" + tName, createWindowProcessId);
                pi.setAD_Client_ID(getAD_Client_ID());
                pi.setAD_User_ID(getAD_User_ID());
                pi.setClassName(createWindowClassName);
                pi.setRecord_ID(table.getAD_Table_ID());   // <- important: table is the "record"
                pi.setParameter(piParams);

                boolean ok = ProcessUtil.startJavaProcess(getCtx(), pi, Trx.get(get_TrxName(), false));

                if (!ok || pi.isError()) {
                    String summary = pi.getSummary();
                    addLog("Error creating window for " + tName + ": " + summary);
                    errorTables.add(tName + " (" + summary + ")");
                } else {
                    addLog("Created window and menu for table " + tName);
                    createdWindows.add(tName);
                }

            } catch (Exception e) {
                addLog("Exception creating window for " + tName + ": " + e.getMessage());
                errorTables.add(tName + " (" + e.getMessage() + ")");
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Windows created: ").append(createdWindows.size());
        if (!createdWindows.isEmpty()) {
            result.append(" [").append(String.join(", ", createdWindows)).append("]");
        }

        if (!skippedTables.isEmpty()) {
            result.append(" - Skipped: [").append(String.join(", ", skippedTables)).append("]");
        }

        if (!errorTables.isEmpty()) {
            result.append(" - Errors: [").append(String.join(", ", errorTables)).append("]");
        }

        return result.toString();
    }
}

