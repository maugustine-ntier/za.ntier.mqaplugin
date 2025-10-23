package za.ntier.strategy;

import java.util.*;

import za.ntier.models.X_ZZ_Monthly_Levy_Files;

/** For approvals ≤ maxLegacyYear (e.g. 2024): aggregate exactly that year. */
public class LegacyYearAggregation implements ApprovalAggregationStrategy {

    private final int minYear;
    private final int maxLegacyYear;

    public LegacyYearAggregation(int minYear, int maxLegacyYear) {
        this.minYear = minYear;
        this.maxLegacyYear = maxLegacyYear;
    }

    @Override
    public Map<Integer, List<X_ZZ_Monthly_Levy_Files>> contributingRows(
            Map<Integer, List<X_ZZ_Monthly_Levy_Files>> rowsByYear, int approvedYear) {

        if (approvedYear < minYear || approvedYear > maxLegacyYear) return Collections.emptyMap();
        List<X_ZZ_Monthly_Levy_Files> list = rowsByYear.get(approvedYear);
        if (list == null || list.isEmpty()) return Collections.emptyMap();

        // Only unlinked rows (C_InvoiceBatchLine_ID = 0)
        List<X_ZZ_Monthly_Levy_Files> unlinked = new ArrayList<>();
        for (X_ZZ_Monthly_Levy_Files r : list) {
            if (r.getC_InvoiceBatchLine_ID() == 0) unlinked.add(r);
        }
        if (unlinked.isEmpty()) return Collections.emptyMap();

        Map<Integer, List<X_ZZ_Monthly_Levy_Files>> out = new LinkedHashMap<>();
        out.put(approvedYear, unlinked);
        return out;
    }
}
