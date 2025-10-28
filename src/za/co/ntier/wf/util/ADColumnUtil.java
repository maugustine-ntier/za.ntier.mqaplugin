package za.co.ntier.wf.util;

import java.util.Properties;
import org.compiere.model.MColumn;
import org.compiere.model.Query;

public final class ADColumnUtil {
    private ADColumnUtil() {}
    public static String getColumnName(Properties ctx, int adColumnId, String trxName) {
        MColumn col = new Query(ctx, MColumn.Table_Name, "AD_Column_ID=?", trxName).setParameters(adColumnId).first();
        return col != null ? col.getColumnName() : null;
    }
}
