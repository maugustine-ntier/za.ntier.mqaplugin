package za.ntier.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.annotation.Parameter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
	private List<String> unresolvedList = new ArrayList<>();

	@Override
	protected void prepare() {

	}

	@Override
	protected String doIt() throws Exception {
		if (filePath == null || filePath.isEmpty())
			throw new IllegalArgumentException("File path not provided.");

		FileInputStream fis = new FileInputStream(filePath);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheetAt(0);

		for (Row row : sheet) {
			if (row.getRowNum() == 0) continue; // skip header

			String key1 = getCell(row, 1);
			String key2 = getCell(row, 2);
			String financialYear = getCell(row, 3);
			String grantStatus = getCell(row, 7);
			String numEmployeesStr = getCell(row, 14);

			
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
				int numEmployees = Integer.parseInt(numEmployeesStr);
				bp.setZZ_Number_Of_Employees(new BigDecimal(numEmployees));
				bp.saveEx();
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
				pi.setExportFile(logFile);
			}
		}




		return "WSP-ATR Approvals import complete.";
	}

	private String getCell(Row row, int colIndex) {
		Cell cell = row.getCell(colIndex);
		if (cell == null) return "";
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue().trim();
	}
}
