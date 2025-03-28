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

/** Generated Model for ZZ_Petty_Cash_Advance_Line
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Petty_Cash_Advance_Line")
public class X_ZZ_Petty_Cash_Advance_Line extends PO implements I_ZZ_Petty_Cash_Advance_Line, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250328L;

    /** Standard Constructor */
    public X_ZZ_Petty_Cash_Advance_Line (Properties ctx, int ZZ_Petty_Cash_Advance_Line_ID, String trxName)
    {
      super (ctx, ZZ_Petty_Cash_Advance_Line_ID, trxName);
      /** if (ZZ_Petty_Cash_Advance_Line_ID == 0)
        {
			setZZ_Petty_Cash_Advance_Line_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Petty_Cash_Advance_Line (Properties ctx, int ZZ_Petty_Cash_Advance_Line_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Petty_Cash_Advance_Line_ID, trxName, virtualColumns);
      /** if (ZZ_Petty_Cash_Advance_Line_ID == 0)
        {
			setZZ_Petty_Cash_Advance_Line_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Petty_Cash_Advance_Line (Properties ctx, String ZZ_Petty_Cash_Advance_Line_UU, String trxName)
    {
      super (ctx, ZZ_Petty_Cash_Advance_Line_UU, trxName);
      /** if (ZZ_Petty_Cash_Advance_Line_UU == null)
        {
			setZZ_Petty_Cash_Advance_Line_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Petty_Cash_Advance_Line (Properties ctx, String ZZ_Petty_Cash_Advance_Line_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Petty_Cash_Advance_Line_UU, trxName, virtualColumns);
      /** if (ZZ_Petty_Cash_Advance_Line_UU == null)
        {
			setZZ_Petty_Cash_Advance_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_Petty_Cash_Advance_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_Petty_Cash_Advance_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount Amount in a defined currency
	*/
	public void setAmount (BigDecimal Amount)
	{
		set_ValueNoCheck (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
	{
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_ID)
			.getPO(getC_Charge_ID(), get_TrxName());
	}

	/** Set Charge.
		@param C_Charge_ID Additional document charges
	*/
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_Petty_Cash_Advance_Hdr getZZ_Petty_Cash_Advance_Hdr() throws RuntimeException
	{
		return (I_ZZ_Petty_Cash_Advance_Hdr)MTable.get(getCtx(), I_ZZ_Petty_Cash_Advance_Hdr.Table_ID)
			.getPO(getZZ_Petty_Cash_Advance_Hdr_ID(), get_TrxName());
	}

	/** Set Petty Cash Advance.
		@param ZZ_Petty_Cash_Advance_Hdr_ID Petty Cash Advance
	*/
	public void setZZ_Petty_Cash_Advance_Hdr_ID (int ZZ_Petty_Cash_Advance_Hdr_ID)
	{
		if (ZZ_Petty_Cash_Advance_Hdr_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Petty_Cash_Advance_Hdr_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Petty_Cash_Advance_Hdr_ID, Integer.valueOf(ZZ_Petty_Cash_Advance_Hdr_ID));
	}

	/** Get Petty Cash Advance.
		@return Petty Cash Advance	  */
	public int getZZ_Petty_Cash_Advance_Hdr_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Petty_Cash_Advance_Hdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Petty Cash Advance.
		@param ZZ_Petty_Cash_Advance_Line_ID Petty Cash Advance
	*/
	public void setZZ_Petty_Cash_Advance_Line_ID (int ZZ_Petty_Cash_Advance_Line_ID)
	{
		if (ZZ_Petty_Cash_Advance_Line_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Petty_Cash_Advance_Line_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Petty_Cash_Advance_Line_ID, Integer.valueOf(ZZ_Petty_Cash_Advance_Line_ID));
	}

	/** Get Petty Cash Advance.
		@return Petty Cash Advance	  */
	public int getZZ_Petty_Cash_Advance_Line_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Petty_Cash_Advance_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_Petty_Cash_Advance_Line_UU.
		@param ZZ_Petty_Cash_Advance_Line_UU ZZ_Petty_Cash_Advance_Line_UU
	*/
	public void setZZ_Petty_Cash_Advance_Line_UU (String ZZ_Petty_Cash_Advance_Line_UU)
	{
		set_Value (COLUMNNAME_ZZ_Petty_Cash_Advance_Line_UU, ZZ_Petty_Cash_Advance_Line_UU);
	}

	/** Get ZZ_Petty_Cash_Advance_Line_UU.
		@return ZZ_Petty_Cash_Advance_Line_UU	  */
	public String getZZ_Petty_Cash_Advance_Line_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Petty_Cash_Advance_Line_UU);
	}

	/** Set Motivation.
		@param ZZ_Petty_Cash_Motivation Motivation
	*/
	public void setZZ_Petty_Cash_Motivation (String ZZ_Petty_Cash_Motivation)
	{
		set_Value (COLUMNNAME_ZZ_Petty_Cash_Motivation, ZZ_Petty_Cash_Motivation);
	}

	/** Get Motivation.
		@return Motivation	  */
	public String getZZ_Petty_Cash_Motivation()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Petty_Cash_Motivation);
	}
}