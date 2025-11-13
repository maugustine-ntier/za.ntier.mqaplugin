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

/** Generated Model for ZZ_ATRQueryReason
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_ATRQueryReason")
public class X_ZZ_ATRQueryReason extends PO implements I_ZZ_ATRQueryReason, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251113L;

    /** Standard Constructor */
    public X_ZZ_ATRQueryReason (Properties ctx, int ZZ_ATRQueryReason_ID, String trxName)
    {
      super (ctx, ZZ_ATRQueryReason_ID, trxName);
      /** if (ZZ_ATRQueryReason_ID == 0)
        {
			setName (null);
			setZZ_ATRQueryReason_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_ATRQueryReason (Properties ctx, int ZZ_ATRQueryReason_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_ATRQueryReason_ID, trxName, virtualColumns);
      /** if (ZZ_ATRQueryReason_ID == 0)
        {
			setName (null);
			setZZ_ATRQueryReason_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_ATRQueryReason (Properties ctx, String ZZ_ATRQueryReason_UU, String trxName)
    {
      super (ctx, ZZ_ATRQueryReason_UU, trxName);
      /** if (ZZ_ATRQueryReason_UU == null)
        {
			setName (null);
			setZZ_ATRQueryReason_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_ATRQueryReason (Properties ctx, String ZZ_ATRQueryReason_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_ATRQueryReason_UU, trxName, virtualColumns);
      /** if (ZZ_ATRQueryReason_UU == null)
        {
			setName (null);
			setZZ_ATRQueryReason_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_ATRQueryReason (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_ATRQueryReason[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
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

	/** Set ATR Query Reason.
		@param ZZ_ATRQueryReason_ID ATR Query Reason
	*/
	public void setZZ_ATRQueryReason_ID (int ZZ_ATRQueryReason_ID)
	{
		if (ZZ_ATRQueryReason_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_ATRQueryReason_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_ATRQueryReason_ID, Integer.valueOf(ZZ_ATRQueryReason_ID));
	}

	/** Get ATR Query Reason.
		@return ATR Query Reason	  */
	public int getZZ_ATRQueryReason_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_ATRQueryReason_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_ATRQueryReason_UU.
		@param ZZ_ATRQueryReason_UU ZZ_ATRQueryReason_UU
	*/
	public void setZZ_ATRQueryReason_UU (String ZZ_ATRQueryReason_UU)
	{
		set_Value (COLUMNNAME_ZZ_ATRQueryReason_UU, ZZ_ATRQueryReason_UU);
	}

	/** Get ZZ_ATRQueryReason_UU.
		@return ZZ_ATRQueryReason_UU	  */
	public String getZZ_ATRQueryReason_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_ATRQueryReason_UU);
	}
}