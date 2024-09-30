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

/** Generated Interface for ZZ_WB_Transaction
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZ_WB_Transaction 
{

    /** TableName=ZZ_WB_Transaction */
    public static final String Table_Name = "ZZ_WB_Transaction";

    /** AD_Table_ID=1000027 */
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

    /** Column name DateOut */
    public static final String COLUMNNAME_DateOut = "DateOut";

	/** Set DateOut	  */
	public void setDateOut (Timestamp DateOut);

	/** Get DateOut	  */
	public Timestamp getDateOut();

    /** Column name DateTimeOut */
    public static final String COLUMNNAME_DateTimeOut = "DateTimeOut";

	/** Set DateTimeOut	  */
	public void setDateTimeOut (Timestamp DateTimeOut);

	/** Get DateTimeOut	  */
	public Timestamp getDateTimeOut();

    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/** Set Error Msg	  */
	public void setErrorMsg (String ErrorMsg);

	/** Get Error Msg	  */
	public String getErrorMsg();

    /** Column name Field1 */
    public static final String COLUMNNAME_Field1 = "Field1";

	/** Set Field1	  */
	public void setField1 (String Field1);

	/** Get Field1	  */
	public String getField1();

    /** Column name Field2 */
    public static final String COLUMNNAME_Field2 = "Field2";

	/** Set Field2	  */
	public void setField2 (String Field2);

	/** Get Field2	  */
	public String getField2();

    /** Column name Field3 */
    public static final String COLUMNNAME_Field3 = "Field3";

	/** Set Field3	  */
	public void setField3 (String Field3);

	/** Get Field3	  */
	public String getField3();

    /** Column name Field4 */
    public static final String COLUMNNAME_Field4 = "Field4";

	/** Set Field4	  */
	public void setField4 (String Field4);

	/** Get Field4	  */
	public String getField4();

    /** Column name Field5 */
    public static final String COLUMNNAME_Field5 = "Field5";

	/** Set Field5	  */
	public void setField5 (String Field5);

	/** Get Field5	  */
	public String getField5();

    /** Column name Field7 */
    public static final String COLUMNNAME_Field7 = "Field7";

	/** Set Field7	  */
	public void setField7 (String Field7);

	/** Get Field7	  */
	public String getField7();

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

    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/** Set Shipment/Receipt.
	  * Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID);

	/** Get Shipment/Receipt.
	  * Material Shipment Document
	  */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException;

    /** Column name NetMass */
    public static final String COLUMNNAME_NetMass = "NetMass";

	/** Set NetMass	  */
	public void setNetMass (BigDecimal NetMass);

	/** Get NetMass	  */
	public BigDecimal getNetMass();

    /** Column name TruckRegNo */
    public static final String COLUMNNAME_TruckRegNo = "TruckRegNo";

	/** Set TruckRegNo	  */
	public void setTruckRegNo (String TruckRegNo);

	/** Get TruckRegNo	  */
	public String getTruckRegNo();

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

    /** Column name WB_TransactionID */
    public static final String COLUMNNAME_WB_TransactionID = "WB_TransactionID";

	/** Set Weighbridge_Transaction	  */
	public void setWB_TransactionID (int WB_TransactionID);

	/** Get Weighbridge_Transaction	  */
	public int getWB_TransactionID();

    /** Column name ZZ_WB_Transaction_ID */
    public static final String COLUMNNAME_ZZ_WB_Transaction_ID = "ZZ_WB_Transaction_ID";

	/** Set Weighbridge Transactions.
	  * load transactions from WB PC
	  */
	public void setZZ_WB_Transaction_ID (int ZZ_WB_Transaction_ID);

	/** Get Weighbridge Transactions.
	  * load transactions from WB PC
	  */
	public int getZZ_WB_Transaction_ID();

    /** Column name ZZ_WB_Transaction_UU */
    public static final String COLUMNNAME_ZZ_WB_Transaction_UU = "ZZ_WB_Transaction_UU";

	/** Set ZZ_WB_Transaction_UU	  */
	public void setZZ_WB_Transaction_UU (String ZZ_WB_Transaction_UU);

	/** Get ZZ_WB_Transaction_UU	  */
	public String getZZ_WB_Transaction_UU();
}
