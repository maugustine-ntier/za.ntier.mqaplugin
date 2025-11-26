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

/** Generated Model for ZZ_LI_Nationality
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_LI_Nationality")
public class X_ZZ_LI_Nationality extends PO implements I_ZZ_LI_Nationality, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251126L;

    /** Standard Constructor */
    public X_ZZ_LI_Nationality (Properties ctx, int ZZ_LI_Nationality_ID, String trxName)
    {
      super (ctx, ZZ_LI_Nationality_ID, trxName);
      /** if (ZZ_LI_Nationality_ID == 0)
        {
			setName (null);
			setZZ_LI_Nationality_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_LI_Nationality (Properties ctx, int ZZ_LI_Nationality_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_LI_Nationality_ID, trxName, virtualColumns);
      /** if (ZZ_LI_Nationality_ID == 0)
        {
			setName (null);
			setZZ_LI_Nationality_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_LI_Nationality (Properties ctx, String ZZ_LI_Nationality_UU, String trxName)
    {
      super (ctx, ZZ_LI_Nationality_UU, trxName);
      /** if (ZZ_LI_Nationality_UU == null)
        {
			setName (null);
			setZZ_LI_Nationality_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_LI_Nationality (Properties ctx, String ZZ_LI_Nationality_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_LI_Nationality_UU, trxName, virtualColumns);
      /** if (ZZ_LI_Nationality_UU == null)
        {
			setName (null);
			setZZ_LI_Nationality_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_LI_Nationality (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_LI_Nationality[")
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

	/** Set Nationality.
		@param ZZ_LI_Nationality_ID Nationality
	*/
	public void setZZ_LI_Nationality_ID (int ZZ_LI_Nationality_ID)
	{
		if (ZZ_LI_Nationality_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_Nationality_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_Nationality_ID, Integer.valueOf(ZZ_LI_Nationality_ID));
	}

	/** Get Nationality.
		@return Nationality
	  */
	public int getZZ_LI_Nationality_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_LI_Nationality_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_LI_Nationality_UU.
		@param ZZ_LI_Nationality_UU ZZ_LI_Nationality_UU
	*/
	public void setZZ_LI_Nationality_UU (String ZZ_LI_Nationality_UU)
	{
		set_Value (COLUMNNAME_ZZ_LI_Nationality_UU, ZZ_LI_Nationality_UU);
	}

	/** Get ZZ_LI_Nationality_UU.
		@return ZZ_LI_Nationality_UU	  */
	public String getZZ_LI_Nationality_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_LI_Nationality_UU);
	}
}