package za.co.ntier.wf.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

import za.ntier.models.X_ZZ_WF_Line_Role;

public class MZZWFLineRole extends X_ZZ_WF_Line_Role {
	private static final long serialVersionUID = 1L;
	public MZZWFLineRole(Properties ctx, int id, String trxName) { 
		super(ctx, id, trxName); 
	}

	/** Load Constructor */
	public MZZWFLineRole (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}
	public static List<Integer> getRoleIds(int wfLinesId, Properties ctx, String trxName) {
		List<X_ZZ_WF_Line_Role> rows = new Query(ctx, Table_Name, "ZZ_WF_Lines_ID=? AND IsActive='Y'", trxName)
				.setParameters(wfLinesId).setOnlyActiveRecords(true).list();
		List<Integer> out = new ArrayList<>(rows.size());
		for (X_ZZ_WF_Line_Role r : rows) out.add(r.getAD_Role_ID());
		return out;
	}
}
