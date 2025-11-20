package za.ntier.process;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProcessPara;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 * ImportRefLookupsFromSheet
 *
 * Given a Google Sheet URL:
 *   - Downloads it as CSV
 *   - For each column:
 *       * Resolves table name as "ZZ_<Header>_Ref"
 *       * Finds MTable
 *       * Imports non-empty cells as lookup rows (Value, Name)
 *       * Avoids duplicates by Name
 *
 * Value:
 *   - 4 characters, numeric, zero-padded: 0001, 0002, ...
 *   - Sequence is per table, starting from max(Value)+1 if any.
 */
@org.adempiere.base.annotation.Process(name = "za.ntier.process.ImportRefLookupsFromSheet")
public class ImportRefLookupsFromSheet extends SvrProcess {

    private String p_googleSheetUrl;
    private int p_AD_Org_ID = 0;

    // Per-table sequence cache: AD_Table_ID -> current max INT value
    private final Map<Integer, Integer> valueSeqByTableId = new HashMap<>();

    @Override
    protected void prepare() {
        for (ProcessInfoParameter para : getParameter()) {
            String name = para.getParameterName();
            if ("GoogleSheetURL".equals(name)) {
                p_googleSheetUrl = para.getParameterAsString();
            } else if ("AD_Org_ID".equals(name)) {
                p_AD_Org_ID = para.getParameterAsInt();
            } else {
                MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para);
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        if (Util.isEmpty(p_googleSheetUrl, true)) {
            throw new AdempiereException("Parameter GoogleSheetURL is empty");
        }

        if (p_AD_Org_ID <= 0) {
            p_AD_Org_ID = Env.getAD_Org_ID(getCtx()); // fall back to current org
        }

        // Build export CSV URL from the sheet link
        String csvUrl = buildCsvExportUrl(p_googleSheetUrl);

        addLog("Downloading CSV from: " + csvUrl);

        List<List<String>> rows = downloadAndParseCsv(csvUrl);
        if (rows.isEmpty()) {
            throw new AdempiereException("No data rows found in sheet");
        }

        // First row is header
        List<String> headers = rows.get(0);
        int columnCount = headers.size();

        // For each header, resolve MTable (ZZ_<Header>_Ref)
        MTable[] tablesByColumn = new MTable[columnCount];

        for (int col = 0; col < columnCount; col++) {
            String header = headers.get(col);
            if (Util.isEmpty(header, true)) {
                continue; // ignore blank header columns
            }

            String tableName = buildTableNameFromHeader(header);
            MTable table = MTable.get(getCtx(), tableName);

            if (table == null || table.getAD_Table_ID() <= 0) {
                addLog("No AD_Table found for header '" + header + "', expected table " + tableName + " - skipping this column");
                tablesByColumn[col] = null;
            } else {
                addLog("Column '" + header + "' -> table " + tableName);
                tablesByColumn[col] = table;
            }
        }

        int totalInserted = 0;
        int totalSkippedExisting = 0;

        // Process data rows
        for (int r = 1; r < rows.size(); r++) {
            List<String> row = rows.get(r);
            // Pad row if shorter than header count
            while (row.size() < columnCount) {
                row.add("");
            }

            for (int col = 0; col < columnCount; col++) {
                MTable table = tablesByColumn[col];
                if (table == null) {
                    continue; // no table mapped for this column
                }

                String cell = row.get(col);
                if (cell == null) {
                    continue;
                }
                String valueText = cell.trim();
                if (valueText.isEmpty()) {
                    continue;
                }

                int adClientId = Env.getAD_Client_ID(getCtx());

                // Check if record already exists (matching Name)
                boolean exists = new Query(getCtx(), table.getTableName(),
                        "Name=? AND AD_Client_ID=?", get_TrxName())
                        .setParameters(valueText, adClientId)
                        .match();

                if (exists) {
                    totalSkippedExisting++;
                    continue;
                }

                // Create new record
                PO po = table.getPO(0, get_TrxName());
                if (po == null) {
                    addLog("Cannot create PO for table " + table.getTableName() + ", skipping value '" + valueText + "'");
                    continue;
                }

                po.set_ValueOfColumn("AD_Client_ID",adClientId);
                po.setAD_Org_ID(p_AD_Org_ID);

                // Value = 4-char sequence: 0001, 0002, ...
                String valueCode = getNextValueCode(table, adClientId);
                if (table.getColumnIndex("Value") >= 0) {
                    po.set_ValueOfColumn("Value", valueCode);
                }
                if (table.getColumnIndex("Name") >= 0) {
                    po.set_ValueOfColumn("Name", valueText);
                }

                if (table.getColumnIndex("IsActive") >= 0) {
                    po.set_ValueOfColumn("IsActive", "Y");
                }

                po.saveEx();
                totalInserted++;
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Imported ").append(totalInserted).append(" rows");
        result.append(", skipped existing: ").append(totalSkippedExisting);

        return result.toString();
    }

    /**
     * Given the Google Sheets URL, attempt to build the CSV export URL.
     * Typical pattern: replace "/edit..." with "/export?format=csv".
     */
    private String buildCsvExportUrl(String sheetUrl) {
        String url = sheetUrl.trim();
        // Basic heuristic for standard sheet link
        // e.g. https://docs.google.com/spreadsheets/d/<ID>/edit#gid=0
        if (url.contains("/edit")) {
            int pos = url.indexOf("/edit");
            url = url.substring(0, pos) + "/export?format=csv";
        }
        // If user already gave an export URL, we leave it as is.
        return url;
    }

    /**
     * Builds the AD table name from a column header.
     * E.g. "Learning_Programme" -> "ZZ_Learning_Programme_Ref"
     */
    private String buildTableNameFromHeader(String header) {
        // Trim and normalise spaces
        String h = header.trim();

        // Replace spaces and illegal chars with underscore, but keep existing underscores
        h = h.replaceAll("[^A-Za-z0-9_]", "_");

        // Collapse multiple underscores
        h = h.replaceAll("_+", "_");

        // Ensure it starts with a letter or underscore (for safety)
        if (!h.isEmpty() && Character.isDigit(h.charAt(0))) {
            h = "_" + h;
        }

        return "ZZ_" + h + "_Ref";
    }

    /**
     * Get next 4-digit zero-padded Value for a table, per client.
     * Reads current max(Value) once per table and caches it.
     */
    private String getNextValueCode(MTable table, int adClientId) {
        int tableId = table.getAD_Table_ID();
        Integer current = valueSeqByTableId.get(tableId);

        if (current == null) {
            // Read max(Value) from DB
            String sql = "SELECT MAX(Value) FROM " + table.getTableName()
                       + " WHERE AD_Client_ID=?";
            String maxValue = DB.getSQLValueStringEx(get_TrxName(), sql, adClientId);
            int start = 0;
            if (maxValue != null && !maxValue.isEmpty()) {
                try {
                    start = Integer.parseInt(maxValue);
                } catch (NumberFormatException e) {
                    // If existing values are not numeric, we just restart from 0
                    start = 0;
                }
            }
            current = start;
        }

        current = current + 1;
        valueSeqByTableId.put(tableId, current);

        // 4-digit zero-padded, e.g. 0001
        return String.format("%04d", current);
    }

    /**
     * Download CSV from URL and parse into rows/columns (simple CSV parser supporting quotes).
     */
    private List<List<String>> downloadAndParseCsv(String csvUrl) throws Exception {
        List<List<String>> rows = new ArrayList<>();

        URL url = new URL(csvUrl);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(60000);

        try (InputStream is = conn.getInputStream();
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                List<String> parsed = parseCsvLine(line);
                rows.add(parsed);
            }
        }

        return rows;
    }

    /**
     * Very simple CSV line parser with quote support.
     * Handles:
     *   - "value, with, commas"
     *   - ""escaped quotes""
     */
    private List<String> parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        if (line == null) {
            return result;
        }

        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '\"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                    // Escaped quote
                    current.append('\"');
                    i++; // skip next
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());

        return result;
    }
}

