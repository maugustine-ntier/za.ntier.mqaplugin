package za.co.ntier.wsp_atr.process;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for template tab validators.
 * Add new tab validators here as you extend the template.
 */
public class TemplateTabValidatorFactory {

    public static List<ITemplateTabValidator> getValidators() {
        List<ITemplateTabValidator> list = new ArrayList<>();

        // First tab: Biodata
        list.add(new BiodataTabValidator());

        // Later:
        // list.add(new EmploymentHistoryTabValidator());
        // list.add(new TrainingTabValidator());
        // etc.

        return list;
    }
}

