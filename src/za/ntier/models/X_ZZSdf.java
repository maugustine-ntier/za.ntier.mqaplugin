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

/** Generated Model for ZZSdf
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZSdf")
public class X_ZZSdf extends PO implements I_ZZSdf, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251126L;

    /** Standard Constructor */
    public X_ZZSdf (Properties ctx, int ZZSdf_ID, String trxName)
    {
      super (ctx, ZZSdf_ID, trxName);
      /** if (ZZSdf_ID == 0)
        {
			setZZSdf_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZSdf (Properties ctx, int ZZSdf_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZSdf_ID, trxName, virtualColumns);
      /** if (ZZSdf_ID == 0)
        {
			setZZSdf_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZSdf (Properties ctx, String ZZSdf_UU, String trxName)
    {
      super (ctx, ZZSdf_UU, trxName);
      /** if (ZZSdf_UU == null)
        {
			setZZSdf_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZSdf (Properties ctx, String ZZSdf_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZSdf_UU, trxName, virtualColumns);
      /** if (ZZSdf_UU == null)
        {
			setZZSdf_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZSdf (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZSdf[")
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

	/** Set Accredited Training Provider.
		@param ZZAccreditedTrainingProvider Accredited Training Provider
	*/
	public void setZZAccreditedTrainingProvider (String ZZAccreditedTrainingProvider)
	{
		set_Value (COLUMNNAME_ZZAccreditedTrainingProvider, ZZAccreditedTrainingProvider);
	}

	/** Get Accredited Training Provider.
		@return Accredited Training Provider	  */
	public String getZZAccreditedTrainingProvider()
	{
		return (String)get_Value(COLUMNNAME_ZZAccreditedTrainingProvider);
	}

	/** Set Current Occupation.
		@param ZZCurrentOccupation Current Occupation
	*/
	public void setZZCurrentOccupation (String ZZCurrentOccupation)
	{
		set_Value (COLUMNNAME_ZZCurrentOccupation, ZZCurrentOccupation);
	}

	/** Get Current Occupation.
		@return Current Occupation	  */
	public String getZZCurrentOccupation()
	{
		return (String)get_Value(COLUMNNAME_ZZCurrentOccupation);
	}

	/** African = Afr */
	public static final String ZZEQUITY_African = "Afr";
	/** Coloured = Col */
	public static final String ZZEQUITY_Coloured = "Col";
	/** Indian = Ind */
	public static final String ZZEQUITY_Indian = "Ind";
	/** White = Wh */
	public static final String ZZEQUITY_White = "Wh";
	/** Set Equity.
		@param ZZEquity Equity
	*/
	public void setZZEquity (String ZZEquity)
	{

		set_Value (COLUMNNAME_ZZEquity, ZZEquity);
	}

	/** Get Equity.
		@return Equity	  */
	public String getZZEquity()
	{
		return (String)get_Value(COLUMNNAME_ZZEquity);
	}

	/** Set Experience.
		@param ZZExperience Experience
	*/
	public void setZZExperience (String ZZExperience)
	{
		set_Value (COLUMNNAME_ZZExperience, ZZExperience);
	}

	/** Get Experience.
		@return Experience	  */
	public String getZZExperience()
	{
		return (String)get_Value(COLUMNNAME_ZZExperience);
	}

	/** Set First Name.
		@param ZZFirstName First Name
	*/
	public void setZZFirstName (String ZZFirstName)
	{
		set_Value (COLUMNNAME_ZZFirstName, ZZFirstName);
	}

	/** Get First Name.
		@return First Name	  */
	public String getZZFirstName()
	{
		return (String)get_Value(COLUMNNAME_ZZFirstName);
	}

	/** Famale = F */
	public static final String ZZGENDER_Famale = "F";
	/** Male = M */
	public static final String ZZGENDER_Male = "M";
	/** Other = O */
	public static final String ZZGENDER_Other = "O";
	/** Set Gender.
		@param ZZGender Gender
	*/
	public void setZZGender (String ZZGender)
	{

		set_Value (COLUMNNAME_ZZGender, ZZGender);
	}

	/** Get Gender.
		@return Gender	  */
	public String getZZGender()
	{
		return (String)get_Value(COLUMNNAME_ZZGender);
	}

	/** Set General Comments.
		@param ZZGeneralComments General Comments
	*/
	public void setZZGeneralComments (String ZZGeneralComments)
	{
		set_Value (COLUMNNAME_ZZGeneralComments, ZZGeneralComments);
	}

	/** Get General Comments.
		@return General Comments	  */
	public String getZZGeneralComments()
	{
		return (String)get_Value(COLUMNNAME_ZZGeneralComments);
	}

	/** Set Highest Education Description.
		@param ZZHighestEducationDesc Highest Education Description
	*/
	public void setZZHighestEducationDesc (String ZZHighestEducationDesc)
	{
		set_Value (COLUMNNAME_ZZHighestEducationDesc, ZZHighestEducationDesc);
	}

	/** Get Highest Education Description.
		@return Highest Education Description	  */
	public String getZZHighestEducationDesc()
	{
		return (String)get_Value(COLUMNNAME_ZZHighestEducationDesc);
	}

	/** Set Initials.
		@param ZZInitials Initials
	*/
	public void setZZInitials (String ZZInitials)
	{
		set_Value (COLUMNNAME_ZZInitials, ZZInitials);
	}

	/** Get Initials.
		@return Initials	  */
	public String getZZInitials()
	{
		return (String)get_Value(COLUMNNAME_ZZInitials);
	}

	/** Adv = Adv */
	public static final String ZZLKPTITLE_Adv = "Adv";
	/** Dr = Dr */
	public static final String ZZLKPTITLE_Dr = "Dr";
	/** Me = Me */
	public static final String ZZLKPTITLE_Me = "Me";
	/** Miss = Miss */
	public static final String ZZLKPTITLE_Miss = "Miss";
	/** Mr = Mr */
	public static final String ZZLKPTITLE_Mr = "Mr";
	/** Mrs = Mrs */
	public static final String ZZLKPTITLE_Mrs = "Mrs";
	/** Ms = Ms */
	public static final String ZZLKPTITLE_Ms = "Ms";
	/** Other = Other */
	public static final String ZZLKPTITLE_Other = "Other";
	/** Prof = Prof */
	public static final String ZZLKPTITLE_Prof = "Prof";
	/** Set Title.
		@param ZZLkpTitle Lkp Title
	*/
	public void setZZLkpTitle (String ZZLkpTitle)
	{

		set_Value (COLUMNNAME_ZZLkpTitle, ZZLkpTitle);
	}

	/** Get Title.
		@return Lkp Title
	  */
	public String getZZLkpTitle()
	{
		return (String)get_Value(COLUMNNAME_ZZLkpTitle);
	}

	/** Set Middle Name.
		@param ZZMiddleName Middle Name
	*/
	public void setZZMiddleName (String ZZMiddleName)
	{
		set_Value (COLUMNNAME_ZZMiddleName, ZZMiddleName);
	}

	/** Get Middle Name.
		@return Middle Name	  */
	public String getZZMiddleName()
	{
		return (String)get_Value(COLUMNNAME_ZZMiddleName);
	}

	/** Set Sdf.
		@param ZZSdf_ID Sdf
	*/
	public void setZZSdf_ID (int ZZSdf_ID)
	{
		if (ZZSdf_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZSdf_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZSdf_ID, Integer.valueOf(ZZSdf_ID));
	}

	/** Get Sdf.
		@return Sdf	  */
	public int getZZSdf_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZSdf_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZSdf_UU.
		@param ZZSdf_UU ZZSdf_UU
	*/
	public void setZZSdf_UU (String ZZSdf_UU)
	{
		set_Value (COLUMNNAME_ZZSdf_UU, ZZSdf_UU);
	}

	/** Get ZZSdf_UU.
		@return ZZSdf_UU	  */
	public String getZZSdf_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZSdf_UU);
	}

	/** Set Surname.
		@param ZZSurname Surname
	*/
	public void setZZSurname (String ZZSurname)
	{
		set_Value (COLUMNNAME_ZZSurname, ZZSurname);
	}

	/** Get Surname.
		@return Surname	  */
	public String getZZSurname()
	{
		return (String)get_Value(COLUMNNAME_ZZSurname);
	}

	/** Set Years In Occupation.
		@param ZZYearsInOccupation Years In Occupation
	*/
	public void setZZYearsInOccupation (int ZZYearsInOccupation)
	{
		set_Value (COLUMNNAME_ZZYearsInOccupation, Integer.valueOf(ZZYearsInOccupation));
	}

	/** Get Years In Occupation.
		@return Years In Occupation	  */
	public int getZZYearsInOccupation()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZYearsInOccupation);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_AlternateIDType getZZ_AlternateIDType() throws RuntimeException
	{
		return (I_ZZ_AlternateIDType)MTable.get(getCtx(), I_ZZ_AlternateIDType.Table_ID)
			.getPO(getZZ_AlternateIDType_ID(), get_TrxName());
	}

	/** Set AlternateIDType.
		@param ZZ_AlternateIDType_ID AlternateIDType
	*/
	public void setZZ_AlternateIDType_ID (int ZZ_AlternateIDType_ID)
	{
		if (ZZ_AlternateIDType_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_AlternateIDType_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_AlternateIDType_ID, Integer.valueOf(ZZ_AlternateIDType_ID));
	}

	/** Get AlternateIDType.
		@return AlternateIDType	  */
	public int getZZ_AlternateIDType_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_AlternateIDType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ID/Passport No.
		@param ZZ_ID_Passport_No ID/Passport No
	*/
	public void setZZ_ID_Passport_No (String ZZ_ID_Passport_No)
	{
		set_Value (COLUMNNAME_ZZ_ID_Passport_No, ZZ_ID_Passport_No);
	}

	/** Get ID/Passport No.
		@return ID/Passport No	  */
	public String getZZ_ID_Passport_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_ID_Passport_No);
	}

	public I_ZZ_LI_CitizenResidentialStatus getZZ_LI_CitizenResidentialStatus() throws RuntimeException
	{
		return (I_ZZ_LI_CitizenResidentialStatus)MTable.get(getCtx(), I_ZZ_LI_CitizenResidentialStatus.Table_ID)
			.getPO(getZZ_LI_CitizenResidentialStatus_ID(), get_TrxName());
	}

	/** Set CitizenResidentialStatus.
		@param ZZ_LI_CitizenResidentialStatus_ID CitizenResidentialStatus
	*/
	public void setZZ_LI_CitizenResidentialStatus_ID (int ZZ_LI_CitizenResidentialStatus_ID)
	{
		if (ZZ_LI_CitizenResidentialStatus_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_CitizenResidentialStatus_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_CitizenResidentialStatus_ID, Integer.valueOf(ZZ_LI_CitizenResidentialStatus_ID));
	}

	/** Get CitizenResidentialStatus.
		@return CitizenResidentialStatus	  */
	public int getZZ_LI_CitizenResidentialStatus_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_LI_CitizenResidentialStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_LI_Disability getZZ_LI_Disability() throws RuntimeException
	{
		return (I_ZZ_LI_Disability)MTable.get(getCtx(), I_ZZ_LI_Disability.Table_ID)
			.getPO(getZZ_LI_Disability_ID(), get_TrxName());
	}

	/** Set Disability.
		@param ZZ_LI_Disability_ID Disability
	*/
	public void setZZ_LI_Disability_ID (int ZZ_LI_Disability_ID)
	{
		if (ZZ_LI_Disability_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_Disability_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_Disability_ID, Integer.valueOf(ZZ_LI_Disability_ID));
	}

	/** Get Disability.
		@return Disability	  */
	public int getZZ_LI_Disability_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_LI_Disability_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_LI_HighestEducation getZZ_LI_HighestEducation() throws RuntimeException
	{
		return (I_ZZ_LI_HighestEducation)MTable.get(getCtx(), I_ZZ_LI_HighestEducation.Table_ID)
			.getPO(getZZ_LI_HighestEducation_ID(), get_TrxName());
	}

	/** Set Highest Education.
		@param ZZ_LI_HighestEducation_ID Highest Education
	*/
	public void setZZ_LI_HighestEducation_ID (int ZZ_LI_HighestEducation_ID)
	{
		if (ZZ_LI_HighestEducation_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_HighestEducation_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_HighestEducation_ID, Integer.valueOf(ZZ_LI_HighestEducation_ID));
	}

	/** Get Highest Education.
		@return Highest Education	  */
	public int getZZ_LI_HighestEducation_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_LI_HighestEducation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_LI_HomeLanguage getZZ_LI_HomeLanguage() throws RuntimeException
	{
		return (I_ZZ_LI_HomeLanguage)MTable.get(getCtx(), I_ZZ_LI_HomeLanguage.Table_ID)
			.getPO(getZZ_LI_HomeLanguage_ID(), get_TrxName());
	}

	/** Set HomeLanguage.
		@param ZZ_LI_HomeLanguage_ID HomeLanguage
	*/
	public void setZZ_LI_HomeLanguage_ID (int ZZ_LI_HomeLanguage_ID)
	{
		if (ZZ_LI_HomeLanguage_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_HomeLanguage_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_HomeLanguage_ID, Integer.valueOf(ZZ_LI_HomeLanguage_ID));
	}

	/** Get HomeLanguage.
		@return HomeLanguage	  */
	public int getZZ_LI_HomeLanguage_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_LI_HomeLanguage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_LI_SocioEconomicStatus getZZ_LI_SocioEconomicStatus() throws RuntimeException
	{
		return (I_ZZ_LI_SocioEconomicStatus)MTable.get(getCtx(), I_ZZ_LI_SocioEconomicStatus.Table_ID)
			.getPO(getZZ_LI_SocioEconomicStatus_ID(), get_TrxName());
	}

	/** Set SocioEconomicStatus.
		@param ZZ_LI_SocioEconomicStatus_ID SocioEconomicStatus
	*/
	public void setZZ_LI_SocioEconomicStatus_ID (int ZZ_LI_SocioEconomicStatus_ID)
	{
		if (ZZ_LI_SocioEconomicStatus_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_SocioEconomicStatus_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_LI_SocioEconomicStatus_ID, Integer.valueOf(ZZ_LI_SocioEconomicStatus_ID));
	}

	/** Get SocioEconomicStatus.
		@return SocioEconomicStatus	  */
	public int getZZ_LI_SocioEconomicStatus_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_LI_SocioEconomicStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_Nationality getZZ_Nationality() throws RuntimeException
	{
		return (I_ZZ_Nationality)MTable.get(getCtx(), I_ZZ_Nationality.Table_ID)
			.getPO(getZZ_Nationality_ID(), get_TrxName());
	}

	/** Set Nationality.
		@param ZZ_Nationality_ID Nationality
	*/
	public void setZZ_Nationality_ID (int ZZ_Nationality_ID)
	{
		if (ZZ_Nationality_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Nationality_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Nationality_ID, Integer.valueOf(ZZ_Nationality_ID));
	}

	/** Get Nationality.
		@return Nationality	  */
	public int getZZ_Nationality_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Nationality_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Passport No.
		@param ZZ_Passport_No Passport No
	*/
	public void setZZ_Passport_No (String ZZ_Passport_No)
	{
		set_Value (COLUMNNAME_ZZ_Passport_No, ZZ_Passport_No);
	}

	/** Get Passport No.
		@return Passport No	  */
	public String getZZ_Passport_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Passport_No);
	}
}