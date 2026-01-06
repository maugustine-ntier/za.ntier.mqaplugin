package za.ntier.process;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.compiere.util.DB;

public final class RoleIdResolver {

    private static final Map<String, Integer> CACHE = new ConcurrentHashMap<>();

    private RoleIdResolver() {}

    public static int roleIdFromUU(String trxName, String adRoleUU) {
        if (adRoleUU == null || adRoleUU.trim().isEmpty())
            return 0;

        return CACHE.computeIfAbsent(adRoleUU, uu -> {
            String sql = "SELECT AD_Role_ID FROM AD_Role WHERE AD_Role_UU=? AND IsActive='Y'";
            int id = DB.getSQLValueEx(trxName, sql, uu);
            return id; // getSQLValueEx throws if not found; see safe version below if you prefer
        });
    }

    /** Safer: returns 0 if not found (no exception) */
    public static int roleIdFromUUSafe(String trxName, String adRoleUU) {
        if (adRoleUU == null || adRoleUU.trim().isEmpty())
            return 0;

        Integer cached = CACHE.get(adRoleUU);
        if (cached != null)
            return cached;

        String sql = "SELECT AD_Role_ID FROM AD_Role WHERE AD_Role_UU=? AND IsActive='Y'";
        int id = DB.getSQLValue(trxName, sql, adRoleUU); // returns -1 if no row
        if (id > 0) CACHE.put(adRoleUU, id);
        return (id > 0) ? id : 0;
    }
}
