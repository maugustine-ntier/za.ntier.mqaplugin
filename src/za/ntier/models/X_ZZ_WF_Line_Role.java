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

/** Generated Model for ZZ_WF_Line_Role
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_WF_Line_Role")
public class X_ZZ_WF_Line_Role extends PO implements I_ZZ_WF_Line_Role, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251031L;

    /** Standard Constructor */
    public X_ZZ_WF_Line_Role (Properties ctx, int ZZ_WF_Line_Role_ID, String trxName)
    {
      super (ctx, ZZ_WF_Line_Role_ID, trxName);
      /** if (ZZ_WF_Line_Role_ID == 0)
        {
			setAD_Role_ID (0);
			setZZ_WF_Line_Role_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Line_Role (Properties ctx, int ZZ_WF_Line_Role_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WF_Line_Role_ID, trxName, virtualColumns);
      /** if (ZZ_WF_Line_Role_ID == 0)
        {
			setAD_Role_ID (0);
			setZZ_WF_Line_Role_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Line_Role (Properties ctx, String ZZ_WF_Line_Role_UU, String trxName)
    {
      super (ctx, ZZ_WF_Line_Role_UU, trxName);
      /** if (ZZ_WF_Line_Role_UU == null)
        {
			setAD_Role_ID (0);
			setZZ_WF_Line_Role_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Line_Role (Properties ctx, String ZZ_WF_Line_Role_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WF_Line_Role_UU, trxName, virtualColumns);
      /** if (ZZ_WF_Line_Role_UU == null)
        {
			setAD_Role_ID (0);
			setZZ_WF_Line_Role_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_WF_Line_Role (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client
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
      StringBuilder sb = new StringBuilder ("X_ZZ_WF_Line_Role[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Role)MTable.get(getCtx(), org.compiere.model.I_AD_Role.Table_ID)
			.getPO(getAD_Role_ID(), get_TrxName());
	}

	/** Set Role.
		@param AD_Role_ID Responsibility Role
	*/
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0)
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Role.
		@return Responsibility Role
	  */
	public int getAD_Role_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_WF_Line_Role.
		@param ZZ_WF_Line_Role_ID ZZ_WF_Line_Role
	*/
	public void setZZ_WF_Line_Role_ID (int ZZ_WF_Line_Role_ID)
	{
		if (ZZ_WF_Line_Role_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Line_Role_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Line_Role_ID, Integer.valueOf(ZZ_WF_Line_Role_ID));
	}

	/** Get ZZ_WF_Line_Role.
		@return ZZ_WF_Line_Role	  */
	public int getZZ_WF_Line_Role_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WF_Line_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_WF_Lines getZZ_WF_Lines() throws RuntimeException
	{
		return (I_ZZ_WF_Lines)MTable.get(getCtx(), I_ZZ_WF_Lines.Table_ID)
			.getPO(getZZ_WF_Lines_ID(), get_TrxName());
	}

	/** Set ZZ_WF_Lines.
		@param ZZ_WF_Lines_ID ZZ_WF_Lines
	*/
	public void setZZ_WF_Lines_ID (int ZZ_WF_Lines_ID)
	{
		if (ZZ_WF_Lines_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Lines_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Lines_ID, Integer.valueOf(ZZ_WF_Lines_ID));
	}

	/** Get ZZ_WF_Lines.
		@return ZZ_WF_Lines	  */
	public int getZZ_WF_Lines_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WF_Lines_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}