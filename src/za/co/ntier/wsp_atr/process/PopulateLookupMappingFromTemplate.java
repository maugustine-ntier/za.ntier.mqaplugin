package za.co.ntier.wsp_atr.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

import org.adempiere.base.annotation.Parameter;
import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.compiere.model.MProcessPara;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Util;

import za.co.ntier.wsp_atr.models.I_ZZ_WSP_ATR_Lookup_Mapping;
import za.co.ntier.wsp_atr.models.I_ZZ_WSP_ATR_Lookup_Mapping_Detail;
import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Lookup_Mapping;
import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Lookup_Mapping_Detail;

/**
 * PopulateLookupMappingFromTemplate
 *
 * - Reads the uploaded WSP/ATR XLSM template.
 * - For each sheet/tab (excluding "Welcome" and "Lookup" tabs):
 *      * Creates/updates header row in ZZ_WSP_ATR_Lookup_Mapping with ZZ_Tab_Name = sheet name.
 *      * Reads header row (row 1 / index 0) and for each non-empty header:
 *          - Creates/updates ZZ_WSP_ATR_Lookup_Mapping_Detail:
 *              * ZZ_Header_Name = header text
 *              * AD_Table_ID = reference table (if found) guessed as ZZ_<Header>_Ref
 *                otherwise left null.
 */
@org.adempiere.base.annotation.Process(
        name = "za.ntier.wsp_atr.process.PopulateLookupMappingFromTemplate"
)
public class PopulateLookupMappingFromTemplate extends SvrProcess {

    @Parameter(name = "FileName")
    private String filePath;   // Path to XLSM/XLSX template on server

