package za.ntier.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MBPartner;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import za.ntier.models.X_ZZ_Levy_Paying;



@org.adempiere.base.annotation.Process(name="za.ntier.process.ImportPaidLevies")
public class ImportPaidLevies extends SvrProcess {

    @Override
    protected void prepare() {
        // no parameters
    }

    @Override
    protected String doIt() throws Exception {
        String sql = "SELECT value FROM paid_levies";
        int created = 0;

        try (PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName());
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String value = rs.getString("value");
                if (value == null || value.trim().isEmpty())
                    continue;

                // Find BP with this value
                MBPartner bp = MBPartner.get(Env.getCtx(), value.trim());
                if (bp != null && bp.get_ID() > 0) {
                    // Create Levy Paying record
                    X_ZZ_Levy_Paying levy = new X_ZZ_Levy_Paying(getCtx(), 0, get_TrxName());
                    levy.setC_BPartner_ID(bp.getC_BPartner_ID());
                    levy.setC_Year_ID(1000000);
                    levy.setZZ_LevyPaying(true);
                    levy.setName(bp.getName()); // optional
                    levy.setValue(bp.getValue()); // optional
                    levy.saveEx();

                    created++;
                }
            }
        }

        return "Created " + created + " Levy Paying records.";
    }
}
