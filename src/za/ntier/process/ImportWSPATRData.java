package za.ntier.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adempiere.base.annotation.Parameter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.compiere.process.ProcessInfo;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import za.ntier.models.MBPartner_New;
import za.ntier.models.X_ZZ_WSP_ATR_Approvals;


@org.adempiere.base.annotation.Process(name="za.ntier.process.ImportWSPATRData")
public class ImportWSPATRData extends SvrProcess {
	@Parameter(name="FileName")
	private String filePath;

	@Parameter(name="ClearExisting")
	private boolean clearExisting;  

	private List<String> unresolvedList = new ArrayList<>();
	private static final DataFormatter DF = new DataFormatter();

	@Override
	protected void prepare() {

	}

	@Override
	protected String doIt() throws Exception {
		int deleted = 0;
		if (clearExisting) {            
			deleted = DB.executeUpdateEx(
					"DELETE FROM ZZ_WSP_ATR_Approvals",
					get_TrxName()
					);
			addLog("Cleared existing data: " + deleted + " row(s)");
		}

		if (filePath == null || filePath.isEmpty())
			throw new IllegalArgumentException("File path not provided.");


		try (FileInputStream fis = new FileInputStream(filePath);
				Workbook wb = WorkbookFactory.create(fis)) {

			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> it = sheet.iterator();
			if (!it.hasNext()) throw new IllegalArgumentException("Empty sheet");

			// 1) Read header row and build index
			Row header = it.next();
			Map<String,Integer> H = buildHeaderIndex(header);

			// Optional: verify required columns exist (fail fast with a clear message)
			List<String> required = Arrays.asList(
					"SDLNumber", "ParentSDLNumber", "WSPYear", "PlanningGrantStatus", "TotalEmployment"
					);
			for (String col : required) {
				if (!H.containsKey(norm(col))) {
					throw new IllegalArgumentException("Missing required column: " + col);
				}
			}

			// 2) Read the rest by header
			while (it.hasNext()) {
				Row row = it.next();
				if (row == null) continue;

				String key1          = byHeader(row, H, "SDLNumber");
				String key2          = byHeader(row, H, "ParentSDLNumber");
				String financialYear = byHeader(row, H, "WSPYear");
				String grantStatus   = byHeader(row, H, "PlanningGrantStatus");
				String numEmployeesStr  = byHeader(row, H, "TotalEmployment");

				String value = null;
				MBPartner_New bp = null;
				int cnt =  DB.getSQLValue(get_TrxName(),"Select count(*) from C_Bpartner bp where bp.value = ?",key1);
				value = key1;
				if (cnt <= 0) {
					if (key2 != null && !key2.isBlank()) {
						cnt =  DB.getSQLValue(get_TrxName(),"Select count(*) from C_Bpartner bp where bp.value = ?",key2);
						if (cnt > 0) {
							value = key2;
						}
					}
				}
				if (cnt > 0) {
					int c_BPartner_ID =  DB.getSQLValue(get_TrxName(),"Select C_BPartner_id from C_Bpartner bp where bp.value = ?",value);
					bp = new MBPartner_New(getCtx(), c_BPartner_ID, get_TrxName());
				} else {			
					unresolvedList.add(key1 + "," + key2);
					continue;
				}

				// Update number of employees on BP
				try {
					if (numEmployeesStr != null && !numEmployeesStr.trim().equals("")) {
						int numEmployees = Integer.parseInt(numEmployeesStr.trim());
						bp.setZZ_Number_Of_Employees(new BigDecimal(numEmployees));
						bp.saveEx();
					}
				} catch (Exception e) {
					log.warning("Invalid employee count for: " + key1);
					continue;
				}

				// Check if record exists for same BP and Financial Year
				String sql = "SELECT ZZ_WSP_ATR_Approvals_ID FROM ZZ_WSP_ATR_Approvals WHERE C_BPartner_ID=? AND zz_financial_year=?";
				int wspID = DB.getSQLValue(get_TrxName(), sql, bp.get_ID(), financialYear);

				X_ZZ_WSP_ATR_Approvals record;
				if (wspID > 0) {
					record = new X_ZZ_WSP_ATR_Approvals(getCtx(), wspID, get_TrxName());
				} else {
					record = new X_ZZ_WSP_ATR_Approvals(getCtx(), 0, get_TrxName());
				}

				record.setC_BPartner_ID(bp.get_ID());
				record.setZZ_Financial_Year(financialYear);
				record.setZZ_Grant_Status((grantStatus.equals("Approved"))? "A" : "R" );
				record.setProcessedOn(new Timestamp(System.currentTimeMillis()));
				record.saveEx();
			}

			if (!unresolvedList.isEmpty()) {
				// Use tmp directory (works cross-platform)
				File logFile = new File(System.getProperty("java.io.tmpdir"), "unresolved_bps.csv");

				try (PrintWriter writer = new PrintWriter(logFile)) {
					writer.println("Key1,Key2");
					for (String line : unresolvedList) {
						writer.println(line);
					}
				}

				addLog("Unresolved BPs: " + unresolvedList.size());

				// ✅ This enables the Download link in Process Monitor
				ProcessInfo pi = getProcessInfo();
				if (pi != null) {
					pi.setExport(true);
					pi.setExportFile(logFile);
					pi.setExportFileExtension("csv");
				}
				addLog("Unresolved BPs: " + unresolvedList.size() + " (Download from Process Monitor)");
			}




			return "WSP-ATR Approvals import complete.";
		}
	}

	/** Normalize header text for matching (case/space-insensitive). */
	private String norm(String s) {
		return s == null ? "" : s.trim().toLowerCase().replaceAll("\\s+", " ");
	}

	/** Build a map: normalized header -> column index, from the first row. */
	private Map<String,Integer> buildHeaderIndex(Row headerRow) {
		Map<String,Integer> map = new HashMap<>();
		for (Cell c : headerRow) {
			String name = norm(DF.formatCellValue(c));
			if (!name.isEmpty()) map.put(name, c.getColumnIndex());
		}
		return map;
	}

	/** Read a cell by header name using the header map. */
	private String byHeader(Row row, Map<String,Integer> H, String header) {
		Integer idx = H.get(norm(header));
		if (idx == null) return "";
		Cell cell = row.getCell(idx, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
		return cell == null ? "" : DF.formatCellValue(cell).trim();
	}
}
