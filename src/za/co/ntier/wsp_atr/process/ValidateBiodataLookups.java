package za.co.ntier.wsp_atr.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.base.annotation.Parameter;
import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
 * - For each column:
 *     * If there is a lookup table ZZ_<Header>_Ref in AD_Table, treat it as lookup column.
 * - For each row:
 *     * If a value is not found in the lookup table (by Name), add a message in column U.
 * - At the end, sets an export file so ZK shows a download link.
 */
@org.adempiere.base.annotation.Process(name = "za.co.ntier.wsp_atr.process.ValidateBiodataLookups")
public class ValidateBiodataLookups extends SvrProcess {

    // Column U is index 20 (0-based) -> 21st column
    private static final int MESSAGE_COL_INDEX = 20;

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
            throw new AdempiereException("No sheet found in workbook.");
        }

        // Header row (row 0)
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new AdempiereException("Header row (row 1) not found in sheet.");
        }

        int lastCellNum = headerRow.getLastCellNum(); // short -> int
        if (lastCellNum <= 0) {
            throw new AdempiereException("No header columns found in sheet.");
        }

        // Ensure message column header at column U
        Cell messageHeaderCell = headerRow.getCell(MESSAGE_COL_INDEX);
        if (messageHeaderCell == null) {
            messageHeaderCell = headerRow.createCell(MESSAGE_COL_INDEX);
        }
        if (Util.isEmpty(messageHeaderCell.getStringCellValue(), true)) {
            messageHeaderCell.setCellValue("Validation Messages");
        }

        // For each column, decide if it is lookup and which table it maps to
        // We map: header "X" -> table "ZZ_X_Ref" (normalized)
        String[] lookupTableNamesByColumn = new String[lastCellNum];
        Map<String, Set<String>> tableNameToAllowedNames = new HashMap<>();

        DataFormatter formatter = new DataFormatter();

        for (int col = 0; col < lastCellNum; col++) {
            Cell headerCell = headerRow.getCell(col);
            if (headerCell == null) {
                continue;
            }

            String headerText = formatter.formatCellValue(headerCell).trim();
            if (Util.isEmpty(headerText, true)) {
                continue;
            }

            String tableName = buildTableNameFromHeader(headerText);
            MTable table = MTable.get(getCtx(), tableName);

            if (table != null && table.getAD_Table_ID() > 0) {
                // We have a lookup table
                lookupTableNamesByColumn[col] = tableName;

                // Pre-load allowed names for this table (per client)
                if (!tableNameToAllowedNames.containsKey(tableName)) {
                    Set<String> allowed = loadAllowedNames(tableName, adClientId);
                    tableNameToAllowedNames.put(tableName, allowed);
                    addLog("Column '" + headerText + "' uses lookup table " + tableName
                            + " (" + allowed.size() + " values)");
                }
            } else {
                // No lookup for this column
                lookupTableNamesByColumn[col] = null;
            }
        }

        int lastRowNum = sheet.getLastRowNum();
        int errorCount = 0;

        // For each data row
        for (int r = 1; r <= lastRowNum; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            StringBuilder rowErrors = new StringBuilder();

            for (int col = 0; col < lastCellNum; col++) {
                String tableName = lookupTableNamesByColumn[col];
                if (tableName == null) {
                    continue; // not a lookup column
                }

                Cell cell = row.getCell(col);
                String cellValue = (cell == null) ? "" : formatter.formatCellValue(cell).trim();
                if (Util.isEmpty(cellValue, true)) {
                    continue; // empty -> no validation error
                }

                Set<String> allowedNames = tableNameToAllowedNames.get(tableName);
                if (allowedNames == null) {
                    continue; // should not happen
                }

                if (!allowedNames.contains(cellValue)) {
                    // Build error message
                    String headerText = formatter.formatCellValue(headerRow.getCell(col)).trim();
                    if (rowErrors.length() > 0) {
                        rowErrors.append("; ");
                    }
                    rowErrors.append(headerText)
                             .append(": '")
                             .append(cellValue)
                             .append("' not found in ")
                             .append(tableName);
                }
            }

            if (rowErrors.length() > 0) {
                Cell errorCell = row.getCell(MESSAGE_COL_INDEX);
                if (errorCell == null) {
                    errorCell = row.createCell(MESSAGE_COL_INDEX);
                }
                errorCell.setCellValue(rowErrors.toString());
                errorCount++;
            }
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

        // Tell the framework this is an export, and which file to offer
        getProcessInfo().setExport(true);
        getProcessInfo().setExportFileExtension(ext);
        getProcessInfo().setExportFile(exportFile);

        String msg = "Validation complete. Rows with errors: " + errorCount
                   + ". A download link will be shown for: " + exportFile.getName();
        addLog(msg);
        return msg;
    }

    /**
     * Builds the AD table name from a column header.
     * Example: "Province"  -> "ZZ_Province_Ref"
     *          "NQF Level" -> "ZZ_NQF_Level_Ref"
     */
    private String buildTableNameFromHeader(String header) {
        String h = header.trim();

        // Replace spaces and illegal chars with underscore, keep existing underscores
        h = h.replaceAll("[^A-Za-z0-9_]", "_");
        h = h.replaceAll("_+", "_"); // collapse multiple underscores
        if (!h.isEmpty() && Character.isDigit(h.charAt(0))) {
            h = "_" + h;
        }

        return "ZZ_" + h + "_Ref";
    }

    /**
     * Load all allowed Name values from the reference table for a client.
     */
    private Set<String> loadAllowedNames(String tableName, int adClientId) {
        Set<String> set = new HashSet<>();

        List<PO> list = new Query(getCtx(), tableName, "AD_Client_ID=?", get_TrxName())
                .setParameters(adClientId)
                .setOnlyActiveRecords(true)
                .list();

        for (PO po : list) {
            Object val = po.get_Value("Name");
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

