package za.ntier.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MClient;
import org.compiere.model.Query;
import org.compiere.model.X_C_InvoiceBatchLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import za.ntier.models.MInvoiceBatch_New;
import za.ntier.models.X_C_InvoiceBatch;
import za.ntier.models.X_ZZ_Monthly_Levy_Files;
import za.ntier.models.X_ZZ_Monthly_Levy_Files_Hdr;

@org.adempiere.base.annotation.Process(name="za.ntier.process.CreateInvoiceBatchFromLevyFiles")
public class CreateInvoiceBatchFromLevyFiles extends SvrProcess {

    private int p_C_Charge_ID = 0;
    private int p_C_DocType_ID = 0;
    private Timestamp p_DateDoc = null;

    private int m_Record_ID = 0;
    private Properties m_ctx;

    private int sourceRows = 0;
    private int createdLines = 0;
    private int skippedNoBP = 0;
    private int skippedZeroAmt = 0;
    private int batchesCreated = 0;
    private int skippedNoApproval = 0;

    // lazy-per-year state
    private final Map<String, MInvoiceBatch_New> batchByYear = new LinkedHashMap<>();
    private final Map<String, Integer> lineNoByYear = new LinkedHashMap<>();

    @Override
    protected void prepare() {
        m_ctx = getCtx();
        m_Record_ID = getRecord_ID();

        for (ProcessInfoParameter p : getParameter()) {
            if (p.getParameter() == null) continue;
            switch (p.getParameterName()) {
                case "C_Charge_ID": p_C_Charge_ID = p.getParameterAsInt(); break;
                case "C_DocType_ID": p_C_DocType_ID = p.getParameterAsInt(); break;
                case "DateDoc": p_DateDoc = (Timestamp) p.getParameter(); break;
                default: /* ignore */ ;
            }
        }

        if (m_Record_ID <= 0) throw new AdempiereException("No ZZ_Monthly_Levy_Files_Hdr record selected.");
        if (p_DateDoc == null) p_DateDoc = new Timestamp(System.currentTimeMillis());
    }

