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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for ZZ_Truck_List
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Truck_List")
public class X_ZZ_Truck_List extends PO implements I_ZZ_Truck_List, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20240508L;

    /** Standard Constructor */
    public X_ZZ_Truck_List (Properties ctx, int ZZ_Truck_List_ID, String trxName)
    {
      super (ctx, ZZ_Truck_List_ID, trxName);
      /** if (ZZ_Truck_List_ID == 0)
        {
			setZZ_Truck_List_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Truck_List (Properties ctx, int ZZ_Truck_List_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Truck_List_ID, trxName, virtualColumns);
      /** if (ZZ_Truck_List_ID == 0)
        {
			setZZ_Truck_List_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Truck_List (Properties ctx, String ZZ_Truck_List_UU, String trxName)
    {
      super (ctx, ZZ_Truck_List_UU, trxName);
      /** if (ZZ_Truck_List_UU == null)
        {
			setZZ_Truck_List_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Truck_List (Properties ctx, String ZZ_Truck_List_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Truck_List_UU, trxName, virtualColumns);
      /** if (ZZ_Truck_List_UU == null)
        {
			setZZ_Truck_List_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_Truck_List (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_Truck_List[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_ZZ_Driver getZZ_Driver() throws RuntimeException
	{
		return (I_ZZ_Driver)MTable.get(getCtx(), I_ZZ_Driver.Table_ID)
			.getPO(getZZ_Driver_ID(), get_TrxName());
	}

	/** Set Driver.
		@param ZZ_Driver_ID Driver table for Transporter window
	*/
	public void setZZ_Driver_ID (int ZZ_Driver_ID)
	{
		if (ZZ_Driver_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Driver_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Driver_ID, Integer.valueOf(ZZ_Driver_ID));
	}

	/** Get Driver.
		@return Driver table for Transporter window
	  */
	public int getZZ_Driver_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Driver_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_ZZ_Truck getZZ_Horse() throws RuntimeException
	{
		return (I_ZZ_Truck)MTable.get(getCtx(), I_ZZ_Truck.Table_ID)
			.getPO(getZZ_Horse_ID(), get_TrxName());
	}

	/** Set Horse.
		@param ZZ_Horse_ID Horse
	*/
	public void setZZ_Horse_ID (int ZZ_Horse_ID)
	{
		if (ZZ_Horse_ID < 1)
			set_Value (COLUMNNAME_ZZ_Horse_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Horse_ID, Integer.valueOf(ZZ_Horse_ID));
	}

	/** Get Horse.
		@return Horse	  */
	public int getZZ_Horse_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Horse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set No Of Loads.
		@param ZZ_No_Of_Loads No Of Loads
	*/
	public void setZZ_No_Of_Loads (BigDecimal ZZ_No_Of_Loads)
	{
		set_Value (COLUMNNAME_ZZ_No_Of_Loads, ZZ_No_Of_Loads);
	}

	/** Get No Of Loads.
		@return No Of Loads	  */
	public BigDecimal getZZ_No_Of_Loads()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ZZ_No_Of_Loads);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_ZZ_Truck getZZ_Trailer1() throws RuntimeException
	{
		return (I_ZZ_Truck)MTable.get(getCtx(), I_ZZ_Truck.Table_ID)
			.getPO(getZZ_Trailer1_ID(), get_TrxName());
	}

	/** Set Trailer 1.
		@param ZZ_Trailer1_ID Trailer 1
	*/
	public void setZZ_Trailer1_ID (int ZZ_Trailer1_ID)
	{
		if (ZZ_Trailer1_ID < 1)
			set_Value (COLUMNNAME_ZZ_Trailer1_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Trailer1_ID, Integer.valueOf(ZZ_Trailer1_ID));
	}

	/** Get Trailer 1.
		@return Trailer 1	  */
	public int getZZ_Trailer1_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Trailer1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_Truck getZZ_Trailer2() throws RuntimeException
	{
		return (I_ZZ_Truck)MTable.get(getCtx(), I_ZZ_Truck.Table_ID)
			.getPO(getZZ_Trailer2_ID(), get_TrxName());
	}

	/** Set Trailer 2.
		@param ZZ_Trailer2_ID Trailer 2
	*/
	public void setZZ_Trailer2_ID (int ZZ_Trailer2_ID)
	{
		if (ZZ_Trailer2_ID < 1)
			set_Value (COLUMNNAME_ZZ_Trailer2_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Trailer2_ID, Integer.valueOf(ZZ_Trailer2_ID));
	}

	/** Get Trailer 2.
		@return Trailer 2	  */
	public int getZZ_Trailer2_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Trailer2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_Transporters getZZ_Transporters() throws RuntimeException
	{
		return (I_ZZ_Transporters)MTable.get(getCtx(), I_ZZ_Transporters.Table_ID)
			.getPO(getZZ_Transporters_ID(), get_TrxName());
	}

	/** Set Transporters.
		@param ZZ_Transporters_ID Transporters
	*/
	public void setZZ_Transporters_ID (int ZZ_Transporters_ID)
	{
		if (ZZ_Transporters_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Transporters_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Transporters_ID, Integer.valueOf(ZZ_Transporters_ID));
	}

	/** Get Transporters.
		@return Transporters	  */
	public int getZZ_Transporters_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Transporters_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Truck List.
		@param ZZ_Truck_List_ID Truck List
	*/
	public void setZZ_Truck_List_ID (int ZZ_Truck_List_ID)
	{
		if (ZZ_Truck_List_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Truck_List_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Truck_List_ID, Integer.valueOf(ZZ_Truck_List_ID));
	}

	/** Get Truck List.
		@return Truck List	  */
	public int getZZ_Truck_List_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Truck_List_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_Truck_List_UU.
		@param ZZ_Truck_List_UU ZZ_Truck_List_UU
	*/
	public void setZZ_Truck_List_UU (String ZZ_Truck_List_UU)
	{
		set_Value (COLUMNNAME_ZZ_Truck_List_UU, ZZ_Truck_List_UU);
	}

	/** Get ZZ_Truck_List_UU.
		@return ZZ_Truck_List_UU	  */
	public String getZZ_Truck_List_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Truck_List_UU);
	}
}