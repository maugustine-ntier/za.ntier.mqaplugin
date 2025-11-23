package za.co.ntier.wsp_atr.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

import org.adempiere.base.annotation.Parameter;
import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;
import org.compiere.model.MProcessPara;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 * ValidateBiodataLookups
 *
 * - User selects an XLSM template via FileName parameter.
 * - Process reads the sheet named "Biodata" (or first sheet if not found).
 * - Uses lookup mapping tables:
 *      ZZ_WSP_ATR_Lookup_Mapping (header: tab name)
 *      ZZ_WSP_ATR_Lookup_Mapping_Detail (detail: header name, AD_Table_ID, ZZ_Use_Value)
 * - For each mapped column:
 *      * If ZZ_Use_Value = Y: treat spreadsheet values as "Value" from ref table.
 *      * If ZZ_Use_Value = N: treat spreadsheet values as "Name" from ref table.
 * - For each data row:
 *      * If a value is not found in the corresponding ref table, the cell is coloured red.
 * - Any column with at least one invalid cell has its header coloured red.
 * - At the end, a modified XLSM/XLSX file is written to a temp file and
 *   processUI.download(exportFile) is called so ZK shows a download link.
 */
@org.adempiere.base.annotation.Process(
        name = "za.co.ntier.wsp_atr.process.ValidateBiodataLookups"
)
public class ValidateBiodataLookups extends SvrProcess {

    @Parameter(name = "FileName")
    private String filePath;   // Path to the uploaded file on the server

