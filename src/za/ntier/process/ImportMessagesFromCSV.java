package za.ntier.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.annotation.Process;
import org.compiere.model.MMessage;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Message;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;

/**
 * Import AD_Message from pasted CSV text.
 *
 * Parameters:
 *  - CSVText (Text)      : Paste CSV content (header optional). Columns: Value,MsgText,MsgTip
 *  - EntityType (String) : Optional, default 'U'
 *
 * Behavior:
 *  - If AD_Message.Value exists -> skip
 *  - Else create a new AD_Message in System client (AD_Client_ID=0)
 */
@Process(name = "za.ntier.process.ImportMessagesFromCSV")
public class ImportMessagesFromCSV extends SvrProcess {

    private static final CLogger log = CLogger.getCLogger(ImportMessagesFromCSV.class);

    private String pCSVText;
    private String pEntityType = "U";

    @Override
    protected void prepare() {
        ProcessInfoParameter[] para = getParameter();
        for (ProcessInfoParameter p : para) {
            String name = p.getParameterName();
            if ("CSVText".equalsIgnoreCase(name)) {
                pCSVText = p.getParameterAsString();
            } else if ("EntityType".equalsIgnoreCase(name)) {
                if (p.getParameter() != null)
                    pEntityType = p.getParameter().toString();
            } else {
                log.warning("Unknown Parameter: " + name);
            }
        }
    }

    @Override
    protected String doIt() throws Exception {
        if (pCSVText == null || pCSVText.trim().isEmpty())
            throw new IllegalArgumentException("CSVText is empty");

        int created = 0;
        int skipped = 0;
        List<String> errors = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new StringReader(pCSVText))) {
            String header = br.readLine();
            if (header == null)
                throw new IllegalArgumentException("CSV appears to be empty");

            String[] headerCols = parseCsvLine(header);
            int idxValue = -1, idxMsgText = -1, idxMsgTip = -1;

            // Detect header columns
            for (int i = 0; i < headerCols.length; i++) {
                String h = headerCols[i].trim().toLowerCase();
                if ("value".equals(h)) idxValue = i;
                else if ("msgtext".equals(h)) idxMsgText = i;
                else if ("msgtip".equals(h)) idxMsgTip = i;
            }

            boolean headerIsPresent = (idxValue >= 0 || idxMsgText >= 0 || idxMsgTip >= 0);
            if (!headerIsPresent) {
                // treat first line as data
                Result r = processRow(parseCsvLine(header), 1);
                if (r.error != null) errors.add(r.error);
                else if (r.created) created++; else skipped++;
            }

            String line;
            int rowNum = headerIsPresent ? 1 : 1;
            while ((line = br.readLine()) != null) {
                rowNum++;
                if (line.trim().isEmpty()) continue;
                String[] cols = parseCsvLine(line);

                if (headerIsPresent) {
                    String[] ordered = new String[3];
                    ordered[0] = getSafe(cols, idxValue >= 0 ? idxValue : 0);
                    ordered[1] = getSafe(cols, idxMsgText >= 0 ? idxMsgText : 1);
                    ordered[2] = (idxMsgTip >= 0 ? getSafe(cols, idxMsgTip) : "");
                    Result r = processRow(ordered, rowNum);
                    if (r.error != null) errors.add(r.error);
                    else if (r.created) created++; else skipped++;
                } else {
                    Result r = processRow(cols, rowNum);
                    if (r.error != null) errors.add(r.error);
                    else if (r.created) created++; else skipped++;
                }
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Error reading CSV text", ioe);
        }

        String msg = "Messages created=" + created + ", skipped(existing)=" + skipped;
        for (String e : errors) addLog(e);
        if (!errors.isEmpty()) msg += ", errors=" + errors.size();
        return msg;
    }

    private Result processRow(String[] cols, int rowNum) {
        Result r = new Result();
        try {
            if (cols.length < 2)
                throw new IllegalArgumentException("Row has fewer than 2 columns (Value, MsgText)");

            String value = safe(cols[0]);
            String msgText = safe(cols[1]);
            String msgTip = (cols.length > 2 ? safe(cols[2]) : "");

            if (value.isEmpty())
                throw new IllegalArgumentException("Missing Value");
            if (msgText.isEmpty())
                throw new IllegalArgumentException("Missing MsgText");

            boolean exists = new Query(getCtx(), MMessage.Table_Name, "Value=?", get_TrxName())
                    .setParameters(value)
                    .setOnlyActiveRecords(false)
                    .match();
            if (exists) { r.created = false; return r; }

            MMessage mm = new MMessage(getCtx(), 0, get_TrxName());
            mm.setAD_Org_ID(0);
            mm.setIsActive(true);
            mm.setMsgType(X_AD_Message.MSGTYPE_Information);
            mm.setValue(value);
            mm.setMsgText(msgText);
            if (!msgTip.isEmpty()) mm.setMsgTip(msgTip);
            mm.setEntityType(pEntityType != null ? pEntityType : "U");
            mm.saveEx();

            r.created = true;
            return r;
        } catch (Exception ex) {
            r.error = "Row " + rowNum + ": " + ex.getMessage();
            return r;
        }
    }

    /** Simple CSV parser: commas, quotes, and "" escape inside quotes */
    private static String[] parseCsvLine(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '\"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '\"') { cur.append('\"'); i++; }
                    else { inQuotes = false; }
                } else { cur.append(c); }
            } else {
                if (c == '\"') inQuotes = true;
                else if (c == ',') { out.add(cur.toString()); cur.setLength(0); }
                else cur.append(c);
            }
        }
        out.add(cur.toString());
        return out.toArray(new String[0]);
    }

    private static String getSafe(String[] arr, int idx) { return (idx >= 0 && idx < arr.length) ? arr[idx] : ""; }
    private static String safe(String s) { return s == null ? "" : s.trim(); }

    private static class Result { boolean created; String error; }
}
