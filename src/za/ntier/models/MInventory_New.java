package za.ntier.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_AD_User;
import org.compiere.model.MInventory;
import org.compiere.model.MTable;
import org.compiere.model.MWarehouse;

import za.co.ntier.fa.process.api.IDocApprove;

public class MInventory_New extends MInventory implements I_M_Inventory,IDocApprove {

	private static final long serialVersionUID = 1L;
	public MInventory_New(Properties ctx, String M_Inventory_UU, String trxName) {
		super(ctx, M_Inventory_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInventory_New(Properties ctx, int M_Inventory_ID, String trxName) {
		super(ctx, M_Inventory_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInventory_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInventory_New(MWarehouse wh, String trxName) {
		super(wh, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInventory_New(MInventory copy) {
		super(copy);
		// TODO Auto-generated constructor stub
	}

	public MInventory_New(Properties ctx, MInventory copy) {
		super(ctx, copy);
		// TODO Auto-generated constructor stub
	}

	public MInventory_New(Properties ctx, MInventory copy, String trxName) {
		super(ctx, copy, trxName);
		// TODO Auto-generated constructor stub
	}

	public org.compiere.model.I_AD_User getLine_Manager() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
				.getPO(getLine_Manager_ID(), get_TrxName());
	}

	/** Set Line Manager.
		@param Line_Manager_ID Line Manager
	 */
	public void setLine_Manager_ID (int Line_Manager_ID)
	{
		if (Line_Manager_ID < 1)
			set_Value (COLUMNNAME_Line_Manager_ID, null);
		else
			set_Value (COLUMNNAME_Line_Manager_ID, Integer.valueOf(Line_Manager_ID));
	}

	/** Get Line Manager.
		@return Line Manager	  */
	public int getLine_Manager_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line_Manager_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set Allow Line Manage Approved.
	@param ZZ_AllowLineManageApproved Choose to allow line manage join to approved workfllow
	 */
	public void setZZ_AllowLineManageApproved (boolean ZZ_AllowLineManageApproved)
	{
		set_ValueNoCheck (COLUMNNAME_ZZ_AllowLineManageApproved, Boolean.valueOf(ZZ_AllowLineManageApproved));
	}

	/** Get Allow Line Manage Approved.
	@return Choose to allow line manage join to approved workfllow
	 */
	public boolean isZZ_AllowLineManageApproved()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_AllowLineManageApproved);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Mgr Finance Consumables Approval.
	@param ZZ_AllowMgrFinConsumablesApproval Allow Mgr Finance Consumables Approval
	 */
	public void setZZ_AllowMgrFinConsumablesApproval (boolean ZZ_AllowMgrFinConsumablesApproval)
	{
		set_ValueNoCheck (COLUMNNAME_ZZ_AllowMgrFinConsumablesApproval, Boolean.valueOf(ZZ_AllowMgrFinConsumablesApproval));
	}

	/** Get Allow Mgr Finance Consumables Approval.
	@return Allow Mgr Finance Consumables Approval	  */
	public boolean isZZ_AllowMgrFinConsumablesApproval()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_AllowMgrFinConsumablesApproval);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow SDL Finance Manager Approval.
	@param ZZ_AllowSdlLineMgrApproved Allow SDL Finance Manager Approval
	 */
	public void setZZ_AllowSdlLineMgrApproved (boolean ZZ_AllowSdlLineMgrApproved)
	{
		set_ValueNoCheck (COLUMNNAME_ZZ_AllowSdlLineMgrApproved, Boolean.valueOf(ZZ_AllowSdlLineMgrApproved));
	}

	/** Get Allow SDL Finance Manager Approval.
	@return Allow SDL Finance Manager Approval	  */
	public boolean isZZ_AllowSdlLineMgrApproved()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_AllowSdlLineMgrApproved);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Snr Admin Finance Approved.
	@param ZZ_AllowSnrAdminFinanceApproved Choose to allow Snr Admin Finance join to approved workfllow
	 */
	public void setZZ_AllowSnrAdminFinanceApproved (boolean ZZ_AllowSnrAdminFinanceApproved)
	{
		set_ValueNoCheck (COLUMNNAME_ZZ_AllowSnrAdminFinanceApproved, Boolean.valueOf(ZZ_AllowSnrAdminFinanceApproved));
	}

	/** Get Allow Snr Admin Finance Approved.
	@return Choose to allow Snr Admin Finance join to approved workfllow
	 */
	public boolean isZZ_AllowSnrAdminFinanceApproved()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_AllowSnrAdminFinanceApproved);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Consumables Document Signed and Uploaded.
	@param ZZ_Consumables_Signed_Uploaded Consumables Document Signed and Uploaded
	 */
	public void setZZ_Consumables_Signed_Uploaded (boolean ZZ_Consumables_Signed_Uploaded)
	{
		set_Value (COLUMNNAME_ZZ_Consumables_Signed_Uploaded, Boolean.valueOf(ZZ_Consumables_Signed_Uploaded));
	}

	/** Get Consumables Document Signed and Uploaded.
	@return Consumables Document Signed and Uploaded	  */
	public boolean isZZ_Consumables_Signed_Uploaded()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Consumables_Signed_Uploaded);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Date Completed.
	@param ZZ_Date_Completed Date Completed
	 */
	public void setZZ_Date_Completed (Timestamp ZZ_Date_Completed)
	{
		set_Value (COLUMNNAME_ZZ_Date_Completed, ZZ_Date_Completed);
	}

