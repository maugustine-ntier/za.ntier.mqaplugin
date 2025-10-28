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

/** Generated Model for ZZ_WF_Header
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_WF_Header")
public class X_ZZ_WF_Header extends PO implements I_ZZ_WF_Header, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251028L;

    /** Standard Constructor */
    public X_ZZ_WF_Header (Properties ctx, int ZZ_WF_Header_ID, String trxName)
    {
      super (ctx, ZZ_WF_Header_ID, trxName);
      /** if (ZZ_WF_Header_ID == 0)
        {
			setAD_Table_ID (0);
			setName (null);
			setZZ_NotifyMode (null);
			setZZ_WF_Header_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Header (Properties ctx, int ZZ_WF_Header_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WF_Header_ID, trxName, virtualColumns);
      /** if (ZZ_WF_Header_ID == 0)
        {
			setAD_Table_ID (0);
			setName (null);
			setZZ_NotifyMode (null);
			setZZ_WF_Header_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Header (Properties ctx, String ZZ_WF_Header_UU, String trxName)
    {
      super (ctx, ZZ_WF_Header_UU, trxName);
      /** if (ZZ_WF_Header_UU == null)
        {
			setAD_Table_ID (0);
			setName (null);
			setZZ_NotifyMode (null);
			setZZ_WF_Header_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Header (Properties ctx, String ZZ_WF_Header_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WF_Header_UU, trxName, virtualColumns);
      /** if (ZZ_WF_Header_UU == null)
        {
			setAD_Table_ID (0);
			setName (null);
			setZZ_NotifyMode (null);
			setZZ_WF_Header_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_WF_Header (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System
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
      StringBuilder sb = new StringBuilder ("X_ZZ_WF_Header[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_ID)
			.getPO(getAD_Table_ID(), get_TrxName());
	}

	/** Set Table.
		@param AD_Table_ID Database Table information
	*/
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1)
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_R_MailText getMMailText_FinalApproved() throws RuntimeException
	{
		return (org.compiere.model.I_R_MailText)MTable.get(getCtx(), org.compiere.model.I_R_MailText.Table_ID)
			.getPO(getMMailText_FinalApproved_ID(), get_TrxName());
	}

	/** Set Mail Text Final Approve.
		@param MMailText_FinalApproved_ID Mail Text Final Approve
	*/
	public void setMMailText_FinalApproved_ID (int MMailText_FinalApproved_ID)
	{
		if (MMailText_FinalApproved_ID < 1)
			set_Value (COLUMNNAME_MMailText_FinalApproved_ID, null);
		else
			set_Value (COLUMNNAME_MMailText_FinalApproved_ID, Integer.valueOf(MMailText_FinalApproved_ID));
	}

	/** Get Mail Text Final Approve.
		@return Mail Text Final Approve	  */
	public int getMMailText_FinalApproved_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MMailText_FinalApproved_ID);
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

	/** Set Final Status.
		@param ZZ_FinalStatus Final Status
	*/
	public void setZZ_FinalStatus (String ZZ_FinalStatus)
	{
		set_Value (COLUMNNAME_ZZ_FinalStatus, ZZ_FinalStatus);
	}

	/** Get Final Status.
		@return Final Status	  */
	public String getZZ_FinalStatus()
	{
		return (String)get_Value(COLUMNNAME_ZZ_FinalStatus);
	}

	/** Set Notify Mode.
		@param ZZ_NotifyMode Notify Mode
	*/
	public void setZZ_NotifyMode (String ZZ_NotifyMode)
	{
		set_Value (COLUMNNAME_ZZ_NotifyMode, ZZ_NotifyMode);
	}

	/** Get Notify Mode.
		@return Notify Mode	  */
	public String getZZ_NotifyMode()
	{
		return (String)get_Value(COLUMNNAME_ZZ_NotifyMode);
	}

	/** Set Start Status.
		@param ZZ_StartStatus Start Status
	*/
	public void setZZ_StartStatus (String ZZ_StartStatus)
	{
		set_Value (COLUMNNAME_ZZ_StartStatus, ZZ_StartStatus);
	}

	/** Get Start Status.
		@return Start Status	  */
	public String getZZ_StartStatus()
	{
		return (String)get_Value(COLUMNNAME_ZZ_StartStatus);
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
}