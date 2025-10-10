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

/** Generated Model for C_InvoiceBatch
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="C_InvoiceBatch")
public class X_C_InvoiceBatch extends PO implements I_C_InvoiceBatch, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251008L;

    /** Standard Constructor */
    public X_C_InvoiceBatch (Properties ctx, int C_InvoiceBatch_ID, String trxName)
    {
      super (ctx, C_InvoiceBatch_ID, trxName);
      /** if (C_InvoiceBatch_ID == 0)
        {
			setC_Currency_ID (0);
// @$C_Currency_ID@
			setC_InvoiceBatch_ID (0);
			setControlAmt (Env.ZERO);
// 0
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocumentAmt (Env.ZERO);
			setDocumentNo (null);
			setIsSOTrx (false);
// N
			setProcessed (false);
			setSalesRep_ID (0);
			setZZ_Account_Reconned (false);
// N
			setZZ_Auth_PO_Order (false);
// N
			setZZ_Calcs_Checked (false);
// N
			setZZ_Cred_Bank_Dets_Verified (false);
// N
			setZZ_GL_Allocation_Checked (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_C_InvoiceBatch (Properties ctx, int C_InvoiceBatch_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, C_InvoiceBatch_ID, trxName, virtualColumns);
      /** if (C_InvoiceBatch_ID == 0)
        {
			setC_Currency_ID (0);
// @$C_Currency_ID@
			setC_InvoiceBatch_ID (0);
			setControlAmt (Env.ZERO);
// 0
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocumentAmt (Env.ZERO);
			setDocumentNo (null);
			setIsSOTrx (false);
// N
			setProcessed (false);
			setSalesRep_ID (0);
			setZZ_Account_Reconned (false);
// N
			setZZ_Auth_PO_Order (false);
// N
			setZZ_Calcs_Checked (false);
// N
			setZZ_Cred_Bank_Dets_Verified (false);
// N
			setZZ_GL_Allocation_Checked (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_C_InvoiceBatch (Properties ctx, String C_InvoiceBatch_UU, String trxName)
    {
      super (ctx, C_InvoiceBatch_UU, trxName);
      /** if (C_InvoiceBatch_UU == null)
        {
			setC_Currency_ID (0);
// @$C_Currency_ID@
			setC_InvoiceBatch_ID (0);
			setControlAmt (Env.ZERO);
// 0
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocumentAmt (Env.ZERO);
			setDocumentNo (null);
			setIsSOTrx (false);
// N
			setProcessed (false);
			setSalesRep_ID (0);
			setZZ_Account_Reconned (false);
// N
			setZZ_Auth_PO_Order (false);
// N
			setZZ_Calcs_Checked (false);
// N
			setZZ_Cred_Bank_Dets_Verified (false);
// N
			setZZ_GL_Allocation_Checked (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_C_InvoiceBatch (Properties ctx, String C_InvoiceBatch_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, C_InvoiceBatch_UU, trxName, virtualColumns);
      /** if (C_InvoiceBatch_UU == null)
        {
			setC_Currency_ID (0);
// @$C_Currency_ID@
			setC_InvoiceBatch_ID (0);
			setControlAmt (Env.ZERO);
// 0
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocumentAmt (Env.ZERO);
			setDocumentNo (null);
			setIsSOTrx (false);
// N
			setProcessed (false);
			setSalesRep_ID (0);
			setZZ_Account_Reconned (false);
// N
			setZZ_Auth_PO_Order (false);
// N
			setZZ_Calcs_Checked (false);
// N
			setZZ_Cred_Bank_Dets_Verified (false);
// N
			setZZ_GL_Allocation_Checked (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_InvoiceBatch (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org
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
      StringBuilder sb = new StringBuilder ("X_C_InvoiceBatch[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException
	{
		return (org.compiere.model.I_C_ConversionType)MTable.get(getCtx(), org.compiere.model.I_C_ConversionType.Table_ID)
			.getPO(getC_ConversionType_ID(), get_TrxName());
	}

	/** Set Currency Type.
		@param C_ConversionType_ID Currency Conversion Rate Type
	*/
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1)
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else
			set_Value (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Currency Type.
		@return Currency Conversion Rate Type
	  */
	public int getC_ConversionType_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_ID)
			.getPO(getC_Currency_ID(), get_TrxName());
	}

	/** Set Currency.
		@param C_Currency_ID The Currency for this record
	*/
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1)
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Invoice Batch.
		@param C_InvoiceBatch_ID Expense Invoice Batch Header
	*/
	public void setC_InvoiceBatch_ID (int C_InvoiceBatch_ID)
	{
		if (C_InvoiceBatch_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_InvoiceBatch_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_InvoiceBatch_ID, Integer.valueOf(C_InvoiceBatch_ID));
	}

	/** Get Invoice Batch.
		@return Expense Invoice Batch Header
	  */
	public int getC_InvoiceBatch_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceBatch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_InvoiceBatch_UU.
		@param C_InvoiceBatch_UU C_InvoiceBatch_UU
	*/
	public void setC_InvoiceBatch_UU (String C_InvoiceBatch_UU)
	{
		set_Value (COLUMNNAME_C_InvoiceBatch_UU, C_InvoiceBatch_UU);
	}

	/** Get C_InvoiceBatch_UU.
		@return C_InvoiceBatch_UU	  */
	public String getC_InvoiceBatch_UU()
	{
		return (String)get_Value(COLUMNNAME_C_InvoiceBatch_UU);
	}

	/** Set Control Amount.
		@param ControlAmt If not zero, the Debit amount of the document must be equal this amount
	*/
	public void setControlAmt (BigDecimal ControlAmt)
	{
		set_Value (COLUMNNAME_ControlAmt, ControlAmt);
	}

	/** Get Control Amount.
		@return If not zero, the Debit amount of the document must be equal this amount
	  */
	public BigDecimal getControlAmt()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ControlAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Document Date.
		@param DateDoc Date of the Document
	*/
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
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

	/** Set Document Amt.
		@param DocumentAmt Document Amount
	*/
	public void setDocumentAmt (BigDecimal DocumentAmt)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentAmt, DocumentAmt);
	}

	/** Get Document Amt.
		@return Document Amount
	  */
	public BigDecimal getDocumentAmt()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DocumentAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Document No.
		@param DocumentNo Document sequence number of the document
	*/
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo()
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
    }

	/** Set Sales Transaction.
		@param IsSOTrx This is a Sales Transaction
	*/
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx()
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed.
		@param Processed The document has been processed
	*/
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed()
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now
	*/
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing()
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getSalesRep_ID(), get_TrxName());
	}

	/** Set Sales Representative.
		@param SalesRep_ID Sales Representative or Company Agent
	*/
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1)
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account reconciled / O/S invoices verified.
		@param ZZ_Account_Reconned Account reconciled / O/S invoices verified
	*/
	public void setZZ_Account_Reconned (boolean ZZ_Account_Reconned)
	{
		set_Value (COLUMNNAME_ZZ_Account_Reconned, Boolean.valueOf(ZZ_Account_Reconned));
	}

	/** Get Account reconciled / O/S invoices verified.
		@return Account reconciled / O/S invoices verified	  */
	public boolean isZZ_Account_Reconned()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Account_Reconned);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Authorized Purchase Order/SLA Attached.
		@param ZZ_Auth_PO_Order Authorized Purchase Order/SLA Attached
	*/
	public void setZZ_Auth_PO_Order (boolean ZZ_Auth_PO_Order)
	{
		set_Value (COLUMNNAME_ZZ_Auth_PO_Order, Boolean.valueOf(ZZ_Auth_PO_Order));
	}

	/** Get Authorized Purchase Order/SLA Attached.
		@return Authorized Purchase Order/SLA Attached	  */
	public boolean isZZ_Auth_PO_Order()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Auth_PO_Order);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Calculation Checked.
		@param ZZ_Calcs_Checked Calculation Checked
	*/
	public void setZZ_Calcs_Checked (boolean ZZ_Calcs_Checked)
	{
		set_Value (COLUMNNAME_ZZ_Calcs_Checked, Boolean.valueOf(ZZ_Calcs_Checked));
	}

	/** Get Calculation Checked.
		@return Calculation Checked	  */
	public boolean isZZ_Calcs_Checked()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Calcs_Checked);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Creditor ID &amp; Banking Details Verified.
		@param ZZ_Cred_Bank_Dets_Verified Creditor ID &amp; Banking Details Verified
	*/
	public void setZZ_Cred_Bank_Dets_Verified (boolean ZZ_Cred_Bank_Dets_Verified)
	{
		set_Value (COLUMNNAME_ZZ_Cred_Bank_Dets_Verified, Boolean.valueOf(ZZ_Cred_Bank_Dets_Verified));
	}

	/** Get Creditor ID &amp; Banking Details Verified.
		@return Creditor ID &amp; Banking Details Verified	  */
	public boolean isZZ_Cred_Bank_Dets_Verified()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Cred_Bank_Dets_Verified);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set GL Allocation Checked.
		@param ZZ_GL_Allocation_Checked GL Allocation Checked
	*/
	public void setZZ_GL_Allocation_Checked (boolean ZZ_GL_Allocation_Checked)
	{
		set_Value (COLUMNNAME_ZZ_GL_Allocation_Checked, Boolean.valueOf(ZZ_GL_Allocation_Checked));
	}

	/** Get GL Allocation Checked.
		@return GL Allocation Checked	  */
	public boolean isZZ_GL_Allocation_Checked()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_GL_Allocation_Checked);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	public I_ZZ_Monthly_Levy_Files_Hdr getZZ_Monthly_Levy_Files_Hdr() throws RuntimeException
	{
		return (I_ZZ_Monthly_Levy_Files_Hdr)MTable.get(getCtx(), I_ZZ_Monthly_Levy_Files_Hdr.Table_ID)
			.getPO(getZZ_Monthly_Levy_Files_Hdr_ID(), get_TrxName());
	}

	/** Set Monthly Levy Files Hdr.
		@param ZZ_Monthly_Levy_Files_Hdr_ID Monthly Levy Files Hdr
	*/
	public void setZZ_Monthly_Levy_Files_Hdr_ID (int ZZ_Monthly_Levy_Files_Hdr_ID)
	{
		if (ZZ_Monthly_Levy_Files_Hdr_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Monthly_Levy_Files_Hdr_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Monthly_Levy_Files_Hdr_ID, Integer.valueOf(ZZ_Monthly_Levy_Files_Hdr_ID));
	}

	/** Get Monthly Levy Files Hdr.
		@return Monthly Levy Files Hdr	  */
	public int getZZ_Monthly_Levy_Files_Hdr_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Monthly_Levy_Files_Hdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Policy Procedure Checklist.
		@param ZZ_Policy_Procedure_Ck Policy Procedure Checklist
	*/
	public void setZZ_Policy_Procedure_Ck (Object ZZ_Policy_Procedure_Ck)
	{
		set_Value (COLUMNNAME_ZZ_Policy_Procedure_Ck, ZZ_Policy_Procedure_Ck);
	}

	/** Get Policy Procedure Checklist.
		@return Policy Procedure Checklist	  */
	public Object getZZ_Policy_Procedure_Ck()
	{
				return get_Value(COLUMNNAME_ZZ_Policy_Procedure_Ck);
	}

	/** Completed = C */
	public static final String ZZ_STATUS_Completed = "C";
	/** Drafted = D */
	public static final String ZZ_STATUS_Drafted = "D";
	/** In Progress = I */
	public static final String ZZ_STATUS_InProgress = "I";
	/** Set Status.
		@param ZZ_Status Status
	*/
	public void setZZ_Status (String ZZ_Status)
	{

		set_Value (COLUMNNAME_ZZ_Status, ZZ_Status);
	}

	/** Get Status.
		@return Status	  */
	public String getZZ_Status()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Status);
	}
}