    @Override
    protected void prepare() {
        // Just validate unknown parameters; filePath is injected by @Parameter
        for (ProcessInfoParameter para : getParameter()) {
            MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para);
        }
    }

    @Override
    protected String doIt() throws Exception {

        if (Util.isEmpty(filePath, true)) {
            throw new AdempiereException("FileName parameter is empty. Please select the spreadsheet file.");
        }

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new AdempiereException("File not found or not a regular file: " + filePath);
        }

        String originalFileName = file.getName();
        int adClientId = Env.getAD_Client_ID(getCtx());

        // Open workbook (xlsm/xlsx) via POI from file path
        Workbook workbook;
        try (InputStream is = new FileInputStream(file)) {
            workbook = WorkbookFactory.create(is);
        }

        // Get "Biodata" sheet, or first sheet if not found
        Sheet sheet = workbook.getSheet("Biodata");
        if (sheet == null) {
            sheet = workbook.getSheetAt(0);
            addLog("Sheet 'Biodata' not found, using first sheet: " + sheet.getSheetName());
        }

        if (sheet == null) {
            workbook.close();
            throw new AdempiereException("No sheet found in workbook.");
        }

        DataFormatter formatter = new DataFormatter();

        // --- Find header row heuristically (row 3 or 4 usually) ---
        Row headerRow = findHeaderRow(sheet, formatter);
        if (headerRow == null) {
            workbook.close();
            throw new AdempiereException("Could not locate header row in sheet " + sheet.getSheetName());
        }
        int headerRowIndex = headerRow.getRowNum();
        int dataStartRowIndex = headerRowIndex + 1;

        int lastCellNum = headerRow.getLastCellNum(); // short -> int
        if (lastCellNum <= 0) {
            workbook.close();
            throw new AdempiereException("No header columns found in sheet.");
        }

        // --- Load mapping header for this tab (sheet name) ---
        String tabName = sheet.getSheetName();

        PO mappingHeader = new Query(getCtx(), "ZZ_WSP_ATR_Lookup_Mapping",
                "UPPER(ZZ_Tab_Name)=UPPER(?)", get_TrxName())
                .setParameters(tabName)
                .setOnlyActiveRecords(true)
                .first();

        if (mappingHeader == null) {
            workbook.close();
            throw new AdempiereException("No lookup mapping header found for tab '" + tabName + "'.");
        }

        int mappingId = mappingHeader.get_ID();

        // --- Load mapping details for this tab ---
        List<PO> detailList = new Query(getCtx(), "ZZ_WSP_ATR_Lookup_Mapping_Detail",
                "ZZ_WSP_ATR_Lookup_Mapping_ID=?", get_TrxName())
                .setParameters(mappingId)
                .setOnlyActiveRecords(true)
                .list();

        if (detailList == null || detailList.isEmpty()) {
            workbook.close();
            throw new AdempiereException("No lookup mapping details found for tab '" + tabName + "'.");
        }

        // Map normalized header name -> detail row
        Map<String, PO> headerToDetail = new HashMap<>();
        for (PO d : detailList) {
            Object hdrObj = d.get_Value("ZZ_Header_Name");
            if (hdrObj == null)
                continue;
            String hdr = hdrObj.toString().trim();
            if (hdr.isEmpty())
                continue;
            headerToDetail.put(hdr.toUpperCase(), d);
        }

        // For caching allowed values:
        // key = tableName + "|" + (useValue ? "V" : "N")
        Map<String, Set<String>> allowedCache = new HashMap<>();

        // Per-column mapping config
        class ColumnConfig {
            String headerText;
            String tableName;
            boolean useValue;
            Set<String> allowedValues;
        }

        ColumnConfig[] columnConfigs = new ColumnConfig[lastCellNum];

        // --- Resolve each header column to a mapping detail / ref table ---
        for (int col = 0; col < lastCellNum; col++) {
        	
            Cell headerCell = headerRow.getCell(col);
            if (headerCell == null)
                continue;

            String headerText = formatter.formatCellValue(headerCell).trim();
            if (Util.isEmpty(headerText, true))
                continue;

            PO detail = headerToDetail.get(headerText.toUpperCase());
            if (detail == null) {
                // No mapping for this header
                continue;
            }

            int adTableId = detail.get_ValueAsInt("AD_Table_ID");
            if (adTableId <= 0) {
                // Mapped, but no reference table set -> can't validate
                addLog("Header '" + headerText + "' has no AD_Table_ID set in mapping detail - skipping.");
                continue;
            }

            MTable refTable = MTable.get(getCtx(), adTableId);
            if (refTable == null || refTable.getAD_Table_ID() <= 0) {
                addLog("Header '" + headerText + "' references AD_Table_ID=" + adTableId + " which is invalid - skipping.");
                continue;
            }

            Object useValObj = detail.get_Value("ZZ_Use_Value");
            boolean useValue = false;
            if (useValObj instanceof Boolean) {
                useValue = ((Boolean) useValObj).booleanValue();
            } else if (useValObj != null) {
                useValue = "Y".equalsIgnoreCase(useValObj.toString());
            }

            String tableName = refTable.getTableName();
            String cacheKey = tableName + "|" + (useValue ? "V" : "N");
            Set<String> allowed = allowedCache.get(cacheKey);
            if (allowed == null) {
                allowed = loadAllowedValues(tableName, useValue, adClientId);
                allowedCache.put(cacheKey, allowed);
                String colType = useValue ? "Value" : "Name";
                addLog("Column '" + headerText + "' uses table " + tableName
                        + " (" + allowed.size() + " " + colType + " values)");
            }

            ColumnConfig cfg = new ColumnConfig();
            cfg.headerText = headerText;
            cfg.tableName = tableName;
            cfg.useValue = useValue;
            cfg.allowedValues = allowed;
            columnConfigs[col] = cfg;
        }

        // If nothing mapped, warn and exit
        boolean anyMapped = false;
        for (ColumnConfig cfg : columnConfigs) {
            if (cfg != null) {
                anyMapped = true;
                break;
            }
        }
        if (!anyMapped) {
            workbook.close();
            throw new AdempiereException("No columns in sheet '" + tabName + "' match lookup mapping details.");
        }

        int lastRowNum = sheet.getLastRowNum();
        int errorRowCount = 0;

        // Styles for errors
        CellStyle errorCellStyle = workbook.createCellStyle();
        errorCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        errorCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle headerErrorStyle = workbook.createCellStyle();
        headerErrorStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        headerErrorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        boolean[] columnHasErrors = new boolean[lastCellNum];

        // --- Validate data rows ---
        for (int r = dataStartRowIndex; r <= lastRowNum; r++) {
        	// IGNORE Excel rows 5 and 6 (0-based 4 and 5)
            if (r == 4 || r == 5) {
                continue;
            }
            Row row = sheet.getRow(r);
            if (row == null)
                continue;

            boolean rowHasError = false;

            for (int col = 0; col < lastCellNum; col++) {
                ColumnConfig cfg = columnConfigs[col];
                if (cfg == null)
                    continue; // no mapping for this column

                Cell cell = row.getCell(col);
                String cellValue = (cell == null) ? "" : formatter.formatCellValue(cell).trim();
                if (Util.isEmpty(cellValue, true))
                    continue; // empty is allowed

                if (!cfg.allowedValues.contains(cellValue)) {
                    // mark cell as error
                    if (cell == null) {
                        cell = row.createCell(col);
                    }
                    CellUtil.setCellStyleProperty(cell, CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.RED.getIndex());
                    CellUtil.setCellStyleProperty(cell, CellUtil.FILL_PATTERN, FillPatternType.SOLID_FOREGROUND);
                    cell.setCellStyle(errorCellStyle);

                    columnHasErrors[col] = true;
                    rowHasError = true;
                }
            }

            if (rowHasError) {
                errorRowCount++;
            }
        }

        // --- Colour headers for columns that had errors ---
        for (int col = 0; col < lastCellNum; col++) {
            if (!columnHasErrors[col])
                continue;
            Cell headerCell = headerRow.getCell(col);
            if (headerCell == null)
                continue;
            CellUtil.setCellStyleProperty(headerCell, CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.RED.getIndex());
            CellUtil.setCellStyleProperty(headerCell, CellUtil.FILL_PATTERN, FillPatternType.SOLID_FOREGROUND);
            headerCell.setCellStyle(headerErrorStyle);
        }

        // ----- EXPORT FOR ZK DOWNLOAD -----

        // Work out extension from original file
        String ext = "xlsm";
        int dot = originalFileName.lastIndexOf('.');
        if (dot > 0 && dot < originalFileName.length() - 1) {
            String origExt = originalFileName.substring(dot + 1);
            if (!Util.isEmpty(origExt)) {
                ext = origExt;
            }
        }

        // Create a temp file for export
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        File exportFile = File.createTempFile("Validated_", "." + ext, tmpDir);

        try (FileOutputStream fos = new FileOutputStream(exportFile)) {
            workbook.write(fos);
        }
        workbook.close();

        // Show download link in ZK using the same pattern as ExportInvoiceBatchToCsv
        if (getProcessInfo().getProcessUI() != null) {
            getProcessInfo().getProcessUI().download(exportFile);
        }

        String msg = "Validation complete. Rows with errors: " + errorRowCount
                + ". File generated: " + exportFile.getName();
        addLog(msg);
        return msg;
    }

    /**
     * Heuristically find the header row:
     * - scan first few rows (0..6)
     * - choose the row with the highest number of non-empty cells.
     */
    private Row findHeaderRow(Sheet sheet, DataFormatter formatter) {
        Row best = null;
        int bestCount = 0;

        int maxRowToScan = Math.min(6, sheet.getLastRowNum());
        for (int i = 0; i <= maxRowToScan; i++) {
        	 // IGNORE Excel rows 5 and 6 (0-based 4 and 5)
            if (i == 4 || i == 5) {
                continue;
            }
            Row r = sheet.getRow(i);
            if (r == null)
                continue;

            int nonEmpty = 0;
            short lastCell = r.getLastCellNum();
            for (int c = 0; c < lastCell; c++) {
                Cell cell = r.getCell(c);
                if (cell == null)
                    continue;
                String val = formatter.formatCellValue(cell).trim();
                if (!val.isEmpty())
                    nonEmpty++;
            }

            if (nonEmpty > bestCount) {
                bestCount = nonEmpty;
                best = r;
            }
        }
        return best;
    }

    /**
     * Load all allowed values from the reference table for a client.
     * If useValue = true -> read "Value" column, else -> read "Name" column.
     */
    private Set<String> loadAllowedValues(String tableName, boolean useValue, int adClientId) {
        Set<String> set = new HashSet<>();

        String column = useValue ? "Value" : "Name";

        List<PO> list = new Query(getCtx(), tableName, "AD_Client_ID=?", get_TrxName())
                .setParameters(adClientId)
                .setOnlyActiveRecords(true)
                .list();

        for (PO po : list) {
            Object val = po.get_Value(column);
            if (val != null) {
                String s = val.toString().trim();
                if (!s.isEmpty()) {
                    set.add(s);
                }
            }
        }

        return set;
    }
}


