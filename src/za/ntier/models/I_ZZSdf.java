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

/** Generated Interface for ZZSdf
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZSdf 
{

    /** TableName=ZZSdf */
    public static final String Table_Name = "ZZSdf";

    /** AD_Table_ID=1000085 */
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

	/** Set Unit.
	  * Organizational entity within tenant
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Unit.
	  * Organizational entity within tenant
	  */
	public int getAD_Org_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

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

    /** Column name ZZAccreditedTrainingProvider */
    public static final String COLUMNNAME_ZZAccreditedTrainingProvider = "ZZAccreditedTrainingProvider";

	/** Set Accredited Training Provider	  */
	public void setZZAccreditedTrainingProvider (String ZZAccreditedTrainingProvider);

	/** Get Accredited Training Provider	  */
	public String getZZAccreditedTrainingProvider();

    /** Column name ZZCurrentOccupation */
    public static final String COLUMNNAME_ZZCurrentOccupation = "ZZCurrentOccupation";

	/** Set Current Occupation	  */
	public void setZZCurrentOccupation (String ZZCurrentOccupation);

	/** Get Current Occupation	  */
	public String getZZCurrentOccupation();

    /** Column name ZZEquity */
    public static final String COLUMNNAME_ZZEquity = "ZZEquity";

	/** Set Equity	  */
	public void setZZEquity (String ZZEquity);

	/** Get Equity	  */
	public String getZZEquity();

    /** Column name ZZExperience */
    public static final String COLUMNNAME_ZZExperience = "ZZExperience";

	/** Set Experience	  */
	public void setZZExperience (String ZZExperience);

	/** Get Experience	  */
	public String getZZExperience();

    /** Column name ZZFirstName */
    public static final String COLUMNNAME_ZZFirstName = "ZZFirstName";

	/** Set First Name	  */
	public void setZZFirstName (String ZZFirstName);

	/** Get First Name	  */
	public String getZZFirstName();

    /** Column name ZZGender */
    public static final String COLUMNNAME_ZZGender = "ZZGender";

	/** Set Gender	  */
	public void setZZGender (String ZZGender);

	/** Get Gender	  */
	public String getZZGender();

    /** Column name ZZGeneralComments */
    public static final String COLUMNNAME_ZZGeneralComments = "ZZGeneralComments";

	/** Set General Comments	  */
	public void setZZGeneralComments (String ZZGeneralComments);

	/** Get General Comments	  */
	public String getZZGeneralComments();

    /** Column name ZZHighestEducationDesc */
    public static final String COLUMNNAME_ZZHighestEducationDesc = "ZZHighestEducationDesc";

	/** Set Highest Education Description	  */
	public void setZZHighestEducationDesc (String ZZHighestEducationDesc);

	/** Get Highest Education Description	  */
	public String getZZHighestEducationDesc();

    /** Column name ZZInitials */
    public static final String COLUMNNAME_ZZInitials = "ZZInitials";

	/** Set Initials	  */
	public void setZZInitials (String ZZInitials);

	/** Get Initials	  */
	public String getZZInitials();

    /** Column name ZZLkpTitle */
    public static final String COLUMNNAME_ZZLkpTitle = "ZZLkpTitle";

	/** Set Title.
	  * Lkp Title
	  */
	public void setZZLkpTitle (String ZZLkpTitle);

	/** Get Title.
	  * Lkp Title
	  */
	public String getZZLkpTitle();

    /** Column name ZZMiddleName */
    public static final String COLUMNNAME_ZZMiddleName = "ZZMiddleName";

	/** Set Middle Name	  */
	public void setZZMiddleName (String ZZMiddleName);

	/** Get Middle Name	  */
	public String getZZMiddleName();

    /** Column name ZZSdf_ID */
    public static final String COLUMNNAME_ZZSdf_ID = "ZZSdf_ID";

	/** Set Sdf	  */
	public void setZZSdf_ID (int ZZSdf_ID);

	/** Get Sdf	  */
	public int getZZSdf_ID();

    /** Column name ZZSdf_UU */
    public static final String COLUMNNAME_ZZSdf_UU = "ZZSdf_UU";

	/** Set ZZSdf_UU	  */
	public void setZZSdf_UU (String ZZSdf_UU);

	/** Get ZZSdf_UU	  */
	public String getZZSdf_UU();

    /** Column name ZZSurname */
    public static final String COLUMNNAME_ZZSurname = "ZZSurname";

	/** Set Surname	  */
	public void setZZSurname (String ZZSurname);

	/** Get Surname	  */
	public String getZZSurname();

    /** Column name ZZYearsInOccupation */
    public static final String COLUMNNAME_ZZYearsInOccupation = "ZZYearsInOccupation";

	/** Set Years In Occupation	  */
	public void setZZYearsInOccupation (int ZZYearsInOccupation);

	/** Get Years In Occupation	  */
	public int getZZYearsInOccupation();

    /** Column name ZZ_AlternateIDType_ID */
    public static final String COLUMNNAME_ZZ_AlternateIDType_ID = "ZZ_AlternateIDType_ID";

	/** Set AlternateIDType	  */
	public void setZZ_AlternateIDType_ID (int ZZ_AlternateIDType_ID);

	/** Get AlternateIDType	  */
	public int getZZ_AlternateIDType_ID();

	public I_ZZ_AlternateIDType getZZ_AlternateIDType() throws RuntimeException;

    /** Column name ZZ_ID_Passport_No */
    public static final String COLUMNNAME_ZZ_ID_Passport_No = "ZZ_ID_Passport_No";

	/** Set ID/Passport No	  */
	public void setZZ_ID_Passport_No (String ZZ_ID_Passport_No);

	/** Get ID/Passport No	  */
	public String getZZ_ID_Passport_No();

    /** Column name ZZ_LI_CitizenResidentialStatus_ID */
    public static final String COLUMNNAME_ZZ_LI_CitizenResidentialStatus_ID = "ZZ_LI_CitizenResidentialStatus_ID";

	/** Set CitizenResidentialStatus	  */
	public void setZZ_LI_CitizenResidentialStatus_ID (int ZZ_LI_CitizenResidentialStatus_ID);

	/** Get CitizenResidentialStatus	  */
	public int getZZ_LI_CitizenResidentialStatus_ID();

	public I_ZZ_LI_CitizenResidentialStatus getZZ_LI_CitizenResidentialStatus() throws RuntimeException;

    /** Column name ZZ_LI_Disability_ID */
    public static final String COLUMNNAME_ZZ_LI_Disability_ID = "ZZ_LI_Disability_ID";

	/** Set Disability	  */
	public void setZZ_LI_Disability_ID (int ZZ_LI_Disability_ID);

	/** Get Disability	  */
	public int getZZ_LI_Disability_ID();

	public I_ZZ_LI_Disability getZZ_LI_Disability() throws RuntimeException;

    /** Column name ZZ_LI_HighestEducation_ID */
    public static final String COLUMNNAME_ZZ_LI_HighestEducation_ID = "ZZ_LI_HighestEducation_ID";

	/** Set Highest Education	  */
	public void setZZ_LI_HighestEducation_ID (int ZZ_LI_HighestEducation_ID);

	/** Get Highest Education	  */
	public int getZZ_LI_HighestEducation_ID();

	public I_ZZ_LI_HighestEducation getZZ_LI_HighestEducation() throws RuntimeException;

    /** Column name ZZ_LI_HomeLanguage_ID */
    public static final String COLUMNNAME_ZZ_LI_HomeLanguage_ID = "ZZ_LI_HomeLanguage_ID";

	/** Set HomeLanguage	  */
	public void setZZ_LI_HomeLanguage_ID (int ZZ_LI_HomeLanguage_ID);

	/** Get HomeLanguage	  */
	public int getZZ_LI_HomeLanguage_ID();

	public I_ZZ_LI_HomeLanguage getZZ_LI_HomeLanguage() throws RuntimeException;

    /** Column name ZZ_LI_SocioEconomicStatus_ID */
    public static final String COLUMNNAME_ZZ_LI_SocioEconomicStatus_ID = "ZZ_LI_SocioEconomicStatus_ID";

	/** Set SocioEconomicStatus	  */
	public void setZZ_LI_SocioEconomicStatus_ID (int ZZ_LI_SocioEconomicStatus_ID);

	/** Get SocioEconomicStatus	  */
	public int getZZ_LI_SocioEconomicStatus_ID();

	public I_ZZ_LI_SocioEconomicStatus getZZ_LI_SocioEconomicStatus() throws RuntimeException;

    /** Column name ZZ_Nationality_ID */
    public static final String COLUMNNAME_ZZ_Nationality_ID = "ZZ_Nationality_ID";

	/** Set Nationality	  */
	public void setZZ_Nationality_ID (int ZZ_Nationality_ID);

	/** Get Nationality	  */
	public int getZZ_Nationality_ID();

	public I_ZZ_Nationality getZZ_Nationality() throws RuntimeException;

    /** Column name ZZ_Passport_No */
    public static final String COLUMNNAME_ZZ_Passport_No = "ZZ_Passport_No";

	/** Set Passport No	  */
	public void setZZ_Passport_No (String ZZ_Passport_No);

	/** Get Passport No	  */
	public String getZZ_Passport_No();
}
