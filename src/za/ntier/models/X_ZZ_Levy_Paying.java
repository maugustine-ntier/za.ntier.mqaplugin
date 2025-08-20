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

/** Generated Model for ZZ_Levy_Paying
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Levy_Paying")
public class X_ZZ_Levy_Paying extends PO implements I_ZZ_Levy_Paying, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250819L;

    /** Standard Constructor */
    public X_ZZ_Levy_Paying (Properties ctx, int ZZ_Levy_Paying_ID, String trxName)
    {
      super (ctx, ZZ_Levy_Paying_ID, trxName);
      /** if (ZZ_Levy_Paying_ID == 0)
        {
			setName (null);
			setZZ_LevyPaying (false);
// N
			setZZ_Levy_Paying_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Levy_Paying (Properties ctx, int ZZ_Levy_Paying_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Levy_Paying_ID, trxName, virtualColumns);
      /** if (ZZ_Levy_Paying_ID == 0)
        {
			setName (null);
			setZZ_LevyPaying (false);
// N
			setZZ_Levy_Paying_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Levy_Paying (Properties ctx, String ZZ_Levy_Paying_UU, String trxName)
    {
      super (ctx, ZZ_Levy_Paying_UU, trxName);
      /** if (ZZ_Levy_Paying_UU == null)
        {
			setName (null);
			setZZ_LevyPaying (false);
// N
			setZZ_Levy_Paying_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Levy_Paying (Properties ctx, String ZZ_Levy_Paying_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Levy_Paying_UU, trxName, virtualColumns);
      /** if (ZZ_Levy_Paying_UU == null)
        {
			setName (null);
			setZZ_LevyPaying (false);
// N
			setZZ_Levy_Paying_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_Levy_Paying (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_Levy_Paying[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException
	{
		return (org.compiere.model.I_C_Year)MTable.get(getCtx(), org.compiere.model.I_C_Year.Table_ID)
			.getPO(getC_Year_ID(), get_TrxName());
	}

	/** Set Year.
		@param C_Year_ID Calendar Year
	*/
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Year_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Year.
		@return Calendar Year
	  */
	public int getC_Year_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
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

	/** Set LevyPaying.
		@param ZZ_LevyPaying LevyPaying
	*/
	public void setZZ_LevyPaying (boolean ZZ_LevyPaying)
	{
		set_Value (COLUMNNAME_ZZ_LevyPaying, Boolean.valueOf(ZZ_LevyPaying));
	}

	/** Get LevyPaying.
		@return LevyPaying	  */
	public boolean isZZ_LevyPaying()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_LevyPaying);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Levy_Paying.
		@param ZZ_Levy_Paying_ID Stores Levy Paying info on BP Window
	*/
	public void setZZ_Levy_Paying_ID (int ZZ_Levy_Paying_ID)
	{
		if (ZZ_Levy_Paying_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Levy_Paying_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Levy_Paying_ID, Integer.valueOf(ZZ_Levy_Paying_ID));
	}

	/** Get Levy_Paying.
		@return Stores Levy Paying info on BP Window
	  */
	public int getZZ_Levy_Paying_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Levy_Paying_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_Levy_Paying_UU.
		@param ZZ_Levy_Paying_UU ZZ_Levy_Paying_UU
	*/
	public void setZZ_Levy_Paying_UU (String ZZ_Levy_Paying_UU)
	{
		set_Value (COLUMNNAME_ZZ_Levy_Paying_UU, ZZ_Levy_Paying_UU);
	}

	/** Get ZZ_Levy_Paying_UU.
		@return ZZ_Levy_Paying_UU	  */
	public String getZZ_Levy_Paying_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Levy_Paying_UU);
	}
}