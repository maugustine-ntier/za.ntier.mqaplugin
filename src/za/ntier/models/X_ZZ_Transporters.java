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

/** Generated Model for ZZ_Transporters
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Transporters")
public class X_ZZ_Transporters extends PO implements I_ZZ_Transporters, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20240610L;

    /** Standard Constructor */
    public X_ZZ_Transporters (Properties ctx, int ZZ_Transporters_ID, String trxName)
    {
      super (ctx, ZZ_Transporters_ID, trxName);
      /** if (ZZ_Transporters_ID == 0)
        {
			setM_Product_ID (0);
			setZZ_Transporters_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Transporters (Properties ctx, int ZZ_Transporters_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Transporters_ID, trxName, virtualColumns);
      /** if (ZZ_Transporters_ID == 0)
        {
			setM_Product_ID (0);
			setZZ_Transporters_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Transporters (Properties ctx, String ZZ_Transporters_UU, String trxName)
    {
      super (ctx, ZZ_Transporters_UU, trxName);
      /** if (ZZ_Transporters_UU == null)
        {
			setM_Product_ID (0);
			setZZ_Transporters_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Transporters (Properties ctx, String ZZ_Transporters_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Transporters_UU, trxName, virtualColumns);
      /** if (ZZ_Transporters_UU == null)
        {
			setM_Product_ID (0);
			setZZ_Transporters_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_Transporters (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_Transporters[")
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_ID)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());
	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID Identifies the (ship to) address for this Business Partner
	*/
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
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

	/** Set Import Truck List Via Excel.
		@param ImportTruckListViaExcel Import Truck List Via Excel
	*/
	public void setImportTruckListViaExcel (String ImportTruckListViaExcel)
	{
		set_Value (COLUMNNAME_ImportTruckListViaExcel, ImportTruckListViaExcel);
	}

	/** Get Import Truck List Via Excel.
		@return Import Truck List Via Excel	  */
	public String getImportTruckListViaExcel()
	{
		return (String)get_Value(COLUMNNAME_ImportTruckListViaExcel);
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_ID)
			.getPO(getM_Product_ID(), get_TrxName());
	}

	/** Set Product.
		@param M_Product_ID Product, Service, Item
	*/
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_Value (COLUMNNAME_M_Product_ID, null);
		else
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_ID)
			.getPO(getM_Warehouse_ID(), get_TrxName());
	}

	/** Set Warehouse.
		@param M_Warehouse_ID Storage Warehouse and Service Point
	*/
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1)
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
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

	/** ZZ_Buckets_Are_Clean AD_Reference_ID=319 */
	public static final int ZZ_BUCKETS_ARE_CLEAN_AD_Reference_ID=319;
	/** No = N */
	public static final String ZZ_BUCKETS_ARE_CLEAN_No = "N";
	/** Yes = Y */
	public static final String ZZ_BUCKETS_ARE_CLEAN_Yes = "Y";
	/** Set Buckets Are Clean.
		@param ZZ_Buckets_Are_Clean Buckets Are Clean
	*/
	public void setZZ_Buckets_Are_Clean (String ZZ_Buckets_Are_Clean)
	{

		set_Value (COLUMNNAME_ZZ_Buckets_Are_Clean, ZZ_Buckets_Are_Clean);
	}

	/** Get Buckets Are Clean.
		@return Buckets Are Clean	  */
	public String getZZ_Buckets_Are_Clean()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Buckets_Are_Clean);
	}

	public org.compiere.model.I_C_BPartner getZZ_Cust_Dest() throws RuntimeException
	{
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_ID)
			.getPO(getZZ_Cust_Dest_ID(), get_TrxName());
	}

	/** Set Customer Destinations.
		@param ZZ_Cust_Dest_ID Customer Destinations
	*/
	public void setZZ_Cust_Dest_ID (int ZZ_Cust_Dest_ID)
	{
		if (ZZ_Cust_Dest_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Cust_Dest_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Cust_Dest_ID, Integer.valueOf(ZZ_Cust_Dest_ID));
	}

	/** Get Customer Destinations.
		@return Customer Destinations	  */
	public int getZZ_Cust_Dest_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Cust_Dest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ZZ_Fire_Extinguisher AD_Reference_ID=319 */
	public static final int ZZ_FIRE_EXTINGUISHER_AD_Reference_ID=319;
	/** No = N */
	public static final String ZZ_FIRE_EXTINGUISHER_No = "N";
	/** Yes = Y */
	public static final String ZZ_FIRE_EXTINGUISHER_Yes = "Y";
	/** Set Fire Extinguisher.
		@param ZZ_Fire_Extinguisher Fire Extinguisher
	*/
	public void setZZ_Fire_Extinguisher (String ZZ_Fire_Extinguisher)
	{

		set_Value (COLUMNNAME_ZZ_Fire_Extinguisher, ZZ_Fire_Extinguisher);
	}

	/** Get Fire Extinguisher.
		@return Fire Extinguisher	  */
	public String getZZ_Fire_Extinguisher()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Fire_Extinguisher);
	}

	/** Set Loading Date.
		@param ZZ_Loading_Date Loading Date
	*/
	public void setZZ_Loading_Date (Timestamp ZZ_Loading_Date)
	{
		set_Value (COLUMNNAME_ZZ_Loading_Date, ZZ_Loading_Date);
	}

	/** Get Loading Date.
		@return Loading Date	  */
	public Timestamp getZZ_Loading_Date()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Loading_Date);
	}

	/** Set Order Tonnage.
		@param ZZ_Order_Tonnage Order Tonnage
	*/
	public void setZZ_Order_Tonnage (BigDecimal ZZ_Order_Tonnage)
	{
		set_Value (COLUMNNAME_ZZ_Order_Tonnage, ZZ_Order_Tonnage);
	}

	/** Get Order Tonnage.
		@return Order Tonnage	  */
	public BigDecimal getZZ_Order_Tonnage()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ZZ_Order_Tonnage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Transporters.
		@param ZZ_Transporters_ID Transporters
	*/
	public void setZZ_Transporters_ID (int ZZ_Transporters_ID)
	{
		if (ZZ_Transporters_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Transporters_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Transporters_ID, Integer.valueOf(ZZ_Transporters_ID));
	}

	/** Get Transporters.
		@return Transporters	  */
	public int getZZ_Transporters_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Transporters_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_Transporters_UU.
		@param ZZ_Transporters_UU ZZ_Transporters_UU
	*/
	public void setZZ_Transporters_UU (String ZZ_Transporters_UU)
	{
		set_Value (COLUMNNAME_ZZ_Transporters_UU, ZZ_Transporters_UU);
	}

	/** Get ZZ_Transporters_UU.
		@return ZZ_Transporters_UU	  */
	public String getZZ_Transporters_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Transporters_UU);
	}

	/** ZZ_Truck_Covers AD_Reference_ID=319 */
	public static final int ZZ_TRUCK_COVERS_AD_Reference_ID=319;
	/** No = N */
	public static final String ZZ_TRUCK_COVERS_No = "N";
	/** Yes = Y */
	public static final String ZZ_TRUCK_COVERS_Yes = "Y";
	/** Set Truck Covers.
		@param ZZ_Truck_Covers Truck Covers
	*/
	public void setZZ_Truck_Covers (String ZZ_Truck_Covers)
	{

		set_Value (COLUMNNAME_ZZ_Truck_Covers, ZZ_Truck_Covers);
	}

	/** Get Truck Covers.
		@return Truck Covers	  */
	public String getZZ_Truck_Covers()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Truck_Covers);
	}

	/** ZZ_Tyres_Are_Good AD_Reference_ID=319 */
	public static final int ZZ_TYRES_ARE_GOOD_AD_Reference_ID=319;
	/** No = N */
	public static final String ZZ_TYRES_ARE_GOOD_No = "N";
	/** Yes = Y */
	public static final String ZZ_TYRES_ARE_GOOD_Yes = "Y";
	/** Set Tyres Are Good.
		@param ZZ_Tyres_Are_Good Tyres Are Good
	*/
	public void setZZ_Tyres_Are_Good (String ZZ_Tyres_Are_Good)
	{

		set_Value (COLUMNNAME_ZZ_Tyres_Are_Good, ZZ_Tyres_Are_Good);
	}

	/** Get Tyres Are Good.
		@return Tyres Are Good	  */
	public String getZZ_Tyres_Are_Good()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Tyres_Are_Good);
	}
}