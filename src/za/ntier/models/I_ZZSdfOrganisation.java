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

/** Generated Interface for ZZSdfOrganisation
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZSdfOrganisation 
{

    /** TableName=ZZSdfOrganisation */
    public static final String Table_Name = "ZZSdfOrganisation";

    /** AD_Table_ID=1000119 */
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

    /** Column name ZZActingForEmployer */
    public static final String COLUMNNAME_ZZActingForEmployer = "ZZActingForEmployer";

	/** Set Acting For Employer.
	  * Consultant acting for Employer?
	  */
	public void setZZActingForEmployer (boolean ZZActingForEmployer);

	/** Get Acting For Employer.
	  * Consultant acting for Employer?
	  */
	public boolean isZZActingForEmployer();

    /** Column name ZZAppointmentProcedure */
    public static final String COLUMNNAME_ZZAppointmentProcedure = "ZZAppointmentProcedure";

	/** Set Appointment Procedure.
	  * Please indicate method of appointment to SDF position
	  */
	public void setZZAppointmentProcedure (String ZZAppointmentProcedure);

	/** Get Appointment Procedure.
	  * Please indicate method of appointment to SDF position
	  */
	public String getZZAppointmentProcedure();

    /** Column name ZZAppointmentProcedureOther */
    public static final String COLUMNNAME_ZZAppointmentProcedureOther = "ZZAppointmentProcedureOther";

	/** Set Appointment Procedure Other.
	  * Other method of appointment
	  */
	public void setZZAppointmentProcedureOther (String ZZAppointmentProcedureOther);

	/** Get Appointment Procedure Other.
	  * Other method of appointment
	  */
	public String getZZAppointmentProcedureOther();

    /** Column name ZZReplacingPrimarySDF */
    public static final String COLUMNNAME_ZZReplacingPrimarySDF = "ZZReplacingPrimarySDF";

	/** Set Replacing Primary SDF.
	  * Are you replacing the previous primary SDF of this Company?
	  */
	public void setZZReplacingPrimarySDF (boolean ZZReplacingPrimarySDF);

	/** Get Replacing Primary SDF.
	  * Are you replacing the previous primary SDF of this Company?
	  */
	public boolean isZZReplacingPrimarySDF();

    /** Column name ZZSdfFunction */
    public static final String COLUMNNAME_ZZSdfFunction = "ZZSdfFunction";

	/** Set Sdf Function.
	  * Will you perform your SDF function in respect of
	  */
	public void setZZSdfFunction (String ZZSdfFunction);

	/** Get Sdf Function.
	  * Will you perform your SDF function in respect of
	  */
	public String getZZSdfFunction();

    /** Column name ZZSdfOrganisation_ID */
    public static final String COLUMNNAME_ZZSdfOrganisation_ID = "ZZSdfOrganisation_ID";

	/** Set SDF Organisation.
	  * Link Organisation And SDF
	  */
	public void setZZSdfOrganisation_ID (int ZZSdfOrganisation_ID);

	/** Get SDF Organisation.
	  * Link Organisation And SDF
	  */
	public int getZZSdfOrganisation_ID();

    /** Column name ZZSdfOrganisation_UU */
    public static final String COLUMNNAME_ZZSdfOrganisation_UU = "ZZSdfOrganisation_UU";

	/** Set SDF Organisation UU	  */
	public void setZZSdfOrganisation_UU (String ZZSdfOrganisation_UU);

	/** Get SDF Organisation UU	  */
	public String getZZSdfOrganisation_UU();

    /** Column name ZZSdfStatus */
    public static final String COLUMNNAME_ZZSdfStatus = "ZZSdfStatus";

	/** Set Sdf Status.
	  * Status Of Organisation Link
	  */
	public void setZZSdfStatus (String ZZSdfStatus);

	/** Get Sdf Status.
	  * Status Of Organisation Link
	  */
	public String getZZSdfStatus();

    /** Column name ZZSdfType */
    public static final String COLUMNNAME_ZZSdfType = "ZZSdfType";

	/** Set Sdf Type.
	  * Secondary Or Master
	  */
	public void setZZSdfType (String ZZSdfType);

	/** Get Sdf Type.
	  * Secondary Or Master
	  */
	public String getZZSdfType();

    /** Column name ZZSdf_ID */
    public static final String COLUMNNAME_ZZSdf_ID = "ZZSdf_ID";

	/** Set Sdf	  */
	public void setZZSdf_ID (int ZZSdf_ID);

	/** Get Sdf	  */
	public int getZZSdf_ID();

	public I_ZZSdf getZZSdf() throws RuntimeException;

    /** Column name ZZSecondarySdf */
    public static final String COLUMNNAME_ZZSecondarySdf = "ZZSecondarySdf";

	/** Set Secondary Sdf.
	  * Are you registering as secondary SDF for this Company?
	  */
	public void setZZSecondarySdf (boolean ZZSecondarySdf);

	/** Get Secondary Sdf.
	  * Are you registering as secondary SDF for this Company?
	  */
	public boolean isZZSecondarySdf();

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

    /** Column name ZZ_Status */
    public static final String COLUMNNAME_ZZ_Status = "ZZ_Status";

	/** Set Status	  */
	public void setZZ_Status (String ZZ_Status);

	/** Get Status	  */
	public String getZZ_Status();

    /** Column name ZZ_Submission_Date */
    public static final String COLUMNNAME_ZZ_Submission_Date = "ZZ_Submission_Date";

	/** Set Submission Date	  */
	public void setZZ_Submission_Date (Timestamp ZZ_Submission_Date);

	/** Get Submission Date	  */
	public Timestamp getZZ_Submission_Date();

    /** Column name ZZ_Submitter_ID */
    public static final String COLUMNNAME_ZZ_Submitter_ID = "ZZ_Submitter_ID";

	/** Set Submitted By	  */
	public void setZZ_Submitter_ID (int ZZ_Submitter_ID);

	/** Get Submitted By	  */
	public int getZZ_Submitter_ID();

	public org.compiere.model.I_AD_User getZZ_Submitter() throws RuntimeException;
}
