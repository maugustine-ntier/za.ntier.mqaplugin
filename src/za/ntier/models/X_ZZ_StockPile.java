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
import org.compiere.util.KeyNamePair;

/** Generated Model for ZZ_StockPile
 *  @author iDempiere (generated)
 *  @version Release 11 - $Id$ */
@org.adempiere.base.Model(table="ZZ_StockPile")
public class X_ZZ_StockPile extends PO implements I_ZZ_StockPile, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20240119L;

    /** Standard Constructor */
    public X_ZZ_StockPile (Properties ctx, int ZZ_StockPile_ID, String trxName)
    {
      super (ctx, ZZ_StockPile_ID, trxName);
      /** if (ZZ_StockPile_ID == 0)
        {
			setC_Year_ID (0);
			setDocumentNo (null);
			setM_Product_ID (0);
			setZZ_Block (null);
			setZZ_Mined_Month (null);
			setZZ_Side (null);
			setZZ_StockPile_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_StockPile (Properties ctx, int ZZ_StockPile_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_StockPile_ID, trxName, virtualColumns);
      /** if (ZZ_StockPile_ID == 0)
        {
			setC_Year_ID (0);
			setDocumentNo (null);
			setM_Product_ID (0);
			setZZ_Block (null);
			setZZ_Mined_Month (null);
			setZZ_Side (null);
			setZZ_StockPile_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_StockPile (Properties ctx, String ZZ_StockPile_UU, String trxName)
    {
      super (ctx, ZZ_StockPile_UU, trxName);
      /** if (ZZ_StockPile_UU == null)
        {
			setC_Year_ID (0);
			setDocumentNo (null);
			setM_Product_ID (0);
			setZZ_Block (null);
			setZZ_Mined_Month (null);
			setZZ_Side (null);
			setZZ_StockPile_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_StockPile (Properties ctx, String ZZ_StockPile_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_StockPile_UU, trxName, virtualColumns);
      /** if (ZZ_StockPile_UU == null)
        {
			setC_Year_ID (0);
			setDocumentNo (null);
			setM_Product_ID (0);
			setZZ_Block (null);
			setZZ_Mined_Month (null);
			setZZ_Side (null);
			setZZ_StockPile_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_StockPile (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client
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
      StringBuilder sb = new StringBuilder ("X_ZZ_StockPile[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
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
			set_Value (COLUMNNAME_C_Year_ID, null);
		else
			set_Value (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
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

	/** Set Document No.
		@param DocumentNo Document sequence number of the document
	*/
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo()
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), String.valueOf(getName()));
    }

	/** 1 = 01 */
	public static final String ZZ_BLOCK_1 = "01";
	/** 02 = 02 */
	public static final String ZZ_BLOCK_02 = "02";
	/** 03 = 03 */
	public static final String ZZ_BLOCK_03 = "03";
	/** 4 = 04 */
	public static final String ZZ_BLOCK_4 = "04";
	/** 05 = 05 */
	public static final String ZZ_BLOCK_05 = "05";
	/** 06 = 06 */
	public static final String ZZ_BLOCK_06 = "06";
	/** 7 = 07 */
	public static final String ZZ_BLOCK_7 = "07";
	/** 8 = 08 */
	public static final String ZZ_BLOCK_8 = "08";
	/** 9 = 09 */
	public static final String ZZ_BLOCK_9 = "09";
	/** 10 = 10 */
	public static final String ZZ_BLOCK_10 = "10";
	/** 11 = 11 */
	public static final String ZZ_BLOCK_11 = "11";
	/** 12 = 12 */
	public static final String ZZ_BLOCK_12 = "12";
	/** 13 = 13 */
	public static final String ZZ_BLOCK_13 = "13";
	/** 14 = 14 */
	public static final String ZZ_BLOCK_14 = "14";
	/** 15 = 15 */
	public static final String ZZ_BLOCK_15 = "15";
	/** 16 = 16 */
	public static final String ZZ_BLOCK_16 = "16";
	/** 17 = 17 */
	public static final String ZZ_BLOCK_17 = "17";
	/** 18 = 18 */
	public static final String ZZ_BLOCK_18 = "18";
	/** 19 = 19 */
	public static final String ZZ_BLOCK_19 = "19";
	/** 20 = 20 */
	public static final String ZZ_BLOCK_20 = "20";
	/** 21 = 21 */
	public static final String ZZ_BLOCK_21 = "21";
	/** 22 = 22 */
	public static final String ZZ_BLOCK_22 = "22";
	/** 23 = 23 */
	public static final String ZZ_BLOCK_23 = "23";
	/** 24 = 24 */
	public static final String ZZ_BLOCK_24 = "24";
	/** 25 = 25 */
	public static final String ZZ_BLOCK_25 = "25";
	/** 26 = 26 */
	public static final String ZZ_BLOCK_26 = "26";
	/** 27 = 27 */
	public static final String ZZ_BLOCK_27 = "27";
	/** 28 = 28 */
	public static final String ZZ_BLOCK_28 = "28";
	/** 29 = 29 */
	public static final String ZZ_BLOCK_29 = "29";
	/** 30 = 30 */
	public static final String ZZ_BLOCK_30 = "30";
	/** Set Block.
		@param ZZ_Block Block
	*/
	public void setZZ_Block (String ZZ_Block)
	{

		set_Value (COLUMNNAME_ZZ_Block, ZZ_Block);
	}

	/** Get Block.
		@return Block	  */
	public String getZZ_Block()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Block);
	}

	/** Set Close StockPile.
		@param ZZ_Close_StockPile Close StockPile
	*/
	public void setZZ_Close_StockPile (boolean ZZ_Close_StockPile)
	{
		set_Value (COLUMNNAME_ZZ_Close_StockPile, Boolean.valueOf(ZZ_Close_StockPile));
	}

	/** Get Close StockPile.
		@return Close StockPile	  */
	public boolean isZZ_Close_StockPile()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Close_StockPile);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Complete StockPile.
		@param ZZ_Complete_StockPile Complete StockPile
	*/
	public void setZZ_Complete_StockPile (boolean ZZ_Complete_StockPile)
	{
		set_Value (COLUMNNAME_ZZ_Complete_StockPile, Boolean.valueOf(ZZ_Complete_StockPile));
	}

	/** Get Complete StockPile.
		@return Complete StockPile	  */
	public boolean isZZ_Complete_StockPile()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Complete_StockPile);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Estimated Tonnage.
		@param ZZ_Estimated_Tonnage Estimated Tonnage
	*/
	public void setZZ_Estimated_Tonnage (BigDecimal ZZ_Estimated_Tonnage)
	{
		set_Value (COLUMNNAME_ZZ_Estimated_Tonnage, ZZ_Estimated_Tonnage);
	}

	/** Get Estimated Tonnage.
		@return Estimated Tonnage	  */
	public BigDecimal getZZ_Estimated_Tonnage()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ZZ_Estimated_Tonnage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** A Grade = A */
	public static final String ZZ_GRADE_AGrade = "A";
	/** B Grade = B */
	public static final String ZZ_GRADE_BGrade = "B";
	/** C Grade = C */
	public static final String ZZ_GRADE_CGrade = "C";
	/** D Grade = D */
	public static final String ZZ_GRADE_DGrade = "D";
	/**  E Grade = E */
	public static final String ZZ_GRADE_EGrade = "E";
	/** Set Grade.
		@param ZZ_Grade Grade
	*/
	public void setZZ_Grade (String ZZ_Grade)
	{

		set_Value (COLUMNNAME_ZZ_Grade, ZZ_Grade);
	}

	/** Get Grade.
		@return Grade	  */
	public String getZZ_Grade()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Grade);
	}

	/** Set Mined Date.
		@param ZZ_Mined_Date Mined Date
	*/
	public void setZZ_Mined_Date (Timestamp ZZ_Mined_Date)
	{
		set_Value (COLUMNNAME_ZZ_Mined_Date, ZZ_Mined_Date);
	}

	/** Get Mined Date.
		@return Mined Date	  */
	public Timestamp getZZ_Mined_Date()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Mined_Date);
	}

	/** January = 01 */
	public static final String ZZ_MINED_MONTH_January = "01";
	/** February = 02 */
	public static final String ZZ_MINED_MONTH_February = "02";
	/** March = 03 */
	public static final String ZZ_MINED_MONTH_March = "03";
	/** April = 04 */
	public static final String ZZ_MINED_MONTH_April = "04";
	/** May = 05 */
	public static final String ZZ_MINED_MONTH_May = "05";
	/** June = 06 */
	public static final String ZZ_MINED_MONTH_June = "06";
	/** July = 07 */
	public static final String ZZ_MINED_MONTH_July = "07";
	/** August = 08 */
	public static final String ZZ_MINED_MONTH_August = "08";
	/** September = 09 */
	public static final String ZZ_MINED_MONTH_September = "09";
	/** October = 10 */
	public static final String ZZ_MINED_MONTH_October = "10";
	/** November = 11 */
	public static final String ZZ_MINED_MONTH_November = "11";
	/** December = 12 */
	public static final String ZZ_MINED_MONTH_December = "12";
	/** Set Mined Month.
		@param ZZ_Mined_Month Mined Month
	*/
	public void setZZ_Mined_Month (String ZZ_Mined_Month)
	{

		set_Value (COLUMNNAME_ZZ_Mined_Month, ZZ_Mined_Month);
	}

	/** Get Mined Month.
		@return Mined Month	  */
	public String getZZ_Mined_Month()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Mined_Month);
	}

	/** A = A */
	public static final String ZZ_SEAM_A = "A";
	/** B = B */
	public static final String ZZ_SEAM_B = "B";
	/** C = C */
	public static final String ZZ_SEAM_C = "C";
	/** D = D */
	public static final String ZZ_SEAM_D = "D";
	/** E = E */
	public static final String ZZ_SEAM_E = "E";
	/** F = F */
	public static final String ZZ_SEAM_F = "F";
	/** G = G */
	public static final String ZZ_SEAM_G = "G";
	/** Set Seam.
		@param ZZ_Seam Seam
	*/
	public void setZZ_Seam (String ZZ_Seam)
	{

		set_Value (COLUMNNAME_ZZ_Seam, ZZ_Seam);
	}

	/** Get Seam.
		@return Seam	  */
	public String getZZ_Seam()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Seam);
	}

	/** East = E */
	public static final String ZZ_SIDE_East = "E";
	/** West = W */
	public static final String ZZ_SIDE_West = "W";
	/** Set Side.
		@param ZZ_Side Side
	*/
	public void setZZ_Side (String ZZ_Side)
	{

		set_Value (COLUMNNAME_ZZ_Side, ZZ_Side);
	}

	/** Get Side.
		@return Side	  */
	public String getZZ_Side()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Side);
	}

	/** Set StockPile .
		@param ZZ_StockPile_ID StockPile 
	*/
	public void setZZ_StockPile_ID (int ZZ_StockPile_ID)
	{
		if (ZZ_StockPile_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_StockPile_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_StockPile_ID, Integer.valueOf(ZZ_StockPile_ID));
	}

	/** Get StockPile .
		@return StockPile 	  */
	public int getZZ_StockPile_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_StockPile_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_StockPile_UU.
		@param ZZ_StockPile_UU ZZ_StockPile_UU
	*/
	public void setZZ_StockPile_UU (String ZZ_StockPile_UU)
	{
		set_Value (COLUMNNAME_ZZ_StockPile_UU, ZZ_StockPile_UU);
	}

	/** Get ZZ_StockPile_UU.
		@return ZZ_StockPile_UU	  */
	public String getZZ_StockPile_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_StockPile_UU);
	}

	/** Set Used Tonnage.
		@param ZZ_Used_Tonnage Used Tonnage
	*/
	public void setZZ_Used_Tonnage (BigDecimal ZZ_Used_Tonnage)
	{
		set_ValueNoCheck (COLUMNNAME_ZZ_Used_Tonnage, ZZ_Used_Tonnage);
	}

	/** Get Used Tonnage.
		@return Used Tonnage	  */
	public BigDecimal getZZ_Used_Tonnage()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ZZ_Used_Tonnage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}