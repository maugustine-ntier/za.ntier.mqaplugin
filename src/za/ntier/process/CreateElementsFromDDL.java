package za.ntier.process;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.M_Element;        // <-- use M_Element
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;


@org.adempiere.base.annotation.Process(name="za.ntier.process.CreateElementsFromDDL")
public class CreateElementsFromDDL extends SvrProcess {

    /** Parameters */
    private String p_EntityType = "U";
    private String p_InlineDDL  = null;

    @Override
    protected void prepare() {
        ProcessInfoParameter[] ps = getParameter();
        for (ProcessInfoParameter p : ps) {
            if ("EntityType".equalsIgnoreCase(p.getParameterName())) {
                p_EntityType = p.getParameterAsString();
            } else if ("InlineDDL".equalsIgnoreCase(p.getParameterName())) {
                p_InlineDDL = p.getParameterAsString();
            }
        }
        if (p_EntityType == null || p_EntityType.trim().isEmpty()) {
            p_EntityType = "U";
        }
    }

    @Override
    protected String doIt() throws Exception {
        int examined = 0;
        int created  = 0;

        if (p_InlineDDL != null && !p_InlineDDL.trim().isEmpty()) {
            Map<String, List<String>> map = parseCreateTableStatements(p_InlineDDL);
            for (Map.Entry<String, List<String>> e : map.entrySet()) {
                String tableName = e.getKey();
                List<String> cols = e.getValue();
                examined += cols.size();
                created  += createElementsForTable(tableName, cols);
            }
        } else {
            // Read from attachments on current record
            MAttachment att = MAttachment.get(getCtx(), getTable_ID(), getRecord_ID());
            if (att == null || att.getEntryCount() == 0) {
                throw new AdempiereException("No attachments found. Attach .sql/.txt containing CREATE TABLE statements or use InlineDDL.");
            }
            for (MAttachmentEntry ae : att.getEntries()) {
                String name = ae.getName() == null ? "" : ae.getName().toLowerCase(Locale.ROOT);
                if (!(name.endsWith(".sql") || name.endsWith(".txt"))) {
                    addLog("Skipping attachment (not .sql/.txt): " + ae.getName());
                    continue;
                }
                String ddl = readString(ae.getData());
                Map<String, List<String>> map = parseCreateTableStatements(ddl);
                for (Map.Entry<String, List<String>> e : map.entrySet()) {
                    String tableName = e.getKey();
                    List<String> cols = e.getValue();
                    examined += cols.size();
                    created  += createElementsForTable(tableName, cols);
                }
            }
        }

        return "AD_Element created: " + created + " (Columns scanned: " + examined + ")";
    }

    /* ===================== Core creation logic ===================== */

    private int createElementsForTable(String tableName, List<String> columns) {
        int created = 0;

        MTable table = MTable.get(getCtx(), tableName);
        String tableDisplayName = (table != null && table.get_ID() > 0) ? table.getName() : tableName;
        String uuidColumn = PO.getUUIDColumnName(tableName);

        for (String rawCol : columns) {
            String normalizedCase = normalizeElementColumnName(rawCol, tableName);

            // Avoid duplicates: look up by raw and normalized names
            M_Element element = M_Element.get(getCtx(), rawCol);
            if (element == null && normalizedCase != null && !normalizedCase.equals(rawCol)) {
                element = M_Element.get(getCtx(), normalizedCase);
            }

            if (element == null) {
                String elementColumnName = (normalizedCase != null ? normalizedCase : rawCol);
                element = new M_Element(getCtx(), elementColumnName, p_EntityType, get_TrxName());

                if (isTableIdColumn(tableName, rawCol)) {
                    element.setColumnName(tableName + "_ID");
                    element.setName(tableDisplayName);
                    element.setPrintName(tableDisplayName);

                } else if (isUuidColumn(tableName, rawCol, uuidColumn)) {
                    element.setColumnName(uuidColumn);
                    element.setName(uuidColumn);
                    element.setPrintName(uuidColumn);

                } else {
                    String pretty = prettifyColumnLabel(rawCol);
                    element.setName(pretty);
                    element.setPrintName(pretty);
                }

                element.saveEx();
                created++;
                addLog("Created AD_Element: " + element.getColumnName());
            } else {
                addLog("Exists: AD_Element " + element.getColumnName());
            }
        }
        return created;
    }

