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

/** Generated Model for ZZSdfOrganisation
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZSdfOrganisation")
public class X_ZZSdfOrganisation extends PO implements I_ZZSdfOrganisation, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251209L;

    /** Standard Constructor */
    public X_ZZSdfOrganisation (Properties ctx, int ZZSdfOrganisation_ID, String trxName)
    {
      super (ctx, ZZSdfOrganisation_ID, trxName);
      /** if (ZZSdfOrganisation_ID == 0)
        {
			setZZActingForEmployer (false);
// N
			setZZReplacingPrimarySDF (false);
// N
			setZZSdfOrganisation_ID (0);
			setZZSecondarySdf (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_ZZSdfOrganisation (Properties ctx, int ZZSdfOrganisation_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZSdfOrganisation_ID, trxName, virtualColumns);
      /** if (ZZSdfOrganisation_ID == 0)
        {
			setZZActingForEmployer (false);
// N
			setZZReplacingPrimarySDF (false);
// N
			setZZSdfOrganisation_ID (0);
			setZZSecondarySdf (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_ZZSdfOrganisation (Properties ctx, String ZZSdfOrganisation_UU, String trxName)
    {
      super (ctx, ZZSdfOrganisation_UU, trxName);
      /** if (ZZSdfOrganisation_UU == null)
        {
			setZZActingForEmployer (false);
// N
			setZZReplacingPrimarySDF (false);
// N
			setZZSdfOrganisation_ID (0);
			setZZSecondarySdf (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_ZZSdfOrganisation (Properties ctx, String ZZSdfOrganisation_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZSdfOrganisation_UU, trxName, virtualColumns);
      /** if (ZZSdfOrganisation_UU == null)
        {
			setZZActingForEmployer (false);
// N
			setZZReplacingPrimarySDF (false);
// N
			setZZSdfOrganisation_ID (0);
			setZZSecondarySdf (false);
// N
        } */
    }

    /** Load Constructor */
    public X_ZZSdfOrganisation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZSdfOrganisation[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getAD_User_ID(), get_TrxName());
	}

	/** Set User/Contact.
		@param AD_User_ID User within the system - Internal or Business Partner Contact
	*/
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_ID)
			.getPO(getC_BPartner_ID(), get_TrxName());
	}

	/** Set Business Partner.
		@param C_BPartner_ID Identifies a Business Partner
	*/
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Acting For Employer.
		@param ZZActingForEmployer Consultant acting for Employer?
	*/
	public void setZZActingForEmployer (boolean ZZActingForEmployer)
	{
		set_Value (COLUMNNAME_ZZActingForEmployer, Boolean.valueOf(ZZActingForEmployer));
	}

	/** Get Acting For Employer.
		@return Consultant acting for Employer?
	  */
	public boolean isZZActingForEmployer()
	{
		Object oo = get_Value(COLUMNNAME_ZZActingForEmployer);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Nominated by employees = Employees */
	public static final String ZZAPPOINTMENTPROCEDURE_NominatedByEmployees = "Employees";
	/** Appointed by employer = Employer */
	public static final String ZZAPPOINTMENTPROCEDURE_AppointedByEmployer = "Employer";
	/** Other, If other please specify = Other */
	public static final String ZZAPPOINTMENTPROCEDURE_OtherIfOtherPleaseSpecify = "Other";
	/** Self-appointed = Self */
	public static final String ZZAPPOINTMENTPROCEDURE_Self_Appointed = "Self";
	/** Set Appointment Procedure.
		@param ZZAppointmentProcedure Please indicate method of appointment to SDF position
	*/
	public void setZZAppointmentProcedure (String ZZAppointmentProcedure)
	{

		set_Value (COLUMNNAME_ZZAppointmentProcedure, ZZAppointmentProcedure);
	}

	/** Get Appointment Procedure.
		@return Please indicate method of appointment to SDF position
	  */
	public String getZZAppointmentProcedure()
	{
		return (String)get_Value(COLUMNNAME_ZZAppointmentProcedure);
	}

	/** Set Appointment Procedure Other.
		@param ZZAppointmentProcedureOther Other method of appointment
	*/
	public void setZZAppointmentProcedureOther (String ZZAppointmentProcedureOther)
	{
		set_Value (COLUMNNAME_ZZAppointmentProcedureOther, ZZAppointmentProcedureOther);
	}

	/** Get Appointment Procedure Other.
		@return Other method of appointment
	  */
	public String getZZAppointmentProcedureOther()
	{
		return (String)get_Value(COLUMNNAME_ZZAppointmentProcedureOther);
	}

	/** Set Replacing Primary SDF.
		@param ZZReplacingPrimarySDF Are you replacing the previous primary SDF of this Company?
	*/
	public void setZZReplacingPrimarySDF (boolean ZZReplacingPrimarySDF)
	{
		set_Value (COLUMNNAME_ZZReplacingPrimarySDF, Boolean.valueOf(ZZReplacingPrimarySDF));
	}

	/** Get Replacing Primary SDF.
		@return Are you replacing the previous primary SDF of this Company?
	  */
	public boolean isZZReplacingPrimarySDF()
	{
		Object oo = get_Value(COLUMNNAME_ZZReplacingPrimarySDF);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Group of organisations = Group */
	public static final String ZZSDFFUNCTION_GroupOfOrganisations = "Group";
	/** Multiple organisations = Multiple */
	public static final String ZZSDFFUNCTION_MultipleOrganisations = "Multiple";
	/** Multiple branch of an organisation = Multiple branch */
	public static final String ZZSDFFUNCTION_MultipleBranchOfAnOrganisation = "Multiple branch";
	/** Single establishment = Single */
	public static final String ZZSDFFUNCTION_SingleEstablishment = "Single";
	/** Single branch of an organisation = Single branch */
	public static final String ZZSDFFUNCTION_SingleBranchOfAnOrganisation = "Single branch";
	/** Set Sdf Function.
		@param ZZSdfFunction Will you perform your SDF function in respect of
	*/
	public void setZZSdfFunction (String ZZSdfFunction)
	{

		set_Value (COLUMNNAME_ZZSdfFunction, ZZSdfFunction);
	}

	/** Get Sdf Function.
		@return Will you perform your SDF function in respect of
	  */
	public String getZZSdfFunction()
	{
		return (String)get_Value(COLUMNNAME_ZZSdfFunction);
	}

	/** Set SDF Organisation.
		@param ZZSdfOrganisation_ID Link Organisation And SDF
	*/
	public void setZZSdfOrganisation_ID (int ZZSdfOrganisation_ID)
	{
		if (ZZSdfOrganisation_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZSdfOrganisation_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZSdfOrganisation_ID, Integer.valueOf(ZZSdfOrganisation_ID));
	}

	/** Get SDF Organisation.
		@return Link Organisation And SDF
	  */
	public int getZZSdfOrganisation_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZSdfOrganisation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SDF Organisation UU.
		@param ZZSdfOrganisation_UU SDF Organisation UU
	*/
	public void setZZSdfOrganisation_UU (String ZZSdfOrganisation_UU)
	{
		set_Value (COLUMNNAME_ZZSdfOrganisation_UU, ZZSdfOrganisation_UU);
	}

	/** Get SDF Organisation UU.
		@return SDF Organisation UU	  */
	public String getZZSdfOrganisation_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZSdfOrganisation_UU);
	}

	/** Primary = Primary */
	public static final String ZZSDFTYPE_Primary = "Primary";
	/** Secondary = Secondary */
	public static final String ZZSDFTYPE_Secondary = "Secondary";
	/** Set Sdf Type.
		@param ZZSdfType Secondary Or Master
	*/
	public void setZZSdfType (String ZZSdfType)
	{

		set_Value (COLUMNNAME_ZZSdfType, ZZSdfType);
	}

	/** Get Sdf Type.
		@return Secondary Or Master
	  */
	public String getZZSdfType()
	{
		return (String)get_Value(COLUMNNAME_ZZSdfType);
	}

	public I_ZZSdf getZZSdf() throws RuntimeException
	{
		return (I_ZZSdf)MTable.get(getCtx(), I_ZZSdf.Table_ID)
			.getPO(getZZSdf_ID(), get_TrxName());
	}

	/** Set Sdf.
		@param ZZSdf_ID Sdf
	*/
	public void setZZSdf_ID (int ZZSdf_ID)
	{
		if (ZZSdf_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZSdf_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZSdf_ID, Integer.valueOf(ZZSdf_ID));
	}

	/** Get Sdf.
		@return Sdf	  */
	public int getZZSdf_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZSdf_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Secondary Sdf.
		@param ZZSecondarySdf Are you registering as secondary SDF for this Company?
	*/
	public void setZZSecondarySdf (boolean ZZSecondarySdf)
	{
		set_Value (COLUMNNAME_ZZSecondarySdf, Boolean.valueOf(ZZSecondarySdf));
	}

	/** Get Secondary Sdf.
		@return Are you registering as secondary SDF for this Company?
	  */
	public boolean isZZSecondarySdf()
	{
		Object oo = get_Value(COLUMNNAME_ZZSecondarySdf);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_AD_User getZZ_Approved() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getZZ_Approved_ID(), get_TrxName());
	}

	/** Set Approved By.
		@param ZZ_Approved_ID Approved By
	*/
	public void setZZ_Approved_ID (int ZZ_Approved_ID)
	{
		if (ZZ_Approved_ID < 1)
			set_Value (COLUMNNAME_ZZ_Approved_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Approved_ID, Integer.valueOf(ZZ_Approved_ID));
	}

	/** Get Approved By.
		@return Approved By	  */
	public int getZZ_Approved_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Approved_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Date Rejected.
		@param ZZ_Date_Rejected Date Rejected
	*/
	public void setZZ_Date_Rejected (Timestamp ZZ_Date_Rejected)
	{
		set_Value (COLUMNNAME_ZZ_Date_Rejected, ZZ_Date_Rejected);
	}

	/** Get Date Rejected.
		@return Date Rejected	  */
	public Timestamp getZZ_Date_Rejected()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Rejected);
	}

	/** Exec Approve = AE */
	public static final String ZZ_DOCACTION_ExecApprove = "AE";
	/** Approve/Do Not Approve = AP */
	public static final String ZZ_DOCACTION_ApproveDoNotApprove = "AP";
	/** Complete = CO */
	public static final String ZZ_DOCACTION_Complete = "CO";
	/** Final Approval/Do not Approve = FA */
	public static final String ZZ_DOCACTION_FinalApprovalDoNotApprove = "FA";
	/** Recommend = RE */
	public static final String ZZ_DOCACTION_Recommend = "RE";
	/** Submit to Manager Finance Consumables = SC */
	public static final String ZZ_DOCACTION_SubmitToManagerFinanceConsumables = "SC";
	/** Submit to SDL Finance Mgr = SD */
	public static final String ZZ_DOCACTION_SubmitToSDLFinanceMgr = "SD";
	/** Submit to Snr Mgr LP = SL */
	public static final String ZZ_DOCACTION_SubmitToSnrMgrLP = "SL";
	/** Submit to Snr Mgr Ops = SO */
	public static final String ZZ_DOCACTION_SubmitToSnrMgrOps = "SO";
	/** Submit to Snr Mgr Projects = SP */
	public static final String ZZ_DOCACTION_SubmitToSnrMgrProjects = "SP";
	/** Submit to Snr Mgr QA = SQ */
	public static final String ZZ_DOCACTION_SubmitToSnrMgrQA = "SQ";
	/** Submit to Recommender = SR */
	public static final String ZZ_DOCACTION_SubmitToRecommender = "SR";
	/** Submit to Snr Mgr SRU = SS */
	public static final String ZZ_DOCACTION_SubmitToSnrMgrSRU = "SS";
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
	/** Not Recommended By Senior Mgr SDR = N1 */
	public static final String ZZ_DOCSTATUS_NotRecommendedBySeniorMgrSDR = "N1";
	/** Not Recommended By Senior Mgr Finance = N2 */
	public static final String ZZ_DOCSTATUS_NotRecommendedBySeniorMgrFinance = "N2";
	/** Not Recommended By COO = N3 */
	public static final String ZZ_DOCSTATUS_NotRecommendedByCOO = "N3";
	/** Not Recommended By CFO = N4 */
	public static final String ZZ_DOCSTATUS_NotRecommendedByCFO = "N4";
	/** Not Recommended By CEO = N5 */
	public static final String ZZ_DOCSTATUS_NotRecommendedByCEO = "N5";
	/** Not Approved by Snr Manager = NA */
	public static final String ZZ_DOCSTATUS_NotApprovedBySnrManager = "NA";
	/** Not Approved By Manager Finance Consumables = NC */
	public static final String ZZ_DOCSTATUS_NotApprovedByManagerFinanceConsumables = "NC";
	/** Not Approved By SDL Finance Mgr = ND */
	public static final String ZZ_DOCSTATUS_NotApprovedBySDLFinanceMgr = "ND";
	/** Not Approved By IT Manager = NI */
	public static final String ZZ_DOCSTATUS_NotApprovedByITManager = "NI";
	/** Not Approved by LM = NL */
	public static final String ZZ_DOCSTATUS_NotApprovedByLM = "NL";
	/** Not Recommended = NR */
	public static final String ZZ_DOCSTATUS_NotRecommended = "NR";
	/** Not Approved by Snr Admin Finance = NS */
	public static final String ZZ_DOCSTATUS_NotApprovedBySnrAdminFinance = "NS";
	/** Pending = PE */
	public static final String ZZ_DOCSTATUS_Pending = "PE";
	/** Recommended By Senior Mgr Finance = R1 */
	public static final String ZZ_DOCSTATUS_RecommendedBySeniorMgrFinance = "R1";
	/** Recommended By COO = R2 */
	public static final String ZZ_DOCSTATUS_RecommendedByCOO = "R2";
	/** Recommended By CFO = R3 */
	public static final String ZZ_DOCSTATUS_RecommendedByCFO = "R3";
	/** Recommended = RC */
	public static final String ZZ_DOCSTATUS_Recommended = "RC";
	/** Recommended By Senior Mgr SDR = RD */
	public static final String ZZ_DOCSTATUS_RecommendedBySeniorMgrSDR = "RD";
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

	public org.compiere.model.I_AD_User getZZ_Rejected() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getZZ_Rejected_ID(), get_TrxName());
	}

	/** Set Rejected By.
		@param ZZ_Rejected_ID Rejected By
	*/
	public void setZZ_Rejected_ID (int ZZ_Rejected_ID)
	{
		if (ZZ_Rejected_ID < 1)
			set_Value (COLUMNNAME_ZZ_Rejected_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Rejected_ID, Integer.valueOf(ZZ_Rejected_ID));
	}

	/** Get Rejected By.
		@return Rejected By	  */
	public int getZZ_Rejected_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Rejected_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Submission Date.
		@param ZZ_Submission_Date Submission Date
	*/
	public void setZZ_Submission_Date (Timestamp ZZ_Submission_Date)
	{
		set_Value (COLUMNNAME_ZZ_Submission_Date, ZZ_Submission_Date);
	}

	/** Get Submission Date.
		@return Submission Date	  */
	public Timestamp getZZ_Submission_Date()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Submission_Date);
	}

	public org.compiere.model.I_AD_User getZZ_Submitter() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getZZ_Submitter_ID(), get_TrxName());
	}

	/** Set Submitted By.
		@param ZZ_Submitter_ID Submitted By
	*/
	public void setZZ_Submitter_ID (int ZZ_Submitter_ID)
	{
		if (ZZ_Submitter_ID < 1)
			set_Value (COLUMNNAME_ZZ_Submitter_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Submitter_ID, Integer.valueOf(ZZ_Submitter_ID));
	}

	/** Get Submitted By.
		@return Submitted By	  */
	public int getZZ_Submitter_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Submitter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}