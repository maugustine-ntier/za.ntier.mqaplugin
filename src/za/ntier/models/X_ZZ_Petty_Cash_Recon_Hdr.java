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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for ZZ_Petty_Cash_Recon_Hdr
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Petty_Cash_Recon_Hdr")
public class X_ZZ_Petty_Cash_Recon_Hdr extends PO implements I_ZZ_Petty_Cash_Recon_Hdr, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250328L;

    /** Standard Constructor */
    public X_ZZ_Petty_Cash_Recon_Hdr (Properties ctx, int ZZ_Petty_Cash_Recon_Hdr_ID, String trxName)
    {
      super (ctx, ZZ_Petty_Cash_Recon_Hdr_ID, trxName);
      /** if (ZZ_Petty_Cash_Recon_Hdr_ID == 0)
        {
			setZZ_Petty_Cash_Recon_Hdr_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Petty_Cash_Recon_Hdr (Properties ctx, int ZZ_Petty_Cash_Recon_Hdr_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Petty_Cash_Recon_Hdr_ID, trxName, virtualColumns);
      /** if (ZZ_Petty_Cash_Recon_Hdr_ID == 0)
        {
			setZZ_Petty_Cash_Recon_Hdr_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Petty_Cash_Recon_Hdr (Properties ctx, String ZZ_Petty_Cash_Recon_Hdr_UU, String trxName)
    {
      super (ctx, ZZ_Petty_Cash_Recon_Hdr_UU, trxName);
      /** if (ZZ_Petty_Cash_Recon_Hdr_UU == null)
        {
			setZZ_Petty_Cash_Recon_Hdr_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Petty_Cash_Recon_Hdr (Properties ctx, String ZZ_Petty_Cash_Recon_Hdr_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Petty_Cash_Recon_Hdr_UU, trxName, virtualColumns);
      /** if (ZZ_Petty_Cash_Recon_Hdr_UU == null)
        {
			setZZ_Petty_Cash_Recon_Hdr_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_Petty_Cash_Recon_Hdr (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_Petty_Cash_Recon_Hdr[")
        .append(get_ID()).append("]");
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

	/** Set End Date.
		@param EndDate Last effective date (inclusive)
	*/
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Start Date.
		@param StartDate First effective day (inclusive)
	*/
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set Float.
		@param ZZ_Float_Amt Float
	*/
	public void setZZ_Float_Amt (BigDecimal ZZ_Float_Amt)
	{
		set_Value (COLUMNNAME_ZZ_Float_Amt, ZZ_Float_Amt);
	}

	/** Get Float.
		@return Float	  */
	public BigDecimal getZZ_Float_Amt()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ZZ_Float_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Petty Cash Recon.
		@param ZZ_Petty_Cash_Recon_Hdr_ID Petty Cash Recon
	*/
	public void setZZ_Petty_Cash_Recon_Hdr_ID (int ZZ_Petty_Cash_Recon_Hdr_ID)
	{
		if (ZZ_Petty_Cash_Recon_Hdr_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Petty_Cash_Recon_Hdr_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Petty_Cash_Recon_Hdr_ID, Integer.valueOf(ZZ_Petty_Cash_Recon_Hdr_ID));
	}

	/** Get Petty Cash Recon.
		@return Petty Cash Recon	  */
	public int getZZ_Petty_Cash_Recon_Hdr_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Petty_Cash_Recon_Hdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_Petty_Cash_Recon_Hdr_UU.
		@param ZZ_Petty_Cash_Recon_Hdr_UU ZZ_Petty_Cash_Recon_Hdr_UU
	*/
	public void setZZ_Petty_Cash_Recon_Hdr_UU (String ZZ_Petty_Cash_Recon_Hdr_UU)
	{
		set_Value (COLUMNNAME_ZZ_Petty_Cash_Recon_Hdr_UU, ZZ_Petty_Cash_Recon_Hdr_UU);
	}

	/** Get ZZ_Petty_Cash_Recon_Hdr_UU.
		@return ZZ_Petty_Cash_Recon_Hdr_UU	  */
	public String getZZ_Petty_Cash_Recon_Hdr_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Petty_Cash_Recon_Hdr_UU);
	}
}