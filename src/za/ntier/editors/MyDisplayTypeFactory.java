package za.ntier.editors;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.adempiere.base.IDisplayTypeFactory;
import org.compiere.db.AdempiereDatabase;
import org.compiere.db.Database;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Language;
import org.osgi.service.component.annotations.Component;


@Component(

		 property= {"service.ranking:Integer=200"},
		 service = org.adempiere.base.IDisplayTypeFactory.class
		 )
public class MyDisplayTypeFactory implements IDisplayTypeFactory {
	public static final int RadioGroupListVertical = 1000003;

	@Override
	public boolean isID(int displayType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isList(int displayType) {
		if (displayType == RadioGroupListVertical)
			return true;
		return IDisplayTypeFactory.super.isList(displayType);
	}

	@Override
	public boolean isNumeric(int displayType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getDefaultPrecision(int displayType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isText(int displayType) {
		if (displayType == RadioGroupListVertical)
			return true;
		return false;
	}

	@Override
	public boolean isDate(int displayType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLookup(int displayType) {
		if (displayType == RadioGroupListVertical)
			return true;
		return false;
	}

	@Override
	public boolean isLOB(int displayType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DecimalFormat getNumberFormat(int displayType, Language language, String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SimpleDateFormat getDateFormat(int displayType, Language language, String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getClass(int displayType, boolean yesNoAsBoolean) {
		if ( displayType == RadioGroupListVertical)
			return String.class;
		return null;
	}

	@Override
	public String getSQLDataType(int displayType, String columnName, int fieldLength) {
		if (displayType == RadioGroupListVertical) {
			if (fieldLength == 1)
				return getDatabase().getCharacterDataType()+"(" + fieldLength + ")";
			else
				return getDatabase().getVarcharDataType()+"(" + fieldLength + getDatabase().getVarcharLengthSuffix() + ")";
		}
		return null;
	}

	@Override
	public String getDescription(int displayType) {
		if (displayType == RadioGroupListVertical)
			return "RadiogroupListVert";
		return null;
	}
	private static final AdempiereDatabase getDatabase() 
	{
		AdempiereDatabase db = DB.getDatabase();
		if (db.isNativeMode())
			return db;
		else
			return Database.getDatabase(Database.DB_ORACLE);
	}

}
