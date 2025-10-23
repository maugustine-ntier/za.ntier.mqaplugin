package za.ntier.repo;

import java.util.Properties;

import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.Query;

public class PartnerRepository {
    private final Properties ctx; private final String trx;

    public PartnerRepository(Properties ctx, String trx) { this.ctx = ctx; this.trx = trx; }

    public MBPartner findActiveByValue(String value) {
        return new Query(ctx, MBPartner.Table_Name, "Value=? AND IsActive='Y'", trx)
                .setParameters(value).setOnlyActiveRecords(true).first();
    }

    public int getBillToOrAnyLocation(MBPartner bp) {
        MBPartnerLocation[] locs = bp.getLocations(true);
        MBPartnerLocation any = null;
        for (MBPartnerLocation l : locs) {
            if (!l.isActive()) continue;
            if (any == null) any = l;
            if (l.isBillTo()) return l.getC_BPartner_Location_ID();
        }
        return any != null ? any.getC_BPartner_Location_ID() : 0;
    }
}
