package za.co.ntier.wsp_atr.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.compiere.model.MColumn;
import org.compiere.model.MRefTable;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.DisplayType;
import org.compiere.util.Util;

import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Lookup_Mapping;
import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Lookup_Mapping_Detail;
import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Submitted;

/**
 * Column-mode importer.
 *
 * Uses ZZ_WSP_ATR_Lookup_Mapping_Detail records where:
 *   - ZZ_Column_Letter         = main column letter on the sheet (A,B,C,...)
 *   - AD_Column_ID             = target AD_Column to populate
 *   - ZZ_Use_Value             = if Y, use Value when resolving references, else Name
 *   - ZZ_Create_If_Not_Exists  = if Y and column is Table/TableDir, create ref record if missing
 *   - ZZ_Value_Column_Letter   = optional letter for Value column (for create-if-missing)
 *   - ZZ_Name_Column_Letter    = optional letter for Name column (for create-if-missing)
 */
public class ColumnModeSheetImporter extends AbstractMappingSheetImporter {

    // Data usually starts at row 7 (Excel 1-based) => index 6 (0-based)
    private static final int DEFAULT_DATA_START_ROW = 6;

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
            // No mappings for this tab = nothing to import
            return 0;
        }

        // Precompute: columnIndex -> ColumnMeta
        Map<Integer, ColumnMeta> colIndexToMeta = new HashMap<>();

        for (X_ZZ_WSP_ATR_Lookup_Mapping_Detail det : details) {
            String letter = det.getZZ_Column_Letter();
            if (Util.isEmpty(letter, true)) {
                continue;
            }
            int colIndex = columnLetterToIndex(letter);

            ColumnMeta meta = new ColumnMeta();
            meta.columnIndex = colIndex;
            meta.detail = det;

            int adColumnId = det.getAD_Column_ID();
            if (adColumnId <= 0) {
                throw new AdempiereException("Detail " + det.get_ID() + " has no AD_Column_ID set");
            }

            MColumn column = new MColumn(ctx, adColumnId, trxName);
            if (column.get_ID() <= 0) {
                throw new AdempiereException("AD_Column_ID " + adColumnId + " not found");
            }
            meta.column = column;

            meta.useValueForRef = det.isZZ_Use_Value();

            // create-if-missing flags + value/name columns
            meta.createIfNotExist = det.isZZ_Create_If_Not_Exists();

            String valueColLetter = det.getZZ_Value_Column_Letter();
            String nameColLetter  = det.getZZ_Name_Column_Letter();

            if (!Util.isEmpty(valueColLetter, true)) {
                meta.valueColumnIndex = columnLetterToIndex(valueColLetter);
            }
            if (!Util.isEmpty(nameColLetter, true)) {
                meta.nameColumnIndex = columnLetterToIndex(nameColLetter);
            }

            colIndexToMeta.put(colIndex, meta);
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

            for (Map.Entry<Integer, ColumnMeta> entry : colIndexToMeta.entrySet()) {
                ColumnMeta meta = entry.getValue();
                int colIndex = meta.columnIndex;

                String mainText = getCellText(row, colIndex, formatter);
                String valueText = null;
                String nameText  = null;

                boolean isRefColumn =
                        meta.column.getAD_Reference_ID() == org.compiere.util.DisplayType.Table
                        || meta.column.getAD_Reference_ID() == org.compiere.util.DisplayType.TableDir;

                if (meta.createIfNotExist && isRefColumn) {
                    if (meta.valueColumnIndex != null) {
                        valueText = getCellText(row, meta.valueColumnIndex, formatter);
                    }
                    if (meta.nameColumnIndex != null) {
                        nameText = getCellText(row, meta.nameColumnIndex, formatter);
                    }

                    // If absolutely nothing in any of the three, skip this field
                    if (Util.isEmpty(mainText, true)
                            && Util.isEmpty(valueText, true)
                            && Util.isEmpty(nameText, true)) {
                        continue;
                    }

                    // Extended version: can create reference records if missing
                    setValueFromText(ctx,
                            line,
                            meta.column,
                            mainText,
                            meta.useValueForRef,
                            true,        // createIfNotExist
                            valueText,
                            nameText,
                            trxName);

                } else {
                    // Normal behaviour: just use mainText
                    if (Util.isEmpty(mainText, true))
                        continue;

                    setValueFromText(ctx,
                            line,
                            meta.column,
                            mainText,
                            meta.useValueForRef,
                            trxName);
                }
            }

            line.saveEx();
            imported++;
        }

        process.addLog("Imported " + imported + " rows from tab " + mappingHeader.getZZ_Tab_Name());
        return imported;
    }
    
    /**
     * Extended version:
     *  - If NOT a Table/TableDir, or createIfNotExist=false, just delegates to the old method.
     *  - If Table/TableDir AND createIfNotExist=true:
     *      * tries to find the record in the reference table using Value or Name
     *      * if not found, creates it (using refValue/refName/mainText)
     *      * then sets the FK on the PO
     */
    protected void setValueFromText(Properties ctx,
                                    PO po,
                                    MColumn column,
                                    String text,
                                    boolean useValueForRef,
                                    boolean createIfNotExist,
                                    String refValue,
                                    String refName,
                                    String trxName) {

        int displayType = column.getAD_Reference_ID();

        // If not a reference or we don't want to create, just delegate to existing method
        if (displayType != DisplayType.Table
                && displayType != DisplayType.TableDir) {
            // normal numeric/string handling, including references handled by old logic
            setValueFromText(ctx, po, column, text, useValueForRef, trxName);
            return;
        }

        if (!createIfNotExist) {
            // Old behaviour for Table/TableDir
            setValueFromText(ctx, po, column, text, useValueForRef, trxName);
            return;
        }

        // --- From here: Table/TableDir + createIfNotExist = true ---

        // If absolutely nothing is supplied, just skip
        boolean noMain = Util.isEmpty(text, true);
        boolean noVal  = Util.isEmpty(refValue, true);
        boolean noName = Util.isEmpty(refName, true);
        if (noMain && noVal && noName) {
            return;
        }

        // Clean up texts
        String mainText = noMain ? null : text.trim();
        String valueText = noVal ? null : refValue.trim();
        String nameText  = noName ? null : refName.trim();

        // Decide what we will use as "Value" and "Name" for lookup/create
        String valueToUse = valueText;
        String nameToUse  = nameText;

        if (valueToUse == null && useValueForRef && mainText != null) {
            valueToUse = mainText;
        }
        if (nameToUse == null && !useValueForRef && mainText != null) {
            nameToUse = mainText;
        }

        // If still nothing for either, fallback: use mainText as Name
        if (valueToUse == null && nameToUse == null && mainText != null) {
            nameToUse = mainText;
        }

        // We need the reference table from AD_Reference_Value_ID
        int adRefTableId = column.getAD_Reference_Value_ID();
        if (adRefTableId <= 0) {
            // No table reference definition, fallback to old behaviour
            setValueFromText(ctx, po, column, text, useValueForRef, trxName);
            return;
        }

        MRefTable refTableCfg = MRefTable.get(ctx, adRefTableId, trxName);
        if (refTableCfg == null || refTableCfg.getAD_Table_ID() <= 0) {
            setValueFromText(ctx, po, column, text, useValueForRef, trxName);
            return;
        }

        MTable refTable = MTable.get(ctx, refTableCfg.getAD_Table_ID());
        if (refTable == null || refTable.getAD_Table_ID() <= 0) {
            setValueFromText(ctx, po, column, text, useValueForRef, trxName);
            return;
        }

        String refTableName = refTable.getTableName();

        // 1) Try to find an existing record

        Integer foundId = null;

        // Prefer Value if we have it and ZZ_Use_Value is true
        if (useValueForRef && !Util.isEmpty(valueToUse, true)) {
            foundId = findIdByColumn(ctx, refTableName, "Value", valueToUse, trxName);
        }

        // If not found and we have a Name, try Name
        if ((foundId == null || foundId <= 0) && !Util.isEmpty(nameToUse, true)) {
            foundId = findIdByColumn(ctx, refTableName, "Name", nameToUse, trxName);
        }

        // If not found and we still have mainText, try Name=mainText
        if ((foundId == null || foundId <= 0) && mainText != null) {
            foundId = findIdByColumn(ctx, refTableName, "Name", mainText, trxName);
        }

        if (foundId != null && foundId > 0) {
            po.set_ValueOfColumn(column.getColumnName(), foundId);
            return;
        }

        // 2) Not found: create new record in the reference table

        if (valueToUse == null && nameToUse == null && mainText == null) {
            throw new AdempiereException("Cannot create reference record; no Value/Name/main text provided");
        }

        PO refPO = refTable.getPO(0, trxName);
        if (refPO == null) {
            throw new AdempiereException("Cannot create reference record for table " + refTableName);
        }

        // Ensure we always have *something* for Name
        if (nameToUse == null) {
            if (valueToUse != null) {
                nameToUse = valueToUse;
            } else if (mainText != null) {
                nameToUse = mainText;
            }
        }

        // For Value, if we don't have anything, we can reuse Name
        if (valueToUse == null && nameToUse != null) {
            valueToUse = nameToUse;
        }

        // Set standard fields if those columns exist
        if (refTable.getColumn("Value") != null && valueToUse != null) {
            refPO.set_ValueOfColumn("Value", valueToUse);
        }
        if (refTable.getColumn("Name") != null && nameToUse != null) {
            refPO.set_ValueOfColumn("Name", nameToUse);
        }

        // If the table has EntityType, default to 'U' (User Maintained)
        if (refTable.getColumn("EntityType") != null) {
            refPO.set_ValueOfColumn("EntityType", "U");
        }

        refPO.saveEx();
        int newId = refPO.get_ID();
        if (newId <= 0) {
            throw new AdempiereException("Failed to create reference record in " + refTableName);
        }

        po.set_ValueOfColumn(column.getColumnName(), newId);
    }

    /**
     * Helper: case-insensitive lookup by a single column (Value or Name)
     */
    private Integer findIdByColumn(Properties ctx,
                                   String tableName,
                                   String columnName,
                                   String text,
                                   String trxName) {
        if (Util.isEmpty(text, true))
            return null;

        String where = "UPPER(" + columnName + ")=UPPER(?)";
        int id = new Query(ctx, tableName, where, trxName)
                .setParameters(text.trim())
                .firstId();

        return (id > 0) ? id : null;
    }


    /**
     * Check if a row is effectively empty for all mapped main columns.
     */
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

    /**
     * Column metadata for one mapped column.
     */
    private static class ColumnMeta {
        int columnIndex;
        X_ZZ_WSP_ATR_Lookup_Mapping_Detail detail;
        MColumn column;
        boolean useValueForRef;

        // create-if-missing support
        boolean createIfNotExist;
        Integer valueColumnIndex; // may be null
        Integer nameColumnIndex;  // may be null
    }

}

