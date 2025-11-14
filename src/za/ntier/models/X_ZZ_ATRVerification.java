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

/** Generated Model for ZZ_ATRVerification
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_ATRVerification")
public class X_ZZ_ATRVerification extends PO implements I_ZZ_ATRVerification, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251114L;

    /** Standard Constructor */
    public X_ZZ_ATRVerification (Properties ctx, int ZZ_ATRVerification_ID, String trxName)
    {
      super (ctx, ZZ_ATRVerification_ID, trxName);
      /** if (ZZ_ATRVerification_ID == 0)
        {
			setAD_User_ID (0);
// @#AD_User_ID@
			setName (null);
			setZZ_ATRVerification_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_ATRVerification (Properties ctx, int ZZ_ATRVerification_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_ATRVerification_ID, trxName, virtualColumns);
      /** if (ZZ_ATRVerification_ID == 0)
        {
			setAD_User_ID (0);
// @#AD_User_ID@
			setName (null);
			setZZ_ATRVerification_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_ATRVerification (Properties ctx, String ZZ_ATRVerification_UU, String trxName)
    {
      super (ctx, ZZ_ATRVerification_UU, trxName);
      /** if (ZZ_ATRVerification_UU == null)
        {
			setAD_User_ID (0);
// @#AD_User_ID@
			setName (null);
			setZZ_ATRVerification_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_ATRVerification (Properties ctx, String ZZ_ATRVerification_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_ATRVerification_UU, trxName, virtualColumns);
      /** if (ZZ_ATRVerification_UU == null)
        {
			setAD_User_ID (0);
// @#AD_User_ID@
			setName (null);
			setZZ_ATRVerification_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_ATRVerification (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_ATRVerification[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getAD_User_ID(), get_TrxName());
	}

	/** Set User/Contact.
		@param AD_User_ID User within the system - Internal or Business Partner Contact
	*/
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1)
			set_Value (COLUMNNAME_AD_User_ID, null);
		else
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
			set_ValueNoCheck (COLUMNNAME_C_Year_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
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

	public I_ZZ_ATRQueryReason getZZ_ATRQueryReason() throws RuntimeException
	{
		return (I_ZZ_ATRQueryReason)MTable.get(getCtx(), I_ZZ_ATRQueryReason.Table_ID)
			.getPO(getZZ_ATRQueryReason_ID(), get_TrxName());
	}

	/** Set Query Reason.
		@param ZZ_ATRQueryReason_ID Query Reason
	*/
	public void setZZ_ATRQueryReason_ID (int ZZ_ATRQueryReason_ID)
	{
		if (ZZ_ATRQueryReason_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_ATRQueryReason_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_ATRQueryReason_ID, Integer.valueOf(ZZ_ATRQueryReason_ID));
	}

	/** Get Query Reason.
		@return Query Reason	  */
	public int getZZ_ATRQueryReason_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_ATRQueryReason_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ATR Verification.
		@param ZZ_ATRVerification_ID ATR Verification
	*/
	public void setZZ_ATRVerification_ID (int ZZ_ATRVerification_ID)
	{
		if (ZZ_ATRVerification_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_ATRVerification_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_ATRVerification_ID, Integer.valueOf(ZZ_ATRVerification_ID));
	}

	/** Get ATR Verification.
		@return ATR Verification	  */
	public int getZZ_ATRVerification_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_ATRVerification_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_ATRVerification_UU.
		@param ZZ_ATRVerification_UU ZZ_ATRVerification_UU
	*/
	public void setZZ_ATRVerification_UU (String ZZ_ATRVerification_UU)
	{
		set_Value (COLUMNNAME_ZZ_ATRVerification_UU, ZZ_ATRVerification_UU);
	}

	/** Get ZZ_ATRVerification_UU.
		@return ZZ_ATRVerification_UU	  */
	public String getZZ_ATRVerification_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_ATRVerification_UU);
	}

	public I_ZZ_ATR_Checklist getZZ_ATR_Checklist() throws RuntimeException
	{
		return (I_ZZ_ATR_Checklist)MTable.get(getCtx(), I_ZZ_ATR_Checklist.Table_ID)
			.getPO(getZZ_ATR_Checklist_ID(), get_TrxName());
	}

	/** Set ATR Checklist.
		@param ZZ_ATR_Checklist_ID ATR Checklist
	*/
	public void setZZ_ATR_Checklist_ID (int ZZ_ATR_Checklist_ID)
	{
		if (ZZ_ATR_Checklist_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_ATR_Checklist_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_ATR_Checklist_ID, Integer.valueOf(ZZ_ATR_Checklist_ID));
	}

	/** Get ATR Checklist.
		@return ATR Checklist	  */
	public int getZZ_ATR_Checklist_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_ATR_Checklist_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Submission Date.
		@param ZZ_Submission_Date Submission Date
	*/
	public void setZZ_Submission_Date (Timestamp ZZ_Submission_Date)
	{
		set_Value (COLUMNNAME_ZZ_Submission_Date, ZZ_Submission_Date);
	}

	/** Get Submission Date.
		@return Submission Date	  */
	public Timestamp getZZ_Submission_Date()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Submission_Date);
	}

	/** Set Verification Comment.
		@param ZZ_Verification_Comment Verification Comment
	*/
	public void setZZ_Verification_Comment (String ZZ_Verification_Comment)
	{
		set_Value (COLUMNNAME_ZZ_Verification_Comment, ZZ_Verification_Comment);
	}

	/** Get Verification Comment.
		@return Verification Comment	  */
	public String getZZ_Verification_Comment()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Verification_Comment);
	}

	/** Not Recommended for Evaluation = Not Recommended for Evaluation */
	public static final String ZZ_VERIFICATION_STATUS_NotRecommendedForEvaluation = "Not Recommended for Evaluation";
	/** Query = Query */
	public static final String ZZ_VERIFICATION_STATUS_Query = "Query";
	/** Recommended for Evaluation = Recommended for Evaluation */
	public static final String ZZ_VERIFICATION_STATUS_RecommendedForEvaluation = "Recommended for Evaluation";
	/** Set Verification Status.
		@param ZZ_Verification_Status Verification Status
	*/
	public void setZZ_Verification_Status (String ZZ_Verification_Status)
	{

		set_Value (COLUMNNAME_ZZ_Verification_Status, ZZ_Verification_Status);
	}

	/** Get Verification Status.
		@return Verification Status	  */
	public String getZZ_Verification_Status()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Verification_Status);
	}
}