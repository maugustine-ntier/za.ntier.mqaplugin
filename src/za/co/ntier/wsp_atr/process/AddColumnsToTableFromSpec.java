package za.co.ntier.wsp_atr.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.adempiere.base.annotation.Parameter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.M_Element;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util;

@org.adempiere.base.annotation.Process(
		name = "za.co.ntier.wsp_atr.process.AddColumnsToTableFromSpec")
public class AddColumnsToTableFromSpec extends SvrProcess {

    @Parameter(name = "AD_Table_ID")
    private int adTableId;

    /**
     * Paste your spec into this parameter (multi-line text).
     * Example lines:
     *   LearningProgrammeTypeDoneID int NOT NULL,
     *   LPOtherDone nvarchar(500) NULL,
     */
    @Parameter(name = "ColumnsSpec")
    private String columnsSpec;

    @Parameter(name = "EntityType")
    private String entityType; // default "U"

    @Parameter(name = "CreateDBColumns")
    private String createDBColumnsStr; // Y/N, default Y

    private boolean createDBColumns = false;

    @Override
    protected void prepare() {
        if (Util.isEmpty(entityType, true)) entityType = "U";
        //createDBColumns = !"N".equalsIgnoreCase(createDBColumnsStr);
    }

    @Override
    protected String doIt() throws Exception {

        if (adTableId <= 0)
            throw new AdempiereException("AD_Table_ID is mandatory");

        if (Util.isEmpty(columnsSpec, true))
            throw new AdempiereException("ColumnsSpec is mandatory");

        MTable table = new MTable(getCtx(), adTableId, get_TrxName());
        if (table.get_ID() <= 0)
            throw new AdempiereException("Table not found for AD_Table_ID=" + adTableId);

        final int clientId = Env.getAD_Client_ID(getCtx());

        List<ColDef> defs = parseSpec(columnsSpec);

        int created = 0;
        int skipped = 0;

        for (ColDef d : defs) {

            String columnName = toZZColumnName(d.rawName);
            String elementName = columnName;

            // Does AD_Column already exist?
            int existingColId = DB.getSQLValueEx(get_TrxName(),
                    "SELECT AD_Column_ID FROM AD_Column WHERE AD_Table_ID=? AND ColumnName=?",
                    adTableId, columnName);

            if (existingColId > 0) {
                addLog("Skip (already exists in AD_Column): " + columnName);
                skipped++;
                continue;
            }

            // Create or reuse AD_Element
            int elementId = findOrCreateElement(clientId, elementName);

            // Create AD_Column
            MColumn col = new MColumn(getCtx(), 0, get_TrxName());
            col.setAD_Table_ID(adTableId);
            col.setColumnName(columnName);
            col.setName(elementName);
            col.setAD_Element_ID(elementId);
            col.setEntityType(entityType);
            col.setIsActive(true);
            col.setIsKey(false);
            col.setIsParent(false);
            col.setIsMandatory(d.notNull);
            col.setIsUpdateable(true);
            col.setIsIdentifier(false);
            col.setIsTranslated(false);
            col.setIsSelectionColumn(false);
            col.setIsEncrypted(false);

            // Type mapping
            if (d.isString) {
                col.setAD_Reference_ID(DisplayType.String);
                col.setFieldLength(d.length);
            } else {
                col.setAD_Reference_ID(DisplayType.Integer);
                col.setFieldLength(10);
            }

            col.saveEx();

            // Create physical DB column
            if (createDBColumns) {
                ensureDbColumn(table.getTableName(), columnName, d);
            }

            created++;
            addLog("Created: " + table.getTableName() + "." + columnName);
        }

        return "Done. Created=" + created + ", Skipped=" + skipped;
    }

    // -------------------- helpers --------------------

    private static class ColDef {
        String rawName;
        boolean isString;
        int length;        // for string
        boolean notNull;
    }

    private List<ColDef> parseSpec(String spec) {
    	spec = spec.replace(";", " ");

        List<ColDef> out = new ArrayList<>();

        // 1) Split into items by commas, but ignore commas inside (...) e.g. varchar(500)
        List<String> items = splitByCommasOutsideParentheses(spec);

        // 2) Parse each item: <Name> <Type ...> [NULL|NOT NULL]
        Pattern p = Pattern.compile("^\\s*([A-Za-z_][A-Za-z0-9_]*)\\s+(.+?)\\s*$", Pattern.CASE_INSENSITIVE);

        for (String item : items) {
            String s = item.trim();
            if (s.isEmpty())
                continue;

            // strip trailing comma if any (defensive)
            if (s.endsWith(",")) s = s.substring(0, s.length() - 1).trim();
            if (s.isEmpty())
                continue;

            Matcher m = p.matcher(s);
            if (!m.find())
                continue;

            String name = m.group(1).trim();
            String rest = m.group(2).trim();

            ColDef d = new ColDef();
            d.rawName = name;

            String restUpper = rest.toUpperCase(Locale.ROOT);

            d.notNull = restUpper.contains("NOT NULL");

            // Determine type
            if (restUpper.contains("NVARCHAR") || restUpper.contains("VARCHAR") || restUpper.contains("NCHAR") || restUpper.contains("CHAR")) {
                d.isString = true;
                d.length = extractLength(restUpper, 500);
            } else {
                // Treat everything else like int/numeric for your use case
                d.isString = false;
                d.length = 0;
            }

            out.add(d);
        }

        return out;
    }

