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
package za.ntier.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for ZZ_System_Access_App
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZ_System_Access_App 
{

    /** TableName=ZZ_System_Access_App */
    public static final String Table_Name = "ZZ_System_Access_App";

    /** AD_Table_ID=1000031 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Tenant.
	  * Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within tenant
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within tenant
	  */
	public int getAD_Org_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name ZZ_Date_Account_Created */
    public static final String COLUMNNAME_ZZ_Date_Account_Created = "ZZ_Date_Account_Created";

	/** Set Date Account Created By IT Admin	  */
	public void setZZ_Date_Account_Created (Timestamp ZZ_Date_Account_Created);

	/** Get Date Account Created By IT Admin	  */
	public Timestamp getZZ_Date_Account_Created();

    /** Column name ZZ_Date_IT_Manager_Approved */
    public static final String COLUMNNAME_ZZ_Date_IT_Manager_Approved = "ZZ_Date_IT_Manager_Approved";

	/** Set Date Approved by IT Manager	  */
	public void setZZ_Date_IT_Manager_Approved (Timestamp ZZ_Date_IT_Manager_Approved);

	/** Get Date Approved by IT Manager	  */
	public Timestamp getZZ_Date_IT_Manager_Approved();

    /** Column name ZZ_Date_IT_Manager_Rejected */
    public static final String COLUMNNAME_ZZ_Date_IT_Manager_Rejected = "ZZ_Date_IT_Manager_Rejected";

	/** Set Date Rejected by IT Manager	  */
	public void setZZ_Date_IT_Manager_Rejected (Timestamp ZZ_Date_IT_Manager_Rejected);

	/** Get Date Rejected by IT Manager	  */
	public Timestamp getZZ_Date_IT_Manager_Rejected();

    /** Column name ZZ_Date_Manager_Approved */
    public static final String COLUMNNAME_ZZ_Date_Manager_Approved = "ZZ_Date_Manager_Approved";

	/** Set Manager Approved	  */
	public void setZZ_Date_Manager_Approved (Timestamp ZZ_Date_Manager_Approved);

	/** Get Manager Approved	  */
	public Timestamp getZZ_Date_Manager_Approved();

    /** Column name ZZ_Date_Manager_Rejected */
    public static final String COLUMNNAME_ZZ_Date_Manager_Rejected = "ZZ_Date_Manager_Rejected";

	/** Set Date Rejected By Manager	  */
	public void setZZ_Date_Manager_Rejected (Timestamp ZZ_Date_Manager_Rejected);

	/** Get Date Rejected By Manager	  */
	public Timestamp getZZ_Date_Manager_Rejected();

    /** Column name ZZ_Date_Submitted */
    public static final String COLUMNNAME_ZZ_Date_Submitted = "ZZ_Date_Submitted";

	/** Set Date Submitted	  */
	public void setZZ_Date_Submitted (Timestamp ZZ_Date_Submitted);

	/** Get Date Submitted	  */
	public Timestamp getZZ_Date_Submitted();

    /** Column name ZZ_DocAction */
    public static final String COLUMNNAME_ZZ_DocAction = "ZZ_DocAction";

	/** Set Document Action	  */
	public void setZZ_DocAction (String ZZ_DocAction);

	/** Get Document Action	  */
	public String getZZ_DocAction();

    /** Column name ZZ_DocStatus */
    public static final String COLUMNNAME_ZZ_DocStatus = "ZZ_DocStatus";

	/** Set Document Status	  */
	public void setZZ_DocStatus (String ZZ_DocStatus);

	/** Get Document Status	  */
	public String getZZ_DocStatus();

    /** Column name ZZ_IT_Admin_ID */
    public static final String COLUMNNAME_ZZ_IT_Admin_ID = "ZZ_IT_Admin_ID";

	/** Set IT Admin	  */
	public void setZZ_IT_Admin_ID (int ZZ_IT_Admin_ID);

	/** Get IT Admin	  */
	public int getZZ_IT_Admin_ID();

	public org.compiere.model.I_AD_User getZZ_IT_Admin() throws RuntimeException;

    /** Column name ZZ_IT_Manager_ID */
    public static final String COLUMNNAME_ZZ_IT_Manager_ID = "ZZ_IT_Manager_ID";

	/** Set IT Manager 	  */
	public void setZZ_IT_Manager_ID (int ZZ_IT_Manager_ID);

	/** Get IT Manager 	  */
	public int getZZ_IT_Manager_ID();

	public org.compiere.model.I_AD_User getZZ_IT_Manager() throws RuntimeException;

    /** Column name ZZ_Manager_ID */
    public static final String COLUMNNAME_ZZ_Manager_ID = "ZZ_Manager_ID";

	/** Set Manager	  */
	public void setZZ_Manager_ID (int ZZ_Manager_ID);

	/** Get Manager	  */
	public int getZZ_Manager_ID();

	public org.compiere.model.I_AD_User getZZ_Manager() throws RuntimeException;

    /** Column name ZZ_System */
    public static final String COLUMNNAME_ZZ_System = "ZZ_System";

	/** Set System	  */
	public void setZZ_System (int ZZ_System);

	/** Get System	  */
	public int getZZ_System();

	public I_ZZ_System getZZ_Sys() throws RuntimeException;

    /** Column name ZZ_System_Access_App_ID */
    public static final String COLUMNNAME_ZZ_System_Access_App_ID = "ZZ_System_Access_App_ID";

	/** Set System Access Application	  */
	public void setZZ_System_Access_App_ID (int ZZ_System_Access_App_ID);

	/** Get System Access Application	  */
	public int getZZ_System_Access_App_ID();

    /** Column name ZZ_System_Access_App_UU */
    public static final String COLUMNNAME_ZZ_System_Access_App_UU = "ZZ_System_Access_App_UU";

	/** Set ZZ_System_Access_App_UU	  */
	public void setZZ_System_Access_App_UU (String ZZ_System_Access_App_UU);

	/** Get ZZ_System_Access_App_UU	  */
	public String getZZ_System_Access_App_UU();
}
