package za.ntier.process;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

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
	private String p_loads = "LOADS";


	private int counter = 0;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("FileName"))
				p_FileName = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		} 
	}


	@Override
	protected String doIt() throws Exception {
		File importFile = new File(p_FileName);
		updateByExcel(importFile);
		return "Count of Records imported: " + counter ;
	}

	private String updateByExcel(File importFile) throws Exception {
		XSSFWorkbook workbook  = null;
		int zz_Transporters_ID = getRecord_ID();
		FileInputStream file = null;
		String msg = null;
		try {
			file = new FileInputStream(importFile);
			workbook = new XSSFWorkbook(file);
			msg = loadTruckList(workbook, zz_Transporters_ID, msg);
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		finally {
			if (file != null)
				file.close();
			if (workbook != null)
				workbook.close();
		}

		return msg;
	}

	private String checkTruckList(XSSFWorkbook workbook, int zz_Transporters_ID, String msg) throws Exception {
		XSSFSheet sheet = workbook.getSheetAt(0);
		int data_start_row = setColumnName(sheet);
		Iterator<Row> rowIterator = sheet.iterator();
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
				horse =   row.getCell(columnmap.get(p_horse)).getStringCellValue();
				trailer_1 =   row.getCell(columnmap.get(p_trailer1)).getStringCellValue();
				trailer_2 =   row.getCell(columnmap.get(p_trailer2)).getStringCellValue();
				driver_IDNo =   row.getCell(columnmap.get(p_driver)).getStringCellValue();                    
				no_Of_Loads =   row.getCell(columnmap.get(p_loads)).getNumericCellValue();
				MTruckList mTruckList = new MTruckList(getCtx(), 0, get_TrxName());
				MTruck mTruck_horse = MTruck.getTruck(getCtx(), horse);
				
				MTruck mTruck_trailer1 = MTruck.getTruck(getCtx(), trailer_1);
				MTruck mTruck_trailer2 = MTruck.getTruck(getCtx(), trailer_2);
				MDriver mDriver = MDriver.getDriver(getCtx(), driver_IDNo);
				mTruckList.setZZ_Transporters_ID(zz_Transporters_ID);
				mTruckList.setZZ_Horse_ID(mTruck_horse.getZZ_Truck_ID());
				mTruckList.setZZ_Trailer1_ID(mTruck_trailer1.getZZ_Truck_ID());
				mTruckList.setZZ_Trailer2_ID(mTruck_trailer2.getZZ_Truck_ID());
				mTruckList.setZZ_Driver_ID(mDriver.getZZ_Driver_ID());
				mTruckList.setZZ_No_Of_Loads(BigDecimal.valueOf(no_Of_Loads));
				if (mTruckList.save()) {
					counter++;
				} else {
					msg = "Could not save trucklist : Driver = " + driver_IDNo;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println();
				throw e;
			}
		}
		return msg;
	}

	private String loadTruckList(XSSFWorkbook workbook, int zz_Transporters_ID, String msg) throws Exception {
		XSSFSheet sheet = workbook.getSheetAt(0);
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
				horse =   row.getCell(columnmap.get(p_horse)).getStringCellValue();
				trailer_1 =   row.getCell(columnmap.get(p_trailer1)).getStringCellValue();
				trailer_2 =   row.getCell(columnmap.get(p_trailer2)).getStringCellValue();
				driver_IDNo =   row.getCell(columnmap.get(p_driver)).getStringCellValue();                    
				no_Of_Loads =   row.getCell(columnmap.get(p_loads)).getNumericCellValue();
				MTruckList mTruckList = new MTruckList(getCtx(), 0, get_TrxName());
				MTruck mTruck_horse = MTruck.getTruck(getCtx(), horse);
				MTruck mTruck_trailer1 = MTruck.getTruck(getCtx(), trailer_1);
				MTruck mTruck_trailer2 = MTruck.getTruck(getCtx(), trailer_2);
				MDriver mDriver = MDriver.getDriver(getCtx(), driver_IDNo);
				mTruckList.setZZ_Transporters_ID(zz_Transporters_ID);
				mTruckList.setZZ_Horse_ID(mTruck_horse.getZZ_Truck_ID());
				mTruckList.setZZ_Trailer1_ID(mTruck_trailer1.getZZ_Truck_ID());
				mTruckList.setZZ_Trailer2_ID(mTruck_trailer2.getZZ_Truck_ID());
				mTruckList.setZZ_Driver_ID(mDriver.getZZ_Driver_ID());
				mTruckList.setZZ_No_Of_Loads(BigDecimal.valueOf(no_Of_Loads));
				if (mTruckList.save()) {
					counter++;
				} else {
					msg = "Could not save trucklist : Driver = " + driver_IDNo;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println();
				throw e;
			}
		}
		return msg;
	}


	private int setColumnName(XSSFSheet sheet) {
		// TODO Auto-generated method stub
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
					Object[] fields =  new Object[] {p_horse,p_trailer1,p_trailer2,p_driver,p_loads};
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


		Object[] fields =  new Object[] {p_horse,p_trailer1,p_trailer2,p_driver,p_loads};


		for (Object field : fields) {
			try {
				if (columnmap.get(field.toString()) == null) {
					throw new AdempiereUserError("Excel " + field.toString() +"Column not found" );
				}
			} catch (Exception e) {
				throw new AdempiereUserError("Excel " + field.toString() +" Column not found" );
			}
			/*try {

				if (row.getCell(columnmap.get(field.toString().trim().strip())) == null)
					throw new AdempiereUserError("Excel " + field.toString() +"Column not found" );
			} catch (Exception e) {
				throw new AdempiereUserError("Excel " + field.toString() +" Column not found" );
			}*/

		}	 
		return i;


	}
	
	private Sheet createErrorXLS() throws Exception {
		
		Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet1 = wb.createSheet("Import Errors");
		Row row = sheet1.createRow(0);
		row.createCell(1).setCellValue(createHelper.createRichTextString("ROW"));
		row.createCell(2).setCellValue(createHelper.createRichTextString("Error Message"));

		return shee1;
	}
	
	private void writeErrorToXLS(Workbook wb ,Sheet sheet,int rowNoToWrite,int rowNoOriginalFile, String errorMsg) throws Exception {
		CreationHelper createHelper = wb.getCreationHelper();
		Row row = sheet.createRow(rowNoToWrite);
		row.createCell(1).setCellValue((rowNoOriginalFile));
		row.createCell(2).setCellValue(createHelper.createRichTextString(errorMsg));

	}


	private void writeOutErrorLogFile(Workbook wb) throws Exception {
		String tmpdir = System.getProperty("java.io.tmpdir");
		try (OutputStream fileOut = new FileOutputStream(tmpdir + "Truck_List_Import_Error_List.xls")) {
		    wb.write(fileOut);
		}
	}





}
