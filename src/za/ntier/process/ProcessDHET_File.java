package za.ntier.process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;

import org.adempiere.base.annotation.Parameter;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MLocation;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import za.ntier.models.MBPartner_New;

@org.adempiere.base.annotation.Process(name="za.ntier.process.ProcessDHET_File")
public class ProcessDHET_File extends SvrProcess {

	@Parameter(name="FileName")
	private String filePath;

	@Override
	protected void prepare() {

	}

	@Override
	protected String doIt() throws Exception {
		if (filePath == null || filePath.isEmpty()) {
			throw new IllegalArgumentException("File path not provided.");
		}
		
	    // STEP 1: Reset MQA_Sector to 'N' for all BPs currently marked 'Y'
	    String resetSQL = "UPDATE C_BPartner SET ZZ_Is_MQA_Sector = 'N' WHERE ZZ_Is_MQA_Sector = 'Y' and AD_Client_ID = 1000000";
	    int resetCount = DB.executeUpdateEx(resetSQL, get_TrxName());
	    addLog("Reset MQA_Sector to 'N' for " + resetCount + " BPs");


	    int cntBPsAdded = 0;
	    int cntBPsUpdated = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			boolean isFirstLine = true;

			while ((line = reader.readLine()) != null) {
				if (isFirstLine) {
					isFirstLine = false; // Skip header
					continue;
				}

				String[] fields = parseCSVLine(line);
				if (fields.length < 59) {
					log.warning("Skipping invalid line: " + line);
					continue;
				}

				String sdlNumber = unquote(fields[0]);
				if (sdlNumber == null || sdlNumber.length() <= 0) {
					log.warning("Skipping invalid line with no sdlNumber: " + line);
					continue;
				}
				String name = unquote(fields[4]);
				String numEmployeesStr = unquote(fields[46]);
				String regNo = unquote(fields[9]);

				// Check if BP exists
				MBPartner_New bp = null;
				int cnt =  DB.getSQLValue(get_TrxName(),"Select count(*) from C_Bpartner bp where bp.value = ?",sdlNumber);
				if (cnt > 0) {
					int c_BPartner_ID =  DB.getSQLValue(get_TrxName(),"Select C_BPartner_id from C_Bpartner bp where bp.value = ?",sdlNumber);
					bp = new MBPartner_New(getCtx(), c_BPartner_ID, get_TrxName());
				}
				if (cnt  <= 0) {
					cntBPsAdded++;
					bp = new MBPartner_New(getCtx(), 0, get_TrxName());
					bp.setValue(sdlNumber);
					bp.setName(name);
					bp.setReferenceNo(regNo);
					bp.setC_BP_Group_ID(1000018);  // UNKNOWN GROUP
					bp.setIsVendor(true);
					bp.setIsCustomer(false);
					bp.setIsEmployee(false);
					bp.setIsProspect(false);

					// Custom column ZZ_Number_Of_Employees
					try {
						int numEmployees = Integer.parseInt(numEmployeesStr);
						bp.setZZ_Number_Of_Employees(new BigDecimal(numEmployees));
					} catch (NumberFormatException e) {
						log.warning("Invalid NumberOfEmployees for " + sdlNumber);
					}
					bp.setZZ_Is_MQA_Sector(true);

					bp.saveEx();


					// --- BUSINESS ADDRESS ---
					MLocation businessLoc = new MLocation(getCtx(), 0, get_TrxName());
					businessLoc.setAddress1(unquote(fields[27]));
					businessLoc.setAddress2(unquote(fields[28]));
					businessLoc.setAddress3(unquote(fields[29]));
					businessLoc.setAddress4(unquote(fields[30]));
					businessLoc.setPostal(unquote(fields[31]));
					businessLoc.saveEx();

					MBPartnerLocation businessBPL = new MBPartnerLocation(bp);
					businessBPL.setC_Location_ID(businessLoc.getC_Location_ID());
					businessBPL.setName("Business Address");
					businessBPL.saveEx();

					// --- RESIDENTIAL ADDRESS ---
					MLocation residentialLoc = new MLocation(getCtx(), 0, get_TrxName());
					residentialLoc.setAddress1(unquote(fields[16]));
					residentialLoc.setAddress2(unquote(fields[17]));
					residentialLoc.setAddress3(unquote(fields[18]));
					residentialLoc.setAddress4(unquote(fields[19]));
					residentialLoc.setPostal(unquote(fields[20]));
					residentialLoc.saveEx();

					MBPartnerLocation residentialBPL = new MBPartnerLocation(bp);
					residentialBPL.setC_Location_ID(residentialLoc.getC_Location_ID());
					residentialBPL.setName("Residential Address");
					residentialBPL.saveEx();

					// --- POSTAL ADDRESS ---
					MLocation postalLoc = new MLocation(getCtx(), 0, get_TrxName());
					postalLoc.setAddress1(unquote(fields[37]));
					postalLoc.setAddress2(unquote(fields[38]));
					postalLoc.setAddress3(unquote(fields[39]));
					postalLoc.setAddress4(unquote(fields[40]));
					postalLoc.setPostal(unquote(fields[41]));
					postalLoc.saveEx();

					MBPartnerLocation postalBPL = new MBPartnerLocation(bp);
					postalBPL.setC_Location_ID(postalLoc.getC_Location_ID());
					postalBPL.setName("Postal Address");
					postalBPL.saveEx();
				} else {
					cntBPsUpdated++;
					bp.setZZ_Is_MQA_Sector(true);
					if (bp.getReferenceNo() == null && regNo != null) {
						bp.setReferenceNo(regNo);
					}
					if (bp.getZZ_Number_Of_Employees().compareTo(new BigDecimal("0")) <= 0) {
						try {
							int numEmployees = Integer.parseInt(numEmployeesStr);
							bp.setZZ_Number_Of_Employees(new BigDecimal(numEmployees));
						} catch (NumberFormatException e) {
							log.warning("Invalid NumberOfEmployees for " + sdlNumber);
						}
					}
					bp.saveEx();
				}
			}
		}
		addLog("New Business Partners Created:  " + cntBPsAdded) ;
		addLog("Business Partners Updated    :  " + cntBPsUpdated);

		return "CSV file processed and Business Partners created.";
	}

	private String[] parseCSVLine(String line) {
		// Handle quoted commas
		return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	}

	private String unquote(String s) {
		return s == null ? null : s.replaceAll("^\"|\"$", "").trim();
	}


}
