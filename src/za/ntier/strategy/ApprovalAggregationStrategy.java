package za.ntier.strategy;

import java.util.List;
import java.util.Map;
import za.ntier.models.X_ZZ_Monthly_Levy_Files;

public interface ApprovalAggregationStrategy {
    Map<Integer, List<X_ZZ_Monthly_Levy_Files>> contributingRows(
        Map<Integer, List<X_ZZ_Monthly_Levy_Files>> rowsByYear,
        int approvedYear
    );
}
