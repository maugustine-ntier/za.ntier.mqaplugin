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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_BP_BankAccount
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="C_BP_BankAccount")
public class X_C_BP_BankAccount extends PO implements I_C_BP_BankAccount, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250116L;

    /** Standard Constructor */
    public X_C_BP_BankAccount (Properties ctx, int C_BP_BankAccount_ID, String trxName)
    {
      super (ctx, C_BP_BankAccount_ID, trxName);
      /** if (C_BP_BankAccount_ID == 0)
        {
			setA_Name (null);
			setC_BP_BankAccount_ID (0);
			setC_BPartner_ID (0);
			setIsACH (false);
			setZZ_Approve (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_C_BP_BankAccount (Properties ctx, int C_BP_BankAccount_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, C_BP_BankAccount_ID, trxName, virtualColumns);
      /** if (C_BP_BankAccount_ID == 0)
        {
			setA_Name (null);
			setC_BP_BankAccount_ID (0);
			setC_BPartner_ID (0);
			setIsACH (false);
			setZZ_Approve (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_C_BP_BankAccount (Properties ctx, String C_BP_BankAccount_UU, String trxName)
    {
      super (ctx, C_BP_BankAccount_UU, trxName);
      /** if (C_BP_BankAccount_UU == null)
        {
			setA_Name (null);
			setC_BP_BankAccount_ID (0);
			setC_BPartner_ID (0);
			setIsACH (false);
			setZZ_Approve (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_C_BP_BankAccount (Properties ctx, String C_BP_BankAccount_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, C_BP_BankAccount_UU, trxName, virtualColumns);
      /** if (C_BP_BankAccount_UU == null)
        {
			setA_Name (null);
			setC_BP_BankAccount_ID (0);
			setC_BPartner_ID (0);
			setIsACH (false);
			setZZ_Approve (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_BP_BankAccount (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_BP_BankAccount[")
        .append(get_ID()).append("]");
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

	/** Set Account City.
		@param A_City City or the Credit Card or Account Holder
	*/
	public void setA_City (String A_City)
	{
		set_Value (COLUMNNAME_A_City, A_City);
	}

	/** Get Account City.
		@return City or the Credit Card or Account Holder
	  */
	public String getA_City()
	{
		return (String)get_Value(COLUMNNAME_A_City);
	}

	/** Set Account Country.
		@param A_Country Country
	*/
	public void setA_Country (String A_Country)
	{
		set_Value (COLUMNNAME_A_Country, A_Country);
	}

	/** Get Account Country.
		@return Country
	  */
	public String getA_Country()
	{
		return (String)get_Value(COLUMNNAME_A_Country);
	}

	/** Set Account EMail.
		@param A_EMail Email Address
	*/
	public void setA_EMail (String A_EMail)
	{
		set_Value (COLUMNNAME_A_EMail, A_EMail);
	}

	/** Get Account EMail.
		@return Email Address
	  */
	public String getA_EMail()
	{
		return (String)get_Value(COLUMNNAME_A_EMail);
	}

	/** Set Driver License.
		@param A_Ident_DL Payment Identification - Driver License
	*/
	public void setA_Ident_DL (String A_Ident_DL)
	{
		set_Value (COLUMNNAME_A_Ident_DL, A_Ident_DL);
	}

	/** Get Driver License.
		@return Payment Identification - Driver License
	  */
	public String getA_Ident_DL()
	{
		return (String)get_Value(COLUMNNAME_A_Ident_DL);
	}

	/** Set Social Security No.
		@param A_Ident_SSN Payment Identification - Social Security No
	*/
	public void setA_Ident_SSN (String A_Ident_SSN)
	{
		set_Value (COLUMNNAME_A_Ident_SSN, A_Ident_SSN);
	}

	/** Get Social Security No.
		@return Payment Identification - Social Security No
	  */
	public String getA_Ident_SSN()
	{
		return (String)get_Value(COLUMNNAME_A_Ident_SSN);
	}

	/** Set Account Name.
		@param A_Name Name on Credit Card or Account holder
	*/
	public void setA_Name (String A_Name)
	{
		set_Value (COLUMNNAME_A_Name, A_Name);
	}

	/** Get Account Name.
		@return Name on Credit Card or Account holder
	  */
	public String getA_Name()
	{
		return (String)get_Value(COLUMNNAME_A_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), getA_Name());
    }

	/** Set Account State.
		@param A_State State of the Credit Card or Account holder
	*/
	public void setA_State (String A_State)
	{
		set_Value (COLUMNNAME_A_State, A_State);
	}

	/** Get Account State.
		@return State of the Credit Card or Account holder
	  */
	public String getA_State()
	{
		return (String)get_Value(COLUMNNAME_A_State);
	}

	/** Set Account Street.
		@param A_Street Street address of the Credit Card or Account holder
	*/
	public void setA_Street (String A_Street)
	{
		set_Value (COLUMNNAME_A_Street, A_Street);
	}

	/** Get Account Street.
		@return Street address of the Credit Card or Account holder
	  */
	public String getA_Street()
	{
		return (String)get_Value(COLUMNNAME_A_Street);
	}

	/** Set Account Zip/Postal.
		@param A_Zip Zip Code of the Credit Card or Account Holder
	*/
	public void setA_Zip (String A_Zip)
	{
		set_Value (COLUMNNAME_A_Zip, A_Zip);
	}

	/** Get Account Zip/Postal.
		@return Zip Code of the Credit Card or Account Holder
	  */
	public String getA_Zip()
	{
		return (String)get_Value(COLUMNNAME_A_Zip);
	}

	/** Set Account No.
		@param AccountNo Account Number
	*/
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo()
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
	}

	/** BPBankAcctUse AD_Reference_ID=393 */
	public static final int BPBANKACCTUSE_AD_Reference_ID=393;
	/** Both = B */
	public static final String BPBANKACCTUSE_Both = "B";
	/** Direct Debit = D */
	public static final String BPBANKACCTUSE_DirectDebit = "D";
	/** None = N */
	public static final String BPBANKACCTUSE_None = "N";
	/** Direct Deposit = T */
	public static final String BPBANKACCTUSE_DirectDeposit = "T";
	/** Set Account Usage.
		@param BPBankAcctUse Business Partner Bank Account usage
	*/
	public void setBPBankAcctUse (String BPBankAcctUse)
	{

		set_Value (COLUMNNAME_BPBankAcctUse, BPBankAcctUse);
	}

	/** Get Account Usage.
		@return Business Partner Bank Account usage
	  */
	public String getBPBankAcctUse()
	{
		return (String)get_Value(COLUMNNAME_BPBankAcctUse);
	}

	/** BankAccountType AD_Reference_ID=216 */
	public static final int BANKACCOUNTTYPE_AD_Reference_ID=216;
	/** Cash = B */
	public static final String BANKACCOUNTTYPE_Cash = "B";
	/** Checking = C */
	public static final String BANKACCOUNTTYPE_Checking = "C";
	/** Card = D */
	public static final String BANKACCOUNTTYPE_Card = "D";
	/** Savings = S */
	public static final String BANKACCOUNTTYPE_Savings = "S";
	/** Set Bank Account Type.
		@param BankAccountType Bank Account Type
	*/
	public void setBankAccountType (String BankAccountType)
	{

		set_Value (COLUMNNAME_BankAccountType, BankAccountType);
	}

	/** Get Bank Account Type.
		@return Bank Account Type
	  */
	public String getBankAccountType()
	{
		return (String)get_Value(COLUMNNAME_BankAccountType);
	}

	/** Set Partner Bank Account.
		@param C_BP_BankAccount_ID Bank Account of the Business Partner
	*/
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Partner Bank Account.
		@return Bank Account of the Business Partner
	  */
	public int getC_BP_BankAccount_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BP_BankAccount_UU.
		@param C_BP_BankAccount_UU C_BP_BankAccount_UU
	*/
	public void setC_BP_BankAccount_UU (String C_BP_BankAccount_UU)
	{
		set_Value (COLUMNNAME_C_BP_BankAccount_UU, C_BP_BankAccount_UU);
	}

	/** Get C_BP_BankAccount_UU.
		@return C_BP_BankAccount_UU	  */
	public String getC_BP_BankAccount_UU()
	{
		return (String)get_Value(COLUMNNAME_C_BP_BankAccount_UU);
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

	public org.compiere.model.I_C_Bank getC_Bank() throws RuntimeException
	{
		return (org.compiere.model.I_C_Bank)MTable.get(getCtx(), org.compiere.model.I_C_Bank.Table_ID)
			.getPO(getC_Bank_ID(), get_TrxName());
	}

	/** Set Bank.
		@param C_Bank_ID Bank
	*/
	public void setC_Bank_ID (int C_Bank_ID)
	{
		if (C_Bank_ID < 1)
			set_Value (COLUMNNAME_C_Bank_ID, null);
		else
			set_Value (COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
	}

	/** Get Bank.
		@return Bank
	  */
	public int getC_Bank_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Bank_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_PaymentProcessor getC_PaymentProcessor() throws RuntimeException
	{
		return (org.compiere.model.I_C_PaymentProcessor)MTable.get(getCtx(), org.compiere.model.I_C_PaymentProcessor.Table_ID)
			.getPO(getC_PaymentProcessor_ID(), get_TrxName());
	}

	/** Set Payment Processor.
		@param C_PaymentProcessor_ID Payment processor for electronic payments
	*/
	public void setC_PaymentProcessor_ID (int C_PaymentProcessor_ID)
	{
		if (C_PaymentProcessor_ID < 1)
			set_Value (COLUMNNAME_C_PaymentProcessor_ID, null);
		else
			set_Value (COLUMNNAME_C_PaymentProcessor_ID, Integer.valueOf(C_PaymentProcessor_ID));
	}

	/** Get Payment Processor.
		@return Payment processor for electronic payments
	  */
	public int getC_PaymentProcessor_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comments.
		@param Comments Comments or additional information
	*/
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments()
	{
		return (String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Exp. Month.
		@param CreditCardExpMM Expiry Month
	*/
	public void setCreditCardExpMM (int CreditCardExpMM)
	{
		set_Value (COLUMNNAME_CreditCardExpMM, Integer.valueOf(CreditCardExpMM));
	}

	/** Get Exp. Month.
		@return Expiry Month
	  */
	public int getCreditCardExpMM()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CreditCardExpMM);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Exp. Year.
		@param CreditCardExpYY Expiry Year
	*/
	public void setCreditCardExpYY (int CreditCardExpYY)
	{
		set_Value (COLUMNNAME_CreditCardExpYY, Integer.valueOf(CreditCardExpYY));
	}

	/** Get Exp. Year.
		@return Expiry Year
	  */
	public int getCreditCardExpYY()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CreditCardExpYY);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Number.
		@param CreditCardNumber Credit Card Number 
	*/
	public void setCreditCardNumber (String CreditCardNumber)
	{
		set_Value (COLUMNNAME_CreditCardNumber, CreditCardNumber);
	}

	/** Get Number.
		@return Credit Card Number 
	  */
	public String getCreditCardNumber()
	{
		return (String)get_Value(COLUMNNAME_CreditCardNumber);
	}

	/** CreditCardType AD_Reference_ID=149 */
	public static final int CREDITCARDTYPE_AD_Reference_ID=149;
	/** Amex = A */
	public static final String CREDITCARDTYPE_Amex = "A";
	/** ATM = C */
	public static final String CREDITCARDTYPE_ATM = "C";
	/** Diners = D */
	public static final String CREDITCARDTYPE_Diners = "D";
	/** MasterCard = M */
	public static final String CREDITCARDTYPE_MasterCard = "M";
	/** Discover = N */
	public static final String CREDITCARDTYPE_Discover = "N";
	/** Purchase Card = P */
	public static final String CREDITCARDTYPE_PurchaseCard = "P";
	/** Visa = V */
	public static final String CREDITCARDTYPE_Visa = "V";
	/** Set Credit Card.
		@param CreditCardType Credit Card (Visa, MC, AmEx)
	*/
	public void setCreditCardType (String CreditCardType)
	{

		set_Value (COLUMNNAME_CreditCardType, CreditCardType);
	}

	/** Get Credit Card.
		@return Credit Card (Visa, MC, AmEx)
	  */
	public String getCreditCardType()
	{
		return (String)get_Value(COLUMNNAME_CreditCardType);
	}

	/** Set Verification Code.
		@param CreditCardVV Credit Card Verification code on credit card
	*/
	public void setCreditCardVV (String CreditCardVV)
	{
		set_Value (COLUMNNAME_CreditCardVV, CreditCardVV);
	}

	/** Get Verification Code.
		@return Credit Card Verification code on credit card
	  */
	public String getCreditCardVV()
	{
		return (String)get_Value(COLUMNNAME_CreditCardVV);
	}

	/** Set Customer Payment Profile ID.
		@param CustomerPaymentProfileID Customer Payment Profile ID
	*/
	public void setCustomerPaymentProfileID (String CustomerPaymentProfileID)
	{
		set_Value (COLUMNNAME_CustomerPaymentProfileID, CustomerPaymentProfileID);
	}

	/** Get Customer Payment Profile ID.
		@return Customer Payment Profile ID	  */
	public String getCustomerPaymentProfileID()
	{
		return (String)get_Value(COLUMNNAME_CustomerPaymentProfileID);
	}

	/** Set IBAN.
		@param IBAN International Bank Account Number
	*/
	public void setIBAN (String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	/** Get IBAN.
		@return International Bank Account Number
	  */
	public String getIBAN()
	{
		return (String)get_Value(COLUMNNAME_IBAN);
	}

	/** Set ACH.
		@param IsACH Automatic Clearing House
	*/
	public void setIsACH (boolean IsACH)
	{
		set_Value (COLUMNNAME_IsACH, Boolean.valueOf(IsACH));
	}

	/** Get ACH.
		@return Automatic Clearing House
	  */
	public boolean isACH()
	{
		Object oo = get_Value(COLUMNNAME_IsACH);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** R_AvsAddr AD_Reference_ID=213 */
	public static final int R_AVSADDR_AD_Reference_ID=213;
	/** No Match = N */
	public static final String R_AVSADDR_NoMatch = "N";
	/** Unavailable = X */
	public static final String R_AVSADDR_Unavailable = "X";
	/** Match = Y */
	public static final String R_AVSADDR_Match = "Y";
	/** Set Address verified.
		@param R_AvsAddr This address has been verified
	*/
	public void setR_AvsAddr (String R_AvsAddr)
	{

		set_ValueNoCheck (COLUMNNAME_R_AvsAddr, R_AvsAddr);
	}

	/** Get Address verified.
		@return This address has been verified
	  */
	public String getR_AvsAddr()
	{
		return (String)get_Value(COLUMNNAME_R_AvsAddr);
	}

	/** R_AvsZip AD_Reference_ID=213 */
	public static final int R_AVSZIP_AD_Reference_ID=213;
	/** No Match = N */
	public static final String R_AVSZIP_NoMatch = "N";
	/** Unavailable = X */
	public static final String R_AVSZIP_Unavailable = "X";
	/** Match = Y */
	public static final String R_AVSZIP_Match = "Y";
	/** Set Zip verified.
		@param R_AvsZip The Zip Code has been verified
	*/
	public void setR_AvsZip (String R_AvsZip)
	{

		set_ValueNoCheck (COLUMNNAME_R_AvsZip, R_AvsZip);
	}

	/** Get Zip verified.
		@return The Zip Code has been verified
	  */
	public String getR_AvsZip()
	{
		return (String)get_Value(COLUMNNAME_R_AvsZip);
	}

	/** Set Routing No.
		@param RoutingNo Bank Routing Number
	*/
	public void setRoutingNo (String RoutingNo)
	{
		set_Value (COLUMNNAME_RoutingNo, RoutingNo);
	}

	/** Get Routing No.
		@return Bank Routing Number
	  */
	public String getRoutingNo()
	{
		return (String)get_Value(COLUMNNAME_RoutingNo);
	}

	/** Set Approve.
		@param ZZ_Approve Approve
	*/
	public void setZZ_Approve (boolean ZZ_Approve)
	{
		set_Value (COLUMNNAME_ZZ_Approve, Boolean.valueOf(ZZ_Approve));
	}

	/** Get Approve.
		@return Approve	  */
	public boolean isZZ_Approve()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Approve);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Branch Name.
		@param ZZ_Branch_Name Branch Name
	*/
	public void setZZ_Branch_Name (String ZZ_Branch_Name)
	{
		set_Value (COLUMNNAME_ZZ_Branch_Name, ZZ_Branch_Name);
	}

	/** Get Branch Name.
		@return Branch Name	  */
	public String getZZ_Branch_Name()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Branch_Name);
	}

	/** Set Branch Number.
		@param ZZ_Branch_Number Branch Number
	*/
	public void setZZ_Branch_Number (String ZZ_Branch_Number)
	{
		set_Value (COLUMNNAME_ZZ_Branch_Number, ZZ_Branch_Number);
	}

	/** Get Branch Number.
		@return Branch Number	  */
	public String getZZ_Branch_Number()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Branch_Number);
	}
}