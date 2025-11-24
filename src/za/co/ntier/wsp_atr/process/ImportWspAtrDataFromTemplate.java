package za.co.ntier.wsp_atr.process;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.adempiere.base.annotation.Parameter;
import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Util;

import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Biodata_Detail;
import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Submitted;

/**
 * ImportWspAtrDataFromTemplate
 *
 * - Run from ZZ_WSP_ATR_Submitted header record.
 * - Reads the attached XLSX/XLSM file.
 * - Uses per-sheet importers (factory pattern) to populate detail tables.
 *
 * Currently implemented:
 *   - Biodata sheet -> ZZ_WSP_ATR_Biodata_Detail
 *
 * Extension points:
 *   - Add more IWspAtrSheetImporter implementations for the other 7 tabs
 *     and wire them into the factory.
 */
@org.adempiere.base.annotation.Process(
        name = "za.co.ntier.wsp_atr.process.ImportWspAtrDataFromTemplate")
public class ImportWspAtrDataFromTemplate extends SvrProcess {

    private static final CLogger log = CLogger.getCLogger(ImportWspAtrDataFromTemplate.class);

    // Same tab booleans as your table-generator process, so you can choose which to import
    @Parameter(name = "Tab_Biodata")
    private boolean pBiodata;

    @Parameter(name = "Tab_ATR")
    private boolean pATR;

    @Parameter(name = "Tab_WSP")
    private boolean pWSP;

    @Parameter(name = "Tab_HTFV")
    private boolean pHTFV;

    @Parameter(name = "Tab_Topup_Skills_Survey")
    private boolean pTopupSkills;

    @Parameter(name = "Tab_NonEmployees_CommunityTraining")
    private boolean pNonEmployees;

    @Parameter(name = "Tab_Contractors")
    private boolean pContractors;

    @Parameter(name = "Tab_Finance_Training_Comparison")
    private boolean pFinanceTraining;

    private DataFormatter formatter;
    private Workbook workbook;
    private String trxName;

    @Override
    protected void prepare() {
        trxName = get_TrxName();
        for (ProcessInfoParameter p : getParameter()) {
            // All parameters are injected by @Parameter; this just validates unknowns
            // (same pattern as your table generator)
            // If you don't have MProcessPara.validateUnknownParameter available,
            // you can safely remove this block.
            // MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), p);
        }
    }

    @Override
    protected String doIt() throws Exception {

        int headerId = getRecord_ID();
        if (headerId <= 0) {
            throw new AdempiereException("This process must be run from WSP/ATR Submitted File (header) record.");
        }

        X_ZZ_WSP_ATR_Submitted header = new X_ZZ_WSP_ATR_Submitted(getCtx(), headerId, trxName);
        if (header.get_ID() <= 0) {
            throw new AdempiereException("Header record not found: ZZ_WSP_ATR_Submitted_ID=" + headerId);
        }

        // Get attachment with template
        MAttachment attachment = MAttachment.get(getCtx(), header.get_Table_ID(), header.get_ID());
        if (attachment == null || attachment.getEntryCount() == 0) {
            throw new AdempiereException("No attachment found on the header record. Please attach the WSP/ATR template file.");
        }

        // Use first attachment entry
        MAttachmentEntry entry = attachment.getEntry(0);
        byte[] data = entry.getData();
        if (data == null || data.length == 0) {
            throw new AdempiereException("Attachment is empty. Please attach a valid XLSX/XLSM template file.");
        }

        try (InputStream is = new ByteArrayInputStream(data)) {
            workbook = WorkbookFactory.create(is);
        }

        formatter = new DataFormatter();

        // Build list of active importers via factory
        pBiodata = true;
        List<IWspAtrSheetImporter> importers = WspAtrSheetImporterFactory.buildImporters(
                pBiodata, pATR, pWSP, pHTFV, pTopupSkills, pNonEmployees, pContractors, pFinanceTraining);

        if (importers.isEmpty()) {
            throw new AdempiereException("No tabs selected for import (Biodata, ATR, WSP, etc.).");
        }

        int totalImported = 0;
        for (IWspAtrSheetImporter importer : importers) {
            int count = importer.importData(workbook, header, trxName, this, formatter);
            addLog("Imported " + count + " rows from sheet '" + importer.getSheetName() + "'");
            totalImported += count;
        }

        String msg = "Imported total " + totalImported + " rows from " + importers.size() + " sheet(s).";
        addLog(msg);
        return msg;
    }

    // -------------------------------------------------------------------------
    // Factory pattern
    // -------------------------------------------------------------------------

    public interface IWspAtrSheetImporter {
        /** Name of the Excel sheet this importer expects (e.g. "Biodata") */
        String getSheetName();

        /**
         * Import data from workbook for this sheet.
         *
         * @return number of detail rows created
         */
        int importData(Workbook wb,
                       X_ZZ_WSP_ATR_Submitted header,
                       String trxName,
                       SvrProcess process,
                       DataFormatter formatter);
    }

    public static class WspAtrSheetImporterFactory {

        public static List<IWspAtrSheetImporter> buildImporters(
                boolean biodata,
                boolean atr,
                boolean wsp,
                boolean htfv,
                boolean topup,
                boolean nonEmployees,
                boolean contractors,
                boolean financeComparison) {

            List<IWspAtrSheetImporter> list = new ArrayList<>();

            if (biodata) {
                list.add(new BiodataSheetImporter());
            }
            // TODO: add implementations for the other 7 sheets:
            // if (atr) list.add(new AtrSheetImporter());
            // ...

            return list;
        }
    }

   
}

