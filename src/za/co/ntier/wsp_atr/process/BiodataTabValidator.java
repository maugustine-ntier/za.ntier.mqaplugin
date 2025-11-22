package za.co.ntier.wsp_atr.process;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.ss.usermodel.*;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 * Validator for the "Biodata" tab.
 *
 * - Detects lookup columns based on header -> ZZ_<Header>_Ref
 * - Checks each cell value against the corresponding lookup table
 * - Writes messages to column U (index 20) when values are missing.
 */
public class BiodataTabValidator implements ITemplateTabValidator {

    // Column U is index 20 (0-based) -> 21st column
    private static final int MESSAGE_COL_INDEX = 20;

    @Override
    public String getTargetSheetName() {
        return "Biodata";
    }

    @Override
    public void validate(Workbook workbook, SvrProcess process) throws Exception {

        int adClientId = Env.getAD_Client_ID(process.getCtx());
        Sheet sheet = workbook.getSheet(getTargetSheetName());

        if (sheet == null) {
            // Fallback: use first sheet but log it
            sheet = workbook.getSheetAt(0);
            process.addLog("Sheet '" + getTargetSheetName()
                    + "' not found, using first sheet: " + sheet.getSheetName());
        }

        if (sheet == null) {
            throw new IllegalStateException("No sheet available for Biodata validator.");
        }

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new IllegalStateException("Header row (row 1) not found in Biodata sheet.");
        }

        int lastCellNum = headerRow.getLastCellNum();
        if (lastCellNum <= 0) {
            throw new IllegalStateException("No header columns found in Biodata sheet.");
        }

        // Ensure message column header at column U
        Cell messageHeaderCell = headerRow.getCell(MESSAGE_COL_INDEX);
        if (messageHeaderCell == null) {
            messageHeaderCell = headerRow.createCell(MESSAGE_COL_INDEX);
        }
        if (Util.isEmpty(messageHeaderCell.getStringCellValue(), true)) {
            messageHeaderCell.setCellValue("Validation Messages");
        }

        // Determine which columns are lookups and load allowed names
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
            MTable table = MTable.get(process.getCtx(), tableName);

            if (table != null && table.getAD_Table_ID() > 0) {
                lookupTableNamesByColumn[col] = tableName;

                if (!tableNameToAllowedNames.containsKey(tableName)) {
                    Set<String> allowed = loadAllowedNames(process, tableName, adClientId);
                    tableNameToAllowedNames.put(tableName, allowed);
                    process.addLog("Biodata column '" + headerText + "' uses lookup table "
                            + tableName + " (" + allowed.size() + " values)");
                }
            } else {
                lookupTableNamesByColumn[col] = null;
            }
        }

        int lastRowNum = sheet.getLastRowNum();
        int errorCount = 0;

        // Validate each data row
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
                    continue;
                }

                Set<String> allowedNames = tableNameToAllowedNames.get(tableName);
                if (allowedNames == null) {
                    continue;
                }

                if (!allowedNames.contains(cellValue)) {
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

        process.addLog("Biodata validation complete. Rows with errors: " + errorCount);
    }

    /**
     * Header → table name mapping: e.g.
     * "Province"  -> "ZZ_Province_Ref"
     * "NQF Level" -> "ZZ_NQF_Level_Ref"
     */
    private String buildTableNameFromHeader(String header) {
        String h = header.trim();
        h = h.replaceAll("[^A-Za-z0-9_]", "_");
        h = h.replaceAll("_+", "_");
        if (!h.isEmpty() && Character.isDigit(h.charAt(0))) {
            h = "_" + h;
        }
        return "ZZ_" + h + "_Ref";
    }

    /**
     * Pre-load all Name values from the lookup table for the given client.
     */
    private Set<String> loadAllowedNames(SvrProcess process, String tableName, int adClientId) {
        Set<String> set = new HashSet<>();

        List<PO> list = new Query(process.getCtx(), tableName, "AD_Client_ID=?", process.get_TrxName())
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

