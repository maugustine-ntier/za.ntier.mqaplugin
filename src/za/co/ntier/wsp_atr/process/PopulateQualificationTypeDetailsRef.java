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

    private boolean deleteExisting = false;
    private boolean onlyActive = true;

    private int imported = 0;
    private int skipped = 0;

    // Value format
    private static final int VALUE_PAD_LEN = 5;

    private static class SourceDef {
        final String tableName;
        final String typeCode;
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

        // Start from current max numeric Value in target (client-scoped)
        int nextSeq = getNextValueSeq(clientId);

        for (SourceDef src : sources) {
            nextSeq = importFromSource(clientId, orgId, src, nextSeq);
        }

        return "Done. Imported=" + imported + ", Skipped=" + skipped;
    }

    /**
     * Imports one source table and returns the next sequence number to use.
     */
    private int importFromSource(int clientId, int orgId, SourceDef src, int nextSeq) {

        String sql =
              "SELECT Name "
            + "FROM " + src.tableName + " "
            + "WHERE AD_Client_ID=? "
            + (onlyActive ? "AND IsActive='Y' " : "")
            + "AND Name IS NOT NULL "
            + "ORDER BY Name";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // detect whether target has ZZ_Qualification_Type column once
        boolean targetHasTypeColumn = hasTargetColumn("ZZ_Qualification_Type");

        try {
            pstmt = DB.prepareStatement(sql, get_TrxName());
            pstmt.setInt(1, clientId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString(1);
                if (name == null) {
                    skipped++;
                    continue;
                }
                name = name.trim();
                if (name.isEmpty()) {
                    skipped++;
                    continue;
                }

                // Duplicate check BY NAME (case-insensitive, trimmed)
                if (existsByName(clientId, name, targetHasTypeColumn ? src.typeCode : null, targetHasTypeColumn)) {
                    skipped++;
                    continue;
                }

                String newValue = pad(nextSeq, VALUE_PAD_LEN);

                X_ZZ_Qualification_Type_Details_Ref target =
                    new X_ZZ_Qualification_Type_Details_Ref(getCtx(), 0, get_TrxName());

                
                target.setAD_Org_ID(orgId);

                target.setValue(newValue);
                target.setName(name);

                setIfColumnExists(target, "ZZ_Qualification_Type", src.typeCode);
                setIfColumnExists(target, "SourceTable", src.tableName);

                target.saveEx();

                imported++;
                nextSeq++;
            }

            addLog("Imported from " + src.tableName + " (" + src.typeCode + ")");

        } catch (Exception e) {
            throw new RuntimeException("Error importing from " + src.tableName + ": " + e.getMessage(), e);
        } finally {
            DB.close(rs, pstmt);
        }

        return nextSeq;
    }

    /**
     * Returns next integer sequence based on max numeric Value in target for this client.
     * Ignores non-numeric Values safely.
     */
    private int getNextValueSeq(int clientId) {

        // Works on PostgreSQL
        // Max of numeric-only Values; if none, start at 1
        String sql =
            "SELECT COALESCE(MAX(CAST(Value AS INTEGER)), 0) "
          + "FROM ZZ_Qualification_Type_Details_Ref "
          + "WHERE AD_Client_ID=? "
          + "  AND Value ~ '^[0-9]+$'";

        int max = DB.getSQLValueEx(get_TrxName(), sql, clientId);
        return max + 1;
    }

    /**
     * Duplicate check by Name (case-insensitive).
     * If targetHasTypeColumn=true, we scope by ZZ_Qualification_Type as well.
     */
    private boolean existsByName(int clientId, String name, String typeCode, boolean targetHasTypeColumn) {

        String sql;
        Object[] params;

        if (targetHasTypeColumn) {
            sql = "SELECT 1 FROM ZZ_Qualification_Type_Details_Ref "
                + "WHERE AD_Client_ID=? "
                + "  AND LOWER(TRIM(Name)) = LOWER(TRIM(?)) "
                + "  AND COALESCE(ZZ_Qualification_Type,'') = COALESCE(?, '')";
            params = new Object[]{ clientId, name, typeCode };
        } else {
            sql = "SELECT 1 FROM ZZ_Qualification_Type_Details_Ref "
                + "WHERE AD_Client_ID=? "
                + "  AND LOWER(TRIM(Name)) = LOWER(TRIM(?))";
            params = new Object[]{ clientId, name };
        }

        int exists = DB.getSQLValueEx(get_TrxName(), sql, params);
        return exists == 1;
    }

    private boolean hasTargetColumn(String columnName) {
        X_ZZ_Qualification_Type_Details_Ref dummy =
            new X_ZZ_Qualification_Type_Details_Ref(getCtx(), 0, get_TrxName());
        return dummy.get_ColumnIndex(columnName) >= 0;
    }

    private void setIfColumnExists(X_ZZ_Qualification_Type_Details_Ref po, String columnName, Object value) {
        if (value == null)
            return;
        if (po.get_ColumnIndex(columnName) >= 0) {
            po.set_ValueOfColumn(columnName, value);
        }
    }

    private String pad(int number, int len) {
        String s = String.valueOf(number);
        if (s.length() >= len) return s;
        StringBuilder sb = new StringBuilder(len);
        for (int i = s.length(); i < len; i++) sb.append('0');
        sb.append(s);
        return sb.toString();
    }
}