    @Override
    protected String doIt() throws Exception {
        final X_ZZ_Monthly_Levy_Files_Hdr hdr = new X_ZZ_Monthly_Levy_Files_Hdr(m_ctx, m_Record_ID, get_TrxName());
        if (hdr.get_ID() <= 0) throw new AdempiereException("Header not found: ID=" + m_Record_ID);

        // charge
        if (p_C_Charge_ID <= 0) {
            p_C_Charge_ID = DB.getSQLValue(get_TrxName(),
                "SELECT C_Charge_ID FROM C_Charge WHERE UPPER(Name)=UPPER('3010 Grant Expenses - Mandatory') AND IsActive='Y' AND AD_Client_ID=? ORDER BY C_Charge_ID",
                Env.getAD_Client_ID(m_ctx));
            if (p_C_Charge_ID <= 0)
                throw new AdempiereException("C_Charge_ID required. Could not find Name='charge1'.");
        }

        // doctype
        if (p_C_DocType_ID <= 0) {
            p_C_DocType_ID = DB.getSQLValue(get_TrxName(),
                "SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='ARI' AND IsActive='Y' AND AD_Client_ID=? ORDER BY IsSOTrx DESC, C_DocType_ID",
                Env.getAD_Client_ID(m_ctx));
            if (p_C_DocType_ID <= 0) {
                p_C_DocType_ID = DB.getSQLValue(get_TrxName(),
                    "SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='API' AND IsActive='Y' AND AD_Client_ID=? ORDER BY C_DocType_ID",
                    Env.getAD_Client_ID(m_ctx));
            }
            if (p_C_DocType_ID <= 0) throw new AdempiereException("Could not resolve C_DocType_ID (ARI/API).");
        }

        // currency
        int currencyID = DB.getSQLValue(get_TrxName(),
            "SELECT C_Currency_ID FROM C_AcctSchema WHERE AD_Client_ID=? AND IsActive='Y' ORDER BY C_AcctSchema_ID",
            Env.getAD_Client_ID(m_ctx));
        if (currencyID <= 0) {
            MClient client = MClient.get(m_ctx);
            currencyID = client.getC_Currency_ID();
        }
        if (currencyID <= 0) throw new AdempiereException("Could not resolve C_Currency_ID.");

        // rows to process (not yet linked)
        List<X_ZZ_Monthly_Levy_Files> rows = new Query(m_ctx, X_ZZ_Monthly_Levy_Files.Table_Name,
                "ZZ_Monthly_Levy_Files_Hdr_ID=? AND IsActive='Y' AND C_InvoiceBatchLine_ID IS NULL",
                get_TrxName())
                .setParameters(hdr.get_ID())
                .setOnlyActiveRecords(true)
                .list();

        sourceRows = rows.size();
        if (sourceRows == 0) return "No ZZ_Monthly_Levy_Files rows for header ID " + hdr.get_ID();

        final Timestamp lineDate = (hdr.getCreated() != null) ? hdr.getCreated() : p_DateDoc;

        for (X_ZZ_Monthly_Levy_Files rec : rows) {
            // derive year key
            String yearKey = safe(rec.getZZ_Year());
            if (Integer.parseInt(yearKey) < 2010) {
            	continue;            	
            }
            if (yearKey.isEmpty()) {
                yearKey = DB.getSQLValueString(get_TrxName(), "SELECT Name FROM C_Year WHERE C_Year_ID=?", rec.getC_Year_ID());
                if (yearKey == null || yearKey.trim().isEmpty()) yearKey = "UNKNOWN";
            }

            // BP by SDL
            final String sdl = safe(rec.getZZ_SDL_No());
            if (sdl.isEmpty()) {
                addLog("WARN: Missing SDL (ZZ_SDL_No) on levy row ID " + rec.get_ID());
                skippedNoBP++;
                continue;
            }
            
            if ("UNKNOWN".equals(yearKey)) {
                addLog("INFO: Missing ZZ_Year on row " + rec.get_ID() + " — skipping (no approval year to match).");
                skippedNoApproval++;
                continue;
            }

            MBPartner bp = new Query(m_ctx, MBPartner.Table_Name, "Value=? AND IsActive='Y'", get_TrxName())
                    .setParameters(sdl)
                    .setOnlyActiveRecords(true)
                    .first();
            if (bp == null) {
                addLog("WARN: No BP where Value=" + sdl + " (row " + rec.get_ID() + ")");
                skippedNoBP++;
                continue;
            }
            int bpLocID = getBillToOrAnyBPLocation(bp);
            
         // Ensure BP has an Approved WSP/ATR for this year
            if (!hasApprovedForYear(bp.getC_BPartner_ID(), yearKey)) {
                addLog("INFO: No Approved WSP/ATR for BP=" + bp.getValue() + " year=" + yearKey + " (row " + rec.get_ID() + ")");
                skippedNoApproval++;
                continue;
            }

            // amounts (MG only in your current flow; uncomment DG if needed)
            BigDecimal mg = nz(rec.getZZ_MG());
            boolean wroteAnyLine = false;

            if (isNegative(mg)) {
            	MInvoiceBatch_New batch = ensureBatchForYear(yearKey, hdr, currencyID); // lazy-create here
                int lineNo = nextLineNo(yearKey, batch.getC_InvoiceBatch_ID());
                createLine(batch.getC_InvoiceBatch_ID(), p_C_DocType_ID, ++createdLines, lineNo,
                        bp.getC_BPartner_ID(), bpLocID, p_C_Charge_ID, lineDate,
                        mg, "MG", rec);
                wroteAnyLine = true;
            } else {
                skippedZeroAmt++;
            }

            // // DG (positive-only) — optional:
            // BigDecimal dg = nz(rec.getZZ_DG());
            // if (isPositive(dg)) {
            //     X_C_InvoiceBatch batch = ensureBatchForYear(yearKey, hdr, currencyID);
            //     int lineNo = nextLineNo(yearKey, batch.getC_InvoiceBatch_ID());
            //     createLine(batch.getC_InvoiceBatch_ID(), p_C_DocType_ID, ++createdLines, lineNo,
            //             bp.getC_BPartner_ID(), bpLocID, p_C_Charge_ID, lineDate,
            //             dg, "DG", rec);
            //     wroteAnyLine = true;
            // } else {
            //     skippedZeroAmt++;
            // }

            // roll up batch amount incrementally (avoids second pass)
            if (wroteAnyLine) {
            	MInvoiceBatch_New batch = batchByYear.get(yearKey);
                BigDecimal current = batch.getDocumentAmt() != null ? batch.getDocumentAmt() : Env.ZERO;
                BigDecimal add = nz(mg); // + (dg if you enable it)
                batch.setDocumentAmt(current.add(add));
                batch.saveEx();
            }
        }

        return String.format(
            "Batches created: %d. Source rows: %d, Lines created: %d, Skipped (no BP): %d, Skipped (zero/neg amt): %d",
            batchesCreated, sourceRows, createdLines, skippedNoBP, skippedZeroAmt
        );
    }

    // ---- lazy per-year batch creation ----
    private MInvoiceBatch_New ensureBatchForYear(String yearKey, X_ZZ_Monthly_Levy_Files_Hdr hdr, int currencyID) {
    	MInvoiceBatch_New batch = batchByYear.get(yearKey);
        if (batch != null) return batch;

        batch = new MInvoiceBatch_New(m_ctx, 0, get_TrxName());
        batch.setAD_Org_ID(1000002);  // fin
        batch.setDateDoc(p_DateDoc);
        batch.setC_Currency_ID(currencyID);
        batch.setSalesRep_ID(getAD_User_ID());  
        batch.setZZ_Monthly_Levy_Files_Hdr_ID(hdr.get_ID());   // your header reference
        batch.setZZ_Status(X_C_InvoiceBatch.ZZ_STATUS_Drafted);

        String name = "Levy Batch Hdr#" + hdr.get_ID();
        if (!safe(hdr.getZZ_Month()).isEmpty()) name += " M" + hdr.getZZ_Month();
        name += " " + yearKey;
        //batch.setDocumentNo(name);
        batch.setDescription("Generated from ZZ_Monthly_Levy_Files_Hdr ID " + hdr.get_ID() + " for ZZ_Year=" + yearKey);
        batch.setDocumentAmt(Env.ZERO); // start at zero; we’ll accumulate
        batch.saveEx();

        batchByYear.put(yearKey, batch);
        batchesCreated++;

        // seed lineNo for this batch
        int start = DB.getSQLValue(get_TrxName(),
                "SELECT NVL(MAX(Line),0) FROM C_InvoiceBatchLine WHERE C_InvoiceBatch_ID=?",
                batch.getC_InvoiceBatch_ID());
        lineNoByYear.put(yearKey, Math.max(0, start));
        return batch;
    }

