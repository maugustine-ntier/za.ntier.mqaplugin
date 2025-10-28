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

/** Generated Interface for ZZ_WF_Header
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZ_WF_Header 
{

    /** TableName=ZZ_WF_Header */
    public static final String Table_Name = "ZZ_WF_Header";

    /** AD_Table_ID=1000077 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

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

    /** Column name MMailText_FinalApproved_ID */
    public static final String COLUMNNAME_MMailText_FinalApproved_ID = "MMailText_FinalApproved_ID";

	/** Set Mail Text Final Approve	  */
	public void setMMailText_FinalApproved_ID (int MMailText_FinalApproved_ID);

	/** Get Mail Text Final Approve	  */
	public int getMMailText_FinalApproved_ID();

	public org.compiere.model.I_R_MailText getMMailText_FinalApproved() throws RuntimeException;

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

    /** Column name ZZ_FinalStatus */
    public static final String COLUMNNAME_ZZ_FinalStatus = "ZZ_FinalStatus";

	/** Set Final Status	  */
	public void setZZ_FinalStatus (String ZZ_FinalStatus);

	/** Get Final Status	  */
	public String getZZ_FinalStatus();

    /** Column name ZZ_NotifyMode */
    public static final String COLUMNNAME_ZZ_NotifyMode = "ZZ_NotifyMode";

	/** Set Notify Mode	  */
	public void setZZ_NotifyMode (String ZZ_NotifyMode);

	/** Get Notify Mode	  */
	public String getZZ_NotifyMode();

    /** Column name ZZ_StartStatus */
    public static final String COLUMNNAME_ZZ_StartStatus = "ZZ_StartStatus";

	/** Set Start Status	  */
	public void setZZ_StartStatus (String ZZ_StartStatus);

	/** Get Start Status	  */
	public String getZZ_StartStatus();

    /** Column name ZZ_WF_Header_ID */
    public static final String COLUMNNAME_ZZ_WF_Header_ID = "ZZ_WF_Header_ID";

	/** Set WF Header	  */
	public void setZZ_WF_Header_ID (int ZZ_WF_Header_ID);

	/** Get WF Header	  */
	public int getZZ_WF_Header_ID();
}
