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

/** Generated Model for ZZ_WB_Transaction
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_WB_Transaction")
public class X_ZZ_WB_Transaction extends PO implements I_ZZ_WB_Transaction, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20240723L;

    /** Standard Constructor */
    public X_ZZ_WB_Transaction (Properties ctx, int ZZ_WB_Transaction_ID, String trxName)
    {
      super (ctx, ZZ_WB_Transaction_ID, trxName);
      /** if (ZZ_WB_Transaction_ID == 0)
        {
			setZZ_WB_Transaction_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WB_Transaction (Properties ctx, int ZZ_WB_Transaction_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WB_Transaction_ID, trxName, virtualColumns);
      /** if (ZZ_WB_Transaction_ID == 0)
        {
			setZZ_WB_Transaction_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WB_Transaction (Properties ctx, String ZZ_WB_Transaction_UU, String trxName)
    {
      super (ctx, ZZ_WB_Transaction_UU, trxName);
      /** if (ZZ_WB_Transaction_UU == null)
        {
			setZZ_WB_Transaction_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WB_Transaction (Properties ctx, String ZZ_WB_Transaction_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WB_Transaction_UU, trxName, virtualColumns);
      /** if (ZZ_WB_Transaction_UU == null)
        {
			setZZ_WB_Transaction_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_WB_Transaction (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_WB_Transaction[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set DateTimeOut.
		@param DateTimeOut DateTimeOut
	*/
	public void setDateTimeOut (Timestamp DateTimeOut)
	{
		set_Value (COLUMNNAME_DateTimeOut, DateTimeOut);
	}

	/** Get DateTimeOut.
		@return DateTimeOut	  */
	public Timestamp getDateTimeOut()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTimeOut);
	}

	/** Set Field1.
		@param Field1 Field1
	*/
	public void setField1 (String Field1)
	{
		set_Value (COLUMNNAME_Field1, Field1);
	}

	/** Get Field1.
		@return Field1	  */
	public String getField1()
	{
		return (String)get_Value(COLUMNNAME_Field1);
	}

	/** Set Field2.
		@param Field2 Field2
	*/
	public void setField2 (String Field2)
	{
		set_Value (COLUMNNAME_Field2, Field2);
	}

	/** Get Field2.
		@return Field2	  */
	public String getField2()
	{
		return (String)get_Value(COLUMNNAME_Field2);
	}

	/** Set Field3.
		@param Field3 Field3
	*/
	public void setField3 (String Field3)
	{
		set_Value (COLUMNNAME_Field3, Field3);
	}

	/** Get Field3.
		@return Field3	  */
	public String getField3()
	{
		return (String)get_Value(COLUMNNAME_Field3);
	}

	/** Set Field4.
		@param Field4 Field4
	*/
	public void setField4 (String Field4)
	{
		set_Value (COLUMNNAME_Field4, Field4);
	}

	/** Get Field4.
		@return Field4	  */
	public String getField4()
	{
		return (String)get_Value(COLUMNNAME_Field4);
	}

	/** Set Field5.
		@param Field5 Field5
	*/
	public void setField5 (String Field5)
	{
		set_Value (COLUMNNAME_Field5, Field5);
	}

	/** Get Field5.
		@return Field5	  */
	public String getField5()
	{
		return (String)get_Value(COLUMNNAME_Field5);
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

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
	{
		return (org.compiere.model.I_M_InOut)MTable.get(getCtx(), org.compiere.model.I_M_InOut.Table_ID)
			.getPO(getM_InOut_ID(), get_TrxName());
	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID Material Shipment Document
	*/
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set NetMass.
		@param NetMass NetMass
	*/
	public void setNetMass (BigDecimal NetMass)
	{
		set_Value (COLUMNNAME_NetMass, NetMass);
	}

	/** Get NetMass.
		@return NetMass	  */
	public BigDecimal getNetMass()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NetMass);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TruckRegNo.
		@param TruckRegNo TruckRegNo
	*/
	public void setTruckRegNo (String TruckRegNo)
	{
		set_Value (COLUMNNAME_TruckRegNo, TruckRegNo);
	}

	/** Get TruckRegNo.
		@return TruckRegNo	  */
	public String getTruckRegNo()
	{
		return (String)get_Value(COLUMNNAME_TruckRegNo);
	}

	/** Set Weighbridge_Transaction.
		@param WB_TransactionID Weighbridge_Transaction
	*/
	public void setWB_TransactionID (int WB_TransactionID)
	{
		set_Value (COLUMNNAME_WB_TransactionID, Integer.valueOf(WB_TransactionID));
	}

	/** Get Weighbridge_Transaction.
		@return Weighbridge_Transaction	  */
	public int getWB_TransactionID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WB_TransactionID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Weighbridge Transactions.
		@param ZZ_WB_Transaction_ID load transactions from WB PC
	*/
	public void setZZ_WB_Transaction_ID (int ZZ_WB_Transaction_ID)
	{
		if (ZZ_WB_Transaction_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WB_Transaction_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WB_Transaction_ID, Integer.valueOf(ZZ_WB_Transaction_ID));
	}

	/** Get Weighbridge Transactions.
		@return load transactions from WB PC
	  */
	public int getZZ_WB_Transaction_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WB_Transaction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_WB_Transaction_UU.
		@param ZZ_WB_Transaction_UU ZZ_WB_Transaction_UU
	*/
	public void setZZ_WB_Transaction_UU (String ZZ_WB_Transaction_UU)
	{
		set_Value (COLUMNNAME_ZZ_WB_Transaction_UU, ZZ_WB_Transaction_UU);
	}

	/** Get ZZ_WB_Transaction_UU.
		@return ZZ_WB_Transaction_UU	  */
	public String getZZ_WB_Transaction_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_WB_Transaction_UU);
	}
}