	/** Get Date Completed.
	@return Date Completed	  */
	public Timestamp getZZ_Date_Completed()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Completed);
	}

	/** Set Date LM Approved.
	@param ZZ_Date_LM_Approved Date LM Approved
	 */
	public void setZZ_Date_LM_Approved (Timestamp ZZ_Date_LM_Approved)
	{
		set_Value (COLUMNNAME_ZZ_Date_LM_Approved, ZZ_Date_LM_Approved);
	}

	/** Get Date LM Approved.
	@return Date LM Approved	  */
	public Timestamp getZZ_Date_LM_Approved()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_LM_Approved);
	}

	/** Set Date Manager Finance Consumables.
	@param ZZ_Date_MFC_Approved Date Manager Finance Consumables
	 */
	public void setZZ_Date_MFC_Approved (Timestamp ZZ_Date_MFC_Approved)
	{
		set_Value (COLUMNNAME_ZZ_Date_MFC_Approved, ZZ_Date_MFC_Approved);
	}

	/** Get Date Manager Finance Consumables.
	@return Date Manager Finance Consumables	  */
	public Timestamp getZZ_Date_MFC_Approved()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_MFC_Approved);
	}

	/** Set Date Manager Finance Not Approved.
	@param ZZ_Date_MFC_Not_Approved Date Manager Finance Not Approved
	 */
	public void setZZ_Date_MFC_Not_Approved (Timestamp ZZ_Date_MFC_Not_Approved)
	{
		set_Value (COLUMNNAME_ZZ_Date_MFC_Not_Approved, ZZ_Date_MFC_Not_Approved);
	}

	/** Get Date Manager Finance Not Approved.
	@return Date Manager Finance Not Approved	  */
	public Timestamp getZZ_Date_MFC_Not_Approved()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_MFC_Not_Approved);
	}

	/** Set Date Not Approved by LM.
	@param ZZ_Date_Not_Approved_by_LM Date Not Approved by LM
	 */
	public void setZZ_Date_Not_Approved_by_LM (Timestamp ZZ_Date_Not_Approved_by_LM)
	{
		set_Value (COLUMNNAME_ZZ_Date_Not_Approved_by_LM, ZZ_Date_Not_Approved_by_LM);
	}

	/** Get Date Not Approved by LM.
	@return Date Not Approved by LM	  */
	public Timestamp getZZ_Date_Not_Approved_by_LM()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Not_Approved_by_LM);
	}

	/** Set Date Not Approved by Snr Admin Finance.
	@param ZZ_Date_Not_Approved_by_Snr_Adm_Fin Date Not Approved by Snr Admin Finance
	 */
	public void setZZ_Date_Not_Approved_by_Snr_Adm_Fin (Timestamp ZZ_Date_Not_Approved_by_Snr_Adm_Fin)
	{
		set_Value (COLUMNNAME_ZZ_Date_Not_Approved_by_Snr_Adm_Fin, ZZ_Date_Not_Approved_by_Snr_Adm_Fin);
	}

	/** Get Date Not Approved by Snr Admin Finance.
	@return Date Not Approved by Snr Admin Finance	  */
	public Timestamp getZZ_Date_Not_Approved_by_Snr_Adm_Fin()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Not_Approved_by_Snr_Adm_Fin);
	}

	/** Set Date Approved By SDL Finance Manager.
	@param ZZ_Date_SDL_Approved Date Approved By SDL Finance Manager
	 */
	public void setZZ_Date_SDL_Approved (Timestamp ZZ_Date_SDL_Approved)
	{
		set_Value (COLUMNNAME_ZZ_Date_SDL_Approved, ZZ_Date_SDL_Approved);
	}

	/** Get Date Approved By SDL Finance Manager.
	@return Date Approved By SDL Finance Manager	  */
	public Timestamp getZZ_Date_SDL_Approved()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_SDL_Approved);
	}

	/** Set Date Not Approved By the SDL Finance Mgr.
	@param ZZ_Date_SDL_Not_Approved Date Not Approved By the SDL Finance Mgr
	 */
	public void setZZ_Date_SDL_Not_Approved (Timestamp ZZ_Date_SDL_Not_Approved)
	{
		set_Value (COLUMNNAME_ZZ_Date_SDL_Not_Approved, ZZ_Date_SDL_Not_Approved);
	}

	/** Get Date Not Approved By the SDL Finance Mgr.
	@return Date Not Approved By the SDL Finance Mgr	  */
	public Timestamp getZZ_Date_SDL_Not_Approved()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_SDL_Not_Approved);
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

	/** Approve/Do Not Approve = AP */
	public static final String ZZ_DOCACTION_ApproveDoNotApprove = "AP";
	/** Complete = CO */
	public static final String ZZ_DOCACTION_Complete = "CO";
	/** Final Approval/Do not Approve = FA */
	public static final String ZZ_DOCACTION_FinalApprovalDoNotApprove = "FA";
	/** Submit to Manager Finance Consumables = SC */
	public static final String ZZ_DOCACTION_SubmitToManagerFinanceConsumables = "SC";
	/** Submit to SDL Finance Mgr = SD */
	public static final String ZZ_DOCACTION_SubmitToSDLFinanceMgr = "SD";
	/** Submit to Line Manager = SU */
	public static final String ZZ_DOCACTION_SubmitToLineManager = "SU";
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

	/** Approved By Manager Finance Consumables = AC */
	public static final String ZZ_DOCSTATUS_ApprovedByManagerFinanceConsumables = "AC";
	/** Approved = AP */
	public static final String ZZ_DOCSTATUS_Approved = "AP";
	/** Completed = CO */
	public static final String ZZ_DOCSTATUS_Completed = "CO";
	/** Draft = DR */
	public static final String ZZ_DOCSTATUS_Draft = "DR";
	/** In Progress = IP */
	public static final String ZZ_DOCSTATUS_InProgress = "IP";
	/** Not Approved By Manager Finance Consumables = NC */
	public static final String ZZ_DOCSTATUS_NotApprovedByManagerFinanceConsumables = "NC";
	/** Not Approved By SDL Finance Mgr = ND */
	public static final String ZZ_DOCSTATUS_NotApprovedBySDLFinanceMgr = "ND";
	/** Not Approved by LM = NL */
	public static final String ZZ_DOCSTATUS_NotApprovedByLM = "NL";
	/** Not Approved by Snr Admin Finance = NS */
	public static final String ZZ_DOCSTATUS_NotApprovedBySnrAdminFinance = "NS";
	/** Submitted to Manager Finance Consumables = SC */
	public static final String ZZ_DOCSTATUS_SubmittedToManagerFinanceConsumables = "SC";
	/** Submitted To SDL Finance Mgr = SD */
	public static final String ZZ_DOCSTATUS_SubmittedToSDLFinanceMgr = "SD";
	/** Submitted = SU */
	public static final String ZZ_DOCSTATUS_Submitted = "SU";
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

	/** Approved By Manager Finance Consumables = AC */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_ApprovedByManagerFinanceConsumables = "AC";
	/** Approved = AP */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_Approved = "AP";
	/** Completed = CO */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_Completed = "CO";
	/** Draft = DR */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_Draft = "DR";
	/** In Progress = IP */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_InProgress = "IP";
	/** Not Approved By Manager Finance Consumables = NC */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_NotApprovedByManagerFinanceConsumables = "NC";
	/** Not Approved By SDL Finance Mgr = ND */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_NotApprovedBySDLFinanceMgr = "ND";
	/** Not Approved by LM = NL */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_NotApprovedByLM = "NL";
	/** Not Approved by Snr Admin Finance = NS */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_NotApprovedBySnrAdminFinance = "NS";
	/** Submitted to Manager Finance Consumables = SC */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_SubmittedToManagerFinanceConsumables = "SC";
	/** Submitted To SDL Finance Mgr = SD */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_SubmittedToSDLFinanceMgr = "SD";
	/** Submitted = SU */
	public static final String ZZ_FINALWORKFLOWSTATEVALUE_Submitted = "SU";
	/** Set Final Workflow State Value.
	@param ZZ_FinalWorkflowStateValue Value set to ZZ_DocStatus when reach to end of approve workflow
	 */
	public void setZZ_FinalWorkflowStateValue (String ZZ_FinalWorkflowStateValue)
	{

		set_Value (COLUMNNAME_ZZ_FinalWorkflowStateValue, ZZ_FinalWorkflowStateValue);
	}

	/** Get Final Workflow State Value.
	@return Value set to ZZ_DocStatus when reach to end of approve workflow
	 */
	public String getZZ_FinalWorkflowStateValue()
	{
		return (String)get_Value(COLUMNNAME_ZZ_FinalWorkflowStateValue);
	}

	public org.compiere.model.I_AD_User getZZ_Mgr_Fin_Consumables() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
				.getPO(getZZ_Mgr_Fin_Consumables_ID(), get_TrxName());
	}

	/** Set Manager Finance Consumables.
	@param ZZ_Mgr_Fin_Consumables_ID Manager Finance Consumables
	 */
	public void setZZ_Mgr_Fin_Consumables_ID (int ZZ_Mgr_Fin_Consumables_ID)
	{
		if (ZZ_Mgr_Fin_Consumables_ID < 1)
			set_Value (COLUMNNAME_ZZ_Mgr_Fin_Consumables_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Mgr_Fin_Consumables_ID, Integer.valueOf(ZZ_Mgr_Fin_Consumables_ID));
	}

	/** Get Manager Finance Consumables.
	@return Manager Finance Consumables	  */
	public int getZZ_Mgr_Fin_Consumables_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Mgr_Fin_Consumables_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getZZ_SDL_Fin_Mgr() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
				.getPO(getZZ_SDL_Fin_Mgr_ID(), get_TrxName());
	}

	/** Set SDL Finance Mgr.
	@param ZZ_SDL_Fin_Mgr_ID SDL Finance Mgr
	 */
	public void setZZ_SDL_Fin_Mgr_ID (int ZZ_SDL_Fin_Mgr_ID)
	{
		if (ZZ_SDL_Fin_Mgr_ID < 1)
			set_Value (COLUMNNAME_ZZ_SDL_Fin_Mgr_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_SDL_Fin_Mgr_ID, Integer.valueOf(ZZ_SDL_Fin_Mgr_ID));
	}

	/** Get SDL Finance Mgr.
	@return SDL Finance Mgr	  */
	public int getZZ_SDL_Fin_Mgr_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_SDL_Fin_Mgr_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getZZ_Snr_Admin_Fin() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
				.getPO(getZZ_Snr_Admin_Fin_ID(), get_TrxName());
	}

	/** Set Snr Admin Finance User.
	@param ZZ_Snr_Admin_Fin_ID Snr Admin Finance User
	 */
	public void setZZ_Snr_Admin_Fin_ID (int ZZ_Snr_Admin_Fin_ID)
	{
		if (ZZ_Snr_Admin_Fin_ID < 1)
			set_Value (COLUMNNAME_ZZ_Snr_Admin_Fin_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Snr_Admin_Fin_ID, Integer.valueOf(ZZ_Snr_Admin_Fin_ID));
	}

	/** Get Snr Admin Finance User.
	@return Snr Admin Finance User	  */
	public int getZZ_Snr_Admin_Fin_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Snr_Admin_Fin_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}


}
