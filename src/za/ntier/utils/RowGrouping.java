package za.ntier.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import za.ntier.models.X_ZZ_Monthly_Levy_Files;
import za.ntier.repo.PartnerRepository;

public final class RowGrouping {
    private RowGrouping() {}

    public static class GroupResult {
        public final Map<Integer, Map<Integer, java.util.List<X_ZZ_Monthly_Levy_Files>>> rowsByBPYear = new LinkedHashMap<>();
        public final Map<Integer, org.compiere.model.MBPartner> bpById = new LinkedHashMap<>();
        public final Map<Integer, Integer> bpLocById = new LinkedHashMap<>();
        public int skippedNoBP = 0;
        public int skippedNotAL = 0;
        public int skippedZero = 0;
    }

    public static GroupResult groupByBpYear(
            java.util.Properties ctx, String trx,
            java.util.List<X_ZZ_Monthly_Levy_Files> rows,
            PartnerRepository partners
    ) {
        GroupResult res = new GroupResult();
        for (X_ZZ_Monthly_Levy_Files rec : rows) {
            if (rec.getC_InvoiceBatchLine_ID() != 0) continue; // already linked
            String sdl = safe(rec.getZZ_SDL_No());
            if (sdl.isEmpty() || !sdl.startsWith("L")) { res.skippedNotAL++; continue; }

            org.compiere.model.MBPartner bp = partners.findActiveByValue(sdl);
            if (bp == null) { res.skippedNoBP++; continue; }

            res.bpById.put(bp.getC_BPartner_ID(), bp);
            res.bpLocById.computeIfAbsent(bp.getC_BPartner_ID(), k -> partners.getBillToOrAnyLocation(bp));

            Integer y = YearUtil.parseYear(safe(rec.getZZ_Year()));
            if (y == null || y < 2010) continue;

            res.rowsByBPYear.computeIfAbsent(bp.getC_BPartner_ID(), k -> new LinkedHashMap<>())
                    .computeIfAbsent(y, k -> new java.util.ArrayList<>())
                    .add(rec);
        }
        return res;
    }

    private static String safe(String s) { return s == null ? "" : s.trim(); }
}
