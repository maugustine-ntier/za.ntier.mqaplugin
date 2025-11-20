package za.ntier.process;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.compiere.util.Util;

/**
 * SyncTablesToDB
 *
 * For each table name in TableNamesCSV:
 *  - If the physical table does NOT exist:
 *      * Call ColumnSync once on AD_Client_ID
 *        -> ColumnSync creates the table and ALL columns.
 *  - If the physical table DOES exist:
 *      * Load physical column names from DB metadata
 *      * For each AD_Column on that table (non-virtual):
 *          - If column exists in DB: skip
 *          - If column is missing: call ColumnSync for that AD_Column_ID
 *
 * ColumnSync behaviour:
 *  - If physical table doesn't exist: creates table + all columns
 *  - If table exists: only creates/alters the given column
 *
 * We only call ColumnSync for missing columns to AVOID altering existing ones.
 */
@org.adempiere.base.annotation.Process(name="za.ntier.process.SyncTablesToDB")
public class SyncTablesToDB extends SvrProcess {

    private String p_tableNamesCSV;

    @Override
    protected void prepare() {
        for (ProcessInfoParameter para : getParameter()) {
            String name = para.getParameterName();
            if ("TableNamesCSV".equals(name)) {
                p_tableNamesCSV = para.getParameterAsString();
            } else {
                // validate unexpected parameters
                MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para);
            }
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

        List<String> syncedTables = new ArrayList<>();
        List<String> skippedTables = new ArrayList<>();
        List<String> errorTables  = new ArrayList<>();

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

            // Load physical columns (names) from the database metadata
            Set<String> physicalColumns = getPhysicalColumns(table.getTableName());

            boolean tableExists = !physicalColumns.isEmpty();

            try {
                if (!tableExists) {
                    // ---- TABLE DOES NOT EXIST: behave as before ----
                    // Call ColumnSync once using AD_Client_ID -> creates table + all columns
                    MColumn adClientCol = new Query(getCtx(), MColumn.Table_Name,
                            "AD_Table_ID=? AND ColumnName=?", get_TrxName())
                            .setParameters(table.getAD_Table_ID(), "AD_Client_ID")
                            .first();

                    if (adClientCol == null) {
                        addLog("Table " + tName + " has no AD_Client_ID column in AD_Column, skipping");
                        skippedTables.add(tName + " (no AD_Client_ID)");
                        continue;
                    }

                    int adColumnId = adClientCol.getAD_Column_ID();

                    ProcessInfo pi = new ProcessInfo("ColumnSync-" + tName, columnSyncProcessId);
                    pi.setAD_Client_ID(getAD_Client_ID());
                    pi.setAD_User_ID(getAD_User_ID());
                    pi.setClassName(columnSyncClassName);
                    pi.setRecord_ID(adColumnId);

                    boolean ok = ProcessUtil.startJavaProcess(getCtx(), pi, Trx.get(get_TrxName(), false));

                    if (!ok || pi.isError()) {
                        String summary = pi.getSummary();
                        addLog("Error syncing (create table) " + tName + ": " + summary);
                        errorTables.add(tName + " (" + summary + ")");
                    } else {
                        addLog("Created table and columns for " + tName);
                        syncedTables.add(tName);
                    }
                } else {
                    // ---- TABLE EXISTS: only sync missing columns ----
                    MColumn[] cols = table.getColumns(false);
                    List<String> syncedCols = new ArrayList<>();

                    for (MColumn col : cols) {
                        // Skip virtual columns
                        if (col.isVirtualColumn()) {
                            continue;
                        }

                        String colName = col.getColumnName();
                        String colNameKey = colName.toUpperCase();

                        if (physicalColumns.contains(colNameKey)) {
                            // Column exists physically -> do NOT call ColumnSync (avoid ALTER)
                            continue;
                        }

                        // Column does not exist physically -> call ColumnSync for that column ID
                        ProcessInfo pi = new ProcessInfo("ColumnSync-" + tName + "-" + colName, columnSyncProcessId);
                        pi.setAD_Client_ID(getAD_Client_ID());
                        pi.setAD_User_ID(getAD_User_ID());
                        pi.setClassName(columnSyncClassName);
                        pi.setRecord_ID(col.getAD_Column_ID());

                        boolean ok = ProcessUtil.startJavaProcess(getCtx(), pi, Trx.get(get_TrxName(), false));

                        if (!ok || pi.isError()) {
                            String summary = pi.getSummary();
                            addLog("Error syncing column " + tName + "." + colName + ": " + summary);
                            errorTables.add(tName + "." + colName + " (" + summary + ")");
                        } else {
                            addLog("Created column " + tName + "." + colName);
                            syncedCols.add(colName);
                        }
                    }

                    if (!syncedCols.isEmpty()) {
                        syncedTables.add(tName + " (cols: " + String.join(", ", syncedCols) + ")");
                    } else {
                        // Nothing to do for this table (all columns already existed)
                        skippedTables.add(tName + " (all columns exist)");
                    }
                }

            } catch (Exception e) {
                addLog("Exception syncing " + tName + ": " + e.getMessage());
                errorTables.add(tName + " (" + e.getMessage() + ")");
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Synced tables: ").append(syncedTables.size());
        if (!syncedTables.isEmpty()) {
            result.append(" [").append(String.join(", ", syncedTables)).append("]");
        }

        if (!skippedTables.isEmpty()) {
            result.append(" - Skipped: [").append(String.join(", ", skippedTables)).append("]");
        }

        if (!errorTables.isEmpty()) {
            result.append(" - Errors: [").append(String.join(", ", errorTables)).append("]");
        }

        return result.toString();
    }

    /**
     * Returns a set of physical column names for a given table name.
     * Names are stored uppercase for easy case-insensitive matching.
     * If the set is empty, the table does not exist physically.
     */
    private Set<String> getPhysicalColumns(String tableName) {
        Set<String> columns = new HashSet<>();

        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();
            DatabaseMetaData md = conn.getMetaData();
            String catalog = DB.getDatabase().getCatalog();
            String schema  = DB.getDatabase().getSchema();
            String dbTableName = tableName;

            if (md.storesUpperCaseIdentifiers()) {
                dbTableName = dbTableName.toUpperCase();
            } else if (md.storesLowerCaseIdentifiers()) {
                dbTableName = dbTableName.toLowerCase();
            }

            rs = md.getColumns(catalog, schema, dbTableName, null);
            while (rs.next()) {
                String colName = rs.getString("COLUMN_NAME");
                if (colName != null) {
                    columns.add(colName.toUpperCase());
                }
            }
        } catch (Exception e) {
            addLog("Error reading DB metadata for table " + tableName + ": " + e.getMessage());
        } finally {
            DB.close(rs);
            rs = null;
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ignore) {}
            }
        }

        return columns;
    }
}
