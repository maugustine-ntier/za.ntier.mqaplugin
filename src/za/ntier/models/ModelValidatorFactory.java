package za.ntier.models;

import org.adempiere.base.IModelValidatorFactory;
import org.compiere.model.ModelValidator;
import org.osgi.service.component.annotations.Component;

import za.ntier.modelvalidator.NtierModelValidator;


@Component(

		 property= {"service.ranking:Integer=100"},
		 service = org.adempiere.base.IModelValidatorFactory.class
		 )
public class ModelValidatorFactory implements IModelValidatorFactory {

	@Override
	public ModelValidator newModelValidatorInstance(String className) {
		if (className.equals("MStockPile")) {
			return new NtierModelValidator();
		}
		return null;
	}

}
