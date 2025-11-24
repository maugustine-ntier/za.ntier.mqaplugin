package za.co.ntier.wsp_atr.process;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
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

public class ColumnModeSheetImporter extends AbstractMappingSheetImporter {

    // You can make this configurable via another field later
    private static final int DEFAULT_DATA_START_ROW = 6; // row 7 in Excel (0-based)

    public ColumnModeSheetImporter(ReferenceLookupService refService) {
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
            throw new org.adempiere.exceptions.AdempiereException(
                    "No mapping details for tab " + mappingHeader.getZZ_Tab_Name());
        }

        // Precompute: columnIndex -> (detail, MColumn)
        Map<Integer, DetailMeta> colIndexToMeta = new HashMap<>();

        for (X_ZZ_WSP_ATR_Lookup_Mapping_Detail det : details) {
            int adColId = det.getAD_Column_ID();
            if (adColId <= 0) {
                throw new org.adempiere.exceptions.AdempiereException(
                        "Mapping detail " + det.get_ID() + " has no AD_Column_ID");
            }

            String letter = det.getZZ_Column_Letter();
            if (Util.isEmpty(letter, true)) {
                throw new org.adempiere.exceptions.AdempiereException(
                        "Mapping detail " + det.get_ID() + " has no ZZ_Column_Letter");
            }

            int colIndex = columnLetterToIndex(letter);
            if (colIndex < 0) {
                throw new org.adempiere.exceptions.AdempiereException(
                        "Invalid column letter '" + letter
                                + "' in mapping detail " + det.get_ID());
            }

            MColumn column = new MColumn(ctx, adColId, trxName);
            colIndexToMeta.put(colIndex, new DetailMeta(det, column));
        }

        int lastRow = sheet.getLastRowNum();
        int imported = 0;

        for (int r = DEFAULT_DATA_START_ROW; r <= lastRow; r++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;

            if (isRowEmptyByMappedColumns(row, colIndexToMeta.keySet(), formatter))
                continue;

            PO line = newTargetPO(ctx, submitted, mappingHeader, trxName);

            for (Map.Entry<Integer, DetailMeta> entry : colIndexToMeta.entrySet()) {
                int colIndex = entry.getKey();
                DetailMeta meta = entry.getValue();

                String txt = getCellText(row, colIndex, formatter);
                if (Util.isEmpty(txt, true))
                    continue;

                boolean useValue = meta.detail.isZZ_Use_Value();
                setValueFromText(ctx, line, meta.column, txt, useValue, trxName);
            }

            line.saveEx();
            imported++;
        }

        process.addLog("Imported " + imported + " rows from tab " + mappingHeader.getZZ_Tab_Name());
        return imported;
    }

    private boolean isRowEmptyByMappedColumns(Row row,
                                              Iterable<Integer> colIndexes,
                                              DataFormatter formatter) {
        for (Integer colIndex : colIndexes) {
            String txt = getCellText(row, colIndex, formatter);
            if (!Util.isEmpty(txt, true))
                return false;
        }
        return true;
    }

    private static class DetailMeta {
        final X_ZZ_WSP_ATR_Lookup_Mapping_Detail detail;
        final MColumn column;

        DetailMeta(X_ZZ_WSP_ATR_Lookup_Mapping_Detail detail, MColumn column) {
            this.detail = detail;
            this.column = column;
        }
    }
}
