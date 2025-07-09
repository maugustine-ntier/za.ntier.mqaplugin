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

/** Generated Model for ZZ_Roles
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Roles")
public class X_ZZ_Roles extends PO implements I_ZZ_Roles, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250708L;

    /** Standard Constructor */
    public X_ZZ_Roles (Properties ctx, int ZZ_Roles_ID, String trxName)
    {
      super (ctx, ZZ_Roles_ID, trxName);
      /** if (ZZ_Roles_ID == 0)
        {
			setName (null);
			setZZ_Roles_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Roles (Properties ctx, int ZZ_Roles_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Roles_ID, trxName, virtualColumns);
      /** if (ZZ_Roles_ID == 0)
        {
			setName (null);
			setZZ_Roles_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Roles (Properties ctx, String ZZ_Roles_UU, String trxName)
    {
      super (ctx, ZZ_Roles_UU, trxName);
      /** if (ZZ_Roles_UU == null)
        {
			setName (null);
			setZZ_Roles_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Roles (Properties ctx, String ZZ_Roles_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Roles_UU, trxName, virtualColumns);
      /** if (ZZ_Roles_UU == null)
        {
			setName (null);
			setZZ_Roles_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_Roles (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_Roles[")
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

	/** Set Roles.
		@param ZZ_Roles_ID MQA System Roles
	*/
	public void setZZ_Roles_ID (int ZZ_Roles_ID)
	{
		if (ZZ_Roles_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Roles_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Roles_ID, Integer.valueOf(ZZ_Roles_ID));
	}

	/** Get Roles.
		@return MQA System Roles
	  */
	public int getZZ_Roles_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Roles_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_Roles_UU.
		@param ZZ_Roles_UU ZZ_Roles_UU
	*/
	public void setZZ_Roles_UU (String ZZ_Roles_UU)
	{
		set_Value (COLUMNNAME_ZZ_Roles_UU, ZZ_Roles_UU);
	}

	/** Get ZZ_Roles_UU.
		@return ZZ_Roles_UU	  */
	public String getZZ_Roles_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Roles_UU);
	}
}