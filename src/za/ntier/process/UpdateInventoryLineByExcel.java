package za.ntier.process;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

import za.ntier.models.MTruckList;



public class UpdateInventoryLineByExcel extends SvrProcess {



	Map<String, Integer> columnmap = new HashMap<String,Integer>(); 
	String p_FileName = "";


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

	/*
	 * AD_Language = getCtx().getProperty("#AD_Language"); inventory = new
	 * MInventoryTG(getCtx(), getRecord_ID(), get_TrxName());
	 * if(!inventory.getDocStatus().equals(DocAction.STATUS_Drafted) &&
	 * !inventory.getDocStatus().equals(DocAction.STATUS_InProgress)) throw new
	 * AdempiereUserError("草稿狀態才能更新盤點數量");
	 * 
	 * if(inventory.getAttachment(true) == null) throw new
	 * AdempiereUserError("未上傳Excel File"); }
	 */

	@Override
	protected String doIt() throws Exception {
		File importFile = new File(p_FileName);
		updateByExcel(importFile);
		return "Count of Records imported: " + counter ;
	}

	private void updateByExcel(File importFile) {
		int zz_Transporters_ID = getRecord_ID();
		FileInputStream file;
		//System.out.println(getCtx());
		//System.out.println("AD_Language:" + AD_Language);
		try {
			file = new FileInputStream(importFile);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
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

					counter++;
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


	}






	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
