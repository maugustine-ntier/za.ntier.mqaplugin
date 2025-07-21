/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package za.ntier.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for ZZ_System_Access_Application
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_System_Access_Application")
public class X_ZZ_System_Access_Application extends PO implements I_ZZ_System_Access_Application, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250721L;

    /** Standard Constructor */
    public X_ZZ_System_Access_Application (Properties ctx, int ZZ_System_Access_Application_ID, String trxName)
    {
      super (ctx, ZZ_System_Access_Application_ID, trxName);
      /** if (ZZ_System_Access_Application_ID == 0)
        {
			setZZ_System_Access_Application_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_System_Access_Application (Properties ctx, int ZZ_System_Access_Application_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_System_Access_Application_ID, trxName, virtualColumns);
      /** if (ZZ_System_Access_Application_ID == 0)
        {
			setZZ_System_Access_Application_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_System_Access_Application (Properties ctx, String ZZ_System_Access_Application_UU, String trxName)
    {
      super (ctx, ZZ_System_Access_Application_UU, trxName);
      /** if (ZZ_System_Access_Application_UU == null)
        {
			setZZ_System_Access_Application_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_System_Access_Application (Properties ctx, String ZZ_System_Access_Application_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_System_Access_Application_UU, trxName, virtualColumns);
      /** if (ZZ_System_Access_Application_UU == null)
        {
			setZZ_System_Access_Application_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_System_Access_Application (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_ZZ_System_Access_Application[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** New User = N */
	public static final String ZZ_APPLICATION_TYPE_NewUser = "N";
	/** Remove User = R */
	public static final String ZZ_APPLICATION_TYPE_RemoveUser = "R";
	/** Update User = U */
	public static final String ZZ_APPLICATION_TYPE_UpdateUser = "U";
	/** Set Application Type.
		@param ZZ_Application_Type Application Type
	*/
	public void setZZ_Application_Type (String ZZ_Application_Type)
	{

		set_Value (COLUMNNAME_ZZ_Application_Type, ZZ_Application_Type);
	}

	/** Get Application Type.
		@return Application Type	  */
	public String getZZ_Application_Type()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Application_Type);
	}

	/** Set Date Account Created By IT Admin.
		@param ZZ_Date_Account_Created Date Account Created By IT Admin
	*/
	public void setZZ_Date_Account_Created (Timestamp ZZ_Date_Account_Created)
	{
		set_Value (COLUMNNAME_ZZ_Date_Account_Created, ZZ_Date_Account_Created);
	}

	/** Get Date Account Created By IT Admin.
		@return Date Account Created By IT Admin	  */
	public Timestamp getZZ_Date_Account_Created()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Account_Created);
	}

	/** Set Date Approved by IT Manager.
		@param ZZ_Date_IT_Manager_Approved Date Approved by IT Manager
	*/
	public void setZZ_Date_IT_Manager_Approved (Timestamp ZZ_Date_IT_Manager_Approved)
	{
		set_Value (COLUMNNAME_ZZ_Date_IT_Manager_Approved, ZZ_Date_IT_Manager_Approved);
	}

	/** Get Date Approved by IT Manager.
		@return Date Approved by IT Manager	  */
	public Timestamp getZZ_Date_IT_Manager_Approved()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_IT_Manager_Approved);
	}

	/** Set Date Rejected by IT Manager.
		@param ZZ_Date_IT_Manager_Rejected Date Rejected by IT Manager
	*/
	public void setZZ_Date_IT_Manager_Rejected (Timestamp ZZ_Date_IT_Manager_Rejected)
	{
		set_Value (COLUMNNAME_ZZ_Date_IT_Manager_Rejected, ZZ_Date_IT_Manager_Rejected);
	}

	/** Get Date Rejected by IT Manager.
		@return Date Rejected by IT Manager	  */
	public Timestamp getZZ_Date_IT_Manager_Rejected()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_IT_Manager_Rejected);
	}

	/** Set Manager Approved.
		@param ZZ_Date_Manager_Approved Manager Approved
	*/
	public void setZZ_Date_Manager_Approved (Timestamp ZZ_Date_Manager_Approved)
	{
		set_Value (COLUMNNAME_ZZ_Date_Manager_Approved, ZZ_Date_Manager_Approved);
	}

	/** Get Manager Approved.
		@return Manager Approved	  */
	public Timestamp getZZ_Date_Manager_Approved()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Manager_Approved);
	}

	/** Set Date Rejected By Manager.
		@param ZZ_Date_Manager_Rejected Date Rejected By Manager
	*/
	public void setZZ_Date_Manager_Rejected (Timestamp ZZ_Date_Manager_Rejected)
	{
		set_Value (COLUMNNAME_ZZ_Date_Manager_Rejected, ZZ_Date_Manager_Rejected);
	}

	/** Get Date Rejected By Manager.
		@return Date Rejected By Manager	  */
	public Timestamp getZZ_Date_Manager_Rejected()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Manager_Rejected);
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
	/** Not Approved By IT Manager = NI */
	public static final String ZZ_DOCSTATUS_NotApprovedByITManager = "NI";
	/** Not Approved by LM = NL */
	public static final String ZZ_DOCSTATUS_NotApprovedByLM = "NL";
	/** Not Approved by Snr Admin Finance = NS */
	public static final String ZZ_DOCSTATUS_NotApprovedBySnrAdminFinance = "NS";
	/** Submitted to Manager Finance Consumables = SC */
	public static final String ZZ_DOCSTATUS_SubmittedToManagerFinanceConsumables = "SC";
	/** Submitted To SDL Finance Mgr = SD */
	public static final String ZZ_DOCSTATUS_SubmittedToSDLFinanceMgr = "SD";
	/** Submitted To IT Manager = SI */
	public static final String ZZ_DOCSTATUS_SubmittedToITManager = "SI";
	/** Submitted To IT Admin = ST */
	public static final String ZZ_DOCSTATUS_SubmittedToITAdmin = "ST";
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

	/** Set Effective Date.
		@param ZZ_Effective_Date Effective Date
	*/
	public void setZZ_Effective_Date (Timestamp ZZ_Effective_Date)
	{
		set_Value (COLUMNNAME_ZZ_Effective_Date, ZZ_Effective_Date);
	}

	/** Get Effective Date.
		@return Effective Date	  */
	public Timestamp getZZ_Effective_Date()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Effective_Date);
	}

	/** Set Expiry Date.
		@param ZZ_Expiry_Date Expiry Date
	*/
	public void setZZ_Expiry_Date (Timestamp ZZ_Expiry_Date)
	{
		set_Value (COLUMNNAME_ZZ_Expiry_Date, ZZ_Expiry_Date);
	}

	/** Get Expiry Date.
		@return Expiry Date	  */
	public Timestamp getZZ_Expiry_Date()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Expiry_Date);
	}

	public org.compiere.model.I_AD_User getZZ_IT_Admin() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getZZ_IT_Admin_ID(), get_TrxName());
	}

	/** Set IT Admin.
		@param ZZ_IT_Admin_ID IT Admin
	*/
	public void setZZ_IT_Admin_ID (int ZZ_IT_Admin_ID)
	{
		if (ZZ_IT_Admin_ID < 1)
			set_Value (COLUMNNAME_ZZ_IT_Admin_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_IT_Admin_ID, Integer.valueOf(ZZ_IT_Admin_ID));
	}

	/** Get IT Admin.
		@return IT Admin	  */
	public int getZZ_IT_Admin_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_IT_Admin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getZZ_IT_Manager() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getZZ_IT_Manager_ID(), get_TrxName());
	}

	/** Set IT Manager .
		@param ZZ_IT_Manager_ID IT Manager 
	*/
	public void setZZ_IT_Manager_ID (int ZZ_IT_Manager_ID)
	{
		if (ZZ_IT_Manager_ID < 1)
			set_Value (COLUMNNAME_ZZ_IT_Manager_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_IT_Manager_ID, Integer.valueOf(ZZ_IT_Manager_ID));
	}

	/** Get IT Manager .
		@return IT Manager 	  */
	public int getZZ_IT_Manager_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_IT_Manager_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getZZ_Manager() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getZZ_Manager_ID(), get_TrxName());
	}

	/** Set Manager.
		@param ZZ_Manager_ID Manager
	*/
	public void setZZ_Manager_ID (int ZZ_Manager_ID)
	{
		if (ZZ_Manager_ID < 1)
			set_Value (COLUMNNAME_ZZ_Manager_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Manager_ID, Integer.valueOf(ZZ_Manager_ID));
	}

	/** Get Manager.
		@return Manager	  */
	public int getZZ_Manager_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Manager_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User Organization.
		@param ZZ_New_Org_ID User Organization
	*/
	public void setZZ_New_Org_ID (int ZZ_New_Org_ID)
	{
		if (ZZ_New_Org_ID < 1)
			set_Value (COLUMNNAME_ZZ_New_Org_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_New_Org_ID, Integer.valueOf(ZZ_New_Org_ID));
	}

	/** Get User Organization.
		@return User Organization	  */
	public int getZZ_New_Org_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_New_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set New User Name.
		@param ZZ_New_User_ID New User Name
	*/
	public void setZZ_New_User_ID (String ZZ_New_User_ID)
	{
		set_Value (COLUMNNAME_ZZ_New_User_ID, ZZ_New_User_ID);
	}

	/** Get New User Name.
		@return New User Name	  */
	public String getZZ_New_User_ID()
	{
		return (String)get_Value(COLUMNNAME_ZZ_New_User_ID);
	}

	/** Set Reason For Additional Access.
		@param ZZ_Reason_For_Additional_Access Reason For Additional Access
	*/
	public void setZZ_Reason_For_Additional_Access (String ZZ_Reason_For_Additional_Access)
	{
		set_Value (COLUMNNAME_ZZ_Reason_For_Additional_Access, ZZ_Reason_For_Additional_Access);
	}

	/** Get Reason For Additional Access.
		@return Reason For Additional Access	  */
	public String getZZ_Reason_For_Additional_Access()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Reason_For_Additional_Access);
	}

	/** Set Reason For Removal.
		@param ZZ_Reason_For_Removal Reason For Removal
	*/
	public void setZZ_Reason_For_Removal (String ZZ_Reason_For_Removal)
	{
		set_Value (COLUMNNAME_ZZ_Reason_For_Removal, ZZ_Reason_For_Removal);
	}

	/** Get Reason For Removal.
		@return Reason For Removal	  */
	public String getZZ_Reason_For_Removal()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Reason_For_Removal);
	}

	/** Set Removal Date.
		@param ZZ_Removal_Date Removal Date
	*/
	public void setZZ_Removal_Date (Timestamp ZZ_Removal_Date)
	{
		set_Value (COLUMNNAME_ZZ_Removal_Date, ZZ_Removal_Date);
	}

	/** Get Removal Date.
		@return Removal Date	  */
	public Timestamp getZZ_Removal_Date()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Removal_Date);
	}

	public org.compiere.model.I_AD_User getZZ_Requester() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getZZ_Requester_ID(), get_TrxName());
	}

	/** Set Requester.
		@param ZZ_Requester_ID Requester
	*/
	public void setZZ_Requester_ID (int ZZ_Requester_ID)
	{
		if (ZZ_Requester_ID < 1)
			set_Value (COLUMNNAME_ZZ_Requester_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Requester_ID, Integer.valueOf(ZZ_Requester_ID));
	}

	/** Get Requester.
		@return Requester	  */
	public int getZZ_Requester_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Requester_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Roles.
		@param ZZ_Roles Roles
	*/
	public void setZZ_Roles (String ZZ_Roles)
	{

		set_Value (COLUMNNAME_ZZ_Roles, ZZ_Roles);
	}

	/** Get Roles.
		@return Roles	  */
	public String getZZ_Roles()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Roles);
	}

	/** Set Updated Roles.
		@param ZZ_Roles_Updated Updated Roles
	*/
	public void setZZ_Roles_Updated (String ZZ_Roles_Updated)
	{

		set_Value (COLUMNNAME_ZZ_Roles_Updated, ZZ_Roles_Updated);
	}

	/** Get Updated Roles.
		@return Updated Roles	  */
	public String getZZ_Roles_Updated()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Roles_Updated);
	}

	/** Set System Access Application.
		@param ZZ_System_Access_Application_ID System Access Application
	*/
	public void setZZ_System_Access_Application_ID (int ZZ_System_Access_Application_ID)
	{
		if (ZZ_System_Access_Application_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_System_Access_Application_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_System_Access_Application_ID, Integer.valueOf(ZZ_System_Access_Application_ID));
	}

	/** Get System Access Application.
		@return System Access Application	  */
	public int getZZ_System_Access_Application_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_System_Access_Application_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_System_Access_Application_UU.
		@param ZZ_System_Access_Application_UU ZZ_System_Access_Application_UU
	*/
	public void setZZ_System_Access_Application_UU (String ZZ_System_Access_Application_UU)
	{
		set_Value (COLUMNNAME_ZZ_System_Access_Application_UU, ZZ_System_Access_Application_UU);
	}

	/** Get ZZ_System_Access_Application_UU.
		@return ZZ_System_Access_Application_UU	  */
	public String getZZ_System_Access_Application_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_System_Access_Application_UU);
	}

	public I_ZZ_System getZZ_System() throws RuntimeException
	{
		return (I_ZZ_System)MTable.get(getCtx(), I_ZZ_System.Table_ID)
			.getPO(getZZ_System_ID(), get_TrxName());
	}

	/** Set System.
		@param ZZ_System_ID System
	*/
	public void setZZ_System_ID (int ZZ_System_ID)
	{
		if (ZZ_System_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_System_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_System_ID, Integer.valueOf(ZZ_System_ID));
	}

	/** Get System.
		@return System	  */
	public int getZZ_System_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_System_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User ID.
		@param ZZ_User User ID
	*/
	public void setZZ_User (String ZZ_User)
	{
		set_Value (COLUMNNAME_ZZ_User, ZZ_User);
	}

	/** Get User ID.
		@return User ID	  */
	public String getZZ_User()
	{
		return (String)get_Value(COLUMNNAME_ZZ_User);
	}
}