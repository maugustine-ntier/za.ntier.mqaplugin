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
	private static final long serialVersionUID = 20251027L;

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
      StringBuilder sb = new StringBuilder ("X_ZZ_WF_Lines[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

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
			set_ValueNoCheck (COLUMNNAME_MMailText_Approved_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_MMailText_Approved_ID, Integer.valueOf(MMailText_Approved_ID));
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