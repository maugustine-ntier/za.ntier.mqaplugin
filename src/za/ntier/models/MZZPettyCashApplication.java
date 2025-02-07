package za.ntier.models;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.webui.window.Dialog;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

public class MZZPettyCashApplication extends X_ZZ_Petty_Cash_Application implements DocAction,DocOptions {

	

	private static final long serialVersionUID = 1L;

	
	

	public MZZPettyCashApplication(Properties ctx, int ID, String trxName, String... virtualColumns) {
		super(ctx, ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZPettyCashApplication(Properties ctx, int ID, String trxName) {
		super(ctx, ID, trxName);
		// TODO Auto-generated constructor stub
	}

	

	public MZZPettyCashApplication(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	

	public MZZPettyCashApplication(Properties ctx, String UUID, String trxName, String... virtualColumns) {
		super(ctx, UUID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZPettyCashApplication(Properties ctx, String UUID, String trxName) {
		super(ctx, UUID, trxName);
		// TODO Auto-generated constructor stub
	}


	
	@Override
	public boolean processIt(String action) throws Exception {
		log.warning("Processing Action=" + action + " - DocStatus=" + getDocStatus() + " - DocAction=" + getDocAction());
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		return true;
	}

	@Override
	public boolean invalidateIt() {
		return true;
	}

	@Override
	public String prepareIt() {
		setC_DocType_ID(getC_DocTypeTarget_ID());
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		return true;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		setProcessed(true);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		return true;
	}

	@Override
	public boolean closeIt() {
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		return true;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		return true;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		if (getDocStatus().equals(DocumentEngine.STATUS_Drafted)) {
			return getLine_Manager_ID();
		} else {
			return 0;
		}
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return BigDecimal.ONE;
	}


	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx,
			int AD_Table_ID, String[] docAction, String[] options, int index) {
		if (options == null)
			throw new IllegalArgumentException("Option array parameter is null");
		if (docAction == null)
			throw new IllegalArgumentException("Doc action array parameter is null");

		// If a document is drafted or invalid, the users are able to complete, prepare or void
		if (docStatus.equals(DocumentEngine.STATUS_Drafted) || docStatus.equals(DocumentEngine.STATUS_Invalid)) {
			options[index++] = DocumentEngine.ACTION_Complete;
			options[index++] = DocumentEngine.ACTION_Prepare;
			options[index++] = DocumentEngine.ACTION_Void;

			// If the document is already completed, we also want to be able to reactivate or void it instead of only closing it
		} else if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
			options[index++] = DocumentEngine.ACTION_Void;
			options[index++] = DocumentEngine.ACTION_ReActivate;
		}

		return index;
	}

}
