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

/** Generated Model for ZZ_SDR_Temp_Org
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_SDR_Temp_Org")
public class X_ZZ_SDR_Temp_Org extends PO implements I_ZZ_SDR_Temp_Org, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251203L;

    /** Standard Constructor */
    public X_ZZ_SDR_Temp_Org (Properties ctx, int ZZ_SDR_Temp_Org_ID, String trxName)
    {
      super (ctx, ZZ_SDR_Temp_Org_ID, trxName);
      /** if (ZZ_SDR_Temp_Org_ID == 0)
        {
			setName (null);
			setZZ_SDR_Temp_Org_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_SDR_Temp_Org (Properties ctx, int ZZ_SDR_Temp_Org_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_SDR_Temp_Org_ID, trxName, virtualColumns);
      /** if (ZZ_SDR_Temp_Org_ID == 0)
        {
			setName (null);
			setZZ_SDR_Temp_Org_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_SDR_Temp_Org (Properties ctx, String ZZ_SDR_Temp_Org_UU, String trxName)
    {
      super (ctx, ZZ_SDR_Temp_Org_UU, trxName);
      /** if (ZZ_SDR_Temp_Org_UU == null)
        {
			setName (null);
			setZZ_SDR_Temp_Org_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_SDR_Temp_Org (Properties ctx, String ZZ_SDR_Temp_Org_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_SDR_Temp_Org_UU, trxName, virtualColumns);
      /** if (ZZ_SDR_Temp_Org_UU == null)
        {
			setName (null);
			setZZ_SDR_Temp_Org_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_SDR_Temp_Org (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_SDR_Temp_Org[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Cellphonenumber.
		@param Cellphonenumber Cellphonenumber
	*/
	public void setCellphonenumber (String Cellphonenumber)
	{
		set_Value (COLUMNNAME_Cellphonenumber, Cellphonenumber);
	}

	/** Get Cellphonenumber.
		@return Cellphonenumber	  */
	public String getCellphonenumber()
	{
		return (String)get_Value(COLUMNNAME_Cellphonenumber);
	}

	/** Set Contact Name.
		@param ContactName Business Partner Contact Name
	*/
	public void setContactName (String ContactName)
	{
		set_ValueNoCheck (COLUMNNAME_ContactName, ContactName);
	}

	/** Get Contact Name.
		@return Business Partner Contact Name
	  */
	public String getContactName()
	{
		return (String)get_Value(COLUMNNAME_ContactName);
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

	/** Set EMail Address.
		@param EMail Electronic Mail Address
	*/
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail()
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Comment/Help.
		@param Help Comment or Hint
	*/
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp()
	{
		return (String)get_Value(COLUMNNAME_Help);
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

	/** Set Search Key.
		@param Value Search key for the record in the format required - must be unique
	*/
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue()
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Landline No.
		@param ZZ_Landline_No Landline No
	*/
	public void setZZ_Landline_No (String ZZ_Landline_No)
	{
		set_Value (COLUMNNAME_ZZ_Landline_No, ZZ_Landline_No);
	}

	/** Get Landline No.
		@return Landline No	  */
	public String getZZ_Landline_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Landline_No);
	}

	/** Set Organisation Name.
		@param ZZ_Organisation_Name Organisation Name
	*/
	public void setZZ_Organisation_Name (String ZZ_Organisation_Name)
	{
		set_Value (COLUMNNAME_ZZ_Organisation_Name, ZZ_Organisation_Name);
	}

	/** Get Organisation Name.
		@return Organisation Name	  */
	public String getZZ_Organisation_Name()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Organisation_Name);
	}

	/** Set Organisation Reg No.
		@param ZZ_Organisation_Reg_No Organisation Reg No
	*/
	public void setZZ_Organisation_Reg_No (String ZZ_Organisation_Reg_No)
	{
		set_Value (COLUMNNAME_ZZ_Organisation_Reg_No, ZZ_Organisation_Reg_No);
	}

	/** Get Organisation Reg No.
		@return Organisation Reg No	  */
	public String getZZ_Organisation_Reg_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Organisation_Reg_No);
	}

	/** Set Unconfirmed SDL No.
		@param ZZ_SDL_No Unconfirmed SDL No
	*/
	public void setZZ_SDL_No (String ZZ_SDL_No)
	{
		set_Value (COLUMNNAME_ZZ_SDL_No, ZZ_SDL_No);
	}

	/** Get Unconfirmed SDL No.
		@return Unconfirmed SDL No	  */
	public String getZZ_SDL_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_SDL_No);
	}

	/** Set SDR Temporary Organisation.
		@param ZZ_SDR_Temp_Org_ID SDR Temporary Organisation
	*/
	public void setZZ_SDR_Temp_Org_ID (int ZZ_SDR_Temp_Org_ID)
	{
		if (ZZ_SDR_Temp_Org_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_SDR_Temp_Org_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_SDR_Temp_Org_ID, Integer.valueOf(ZZ_SDR_Temp_Org_ID));
	}

	/** Get SDR Temporary Organisation.
		@return SDR Temporary Organisation	  */
	public int getZZ_SDR_Temp_Org_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_SDR_Temp_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_SDR_Temp_Org_UU.
		@param ZZ_SDR_Temp_Org_UU ZZ_SDR_Temp_Org_UU
	*/
	public void setZZ_SDR_Temp_Org_UU (String ZZ_SDR_Temp_Org_UU)
	{
		set_Value (COLUMNNAME_ZZ_SDR_Temp_Org_UU, ZZ_SDR_Temp_Org_UU);
	}

	/** Get ZZ_SDR_Temp_Org_UU.
		@return ZZ_SDR_Temp_Org_UU	  */
	public String getZZ_SDR_Temp_Org_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_SDR_Temp_Org_UU);
	}

	/** Set Trading As.
		@param ZZ_TradingAs Trading As
	*/
	public void setZZ_TradingAs (String ZZ_TradingAs)
	{
		set_Value (COLUMNNAME_ZZ_TradingAs, ZZ_TradingAs);
	}

	/** Get Trading As.
		@return Trading As	  */
	public String getZZ_TradingAs()
	{
		return (String)get_Value(COLUMNNAME_ZZ_TradingAs);
	}
}