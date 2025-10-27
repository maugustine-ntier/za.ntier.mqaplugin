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

/** Generated Interface for ZZ_WF_Audit
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZ_WF_Audit 
{

    /** TableName=ZZ_WF_Audit */
    public static final String Table_Name = "ZZ_WF_Audit";

    /** AD_Table_ID=1000080 */
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

    /** Column name ActionTaken */
    public static final String COLUMNNAME_ActionTaken = "ActionTaken";

	/** Set Action Taken	  */
	public void setActionTaken (String ActionTaken);

	/** Get Action Taken	  */
	public String getActionTaken();

    /** Column name Actor_AD_User_ID */
    public static final String COLUMNNAME_Actor_AD_User_ID = "Actor_AD_User_ID";

	/** Set Actor AD User ID	  */
	public void setActor_AD_User_ID (int Actor_AD_User_ID);

	/** Get Actor AD User ID	  */
	public int getActor_AD_User_ID();

	public org.compiere.model.I_AD_User getActor_AD_User() throws RuntimeException;

    /** Column name Comment */
    public static final String COLUMNNAME_Comment = "Comment";

	/** Set Comment	  */
	public void setComment (String Comment);

	/** Get Comment	  */
	public String getComment();

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

    /** Column name NewAction */
    public static final String COLUMNNAME_NewAction = "NewAction";

	/** Set New Action	  */
	public void setNewAction (String NewAction);

	/** Get New Action	  */
	public String getNewAction();

    /** Column name NewStatus */
    public static final String COLUMNNAME_NewStatus = "NewStatus";

	/** Set New Status	  */
	public void setNewStatus (String NewStatus);

	/** Get New Status	  */
	public String getNewStatus();

    /** Column name OldAction */
    public static final String COLUMNNAME_OldAction = "OldAction";

	/** Set Old Action	  */
	public void setOldAction (String OldAction);

	/** Get Old Action	  */
	public String getOldAction();

    /** Column name OldStatus */
    public static final String COLUMNNAME_OldStatus = "OldStatus";

	/** Set Old Status	  */
	public void setOldStatus (String OldStatus);

	/** Get Old Status	  */
	public String getOldStatus();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Record ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Record ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

    /** Column name ZZ_WF_Audit_ID */
    public static final String COLUMNNAME_ZZ_WF_Audit_ID = "ZZ_WF_Audit_ID";

	/** Set ZZ_WF_Audit	  */
	public void setZZ_WF_Audit_ID (int ZZ_WF_Audit_ID);

	/** Get ZZ_WF_Audit	  */
	public int getZZ_WF_Audit_ID();

    /** Column name ZZ_WF_Lines_ID */
    public static final String COLUMNNAME_ZZ_WF_Lines_ID = "ZZ_WF_Lines_ID";

	/** Set ZZ_WF_Lines	  */
	public void setZZ_WF_Lines_ID (int ZZ_WF_Lines_ID);

	/** Get ZZ_WF_Lines	  */
	public int getZZ_WF_Lines_ID();

	public I_ZZ_WF_Lines getZZ_WF_Lines() throws RuntimeException;
}
