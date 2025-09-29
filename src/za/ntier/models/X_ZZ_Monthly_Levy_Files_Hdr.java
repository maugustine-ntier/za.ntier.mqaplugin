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

/** Generated Model for ZZ_Monthly_Levy_Files_Hdr
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Monthly_Levy_Files_Hdr")
public class X_ZZ_Monthly_Levy_Files_Hdr extends PO implements I_ZZ_Monthly_Levy_Files_Hdr, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250929L;

    /** Standard Constructor */
    public X_ZZ_Monthly_Levy_Files_Hdr (Properties ctx, int ZZ_Monthly_Levy_Files_Hdr_ID, String trxName)
    {
      super (ctx, ZZ_Monthly_Levy_Files_Hdr_ID, trxName);
      /** if (ZZ_Monthly_Levy_Files_Hdr_ID == 0)
        {
			setZZ_Monthly_Levy_Files_Hdr_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Monthly_Levy_Files_Hdr (Properties ctx, int ZZ_Monthly_Levy_Files_Hdr_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Monthly_Levy_Files_Hdr_ID, trxName, virtualColumns);
      /** if (ZZ_Monthly_Levy_Files_Hdr_ID == 0)
        {
			setZZ_Monthly_Levy_Files_Hdr_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Monthly_Levy_Files_Hdr (Properties ctx, String ZZ_Monthly_Levy_Files_Hdr_UU, String trxName)
    {
      super (ctx, ZZ_Monthly_Levy_Files_Hdr_UU, trxName);
      /** if (ZZ_Monthly_Levy_Files_Hdr_UU == null)
        {
			setZZ_Monthly_Levy_Files_Hdr_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Monthly_Levy_Files_Hdr (Properties ctx, String ZZ_Monthly_Levy_Files_Hdr_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Monthly_Levy_Files_Hdr_UU, trxName, virtualColumns);
      /** if (ZZ_Monthly_Levy_Files_Hdr_UU == null)
        {
			setZZ_Monthly_Levy_Files_Hdr_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_Monthly_Levy_Files_Hdr (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_Monthly_Levy_Files_Hdr[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** January = 01 */
	public static final String ZZ_MONTH_January = "01";
	/** February = 02 */
	public static final String ZZ_MONTH_February = "02";
	/** March = 03 */
	public static final String ZZ_MONTH_March = "03";
	/** April = 04 */
	public static final String ZZ_MONTH_April = "04";
	/** May = 05 */
	public static final String ZZ_MONTH_May = "05";
	/** June = 06 */
	public static final String ZZ_MONTH_June = "06";
	/** July = 07 */
	public static final String ZZ_MONTH_July = "07";
	/** August = 08 */
	public static final String ZZ_MONTH_August = "08";
	/** September = 09 */
	public static final String ZZ_MONTH_September = "09";
	/** October = 10 */
	public static final String ZZ_MONTH_October = "10";
	/** November = 11 */
	public static final String ZZ_MONTH_November = "11";
	/** December = 12 */
	public static final String ZZ_MONTH_December = "12";
	/** Set Month.
		@param ZZ_Month Month
	*/
	public void setZZ_Month (String ZZ_Month)
	{

		set_Value (COLUMNNAME_ZZ_Month, ZZ_Month);
	}

	/** Get Month.
		@return Month	  */
	public String getZZ_Month()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Month);
	}

	/** Set Monthly Levy Files Hdr.
		@param ZZ_Monthly_Levy_Files_Hdr_ID Monthly Levy Files Hdr
	*/
	public void setZZ_Monthly_Levy_Files_Hdr_ID (int ZZ_Monthly_Levy_Files_Hdr_ID)
	{
		if (ZZ_Monthly_Levy_Files_Hdr_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Monthly_Levy_Files_Hdr_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Monthly_Levy_Files_Hdr_ID, Integer.valueOf(ZZ_Monthly_Levy_Files_Hdr_ID));
	}

	/** Get Monthly Levy Files Hdr.
		@return Monthly Levy Files Hdr	  */
	public int getZZ_Monthly_Levy_Files_Hdr_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Monthly_Levy_Files_Hdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_Monthly_Levy_Files_Hdr_UU.
		@param ZZ_Monthly_Levy_Files_Hdr_UU ZZ_Monthly_Levy_Files_Hdr_UU
	*/
	public void setZZ_Monthly_Levy_Files_Hdr_UU (String ZZ_Monthly_Levy_Files_Hdr_UU)
	{
		set_Value (COLUMNNAME_ZZ_Monthly_Levy_Files_Hdr_UU, ZZ_Monthly_Levy_Files_Hdr_UU);
	}

	/** Get ZZ_Monthly_Levy_Files_Hdr_UU.
		@return ZZ_Monthly_Levy_Files_Hdr_UU	  */
	public String getZZ_Monthly_Levy_Files_Hdr_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Monthly_Levy_Files_Hdr_UU);
	}

	/** Set Upload.
		@param ZZ_Upload Upload
	*/
	public void setZZ_Upload (String ZZ_Upload)
	{
		set_Value (COLUMNNAME_ZZ_Upload, ZZ_Upload);
	}

	/** Get Upload.
		@return Upload	  */
	public String getZZ_Upload()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Upload);
	}
}