    private boolean isTableIdColumn(String tableName, String columnName) {
        return columnName != null && columnName.equalsIgnoreCase(tableName + "_ID");
    }

    private boolean isUuidColumn(String tableName, String columnName, String uuidColumn) {
        if (uuidColumn == null || columnName == null) return false;
        return columnName.equalsIgnoreCase(uuidColumn);
    }

    /* ===================== DDL parsing ===================== */

    private Map<String, List<String>> parseCreateTableStatements(String sql) {
        Map<String, List<String>> out = new LinkedHashMap<>();
        List<String[]> pairs = findCreateTables(sql);
        for (String[] p : pairs) {
            String fullIdent = p[0];
            String colsBlock = p[1];
            String tableName = extractUnqualifiedName(fullIdent);
            List<String> cols = extractColumnNames(colsBlock);
            if (!cols.isEmpty()) out.put(tableName, cols);
        }
        return out;
    }

    /** Remove /*...*\/ and -- ... EOL comments, respecting quotes. */
    private String preprocessSql(String sql) {
        if (sql == null) return "";
        String s = sql;

        // Strip block comments (/* ... */)
        s = s.replaceAll("(?s)/\\*.*?\\*/", " ");

        // Strip -- comments outside of quotes
        StringBuilder out = new StringBuilder();
        boolean inSingle = false, inDouble = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            char next = (i + 1 < s.length()) ? s.charAt(i + 1) : '\0';

            if (c == '\'' && !inDouble) {
                if (inSingle && next == '\'') { out.append("''"); i++; continue; }
                inSingle = !inSingle; out.append(c); continue;
            }
            if (c == '"' && !inSingle) {
                if (inDouble && next == '"') { out.append("\"\""); i++; continue; }
                inDouble = !inDouble; out.append(c); continue;
            }

            if (!inSingle && !inDouble && c == '-' && next == '-') {
                while (i < s.length() && s.charAt(i) != '\n' && s.charAt(i) != '\r') i++;
                if (i < s.length()) out.append(s.charAt(i));
                continue;
            }
            out.append(c);
        }
        return out.toString();
    }

    /** Find CREATE TABLE statements and return [fullTableIdent, colsBlock] pairs. */
    private List<String[]> findCreateTables(String sql) {
        String s = preprocessSql(sql);
        List<String[]> out = new ArrayList<>();
        int i = 0, n = s.length();

        while (i < n) {
            int k = indexOfWord(s, "CREATE", i);
            if (k < 0) break;
            int p = skipWord(s, k, "CREATE");
            p = skipWS(s, p);
            if (!regionMatchesWord(s, p, "TABLE")) { i = p; continue; }
            p = skipWord(s, p, "TABLE");
            p = skipWS(s, p);

            // Optional IF NOT EXISTS
            int save = p;
            if (regionMatchesWord(s, p, "IF")) {
                int p1 = skipWord(s, p, "IF"); p1 = skipWS(s, p1);
                if (regionMatchesWord(s, p1, "NOT")) {
                    p1 = skipWord(s, p1, "NOT"); p1 = skipWS(s, p1);
                    if (regionMatchesWord(s, p1, "EXISTS")) {
                        p = skipWord(s, p1, "EXISTS"); p = skipWS(s, p);
                    } else p = save;
                } else p = save;
            }

            // Find the ( ... ) block that holds columns
            int[] par = findNextParenBlock(s, p);
            if (par == null) { i = p; continue; }
            int open = par[0], close = par[1];

            String fullIdent = s.substring(p, open).trim();
            String colsBlock = s.substring(open + 1, close);
            out.add(new String[]{ fullIdent, colsBlock });

            i = close + 1; // continue scanning
        }
        return out;
    }

    private int[] findNextParenBlock(String s, int from) {
        boolean inSingle = false, inDouble = false;
        for (int i = from; i < s.length(); i++) {
            char c = s.charAt(i), next = (i + 1 < s.length() ? s.charAt(i + 1) : '\0');
            if (c == '\'' && !inDouble) { if (inSingle && next=='\'') { i++; continue; } inSingle = !inSingle; continue; }
            if (c == '"'  && !inSingle) { if (inDouble && next=='"')  { i++; continue; } inDouble = !inDouble; continue; }
            if (!inSingle && !inDouble && c == '(') {
                int open = i, depth = 1;
                for (i = i + 1; i < s.length(); i++) {
                    c = s.charAt(i); next = (i + 1 < s.length() ? s.charAt(i + 1) : '\0');
                    if (c == '\'' && !inDouble) { if (next=='\'') { i++; continue; } inSingle = !inSingle; continue; }
                    if (c == '"'  && !inSingle) { if (next=='"')  { i++; continue; } inDouble = !inDouble; continue; }
                    if (!inSingle && !inDouble) {
                        if (c == '(') depth++;
                        else if (c == ')') { depth--; if (depth == 0) return new int[]{ open, i }; }
                    }
                }
                return null; // unmatched
            }
        }
        return null;
    }

    private int indexOfWord(String s, String word, int from) {
        return s.toLowerCase(Locale.ROOT).indexOf(word.toLowerCase(Locale.ROOT), from);
    }
    private boolean regionMatchesWord(String s, int pos, String word) {
        int len = word.length();
        if (pos + len > s.length()) return false;
        return s.regionMatches(true, pos, word, 0, len);
    }
    private int skipWord(String s, int pos, String word) {
        return pos + word.length();
    }
    private int skipWS(String s, int i) {
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) i++;
        return i;
    }

    /** Split the column block into individual defs (top-level commas only). */
    private List<String> splitColumnDefs(String block) {
        List<String> parts = new ArrayList<>();
        if (block == null) return parts;

        String s = block;
        StringBuilder cur = new StringBuilder();
        int depth = 0;
        boolean inSingle = false, inDouble = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i), next = (i + 1 < s.length() ? s.charAt(i + 1) : '\0');

            // defensive: skip inline -- comments in block
            if (!inSingle && !inDouble && c == '-' && next == '-') {
                while (i < s.length() && s.charAt(i) != '\n' && s.charAt(i) != '\r') i++;
                if (i < s.length()) cur.append(s.charAt(i));
                continue;
            }

            if (c == '\'' && !inDouble) { if (inSingle && next=='\'') { cur.append("''"); i++; continue; } inSingle = !inSingle; cur.append(c); continue; }
            if (c == '"'  && !inSingle) { if (inDouble && next=='"')  { cur.append("\"\""); i++; continue; } inDouble = !inDouble; cur.append(c); continue; }

            if (!inSingle && !inDouble) {
                if (c == '(') { depth++; cur.append(c); continue; }
                if (c == ')') { depth = Math.max(0, depth - 1); cur.append(c); continue; }
                if (c == ',' && depth == 0) {
                    String item = cur.toString().trim();
                    if (!item.isEmpty()) parts.add(item);
                    cur.setLength(0);
                    continue;
                }
            }
            cur.append(c);
        }
        String last = cur.toString().trim();
        if (!last.isEmpty()) parts.add(last);
        return parts;
    }

    /** Extract first token per def as column name; skip table-level constraints. */
    private List<String> extractColumnNames(String colsBlock) {
        List<String> cols = new ArrayList<>();
        if (colsBlock == null) return cols;

        List<String> defs = splitColumnDefs(colsBlock);
        Set<String> skip = new HashSet<>(Arrays.asList(
                "PRIMARY", "FOREIGN", "UNIQUE", "CHECK", "CONSTRAINT", "INDEX"
        ));

        for (String def : defs) {
            String line = def.trim();
            if (line.isEmpty()) continue;

            // (defensive) remove any leftover inline comment
            int cut = line.indexOf("--");
            if (cut >= 0) line = line.substring(0, cut).trim();
            if (line.isEmpty()) continue;

            // first token
            String first = line.split("\\s+")[0];
            String upper = first.replaceAll("^\"|\"$", "").toUpperCase(Locale.ROOT);
            if (skip.contains(upper)) continue; // table-level constraint -> skip

            String col = null;
            if (first.startsWith("\"")) {
                Matcher qm = Pattern.compile("^\"((?:[^\"]|\"\")+)\"").matcher(line);
                if (qm.find()) col = qm.group(1).replace("\"\"", "\"");
            } else {
                Matcher id = Pattern.compile("^([A-Za-z_][A-Za-z0-9_]*)").matcher(line);
                if (id.find()) col = id.group(1);
            }

            if (col != null && !col.isEmpty()) cols.add(col);
        }
        return cols;
    }

    /** Extract unqualified table name (strip schema and quotes). */
    private String extractUnqualifiedName(String full) {
        String last = full;
        int dot = full.lastIndexOf('.');
        if (dot >= 0 && dot < full.length() - 1) last = full.substring(dot + 1).trim();
        if (last.startsWith("\"") && last.endsWith("\"") && last.length() > 1)
            last = last.substring(1, last.length() - 1);
        return last;
    }

    /* ===================== Naming helpers ===================== */

    /** Normalize to iDempiere-style ColumnName: zz_approved_ts_col_id -> ZZ_Approved_TS_Col_ID (and PK/UUID special-cases). */
    private String normalizeElementColumnName(String raw, String tableName) {
        if (raw == null) return null;
        String s = raw;

        // strip outer quotes
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() > 1) {
            s = s.substring(1, s.length() - 1);
        }

        // Primary Key: TableName_ID exact casing
        if (s.equalsIgnoreCase(tableName + "_id")) {
            return tableName + "_ID";
        }

        // UUID: use system-defined UUID column
        String uuidCol = PO.getUUIDColumnName(tableName);
        if (s.equalsIgnoreCase(uuidCol)) {
            return uuidCol;
        }

        String[] parts = s.split("_");
        List<String> out = new ArrayList<>(parts.length);
        for (int i = 0; i < parts.length; i++) {
            String t = parts[i];
            if (t.isEmpty()) continue;

            // common prefixes preserved as uppercase
            if (i == 0 && t.equalsIgnoreCase("zz")) { out.add("ZZ"); continue; }
            if (i == 0 && t.equalsIgnoreCase("z"))  { out.add("Z");  continue; }

            // common acronyms
            String lower = t.toLowerCase(Locale.ROOT);
            if (lower.equals("id"))  { out.add("ID");  continue; }
            if (lower.equals("uu"))  { out.add("UU");  continue; }
            if (lower.equals("ts"))  { out.add("TS");  continue; }
            if (lower.equals("ad"))  { out.add("AD");  continue; }
            if (lower.equals("bp"))  { out.add("BP");  continue; }

            // short tokens -> uppercase; else Capitalize
            if (t.length() <= 3) out.add(t.toUpperCase(Locale.ROOT));
            else out.add(t.substring(0,1).toUpperCase(Locale.ROOT) + t.substring(1).toLowerCase(Locale.ROOT));
        }
        return String.join("_", out);
    }

    /** Produce human Name/PrintName for a non-ID, non-UUID column. Removes ZZ_ prefix; splits '_' and CamelCase; preserves acronyms. */
    private String prettifyColumnLabel(String raw) {
        if (raw == null) return "";
        String s = raw;

        // strip quotes
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() > 1) {
            s = s.substring(1, s.length() - 1);
        }

        // remove leading ZZ_/Z_
        if (s.startsWith("ZZ__")) s = s.substring(4);
        else if (s.startsWith("ZZ_")) s = s.substring(3);
        else if (s.startsWith("Z_")) s = s.substring(2);

        // underscores to spaces
        s = s.replace('_', ' ');

        // split CamelCase boundaries
        s = s.replaceAll("(?<=[a-z0-9])(?=[A-Z])", " ");
        s = s.replaceAll("(?<=[A-Z])(?=[A-Z][a-z])", " ");

        // collapse whitespace
        s = s.trim().replaceAll("\\s{2,}", " ");

        // title-case words, but keep acronyms
        StringBuilder out = new StringBuilder();
        for (String w : s.split(" ")) {
            if (w.isEmpty()) continue;
            boolean isAllCaps = w.equals(w.toUpperCase(Locale.ROOT));
            String word = w;
            if (!isAllCaps) {
                word = w.substring(0,1).toUpperCase(Locale.ROOT) + (w.length() > 1 ? w.substring(1).toLowerCase(Locale.ROOT) : "");
            }
            if (out.length() > 0) out.append(' ');
            out.append(word);
        }
        return out.toString();
    }

    /* ===================== IO helper ===================== */

    private String readString(byte[] data) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append('\n');
        }
        return sb.toString();
    }
}


