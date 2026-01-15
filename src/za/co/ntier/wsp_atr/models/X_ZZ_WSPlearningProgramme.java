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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for ZZ_WSPlearningProgramme
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_WSPlearningProgramme")
public class X_ZZ_WSPlearningProgramme extends PO implements I_ZZ_WSPlearningProgramme, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260115L;

    /** Standard Constructor */
    public X_ZZ_WSPlearningProgramme (Properties ctx, int ZZ_WSPlearningProgramme_ID, String trxName)
    {
      super (ctx, ZZ_WSPlearningProgramme_ID, trxName);
      /** if (ZZ_WSPlearningProgramme_ID == 0)
        {
			setName (null);
			setprogrammecode (null);
			setprogrammetype (null);
			setseta (null);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WSPlearningProgramme (Properties ctx, int ZZ_WSPlearningProgramme_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WSPlearningProgramme_ID, trxName, virtualColumns);
      /** if (ZZ_WSPlearningProgramme_ID == 0)
        {
			setName (null);
			setprogrammecode (null);
			setprogrammetype (null);
			setseta (null);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WSPlearningProgramme (Properties ctx, String ZZ_WSPlearningProgramme_UU, String trxName)
    {
      super (ctx, ZZ_WSPlearningProgramme_UU, trxName);
      /** if (ZZ_WSPlearningProgramme_UU == null)
        {
			setName (null);
			setprogrammecode (null);
			setprogrammetype (null);
			setseta (null);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WSPlearningProgramme (Properties ctx, String ZZ_WSPlearningProgramme_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WSPlearningProgramme_UU, trxName, virtualColumns);
      /** if (ZZ_WSPlearningProgramme_UU == null)
        {
			setName (null);
			setprogrammecode (null);
			setprogrammetype (null);
			setseta (null);
        } */
    }

    /** Load Constructor */
    public X_ZZ_WSPlearningProgramme (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_WSPlearningProgramme[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Createdby Old.
		@param Createdby_OLD Createdby Old
	*/
	public void setCreatedby_OLD (int Createdby_OLD)
	{
		set_ValueNoCheck (COLUMNNAME_Createdby_OLD, Integer.valueOf(Createdby_OLD));
	}

	/** Get Createdby Old.
		@return Createdby Old	  */
	public int getCreatedby_OLD()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Createdby_OLD);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Title.
		@param Title Name this entity is referred to as
	*/
	public void setTitle (String Title)
	{
		set_ValueNoCheck (COLUMNNAME_Title, Title);
	}

	/** Get Title.
		@return Name this entity is referred to as
	  */
	public String getTitle()
	{
		return (String)get_Value(COLUMNNAME_Title);
	}

	/** Set Updatedby Old.
		@param Updatedby_OLD Updatedby Old
	*/
	public void setUpdatedby_OLD (int Updatedby_OLD)
	{
		set_Value (COLUMNNAME_Updatedby_OLD, Integer.valueOf(Updatedby_OLD));
	}

	/** Get Updatedby Old.
		@return Updatedby Old	  */
	public int getUpdatedby_OLD()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Updatedby_OLD);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set WSP Learning Programme.
		@param ZZ_WSPlearningProgramme_ID WSP Learning Programme
	*/
	public void setZZ_WSPlearningProgramme_ID (int ZZ_WSPlearningProgramme_ID)
	{
		if (ZZ_WSPlearningProgramme_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WSPlearningProgramme_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WSPlearningProgramme_ID, Integer.valueOf(ZZ_WSPlearningProgramme_ID));
	}

	/** Get WSP Learning Programme.
		@return WSP Learning Programme	  */
	public int getZZ_WSPlearningProgramme_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WSPlearningProgramme_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_WSPlearningProgramme_UU.
		@param ZZ_WSPlearningProgramme_UU ZZ_WSPlearningProgramme_UU
	*/
	public void setZZ_WSPlearningProgramme_UU (String ZZ_WSPlearningProgramme_UU)
	{
		set_Value (COLUMNNAME_ZZ_WSPlearningProgramme_UU, ZZ_WSPlearningProgramme_UU);
	}

	/** Get ZZ_WSPlearningProgramme_UU.
		@return ZZ_WSPlearningProgramme_UU	  */
	public String getZZ_WSPlearningProgramme_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_WSPlearningProgramme_UU);
	}

	/** Set id.
		@param id id
	*/
	public void setid (String id)
	{
		set_Value (COLUMNNAME_id, id);
	}

	/** Get id.
		@return id	  */
	public String getid()
	{
		return (String)get_Value(COLUMNNAME_id);
	}

	/** Set iscurrentyear.
		@param iscurrentyear iscurrentyear
	*/
	public void setiscurrentyear (int iscurrentyear)
	{
		set_Value (COLUMNNAME_iscurrentyear, Integer.valueOf(iscurrentyear));
	}

	/** Get iscurrentyear.
		@return iscurrentyear	  */
	public int getiscurrentyear()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_iscurrentyear);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set isdeleted.
		@param isdeleted isdeleted
	*/
	public void setisdeleted (BigDecimal isdeleted)
	{
		set_Value (COLUMNNAME_isdeleted, isdeleted);
	}

	/** Get isdeleted.
		@return isdeleted	  */
	public BigDecimal getisdeleted()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_isdeleted);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set programmecode.
		@param programmecode programmecode
	*/
	public void setprogrammecode (String programmecode)
	{
		set_Value (COLUMNNAME_programmecode, programmecode);
	}

	/** Get programmecode.
		@return programmecode	  */
	public String getprogrammecode()
	{
		return (String)get_Value(COLUMNNAME_programmecode);
	}

	/** Set programmetype.
		@param programmetype programmetype
	*/
	public void setprogrammetype (String programmetype)
	{
		set_Value (COLUMNNAME_programmetype, programmetype);
	}

	/** Get programmetype.
		@return programmetype	  */
	public String getprogrammetype()
	{
		return (String)get_Value(COLUMNNAME_programmetype);
	}

	/** Set seta.
		@param seta seta
	*/
	public void setseta (String seta)
	{
		set_Value (COLUMNNAME_seta, seta);
	}

	/** Get seta.
		@return seta	  */
	public String getseta()
	{
		return (String)get_Value(COLUMNNAME_seta);
	}

	/** Set sysendtime.
		@param sysendtime sysendtime
	*/
	public void setsysendtime (Timestamp sysendtime)
	{
		set_Value (COLUMNNAME_sysendtime, sysendtime);
	}

	/** Get sysendtime.
		@return sysendtime	  */
	public Timestamp getsysendtime()
	{
		return (Timestamp)get_Value(COLUMNNAME_sysendtime);
	}

	/** Set sysstarttime.
		@param sysstarttime sysstarttime
	*/
	public void setsysstarttime (Timestamp sysstarttime)
	{
		set_Value (COLUMNNAME_sysstarttime, sysstarttime);
	}

	/** Get sysstarttime.
		@return sysstarttime	  */
	public Timestamp getsysstarttime()
	{
		return (Timestamp)get_Value(COLUMNNAME_sysstarttime);
	}
}