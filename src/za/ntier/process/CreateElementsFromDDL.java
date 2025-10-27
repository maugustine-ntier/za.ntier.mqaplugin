package za.ntier.process;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MTable;
import org.compiere.model.M_Element;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

@org.adempiere.base.annotation.Process(
		name = "za.ntier.process.CreateElementsFromDDL"
		)
public class CreateElementsFromDDL extends SvrProcess {

	private String p_EntityType = "U";      // default user entity type
	private String p_InlineDDL  = null;     // optional pasted SQL text

	@Override
	protected void prepare() {
		ProcessInfoParameter[] ps = getParameter();
		for (ProcessInfoParameter p : ps) {
			String name = p.getParameterName();
			if ("EntityType".equalsIgnoreCase(name)) {
				p_EntityType = p.getParameterAsString();
			} else if ("InlineDDL".equalsIgnoreCase(name)) {
				p_InlineDDL = p.getParameterAsString();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		if ((p_InlineDDL == null || p_InlineDDL.trim().isEmpty())) {
			// Read from attachments on the current record
			MAttachment att = MAttachment.get(getCtx(), getTable_ID(), getRecord_ID());
			if (att == null || att.getEntryCount() == 0)
				throw new AdempiereException("No attachments found. Attach .sql/.txt with CREATE TABLE statements or use the InlineDDL parameter.");

			int created = 0;
			int examined = 0;

			for (MAttachmentEntry e : att.getEntries()) {
				String name = e.getName() != null ? e.getName().toLowerCase(Locale.ROOT) : "";
				if (!(name.endsWith(".sql") || name.endsWith(".txt"))) {
					addLog("Skipping attachment (not .sql/.txt): " + e.getName());
					continue;
				}

				String ddl = readString(e.getData());
				Map<String, List<String>> map = parseCreateTableStatements(ddl);

				for (Map.Entry<String, List<String>> entry : map.entrySet()) {
					String tableName = entry.getKey();
					List<String> cols = entry.getValue();
					examined += cols.size();
					created += createElementsForTable(tableName, cols);
				}
			}
			return "AD_Element created: " + created + " (Columns scanned: " + examined + ")";
		} else {
			// Use inline parameter text
			Map<String, List<String>> map = parseCreateTableStatements(p_InlineDDL);
			int created = 0;
			int examined = 0;
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				String tableName = entry.getKey();
				List<String> cols = entry.getValue();
				examined += cols.size();
				created += createElementsForTable(tableName, cols);
			}
			return "AD_Element created: " + created + " (Columns scanned: " + examined + ")";
		}
	}

	private String readString(byte[] data) throws Exception {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(data), StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) sb.append(line).append('\n');
		}
		return sb.toString();
	}

	/**
	 * Parse one or more CREATE TABLE statements from an SQL blob.
	 * Returns map of tableName -> columnName list (order preserved).
	 */
	private Map<String, List<String>> parseCreateTableStatements(String sql) {
	    Map<String, List<String>> out = new LinkedHashMap<>();
	    if (sql == null) return out;

	    String noComments = preprocessSql(sql);

	    Pattern createTbl = Pattern.compile(
	        "(?is)\\bCREATE\\s+TABLE\\s+(?:IF\\s+NOT\\s+EXISTS\\s+)?([\\w\\.\"]+)\\s*\\((.*?)\\)\\s*;",
	        Pattern.CASE_INSENSITIVE | Pattern.DOTALL
	    );

	    Matcher m = createTbl.matcher(noComments);
	    while (m.find()) {
	        String fullTable = m.group(1).trim();
	        String colsBlock = m.group(2);
	        String tableName = extractUnqualifiedName(fullTable);
	        List<String> cols = extractColumnNames(colsBlock);
	        if (!cols.isEmpty()) out.put(tableName, cols);
	    }
	    return out;
	}

	private String extractUnqualifiedName(String full) {
		// split by dot and take last
		String last = full;
		int dot = full.lastIndexOf('.');
		if (dot >= 0 && dot < full.length() - 1) last = full.substring(dot + 1);

		// strip quotes if any
		if (last.startsWith("\"") && last.endsWith("\"") && last.length() >= 2) {
			last = last.substring(1, last.length() - 1);
		}
		return last;
	}

	/**
	 * Extract column names from the (...) block.
	 * Skips constraints and comments; returns first token of a definition line.
	 */
	private List<String> extractColumnNames(String colsBlock) {
	    List<String> cols = new ArrayList<>();
	    if (colsBlock == null) return cols;

	    List<String> defs = splitColumnDefs(colsBlock);

	    // lines that start table-level constraints / indexes
	    Set<String> skipStarters = new HashSet<>(Arrays.asList(
	            "PRIMARY", "FOREIGN", "UNIQUE", "CHECK", "CONSTRAINT", "INDEX"));

	    for (String raw : defs) {
	        String line = raw.trim();
	        if (line.isEmpty()) continue;

	        // remove trailing inline comment
	        int dashdash = line.indexOf("--");
	        if (dashdash >= 0) line = line.substring(0, dashdash).trim();
	        if (line.isEmpty()) continue;

	        // first token
	        String first = line.split("\\s+")[0];

	        // skip table-level constraints
	        String upper = first.replaceAll("^\"|\"$", "").toUpperCase(Locale.ROOT);
	        if (skipStarters.contains(upper)) continue;

	        // column name: quoted or bare
	        String colName = null;
	        if (first.startsWith("\"")) {
	            // quoted identifier possibly with escaped quotes
	            java.util.regex.Matcher qm = java.util.regex.Pattern
	                    .compile("^\"((?:[^\"]|\"\")+)\"")
	                    .matcher(line);
	            if (qm.find()) colName = qm.group(1).replace("\"\"", "\"");
	        } else {
	            java.util.regex.Matcher id = java.util.regex.Pattern
	                    .compile("^([A-Za-z_][A-Za-z0-9_]*)")
	                    .matcher(line);
	            if (id.find()) colName = id.group(1);
	        }

	        if (colName != null && !colName.isEmpty()) cols.add(colName);
	    }
	    return cols;
	}

	private int createElementsForTable(String tableName, List<String> columns) {
		int created = 0;

		// Try to resolve AD_Table for display names when we special-case TableName_ID
		MTable table = MTable.get(getCtx(), tableName);
		String tableDisplayName = (table != null && table.get_ID() > 0) ? table.getName() : tableName;

		// UUID column name for this table
		String uuidColumn = PO.getUUIDColumnName(tableName);

		for (String columnName : columns) {
			String normalized = columnName;

			M_Element element = M_Element.get(getCtx(), normalized,get_TrxName());
			if (element == null) {
				element = new M_Element(getCtx(), normalized, p_EntityType, get_TrxName());

				// Special cases: TableName_ID and UUID column
				if (isTableIdColumn(tableName, normalized)) {
					element.setColumnName(tableName + "_ID");
					element.setName(tableDisplayName);
					element.setPrintName(tableDisplayName);

				} else if (isUuidColumn(tableName, normalized)) {
					String uuidCol = PO.getUUIDColumnName(tableName);
					element.setColumnName(uuidCol);
					element.setName(uuidCol);
					element.setPrintName(uuidCol);

				} else {
					// General case: prettify for Name/PrintName (no ZZ_, no underscores, word-split)
					String pretty = prettifyColumnLabel(normalized);
					element.setName(pretty);
					element.setPrintName(pretty);
				}

				element.saveEx();
				created++;
				addLog("Created AD_Element for " + tableName + "." + normalized);
			} else {
				addLog("Exists: AD_Element " + normalized);
			}
		}

		return created;
	}


	/** True if this is the table PK (TableName_ID) */
	private boolean isTableIdColumn(String tableName, String columnName) {
		return columnName.equalsIgnoreCase(tableName + "_ID");
	}

	/** True if this is the UUID column for the table */
	private boolean isUuidColumn(String tableName, String columnName) {
		String uuidColumn = PO.getUUIDColumnName(tableName);
		return columnName.equalsIgnoreCase(uuidColumn);
	}

	/** Produce a human Name/PrintName for a non-ID, non-UUID column. */
	private String prettifyColumnLabel(String raw) {
		if (raw == null) return "";
		String s = raw;

		// Strip quotes if any
		if (s.startsWith("\"") && s.endsWith("\"") && s.length() > 1) {
			s = s.substring(1, s.length() - 1);
		}

		// Remove leading ZZ_ prefix (single or double underscore variants)
		if (s.startsWith("ZZ__")) s = s.substring(4);
		else if (s.startsWith("ZZ_")) s = s.substring(3);

		// Replace underscores with space first
		s = s.replace('_', ' ');

		// Insert spaces for CamelCase: ABCDef -> ABC Def, startStatus -> start Status
		// 1) Boundary between lower/digit and upper: "tA" -> "t A"
		s = s.replaceAll("(?<=[a-z0-9])(?=[A-Z])", " ");
		// 2) Boundary in acronym followed by word: "XMLParser" -> "XML Parser"
		s = s.replaceAll("(?<=[A-Z])(?=[A-Z][a-z])", " ");

		s = s.trim().replaceAll("\\s{2,}", " ");

		// Title-case words unless they are all-caps (to preserve acronyms like ID, UUID)
		StringBuilder out = new StringBuilder();
		for (String w : s.split(" ")) {
			if (w.isEmpty()) continue;
			String word = w;
			boolean isAllCaps = w.equals(w.toUpperCase());
			if (!isAllCaps) {
				word = w.substring(0,1).toUpperCase() + (w.length() > 1 ? w.substring(1).toLowerCase() : "");
			}
			if (out.length() > 0) out.append(' ');
			out.append(word);
		}
		return out.toString();
	}
	
	/** Split a CREATE TABLE column block into individual definitions.
	 *  Splits on commas that are at top-level (parenDepth==0) and not in quotes/comments.
	 */
	private List<String> splitColumnDefs(String block) {
	    List<String> parts = new ArrayList<>();
	    if (block == null) return parts;

	    String s = block; // block comments already removed by preprocessSql()
	    StringBuilder cur = new StringBuilder();
	    int parenDepth = 0;
	    boolean inSingle = false;
	    boolean inDouble = false;

	    for (int i = 0; i < s.length(); i++) {
	        char c = s.charAt(i);
	        char next = (i + 1 < s.length()) ? s.charAt(i + 1) : '\0';

	        // handle -- comments here too (just in case colsBlock came un-preprocessed)
	        if (!inSingle && !inDouble && c == '-' && next == '-') {
	            // skip until end-of-line WITHOUT appending
	            while (i < s.length() && s.charAt(i) != '\n' && s.charAt(i) != '\r') i++;
	            // keep the newline boundary if present
	            if (i < s.length()) cur.append(s.charAt(i));
	            continue;
	        }

	        // quotes (respect doubling)
	        if (c == '\'' && !inDouble) {
	            if (inSingle && next == '\'') { cur.append("''"); i++; continue; }
	            inSingle = !inSingle; cur.append(c); continue;
	        }
	        if (c == '"' && !inSingle) {
	            if (inDouble && next == '"') { cur.append("\"\""); i++; continue; }
	            inDouble = !inDouble; cur.append(c); continue;
	        }

	        if (!inSingle && !inDouble) {
	            if (c == '(') { parenDepth++; cur.append(c); continue; }
	            if (c == ')') { parenDepth = Math.max(0, parenDepth - 1); cur.append(c); continue; }
	            if (c == ',' && parenDepth == 0) {
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



	/** Remove /* ... *\/ and -- ... EOL comments, but keep content inside quotes. */
	private String preprocessSql(String sql) {
	    if (sql == null) return "";
	    String s = sql;

	    // 1) Strip block comments (/* ... */) – not nested in PostgreSQL
	    s = s.replaceAll("(?s)/\\*.*?\\*/", " ");

	    // 2) Strip -- line comments when not inside quotes
	    StringBuilder out = new StringBuilder();
	    boolean inSingle = false; // '
	    boolean inDouble = false; // "
	    for (int i = 0; i < s.length(); i++) {
	        char c = s.charAt(i);
	        char next = (i + 1 < s.length()) ? s.charAt(i + 1) : '\0';

	        // toggle quotes (handle doubled quotes)
	        if (c == '\'' && !inDouble) {
	            if (inSingle && next == '\'') { out.append("''"); i++; continue; }
	            inSingle = !inSingle; out.append(c); continue;
	        }
	        if (c == '"' && !inSingle) {
	            if (inDouble && next == '"') { out.append("\"\""); i++; continue; }
	            inDouble = !inDouble; out.append(c); continue;
	        }

	        // if we see -- and we're not in a quote, skip until EOL
	        if (!inSingle && !inDouble && c == '-' && next == '-') {
	            // skip until end-of-line
	            while (i < s.length() && s.charAt(i) != '\n' && s.charAt(i) != '\r') i++;
	            // keep the newline (if any) to maintain statement boundaries
	            if (i < s.length()) out.append(s.charAt(i));
	            continue;
	        }

	        out.append(c);
	    }
	    return out.toString();
	}

}

