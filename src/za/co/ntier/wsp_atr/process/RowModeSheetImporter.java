package za.co.ntier.wsp_atr.process;

import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.*;
import org.compiere.model.MColumn;
import org.compiere.model.PO;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Util;

import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Lookup_Mapping;
import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Lookup_Mapping_Detail;
import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Submitted;

public class RowModeSheetImporter extends AbstractMappingSheetImporter {

    // Default to column B if ZZ_Column_Letter is empty
    private static final String DEFAULT_VALUE_COLUMN_LETTER = "B";

    public RowModeSheetImporter(ReferenceLookupService refService) {
        super(refService);
    }

    @Override
    public int importData(Properties ctx,
                          Workbook wb,
                          X_ZZ_WSP_ATR_Submitted submitted,
                          X_ZZ_WSP_ATR_Lookup_Mapping mappingHeader,
                          String trxName,
                          SvrProcess process,
                          DataFormatter formatter) {

        Sheet sheet = getSheetOrThrow(wb, mappingHeader);
        List<X_ZZ_WSP_ATR_Lookup_Mapping_Detail> details =
                loadDetails(mappingHeader, trxName);

        if (details == null || details.isEmpty()) {
        	return 0;
           // throw new org.adempiere.exceptions.AdempiereException(
          //          "No mapping details for tab " + mappingHeader.getZZ_Tab_Name());
        }

        // One row in DB (header-like record)
        PO target = newTargetPO(ctx, submitted, mappingHeader, trxName);

        for (X_ZZ_WSP_ATR_Lookup_Mapping_Detail det : details) {
            int adColId = det.getAD_Column_ID();
            if (adColId <= 0) {
            	return 0;
               // throw new org.adempiere.exceptions.AdempiereException(
                  //      "Mapping detail " + det.get_ID() + " has no AD_Column_ID");
            }

            int rowNo = det.get_ValueAsInt("ZZ_Row_No");
            if (rowNo <= 0) {
            	return 0;
              //  throw new org.adempiere.exceptions.AdempiereException(
                //        "Mapping detail " + det.get_ID() + " has no ZZ_Row_No");
            }

            // Excel row index is 0-based
            Row row = sheet.getRow(rowNo - 1);
            if (row == null) {
            	return 0;
              // throw new org.adempiere.exceptions.AdempiereException(
               //         "Row " + rowNo + " not found in sheet " + mappingHeader.getZZ_Tab_Name());
            }

            String letter = det.getZZ_Column_Letter();
            if (Util.isEmpty(letter, true)) {
                letter = DEFAULT_VALUE_COLUMN_LETTER;
            }

            int colIndex = columnLetterToIndex(letter);
            String txt = getCellText(row, colIndex, formatter);
            if (Util.isEmpty(txt, true))
                continue;

            MColumn column = new MColumn(ctx, adColId, trxName);
            boolean useValue = det.isZZ_Use_Value();

            setValueFromText(ctx, target, column, txt, useValue, trxName);
        }

        target.saveEx();
        process.addLog("Imported Finance & Training comparison for tab " + mappingHeader.getZZ_Tab_Name());
        return 1;
    }
}
