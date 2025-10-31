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

/** Generated Interface for ZZ_WF_Lines
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZ_WF_Lines 
{

    /** TableName=ZZ_WF_Lines */
    public static final String Table_Name = "ZZ_WF_Lines";

    /** AD_Table_ID=1000078 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

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

    /** Column name AllowedFromStatus */
    public static final String COLUMNNAME_AllowedFromStatus = "AllowedFromStatus";

	/** Set Allowed From Status	  */
	public void setAllowedFromStatus (String AllowedFromStatus);

	/** Get Allowed From Status	  */
	public String getAllowedFromStatus();

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

    /** Column name MMailText_Approved_ID */
    public static final String COLUMNNAME_MMailText_Approved_ID = "MMailText_Approved_ID";

	/** Set M Mail Text Approved ID	  */
	public void setMMailText_Approved_ID (int MMailText_Approved_ID);

	/** Get M Mail Text Approved ID	  */
	public int getMMailText_Approved_ID();

	public org.compiere.model.I_R_MailText getMMailText_Approved() throws RuntimeException;

    /** Column name MMailText_Rejected_ID */
    public static final String COLUMNNAME_MMailText_Rejected_ID = "MMailText_Rejected_ID";

	/** Set M Mail Text Rejected ID	  */
	public void setMMailText_Rejected_ID (int MMailText_Rejected_ID);

	/** Get M Mail Text Rejected ID	  */
	public int getMMailText_Rejected_ID();

	public org.compiere.model.I_R_MailText getMMailText_Rejected() throws RuntimeException;

    /** Column name MMailText_Request_ID */
    public static final String COLUMNNAME_MMailText_Request_ID = "MMailText_Request_ID";

	/** Set M Mail Text Request ID	  */
	public void setMMailText_Request_ID (int MMailText_Request_ID);

	/** Get M Mail Text Request ID	  */
	public int getMMailText_Request_ID();

	public org.compiere.model.I_R_MailText getMMailText_Request() throws RuntimeException;

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

    /** Column name NextActionOnApprove */
    public static final String COLUMNNAME_NextActionOnApprove = "NextActionOnApprove";

	/** Set Next Action On Approve	  */
	public void setNextActionOnApprove (String NextActionOnApprove);

	/** Get Next Action On Approve	  */
	public String getNextActionOnApprove();

    /** Column name NextActionOnReject */
    public static final String COLUMNNAME_NextActionOnReject = "NextActionOnReject";

	/** Set Next Action On Reject	  */
	public void setNextActionOnReject (String NextActionOnReject);

	/** Get Next Action On Reject	  */
	public String getNextActionOnReject();

    /** Column name NextStatusOnApprove */
    public static final String COLUMNNAME_NextStatusOnApprove = "NextStatusOnApprove";

	/** Set Next Status On Approve	  */
	public void setNextStatusOnApprove (String NextStatusOnApprove);

	/** Get Next Status On Approve	  */
	public String getNextStatusOnApprove();

    /** Column name NextStatusOnReject */
    public static final String COLUMNNAME_NextStatusOnReject = "NextStatusOnReject";

	/** Set Next Status On Reject	  */
	public void setNextStatusOnReject (String NextStatusOnReject);

	/** Get Next Status On Reject	  */
	public String getNextStatusOnReject();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name SetDocAction */
    public static final String COLUMNNAME_SetDocAction = "SetDocAction";

	/** Set Set Doc Action	  */
	public void setSetDocAction (String SetDocAction);

	/** Get Set Doc Action	  */
	public String getSetDocAction();

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

    /** Column name ZZ_Approved_TS_COL_ID */
    public static final String COLUMNNAME_ZZ_Approved_TS_COL_ID = "ZZ_Approved_TS_COL_ID";

	/** Set Zz Approved Ts Col Id	  */
	public void setZZ_Approved_TS_COL_ID (int ZZ_Approved_TS_COL_ID);

	/** Get Zz Approved Ts Col Id	  */
	public int getZZ_Approved_TS_COL_ID();

	public org.compiere.model.I_AD_Column getZZ_Approved_TS_COL() throws RuntimeException;

    /** Column name ZZ_Approved_User_COL_ID */
    public static final String COLUMNNAME_ZZ_Approved_User_COL_ID = "ZZ_Approved_User_COL_ID";

	/** Set Approved User Col Id	  */
	public void setZZ_Approved_User_COL_ID (int ZZ_Approved_User_COL_ID);

	/** Get Approved User Col Id	  */
	public int getZZ_Approved_User_COL_ID();

	public org.compiere.model.I_AD_Column getZZ_Approved_User_COL() throws RuntimeException;

    /** Column name ZZ_Rejected_TS_COL_ID */
    public static final String COLUMNNAME_ZZ_Rejected_TS_COL_ID = "ZZ_Rejected_TS_COL_ID";

	/** Set Zz Rejected Ts Col Id	  */
	public void setZZ_Rejected_TS_COL_ID (int ZZ_Rejected_TS_COL_ID);

	/** Get Zz Rejected Ts Col Id	  */
	public int getZZ_Rejected_TS_COL_ID();

	public org.compiere.model.I_AD_Column getZZ_Rejected_TS_COL() throws RuntimeException;

    /** Column name ZZ_Rejected_User_COL_ID */
    public static final String COLUMNNAME_ZZ_Rejected_User_COL_ID = "ZZ_Rejected_User_COL_ID";

	/** Set Zz Rejected User Col Id	  */
	public void setZZ_Rejected_User_COL_ID (int ZZ_Rejected_User_COL_ID);

	/** Get Zz Rejected User Col Id	  */
	public int getZZ_Rejected_User_COL_ID();

	public org.compiere.model.I_AD_Column getZZ_Rejected_User_COL() throws RuntimeException;

    /** Column name ZZ_Specific_Responsible_Col_ID */
    public static final String COLUMNNAME_ZZ_Specific_Responsible_Col_ID = "ZZ_Specific_Responsible_Col_ID";

	/** Set Specific Responsible Col ID	  */
	public void setZZ_Specific_Responsible_Col_ID (int ZZ_Specific_Responsible_Col_ID);

	/** Get Specific Responsible Col ID	  */
	public int getZZ_Specific_Responsible_Col_ID();

	public org.compiere.model.I_AD_Column getZZ_Specific_Responsible_Col() throws RuntimeException;

    /** Column name ZZ_WF_Header_ID */
    public static final String COLUMNNAME_ZZ_WF_Header_ID = "ZZ_WF_Header_ID";

	/** Set WF Header	  */
	public void setZZ_WF_Header_ID (int ZZ_WF_Header_ID);

	/** Get WF Header	  */
	public int getZZ_WF_Header_ID();

	public I_ZZ_WF_Header getZZ_WF_Header() throws RuntimeException;

    /** Column name ZZ_WF_Lines_ID */
    public static final String COLUMNNAME_ZZ_WF_Lines_ID = "ZZ_WF_Lines_ID";

	/** Set ZZ_WF_Lines	  */
	public void setZZ_WF_Lines_ID (int ZZ_WF_Lines_ID);

	/** Get ZZ_WF_Lines	  */
	public int getZZ_WF_Lines_ID();
}
