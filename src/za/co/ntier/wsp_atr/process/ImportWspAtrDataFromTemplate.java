package za.co.ntier.wsp_atr.process;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Util;

import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Lookup_Mapping;
import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Submitted;

public class ImportWspAtrDataFromTemplate extends SvrProcess {

    private int p_ZZ_WSP_ATR_Submitted_ID;

    private final ReferenceLookupService refService = new ReferenceLookupService();

    @Override
    protected void prepare() {
        p_ZZ_WSP_ATR_Submitted_ID = getRecord_ID();
    }

    @Override
    protected String doIt() throws Exception {
        if (p_ZZ_WSP_ATR_Submitted_ID <= 0) {
            throw new org.adempiere.exceptions.AdempiereException(
                    "No WSP/ATR Submitted record selected");
        }

        Properties ctx = Env.getCtx();
        String trxName = get_TrxName();

        X_ZZ_WSP_ATR_Submitted submitted =
                new X_ZZ_WSP_ATR_Submitted(ctx, p_ZZ_WSP_ATR_Submitted_ID, trxName);

        Workbook wb = loadWorkbook(submitted);
        DataFormatter formatter = new DataFormatter();

        // Load all mapping headers for this process (you can filter by TabName if needed)
        List<X_ZZ_WSP_ATR_Lookup_Mapping> headers = new Query(
                ctx,
                X_ZZ_WSP_ATR_Lookup_Mapping.Table_Name,
                null,
                trxName)
                .setOnlyActiveRecords(true)
                .list();

        if (headers == null || headers.isEmpty()) {
            throw new org.adempiere.exceptions.AdempiereException(
                    "No WSP/ATR mapping header records defined");
        }

        int totalImported = 0;
        for (X_ZZ_WSP_ATR_Lookup_Mapping mapHeader : headers) {
            boolean isColumns = mapHeader.get_ValueAsBoolean("ZZ_Is_Columns");  // Y=column mode, N=row mode

            IWspAtrSheetImporter importer = isColumns
                    ? new ColumnModeSheetImporter(refService)
                    : new RowModeSheetImporter(refService);

            int count = importer.importData(ctx, wb, submitted, mapHeader, trxName, this, formatter);
            totalImported += count;
        }

        return "Imported " + totalImported + " records from all mapped tabs";
    }

    private Workbook loadWorkbook(X_ZZ_WSP_ATR_Submitted submitted) throws Exception {
        String fileName = submitted.getFileName();
        if (Util.isEmpty(fileName, true)) {
            throw new org.adempiere.exceptions.AdempiereException(
                    "No file attached / FileName is empty");
        }

        // Adjust this to however you retrieve the file stream in your installation
        InputStream is = /* your attachment retrieval logic */ null;
        // e.g. MAttachment att = ...; att.getEntryAsStream(0);

        if (is == null) {
            throw new org.adempiere.exceptions.AdempiereException(
                    "Cannot open workbook for " + fileName);
        }

        return WorkbookFactory.create(is);
    }
}
