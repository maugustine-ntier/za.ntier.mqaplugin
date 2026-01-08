package za.co.ntier.wsp_atr.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import za.co.ntier.wsp_atr.models.X_ZZ_Qualification_Type_Details_Ref;
@org.adempiere.base.annotation.Process(
		name = "za.co.ntier.wsp_atr.process.PopulateQualificationTypeDetailsRef")
public class PopulateQualificationTypeDetailsRef extends SvrProcess {

    // Parameters
    private boolean deleteExisting = false;
    private boolean onlyActive = true;

    private int imported = 0;
    private int skipped = 0;

    private static class SourceDef {
        final String tableName;
        final String typeCode; // optional if your target has ZZ_Qualification_Type
        SourceDef(String tableName, String typeCode) {
            this.tableName = tableName;
            this.typeCode = typeCode;
        }
    }

    private final List<SourceDef> sources = new ArrayList<SourceDef>() {{
        add(new SourceDef("ZZ_Internship_Ref", "INTERNSHIP"));
        add(new SourceDef("ZZ_Bursary_Ref", "BURSARY"));
        add(new SourceDef("ZZ_Short_Course_Ref", "SHORT_COURSE"));
        add(new SourceDef("ZZ_Adult_Education_and_Training_Ref", "AET"));
    }};

    @Override
    protected void prepare() {
        ProcessInfoParameter[] para = getParameter();
        if (para == null)
            return;

        for (ProcessInfoParameter p : para) {
            String name = p.getParameterName();
            if (p.getParameter() == null)
                continue;

            if ("DeleteExisting".equalsIgnoreCase(name)) {
                deleteExisting = "Y".equalsIgnoreCase(p.getParameterAsString());
            } else if ("OnlyActive".equalsIgnoreCase(name)) {
                onlyActive = "Y".equalsIgnoreCase(p.getParameterAsString());
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        final int clientId = Env.getAD_Client_ID(getCtx());
        final int orgId = Env.getAD_Org_ID(getCtx());

        if (deleteExisting) {
            int deleted = DB.executeUpdateEx(
                "DELETE FROM ZZ_Qualification_Type_Details_Ref WHERE AD_Client_ID=?",
                new Object[]{ clientId },
                get_TrxName()
            );
            addLog("Deleted existing target rows: " + deleted);
        }

        for (SourceDef src : sources) {
            importFromSource(clientId, orgId, src);
        }

        return "Done. Imported=" + imported + ", Skipped=" + skipped;
    }

    private void importFromSource(int clientId, int orgId, SourceDef src) {

        String sql =
              "SELECT Value, Name "
            + "FROM " + src.tableName + " "
            + "WHERE AD_Client_ID=? "
            + (onlyActive ? "AND IsActive='Y' " : "")
            + "AND Value IS NOT NULL "
            + "ORDER BY Value";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DB.prepareStatement(sql, get_TrxName());
            pstmt.setInt(1, clientId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String value = rs.getString(1);
                String name = rs.getString(2);

                if (value == null || value.trim().isEmpty()) {
                    skipped++;
                    continue;
                }

                // Duplicate check in target (per client + value)
                if (existsInTarget(clientId, value, src.typeCode)) {
                    skipped++;
                    continue;
                }

                X_ZZ_Qualification_Type_Details_Ref target =
                    new X_ZZ_Qualification_Type_Details_Ref(getCtx(), 0, get_TrxName());

                target.set_ValueOfColumn(X_ZZ_Qualification_Type_Details_Ref.COLUMNNAME_AD_Client_ID, clientId);
                target.setAD_Org_ID(orgId);

                target.setValue(value.trim());
                target.setName(name != null ? name.trim() : value.trim());

                // If your target table has these columns, they’ll be populated
                setIfColumnExists(target, "ZZ_Qualification_Type", src.typeCode);
                setIfColumnExists(target, "SourceTable", src.tableName);

                target.saveEx();
                imported++;
            }

            addLog("Imported from " + src.tableName + " (" + src.typeCode + ")");

        } catch (Exception e) {
            throw new RuntimeException("Error importing from " + src.tableName + ": " + e.getMessage(), e);
        } finally {
            DB.close(rs, pstmt);
        }
    }

    /**
     * Duplicate check:
     * - Always checks AD_Client_ID + Value
     * - If target has ZZ_Qualification_Type column, also checks it (so same Value can exist in different types)
     */
    private boolean existsInTarget(int clientId, String value, String typeCode) {

        // If your target has ZZ_Qualification_Type we prefer to scope duplicates by type.
        // Otherwise we just check by Value.
        boolean hasTypeColumn = X_ZZ_Qualification_Type_Details_Ref.COLUMNNAME_Value != null; // just to avoid warnings

        // We can detect column existence by instantiating a dummy PO and checking column index.
        X_ZZ_Qualification_Type_Details_Ref dummy =
            new X_ZZ_Qualification_Type_Details_Ref(getCtx(), 0, get_TrxName());

        boolean targetHasType = dummy.get_ColumnIndex("ZZ_Qualification_Type") >= 0;

        String sql;
        Object[] params;

        if (targetHasType) {
            sql = "SELECT 1 FROM ZZ_Qualification_Type_Details_Ref "
                + "WHERE AD_Client_ID=? AND Value=? AND COALESCE(ZZ_Qualification_Type,'')=COALESCE(?, '')";
            params = new Object[]{ clientId, value, typeCode };
        } else {
            sql = "SELECT 1 FROM ZZ_Qualification_Type_Details_Ref "
                + "WHERE AD_Client_ID=? AND Value=?";
            params = new Object[]{ clientId, value };
        }

        int exists = DB.getSQLValueEx(get_TrxName(), sql, params);
        return exists == 1;
    }

    private void setIfColumnExists(X_ZZ_Qualification_Type_Details_Ref po, String columnName, Object value) {
        if (value == null)
            return;
        if (po.get_ColumnIndex(columnName) >= 0) {
            po.set_ValueOfColumn(columnName, value);
        }
    }
}
