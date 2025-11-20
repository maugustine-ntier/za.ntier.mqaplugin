package za.ntier.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ProcessUtil;
import org.compiere.model.MColumn;
import org.compiere.model.MProcess;
import org.compiere.model.MProcessPara;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Trx;
import org.compiere.util.Util;

/**
 * IncreaseNameLengthForTables
 *
 * For each table name in TableNamesCSV:
 *   - Find AD_Column "Name"
 *   - Set FieldLength = NameLength (default 300, if shorter)
 *   - Call ColumnSync for that column to alter the DB column definition
 */
@org.adempiere.base.annotation.Process(name = "za.ntier.process.IncreaseNameLengthForTables")
public class IncreaseNameLengthForTables extends SvrProcess {

    private String p_tableNamesCSV;
    private int p_nameLength = 300;   // default

    @Override
    protected void prepare() {
        for (ProcessInfoParameter para : getParameter()) {
            String name = para.getParameterName();
            if ("TableNamesCSV".equals(name)) {
                p_tableNamesCSV = para.getParameterAsString();
            } else if ("NameLength".equals(name)) {
                Integer len = para.getParameterAsInt();
                if (len != null && len > 0) {
                    p_nameLength = len;
                }
            } else {
                MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para);
            }
        }

        // defensive default
        if (p_nameLength <= 0) {
            p_nameLength = 300;
        }
    }

    @Override
    protected String doIt() throws Exception {

        if (Util.isEmpty(p_tableNamesCSV, true)) {
            throw new AdempiereException("Parameter TableNamesCSV is empty");
        }

        // Find ColumnSync process by class name
        MProcess columnSyncProcess = new Query(getCtx(), MProcess.Table_Name,
                "ClassName=?", get_TrxName())
                .setParameters("org.compiere.process.ColumnSync")
                .first();

        if (columnSyncProcess == null) {
            throw new AdempiereException("ColumnSync process (org.compiere.process.ColumnSync) not found");
        }

        String columnSyncClassName = columnSyncProcess.getClassname();
        if (Util.isEmpty(columnSyncClassName, true)) {
            throw new AdempiereException("ColumnSync process has empty ClassName");
        }

        int columnSyncProcessId = columnSyncProcess.getAD_Process_ID();

        List<String> updatedTables = new ArrayList<>();
        List<String> skippedTables  = new ArrayList<>();
        List<String> errorTables    = new ArrayList<>();

        // Split CSV, keep casing
        List<String> tableNames = new ArrayList<>();
        Arrays.stream(p_tableNamesCSV.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .forEach(tableNames::add);

        for (String tableName : tableNames) {
            String tName = tableName;

            // Find AD_Table
            MTable table = new Query(getCtx(), MTable.Table_Name,
                    "TableName=?", get_TrxName())
                    .setParameters(tName)
                    .first();

            if (table == null) {
                addLog("Table " + tName + " not found in AD_Table, skipping");
                skippedTables.add(tName + " (no AD_Table)");
                continue;
            }

            // Find Name column
            MColumn nameCol = new Query(getCtx(), MColumn.Table_Name,
                    "AD_Table_ID=? AND ColumnName=?", get_TrxName())
                    .setParameters(table.getAD_Table_ID(), "Name")
                    .first();

            if (nameCol == null) {
                addLog("Table " + tName + " has no Name column in AD_Column, skipping");
                skippedTables.add(tName + " (no Name column)");
                continue;
            }

            try {
                int currentLen = nameCol.getFieldLength();
                if (currentLen >= p_nameLength) {
                    addLog("Table " + tName + " Name column already length " + currentLen + " >= " + p_nameLength + ", skipping AD update");
                } else {
                    nameCol.setFieldLength(p_nameLength);
                    nameCol.saveEx();
                    addLog("Updated AD_Column " + tName + ".Name length from " + currentLen + " to " + p_nameLength);
                }

                // Sync to DB using ColumnSync
                ProcessInfo pi = new ProcessInfo("ColumnSync-" + tName + ".Name", columnSyncProcessId);
                pi.setAD_Client_ID(getAD_Client_ID());
                pi.setAD_User_ID(getAD_User_ID());
                pi.setClassName(columnSyncClassName);
                pi.setRecord_ID(nameCol.getAD_Column_ID());

                boolean ok = ProcessUtil.startJavaProcess(getCtx(), pi, Trx.get(get_TrxName(), false));

                if (!ok || pi.isError()) {
                    String summary = pi.getSummary();
                    addLog("Error syncing column " + tName + ".Name: " + summary);
                    errorTables.add(tName + " (" + summary + ")");
                } else {
                    addLog("Synced DB column " + tName + ".Name to length " + p_nameLength);
                    updatedTables.add(tName);
                }

            } catch (Exception e) {
                addLog("Exception updating " + tName + ".Name: " + e.getMessage());
                errorTables.add(tName + " (" + e.getMessage() + ")");
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Target length: ").append(p_nameLength)
              .append(" | Tables updated: ").append(updatedTables.size());
        if (!updatedTables.isEmpty()) {
            result.append(" [").append(String.join(", ", updatedTables)).append("]");
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

