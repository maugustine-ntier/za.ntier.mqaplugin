package za.ntier.repo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Properties;

import org.compiere.model.MInvoiceBatchLine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import za.ntier.models.MInvoiceBatch_New;
import za.ntier.models.X_C_InvoiceBatch;
import za.ntier.models.X_ZZ_Monthly_Levy_Files_Hdr;

public class BatchRepository {

    private final Properties ctx; private final String trx; private final int adUserId; private final Timestamp dateDoc;

    public BatchRepository(Properties ctx, String trx, int adUserId, Timestamp dateDoc) {
        this.ctx = ctx; this.trx = trx; this.adUserId = adUserId; this.dateDoc = dateDoc;
    }

    public int resolveDefaultChargeId() {
        return DB.getSQLValue(trx,
            "SELECT C_Charge_ID FROM C_Charge WHERE UPPER(Name)=UPPER('3010 Grant Expenses - Mandatory') AND IsActive='Y' AND AD_Client_ID=? ORDER BY C_Charge_ID",
            Env.getAD_Client_ID(ctx));
    }

    public int resolveDocTypeId(int preferred) {
        if (preferred > 0) return preferred;
        int id = DB.getSQLValue(trx,
            "SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='ARI' AND IsActive='Y' AND AD_Client_ID=? ORDER BY IsSOTrx DESC, C_DocType_ID",
            Env.getAD_Client_ID(ctx));
        if (id <= 0) {
            id = DB.getSQLValue(trx,
                "SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='API' AND IsActive='Y' AND AD_Client_ID=? ORDER BY C_DocType_ID",
                Env.getAD_Client_ID(ctx));
        }
        return id;
    }

    public int resolveCurrencyId(int fallback) {
        int id = DB.getSQLValue(trx,
            "SELECT C_Currency_ID FROM C_AcctSchema WHERE AD_Client_ID=? AND IsActive='Y' ORDER BY C_AcctSchema_ID",
            Env.getAD_Client_ID(ctx));
        return id > 0 ? id : fallback;
    }

    public MInvoiceBatch_New ensureBatchForYear(Map<String, MInvoiceBatch_New> cache,
                                                X_ZZ_Monthly_Levy_Files_Hdr hdr, int currencyId, String yearKey) {
        MInvoiceBatch_New cached = cache.get(yearKey);
        if (cached != null) return cached;

        MInvoiceBatch_New b = new MInvoiceBatch_New(ctx, 0, trx);
        b.setAD_Org_ID(1000016);
        b.setDateDoc(dateDoc);
        b.setC_Currency_ID(currencyId);
        b.setSalesRep_ID(adUserId);
        b.setZZ_Monthly_Levy_Files_Hdr_ID(hdr.get_ID());
        b.setZZ_Status(X_C_InvoiceBatch.ZZ_STATUS_Drafted);
        b.setZZ_IS_WSP_ATR(true);
        b.setZZ_DocAction(X_C_InvoiceBatch.ZZ_DOCACTION_Recommend);

        String name = "Levy Batch Hdr#" + hdr.get_ID();
        if (hdr.getZZ_Month() != null && !hdr.getZZ_Month().trim().isEmpty()) name += " M" + hdr.getZZ_Month();
        name += " " + yearKey;
        b.setDescription("MG Generated for Year: " + yearKey + " Month: " + hdr.getZZ_Month());
        b.setDocumentAmt(Env.ZERO);
        b.saveEx();

        cache.put(yearKey, b);
        return b;
    }

    public int nextLineNo(Map<String, Integer> lineNoByYear, String yearKey, int batchId) {
        Integer ln = lineNoByYear.get(yearKey);
        if (ln == null) {
            ln = DB.getSQLValue(trx,
                "SELECT COALESCE(MAX(Line),0) FROM C_InvoiceBatchLine WHERE C_InvoiceBatch_ID=?",
                batchId);
            if (ln < 0) ln = 0;
        }
        ln += 10;
        lineNoByYear.put(yearKey, ln);
        return ln;
    }

    public int createBatchLine(int batchId, int docTypeId, int lineNo, int bpId, int bpLocId,
                               int chargeId, Timestamp date, BigDecimal amount, String description) {
    	MInvoiceBatchLine l = new MInvoiceBatchLine(ctx, 0, trx);
        l.setAD_Org_ID(1000016);
        l.setC_InvoiceBatch_ID(batchId);
        l.setC_DocType_ID(docTypeId);
        l.setLine(lineNo);
        l.setC_BPartner_ID(bpId);
        if (bpLocId > 0) l.setC_BPartner_Location_ID(bpLocId);
        l.setC_Charge_ID(chargeId);
        l.setIsTaxIncluded(false);
        l.setC_Tax_ID(1000000);
        l.setDateInvoiced(date);
        l.setDateAcct(date);
        l.setQtyEntered(org.compiere.util.Env.ONE);
        l.setPriceEntered(amount);
        l.setLineNetAmt(amount);
        l.setLineTotalAmt(amount);
        l.setDescription(description);
        l.saveEx();
        return l.getC_InvoiceBatchLine_ID();
    }

    public void updateControlAmt(MInvoiceBatch_New batch) {
    	batch = new MInvoiceBatch_New(ctx, batch.getC_InvoiceBatch_ID(), batch.get_TrxName());  // Refresh values that were updated by batch lines after save
        batch.setControlAmt(batch.getDocumentAmt());
        batch.saveEx();
    }
}
