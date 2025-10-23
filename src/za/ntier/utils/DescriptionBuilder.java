package za.ntier.utils;

import java.math.BigDecimal;
import java.util.Map;

public final class DescriptionBuilder {
    private DescriptionBuilder() {}

    /** Example: "MG aggregated via approval year 2025 from Hdr ID 123 {2018=123.45, 2019=456.78}" */
    public static String buildAggregateDescription(String tag, int hdrId, int approvalYear,
                                                   Map<Integer, BigDecimal> perYearShown) {
        StringBuilder sb = new StringBuilder();
        sb.append(tag).append(" aggregated via approval year ").append(approvalYear)
          .append(" from ZZ_Monthly_Levy_Files_Hdr ID ").append(hdrId)
          .append(" {");
        boolean first = true;
        for (Map.Entry<Integer, BigDecimal> e : perYearShown.entrySet()) {
            if (!first) sb.append(", ");
            first = false;
            sb.append(e.getKey()).append("=").append(e.getValue());
        }
        sb.append("}");
        return sb.toString();
    }
}
