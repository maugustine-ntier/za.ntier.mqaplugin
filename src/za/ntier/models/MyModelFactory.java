package za.ntier.models;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.osgi.service.component.annotations.Component;

@Component(

		 property= {"service.ranking:Integer=2"},
		 service = org.adempiere.base.IModelFactory.class
		 )
public class MyModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		if (tableName.equals(I_C_Invoice.Table_Name)) {
			return MInvoice_New.class;
		}
		if (tableName.equals(X_M_InOut.Table_Name)) {
			return MInOut_New.class;
		}
			
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		if (tableName.equals(I_C_Invoice.Table_Name)) {
			return new MInvoice_New(Env.getCtx(),Record_ID,trxName);
		}
		if (tableName.equals(X_M_InOut.Table_Name)) {
			return new MInOut_New(Env.getCtx(),Record_ID,trxName);
		}
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		if (tableName.equals(I_C_Invoice.Table_Name)) {
			return new MInvoice_New(Env.getCtx(),rs,trxName);
		}
		if (tableName.equals(X_M_InOut.Table_Name)) {
			return new MInOut_New(Env.getCtx(),rs,trxName);
		}
			
		return null;
	}

}
