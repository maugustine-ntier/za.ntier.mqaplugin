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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for ZZ_Driver
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Driver")
public class X_ZZ_Driver extends PO implements I_ZZ_Driver, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20240610L;

    /** Standard Constructor */
    public X_ZZ_Driver (Properties ctx, int ZZ_Driver_ID, String trxName)
    {
      super (ctx, ZZ_Driver_ID, trxName);
      /** if (ZZ_Driver_ID == 0)
        {
			setName (null);
			setZZ_Driver_ID (0);
			setZZ_ID_Passport_Attached (false);
// N
			setZZ_ID_Passport_No (null);
			setZZ_Is_Valid (false);
// N
			setZZ_License_Attached (false);
// N
			setZZ_License_Expiry_Date (new Timestamp( System.currentTimeMillis() ));
			setZZ_Surname (null);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Driver (Properties ctx, int ZZ_Driver_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Driver_ID, trxName, virtualColumns);
      /** if (ZZ_Driver_ID == 0)
        {
			setName (null);
			setZZ_Driver_ID (0);
			setZZ_ID_Passport_Attached (false);
// N
			setZZ_ID_Passport_No (null);
			setZZ_Is_Valid (false);
// N
			setZZ_License_Attached (false);
// N
			setZZ_License_Expiry_Date (new Timestamp( System.currentTimeMillis() ));
			setZZ_Surname (null);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Driver (Properties ctx, String ZZ_Driver_UU, String trxName)
    {
      super (ctx, ZZ_Driver_UU, trxName);
      /** if (ZZ_Driver_UU == null)
        {
			setName (null);
			setZZ_Driver_ID (0);
			setZZ_ID_Passport_Attached (false);
// N
			setZZ_ID_Passport_No (null);
			setZZ_Is_Valid (false);
// N
			setZZ_License_Attached (false);
// N
			setZZ_License_Expiry_Date (new Timestamp( System.currentTimeMillis() ));
			setZZ_Surname (null);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Driver (Properties ctx, String ZZ_Driver_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Driver_UU, trxName, virtualColumns);
      /** if (ZZ_Driver_UU == null)
        {
			setName (null);
			setZZ_Driver_ID (0);
			setZZ_ID_Passport_Attached (false);
// N
			setZZ_ID_Passport_No (null);
			setZZ_Is_Valid (false);
// N
			setZZ_License_Attached (false);
// N
			setZZ_License_Expiry_Date (new Timestamp( System.currentTimeMillis() ));
			setZZ_Surname (null);
        } */
    }

    /** Load Constructor */
    public X_ZZ_Driver (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_Driver[")
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

	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException
	{
		return (org.compiere.model.I_M_Shipper)MTable.get(getCtx(), org.compiere.model.I_M_Shipper.Table_ID)
			.getPO(getM_Shipper_ID(), get_TrxName());
	}

	/** Set Shipper.
		@param M_Shipper_ID Method or manner of product delivery
	*/
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1)
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else
			set_Value (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Shipper.
		@return Method or manner of product delivery
	  */
	public int getM_Shipper_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
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

	/** Set ZZ_Driver_UU.
		@param ZZ_Driver_UU ZZ_Driver_UU
	*/
	public void setZZ_Driver_UU (String ZZ_Driver_UU)
	{
		set_Value (COLUMNNAME_ZZ_Driver_UU, ZZ_Driver_UU);
	}

	/** Get ZZ_Driver_UU.
		@return ZZ_Driver_UU	  */
	public String getZZ_Driver_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Driver_UU);
	}

	/** Set ID/Passport attached.
		@param ZZ_ID_Passport_Attached ID/Passport attached
	*/
	public void setZZ_ID_Passport_Attached (boolean ZZ_ID_Passport_Attached)
	{
		set_Value (COLUMNNAME_ZZ_ID_Passport_Attached, Boolean.valueOf(ZZ_ID_Passport_Attached));
	}

	/** Get ID/Passport attached.
		@return ID/Passport attached	  */
	public boolean isZZ_ID_Passport_Attached()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_ID_Passport_Attached);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ID / Passport.
		@param ZZ_ID_Passport_ID ID / Passport
	*/
	public void setZZ_ID_Passport_ID (int ZZ_ID_Passport_ID)
	{
		if (ZZ_ID_Passport_ID < 1)
			set_Value (COLUMNNAME_ZZ_ID_Passport_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_ID_Passport_ID, Integer.valueOf(ZZ_ID_Passport_ID));
	}

	/** Get ID / Passport.
		@return ID / Passport	  */
	public int getZZ_ID_Passport_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_ID_Passport_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ID/Passport No.
		@param ZZ_ID_Passport_No ID/Passport No
	*/
	public void setZZ_ID_Passport_No (String ZZ_ID_Passport_No)
	{
		set_Value (COLUMNNAME_ZZ_ID_Passport_No, ZZ_ID_Passport_No);
	}

	/** Get ID/Passport No.
		@return ID/Passport No	  */
	public String getZZ_ID_Passport_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_ID_Passport_No);
	}

	/** Set Valid Driver.
		@param ZZ_Is_Valid Valid Driver
	*/
	public void setZZ_Is_Valid (boolean ZZ_Is_Valid)
	{
		set_Value (COLUMNNAME_ZZ_Is_Valid, Boolean.valueOf(ZZ_Is_Valid));
	}

	/** Get Valid Driver.
		@return Valid Driver	  */
	public boolean isZZ_Is_Valid()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Is_Valid);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set License Attached.
		@param ZZ_License_Attached License Attached
	*/
	public void setZZ_License_Attached (boolean ZZ_License_Attached)
	{
		set_Value (COLUMNNAME_ZZ_License_Attached, Boolean.valueOf(ZZ_License_Attached));
	}

	/** Get License Attached.
		@return License Attached	  */
	public boolean isZZ_License_Attached()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_License_Attached);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set License Expiry Date.
		@param ZZ_License_Expiry_Date License Expiry Date
	*/
	public void setZZ_License_Expiry_Date (Timestamp ZZ_License_Expiry_Date)
	{
		set_Value (COLUMNNAME_ZZ_License_Expiry_Date, ZZ_License_Expiry_Date);
	}

	/** Get License Expiry Date.
		@return License Expiry Date	  */
	public Timestamp getZZ_License_Expiry_Date()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_License_Expiry_Date);
	}

	/** Set License.
		@param ZZ_License_ID License
	*/
	public void setZZ_License_ID (int ZZ_License_ID)
	{
		if (ZZ_License_ID < 1)
			set_Value (COLUMNNAME_ZZ_License_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_License_ID, Integer.valueOf(ZZ_License_ID));
	}

	/** Get License.
		@return License	  */
	public int getZZ_License_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_License_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set First Name And Surname.
		@param ZZ_Name_Surname First Name And Surname
	*/
	public void setZZ_Name_Surname (String ZZ_Name_Surname)
	{
		throw new IllegalArgumentException ("ZZ_Name_Surname is virtual column");	}

	/** Get First Name And Surname.
		@return First Name And Surname	  */
	public String getZZ_Name_Surname()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Name_Surname);
	}

	/** Set Surname.
		@param ZZ_Surname Surname
	*/
	public void setZZ_Surname (String ZZ_Surname)
	{
		set_Value (COLUMNNAME_ZZ_Surname, ZZ_Surname);
	}

	/** Get Surname.
		@return Surname	  */
	public String getZZ_Surname()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Surname);
	}
}