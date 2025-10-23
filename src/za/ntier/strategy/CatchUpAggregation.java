package za.ntier.strategy;

import java.util.*;

import za.ntier.models.X_ZZ_Monthly_Levy_Files;

/** For approvals ≥ minCatchUpYear (e.g. 2025): aggregate 2010..approvedYear. */
public class CatchUpAggregation implements ApprovalAggregationStrategy {

    private final int minCatchUpYear;
    private final int maxCatchUpYear;

    public CatchUpAggregation(int minCatchUpYear, int maxCatchUpYear) {
        this.minCatchUpYear = minCatchUpYear;
        this.maxCatchUpYear = maxCatchUpYear;
    }

    @Override
    public Map<Integer, List<X_ZZ_Monthly_Levy_Files>> contributingRows(
            Map<Integer, List<X_ZZ_Monthly_Levy_Files>> rowsByYear, int approvedYear) {

        if (approvedYear < minCatchUpYear || approvedYear > maxCatchUpYear) return Collections.emptyMap();

        List<Integer> years = new ArrayList<>(rowsByYear.keySet());
        Collections.sort(years);

        Map<Integer, List<X_ZZ_Monthly_Levy_Files>> out = new LinkedHashMap<>();
        for (Integer y : years) {
            if (y < minCatchUpYear || y > approvedYear) continue;
            List<X_ZZ_Monthly_Levy_Files> list = rowsByYear.get(y);
            if (list == null) continue;
            List<X_ZZ_Monthly_Levy_Files> unlinked = new ArrayList<>();
            for (X_ZZ_Monthly_Levy_Files r : list) {
                if (r.getC_InvoiceBatchLine_ID() == 0) unlinked.add(r);
            }
            if (!unlinked.isEmpty()) out.put(y, unlinked);
        }
        return out;
    }
}
