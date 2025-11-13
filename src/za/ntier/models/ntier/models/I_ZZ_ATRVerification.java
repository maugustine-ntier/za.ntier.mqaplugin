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

/** Generated Interface for ZZ_ATRVerification
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZ_ATRVerification 
{

    /** TableName=ZZ_ATRVerification */
    public static final String Table_Name = "ZZ_ATRVerification";

    /** AD_Table_ID=1000115 */
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

	/** Set Unit.
	  * Organizational entity within tenant
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Unit.
	  * Organizational entity within tenant
	  */
	public int getAD_Org_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner.
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner.
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Year_ID */
    public static final String COLUMNNAME_C_Year_ID = "C_Year_ID";

	/** Set Year.
	  * Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID);

	/** Get Year.
	  * Calendar Year
	  */
	public int getC_Year_ID();

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException;

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

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

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name ZZ_ATRQueryReason_ID */
    public static final String COLUMNNAME_ZZ_ATRQueryReason_ID = "ZZ_ATRQueryReason_ID";

	/** Set Query Reason	  */
	public void setZZ_ATRQueryReason_ID (int ZZ_ATRQueryReason_ID);

	/** Get Query Reason	  */
	public int getZZ_ATRQueryReason_ID();

	public I_ZZ_ATRQueryReason getZZ_ATRQueryReason() throws RuntimeException;

    /** Column name ZZ_ATRVerification_ID */
    public static final String COLUMNNAME_ZZ_ATRVerification_ID = "ZZ_ATRVerification_ID";

	/** Set ATR Verification	  */
	public void setZZ_ATRVerification_ID (int ZZ_ATRVerification_ID);

	/** Get ATR Verification	  */
	public int getZZ_ATRVerification_ID();

    /** Column name ZZ_ATRVerification_UU */
    public static final String COLUMNNAME_ZZ_ATRVerification_UU = "ZZ_ATRVerification_UU";

	/** Set ZZ_ATRVerification_UU	  */
	public void setZZ_ATRVerification_UU (String ZZ_ATRVerification_UU);

	/** Get ZZ_ATRVerification_UU	  */
	public String getZZ_ATRVerification_UU();

    /** Column name ZZ_ATR_Checklist_ID */
    public static final String COLUMNNAME_ZZ_ATR_Checklist_ID = "ZZ_ATR_Checklist_ID";

	/** Set ATR Checklist	  */
	public void setZZ_ATR_Checklist_ID (int ZZ_ATR_Checklist_ID);

	/** Get ATR Checklist	  */
	public int getZZ_ATR_Checklist_ID();

	public I_ZZ_ATR_Checklist getZZ_ATR_Checklist() throws RuntimeException;

    /** Column name ZZ_Submission_Date */
    public static final String COLUMNNAME_ZZ_Submission_Date = "ZZ_Submission_Date";

	/** Set Submission Date	  */
	public void setZZ_Submission_Date (Timestamp ZZ_Submission_Date);

	/** Get Submission Date	  */
	public Timestamp getZZ_Submission_Date();

    /** Column name ZZ_Verification_Comment */
    public static final String COLUMNNAME_ZZ_Verification_Comment = "ZZ_Verification_Comment";

	/** Set Verification Comment	  */
	public void setZZ_Verification_Comment (String ZZ_Verification_Comment);

	/** Get Verification Comment	  */
	public String getZZ_Verification_Comment();

    /** Column name ZZ_Verification_Status */
    public static final String COLUMNNAME_ZZ_Verification_Status = "ZZ_Verification_Status";

	/** Set Verification Status	  */
	public void setZZ_Verification_Status (String ZZ_Verification_Status);

	/** Get Verification Status	  */
	public String getZZ_Verification_Status();
}