    @Override
    protected void prepare() {
        for (ProcessInfoParameter para : getParameter()) {
            // Just validate unknown parameters; FileName is injected by @Parameter
            MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para);
        }
    }

    @Override
    protected String doIt() throws Exception {

        if (Util.isEmpty(filePath, true)) {
            throw new AdempiereException("FileName parameter is empty. Please select the template file.");
        }

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new AdempiereException("File not found or not a regular file: " + filePath);
        }

        int headersCreated = 0;
        int headersUpdated = 0;
        int detailsCreated = 0;
        int detailsUpdated = 0;

        Workbook workbook;
        try (InputStream is = new FileInputStream(file)) {
            workbook = WorkbookFactory.create(is);
        }

        DataFormatter formatter = new DataFormatter();

        // Loop all sheets
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            String sheetName = sheet.getSheetName();
            if (Util.isEmpty(sheetName, true)) {
                continue;
            }

            String sNameLower = sheetName.toLowerCase(Locale.ROOT).trim();
            // Exclude "Welcome" and "Lookup" tabs (Lookup / Lookups etc.)
            if ("welcome".equals(sNameLower) || sNameLower.startsWith("lookup")) {
                addLog("Skipping sheet: " + sheetName);
                continue;
            }

            // Header record in ZZ_WSP_ATR_Lookup_Mapping
            X_ZZ_WSP_ATR_Lookup_Mapping header = (X_ZZ_WSP_ATR_Lookup_Mapping) new Query(
                    getCtx(),
                    I_ZZ_WSP_ATR_Lookup_Mapping.Table_Name,
                    I_ZZ_WSP_ATR_Lookup_Mapping.COLUMNNAME_ZZ_Tab_Name + "=?",
                    get_TrxName()
            ).setParameters(sheetName)
             .first();

            boolean isNewHeader = false;
            if (header == null) {
                header = new X_ZZ_WSP_ATR_Lookup_Mapping(getCtx(), 0, get_TrxName());
                header.setZZ_Tab_Name(sheetName);
                header.saveEx();
                headersCreated++;
                isNewHeader = true;
            } else {
                // Nothing else to update for now, but count as updated for reporting
                headersUpdated++;
            }

            addLog("Processing sheet '" + sheetName + "' ("
                    + (isNewHeader ? "new" : "existing") + " header ID=" + header.get_ID() + ")");

            // Header row (first row)
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                addLog("Sheet '" + sheetName + "' has no header row, skipping details");
                continue;
            }

            int lastCellNum = headerRow.getLastCellNum();
            if (lastCellNum <= 0) {
                addLog("Sheet '" + sheetName + "' has empty header row, skipping details");
                continue;
            }

            for (int col = 0; col < lastCellNum; col++) {
                Cell headerCell = headerRow.getCell(col);
                if (headerCell == null) {
                    continue;
                }

                String headerText = formatter.formatCellValue(headerCell).trim();
                if (Util.isEmpty(headerText, true)) {
                    continue;
                }

                // Find or create detail row for this header
                X_ZZ_WSP_ATR_Lookup_Mapping_Detail detail =
                        (X_ZZ_WSP_ATR_Lookup_Mapping_Detail) new Query(
                                getCtx(),
                                I_ZZ_WSP_ATR_Lookup_Mapping_Detail.Table_Name,
                                I_ZZ_WSP_ATR_Lookup_Mapping_Detail.COLUMNNAME_ZZ_WSP_ATR_Lookup_Mapping_ID + "=? AND "
                                        + I_ZZ_WSP_ATR_Lookup_Mapping_Detail.COLUMNNAME_ZZ_Header_Name + "=?",
                                get_TrxName()
                        )
                        .setParameters(header.get_ID(), headerText)
                        .first();

                boolean isNewDetail = false;
                if (detail == null) {
                    detail = new X_ZZ_WSP_ATR_Lookup_Mapping_Detail(getCtx(), 0, get_TrxName());
                    detail.setZZ_WSP_ATR_Lookup_Mapping_ID(header.get_ID());
                    detail.setZZ_Header_Name(headerText);
                    isNewDetail = true;
                }

                // Try to guess reference table name from header
                Integer adTableId = findReferenceTableIdForHeader(headerText);
                if (adTableId != null && adTableId > 0) {
                    detail.setAD_Table_ID(adTableId);
                    addLog("Header '" + headerText + "' on sheet '" + sheetName
                            + "' mapped to AD_Table_ID=" + adTableId);
                } else {
                    // Leave AD_Table_ID null if not found
                    detail.setAD_Table_ID(0);
                    addLog("Header '" + headerText + "' on sheet '" + sheetName
                            + "' has no matching reference table (left blank).");
                }

                if (isNewDetail) {
                    detail.saveEx();
                    detailsCreated++;
                } else {
                    detail.saveEx();
                    detailsUpdated++;
                }
            }
        }

        workbook.close();

        String summary = "Lookup mapping populated from template.\n"
                + "Headers created: " + headersCreated
                + ", updated: " + headersUpdated
                + "; Details created: " + detailsCreated
                + ", updated: " + detailsUpdated;

        addLog(summary);
        return summary;
    }

    /**
     * Try to find AD_Table_ID for a header name using ZZ_<Header>_Ref pattern.
     * Example:
     *   "Province"   -> "ZZ_Province_Ref"
     *   "NQF Level"  -> "ZZ_NQF_Level_Ref"
     */
    private Integer findReferenceTableIdForHeader(String headerText) {
        String tableName = buildTableNameFromHeader(headerText);
        if (Util.isEmpty(tableName, true)) {
            return null;
        }

        MTable t = new Query(getCtx(), MTable.Table_Name, "TableName=?", get_TrxName())
                .setParameters(tableName)
                .first();

        if (t != null && t.getAD_Table_ID() > 0) {
            return t.getAD_Table_ID();
        }
        return null;
    }

    /**
     * Builds the AD table name from a column header.
     * Example: "Province"  -> "ZZ_Province_Ref"
     *          "NQF Level" -> "ZZ_NQF_Level_Ref"
     */
    private String buildTableNameFromHeader(String header) {
        if (header == null) {
            return null;
        }
        String h = header.trim();

        // Replace spaces and illegal chars with underscore, keep existing underscores
        h = h.replaceAll("[^A-Za-z0-9_]", "_");
        h = h.replaceAll("_+", "_"); // collapse multiple underscores
        if (!h.isEmpty() && Character.isDigit(h.charAt(0))) {
            h = "_" + h;
        }

        return "ZZ_" + h + "_Ref";
    }
}
