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
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_User
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="AD_User")
public class X_AD_User extends PO implements I_AD_User, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241011L;

    /** Standard Constructor */
    public X_AD_User (Properties ctx, int AD_User_ID, String trxName)
    {
      super (ctx, AD_User_ID, trxName);
      /** if (AD_User_ID == 0)
        {
			setAD_User_ID (0);
			setFailedLoginCount (0);
// 0
			setIsAddMailTextAutomatically (false);
// N
			setIsBillTo (false);
// N
			setIsEmployee (false);
// N
			setIsExpired (false);
// N
			setIsFullBPAccess (true);
// Y
			setIsInPayroll (false);
// N
			setIsLocked (false);
// 'N'
			setIsNoExpire (false);
// N
			setIsNoPasswordReset (false);
// 'N'
			setIsSalesLead (false);
// N
			setIsSalesRep (false);
// N
			setIsShipTo (false);
// N
			setIsSupportUser (false);
// N
			setIsVendorLead (false);
// N
			setName (null);
			setNotificationType (null);
// X
        } */
    }

    /** Standard Constructor */
    public X_AD_User (Properties ctx, int AD_User_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, AD_User_ID, trxName, virtualColumns);
      /** if (AD_User_ID == 0)
        {
			setAD_User_ID (0);
			setFailedLoginCount (0);
// 0
			setIsAddMailTextAutomatically (false);
// N
			setIsBillTo (false);
// N
			setIsEmployee (false);
// N
			setIsExpired (false);
// N
			setIsFullBPAccess (true);
// Y
			setIsInPayroll (false);
// N
			setIsLocked (false);
// 'N'
			setIsNoExpire (false);
// N
			setIsNoPasswordReset (false);
// 'N'
			setIsSalesLead (false);
// N
			setIsSalesRep (false);
// N
			setIsShipTo (false);
// N
			setIsSupportUser (false);
// N
			setIsVendorLead (false);
// N
			setName (null);
			setNotificationType (null);
// X
        } */
    }

    /** Standard Constructor */
    public X_AD_User (Properties ctx, String AD_User_UU, String trxName)
    {
      super (ctx, AD_User_UU, trxName);
      /** if (AD_User_UU == null)
        {
			setAD_User_ID (0);
			setFailedLoginCount (0);
// 0
			setIsAddMailTextAutomatically (false);
// N
			setIsBillTo (false);
// N
			setIsEmployee (false);
// N
			setIsExpired (false);
// N
			setIsFullBPAccess (true);
// Y
			setIsInPayroll (false);
// N
			setIsLocked (false);
// 'N'
			setIsNoExpire (false);
// N
			setIsNoPasswordReset (false);
// 'N'
			setIsSalesLead (false);
// N
			setIsSalesRep (false);
// N
			setIsShipTo (false);
// N
			setIsSupportUser (false);
// N
			setIsVendorLead (false);
// N
			setName (null);
			setNotificationType (null);
// X
        } */
    }

    /** Standard Constructor */
    public X_AD_User (Properties ctx, String AD_User_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, AD_User_UU, trxName, virtualColumns);
      /** if (AD_User_UU == null)
        {
			setAD_User_ID (0);
			setFailedLoginCount (0);
// 0
			setIsAddMailTextAutomatically (false);
// N
			setIsBillTo (false);
// N
			setIsEmployee (false);
// N
			setIsExpired (false);
// N
			setIsFullBPAccess (true);
// Y
			setIsInPayroll (false);
// N
			setIsLocked (false);
// 'N'
			setIsNoExpire (false);
// N
			setIsNoPasswordReset (false);
// 'N'
			setIsSalesLead (false);
// N
			setIsSalesRep (false);
// N
			setIsShipTo (false);
// N
			setIsSupportUser (false);
// N
			setIsVendorLead (false);
// N
			setName (null);
			setNotificationType (null);
// X
        } */
    }

    /** Load Constructor */
    public X_AD_User (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org
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
      StringBuilder sb = new StringBuilder ("X_AD_User[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Image.
		@param AD_Image_ID Image or Icon
	*/
	public void setAD_Image_ID (int AD_Image_ID)
	{
		if (AD_Image_ID < 1)
			set_Value (COLUMNNAME_AD_Image_ID, null);
		else
			set_Value (COLUMNNAME_AD_Image_ID, Integer.valueOf(AD_Image_ID));
	}

	/** Get Image.
		@return Image or Icon
	  */
	public int getAD_Image_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Image_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Trx Organization.
		@param AD_OrgTrx_ID Performing or initiating organization
	*/
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1)
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Organization.
		@return Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User/Contact.
		@param AD_User_ID User within the system - Internal or Business Partner Contact
	*/
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
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

	/** Set AD_User_UU.
		@param AD_User_UU AD_User_UU
	*/
	public void setAD_User_UU (String AD_User_UU)
	{
		set_Value (COLUMNNAME_AD_User_UU, AD_User_UU);
	}

	/** Get AD_User_UU.
		@return AD_User_UU	  */
	public String getAD_User_UU()
	{
		return (String)get_Value(COLUMNNAME_AD_User_UU);
	}

	/** Set Answer.
		@param Answer Answer
	*/
	public void setAnswer (String Answer)
	{
		set_Value (COLUMNNAME_Answer, Answer);
	}

	/** Get Answer.
		@return Answer	  */
	public String getAnswer()
	{
		return (String)get_Value(COLUMNNAME_Answer);
	}

	/** AuthenticationType AD_Reference_ID=200239 */
	public static final int AUTHENTICATIONTYPE_AD_Reference_ID=200239;
	/** Application and SSO = AAS */
	public static final String AUTHENTICATIONTYPE_ApplicationAndSSO = "AAS";
	/** Application Only = APO */
	public static final String AUTHENTICATIONTYPE_ApplicationOnly = "APO";
	/** SSO Only = SSO */
	public static final String AUTHENTICATIONTYPE_SSOOnly = "SSO";
	/** Set Authentication Type.
		@param AuthenticationType Authentication Type
	*/
	public void setAuthenticationType (String AuthenticationType)
	{

		set_Value (COLUMNNAME_AuthenticationType, AuthenticationType);
	}

	/** Get Authentication Type.
		@return Authentication Type	  */
	public String getAuthenticationType()
	{
		return (String)get_Value(COLUMNNAME_AuthenticationType);
	}

	/** Set BP Name.
		@param BPName BP Name
	*/
	public void setBPName (String BPName)
	{
		set_Value (COLUMNNAME_BPName, BPName);
	}

	/** Get BP Name.
		@return BP Name	  */
	public String getBPName()
	{
		return (String)get_Value(COLUMNNAME_BPName);
	}

	public I_C_Location getBP_Location() throws RuntimeException
	{
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_ID)
			.getPO(getBP_Location_ID(), get_TrxName());
	}

	/** Set BP Address.
		@param BP_Location_ID Address of the Business Partner
	*/
	public void setBP_Location_ID (int BP_Location_ID)
	{
		if (BP_Location_ID < 1)
			set_Value (COLUMNNAME_BP_Location_ID, null);
		else
			set_Value (COLUMNNAME_BP_Location_ID, Integer.valueOf(BP_Location_ID));
	}

	/** Get BP Address.
		@return Address of the Business Partner
	  */
	public int getBP_Location_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BP_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Birthday.
		@param Birthday Birthday or Anniversary day
	*/
	public void setBirthday (Timestamp Birthday)
	{
		set_Value (COLUMNNAME_Birthday, Birthday);
	}

	/** Get Birthday.
		@return Birthday or Anniversary day
	  */
	public Timestamp getBirthday()
	{
		return (Timestamp)get_Value(COLUMNNAME_Birthday);
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

	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException
	{
		return (org.compiere.model.I_C_Campaign)MTable.get(getCtx(), org.compiere.model.I_C_Campaign.Table_ID)
			.getPO(getC_Campaign_ID(), get_TrxName());
	}

	/** Set Campaign.
		@param C_Campaign_ID Marketing Campaign
	*/
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1)
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Campaign.
		@return Marketing Campaign
	  */
	public int getC_Campaign_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Greeting getC_Greeting() throws RuntimeException
	{
		return (org.compiere.model.I_C_Greeting)MTable.get(getCtx(), org.compiere.model.I_C_Greeting.Table_ID)
			.getPO(getC_Greeting_ID(), get_TrxName());
	}

	/** Set Greeting.
		@param C_Greeting_ID Greeting to print on correspondence
	*/
	public void setC_Greeting_ID (int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1)
			set_Value (COLUMNNAME_C_Greeting_ID, null);
		else
			set_Value (COLUMNNAME_C_Greeting_ID, Integer.valueOf(C_Greeting_ID));
	}

	/** Get Greeting.
		@return Greeting to print on correspondence
	  */
	public int getC_Greeting_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Greeting_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Job getC_Job() throws RuntimeException
	{
		return (org.compiere.model.I_C_Job)MTable.get(getCtx(), org.compiere.model.I_C_Job.Table_ID)
			.getPO(getC_Job_ID(), get_TrxName());
	}

	/** Set Position.
		@param C_Job_ID Job Position
	*/
	public void setC_Job_ID (int C_Job_ID)
	{
		if (C_Job_ID < 1)
			set_Value (COLUMNNAME_C_Job_ID, null);
		else
			set_Value (COLUMNNAME_C_Job_ID, Integer.valueOf(C_Job_ID));
	}

	/** Get Position.
		@return Job Position
	  */
	public int getC_Job_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Job_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Location getC_Location() throws RuntimeException
	{
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_ID)
			.getPO(getC_Location_ID(), get_TrxName());
	}

	/** Set Address.
		@param C_Location_ID Location or Address
	*/
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1)
			set_Value (COLUMNNAME_C_Location_ID, null);
		else
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
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

	/** Set Date Account Locked.
		@param DateAccountLocked Date Account Locked
	*/
	public void setDateAccountLocked (Timestamp DateAccountLocked)
	{
		set_Value (COLUMNNAME_DateAccountLocked, DateAccountLocked);
	}

	/** Get Date Account Locked.
		@return Date Account Locked	  */
	public Timestamp getDateAccountLocked()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAccountLocked);
	}

	/** Set Date Last Login.
		@param DateLastLogin Date Last Login
	*/
	public void setDateLastLogin (Timestamp DateLastLogin)
	{
		set_Value (COLUMNNAME_DateLastLogin, DateLastLogin);
	}

	/** Get Date Last Login.
		@return Date Last Login	  */
	public Timestamp getDateLastLogin()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLastLogin);
	}

	/** Set Date Password Changed.
		@param DatePasswordChanged Date Password Changed
	*/
	public void setDatePasswordChanged (Timestamp DatePasswordChanged)
	{
		set_Value (COLUMNNAME_DatePasswordChanged, DatePasswordChanged);
	}

	/** Get Date Password Changed.
		@return Date Password Changed	  */
	public Timestamp getDatePasswordChanged()
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePasswordChanged);
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

	/** Set EMail Address.
		@param EMail Electronic Mail Address
	*/
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail()
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set EMail User ID.
		@param EMailUser User Name (ID) in the Mail System
	*/
	public void setEMailUser (String EMailUser)
	{
		set_Value (COLUMNNAME_EMailUser, EMailUser);
	}

	/** Get EMail User ID.
		@return User Name (ID) in the Mail System
	  */
	public String getEMailUser()
	{
		return (String)get_Value(COLUMNNAME_EMailUser);
	}

	/** Set EMail User Password.
		@param EMailUserPW Password of your email user id
	*/
	public void setEMailUserPW (String EMailUserPW)
	{
		set_Value (COLUMNNAME_EMailUserPW, EMailUserPW);
	}

	/** Get EMail User Password.
		@return Password of your email user id
	  */
	public String getEMailUserPW()
	{
		return (String)get_Value(COLUMNNAME_EMailUserPW);
	}

	/** Set Verification Info.
		@param EMailVerify Verification information of EMail Address
	*/
	public void setEMailVerify (String EMailVerify)
	{
		set_ValueNoCheck (COLUMNNAME_EMailVerify, EMailVerify);
	}

	/** Get Verification Info.
		@return Verification information of EMail Address
	  */
	public String getEMailVerify()
	{
		return (String)get_Value(COLUMNNAME_EMailVerify);
	}

	/** Set EMail Verify.
		@param EMailVerifyDate Date Email was verified
	*/
	public void setEMailVerifyDate (Timestamp EMailVerifyDate)
	{
		set_ValueNoCheck (COLUMNNAME_EMailVerifyDate, EMailVerifyDate);
	}

	/** Get EMail Verify.
		@return Date Email was verified
	  */
	public Timestamp getEMailVerifyDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_EMailVerifyDate);
	}

	/** Set Failed Login Count.
		@param FailedLoginCount Failed Login Count
	*/
	public void setFailedLoginCount (int FailedLoginCount)
	{
		set_Value (COLUMNNAME_FailedLoginCount, Integer.valueOf(FailedLoginCount));
	}

	/** Get Failed Login Count.
		@return Failed Login Count	  */
	public int getFailedLoginCount()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FailedLoginCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fax.
		@param Fax Facsimile number
	*/
	public void setFax (String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	/** Get Fax.
		@return Facsimile number
	  */
	public String getFax()
	{
		return (String)get_Value(COLUMNNAME_Fax);
	}

	/** Set Add Mail Text Automatically.
		@param IsAddMailTextAutomatically The selected mail template will be automatically inserted when creating an email
	*/
	public void setIsAddMailTextAutomatically (boolean IsAddMailTextAutomatically)
	{
		set_Value (COLUMNNAME_IsAddMailTextAutomatically, Boolean.valueOf(IsAddMailTextAutomatically));
	}

	/** Get Add Mail Text Automatically.
		@return The selected mail template will be automatically inserted when creating an email
	  */
	public boolean isAddMailTextAutomatically()
	{
		Object oo = get_Value(COLUMNNAME_IsAddMailTextAutomatically);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Invoice Address.
		@param IsBillTo Business Partner Invoice/Bill Address
	*/
	public void setIsBillTo (boolean IsBillTo)
	{
		set_Value (COLUMNNAME_IsBillTo, Boolean.valueOf(IsBillTo));
	}

	/** Get Invoice Address.
		@return Business Partner Invoice/Bill Address
	  */
	public boolean isBillTo()
	{
		Object oo = get_Value(COLUMNNAME_IsBillTo);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Employee.
		@param IsEmployee Indicates if  this Business Partner is an employee
	*/
	public void setIsEmployee (boolean IsEmployee)
	{
		set_Value (COLUMNNAME_IsEmployee, Boolean.valueOf(IsEmployee));
	}

	/** Get Employee.
		@return Indicates if  this Business Partner is an employee
	  */
	public boolean isEmployee()
	{
		Object oo = get_Value(COLUMNNAME_IsEmployee);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Expired.
		@param IsExpired Expired
	*/
	public void setIsExpired (boolean IsExpired)
	{
		set_Value (COLUMNNAME_IsExpired, Boolean.valueOf(IsExpired));
	}

	/** Get Expired.
		@return Expired	  */
	public boolean isExpired()
	{
		Object oo = get_Value(COLUMNNAME_IsExpired);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Full BP Access.
		@param IsFullBPAccess The user/contact has full access to Business Partner information and resources
	*/
	public void setIsFullBPAccess (boolean IsFullBPAccess)
	{
		set_Value (COLUMNNAME_IsFullBPAccess, Boolean.valueOf(IsFullBPAccess));
	}

	/** Get Full BP Access.
		@return The user/contact has full access to Business Partner information and resources
	  */
	public boolean isFullBPAccess()
	{
		Object oo = get_Value(COLUMNNAME_IsFullBPAccess);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is In Payroll.
		@param IsInPayroll Defined if any User Contact will be used for Calculate Payroll
	*/
	public void setIsInPayroll (boolean IsInPayroll)
	{
		set_Value (COLUMNNAME_IsInPayroll, Boolean.valueOf(IsInPayroll));
	}

	/** Get Is In Payroll.
		@return Defined if any User Contact will be used for Calculate Payroll
	  */
	public boolean isInPayroll()
	{
		Object oo = get_Value(COLUMNNAME_IsInPayroll);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Locked.
		@param IsLocked Locked
	*/
	public void setIsLocked (boolean IsLocked)
	{
		set_Value (COLUMNNAME_IsLocked, Boolean.valueOf(IsLocked));
	}

	/** Get Locked.
		@return Locked	  */
	public boolean isLocked()
	{
		Object oo = get_Value(COLUMNNAME_IsLocked);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** IsMenuAutoExpand AD_Reference_ID=319 */
	public static final int ISMENUAUTOEXPAND_AD_Reference_ID=319;
	/** No = N */
	public static final String ISMENUAUTOEXPAND_No = "N";
	/** Yes = Y */
	public static final String ISMENUAUTOEXPAND_Yes = "Y";
	/** Set Auto expand menu.
		@param IsMenuAutoExpand If ticked, the menu is automatically expanded
	*/
	public void setIsMenuAutoExpand (String IsMenuAutoExpand)
	{

		set_Value (COLUMNNAME_IsMenuAutoExpand, IsMenuAutoExpand);
	}

	/** Get Auto expand menu.
		@return If ticked, the menu is automatically expanded
	  */
	public String getIsMenuAutoExpand()
	{
		return (String)get_Value(COLUMNNAME_IsMenuAutoExpand);
	}

	/** Set No Expire.
		@param IsNoExpire No Expire
	*/
	public void setIsNoExpire (boolean IsNoExpire)
	{
		set_Value (COLUMNNAME_IsNoExpire, Boolean.valueOf(IsNoExpire));
	}

	/** Get No Expire.
		@return No Expire	  */
	public boolean isNoExpire()
	{
		Object oo = get_Value(COLUMNNAME_IsNoExpire);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set No Password Reset.
		@param IsNoPasswordReset No Password Reset
	*/
	public void setIsNoPasswordReset (boolean IsNoPasswordReset)
	{
		set_Value (COLUMNNAME_IsNoPasswordReset, Boolean.valueOf(IsNoPasswordReset));
	}

	/** Get No Password Reset.
		@return No Password Reset	  */
	public boolean isNoPasswordReset()
	{
		Object oo = get_Value(COLUMNNAME_IsNoPasswordReset);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Lead.
		@param IsSalesLead This contact is a sales lead
	*/
	public void setIsSalesLead (boolean IsSalesLead)
	{
		set_Value (COLUMNNAME_IsSalesLead, Boolean.valueOf(IsSalesLead));
	}

	/** Get Sales Lead.
		@return This contact is a sales lead
	  */
	public boolean isSalesLead()
	{
		Object oo = get_Value(COLUMNNAME_IsSalesLead);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Representative.
		@param IsSalesRep Indicates if  the business partner is a sales representative or company agent
	*/
	public void setIsSalesRep (boolean IsSalesRep)
	{
		set_Value (COLUMNNAME_IsSalesRep, Boolean.valueOf(IsSalesRep));
	}

	/** Get Sales Representative.
		@return Indicates if  the business partner is a sales representative or company agent
	  */
	public boolean isSalesRep()
	{
		Object oo = get_Value(COLUMNNAME_IsSalesRep);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ship Address.
		@param IsShipTo Business Partner Shipment Address
	*/
	public void setIsShipTo (boolean IsShipTo)
	{
		set_Value (COLUMNNAME_IsShipTo, Boolean.valueOf(IsShipTo));
	}

	/** Get Ship Address.
		@return Business Partner Shipment Address
	  */
	public boolean isShipTo()
	{
		Object oo = get_Value(COLUMNNAME_IsShipTo);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Support User.
		@param IsSupportUser Support User
	*/
	public void setIsSupportUser (boolean IsSupportUser)
	{
		set_Value (COLUMNNAME_IsSupportUser, Boolean.valueOf(IsSupportUser));
	}

	/** Get Support User.
		@return Support User	  */
	public boolean isSupportUser()
	{
		Object oo = get_Value(COLUMNNAME_IsSupportUser);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Vendor Lead.
		@param IsVendorLead This contact is a vendor lead
	*/
	public void setIsVendorLead (boolean IsVendorLead)
	{
		set_Value (COLUMNNAME_IsVendorLead, Boolean.valueOf(IsVendorLead));
	}

	/** Get Vendor Lead.
		@return This contact is a vendor lead
	  */
	public boolean isVendorLead()
	{
		Object oo = get_Value(COLUMNNAME_IsVendorLead);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set LDAP User Name.
		@param LDAPUser User Name used for authorization via LDAP (directory) services
	*/
	public void setLDAPUser (String LDAPUser)
	{
		set_Value (COLUMNNAME_LDAPUser, LDAPUser);
	}

	/** Get LDAP User Name.
		@return User Name used for authorization via LDAP (directory) services
	  */
	public String getLDAPUser()
	{
		return (String)get_Value(COLUMNNAME_LDAPUser);
	}

	/** Set Last Contact.
		@param LastContact Date this individual was last contacted
	*/
	public void setLastContact (Timestamp LastContact)
	{
		set_Value (COLUMNNAME_LastContact, LastContact);
	}

	/** Get Last Contact.
		@return Date this individual was last contacted
	  */
	public Timestamp getLastContact()
	{
		return (Timestamp)get_Value(COLUMNNAME_LastContact);
	}

	/** Set Last Result.
		@param LastResult Result of last contact
	*/
	public void setLastResult (String LastResult)
	{
		set_Value (COLUMNNAME_LastResult, LastResult);
	}

	/** Get Last Result.
		@return Result of last contact
	  */
	public String getLastResult()
	{
		return (String)get_Value(COLUMNNAME_LastResult);
	}

	/** LeadSource AD_Reference_ID=53415 */
	public static final int LEADSOURCE_AD_Reference_ID=53415;
	/** Cold Call = CC */
	public static final String LEADSOURCE_ColdCall = "CC";
	/** Conference = CN */
	public static final String LEADSOURCE_Conference = "CN";
	/** Existing Customer = EC */
	public static final String LEADSOURCE_ExistingCustomer = "EC";
	/** Email = EL */
	public static final String LEADSOURCE_Email = "EL";
	/** Employee = EM */
	public static final String LEADSOURCE_Employee = "EM";
	/** Partner = PT */
	public static final String LEADSOURCE_Partner = "PT";
	/** Trade Show = TS */
	public static final String LEADSOURCE_TradeShow = "TS";
	/** Word of Mouth = WM */
	public static final String LEADSOURCE_WordOfMouth = "WM";
	/** Web Site = WS */
	public static final String LEADSOURCE_WebSite = "WS";
	/** Set Lead Source.
		@param LeadSource The source of this lead/opportunity
	*/
	public void setLeadSource (String LeadSource)
	{

		set_Value (COLUMNNAME_LeadSource, LeadSource);
	}

	/** Get Lead Source.
		@return The source of this lead/opportunity
	  */
	public String getLeadSource()
	{
		return (String)get_Value(COLUMNNAME_LeadSource);
	}

	/** Set Lead Source Description.
		@param LeadSourceDescription Additional information on the source of this lead/opportunity
	*/
	public void setLeadSourceDescription (String LeadSourceDescription)
	{
		set_Value (COLUMNNAME_LeadSourceDescription, LeadSourceDescription);
	}

	/** Get Lead Source Description.
		@return Additional information on the source of this lead/opportunity
	  */
	public String getLeadSourceDescription()
	{
		return (String)get_Value(COLUMNNAME_LeadSourceDescription);
	}

	/** LeadStatus AD_Reference_ID=53416 */
	public static final int LEADSTATUS_AD_Reference_ID=53416;
	/** Converted = C */
	public static final String LEADSTATUS_Converted = "C";
	/** Expired = E */
	public static final String LEADSTATUS_Expired = "E";
	/** New = N */
	public static final String LEADSTATUS_New = "N";
	/** Recycled = R */
	public static final String LEADSTATUS_Recycled = "R";
	/** Working = W */
	public static final String LEADSTATUS_Working = "W";
	/** Set Lead Status.
		@param LeadStatus The status of this lead/opportunity in the sales cycle
	*/
	public void setLeadStatus (String LeadStatus)
	{

		set_Value (COLUMNNAME_LeadStatus, LeadStatus);
	}

	/** Get Lead Status.
		@return The status of this lead/opportunity in the sales cycle
	  */
	public String getLeadStatus()
	{
		return (String)get_Value(COLUMNNAME_LeadStatus);
	}

	/** Set Lead Status Description.
		@param LeadStatusDescription Additional information on the status of this lead/opportunity
	*/
	public void setLeadStatusDescription (String LeadStatusDescription)
	{
		set_Value (COLUMNNAME_LeadStatusDescription, LeadStatusDescription);
	}

	/** Get Lead Status Description.
		@return Additional information on the status of this lead/opportunity
	  */
	public String getLeadStatusDescription()
	{
		return (String)get_Value(COLUMNNAME_LeadStatusDescription);
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
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Name 2.
		@param Name2 Additional Name
	*/
	public void setName2 (String Name2)
	{
		set_ValueNoCheck (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Additional Name
	  */
	public String getName2()
	{
		return (String)get_Value(COLUMNNAME_Name2);
	}

	/** NotificationType AD_Reference_ID=344 */
	public static final int NOTIFICATIONTYPE_AD_Reference_ID=344;
	/** EMail+Notice = B */
	public static final String NOTIFICATIONTYPE_EMailPlusNotice = "B";
	/** EMail = E */
	public static final String NOTIFICATIONTYPE_EMail = "E";
	/** Whatsapp + Email = M */
	public static final String NOTIFICATIONTYPE_WhatsappPlusEmail = "M";
	/** Notice = N */
	public static final String NOTIFICATIONTYPE_Notice = "N";
	/** Whatsapp = W */
	public static final String NOTIFICATIONTYPE_Whatsapp = "W";
	/** None = X */
	public static final String NOTIFICATIONTYPE_None = "X";
	/** Set Notification Type.
		@param NotificationType Type of Notifications
	*/
	public void setNotificationType (String NotificationType)
	{

		set_Value (COLUMNNAME_NotificationType, NotificationType);
	}

	/** Get Notification Type.
		@return Type of Notifications
	  */
	public String getNotificationType()
	{
		return (String)get_Value(COLUMNNAME_NotificationType);
	}

	/** Set Whatsapp Opt In.
		@param Opt_In_Date Whatsapp Opt In
	*/
	public void setOpt_In_Date (Timestamp Opt_In_Date)
	{
		set_Value (COLUMNNAME_Opt_In_Date, Opt_In_Date);
	}

	/** Get Whatsapp Opt In.
		@return Whatsapp Opt In	  */
	public Timestamp getOpt_In_Date()
	{
		return (Timestamp)get_Value(COLUMNNAME_Opt_In_Date);
	}

	/** Set Whatsapp Opt Out.
		@param Opt_Out_Date Whatsapp Opt Out
	*/
	public void setOpt_Out_Date (Timestamp Opt_Out_Date)
	{
		set_Value (COLUMNNAME_Opt_Out_Date, Opt_Out_Date);
	}

	/** Get Whatsapp Opt Out.
		@return Whatsapp Opt Out	  */
	public Timestamp getOpt_Out_Date()
	{
		return (Timestamp)get_Value(COLUMNNAME_Opt_Out_Date);
	}

	/** Set Password.
		@param Password Password of any length (case sensitive)
	*/
	public void setPassword (String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Password.
		@return Password of any length (case sensitive)
	  */
	public String getPassword()
	{
		return (String)get_Value(COLUMNNAME_Password);
	}

	/** Set Phone.
		@param Phone Identifies a telephone number
	*/
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone()
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	/** Set 2nd Phone.
		@param Phone2 Identifies an alternate telephone number.
	*/
	public void setPhone2 (String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	/** Get 2nd Phone.
		@return Identifies an alternate telephone number.
	  */
	public String getPhone2()
	{
		return (String)get_Value(COLUMNNAME_Phone2);
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

	public org.compiere.model.I_R_Category getR_Category() throws RuntimeException
	{
		return (org.compiere.model.I_R_Category)MTable.get(getCtx(), org.compiere.model.I_R_Category.Table_ID)
			.getPO(getR_Category_ID(), get_TrxName());
	}

	/** Set Category.
		@param R_Category_ID Request Category
	*/
	public void setR_Category_ID (int R_Category_ID)
	{
		if (R_Category_ID < 1)
			set_ValueNoCheck (COLUMNNAME_R_Category_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_R_Category_ID, Integer.valueOf(R_Category_ID));
	}

	/** Get Category.
		@return Request Category
	  */
	public int getR_Category_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_R_MailText getR_DefaultMailText() throws RuntimeException
	{
		return (org.compiere.model.I_R_MailText)MTable.get(getCtx(), org.compiere.model.I_R_MailText.Table_ID)
			.getPO(getR_DefaultMailText_ID(), get_TrxName());
	}

	/** Set Default mail template.
		@param R_DefaultMailText_ID Default mail template
	*/
	public void setR_DefaultMailText_ID (int R_DefaultMailText_ID)
	{
		if (R_DefaultMailText_ID < 1)
			set_Value (COLUMNNAME_R_DefaultMailText_ID, null);
		else
			set_Value (COLUMNNAME_R_DefaultMailText_ID, Integer.valueOf(R_DefaultMailText_ID));
	}

	/** Get Default mail template.
		@return Default mail template	  */
	public int getR_DefaultMailText_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_DefaultMailText_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_R_Group getR_Group() throws RuntimeException
	{
		return (org.compiere.model.I_R_Group)MTable.get(getCtx(), org.compiere.model.I_R_Group.Table_ID)
			.getPO(getR_Group_ID(), get_TrxName());
	}

	/** Set Group.
		@param R_Group_ID Request Group
	*/
	public void setR_Group_ID (int R_Group_ID)
	{
		if (R_Group_ID < 1)
			set_ValueNoCheck (COLUMNNAME_R_Group_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_R_Group_ID, Integer.valueOf(R_Group_ID));
	}

	/** Get Group.
		@return Request Group
	  */
	public int getR_Group_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_R_RequestType getR_RequestType() throws RuntimeException
	{
		return (org.compiere.model.I_R_RequestType)MTable.get(getCtx(), org.compiere.model.I_R_RequestType.Table_ID)
			.getPO(getR_RequestType_ID(), get_TrxName());
	}

	/** Set Request Type.
		@param R_RequestType_ID Type of request (e.g. Inquiry, Complaint, ..)
	*/
	public void setR_RequestType_ID (int R_RequestType_ID)
	{
		if (R_RequestType_ID < 1)
			set_Value (COLUMNNAME_R_RequestType_ID, null);
		else
			set_Value (COLUMNNAME_R_RequestType_ID, Integer.valueOf(R_RequestType_ID));
	}

	/** Get Request Type.
		@return Type of request (e.g. Inquiry, Complaint, ..)
	  */
	public int getR_RequestType_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Salt.
		@param Salt Random data added to improve password hash effectiveness
	*/
	public void setSalt (String Salt)
	{
		set_ValueNoCheck (COLUMNNAME_Salt, Salt);
	}

	/** Get Salt.
		@return Random data added to improve password hash effectiveness
	  */
	public String getSalt()
	{
		return (String)get_Value(COLUMNNAME_Salt);
	}

	/** Set Security Question.
		@param SecurityQuestion Security Question
	*/
	public void setSecurityQuestion (String SecurityQuestion)
	{
		set_Value (COLUMNNAME_SecurityQuestion, SecurityQuestion);
	}

	/** Get Security Question.
		@return Security Question	  */
	public String getSecurityQuestion()
	{
		return (String)get_Value(COLUMNNAME_SecurityQuestion);
	}

	public org.compiere.model.I_AD_User getSupervisor() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getSupervisor_ID(), get_TrxName());
	}

	/** Set Supervisor.
		@param Supervisor_ID Supervisor for this user/organization - used for escalation and approval
	*/
	public void setSupervisor_ID (int Supervisor_ID)
	{
		if (Supervisor_ID < 1)
			set_Value (COLUMNNAME_Supervisor_ID, null);
		else
			set_Value (COLUMNNAME_Supervisor_ID, Integer.valueOf(Supervisor_ID));
	}

	/** Get Supervisor.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Title.
		@param Title Name this entity is referred to as
	*/
	public void setTitle (String Title)
	{
		set_Value (COLUMNNAME_Title, Title);
	}

	/** Get Title.
		@return Name this entity is referred to as
	  */
	public String getTitle()
	{
		return (String)get_Value(COLUMNNAME_Title);
	}

	/** Set User PIN.
		@param UserPIN User PIN
	*/
	public void setUserPIN (String UserPIN)
	{
		set_Value (COLUMNNAME_UserPIN, UserPIN);
	}

	/** Get User PIN.
		@return User PIN	  */
	public String getUserPIN()
	{
		return (String)get_Value(COLUMNNAME_UserPIN);
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

	/** Nooitgedacht = RGN 1 */
	public static final String ZZ_COMPANY_Nooitgedacht = "RGN 1";
	/** Uitvalgronde = RGN 2 */
	public static final String ZZ_COMPANY_Uitvalgronde = "RGN 2";
	/** Kriel Coal = RGN 3 */
	public static final String ZZ_COMPANY_KrielCoal = "RGN 3";
	/** Brits WPlant = RGN 4 */
	public static final String ZZ_COMPANY_BritsWPlant = "RGN 4";
	/** Set Company.
		@param ZZ_Company Company
	*/
	public void setZZ_Company (String ZZ_Company)
	{

		set_Value (COLUMNNAME_ZZ_Company, ZZ_Company);
	}

	/** Get Company.
		@return Company	  */
	public String getZZ_Company()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Company);
	}
}