package org.adempiere.impexp;

public class ExportExcel extends AbstractExcelExporter {

	public ExportExcel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isFunctionRow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void setCurrentRow(int row) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int getCurrentRow() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isColumnPrinted(int col) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getHeaderName(int col) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDisplayType(int row, int col) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPageBreak(int row, int col) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDisplayed(int row, int col) {
		// TODO Auto-generated method stub
		return false;
	}

}
