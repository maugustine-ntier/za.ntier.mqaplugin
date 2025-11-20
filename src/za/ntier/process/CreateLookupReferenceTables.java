package za.ntier.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ProcessUtil;
import org.compiere.model.MProcess;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Trx;
import org.compiere.util.Util;

/**
 * CreateLookupReferenceTables
 *
 * Reads a CSV list of table names (with correct Idempiere casing),
 * checks if each table exists, and for non-existing ones calls the
 * standard CreateTable process, ensuring that Value and Name columns
 * are created.
 *
 * At the end it returns a message listing:
 *  - how many new tables were created
 *  - which tables already existed
 */
@org.adempiere.base.annotation.Process(name="za.ntier.process.CreateLookupReferenceTables")
public class CreateLookupReferenceTables extends SvrProcess {

    private String p_tableNamesCSV;

    @Override
    protected void prepare() {
        for (ProcessInfoParameter para : getParameter()) {
            String name = para.getParameterName();
            if ("TableNamesCSV".equals(name)) {
                p_tableNamesCSV = para.getParameterAsString();
            } else {
                // ignore other params
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        if (Util.isEmpty(p_tableNamesCSV, true)) {
            throw new AdempiereException("Parameter TableNamesCSV is empty");
        }

        MProcess createTableProcess = new Query(getCtx(), MProcess.Table_Name,
                "AD_Process_ID=?", get_TrxName())
                .setParameters(200134) // or use the value you found dynamically
                .first();

        if (createTableProcess == null) {
            throw new AdempiereException("CreateTable process (AD_Process_ID=200134) not found");
        }

        String createTableClassName = createTableProcess.getClassname();
        if (Util.isEmpty(createTableClassName, true)) {
            throw new AdempiereException("CreateTable process has empty ClassName");
        }

        int createTableProcessId = createTableProcess.getAD_Process_ID();


        List<String> alreadyExisting = new ArrayList<>();
        List<String> created = new ArrayList<>();

        // Split CSV, keep original casing
        List<String> tableNames = new ArrayList<>();
        Arrays.stream(p_tableNamesCSV.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .forEach(tableNames::add);

        for (String tableName : tableNames) {

            String tName = tableName; // keep casing exactly as user typed

            // Case-insensitive existence check on AD_Table
            MTable existing = new Query(getCtx(), MTable.Table_Name,
                    "UPPER(TableName)=UPPER(?)", get_TrxName())
                    .setParameters(tName)
                    .first();

            if (existing != null) {
                // store the real name from AD_Table (in case casing differs)
                alreadyExisting.add(existing.getTableName());
                continue;
            }

            // Build parameters for CreateTable
            List<ProcessInfoParameter> params = new ArrayList<>();

            params.add(new ProcessInfoParameter("TableName", tName, null, null, null));
            params.add(new ProcessInfoParameter("Name", tName, null, null, null));
            params.add(new ProcessInfoParameter("Description", tName + " reference table", null, null, null));

            // Client+Org = "3"
            params.add(new ProcessInfoParameter("AccessLevel", "3", null, null, null));

            // Use a suitable EntityType for your plugin (e.g. "U" or "NT")
            String entityType = "U";
            params.add(new ProcessInfoParameter("EntityType", entityType, null, null, null));

            // Ensure key column, Value, and Name are created
            params.add(new ProcessInfoParameter("IsCreateKeyColumn", "Y", null, null, null));
            params.add(new ProcessInfoParameter("IsCreateColValue", "Y", null, null, null));
            params.add(new ProcessInfoParameter("IsCreateColName", "Y", null, null, null));

            ProcessInfoParameter[] piParams = params.toArray(new ProcessInfoParameter[0]);
            
            
            ProcessInfo pi = new ProcessInfo("CreateTable-" + tName, createTableProcessId);
            pi.setAD_Client_ID(getAD_Client_ID());
            pi.setAD_User_ID(getAD_User_ID());
            pi.setClassName(createTableClassName);   // <<< THIS IS THE IMPORTANT LINE
            pi.setParameter(piParams);

            boolean ok = ProcessUtil.startJavaProcess(getCtx(), pi, Trx.get(get_TrxName(),false));


         

            if (!ok || pi.isError()) {
                throw new AdempiereException("Error creating table " + tName + ": " + pi.getSummary());
            }

            created.add(tName);
        }

        StringBuilder result = new StringBuilder();
        result.append("Created tables: ").append(created.size());
        if (!created.isEmpty()) {
            result.append(" [").append(String.join(", ", created)).append("]");
        }

        if (!alreadyExisting.isEmpty()) {
            result.append(" - Already existing: [")
                  .append(String.join(", ", alreadyExisting))
                  .append("]");
        }

        return result.toString();
    }
}
