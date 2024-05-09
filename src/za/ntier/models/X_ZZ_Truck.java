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

/** Generated Model for ZZ_Truck
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Truck")
public class X_ZZ_Truck extends PO implements I_ZZ_Truck, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20240508L;

    /** Standard Constructor */
    public X_ZZ_Truck (Properties ctx, int ZZ_Truck_ID, String trxName)
    {
      super (ctx, ZZ_Truck_ID, trxName);
      /** if (ZZ_Truck_ID == 0)
        {
			setName (null);
			setZZ_Truck_ID (0);
			setZZ_Truck_Type (null);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Truck (Properties ctx, int ZZ_Truck_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Truck_ID, trxName, virtualColumns);
      /** if (ZZ_Truck_ID == 0)
        {
			setName (null);
			setZZ_Truck_ID (0);
			setZZ_Truck_Type (null);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Truck (Properties ctx, String ZZ_Truck_UU, String trxName)
    {
      super (ctx, ZZ_Truck_UU, trxName);
      /** if (ZZ_Truck_UU == null)
        {
			setName (null);
			setZZ_Truck_ID (0);
			setZZ_Truck_Type (null);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Truck (Properties ctx, String ZZ_Truck_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Truck_UU, trxName, virtualColumns);
      /** if (ZZ_Truck_UU == null)
        {
			setName (null);
			setZZ_Truck_ID (0);
			setZZ_Truck_Type (null);
        } */
    }

    /** Load Constructor */
    public X_ZZ_Truck (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_Truck[")
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

	/** Set Fleet No.
		@param ZZ_Fleet_No Fleet No
	*/
	public void setZZ_Fleet_No (String ZZ_Fleet_No)
	{
		set_Value (COLUMNNAME_ZZ_Fleet_No, ZZ_Fleet_No);
	}

	/** Get Fleet No.
		@return Fleet No	  */
	public String getZZ_Fleet_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Fleet_No);
	}

	/** Set Registration No.
		@param ZZ_Registration_No Unique license plate number
	*/
	public void setZZ_Registration_No (String ZZ_Registration_No)
	{
		set_Value (COLUMNNAME_ZZ_Registration_No, ZZ_Registration_No);
	}

	/** Get Registration No.
		@return Unique license plate number
	  */
	public String getZZ_Registration_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Registration_No);
	}

	/** Set Truck.
		@param ZZ_Truck_ID Horse or Trailer
	*/
	public void setZZ_Truck_ID (int ZZ_Truck_ID)
	{
		if (ZZ_Truck_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Truck_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Truck_ID, Integer.valueOf(ZZ_Truck_ID));
	}

	/** Get Truck.
		@return Horse or Trailer
	  */
	public int getZZ_Truck_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Truck_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Horse = H */
	public static final String ZZ_TRUCK_TYPE_Horse = "H";
	/** Trailer = T */
	public static final String ZZ_TRUCK_TYPE_Trailer = "T";
	/** Set Truck Type.
		@param ZZ_Truck_Type Truck Type
	*/
	public void setZZ_Truck_Type (String ZZ_Truck_Type)
	{

		set_Value (COLUMNNAME_ZZ_Truck_Type, ZZ_Truck_Type);
	}

	/** Get Truck Type.
		@return Truck Type	  */
	public String getZZ_Truck_Type()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Truck_Type);
	}

	/** Set ZZ_Truck_UU.
		@param ZZ_Truck_UU ZZ_Truck_UU
	*/
	public void setZZ_Truck_UU (String ZZ_Truck_UU)
	{
		set_Value (COLUMNNAME_ZZ_Truck_UU, ZZ_Truck_UU);
	}

	/** Get ZZ_Truck_UU.
		@return ZZ_Truck_UU	  */
	public String getZZ_Truck_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Truck_UU);
	}
}