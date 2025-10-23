package za.ntier.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;

import za.ntier.model.Approval;
import za.ntier.utils.YearUtil;

public class ApprovalsRepository {
    private final Properties ctx; private final String trx;

    public ApprovalsRepository(Properties ctx, String trx) { this.ctx = ctx; this.trx = trx; }

    public List<Approval> loadApprovalsAscForBps(List<Integer> bpIds) {
        List<Approval> out = new ArrayList<>();
        if (bpIds.isEmpty()) return out;
        String in = String.join(",", bpIds.stream().map(String::valueOf).toArray(String[]::new));
        String sql = "SELECT C_BPartner_ID, TRIM(ZZ_Financial_Year) AS yr " +
                     "FROM ZZ_WSP_ATR_Approvals " +
                     "WHERE ZZ_Grant_Status='A' AND IsActive='Y' AND C_BPartner_ID IN ("+in+")";
        List<List<Object>> rows = DB.getSQLArrayObjectsEx(trx, sql, new Object[]{});
        if (rows == null) return out;
        for (List<Object> r : rows) {
            Integer bpId = (Integer) r.get(0);
            String yrStr = (String) r.get(1);
            Integer y = YearUtil.parseYear(yrStr);
            if (y != null) out.add(new Approval(bpId, y));
        }
        out.sort((a,b)->{
            int c = Integer.compare(a.year(), b.year());
            return c != 0 ? c : Integer.compare(a.bpId(), b.bpId());
        });
        return out;
    }

    public boolean hasApprovedForYear(int bpId, String yearKey) {
        String sql = "SELECT 1 FROM ZZ_WSP_ATR_Approvals WHERE C_BPartner_ID=? " +
                     "AND ZZ_Grant_Status='A' AND IsActive='Y' AND COALESCE(TRIM(ZZ_Financial_Year),'')=TRIM(?) " +
                     "FETCH FIRST 1 ROWS ONLY";
        Integer one = DB.getSQLValue(trx, sql, bpId, yearKey);
        return one != null && one == 1;
    }
}
