package za.ntier.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_AD_User;
import org.compiere.model.MTable;
import org.compiere.model.PO;

import za.co.ntier.api.model.X_ZZ_Program_Master_Data;
import za.co.ntier.fa.process.api.IDocApprove;

public class MZZProgramMasterData extends X_ZZ_Program_Master_Data implements IDocApprove{

	private static final long serialVersionUID = 1L;

	public MZZProgramMasterData(Properties ctx, int ZZ_Program_Master_Data_ID, String trxName) {
		super(ctx, ZZ_Program_Master_Data_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramMasterData(Properties ctx, int ZZ_Program_Master_Data_ID, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Program_Master_Data_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramMasterData(Properties ctx, String ZZ_Program_Master_Data_UU, String trxName) {
		super(ctx, ZZ_Program_Master_Data_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramMasterData(Properties ctx, String ZZ_Program_Master_Data_UU, String trxName,
			String... virtualColumns) {
		super(ctx, ZZ_Program_Master_Data_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MZZProgramMasterData(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (newRecord) {
			int ids[] = PO.getAllIDs(X_ZZ_Gen_Rules.Table_Name, null, get_TrxName());
			for (int id:ids) {
				X_ZZ_Gen_Rules X_ZZ_Gen_Rules = new X_ZZ_Gen_Rules(getCtx(),id,get_TrxName());
				MZZProgramGenRules mZZProgramGenRules = new MZZProgramGenRules(getCtx(), 0, get_TrxName());
				mZZProgramGenRules.setZZ_Program_Master_Data_ID(getZZ_Program_Master_Data_ID());
				mZZProgramGenRules.setLine(X_ZZ_Gen_Rules.getLine());
				mZZProgramGenRules.setName(X_ZZ_Gen_Rules.getName());
				mZZProgramGenRules.saveEx();
			}
		}
		return super.afterSave(newRecord, success);
	}


	/** Set Project.
	@param C_Project_ID Financial Project
	 */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1)
			set_Value (COLUMNNAME_C_Project_ID, null);
		else
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
	@return Financial Project
	 */
	public int getC_Project_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException
	{
		return (org.compiere.model.I_C_Year)MTable.get(getCtx(), org.compiere.model.I_C_Year.Table_ID)
				.getPO(getC_Year_ID(), get_TrxName());
	}

	/** Set Year.
	@param C_Year_ID Calendar Year
	 */
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1)
			set_Value (COLUMNNAME_C_Year_ID, null);
		else
			set_Value (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Year.
	@return Calendar Year
	 */
	public int getC_Year_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set Description.
	@param Description Optional short description of the record
	 */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
	@return Optional short description of the record
	 */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Document No.
	@param DocumentNo Document sequence number of the document
	 */
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
	@return Document sequence number of the document
	 */
	public String getDocumentNo()
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	public org.compiere.model.I_AD_User getLine_Manager() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
				.getPO(getLine_Manager_ID(), get_TrxName());
	}

	/** Set Snr Mgr.
	@param Line_Manager_ID Snr Mgr
	 */
	public void setLine_Manager_ID (int Line_Manager_ID)
	{
		if (Line_Manager_ID < 1)
			set_Value (COLUMNNAME_Line_Manager_ID, null);
		else
			set_Value (COLUMNNAME_Line_Manager_ID, Integer.valueOf(Line_Manager_ID));
	}

	/** Get Snr Mgr.
	@return Snr Mgr	  */
	public int getLine_Manager_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line_Manager_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set Name.
	@param Name Alphanumeric identifier of the entity
	 */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
	@return Alphanumeric identifier of the entity
	 */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Title.
	@param Title Name this entity is referred to as
	 */
	public void setTitle (String Title)
	{
		set_Value (COLUMNNAME_Title, Title);
	}

	/** Get Title.
	@return Name this entity is referred to as
	 */
	public String getTitle()
	{
		return (String)get_Value(COLUMNNAME_Title);
	}

	
	/** Set Criteria.
	@param ZZ_Criteria Criteria
	 */
	public void setZZ_Criteria (String ZZ_Criteria)
	{

		set_Value (COLUMNNAME_ZZ_Criteria, ZZ_Criteria);
	}

	/** Get Criteria.
	@return Criteria	  */
	public String getZZ_Criteria()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Criteria);
	}

	/** Set Date Approved.
	@param ZZ_Date_Approved Date Approved
	 */
	public void setZZ_Date_Approved (Timestamp ZZ_Date_Approved)
	{
		set_Value (COLUMNNAME_ZZ_Date_Approved, ZZ_Date_Approved);
	}

	/** Get Date Approved.
	@return Date Approved	  */
	public Timestamp getZZ_Date_Approved()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Approved);
	}

	/** Set Date Submitted.
	@param ZZ_Date_Submitted Date Submitted
	 */
	public void setZZ_Date_Submitted (Timestamp ZZ_Date_Submitted)
	{
		set_Value (COLUMNNAME_ZZ_Date_Submitted, ZZ_Date_Submitted);
	}

	/** Get Date Submitted.
	@return Date Submitted	  */
	public Timestamp getZZ_Date_Submitted()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Submitted);
	}

	
	/** Set Document Action.
	@param ZZ_DocAction Document Action
	 */
	public void setZZ_DocAction (String ZZ_DocAction)
	{

		set_Value (COLUMNNAME_ZZ_DocAction, ZZ_DocAction);
	}

	/** Get Document Action.
	@return Document Action	  */
	public String getZZ_DocAction()
	{
		return (String)get_Value(COLUMNNAME_ZZ_DocAction);
	}

	
	 
	public void setZZ_DocStatus (String ZZ_DocStatus)
	{

		set_Value (COLUMNNAME_ZZ_DocStatus, ZZ_DocStatus);
	}

	/** Get Document Status.
	@return Document Status	  */
	public String getZZ_DocStatus()
	{
		return (String)get_Value(COLUMNNAME_ZZ_DocStatus);
	}

	/** Set Program Master Data.
	@param ZZ_Program_Master_Data_ID Program Master Data
	 */
	public void setZZ_Program_Master_Data_ID (int ZZ_Program_Master_Data_ID)
	{
		if (ZZ_Program_Master_Data_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Program_Master_Data_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Program_Master_Data_ID, Integer.valueOf(ZZ_Program_Master_Data_ID));
	}

	/** Get Program Master Data.
	@return Program Master Data	  */
	public int getZZ_Program_Master_Data_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Program_Master_Data_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set ZZ_Program_Master_Data_UU.
	@param ZZ_Program_Master_Data_UU ZZ_Program_Master_Data_UU
	 */
	public void setZZ_Program_Master_Data_UU (String ZZ_Program_Master_Data_UU)
	{
		set_Value (COLUMNNAME_ZZ_Program_Master_Data_UU, ZZ_Program_Master_Data_UU);
	}

	/** Get ZZ_Program_Master_Data_UU.
	@return ZZ_Program_Master_Data_UU	  */
	public String getZZ_Program_Master_Data_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Program_Master_Data_UU);
	}

	public org.compiere.model.I_AD_User getZZ_Snr_Mgr_LP() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
				.getPO(getZZ_Snr_Mgr_LP_ID(), get_TrxName());
	}

	/** Set Snr Mgr LP.
	@param ZZ_Snr_Mgr_LP_ID Snr Mgr LP
	 */
	public void setZZ_Snr_Mgr_LP_ID (int ZZ_Snr_Mgr_LP_ID)
	{
		if (ZZ_Snr_Mgr_LP_ID < 1)
			set_Value (COLUMNNAME_ZZ_Snr_Mgr_LP_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Snr_Mgr_LP_ID, Integer.valueOf(ZZ_Snr_Mgr_LP_ID));
	}

	/** Get Snr Mgr LP.
	@return Snr Mgr LP	  */
	public int getZZ_Snr_Mgr_LP_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Snr_Mgr_LP_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public void setZZ_Date_LM_Approved(Timestamp approveDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZZ_Date_Not_Approved_by_LM(Timestamp notApproveDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_Snr_Admin_Fin_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setZZ_Snr_Admin_Fin_ID(int ZZ_Snr_Admin_Fin_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZZ_Date_Not_Approved_by_Snr_Adm_Fin(Timestamp ZZ_Date_Not_Approved_by_Snr_Adm_Fin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZZ_Date_Completed(Timestamp completeDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZZ_Date_MFC_Approved(Timestamp ZZ_Date_MFC_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_MFC_Not_Approved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isZZ_AllowMgrFinConsumablesApproval() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setZZ_Date_MFC_Not_Approved(Timestamp ZZ_Date_MFC_Not_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZZ_Date_SDL_Approved(Timestamp ZZ_Date_SDL_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_SDL_Approved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_SDL_Not_Approved(Timestamp ZZ_Date_SDL_Not_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_SDL_Not_Approved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_Account_Created(Timestamp ZZ_Date_Account_Created) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Account_Created() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_IT_Manager_Approved(Timestamp ZZ_Date_IT_Manager_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_IT_Manager_Approved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_IT_Manager_Rejected(Timestamp ZZ_Date_IT_Manager_Rejected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_IT_Manager_Rejected() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_Manager_Approved(Timestamp ZZ_Date_Manager_Approved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Manager_Approved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_Manager_Rejected(Timestamp ZZ_Date_Manager_Rejected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Manager_Rejected() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_IT_Admin_ID(int ZZ_IT_Admin_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_IT_Admin_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_AD_User getZZ_IT_Admin() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_IT_Manager_ID(int ZZ_IT_Manager_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_IT_Manager_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_AD_User getZZ_IT_Manager() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Manager_ID(int ZZ_Manager_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_Manager_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setZZ_Mgr_Fin_Consumables_ID(int ZZ_Mgr_Fin_Consumables_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_Mgr_Fin_Consumables_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_AD_User getZZ_Mgr_Fin_Consumables() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_SDL_Fin_Mgr_ID(int ZZ_SDL_Fin_Mgr_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_SDL_Fin_Mgr_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_AD_User getZZ_SDL_Fin_Mgr() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Recommender_ID(int ZZ_Recommender_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_Recommender_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setZZ_Exec_Approver_ID(int ZZ_Exec_Approver_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getZZ_Exec_Approver_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setStartDate(Timestamp StartDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getStartDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEndDate(Timestamp EndDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getEndDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZZ_Date_Recommended(Timestamp ZZ_Date_Recommended) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Recommended() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setZZ_Date_Not_Approved (Timestamp ZZ_Date_Not_Approved)
	{
		set_Value (COLUMNNAME_ZZ_Date_Not_Approved, ZZ_Date_Not_Approved);
	}

	/** Get Date Not Approved.
		@return Date Not Approved	  */
	public Timestamp getZZ_Date_Not_Approved()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Not_Approved);
	}
	
	@Override
	public void setZZ_Date_Not_Recommended(Timestamp ZZ_Date_Not_Recommended) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getZZ_Date_Not_Recommended() {
		// TODO Auto-generated method stub
		return null;
	}
}
