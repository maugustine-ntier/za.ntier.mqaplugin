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
package za.ntier.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for ZZ_StockPile
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZ_StockPile 
{

    /** TableName=ZZ_StockPile */
    public static final String Table_Name = "ZZ_StockPile";

    /** AD_Table_ID=1000000 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Tenant.
	  * Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within tenant
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within tenant
	  */
	public int getAD_Org_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name C_Year_ID */
    public static final String COLUMNNAME_C_Year_ID = "C_Year_ID";

	/** Set Year.
	  * Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID);

	/** Get Year.
	  * Calendar Year
	  */
	public int getC_Year_ID();

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException;

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name ZZ_Block */
    public static final String COLUMNNAME_ZZ_Block = "ZZ_Block";

	/** Set Block	  */
	public void setZZ_Block (String ZZ_Block);

	/** Get Block	  */
	public String getZZ_Block();

    /** Column name ZZ_Close_StockPile */
    public static final String COLUMNNAME_ZZ_Close_StockPile = "ZZ_Close_StockPile";

	/** Set Close StockPile	  */
	public void setZZ_Close_StockPile (boolean ZZ_Close_StockPile);

	/** Get Close StockPile	  */
	public boolean isZZ_Close_StockPile();

    /** Column name ZZ_Complete_StockPile */
    public static final String COLUMNNAME_ZZ_Complete_StockPile = "ZZ_Complete_StockPile";

	/** Set Complete StockPile	  */
	public void setZZ_Complete_StockPile (boolean ZZ_Complete_StockPile);

	/** Get Complete StockPile	  */
	public boolean isZZ_Complete_StockPile();

    /** Column name ZZ_Estimated_Tonnage */
    public static final String COLUMNNAME_ZZ_Estimated_Tonnage = "ZZ_Estimated_Tonnage";

	/** Set Estimated Tonnage	  */
	public void setZZ_Estimated_Tonnage (BigDecimal ZZ_Estimated_Tonnage);

	/** Get Estimated Tonnage	  */
	public BigDecimal getZZ_Estimated_Tonnage();

    /** Column name ZZ_Grade */
    public static final String COLUMNNAME_ZZ_Grade = "ZZ_Grade";

	/** Set Grade	  */
	public void setZZ_Grade (String ZZ_Grade);

	/** Get Grade	  */
	public String getZZ_Grade();

    /** Column name ZZ_Mined_Date */
    public static final String COLUMNNAME_ZZ_Mined_Date = "ZZ_Mined_Date";

	/** Set Mined Date	  */
	public void setZZ_Mined_Date (Timestamp ZZ_Mined_Date);

	/** Get Mined Date	  */
	public Timestamp getZZ_Mined_Date();

    /** Column name ZZ_Mined_Month */
    public static final String COLUMNNAME_ZZ_Mined_Month = "ZZ_Mined_Month";

	/** Set Mined Month	  */
	public void setZZ_Mined_Month (String ZZ_Mined_Month);

	/** Get Mined Month	  */
	public String getZZ_Mined_Month();

    /** Column name ZZ_Seam */
    public static final String COLUMNNAME_ZZ_Seam = "ZZ_Seam";

	/** Set Seam	  */
	public void setZZ_Seam (String ZZ_Seam);

	/** Get Seam	  */
	public String getZZ_Seam();

    /** Column name ZZ_Side */
    public static final String COLUMNNAME_ZZ_Side = "ZZ_Side";

	/** Set Side	  */
	public void setZZ_Side (String ZZ_Side);

	/** Get Side	  */
	public String getZZ_Side();

    /** Column name ZZ_StockPile_ID */
    public static final String COLUMNNAME_ZZ_StockPile_ID = "ZZ_StockPile_ID";

	/** Set StockPile 	  */
	public void setZZ_StockPile_ID (int ZZ_StockPile_ID);

	/** Get StockPile 	  */
	public int getZZ_StockPile_ID();

    /** Column name ZZ_StockPile_UU */
    public static final String COLUMNNAME_ZZ_StockPile_UU = "ZZ_StockPile_UU";

	/** Set ZZ_StockPile_UU	  */
	public void setZZ_StockPile_UU (String ZZ_StockPile_UU);

	/** Get ZZ_StockPile_UU	  */
	public String getZZ_StockPile_UU();

    /** Column name ZZ_Used_Tonnage */
    public static final String COLUMNNAME_ZZ_Used_Tonnage = "ZZ_Used_Tonnage";

	/** Set Used Tonnage	  */
	public void setZZ_Used_Tonnage (BigDecimal ZZ_Used_Tonnage);

	/** Get Used Tonnage	  */
	public BigDecimal getZZ_Used_Tonnage();
}