    /**
     * Splits a string by commas that are NOT inside parentheses.
     * Example: "A nvarchar(500), B int" -> ["A nvarchar(500)", "B int"]
     */
    private List<String> splitByCommasOutsideParentheses(String input) {
        List<String> parts = new ArrayList<>();
        if (input == null) return parts;

        StringBuilder current = new StringBuilder();
        int depth = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '(') depth++;
            if (c == ')' && depth > 0) depth--;

            if (c == ',' && depth == 0) {
                parts.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            parts.add(current.toString());
        }

        return parts;
    }


    private int extractLength(String s, int def) {
        Matcher m = Pattern.compile("\\((\\d+)\\)").matcher(s);
        if (m.find()) {
            try { return Integer.parseInt(m.group(1)); } catch (Exception ignore) {}
        }
        return def;
    }

    /**
     * Converts:
     *   LearningProgrammeTypeDoneID -> ZZ_Learning_Programme_Type_Done_ID
     *   LPOtherDone -> ZZ_LP_Other_Done
     */
    private String toZZColumnName(String raw) {
        // Handle trailing ID specially: DoneID -> Done_ID
        String s = raw.trim();

        // Split CamelCase / acronym transitions
        // Insert underscore before capitals where previous is lowercase or next is lowercase after acronym
        s = s.replaceAll("([a-z0-9])([A-Z])", "$1_$2");
        s = s.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2");

        // Ensure trailing _ID if ends with ID
        if (s.endsWith("ID") && !s.endsWith("_ID")) {
            s = s.substring(0, s.length() - 2) + "_ID";
        }

        return "ZZ_" + s;
    }

    private int findOrCreateElement(int clientId, String columnName) {

        String label = toElementLabel(columnName);

        int elementId = DB.getSQLValueEx(get_TrxName(),
            "SELECT AD_Element_ID FROM AD_Element " +
            "WHERE ColumnName=? AND AD_Client_ID IN (0,?) " +
            "ORDER BY AD_Client_ID DESC",
            columnName, clientId);

        if (elementId > 0)
            return elementId;

        M_Element el = new M_Element(getCtx(), 0, get_TrxName());
        el.setAD_Org_ID(0);

        el.setColumnName(columnName);
        el.setName(label);
        el.setPrintName(label);

        el.setEntityType(entityType);
        el.setIsActive(true);
        el.saveEx();

        return el.getAD_Element_ID();
    }


    private void ensureDbColumn(String tableName, String columnName, ColDef d) {
        // Check if column exists in DB (PostgreSQL)
        int exists = DB.getSQLValueEx(get_TrxName(),
                "SELECT COUNT(1) FROM information_schema.columns WHERE table_name=? AND column_name=?",
                tableName.toLowerCase(Locale.ROOT), columnName.toLowerCase(Locale.ROOT));

        if (exists > 0) {
            addLog("DB column already exists: " + tableName + "." + columnName);
            return;
        }

        String ddl;
        if (d.isString) {
            ddl = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " VARCHAR(" + d.length + ")"
                    + (d.notNull ? " NOT NULL" : "");
        } else {
            ddl = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " NUMERIC(10)"
                    + (d.notNull ? " NOT NULL" : "");
        }

        DB.executeUpdateEx(ddl, get_TrxName());
        addLog("Executed: " + ddl);
    }
    
    /**
     * Converts a column name like:
     *   ZZ_Learning_Programme_Type_Done_ID
     * to:
     *   Learning Programme Type Done
     */
    private String toElementLabel(String columnName) {
        if (Util.isEmpty(columnName, true))
            return columnName;

        String s = columnName.trim();

        // Remove ZZ_ prefix
        if (s.startsWith("ZZ_")) {
            s = s.substring(3);
        }

        // Remove trailing _ID
        if (s.endsWith("_ID")) {
            s = s.substring(0, s.length() - 3);
        }

        // Replace underscores with spaces
        s = s.replace('_', ' ');

        // Normalize spacing
        s = s.replaceAll("\\s+", " ").trim();

        return s;
    }

}

