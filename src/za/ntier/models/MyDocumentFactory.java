package za.ntier.models;

import java.sql.ResultSet;

import org.adempiere.base.IDocFactory;
import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;
import org.osgi.service.component.annotations.Component;


@Component(

		 property= {"service.ranking:Integer=2","gaap=*"},
		 service = org.adempiere.base.IDocFactory.class
		 )
public class MyDocumentFactory implements IDocFactory {

	@Override
	public Doc getDocument(MAcctSchema as, int AD_Table_ID, ResultSet rs, String trxName) {
		// TODO Auto-generated method stub
		return null;
	}

}
