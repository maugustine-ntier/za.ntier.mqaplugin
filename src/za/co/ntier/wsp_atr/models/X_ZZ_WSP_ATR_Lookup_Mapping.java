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

/** Generated Model for ZZ_WSP_ATR_Lookup_Mapping
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_WSP_ATR_Lookup_Mapping")
public class X_ZZ_WSP_ATR_Lookup_Mapping extends PO implements I_ZZ_WSP_ATR_Lookup_Mapping, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251122L;

    /** Standard Constructor */
    public X_ZZ_WSP_ATR_Lookup_Mapping (Properties ctx, int ZZ_WSP_ATR_Lookup_Mapping_ID, String trxName)
    {
      super (ctx, ZZ_WSP_ATR_Lookup_Mapping_ID, trxName);
      /** if (ZZ_WSP_ATR_Lookup_Mapping_ID == 0)
        {
			setZZ_WSP_ATR_Lookup_Mapping_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WSP_ATR_Lookup_Mapping (Properties ctx, int ZZ_WSP_ATR_Lookup_Mapping_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WSP_ATR_Lookup_Mapping_ID, trxName, virtualColumns);
      /** if (ZZ_WSP_ATR_Lookup_Mapping_ID == 0)
        {
			setZZ_WSP_ATR_Lookup_Mapping_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WSP_ATR_Lookup_Mapping (Properties ctx, String ZZ_WSP_ATR_Lookup_Mapping_UU, String trxName)
    {
      super (ctx, ZZ_WSP_ATR_Lookup_Mapping_UU, trxName);
      /** if (ZZ_WSP_ATR_Lookup_Mapping_UU == null)
        {
			setZZ_WSP_ATR_Lookup_Mapping_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WSP_ATR_Lookup_Mapping (Properties ctx, String ZZ_WSP_ATR_Lookup_Mapping_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WSP_ATR_Lookup_Mapping_UU, trxName, virtualColumns);
      /** if (ZZ_WSP_ATR_Lookup_Mapping_UU == null)
        {
			setZZ_WSP_ATR_Lookup_Mapping_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_WSP_ATR_Lookup_Mapping (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System
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
      StringBuilder sb = new StringBuilder ("X_ZZ_WSP_ATR_Lookup_Mapping[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Tab Name.
		@param ZZ_Tab_Name Tab Name
	*/
	public void setZZ_Tab_Name (String ZZ_Tab_Name)
	{
		set_Value (COLUMNNAME_ZZ_Tab_Name, ZZ_Tab_Name);
	}

	/** Get Tab Name.
		@return Tab Name	  */
	public String getZZ_Tab_Name()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Tab_Name);
	}

	/** Set WSP ATR Lookup Mapping.
		@param ZZ_WSP_ATR_Lookup_Mapping_ID WSP ATR Lookup Mapping
	*/
	public void setZZ_WSP_ATR_Lookup_Mapping_ID (int ZZ_WSP_ATR_Lookup_Mapping_ID)
	{
		if (ZZ_WSP_ATR_Lookup_Mapping_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WSP_ATR_Lookup_Mapping_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WSP_ATR_Lookup_Mapping_ID, Integer.valueOf(ZZ_WSP_ATR_Lookup_Mapping_ID));
	}

	/** Get WSP ATR Lookup Mapping.
		@return WSP ATR Lookup Mapping	  */
	public int getZZ_WSP_ATR_Lookup_Mapping_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WSP_ATR_Lookup_Mapping_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_WSP_ATR_Lookup_Mapping_UU.
		@param ZZ_WSP_ATR_Lookup_Mapping_UU ZZ_WSP_ATR_Lookup_Mapping_UU
	*/
	public void setZZ_WSP_ATR_Lookup_Mapping_UU (String ZZ_WSP_ATR_Lookup_Mapping_UU)
	{
		set_Value (COLUMNNAME_ZZ_WSP_ATR_Lookup_Mapping_UU, ZZ_WSP_ATR_Lookup_Mapping_UU);
	}

	/** Get ZZ_WSP_ATR_Lookup_Mapping_UU.
		@return ZZ_WSP_ATR_Lookup_Mapping_UU	  */
	public String getZZ_WSP_ATR_Lookup_Mapping_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_WSP_ATR_Lookup_Mapping_UU);
	}
}