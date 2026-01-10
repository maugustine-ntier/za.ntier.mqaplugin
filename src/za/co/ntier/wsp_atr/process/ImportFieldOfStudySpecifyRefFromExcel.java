package za.co.ntier.wsp_atr.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.base.annotation.Parameter;
import org.apache.poi.ss.usermodel.*;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import za.co.ntier.wsp_atr.models.X_ZZ_Field_of_Study_Specify_Ref;

@org.adempiere.base.annotation.Process(
		name = "za.co.ntier.wsp_atr.process.ImportFieldOfStudySpecifyRefFromExcel")
public class ImportFieldOfStudySpecifyRefFromExcel extends SvrProcess {

    @Parameter(name = "FileName") // AD_Process_Para (Data Type = File)
    private String fileName;

    @Parameter(name = "DeleteExisting")
    private String deleteExistingStr; // Y/N

    @Parameter(name = "StartValue")
    private BigDecimal startValueBD; // default = 1

    @Parameter(name = "ColumnNo")
    private BigDecimal columnNoBD; // default = 0 (Column A)

    private boolean deleteExisting;

    @Override
    protected void prepare() {
        deleteExisting = "Y".equalsIgnoreCase(deleteExistingStr);
        if (startValueBD == null) startValueBD = BigDecimal.ONE;
        if (columnNoBD == null) columnNoBD = BigDecimal.ZERO;
    }

    @Override
    protected String doIt() throws Exception {

        if (fileName == null || fileName.trim().isEmpty())
            throw new IllegalArgumentException("Please select an Excel file.");

        final int clientId = Env.getAD_Client_ID(getCtx());
        final int orgId = Env.getAD_Org_ID(getCtx());

        if (deleteExisting) {
            int deleted = DB.executeUpdateEx(
                "DELETE FROM ZZ_Field_of_Study_Specify_Ref WHERE AD_Client_ID=?",
                new Object[]{ clientId },
                get_TrxName()
            );
            addLog("Deleted existing rows: " + deleted);
        }

        // Load ALL existing names (always skip duplicates)
        Set<String> existingNames = new HashSet<>();
        String loadSql =
            "SELECT LOWER(TRIM(Name)) " +
            "FROM ZZ_Field_of_Study_Specify_Ref " +
            "WHERE AD_Client_ID=?";
        java.sql.PreparedStatement ps = null;
        java.sql.ResultSet rs = null;

        try {
            ps = DB.prepareStatement(loadSql, get_TrxName());
            ps.setInt(1, clientId);
            rs = ps.executeQuery();
            while (rs.next()) {
                String n = rs.getString(1);
                if (n != null && !n.isEmpty())
                    existingNames.add(n);
            }
        } finally {
            DB.close(rs, ps);
        }

        int col = columnNoBD.intValue();               // 0 = A
        int valueCounter = startValueBD.intValue();    // 1,2,3...

        int imported = 0;
        int skippedBlank = 0;
        int skippedDuplicate = 0;

        DataFormatter formatter = new DataFormatter();
        FormulaEvaluator evaluator;

        File excelFile = new File(fileName);
        if (!excelFile.exists())
            throw new IllegalArgumentException("File not found: " + fileName);

        try (InputStream in = new FileInputStream(excelFile);
             Workbook wb = WorkbookFactory.create(in)) {

            evaluator = wb.getCreationHelper().createFormulaEvaluator();
            Sheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {
                Cell cell = row.getCell(col);
                String name = cell == null ? null : formatter.formatCellValue(cell, evaluator);
                if (name != null) name = name.trim();

                if (name == null || name.isEmpty()) {
                    skippedBlank++;
                    continue;
                }

                String key = name.toLowerCase();
                if (existingNames.contains(key)) {
                    skippedDuplicate++;
                    continue;
                }

                X_ZZ_Field_of_Study_Specify_Ref rec =
                    new X_ZZ_Field_of_Study_Specify_Ref(getCtx(), 0, get_TrxName());
                rec.setAD_Org_ID(orgId);
                rec.setName(name);
                rec.setValue(String.valueOf(valueCounter));

                rec.saveEx();

                existingNames.add(key);
                valueCounter++;
                imported++;
            }
        }

        return "Imported=" + imported
             + ", SkippedBlank=" + skippedBlank
             + ", SkippedDuplicate=" + skippedDuplicate;
    }
}

