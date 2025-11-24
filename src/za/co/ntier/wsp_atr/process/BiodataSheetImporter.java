package za.co.ntier.wsp_atr.process;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Util;

import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Biodata_Detail;
import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Submitted;
import za.co.ntier.wsp_atr.process.ImportWspAtrDataFromTemplate.IWspAtrSheetImporter;

// -------------------------------------------------------------------------
// Biodata Sheet Importer
// -------------------------------------------------------------------------

/**
 * Biodata sheet importer:
 *  - Sheet "Biodata"
 *  - Populates ZZ_WSP_ATR_Biodata_Detail
 */
public class BiodataSheetImporter implements IWspAtrSheetImporter {

    // Expected logical sheet name (same as generator)
    private static final String SHEET_BIODATA = "Biodata";

    // We assume data starts around row 7 (1-based), same as your generator
    private static final int DEFAULT_DATA_START_ROW_INDEX = 6; // 0-based

    // Canonical header -> enum field mapping
    private enum BioField {
        ID_PASSPORT,
        NAME_SURNAME,
        BIRTH_YEAR,
        GENDER,
        RACE,
        DISABLED,
        SA_CITIZEN,
        PROVINCE,
        MUNICIPALITY,
        HIGHEST_QUAL_TYPE,
        SPECIFY_FIELD_OF_STUDY,
        EMP_STATUS,
        EMP_START_YEAR,
        OCCUPATIONAL_LEVEL,
        ORG_STRUCTURE,
        POST_REFERENCE,
        JOB_TITLE,
        OFO_OCC_CODE,
        OFO_SPEC,
        OFO_OCC
    }

    private final Map<String, BioField> headerMap = new HashMap<>();

    public BiodataSheetImporter() {
        // Header texts as they appear (or likely appear) in the template,
        // mapped via canonical form (lowercase, trimmed, whitespaces collapsed)

        putHeader("id / passport no / employee number", BioField.ID_PASSPORT);
        putHeader("id/passport no/employee number", BioField.ID_PASSPORT);

        putHeader("name & surname", BioField.NAME_SURNAME);
        putHeader("name and surname", BioField.NAME_SURNAME);

        putHeader("birth year", BioField.BIRTH_YEAR);
        putHeader("birth year - true", BioField.BIRTH_YEAR);
        putHeader("birth year true", BioField.BIRTH_YEAR);

        putHeader("gender", BioField.GENDER);
        putHeader("race", BioField.RACE);
        putHeader("disabled?", BioField.DISABLED);
        putHeader("disabled", BioField.DISABLED);
        putHeader("sa citizen?", BioField.SA_CITIZEN);
        putHeader("sa citizen", BioField.SA_CITIZEN);

        putHeader("province", BioField.PROVINCE);
        putHeader("municipality", BioField.MUNICIPALITY);

        putHeader("highest qualification type", BioField.HIGHEST_QUAL_TYPE);
        putHeader("specify field of study for post-school qualifications or reason", BioField.SPECIFY_FIELD_OF_STUDY);
        putHeader("specify field of study for post-school qualifications or rea", BioField.SPECIFY_FIELD_OF_STUDY);

        putHeader("employment status", BioField.EMP_STATUS);
        putHeader("employment start year (year of engagement)", BioField.EMP_START_YEAR);

        putHeader("occupational levels for equity reporting purposes", BioField.OCCUPATIONAL_LEVEL);
        putHeader("organisation structure filter (optional)", BioField.ORG_STRUCTURE);
        putHeader("post reference (optional)", BioField.POST_REFERENCE);
        putHeader("job title", BioField.JOB_TITLE);

        putHeader("ofo occupation code", BioField.OFO_OCC_CODE);
        putHeader("ofo specialisation", BioField.OFO_SPEC);
        putHeader("ofo occupation", BioField.OFO_OCC);
    }

    private void putHeader(String raw, BioField field) {
        headerMap.put(canonicalHeader(raw), field);
    }

    @Override
    public String getSheetName() {
        return SHEET_BIODATA;
    }

