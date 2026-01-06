package za.ntier.models;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Properties;

public final class OpenAppOverlapInput {
    private final Properties ctx;
    private final String trxName;
    private final int cYearId;
    private final int openApplicationId;
    private final Timestamp startDate;
    private final Timestamp endDate;

    public OpenAppOverlapInput(
            Properties ctx,
            String trxName,
            int cYearId,
            int openApplicationId,
            Timestamp startDate,
            Timestamp endDate) {

        this.ctx = Objects.requireNonNull(ctx, "ctx");
        this.trxName = trxName; // can be null in iDempiere, that’s OK
        this.cYearId = cYearId;
        this.openApplicationId = openApplicationId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Properties getCtx() { return ctx; }
    public String getTrxName() { return trxName; }
    public int getC_Year_ID() { return cYearId; }                 // X_ZZ_Open_Application#getC_Year_ID -> int
    public int getZZ_Open_Application_ID() { return openApplicationId; } // X_ZZ_Open_Application#getZZ_Open_Application_ID -> int
    public Timestamp getStartDate() { return startDate; }         // X_ZZ_Open_Application#getStartDate -> Timestamp
    public Timestamp getEndDate() { return endDate; }             // X_ZZ_Open_Application#getEndDate -> Timestamp
}
