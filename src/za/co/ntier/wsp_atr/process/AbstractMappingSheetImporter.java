package za.co.ntier.wsp_atr.process;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.*;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util;

import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Lookup_Mapping;
import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Lookup_Mapping_Detail;
import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Submitted;

public abstract class AbstractMappingSheetImporter implements IWspAtrSheetImporter {

    protected final ReferenceLookupService refService;

    protected AbstractMappingSheetImporter(ReferenceLookupService refService) {
        this.refService = refService;
    }

    // ---------- common helpers ----------

    protected Sheet getSheetOrThrow(Workbook wb, X_ZZ_WSP_ATR_Lookup_Mapping mappingHeader) {
        String sheetName = mappingHeader.getZZ_Tab_Name();
        Sheet sheet = wb.getSheet(sheetName);
        if (sheet == null) {
            throw new org.adempiere.exceptions.AdempiereException(
                    "Sheet '" + sheetName + "' not found in workbook");
        }
        return sheet;
    }

    protected List<X_ZZ_WSP_ATR_Lookup_Mapping_Detail> loadDetails(X_ZZ_WSP_ATR_Lookup_Mapping mappingHeader,
                                                                   String trxName) {
        return new Query(Env.getCtx(),
                X_ZZ_WSP_ATR_Lookup_Mapping_Detail.Table_Name,
                "ZZ_WSP_ATR_Lookup_Mapping_ID=?",
                trxName)
                .setParameters(mappingHeader.getZZ_WSP_ATR_Lookup_Mapping_ID())
                .setOnlyActiveRecords(true)
                .list();
    }

    protected PO newTargetPO(Properties ctx,
                             X_ZZ_WSP_ATR_Submitted submitted,
                             X_ZZ_WSP_ATR_Lookup_Mapping mappingHeader,
                             String trxName) {

        int adTableId = mappingHeader.getAD_Table_ID();
        if (adTableId <= 0) {
        	return null;
           //// throw new org.adempiere.exceptions.AdempiereException(
            //        "Mapping header " + mappingHeader.get_ID()
             //               + " has no AD_Table_ID");
        }

        MTable table = MTable.get(ctx, adTableId);
        PO po = table.getPO(0, trxName);

        // attach header if column exists
        int idx = po.get_ColumnIndex("ZZ_WSP_ATR_Submitted_ID");
        if (idx >= 0) {
            po.set_ValueOfColumn("ZZ_WSP_ATR_Submitted_ID",
                    submitted.getZZ_WSP_ATR_Submitted_ID());
        }

        return po;
    }

    protected String getCellText(Row row, int colIndex, DataFormatter formatter) {
        if (row == null)
            return "";
        Cell cell = row.getCell(colIndex);
        if (cell == null)
            return "";
        try {
            return formatter.formatCellValue(cell).trim();
        } catch (Exception e) {
            try {
                if (cell.getCellType() == CellType.STRING) {
                    return cell.getStringCellValue().trim();
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            } catch (Exception ignore) {
            }
            return "";
        }
    }

    protected BigDecimal parseBigDecimal(String txt) {
        if (Util.isEmpty(txt, true))
            return null;
        try {
            return new BigDecimal(txt.trim());
        } catch (Exception e) {
            return null;
        }
    }

    protected String truncate(String s, int max) {
        if (s == null)
            return null;
        s = s.trim();
        if (max > 0 && s.length() > max)
            return s.substring(0, max);
        return s;
    }

    protected int columnLetterToIndex(String letter) {
        if (Util.isEmpty(letter, true))
            return -1;

        letter = letter.trim().toUpperCase();
        int result = 0;
        for (int i = 0; i < letter.length(); i++) {
            char c = letter.charAt(i);
            if (c < 'A' || c > 'Z')
                throw new IllegalArgumentException("Invalid column letter: " + letter);
            result = result * 26 + (c - 'A' + 1);
        }
        return result - 1; // zero-based
    }

    /**
     * Set a value into the target PO based on the column definition and text from Excel.
     * Supports numeric, String and Table references.
     */
    protected void setValueFromText(Properties ctx,
                                    PO po,
                                    MColumn column,
                                    String text,
                                    boolean useValueForRef,
                                    String trxName) {

        String colName = column.getColumnName();
        int displayType = column.getAD_Reference_ID();

        if (DisplayType.isNumeric(displayType)) {
            BigDecimal bd = parseBigDecimal(text);
            po.set_ValueOfColumn(colName, bd);
        } else if (displayType == DisplayType.Table || displayType == DisplayType.TableDir) {
            Integer id = refService.lookupReferenceId(ctx, column, text, useValueForRef, trxName);
            if (id == null) {
                throw new org.adempiere.exceptions.AdempiereException(
                        "No reference record found for text '" + text
                                + "' in column " + colName);
            }
            po.set_ValueOfColumn(colName, id);
        } else {
            int len = column.getFieldLength();
            po.set_ValueOfColumn(colName, truncate(text, len > 0 ? len : 200));
        }
    }
}