    private int nextLineNo(String yearKey, int C_InvoiceBatch_ID) {
        Integer ln = lineNoByYear.get(yearKey);
        if (ln == null) {
            ln = DB.getSQLValue(get_TrxName(),
                "SELECT NVL(MAX(Line),0) FROM C_InvoiceBatchLine WHERE C_InvoiceBatch_ID=?",
                C_InvoiceBatch_ID);
            if (ln < 0) ln = 0;
        }
        ln += 10;
        lineNoByYear.put(yearKey, ln);
        return ln;
    }

    // ---- line creation ----
    private void createLine(
            int C_InvoiceBatch_ID,
            int C_DocType_ID,
            int seqCounter,
            int lineNo,
            int C_BPartner_ID,
            int C_BPartner_Location_ID,
            int C_Charge_ID,
            Timestamp date,
            BigDecimal amount,
            String tag,
            X_ZZ_Monthly_Levy_Files src) {

        X_C_InvoiceBatchLine line = new X_C_InvoiceBatchLine(m_ctx, 0, get_TrxName());
        line.setAD_Org_ID(1000002);
        line.setC_InvoiceBatch_ID(C_InvoiceBatch_ID);
        line.setC_DocType_ID(C_DocType_ID);
        line.setLine(lineNo);

        line.setC_BPartner_ID(C_BPartner_ID);
        if (C_BPartner_Location_ID > 0) line.setC_BPartner_Location_ID(C_BPartner_Location_ID);

        line.setC_Charge_ID(C_Charge_ID);
        line.setIsTaxIncluded(false);
        line.setC_Tax_ID(1000000);
        line.setDateInvoiced(date);
        line.setDateAcct(date);

        line.setQtyEntered(Env.ONE);
        line.setPriceEntered(amount);
        line.setLineNetAmt(amount);
        line.setLineTotalAmt(amount);

        StringBuilder desc = new StringBuilder();
        desc.append(tag).append(" from ZZ_Monthly_Levy_Files ID ").append(src.get_ID());
        final String fn = safe(src.getZZ_File_Name());
        if (!fn.isEmpty()) desc.append(" (").append(fn).append(")");
        final String month = safe(src.getZZ_Month());
        final String year = safe(src.getZZ_Year());
        if (!month.isEmpty()) desc.append(" M").append(month);
        if (!year.isEmpty()) desc.append(" ").append(year);
        line.setDescription(desc.toString());

        line.saveEx();

        // link back (note: only holds one line id; if you add DG later, last one wins)
        src.setC_InvoiceBatchLine_ID(line.getC_InvoiceBatchLine_ID());
        src.saveEx();
    }

    private int getBillToOrAnyBPLocation(MBPartner bp) {
        MBPartnerLocation[] locs = bp.getLocations(true);
        MBPartnerLocation any = null;
        for (MBPartnerLocation l : locs) {
            if (!l.isActive()) continue;
            if (any == null) any = l;
            if (l.isBillTo()) return l.getC_BPartner_Location_ID();
        }
        return any != null ? any.getC_BPartner_Location_ID() : 0;
    }
    
 // helper: does BP have an Approved WSP/ATR for the given year?
    private boolean hasApprovedForYear(int cBPartnerId, String yearKey) {
        if (cBPartnerId <= 0 || yearKey == null || yearKey.trim().isEmpty()) return false;
        final String sql =
            "SELECT 1 " +
            "FROM ZZ_WSP_ATR_Approvals " +
            "WHERE C_BPartner_ID=? " +
            "  AND ZZ_Grant_Status='A' " +            // Approved
            "  AND IsActive = 'Y'" +
            "  AND COALESCE(TRIM(ZZ_Financial_Year),'') = TRIM(?) " +
            "FETCH FIRST 1 ROWS ONLY";
        Integer one = DB.getSQLValue(get_TrxName(), sql, cBPartnerId, yearKey.trim());
        return one != null && one == 1;
    }

    private static String safe(String s) { return s == null ? "" : s.trim(); }
    private static BigDecimal nz(BigDecimal b) { return b == null ? Env.ZERO : b; }
    private static boolean isNegative(BigDecimal b) { return b != null && b.compareTo(Env.ZERO) < 0; }
}