    @Override
    public int importData(Workbook wb,
                          X_ZZ_WSP_ATR_Submitted header,
                          String trxName,
                          SvrProcess process,
                          DataFormatter formatter) {

        Sheet sheet = wb.getSheet(SHEET_BIODATA);
        if (sheet == null) {
            throw new AdempiereException("Sheet '" + SHEET_BIODATA + "' not found in workbook.");
        }

        // Determine header row (similar to your generator logic, but simpler)
        int headerRowIdx = findHeaderRowIndex(sheet, formatter);
        if (headerRowIdx < 0) {
            throw new AdempiereException("No header row found in sheet '" + SHEET_BIODATA + "'");
        }

        Row headerRow = sheet.getRow(headerRowIdx);
        if (headerRow == null) {
            throw new AdempiereException("Header row is null in sheet '" + SHEET_BIODATA + "'");
        }

        int lastCell = headerRow.getLastCellNum();
        if (lastCell <= 0) {
            throw new AdempiereException("No header columns in sheet '" + SHEET_BIODATA + "'");
        }

        // Map: column index -> BioField
        Map<Integer, BioField> colToField = new HashMap<>();

        for (int col = 0; col < lastCell; col++) {
            String h = getCellText(headerRow, col, formatter);
            if (Util.isEmpty(h, true))
                continue;

            String key = canonicalHeader(h);
            BioField field = headerMap.get(key);
            if (field != null) {
                colToField.put(col, field);
            } else {
                // Ignore unknown columns but helpful to know
                process.addLog("Biodata: Ignoring column " + col + " with header '" + h + "'");
            }
        }

        if (colToField.isEmpty()) {
            throw new AdempiereException("No recognizable columns found in Biodata sheet headers.");
        }

        int imported = 0;
        int lastRow = sheet.getLastRowNum();

        // Data usually starts around row 7; ensure we don't start before headerRow+1
        int dataStart = Math.max(DEFAULT_DATA_START_ROW_INDEX, headerRowIdx + 1);

        for (int r = dataStart; r <= lastRow; r++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;

            // Simple check: if ID/Name is blank, consider row empty
            if (isRowEffectivelyEmpty(row, colToField, formatter)) {
                continue;
            }

            X_ZZ_WSP_ATR_Biodata_Detail line =
                    new X_ZZ_WSP_ATR_Biodata_Detail(Env.getCtx(), 0, trxName);

            line.setZZ_WSP_ATR_Submitted_ID(header.getZZ_WSP_ATR_Submitted_ID());

            for (Map.Entry<Integer, BioField> e : colToField.entrySet()) {
                int colIdx = e.getKey();
                BioField field = e.getValue();

                Cell cell = row.getCell(colIdx);
                String txt = getCellText(row, colIdx, formatter);

                if (Util.isEmpty(txt, true)) {
                    continue;
                }

                switch (field) {
                    case ID_PASSPORT: {
                        BigDecimal bd = parseBigDecimal(txt);
                        line.setID_Passport_No_Employee_Number(bd);
                        break;
                    }
                    case NAME_SURNAME: {
                        line.setName_Surname(truncate(txt, 200));
                        break;
                    }
                    case BIRTH_YEAR: {
                        BigDecimal bd = parseBigDecimal(txt);
                        line.setBirth_Year_TRUE(bd);
                        break;
                    }
                    case GENDER: {
                        Integer id = lookupIdByName("ZZ_Gender_Ref", txt, trxName);
                        if (id != null) line.setGender_ID(id);
                        break;
                    }
                    case RACE: {
                        Integer id = lookupIdByName("ZZ_Equity_Ref", txt, trxName);
                        if (id != null) line.setRace_ID(id);
                        break;
                    }
                    case DISABLED: {
                        Integer id = lookupIdByName("ZZ_No_Yes_Ref", txt, trxName);
                        if (id != null) line.setDisabled_ID(id);
                        break;
                    }
                    case SA_CITIZEN: {
                        Integer id = lookupIdByName("ZZ_No_Yes_Ref", txt, trxName);
                        if (id != null) line.setSA_Citizen_ID(id);
                        break;
                    }
                    case PROVINCE: {
                        Integer id = lookupIdByName("ZZ_Province_Ref", txt, trxName);
                        if (id != null) line.setProvince_ID(id);
                        break;
                    }
                    case MUNICIPALITY: {
                        Integer id = lookupIdByName("ZZ_Municipality_Ref", txt, trxName);
                        if (id != null) line.setMunicipality_ID(id);
                        break;
                    }
                    case HIGHEST_QUAL_TYPE: {
                        Integer id = lookupIdByName("ZZ_Qualification_Type_Ref", txt, trxName);
                        if (id != null) line.setHighest_Qualification_Type_ID(id);
                        break;
                    }
                    case SPECIFY_FIELD_OF_STUDY: {
                        line.setSpecify_field_of_Study_for_Post_School_qualifications_or_reason(
                                truncate(txt, 200));
                        break;
                    }
                    case EMP_STATUS: {
                        Integer id = lookupIdByName("ZZ_Appointment_Ref", txt, trxName);
                        if (id != null) line.setEmployment_Status_ID(id);
                        break;
                    }
                    case EMP_START_YEAR: {
                        // Try Name first, then Value
                        Integer id = lookupIdByNameOrValue("ZZ_Year_Ref", txt, trxName);
                        if (id != null) line.setEmployment_Start_Year_year_of_engagement_ID(id);
                        break;
                    }
                    case OCCUPATIONAL_LEVEL: {
                        line.setOccupational_Levels_For_Equity_Reporting_Purposes(
                                truncate(txt, 200));
                        break;
                    }
                    case ORG_STRUCTURE: {
                        line.setOrganisation_Structure_Filter_Optional(truncate(txt, 200));
                        break;
                    }
                    case POST_REFERENCE: {
                        line.setPost_Reference_Optional(truncate(txt, 200));
                        break;
                    }
                    case JOB_TITLE: {
                        line.setJob_Title(truncate(txt, 200));
                        break;
                    }
                    case OFO_OCC_CODE: {
                        Integer id = lookupIdByNameOrValue("ZZ_Occupations_Ref", txt, trxName);
                        if (id != null) line.setOFO_Occupation_Code_ID(id);
                        break;
                    }
                    case OFO_SPEC: {
                        Integer id = lookupIdByNameOrValue("ZZ_Specializations_Ref", txt, trxName);
                        if (id != null) line.setOFO_Specialisation_ID(id);
                        break;
                    }
                    case OFO_OCC: {
                        Integer id = lookupIdByNameOrValue("ZZ_Occupations_Ref", txt, trxName);
                        if (id != null) line.setOFO_Occupation_ID(id);
                        break;
                    }
                }
            }

            // Save detail line
            line.saveEx();
            imported++;
        }

        return imported;
    }

