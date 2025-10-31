package za.co.ntier.wf.process;

import java.util.Properties;

import org.compiere.model.MUserRoles;
import org.compiere.model.PO;
import org.compiere.util.Env;

import za.co.ntier.wf.model.MZZWFLineRole;
import za.co.ntier.wf.model.MZZWFLines;
import za.co.ntier.wf.util.ADColumnUtil;

public final class AuthUtil {
	private AuthUtil() {}
	public static boolean isActorAuthorized(Properties ctx, String trxName, MZZWFLines step, PO po, int actorUserId) {
		int colId = step.getZZ_Specific_Responsible_Col_ID();
		if (colId > 0) {
			String colName = ADColumnUtil.getColumnName(ctx, colId, trxName);
			int uid = po.get_ValueAsInt(colName);
			if (uid == actorUserId) return true;
		}
		int currentRole_ID = Env.getAD_Role_ID(ctx);
		if (step.getZZ_Resp_Role_ID() > 0) {
			if (currentRole_ID == step.getZZ_Resp_Role_ID()) return true;
		}
		return false;
	}
}
