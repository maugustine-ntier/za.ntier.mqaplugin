package za.ntier.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_AD_User;
import org.compiere.model.MTable;
import org.compiere.model.MUser;
import org.compiere.util.Msg;

import za.co.ntier.fa.process.api.IDocApprove;

public class MZZOpenApplication extends X_ZZ_Open_Application implements IDocApprove{

	private static final long serialVersionUID = 1L;

	public MZZOpenApplication(Properties ctx, int ZZ_Open_Application_ID, String trxName) {
		super(ctx, ZZ_Open_Application_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	
	public MZZOpenApplication(Properties ctx, int ZZ_Open_Application_ID, String trxName, String... virtualColumns) {
		super(ctx, ZZ_Open_Application_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}


	public MZZOpenApplication(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}


	public MZZOpenApplication(Properties ctx, String ZZ_Open_Application_UU, String trxName, String... virtualColumns) {
		super(ctx, ZZ_Open_Application_UU, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}


	public MZZOpenApplication(Properties ctx, String ZZ_Open_Application_UU, String trxName) {
		super(ctx, ZZ_Open_Application_UU, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {

	    if (getC_Year_ID() <= 0)
	        return super.beforeSave(newRecord);

	    if (getStartDate() == null || getEndDate() == null)
	        return super.beforeSave(newRecord);

	    if (getStartDate().after(getEndDate())) {
	        log.saveError("Error", Msg.getMsg(getCtx(), "STARTDATEBEFORENDDATE")); 
			return false;
	    }

	    String programs = getZZ_Programs();
	    if (programs == null || programs.trim().isEmpty())
	        return super.beforeSave(newRecord);

	    String[] ids = programs.split("\\s*,\\s*");

	    String sql =
	        "SELECT COUNT(1) " +
	        "FROM ZZ_Open_Application oa " +
	        "WHERE oa.IsActive='Y' " +
	        "  AND oa.C_Year_ID=? " +
	        "  AND oa.ZZ_Open_Application_ID<>? " +
	        "  AND (',' || COALESCE(oa.ZZ_Programs,'') || ',') LIKE ? " +
	        "  AND oa.StartDate <= ? " +   // other.start <= this.end
	        "  AND oa.EndDate >= ?";       // other.end   >= this.start

	    for (String idStr : ids) {
	        if (idStr == null || idStr.trim().isEmpty())
	            continue;

	        int progId;
	        try {
	            progId = Integer.parseInt(idStr.trim());
	        } catch (NumberFormatException nfe) {
	            Object[] args = new Object[] {idStr};
	            log.saveError("Error", Msg.getMsg(getCtx(), "INVALIDPROGRAMID",args)); 
				return false;
	        }

	        String like = "%," + progId + ",%";

	        int cnt = org.compiere.util.DB.getSQLValueEx(
	            get_TrxName(),
	            sql,
	            getC_Year_ID(),
	            getZZ_Open_Application_ID(),
	            like,
	            getEndDate(),
	            getStartDate()
	        );

	        if (cnt > 0) {
	            Object[] args = new Object[] {progId};
	            log.saveError("Error", Msg.getMsg(getCtx(), "OVERLAPPINGOPENAPP")); 
				return false;
	        }
	    }

	    return super.beforeSave(newRecord);
	}



	/** Set Start Date.
	@param StartDate First effective day (inclusive)
	 */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
	@return First effective day (inclusive)
	 */
	public Timestamp getStartDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
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

	/** Set Date Not Approved.
	@param ZZ_Date_Not_Approved Date Not Approved
	 */
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

	/** Set Date Not Recommended.
	@param ZZ_Date_Not_Recommended Date Not Recommended
	 */
	public void setZZ_Date_Not_Recommended (Timestamp ZZ_Date_Not_Recommended)
	{
		set_Value (COLUMNNAME_ZZ_Date_Not_Recommended, ZZ_Date_Not_Recommended);
	}

	/** Get Date Not Recommended.
	@return Date Not Recommended	  */
	public Timestamp getZZ_Date_Not_Recommended()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Not_Recommended);
	}

	/** Set Date Recommended.
	@param ZZ_Date_Recommended Date Recommended
	 */
	public void setZZ_Date_Recommended (Timestamp ZZ_Date_Recommended)
	{
		set_Value (COLUMNNAME_ZZ_Date_Recommended, ZZ_Date_Recommended);
	}

	/** Get Date Recommended.
	@return Date Recommended	  */
	public Timestamp getZZ_Date_Recommended()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Recommended);
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

	
	/** Set Document Status.
	@param ZZ_DocStatus Document Status
	 */
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

	public org.compiere.model.I_AD_User getZZ_Exec_Approver() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
				.getPO(getZZ_Exec_Approver_ID(), get_TrxName());
	}

	/** Set Exec Approver.
	@param ZZ_Exec_Approver_ID Exec Approver
	 */
	public void setZZ_Exec_Approver_ID (int ZZ_Exec_Approver_ID)
	{
		if (ZZ_Exec_Approver_ID < 1)
			set_Value (COLUMNNAME_ZZ_Exec_Approver_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Exec_Approver_ID, Integer.valueOf(ZZ_Exec_Approver_ID));
	}

	/** Get Exec Approver.
	@return Exec Approver	  */
	public int getZZ_Exec_Approver_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Exec_Approver_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set Open Application.
	@param ZZ_Open_Application_ID Open Application
	 */
	public void setZZ_Open_Application_ID (int ZZ_Open_Application_ID)
	{
		if (ZZ_Open_Application_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Open_Application_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Open_Application_ID, Integer.valueOf(ZZ_Open_Application_ID));
	}

	/** Get Open Application.
	@return Open Application	  */
	public int getZZ_Open_Application_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Open_Application_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set ZZ_Open_Application_UU.
	@param ZZ_Open_Application_UU ZZ_Open_Application_UU
	 */
	public void setZZ_Open_Application_UU (String ZZ_Open_Application_UU)
	{
		set_Value (COLUMNNAME_ZZ_Open_Application_UU, ZZ_Open_Application_UU);
	}

	/** Get ZZ_Open_Application_UU.
	@return ZZ_Open_Application_UU	  */
	public String getZZ_Open_Application_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Open_Application_UU);
	}

	/** Set Programs.
	@param ZZ_Programs Programs
	 */
	public void setZZ_Programs (String ZZ_Programs)
	{

		set_ValueNoCheck (COLUMNNAME_ZZ_Programs, ZZ_Programs);
	}

	/** Get Programs.
	@return Programs	  */
	public String getZZ_Programs()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Programs);
	}

	public org.compiere.model.I_AD_User getZZ_Recommender() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
				.getPO(getZZ_Recommender_ID(), get_TrxName());
	}

	/** Set Recommender.
	@param ZZ_Recommender_ID Recommender
	 */
	public void setZZ_Recommender_ID (int ZZ_Recommender_ID)
	{
		if (ZZ_Recommender_ID < 1)
			set_Value (COLUMNNAME_ZZ_Recommender_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Recommender_ID, Integer.valueOf(ZZ_Recommender_ID));
	}

	/** Get Recommender.
	@return Recommender	  */
	public int getZZ_Recommender_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Recommender_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public int getLine_Manager_ID() {
		// TODO Auto-generated method stub
		return 0;
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
	public void setZZ_Snr_Mgr_LP_ID(int ZZ_Snr_Mgr_LP_ID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getZZ_Snr_Mgr_LP_ID() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setZZ_Submitter_ID(int ZZ_Submitter_ID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getZZ_Submitter_ID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