    // -------------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------------

    /** Canonicalize a header or label: lowercase, trim, collapse whitespace. */
    private static String canonicalHeader(String s) {
        if (s == null)
            return "";
        String cleaned = s.replace('\n', ' ')
                          .replace('\r', ' ')
                          .trim()
                          .replaceAll("\\s+", " ");
        return cleaned.toLowerCase(Locale.ROOT);
    }

    /** Find a reasonable header row: first row with >0 non-empty cells. */
    private int findHeaderRowIndex(Sheet sheet, DataFormatter formatter) {
        int lastRow = sheet.getLastRowNum();
        for (int r = 0; r <= lastRow; r++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;
            int nonEmpty = 0;
            int lastCell = row.getLastCellNum();
            for (int c = 0; c < lastCell; c++) {
                String txt = getCellText(row, c, formatter);
                if (!Util.isEmpty(txt, true))
                    nonEmpty++;
            }
            if (nonEmpty > 0) {
                return r;
            }
        }
        return -1;
    }

    private String getCellText(Row row, int col, DataFormatter formatter) {
        if (row == null)
            return "";
        Cell cell = row.getCell(col);
        if (cell == null)
            return "";
        try {
            // Do NOT evaluate formulas aggressively; DataFormatter will handle nicely
            return formatter.formatCellValue(cell).trim();
        } catch (Exception e) {
            // Fallback: simple string / numeric
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

    private boolean isRowEffectivelyEmpty(Row row,
                                          Map<Integer, BioField> colToField,
                                          DataFormatter formatter) {
        for (Integer col : colToField.keySet()) {
            String txt = getCellText(row, col, formatter);
            if (!Util.isEmpty(txt, true))
                return false;
        }
        return true;
    }

    private BigDecimal parseBigDecimal(String txt) {
        try {
            return new BigDecimal(txt.trim());
        } catch (Exception e) {
            return Env.ZERO;
        }
    }

    private String truncate(String s, int max) {
        if (s == null)
            return null;
        s = s.trim();
        if (s.length() > max)
            return s.substring(0, max);
        return s;
    }

    private Integer lookupIdByName(String tableName, String name, String trxName) {
        if (Util.isEmpty(name, true))
            return null;

        name = name.trim();
        MTable t = MTable.get(Env.getCtx(), tableName);
        if (t == null || t.getAD_Table_ID() <= 0)
            return null;

        int id = new Query(Env.getCtx(), t.getTableName(), "Name=?", trxName)
                .setParameters(name)
                .firstId();
        if (id <= 0) {
            // Optionally, log something here
            return null;
        }
        return id;
    }

    private Integer lookupIdByNameOrValue(String tableName, String txt, String trxName) {
        if (Util.isEmpty(txt, true))
            return null;
        txt = txt.trim();

        MTable t = MTable.get(Env.getCtx(), tableName);
        if (t == null || t.getAD_Table_ID() <= 0)
            return null;

        // Try Value first
        int id = new Query(Env.getCtx(), t.getTableName(), "Value=?", trxName)
                .setParameters(txt)
                .firstId();
        if (id > 0)
            return id;

        // Then Name
        id = new Query(Env.getCtx(), t.getTableName(), "Name=?", trxName)
                .setParameters(txt)
                .firstId();
        if (id > 0)
            return id;

        return null;
    }
}