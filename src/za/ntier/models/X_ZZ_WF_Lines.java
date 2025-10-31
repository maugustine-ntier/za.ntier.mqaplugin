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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for ZZ_WF_Lines
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_WF_Lines")
public class X_ZZ_WF_Lines extends PO implements I_ZZ_WF_Lines, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251031L;

    /** Standard Constructor */
    public X_ZZ_WF_Lines (Properties ctx, int ZZ_WF_Lines_ID, String trxName)
    {
      super (ctx, ZZ_WF_Lines_ID, trxName);
      /** if (ZZ_WF_Lines_ID == 0)
        {
			setAllowedFromStatus (null);
			setName (null);
			setNextStatusOnApprove (null);
			setNextStatusOnReject (null);
			setSeqNo (0);
			setSetDocAction (null);
			setZZ_WF_Header_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Lines (Properties ctx, int ZZ_WF_Lines_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WF_Lines_ID, trxName, virtualColumns);
      /** if (ZZ_WF_Lines_ID == 0)
        {
			setAllowedFromStatus (null);
			setName (null);
			setNextStatusOnApprove (null);
			setNextStatusOnReject (null);
			setSeqNo (0);
			setSetDocAction (null);
			setZZ_WF_Header_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Lines (Properties ctx, String ZZ_WF_Lines_UU, String trxName)
    {
      super (ctx, ZZ_WF_Lines_UU, trxName);
      /** if (ZZ_WF_Lines_UU == null)
        {
			setAllowedFromStatus (null);
			setName (null);
			setNextStatusOnApprove (null);
			setNextStatusOnReject (null);
			setSeqNo (0);
			setSetDocAction (null);
			setZZ_WF_Header_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Lines (Properties ctx, String ZZ_WF_Lines_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WF_Lines_UU, trxName, virtualColumns);
      /** if (ZZ_WF_Lines_UU == null)
        {
			setAllowedFromStatus (null);
			setName (null);
			setNextStatusOnApprove (null);
			setNextStatusOnReject (null);
			setSeqNo (0);
			setSetDocAction (null);
			setZZ_WF_Header_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_WF_Lines (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client
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
      StringBuilder sb = new StringBuilder ("X_ZZ_WF_Lines[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Approved By Manager Finance Consumables = AC */
	public static final String ALLOWEDFROMSTATUS_ApprovedByManagerFinanceConsumables = "AC";
	/** Approved = AP */
	public static final String ALLOWEDFROMSTATUS_Approved = "AP";
	/** Completed = CO */
	public static final String ALLOWEDFROMSTATUS_Completed = "CO";
	/** Draft = DR */
	public static final String ALLOWEDFROMSTATUS_Draft = "DR";
	/** In Progress = IP */
	public static final String ALLOWEDFROMSTATUS_InProgress = "IP";
	/** Not Recommended By Senior Mgr SDR = N1 */
	public static final String ALLOWEDFROMSTATUS_NotRecommendedBySeniorMgrSDR = "N1";
	/** Not Recommended By Senior Mgr Finance = N2 */
	public static final String ALLOWEDFROMSTATUS_NotRecommendedBySeniorMgrFinance = "N2";
	/** Not Recommended By COO = N3 */
	public static final String ALLOWEDFROMSTATUS_NotRecommendedByCOO = "N3";
	/** Not Recommended By CFO = N4 */
	public static final String ALLOWEDFROMSTATUS_NotRecommendedByCFO = "N4";
	/** Not Recommended By CEO = N5 */
	public static final String ALLOWEDFROMSTATUS_NotRecommendedByCEO = "N5";
	/** Not Approved by Snr Manager = NA */
	public static final String ALLOWEDFROMSTATUS_NotApprovedBySnrManager = "NA";
	/** Not Approved By Manager Finance Consumables = NC */
	public static final String ALLOWEDFROMSTATUS_NotApprovedByManagerFinanceConsumables = "NC";
	/** Not Approved By SDL Finance Mgr = ND */
	public static final String ALLOWEDFROMSTATUS_NotApprovedBySDLFinanceMgr = "ND";
	/** Not Approved By IT Manager = NI */
	public static final String ALLOWEDFROMSTATUS_NotApprovedByITManager = "NI";
	/** Not Approved by LM = NL */
	public static final String ALLOWEDFROMSTATUS_NotApprovedByLM = "NL";
	/** Not Recommended = NR */
	public static final String ALLOWEDFROMSTATUS_NotRecommended = "NR";
	/** Not Approved by Snr Admin Finance = NS */
	public static final String ALLOWEDFROMSTATUS_NotApprovedBySnrAdminFinance = "NS";
	/** Recommended By Senior Mgr Finance = R1 */
	public static final String ALLOWEDFROMSTATUS_RecommendedBySeniorMgrFinance = "R1";
	/** Recommended By COO = R2 */
	public static final String ALLOWEDFROMSTATUS_RecommendedByCOO = "R2";
	/** Recommended = RC */
	public static final String ALLOWEDFROMSTATUS_Recommended = "RC";
	/** Recommended By Senior Mgr SDR = RD */
	public static final String ALLOWEDFROMSTATUS_RecommendedBySeniorMgrSDR = "RD";
	/** Submitted to Manager Finance Consumables = SC */
	public static final String ALLOWEDFROMSTATUS_SubmittedToManagerFinanceConsumables = "SC";
	/** Submitted To SDL Finance Mgr = SD */
	public static final String ALLOWEDFROMSTATUS_SubmittedToSDLFinanceMgr = "SD";
	/** Submitted To IT Manager = SI */
	public static final String ALLOWEDFROMSTATUS_SubmittedToITManager = "SI";
	/** Submitted To IT Admin = ST */
	public static final String ALLOWEDFROMSTATUS_SubmittedToITAdmin = "ST";
	/** Submitted = SU */
	public static final String ALLOWEDFROMSTATUS_Submitted = "SU";
	/** Set Allowed From Status.
		@param AllowedFromStatus Allowed From Status
	*/
	public void setAllowedFromStatus (String AllowedFromStatus)
	{

		set_Value (COLUMNNAME_AllowedFromStatus, AllowedFromStatus);
	}

	/** Get Allowed From Status.
		@return Allowed From Status	  */
	public String getAllowedFromStatus()
	{
		return (String)get_Value(COLUMNNAME_AllowedFromStatus);
	}

	/** Set Description.
		@param Description Optional short description of the record
	*/
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	public org.compiere.model.I_R_MailText getMMailText_Approved() throws RuntimeException
	{
		return (org.compiere.model.I_R_MailText)MTable.get(getCtx(), org.compiere.model.I_R_MailText.Table_ID)
			.getPO(getMMailText_Approved_ID(), get_TrxName());
	}

	/** Set M Mail Text Approved ID.
		@param MMailText_Approved_ID M Mail Text Approved ID
	*/
	public void setMMailText_Approved_ID (int MMailText_Approved_ID)
	{
		if (MMailText_Approved_ID < 1)
			set_Value (COLUMNNAME_MMailText_Approved_ID, null);
		else
			set_Value (COLUMNNAME_MMailText_Approved_ID, Integer.valueOf(MMailText_Approved_ID));
	}

	/** Get M Mail Text Approved ID.
		@return M Mail Text Approved ID	  */
	public int getMMailText_Approved_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MMailText_Approved_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_R_MailText getMMailText_Rejected() throws RuntimeException
	{
		return (org.compiere.model.I_R_MailText)MTable.get(getCtx(), org.compiere.model.I_R_MailText.Table_ID)
			.getPO(getMMailText_Rejected_ID(), get_TrxName());
	}

	/** Set M Mail Text Rejected ID.
		@param MMailText_Rejected_ID M Mail Text Rejected ID
	*/
	public void setMMailText_Rejected_ID (int MMailText_Rejected_ID)
	{
		if (MMailText_Rejected_ID < 1)
			set_Value (COLUMNNAME_MMailText_Rejected_ID, null);
		else
			set_Value (COLUMNNAME_MMailText_Rejected_ID, Integer.valueOf(MMailText_Rejected_ID));
	}

	/** Get M Mail Text Rejected ID.
		@return M Mail Text Rejected ID	  */
	public int getMMailText_Rejected_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MMailText_Rejected_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_R_MailText getMMailText_Request() throws RuntimeException
	{
		return (org.compiere.model.I_R_MailText)MTable.get(getCtx(), org.compiere.model.I_R_MailText.Table_ID)
			.getPO(getMMailText_Request_ID(), get_TrxName());
	}

	/** Set M Mail Text Request ID.
		@param MMailText_Request_ID M Mail Text Request ID
	*/
	public void setMMailText_Request_ID (int MMailText_Request_ID)
	{
		if (MMailText_Request_ID < 1)
			set_Value (COLUMNNAME_MMailText_Request_ID, null);
		else
			set_Value (COLUMNNAME_MMailText_Request_ID, Integer.valueOf(MMailText_Request_ID));
	}

	/** Get M Mail Text Request ID.
		@return M Mail Text Request ID	  */
	public int getMMailText_Request_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MMailText_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name Alphanumeric identifier of the entity
	*/
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Exec Approve = AE */
	public static final String NEXTACTIONONAPPROVE_ExecApprove = "AE";
	/** Approve/Do Not Approve = AP */
	public static final String NEXTACTIONONAPPROVE_ApproveDoNotApprove = "AP";
	/** Complete = CO */
	public static final String NEXTACTIONONAPPROVE_Complete = "CO";
	/** Final Approval/Do not Approve = FA */
	public static final String NEXTACTIONONAPPROVE_FinalApprovalDoNotApprove = "FA";
	/** Recommend = RE */
	public static final String NEXTACTIONONAPPROVE_Recommend = "RE";
	/** Submit to Manager Finance Consumables = SC */
	public static final String NEXTACTIONONAPPROVE_SubmitToManagerFinanceConsumables = "SC";
	/** Submit to SDL Finance Mgr = SD */
	public static final String NEXTACTIONONAPPROVE_SubmitToSDLFinanceMgr = "SD";
	/** Submit to Snr Mgr LP = SL */
	public static final String NEXTACTIONONAPPROVE_SubmitToSnrMgrLP = "SL";
	/** Submit to Snr Mgr Ops = SO */
	public static final String NEXTACTIONONAPPROVE_SubmitToSnrMgrOps = "SO";
	/** Submit to Snr Mgr Projects = SP */
	public static final String NEXTACTIONONAPPROVE_SubmitToSnrMgrProjects = "SP";
	/** Submit to Snr Mgr QA = SQ */
	public static final String NEXTACTIONONAPPROVE_SubmitToSnrMgrQA = "SQ";
	/** Submit to Recommender = SR */
	public static final String NEXTACTIONONAPPROVE_SubmitToRecommender = "SR";
	/** Submit to Snr Mgr SRU = SS */
	public static final String NEXTACTIONONAPPROVE_SubmitToSnrMgrSRU = "SS";
	/** Submit to Line Manager = SU */
	public static final String NEXTACTIONONAPPROVE_SubmitToLineManager = "SU";
	/** Set Next Action On Approve.
		@param NextActionOnApprove Next Action On Approve
	*/
	public void setNextActionOnApprove (String NextActionOnApprove)
	{

		set_Value (COLUMNNAME_NextActionOnApprove, NextActionOnApprove);
	}

	/** Get Next Action On Approve.
		@return Next Action On Approve	  */
	public String getNextActionOnApprove()
	{
		return (String)get_Value(COLUMNNAME_NextActionOnApprove);
	}

	/** Exec Approve = AE */
	public static final String NEXTACTIONONREJECT_ExecApprove = "AE";
	/** Approve/Do Not Approve = AP */
	public static final String NEXTACTIONONREJECT_ApproveDoNotApprove = "AP";
	/** Complete = CO */
	public static final String NEXTACTIONONREJECT_Complete = "CO";
	/** Final Approval/Do not Approve = FA */
	public static final String NEXTACTIONONREJECT_FinalApprovalDoNotApprove = "FA";
	/** Recommend = RE */
	public static final String NEXTACTIONONREJECT_Recommend = "RE";
	/** Submit to Manager Finance Consumables = SC */
	public static final String NEXTACTIONONREJECT_SubmitToManagerFinanceConsumables = "SC";
	/** Submit to SDL Finance Mgr = SD */
	public static final String NEXTACTIONONREJECT_SubmitToSDLFinanceMgr = "SD";
	/** Submit to Snr Mgr LP = SL */
	public static final String NEXTACTIONONREJECT_SubmitToSnrMgrLP = "SL";
	/** Submit to Snr Mgr Ops = SO */
	public static final String NEXTACTIONONREJECT_SubmitToSnrMgrOps = "SO";
	/** Submit to Snr Mgr Projects = SP */
	public static final String NEXTACTIONONREJECT_SubmitToSnrMgrProjects = "SP";
	/** Submit to Snr Mgr QA = SQ */
	public static final String NEXTACTIONONREJECT_SubmitToSnrMgrQA = "SQ";
	/** Submit to Recommender = SR */
	public static final String NEXTACTIONONREJECT_SubmitToRecommender = "SR";
	/** Submit to Snr Mgr SRU = SS */
	public static final String NEXTACTIONONREJECT_SubmitToSnrMgrSRU = "SS";
	/** Submit to Line Manager = SU */
	public static final String NEXTACTIONONREJECT_SubmitToLineManager = "SU";
	/** Set Next Action On Reject.
		@param NextActionOnReject Next Action On Reject
	*/
	public void setNextActionOnReject (String NextActionOnReject)
	{

		set_Value (COLUMNNAME_NextActionOnReject, NextActionOnReject);
	}

	/** Get Next Action On Reject.
		@return Next Action On Reject	  */
	public String getNextActionOnReject()
	{
		return (String)get_Value(COLUMNNAME_NextActionOnReject);
	}

	/** Approved By Manager Finance Consumables = AC */
	public static final String NEXTSTATUSONAPPROVE_ApprovedByManagerFinanceConsumables = "AC";
	/** Approved = AP */
	public static final String NEXTSTATUSONAPPROVE_Approved = "AP";
	/** Completed = CO */
	public static final String NEXTSTATUSONAPPROVE_Completed = "CO";
	/** Draft = DR */
	public static final String NEXTSTATUSONAPPROVE_Draft = "DR";
	/** In Progress = IP */
	public static final String NEXTSTATUSONAPPROVE_InProgress = "IP";
	/** Not Recommended By Senior Mgr SDR = N1 */
	public static final String NEXTSTATUSONAPPROVE_NotRecommendedBySeniorMgrSDR = "N1";
	/** Not Recommended By Senior Mgr Finance = N2 */
	public static final String NEXTSTATUSONAPPROVE_NotRecommendedBySeniorMgrFinance = "N2";
	/** Not Recommended By COO = N3 */
	public static final String NEXTSTATUSONAPPROVE_NotRecommendedByCOO = "N3";
	/** Not Recommended By CFO = N4 */
	public static final String NEXTSTATUSONAPPROVE_NotRecommendedByCFO = "N4";
	/** Not Recommended By CEO = N5 */
	public static final String NEXTSTATUSONAPPROVE_NotRecommendedByCEO = "N5";
	/** Not Approved by Snr Manager = NA */
	public static final String NEXTSTATUSONAPPROVE_NotApprovedBySnrManager = "NA";
	/** Not Approved By Manager Finance Consumables = NC */
	public static final String NEXTSTATUSONAPPROVE_NotApprovedByManagerFinanceConsumables = "NC";
	/** Not Approved By SDL Finance Mgr = ND */
	public static final String NEXTSTATUSONAPPROVE_NotApprovedBySDLFinanceMgr = "ND";
	/** Not Approved By IT Manager = NI */
	public static final String NEXTSTATUSONAPPROVE_NotApprovedByITManager = "NI";
	/** Not Approved by LM = NL */
	public static final String NEXTSTATUSONAPPROVE_NotApprovedByLM = "NL";
	/** Not Recommended = NR */
	public static final String NEXTSTATUSONAPPROVE_NotRecommended = "NR";
	/** Not Approved by Snr Admin Finance = NS */
	public static final String NEXTSTATUSONAPPROVE_NotApprovedBySnrAdminFinance = "NS";
	/** Recommended By Senior Mgr Finance = R1 */
	public static final String NEXTSTATUSONAPPROVE_RecommendedBySeniorMgrFinance = "R1";
	/** Recommended By COO = R2 */
	public static final String NEXTSTATUSONAPPROVE_RecommendedByCOO = "R2";
	/** Recommended = RC */
	public static final String NEXTSTATUSONAPPROVE_Recommended = "RC";
	/** Recommended By Senior Mgr SDR = RD */
	public static final String NEXTSTATUSONAPPROVE_RecommendedBySeniorMgrSDR = "RD";
	/** Submitted to Manager Finance Consumables = SC */
	public static final String NEXTSTATUSONAPPROVE_SubmittedToManagerFinanceConsumables = "SC";
	/** Submitted To SDL Finance Mgr = SD */
	public static final String NEXTSTATUSONAPPROVE_SubmittedToSDLFinanceMgr = "SD";
	/** Submitted To IT Manager = SI */
	public static final String NEXTSTATUSONAPPROVE_SubmittedToITManager = "SI";
	/** Submitted To IT Admin = ST */
	public static final String NEXTSTATUSONAPPROVE_SubmittedToITAdmin = "ST";
	/** Submitted = SU */
	public static final String NEXTSTATUSONAPPROVE_Submitted = "SU";
	/** Set Next Status On Approve.
		@param NextStatusOnApprove Next Status On Approve
	*/
	public void setNextStatusOnApprove (String NextStatusOnApprove)
	{

		set_Value (COLUMNNAME_NextStatusOnApprove, NextStatusOnApprove);
	}

	/** Get Next Status On Approve.
		@return Next Status On Approve	  */
	public String getNextStatusOnApprove()
	{
		return (String)get_Value(COLUMNNAME_NextStatusOnApprove);
	}

	/** Approved By Manager Finance Consumables = AC */
	public static final String NEXTSTATUSONREJECT_ApprovedByManagerFinanceConsumables = "AC";
	/** Approved = AP */
	public static final String NEXTSTATUSONREJECT_Approved = "AP";
	/** Completed = CO */
	public static final String NEXTSTATUSONREJECT_Completed = "CO";
	/** Draft = DR */
	public static final String NEXTSTATUSONREJECT_Draft = "DR";
	/** In Progress = IP */
	public static final String NEXTSTATUSONREJECT_InProgress = "IP";
	/** Not Recommended By Senior Mgr SDR = N1 */
	public static final String NEXTSTATUSONREJECT_NotRecommendedBySeniorMgrSDR = "N1";
	/** Not Recommended By Senior Mgr Finance = N2 */
	public static final String NEXTSTATUSONREJECT_NotRecommendedBySeniorMgrFinance = "N2";
	/** Not Recommended By COO = N3 */
	public static final String NEXTSTATUSONREJECT_NotRecommendedByCOO = "N3";
	/** Not Recommended By CFO = N4 */
	public static final String NEXTSTATUSONREJECT_NotRecommendedByCFO = "N4";
	/** Not Recommended By CEO = N5 */
	public static final String NEXTSTATUSONREJECT_NotRecommendedByCEO = "N5";
	/** Not Approved by Snr Manager = NA */
	public static final String NEXTSTATUSONREJECT_NotApprovedBySnrManager = "NA";
	/** Not Approved By Manager Finance Consumables = NC */
	public static final String NEXTSTATUSONREJECT_NotApprovedByManagerFinanceConsumables = "NC";
	/** Not Approved By SDL Finance Mgr = ND */
	public static final String NEXTSTATUSONREJECT_NotApprovedBySDLFinanceMgr = "ND";
	/** Not Approved By IT Manager = NI */
	public static final String NEXTSTATUSONREJECT_NotApprovedByITManager = "NI";
	/** Not Approved by LM = NL */
	public static final String NEXTSTATUSONREJECT_NotApprovedByLM = "NL";
	/** Not Recommended = NR */
	public static final String NEXTSTATUSONREJECT_NotRecommended = "NR";
	/** Not Approved by Snr Admin Finance = NS */
	public static final String NEXTSTATUSONREJECT_NotApprovedBySnrAdminFinance = "NS";
	/** Recommended By Senior Mgr Finance = R1 */
	public static final String NEXTSTATUSONREJECT_RecommendedBySeniorMgrFinance = "R1";
	/** Recommended By COO = R2 */
	public static final String NEXTSTATUSONREJECT_RecommendedByCOO = "R2";
	/** Recommended = RC */
	public static final String NEXTSTATUSONREJECT_Recommended = "RC";
	/** Recommended By Senior Mgr SDR = RD */
	public static final String NEXTSTATUSONREJECT_RecommendedBySeniorMgrSDR = "RD";
	/** Submitted to Manager Finance Consumables = SC */
	public static final String NEXTSTATUSONREJECT_SubmittedToManagerFinanceConsumables = "SC";
	/** Submitted To SDL Finance Mgr = SD */
	public static final String NEXTSTATUSONREJECT_SubmittedToSDLFinanceMgr = "SD";
	/** Submitted To IT Manager = SI */
	public static final String NEXTSTATUSONREJECT_SubmittedToITManager = "SI";
	/** Submitted To IT Admin = ST */
	public static final String NEXTSTATUSONREJECT_SubmittedToITAdmin = "ST";
	/** Submitted = SU */
	public static final String NEXTSTATUSONREJECT_Submitted = "SU";
	/** Set Next Status On Reject.
		@param NextStatusOnReject Next Status On Reject
	*/
	public void setNextStatusOnReject (String NextStatusOnReject)
	{

		set_Value (COLUMNNAME_NextStatusOnReject, NextStatusOnReject);
	}

	/** Get Next Status On Reject.
		@return Next Status On Reject	  */
	public String getNextStatusOnReject()
	{
		return (String)get_Value(COLUMNNAME_NextStatusOnReject);
	}

	/** Set Sequence.
		@param SeqNo Method of ordering records; lowest number comes first
	*/
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Exec Approve = AE */
	public static final String SETDOCACTION_ExecApprove = "AE";
	/** Approve/Do Not Approve = AP */
	public static final String SETDOCACTION_ApproveDoNotApprove = "AP";
	/** Complete = CO */
	public static final String SETDOCACTION_Complete = "CO";
	/** Final Approval/Do not Approve = FA */
	public static final String SETDOCACTION_FinalApprovalDoNotApprove = "FA";
	/** Recommend = RE */
	public static final String SETDOCACTION_Recommend = "RE";
	/** Submit to Manager Finance Consumables = SC */
	public static final String SETDOCACTION_SubmitToManagerFinanceConsumables = "SC";
	/** Submit to SDL Finance Mgr = SD */
	public static final String SETDOCACTION_SubmitToSDLFinanceMgr = "SD";
	/** Submit to Snr Mgr LP = SL */
	public static final String SETDOCACTION_SubmitToSnrMgrLP = "SL";
	/** Submit to Snr Mgr Ops = SO */
	public static final String SETDOCACTION_SubmitToSnrMgrOps = "SO";
	/** Submit to Snr Mgr Projects = SP */
	public static final String SETDOCACTION_SubmitToSnrMgrProjects = "SP";
	/** Submit to Snr Mgr QA = SQ */
	public static final String SETDOCACTION_SubmitToSnrMgrQA = "SQ";
	/** Submit to Recommender = SR */
	public static final String SETDOCACTION_SubmitToRecommender = "SR";
	/** Submit to Snr Mgr SRU = SS */
	public static final String SETDOCACTION_SubmitToSnrMgrSRU = "SS";
	/** Submit to Line Manager = SU */
	public static final String SETDOCACTION_SubmitToLineManager = "SU";
	/** Set Set Doc Action.
		@param SetDocAction Set Doc Action
	*/
	public void setSetDocAction (String SetDocAction)
	{

		set_Value (COLUMNNAME_SetDocAction, SetDocAction);
	}

	/** Get Set Doc Action.
		@return Set Doc Action	  */
	public String getSetDocAction()
	{
		return (String)get_Value(COLUMNNAME_SetDocAction);
	}

	public org.compiere.model.I_AD_Column getZZ_Approved_TS_COL() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_ID)
			.getPO(getZZ_Approved_TS_COL_ID(), get_TrxName());
	}

	/** Set Zz Approved Ts Col Id.
		@param ZZ_Approved_TS_COL_ID Zz Approved Ts Col Id
	*/
	public void setZZ_Approved_TS_COL_ID (int ZZ_Approved_TS_COL_ID)
	{
		if (ZZ_Approved_TS_COL_ID < 1)
			set_Value (COLUMNNAME_ZZ_Approved_TS_COL_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Approved_TS_COL_ID, Integer.valueOf(ZZ_Approved_TS_COL_ID));
	}

	/** Get Zz Approved Ts Col Id.
		@return Zz Approved Ts Col Id	  */
	public int getZZ_Approved_TS_COL_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Approved_TS_COL_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Column getZZ_Approved_User_COL() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_ID)
			.getPO(getZZ_Approved_User_COL_ID(), get_TrxName());
	}

	/** Set Approved User Col Id.
		@param ZZ_Approved_User_COL_ID Approved User Col Id
	*/
	public void setZZ_Approved_User_COL_ID (int ZZ_Approved_User_COL_ID)
	{
		if (ZZ_Approved_User_COL_ID < 1)
			set_Value (COLUMNNAME_ZZ_Approved_User_COL_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Approved_User_COL_ID, Integer.valueOf(ZZ_Approved_User_COL_ID));
	}

	/** Get Approved User Col Id.
		@return Approved User Col Id	  */
	public int getZZ_Approved_User_COL_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Approved_User_COL_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Column getZZ_Rejected_TS_COL() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_ID)
			.getPO(getZZ_Rejected_TS_COL_ID(), get_TrxName());
	}

	/** Set Zz Rejected Ts Col Id.
		@param ZZ_Rejected_TS_COL_ID Zz Rejected Ts Col Id
	*/
	public void setZZ_Rejected_TS_COL_ID (int ZZ_Rejected_TS_COL_ID)
	{
		if (ZZ_Rejected_TS_COL_ID < 1)
			set_Value (COLUMNNAME_ZZ_Rejected_TS_COL_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Rejected_TS_COL_ID, Integer.valueOf(ZZ_Rejected_TS_COL_ID));
	}

	/** Get Zz Rejected Ts Col Id.
		@return Zz Rejected Ts Col Id	  */
	public int getZZ_Rejected_TS_COL_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Rejected_TS_COL_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Column getZZ_Rejected_User_COL() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_ID)
			.getPO(getZZ_Rejected_User_COL_ID(), get_TrxName());
	}

	/** Set Zz Rejected User Col Id.
		@param ZZ_Rejected_User_COL_ID Zz Rejected User Col Id
	*/
	public void setZZ_Rejected_User_COL_ID (int ZZ_Rejected_User_COL_ID)
	{
		if (ZZ_Rejected_User_COL_ID < 1)
			set_Value (COLUMNNAME_ZZ_Rejected_User_COL_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Rejected_User_COL_ID, Integer.valueOf(ZZ_Rejected_User_COL_ID));
	}

	/** Get Zz Rejected User Col Id.
		@return Zz Rejected User Col Id	  */
	public int getZZ_Rejected_User_COL_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Rejected_User_COL_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Column getZZ_Specific_Responsible_Col() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_ID)
			.getPO(getZZ_Specific_Responsible_Col_ID(), get_TrxName());
	}

	/** Set Specific Responsible Col ID.
		@param ZZ_Specific_Responsible_Col_ID Specific Responsible Col ID
	*/
	public void setZZ_Specific_Responsible_Col_ID (int ZZ_Specific_Responsible_Col_ID)
	{
		if (ZZ_Specific_Responsible_Col_ID < 1)
			set_Value (COLUMNNAME_ZZ_Specific_Responsible_Col_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Specific_Responsible_Col_ID, Integer.valueOf(ZZ_Specific_Responsible_Col_ID));
	}

	/** Get Specific Responsible Col ID.
		@return Specific Responsible Col ID	  */
	public int getZZ_Specific_Responsible_Col_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Specific_Responsible_Col_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_WF_Header getZZ_WF_Header() throws RuntimeException
	{
		return (I_ZZ_WF_Header)MTable.get(getCtx(), I_ZZ_WF_Header.Table_ID)
			.getPO(getZZ_WF_Header_ID(), get_TrxName());
	}

	/** Set WF Header.
		@param ZZ_WF_Header_ID WF Header
	*/
	public void setZZ_WF_Header_ID (int ZZ_WF_Header_ID)
	{
		if (ZZ_WF_Header_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Header_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Header_ID, Integer.valueOf(ZZ_WF_Header_ID));
	}

	/** Get WF Header.
		@return WF Header	  */
	public int getZZ_WF_Header_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WF_Header_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_WF_Lines.
		@param ZZ_WF_Lines_ID ZZ_WF_Lines
	*/
	public void setZZ_WF_Lines_ID (int ZZ_WF_Lines_ID)
	{
		if (ZZ_WF_Lines_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Lines_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Lines_ID, Integer.valueOf(ZZ_WF_Lines_ID));
	}

	/** Get ZZ_WF_Lines.
		@return ZZ_WF_Lines	  */
	public int getZZ_WF_Lines_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WF_Lines_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}