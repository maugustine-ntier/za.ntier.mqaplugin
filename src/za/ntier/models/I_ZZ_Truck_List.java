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

/** Generated Interface for ZZ_Truck_List
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZ_Truck_List 
{

    /** TableName=ZZ_Truck_List */
    public static final String Table_Name = "ZZ_Truck_List";

    /** AD_Table_ID=1000011 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

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

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

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

    /** Column name ZZ_Driver_ID */
    public static final String COLUMNNAME_ZZ_Driver_ID = "ZZ_Driver_ID";

	/** Set Driver.
	  * Driver table for Transporter window
	  */
	public void setZZ_Driver_ID (int ZZ_Driver_ID);

	/** Get Driver.
	  * Driver table for Transporter window
	  */
	public int getZZ_Driver_ID();

	public I_ZZ_Driver getZZ_Driver() throws RuntimeException;

    /** Column name ZZ_Fleet_No */
    public static final String COLUMNNAME_ZZ_Fleet_No = "ZZ_Fleet_No";

	/** Set Fleet No	  */
	public void setZZ_Fleet_No (String ZZ_Fleet_No);

	/** Get Fleet No	  */
	public String getZZ_Fleet_No();

    /** Column name ZZ_Horse_ID */
    public static final String COLUMNNAME_ZZ_Horse_ID = "ZZ_Horse_ID";

	/** Set Horse	  */
	public void setZZ_Horse_ID (int ZZ_Horse_ID);

	/** Get Horse	  */
	public int getZZ_Horse_ID();

	public I_ZZ_Truck getZZ_Horse() throws RuntimeException;

    /** Column name ZZ_No_Of_Loads */
    public static final String COLUMNNAME_ZZ_No_Of_Loads = "ZZ_No_Of_Loads";

	/** Set No Of Loads	  */
	public void setZZ_No_Of_Loads (BigDecimal ZZ_No_Of_Loads);

	/** Get No Of Loads	  */
	public BigDecimal getZZ_No_Of_Loads();

    /** Column name ZZ_Trailer1_ID */
    public static final String COLUMNNAME_ZZ_Trailer1_ID = "ZZ_Trailer1_ID";

	/** Set Trailer 1	  */
	public void setZZ_Trailer1_ID (int ZZ_Trailer1_ID);

	/** Get Trailer 1	  */
	public int getZZ_Trailer1_ID();

	public I_ZZ_Truck getZZ_Trailer1() throws RuntimeException;

    /** Column name ZZ_Trailer2_ID */
    public static final String COLUMNNAME_ZZ_Trailer2_ID = "ZZ_Trailer2_ID";

	/** Set Trailer 2	  */
	public void setZZ_Trailer2_ID (int ZZ_Trailer2_ID);

	/** Get Trailer 2	  */
	public int getZZ_Trailer2_ID();

	public I_ZZ_Truck getZZ_Trailer2() throws RuntimeException;

    /** Column name ZZ_Transporters_ID */
    public static final String COLUMNNAME_ZZ_Transporters_ID = "ZZ_Transporters_ID";

	/** Set Transporters	  */
	public void setZZ_Transporters_ID (int ZZ_Transporters_ID);

	/** Get Transporters	  */
	public int getZZ_Transporters_ID();

	public I_ZZ_Transporters getZZ_Transporters() throws RuntimeException;

    /** Column name ZZ_Truck_List_ID */
    public static final String COLUMNNAME_ZZ_Truck_List_ID = "ZZ_Truck_List_ID";

	/** Set Truck List	  */
	public void setZZ_Truck_List_ID (int ZZ_Truck_List_ID);

	/** Get Truck List	  */
	public int getZZ_Truck_List_ID();

    /** Column name ZZ_Truck_List_UU */
    public static final String COLUMNNAME_ZZ_Truck_List_UU = "ZZ_Truck_List_UU";

	/** Set ZZ_Truck_List_UU	  */
	public void setZZ_Truck_List_UU (String ZZ_Truck_List_UU);

	/** Get ZZ_Truck_List_UU	  */
	public String getZZ_Truck_List_UU();
}
