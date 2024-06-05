package za.ntier.process;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

import za.ntier.models.MDriver;
import za.ntier.models.MTruck;
import za.ntier.models.MTruckList;



public class ImportTruckListViaExcel extends SvrProcess {



	Map<String, Integer> columnmap = new HashMap<String,Integer>(); 
	String p_FileName = "";
	private String p_horse = "Horse";
	private String p_trailer1 = "Trailer1";
	private String p_trailer2 = "Trailer2";
	private String p_driver = "Driver";
	private String p_loads = "Loads";


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

	private String updateByExcel(File importFile) {
		int zz_Transporters_ID = getRecord_ID();
		FileInputStream file;
		String msg = null;
		try {
			file = new FileInputStream(importFile);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			setColumnName(sheet);
			Iterator<Row> rowIterator = sheet.iterator();
			// pass first row
			rowIterator.next();
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				String horse = null;
				String trailer_1 = null;
				String trailer_2 = null;
				String driver_IDNo = null;
				Double no_Of_Loads = null;
				try {
					horse =   row.getCell(columnmap.get(0)).getStringCellValue();
					trailer_1 =   row.getCell(columnmap.get(1)).getStringCellValue();
					trailer_2 =   row.getCell(columnmap.get(2)).getStringCellValue();
					driver_IDNo =   row.getCell(columnmap.get(4)).getStringCellValue();                    
					no_Of_Loads =   row.getCell(columnmap.get(5)).getNumericCellValue();
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

				}



			}
			file.close();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return msg;
	}


	private void setColumnName(XSSFSheet sheet) {
		// TODO Auto-generated method stub
		Row row = sheet.getRow(0); //Get first row
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) 
		{
			Cell cell = cellIterator.next();
			String columnname = null;
			//Check the cell type and format accordingly
			switch (cell.getCellType().toString()) 
			{
			case "STRING":
				columnname = cell.getStringCellValue();
				break;
			case "NUMERIC":
				columnname = String.valueOf(cell.getNumericCellValue()) ;
				break;                    			

			default:
				columnname=  "Unknown";
				System.out.println("Unknown Cell type:" +  cell.getCellType());	
				break;
			}
			columnmap.put(columnname,cell.getColumnIndex()) ;
		}

		Object[] fields =  new Object[] {p_horse,p_trailer1,p_trailer2,p_driver,p_loads};


		for (Object field : fields) {

			try {

				if(row.getCell(columnmap.get(field.toString())) == null)
					throw new AdempiereUserError("Excel " + field.toString() +"Column not found" );
			} catch (Exception e) {
				throw new AdempiereUserError("Excel " + field.toString() +" Column not found" );
			}

		}	 


	}







}
