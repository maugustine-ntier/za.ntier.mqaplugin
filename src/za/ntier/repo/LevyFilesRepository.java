package za.ntier.repo;

import java.util.*;
import java.util.Map.Entry;
import java.util.Properties;

import org.compiere.model.Query;

import za.ntier.models.X_ZZ_Monthly_Levy_Files;
import za.ntier.models.X_ZZ_Monthly_Levy_Files_Hdr;

public class LevyFilesRepository {
    private final Properties ctx; private final String trx;

    public LevyFilesRepository(Properties ctx, String trx) { this.ctx = ctx; this.trx = trx; }

    public X_ZZ_Monthly_Levy_Files_Hdr getHeaderById(int id) {
        return new X_ZZ_Monthly_Levy_Files_Hdr(ctx, id, trx);
    }

    public List<X_ZZ_Monthly_Levy_Files> getUnprocessedRows(int hdrId) {
        return new Query(ctx, X_ZZ_Monthly_Levy_Files.Table_Name,
                "ZZ_Monthly_Levy_Files_Hdr_ID=? AND IsActive='Y' AND C_InvoiceBatchLine_ID IS NULL", trx)
                .setParameters(hdrId).setOnlyActiveRecords(true).list();
    }

    public void linkRowsToLine(Map<Integer, List<X_ZZ_Monthly_Levy_Files>> rowsByYear, int lineId) {
        for (Entry<Integer, List<X_ZZ_Monthly_Levy_Files>> e : rowsByYear.entrySet()) {
            for (X_ZZ_Monthly_Levy_Files r : e.getValue()) {
                if (r.getC_InvoiceBatchLine_ID() == 0) {
                    r.setC_InvoiceBatchLine_ID(lineId);
                    r.saveEx();
                }
            }
        }
    }
}
