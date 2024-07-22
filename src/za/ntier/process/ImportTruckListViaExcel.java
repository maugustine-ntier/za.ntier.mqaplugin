package za.ntier.process;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.window.ZkReportViewerProviderRGN;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellCopyContext;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.compiere.model.MImportTemplate;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import za.ntier.models.MDriver;
import za.ntier.models.MTruck;
import za.ntier.models.MTruckList;


@org.adempiere.base.annotation.Process
public class ImportTruckListViaExcel extends SvrProcess {



	Map<String, Integer> columnmap = new HashMap<String,Integer>(); 
	String p_FileName = "";
	private String p_horse = "HORSE";
	private String p_trailer1 = "TRAILER1";
	private String p_trailer2 = "TRAILER2";
	private String p_driver = "DRIVER";
	private String p_driver_name = "DriverName";
	private String p_loads = "LOADS";
	private String p_fleet = "FLEET";
	private int counter = 0;
	private Workbook errorWb = null;
	private FileInputStream inp = null;
	private File importFile = null;
	private Sheet errorSheet = null;
	private int maxCols = 0;
	private Map<Integer, String> errorMsgs = new HashMap<>();

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null) {
				;
			} else if (name.equals("FileName")) {
				p_FileName = para[i].getParameterAsString();
			} else {
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
		} 
	}


	@Override
	protected String doIt() throws Exception {
		importFile = new File(p_FileName);

		String msg = updateByExcel(importFile);

		return msg ;
	}

	private String updateByExcel(File importFile) throws Exception {
		Workbook workbook  = null;
		int zz_Transporters_ID = getRecord_ID();

		String msg = null;
		try {
			inp = new FileInputStream(importFile);
			ZipSecureFile.setMinInflateRatio(-1.0d);
			if (importFile.getName().toUpperCase().endsWith(MImportTemplate.IMPORTTEMPLATETYPE_XLS)) {
				HSSFWorkbookFactory xlsWbf = new HSSFWorkbookFactory();
				workbook = xlsWbf.create(inp);
			} else if (importFile.getName().toUpperCase().endsWith(MImportTemplate.IMPORTTEMPLATETYPE_XLSX)) {
				XSSFWorkbookFactory xlsxWbf = new XSSFWorkbookFactory();
				workbook = xlsxWbf.create(inp);
			} else {
				// unexpected error
				throw new AdempiereException("Wrong template type -> " + importFile.getName());
			}

			int noOfErrorLines = checkTruckList(workbook,zz_Transporters_ID);

			msg = loadTruckList(workbook, zz_Transporters_ID);

			if (noOfErrorLines > 0) {  // dont display if we just have headers
				//String fileName = writeOutErrorLogFile(errorSheet);
				//msg = "There are errors on the file.  Please check the error Log file : " + fileName;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}	
		finally {
			if (inp != null) {
				inp.close();
			}
			if (workbook != null) {
				workbook.close();
			}
		}

		return msg;
	}

	private int checkTruckList(Workbook workbook, int zz_Transporters_ID) throws Exception {
		errorSheet = createErrorXLS();
		int noOfHeaderLines = 0;
		Sheet sheet = workbook.getSheetAt(0);
		int data_start_row = setColumnName(sheet);
		Iterator<Row> rowIterator = sheet.iterator();
		int rowNoToWrite = 0;
		while (rowIterator.hasNext()) 
		{
			Row row = rowIterator.next();
			if (row.getRowNum() < data_start_row) {
				writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), null,row);
				rowNoToWrite++;
				noOfHeaderLines++;
				continue;
			}

			String horse = null;
			String trailer_1 = null;
			String trailer_2 = null;
			String driver_IDNo = null;
			try {
				try {
					if (row.getCell(columnmap.get(p_horse)).getCellType().equals(CellType.STRING)) {
						horse =   row.getCell(columnmap.get(p_horse)).getStringCellValue();
					} else if (row.getCell(columnmap.get(p_horse)).getCellType().equals(CellType.NUMERIC)) {
						horse =   row.getCell(columnmap.get(p_horse)).getNumericCellValue() + "";
					}
					if (row.getCell(columnmap.get(p_trailer1)).getCellType().equals(CellType.STRING)) {
						trailer_1 =   row.getCell(columnmap.get(p_trailer1)).getStringCellValue();
					} else if (row.getCell(columnmap.get(p_trailer1)).getCellType().equals(CellType.NUMERIC)){
						trailer_1 =   row.getCell(columnmap.get(p_trailer1)).getNumericCellValue() + "";
					}
					if (row.getCell(columnmap.get(p_trailer2)).getCellType().equals(CellType.STRING)) {
						trailer_2 =   row.getCell(columnmap.get(p_trailer2)).getStringCellValue();
					} else if (row.getCell(columnmap.get(p_trailer2)).getCellType().equals(CellType.NUMERIC)){
						trailer_2 =   row.getCell(columnmap.get(p_trailer2)).getNumericCellValue() + "";
					}
					if (row.getCell(columnmap.get(p_driver)).getCellType().equals(CellType.STRING)) {
						driver_IDNo =   row.getCell(columnmap.get(p_driver)).getStringCellValue(); 
					} else if (row.getCell(columnmap.get(p_driver)).getCellType().equals(CellType.NUMERIC)){
						BigInteger bint = BigDecimal.valueOf(row.getCell(columnmap.get(p_driver)).getNumericCellValue()).toBigInteger() ;
						driver_IDNo =   bint.toString();
					}
					//	if (row.getCell(columnmap.get(p_loads)).getCellType().equals(CellType.NUMERIC)) {
					//	no_Of_Loads =   row.getCell(columnmap.get(p_loads)).getNumericCellValue();
					//}
				} catch(Exception e) {
					System.out.println("getCell is null");
					continue;
				}
				if ((horse == null || horse.trim().equals(""))
						&& (trailer_1 == null || trailer_1.trim().equals(""))
						&& (trailer_2 == null || trailer_2.trim().equals(""))
						&& (driver_IDNo == null|| driver_IDNo.trim().equals(""))) {
					continue;
				}
				//	MTruckList mTruckList = new MTruckList(getCtx(), 0, get_TrxName());
				if (horse == null || horse.trim().equals("")) {
					writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Horse Missing",row);
					rowNoToWrite++;
				} else {
					MTruck mTruck_horse = MTruck.getTruck(getCtx(), horse,get_TrxName());
					if (mTruck_horse == null) {
						writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Horse Does not exist (Will be Created)",row);
						rowNoToWrite++;
					} else {
						if (!mTruck_horse.getZZ_Truck_Type().equals("H")) {
							writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Horse on the database is marked as a Trailer",row);
							rowNoToWrite++;
						}
						if (mTruck_horse.getZZ_Vehicle_License_Expiry() == null || (DB.TO_DATE(mTruck_horse.getZZ_Vehicle_License_Expiry()).compareTo(DB.TO_DATE(new Timestamp(System.currentTimeMillis()))) < 0)) {
							String stringDt = "";
							if (mTruck_horse.getZZ_Vehicle_License_Expiry() != null) {
								stringDt = new SimpleDateFormat("dd/MM/yyyy").format(mTruck_horse.getZZ_Vehicle_License_Expiry()); 
							}
							writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Horse's License has expired or is missing: " + stringDt,row);
							rowNoToWrite++;
						}
					}
				}

				if (trailer_1 == null  || trailer_1.trim().equals("")) {
					writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Trailer 1 Missing",row);
					rowNoToWrite++;
				} else {
					MTruck mTruck_Trailer_1 = MTruck.getTruck(getCtx(), trailer_1,get_TrxName());
					if (mTruck_Trailer_1 == null) {
						writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Trailer 1 Does not exist (Will be Created)",row);
						rowNoToWrite++;
					} else {
						if (!mTruck_Trailer_1.getZZ_Truck_Type().equals("T")) {
							writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Trailer 1 on the database is marked as a Horse",row);
							rowNoToWrite++;
						}
						if (mTruck_Trailer_1.getZZ_Vehicle_License_Expiry() == null || (DB.TO_DATE(mTruck_Trailer_1.getZZ_Vehicle_License_Expiry()).compareTo(DB.TO_DATE(new Timestamp(System.currentTimeMillis()))) < 0)) {
							String stringDt = "";
							if (mTruck_Trailer_1.getZZ_Vehicle_License_Expiry() != null) {
								stringDt = new SimpleDateFormat("dd/MM/yyyy").format(mTruck_Trailer_1.getZZ_Vehicle_License_Expiry()); 
							}
							writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Trailer 1's License has expired or is missing: " + stringDt,row);
							rowNoToWrite++;
						}
					}
				}

				if (trailer_2 == null || trailer_2.trim().equals("")) {
					writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Trailer 1 Missing",row);
					rowNoToWrite++;
				} else {
					MTruck mTruck_Trailer_2 = MTruck.getTruck(getCtx(), trailer_2,get_TrxName());
					if (mTruck_Trailer_2 == null) {
						writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Trailer 2 Does not exist (Will be Created)",row);
						rowNoToWrite++;
					} else {
						if (!mTruck_Trailer_2.getZZ_Truck_Type().equals("T")) {
							writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Trailer 2 on the database is marked as a Horse",row);
							rowNoToWrite++;
						}
						if (mTruck_Trailer_2.getZZ_Vehicle_License_Expiry() == null || (DB.TO_DATE(mTruck_Trailer_2.getZZ_Vehicle_License_Expiry()).compareTo(DB.TO_DATE(new Timestamp(System.currentTimeMillis()))) < 0)) {
							String stringDt = "";
							if (mTruck_Trailer_2.getZZ_Vehicle_License_Expiry() != null) {
								stringDt = new SimpleDateFormat("dd/MM/yyyy").format(mTruck_Trailer_2.getZZ_Vehicle_License_Expiry()); 
							}
							writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Trailer 2's License has expired or is missing: " + stringDt,row);
							rowNoToWrite++;
						}
					}
				}

				if (driver_IDNo == null || driver_IDNo.trim().equals("")) {
					writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Driver ID is Missing",row);
					rowNoToWrite++;
				} else {
					MDriver mDriver = MDriver.getDriver(getCtx(), driver_IDNo,get_TrxName());
					if (mDriver == null) {
						writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Driver does not Exist on the database (Will be Created)",row);
						rowNoToWrite++;
					} else {
						if (!mDriver.isZZ_Is_Valid()) {
							//	writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Driver is marked as Invalid on the database",row);
							//	rowNoToWrite++;
						}
						if (mDriver.getZZ_License_Expiry_Date() == null || (DB.TO_DATE(mDriver.getZZ_License_Expiry_Date()).compareTo(DB.TO_DATE(new Timestamp(System.currentTimeMillis()))) < 0)) {
							String stringDt = "";
							if (mDriver.getZZ_License_Expiry_Date() != null) {
								stringDt = new SimpleDateFormat("dd/MM/yyyy").format(mDriver.getZZ_License_Expiry_Date()); 
							}
							writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), "Driver has a expired Driver's License : " + stringDt,row);
							rowNoToWrite++;
						}
					}
				}




			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println();
				throw e;
			}
		}
		if (rowNoToWrite > noOfHeaderLines) {
			return rowNoToWrite;
		} else {
			return 0;
		}
	}

	private String loadTruckList(Workbook workbook, int zz_Transporters_ID) throws Exception {
		String msg = null;
		int rowNoToWrite = 0;
		Sheet sheet = workbook.getSheetAt(0);
		int data_start_row = setColumnName(sheet);
		Iterator<Row> rowIterator = sheet.iterator();
		// pass first row
		rowIterator.next();
		while (rowIterator.hasNext()) 
		{
			Row row = rowIterator.next();
			if (row.getRowNum() < data_start_row) {
				continue;
			}
			String horse = null;
			String trailer_1 = null;
			String trailer_2 = null;
			String driver_IDNo = null;
			Double no_Of_Loads = null;
			try {
				if (row.getCell(columnmap.get(p_horse)) == null) {
					continue;
				}
				if (row.getCell(columnmap.get(p_horse)).getCellType().equals(CellType.STRING)) {
					horse =   row.getCell(columnmap.get(p_horse)).getStringCellValue();
				} else if (row.getCell(columnmap.get(p_horse)).getCellType().equals(CellType.NUMERIC)) {
					horse =   row.getCell(columnmap.get(p_horse)).getNumericCellValue() + "";
				}
				if (row.getCell(columnmap.get(p_trailer1)).getCellType().equals(CellType.STRING)) {
					trailer_1 =   row.getCell(columnmap.get(p_trailer1)).getStringCellValue();
				} else if (row.getCell(columnmap.get(p_trailer1)).getCellType().equals(CellType.NUMERIC)){
					trailer_1 =   row.getCell(columnmap.get(p_trailer1)).getNumericCellValue() + "";
				}
				if (row.getCell(columnmap.get(p_trailer2)).getCellType().equals(CellType.STRING)) {
					trailer_2 =   row.getCell(columnmap.get(p_trailer2)).getStringCellValue();
				} else if (row.getCell(columnmap.get(p_trailer2)).getCellType().equals(CellType.NUMERIC)){
					trailer_2 =   row.getCell(columnmap.get(p_trailer2)).getNumericCellValue() + "";
				}
				if (row.getCell(columnmap.get(p_driver)).getCellType().equals(CellType.STRING)) {
					driver_IDNo =   row.getCell(columnmap.get(p_driver)).getStringCellValue(); 
				} else if (row.getCell(columnmap.get(p_driver)).getCellType().equals(CellType.NUMERIC)){
					BigInteger bint = BigDecimal.valueOf(row.getCell(columnmap.get(p_driver)).getNumericCellValue()).toBigInteger() ;
					driver_IDNo =   bint.toString();
				}
				if (columnmap.get(p_loads) != null && row.getCell(columnmap.get(p_loads)) != null && row.getCell(columnmap.get(p_loads)).getCellType().equals(CellType.NUMERIC)) {
					no_Of_Loads =   row.getCell(columnmap.get(p_loads)).getNumericCellValue(); 
				}
				String driversName = null;
				if (row.getCell(columnmap.get(p_driver_name)).getCellType().equals(CellType.STRING)) {
					driversName =   row.getCell(columnmap.get(p_driver_name)).getStringCellValue(); 
				} else if (row.getCell(columnmap.get(p_driver_name)).getCellType().equals(CellType.NUMERIC)){
					BigInteger bint = BigDecimal.valueOf(row.getCell(columnmap.get(p_driver_name)).getNumericCellValue()).toBigInteger() ;
					driversName =   bint.toString();
				}
				String name = null;
				String surname = null;
				if (driversName != null) {
					if (driversName.trim().contains(" ")) {
						surname = driversName.trim().substring(driversName.trim().lastIndexOf(' ') + 1);
						name = driversName.trim().substring(0,driversName.trim().lastIndexOf(' '));
					} else {
						surname = driversName.trim();
					}
				}

				if ((horse == null || horse.trim().equals(""))
						&& (trailer_1 == null || trailer_1.trim().equals(""))
						&& (trailer_2 == null || trailer_2.trim().equals(""))
						&& (driver_IDNo == null|| driver_IDNo.trim().equals(""))) {
					continue;
				}
				String fleetNO = null;
				if (row.getCell(columnmap.get(p_fleet)).getCellType().equals(CellType.STRING)) {
					fleetNO =   row.getCell(columnmap.get(p_fleet)).getStringCellValue(); 
				} else if (row.getCell(columnmap.get(p_fleet)).getCellType().equals(CellType.NUMERIC)){
					BigInteger bint = BigDecimal.valueOf(row.getCell(columnmap.get(p_fleet)).getNumericCellValue()).toBigInteger() ;
					fleetNO =   bint.toString();
				}

				MTruck mTruck_horse = MTruck.getTruck(getCtx(), horse,get_TrxName());
				if (mTruck_horse == null) {
					mTruck_horse = MTruck.createTruck(getCtx(), horse, "H",get_TrxName());
				}
				if (mTruck_horse != null && (mTruck_horse.getZZ_Fleet_No() == null || mTruck_horse.getZZ_Fleet_No().equals(fleetNO))) {
					mTruck_horse.setZZ_Fleet_No(fleetNO);
					mTruck_horse.saveEx();
				}

				MTruckList mTruckList = null;
				if (mTruck_horse != null && mTruck_horse.getZZ_Truck_ID() > 0) {
					mTruckList = MTruckList.getTruckList(getCtx(), zz_Transporters_ID,mTruck_horse.getZZ_Truck_ID(), get_TrxName());
				}
				if (mTruckList == null) {
					mTruckList = new MTruckList(getCtx(), 0, get_TrxName());
				}
				MTruck mTruck_trailer1 = MTruck.getTruck(getCtx(), trailer_1,get_TrxName());
				if (mTruck_trailer1 == null) {
					mTruck_trailer1 = MTruck.createTruck(getCtx(), trailer_1, "T",get_TrxName());
				}
				MTruck mTruck_trailer2 = MTruck.getTruck(getCtx(), trailer_2,get_TrxName());
				if (mTruck_trailer2 == null) {
					mTruck_trailer2 = MTruck.createTruck(getCtx(), trailer_2, "T",get_TrxName());
				}
				MDriver mDriver = MDriver.getDriver(getCtx(), driver_IDNo,get_TrxName());
				if (mDriver == null) {
					mDriver = MDriver.createDriver(getCtx(), driver_IDNo, name, surname, null, get_TrxName());
				} 
				mTruckList.setZZ_Transporters_ID(zz_Transporters_ID);
				if (mTruck_horse != null) {
					mTruckList.setZZ_Horse_ID(mTruck_horse.getZZ_Truck_ID());
				}
				if (mTruck_trailer1 != null) {
					mTruckList.setZZ_Trailer1_ID(mTruck_trailer1.getZZ_Truck_ID());
				}
				if (mTruck_trailer2 != null) {
					mTruckList.setZZ_Trailer2_ID(mTruck_trailer2.getZZ_Truck_ID());
				}
				if (mDriver != null) {
					mTruckList.setZZ_Driver_ID(mDriver.getZZ_Driver_ID());
				}
				if (fleetNO != null) {
					mTruckList.setZZ_Fleet_No(fleetNO);
				}
				mTruckList.setZZ_No_Of_Loads((no_Of_Loads == null) ? null :BigDecimal.valueOf(no_Of_Loads));
				mTruckList.setZZ_Import_Errors(errorMsgs.get(row.getRowNum()));
				String errMessage = mTruckList.doChecks(true);
				if (errMessage != null) {
					writeErrorToXLS(errorSheet,rowNoToWrite,row.getRowNum(), errMessage,row);
					rowNoToWrite++;
				} else {
					if (mTruckList.save()) {
						counter++;
					} else {
						msg = "Could not save trucklist : Driver = " + driver_IDNo;
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println();
				throw e;
			}
		}
		if (msg == null) {
			msg = "Count of Records imported: " + counter;
		}
		return msg;
	}


	private int setColumnName(Sheet sheet) {
		columnmap =  new HashMap<String,Integer>();
		boolean headerNotFound = false;
		int i = 0;
		Row row = null;
		while (!headerNotFound) {
			row = sheet.getRow(i); 
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) 
			{
				Cell cell = cellIterator.next();
				String columnname = null;
				//Check the cell type and format accordingly
				switch (cell.getCellType().toString()) 
				{
				case "STRING":
					columnname = cell.getStringCellValue().replaceAll("\\W", "").toUpperCase();
					if (columnname.contains(p_loads)) {
						columnname = p_loads;
					} else if (columnname.contains(p_driver) && columnname.contains("ID")) {
						columnname = p_driver;
					} else if (columnname.contains(p_horse)) {
						columnname = p_horse;
					} else if (columnname.contains(p_trailer1)) {
						columnname = p_trailer1;
					} else if (columnname.contains(p_trailer2)) {
						columnname = p_trailer2;
					} else if (columnname.contains("DRIVER") && columnname.contains("NAME")) {
						columnname = p_driver_name;
					} else if (columnname.contains("FLEET") && columnname.contains("NO")) {
						columnname = p_fleet;
					}
					break;
				case "NUMERIC":
					columnname = String.valueOf(cell.getNumericCellValue()) ;
					break;                    			

				default:
					columnname=  "Unknown";
					System.out.println("Unknown Cell type:" +  cell.getCellType());	
					break;
				}
				if (!columnname.equals("Unknown")) {
					Object[] fields =  new Object[] {p_horse,p_trailer1,p_trailer2,p_driver,p_driver_name,p_loads,p_fleet};
					for (Object field : fields) {
						if (field.toString().equals(columnname)) {
							columnmap.put(columnname,cell.getColumnIndex()) ;
						}
					}
				}
			}
			if (columnmap.size() >= 3) {
				headerNotFound = true;
			}
			i++;
		}


		//Object[] fields =  new Object[] {p_horse,p_trailer1,p_trailer2,p_driver,p_driver_name,p_loads};


		/*
		 * for (Object field : fields) { try { if (columnmap.get(field.toString()) ==
		 * null) { throw new AdempiereUserError("Excel " + field.toString()
		 * +"Column not found" ); } } catch (Exception e) { throw new
		 * AdempiereUserError("Excel " + field.toString() +" Column not found" ); }
		 * 
		 * 
		 * }
		 */
		return i;


	}

	private Sheet createErrorXLS() throws Exception {

		errorWb= new XSSFWorkbook();  // or new XSSFWorkbook();
		Sheet sheet1 = errorWb.createSheet("Import Errors");
		return sheet1;
	}

	private void writeErrorToXLS(Sheet sheet,int rowNoToWrite,int rowNoOriginalFile, String errorMsg, Row fromRow) throws Exception {
		CreationHelper createHelper = errorWb.getCreationHelper();
		Row row = sheet.createRow(rowNoToWrite);
		int col = 0;
		if (fromRow != null) {
			for (Cell cell : fromRow) {
				CellUtil.copyCell(cell, row.createCell(col), new CellCopyPolicy(), new CellCopyContext());
				col++;
			}
		}
		if (errorMsg != null && !errorMsg.trim().equals("")) {
			row.createCell(col).setCellValue(createHelper.createRichTextString(errorMsg));
			col++;
			String oldMsg = errorMsgs.get(rowNoOriginalFile);
			String newMsg = "";
			if (oldMsg != null && !oldMsg.trim().equals("") ) {
				newMsg = oldMsg + "\n" + errorMsg;
			} else {
				newMsg = errorMsg;
			}
			errorMsgs.put(rowNoOriginalFile, newMsg);
		}
		if (col > maxCols) {
			maxCols = col - 1;
		}

	}




	// Was removed because the report was confusing the user.   Can be used later.   It brings the excel sheet in another tab.
	private String writeOutErrorLogFile(Sheet sheet) {
		for (int i = 0; i <= maxCols; i++) {
			sheet.autoSizeColumn(i); 
		}
		String logFileName = importFile.getAbsolutePath() + "_Error_Log.xlsx";

		try (OutputStream fileOut = new FileOutputStream(logFileName)) {
			errorWb.write(fileOut);
			File[] file = { new File(logFileName)};
			ZkReportViewerProviderRGN zkReportViewerProviderRGN = new ZkReportViewerProviderRGN();
			zkReportViewerProviderRGN.setFile(file[0]);
			zkReportViewerProviderRGN.openViewer(null);

		}  catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return logFileName;
	}


	public static void main(String[] args) {
		String driversName = "Test ";
		String surname = driversName.substring(driversName.lastIndexOf(' ') + 1);
		String name = driversName.substring(0,driversName.lastIndexOf(' '));
		System.out.println(surname);
		System.out.println(name);
	}


}
