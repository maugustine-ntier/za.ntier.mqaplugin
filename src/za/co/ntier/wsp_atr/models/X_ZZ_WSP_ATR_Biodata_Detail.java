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
package za.co.ntier.wsp_atr.models;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for ZZ_WSP_ATR_Biodata_Detail
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_WSP_ATR_Biodata_Detail")
public class X_ZZ_WSP_ATR_Biodata_Detail extends PO implements I_ZZ_WSP_ATR_Biodata_Detail, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251127L;

    /** Standard Constructor */
    public X_ZZ_WSP_ATR_Biodata_Detail (Properties ctx, int ZZ_WSP_ATR_Biodata_Detail_ID, String trxName)
    {
      super (ctx, ZZ_WSP_ATR_Biodata_Detail_ID, trxName);
      /** if (ZZ_WSP_ATR_Biodata_Detail_ID == 0)
        {
			setZZ_WSP_ATR_Biodata_Detail_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WSP_ATR_Biodata_Detail (Properties ctx, int ZZ_WSP_ATR_Biodata_Detail_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WSP_ATR_Biodata_Detail_ID, trxName, virtualColumns);
      /** if (ZZ_WSP_ATR_Biodata_Detail_ID == 0)
        {
			setZZ_WSP_ATR_Biodata_Detail_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WSP_ATR_Biodata_Detail (Properties ctx, String ZZ_WSP_ATR_Biodata_Detail_UU, String trxName)
    {
      super (ctx, ZZ_WSP_ATR_Biodata_Detail_UU, trxName);
      /** if (ZZ_WSP_ATR_Biodata_Detail_UU == null)
        {
			setZZ_WSP_ATR_Biodata_Detail_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WSP_ATR_Biodata_Detail (Properties ctx, String ZZ_WSP_ATR_Biodata_Detail_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WSP_ATR_Biodata_Detail_UU, trxName, virtualColumns);
      /** if (ZZ_WSP_ATR_Biodata_Detail_UU == null)
        {
			setZZ_WSP_ATR_Biodata_Detail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_WSP_ATR_Biodata_Detail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_WSP_ATR_Biodata_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set WSP/ATR Biodata Detail.
		@param ZZ_WSP_ATR_Biodata_Detail_ID WSP/ATR Biodata Detail
	*/
	public void setZZ_WSP_ATR_Biodata_Detail_ID (int ZZ_WSP_ATR_Biodata_Detail_ID)
	{
		if (ZZ_WSP_ATR_Biodata_Detail_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WSP_ATR_Biodata_Detail_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WSP_ATR_Biodata_Detail_ID, Integer.valueOf(ZZ_WSP_ATR_Biodata_Detail_ID));
	}

	/** Get WSP/ATR Biodata Detail.
		@return WSP/ATR Biodata Detail
	  */
	public int getZZ_WSP_ATR_Biodata_Detail_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WSP_ATR_Biodata_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_WSP_ATR_Biodata_Detail_UU.
		@param ZZ_WSP_ATR_Biodata_Detail_UU ZZ_WSP_ATR_Biodata_Detail_UU
	*/
	public void setZZ_WSP_ATR_Biodata_Detail_UU (String ZZ_WSP_ATR_Biodata_Detail_UU)
	{
		set_Value (COLUMNNAME_ZZ_WSP_ATR_Biodata_Detail_UU, ZZ_WSP_ATR_Biodata_Detail_UU);
	}

	/** Get ZZ_WSP_ATR_Biodata_Detail_UU.
		@return ZZ_WSP_ATR_Biodata_Detail_UU	  */
	public String getZZ_WSP_ATR_Biodata_Detail_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_WSP_ATR_Biodata_Detail_UU);
	}

	public I_ZZ_WSP_ATR_Submitted getZZ_WSP_ATR_Submitted() throws RuntimeException
	{
		return (I_ZZ_WSP_ATR_Submitted)MTable.get(getCtx(), I_ZZ_WSP_ATR_Submitted.Table_ID)
			.getPO(getZZ_WSP_ATR_Submitted_ID(), get_TrxName());
	}

	/** Set WSP/ATR Submitted File.
		@param ZZ_WSP_ATR_Submitted_ID WSP/ATR Submitted File
	*/
	public void setZZ_WSP_ATR_Submitted_ID (int ZZ_WSP_ATR_Submitted_ID)
	{
		if (ZZ_WSP_ATR_Submitted_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WSP_ATR_Submitted_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WSP_ATR_Submitted_ID, Integer.valueOf(ZZ_WSP_ATR_Submitted_ID));
	}

	/** Get WSP/ATR Submitted File.
		@return WSP/ATR Submitted File
	  */
	public int getZZ_WSP_ATR_Submitted_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WSP_ATR_Submitted_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_WSP_Employees getZZ_WSP_Employees() throws RuntimeException
	{
		return (I_ZZ_WSP_Employees)MTable.get(getCtx(), I_ZZ_WSP_Employees.Table_ID)
			.getPO(getZZ_WSP_Employees_ID(), get_TrxName());
	}

	/** Set ZZ_WSP_Employees.
		@param ZZ_WSP_Employees_ID ZZ_WSP_Employees reference table
	*/
	public void setZZ_WSP_Employees_ID (int ZZ_WSP_Employees_ID)
	{
		if (ZZ_WSP_Employees_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WSP_Employees_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WSP_Employees_ID, Integer.valueOf(ZZ_WSP_Employees_ID));
	}

	/** Get ZZ_WSP_Employees.
		@return ZZ_WSP_Employees reference table
	  */
	public int getZZ_WSP_Employees_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WSP_Employees_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}