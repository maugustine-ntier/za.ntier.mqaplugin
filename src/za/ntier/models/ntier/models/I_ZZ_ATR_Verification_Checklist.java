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

/** Generated Interface for ZZ_ATR_Verification_Checklist
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZ_ATR_Verification_Checklist 
{

    /** TableName=ZZ_ATR_Verification_Checklist */
    public static final String Table_Name = "ZZ_ATR_Verification_Checklist";

    /** AD_Table_ID=1000118 */
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

    /** Column name ZZ_ATRVerification_ID */
    public static final String COLUMNNAME_ZZ_ATRVerification_ID = "ZZ_ATRVerification_ID";

	/** Set ATR Verification	  */
	public void setZZ_ATRVerification_ID (int ZZ_ATRVerification_ID);

	/** Get ATR Verification	  */
	public int getZZ_ATRVerification_ID();

	public I_ZZ_ATRVerification getZZ_ATRVerification() throws RuntimeException;

    /** Column name ZZ_ATR_Verification_Checklist_ID */
    public static final String COLUMNNAME_ZZ_ATR_Verification_Checklist_ID = "ZZ_ATR_Verification_Checklist_ID";

	/** Set ATR Verification Checklist	  */
	public void setZZ_ATR_Verification_Checklist_ID (int ZZ_ATR_Verification_Checklist_ID);

	/** Get ATR Verification Checklist	  */
	public int getZZ_ATR_Verification_Checklist_ID();

    /** Column name ZZ_ATR_Verification_Checklist_UU */
    public static final String COLUMNNAME_ZZ_ATR_Verification_Checklist_UU = "ZZ_ATR_Verification_Checklist_UU";

	/** Set ZZ_ATR_Verification_Checklist_UU	  */
	public void setZZ_ATR_Verification_Checklist_UU (String ZZ_ATR_Verification_Checklist_UU);

	/** Get ZZ_ATR_Verification_Checklist_UU	  */
	public String getZZ_ATR_Verification_Checklist_UU();

    /** Column name ZZ_Information_Completed */
    public static final String COLUMNNAME_ZZ_Information_Completed = "ZZ_Information_Completed";

	/** Set Information Completed	  */
	public void setZZ_Information_Completed (boolean ZZ_Information_Completed);

	/** Get Information Completed	  */
	public boolean isZZ_Information_Completed();

    /** Column name ZZ_Information_Submitted */
    public static final String COLUMNNAME_ZZ_Information_Submitted = "ZZ_Information_Submitted";

	/** Set Information Submitted	  */
	public void setZZ_Information_Submitted (boolean ZZ_Information_Submitted);

	/** Get Information Submitted	  */
	public boolean isZZ_Information_Submitted();

    /** Column name ZZ_Verification_Comments */
    public static final String COLUMNNAME_ZZ_Verification_Comments = "ZZ_Verification_Comments";

	/** Set Comments	  */
	public void setZZ_Verification_Comments (String ZZ_Verification_Comments);

	/** Get Comments	  */
	public String getZZ_Verification_Comments();
}
