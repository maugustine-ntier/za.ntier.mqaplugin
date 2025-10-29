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

/** Generated Model for ZZ_WF_Audit
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_WF_Audit")
public class X_ZZ_WF_Audit extends PO implements I_ZZ_WF_Audit, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251029L;

    /** Standard Constructor */
    public X_ZZ_WF_Audit (Properties ctx, int ZZ_WF_Audit_ID, String trxName)
    {
      super (ctx, ZZ_WF_Audit_ID, trxName);
      /** if (ZZ_WF_Audit_ID == 0)
        {
			setAD_Table_ID (0);
			setActionTaken (null);
			setActor_AD_User_ID (0);
			setNewStatus (null);
			setOldStatus (null);
			setRecord_ID (0);
			setZZ_WF_Audit_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Audit (Properties ctx, int ZZ_WF_Audit_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WF_Audit_ID, trxName, virtualColumns);
      /** if (ZZ_WF_Audit_ID == 0)
        {
			setAD_Table_ID (0);
			setActionTaken (null);
			setActor_AD_User_ID (0);
			setNewStatus (null);
			setOldStatus (null);
			setRecord_ID (0);
			setZZ_WF_Audit_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Audit (Properties ctx, String ZZ_WF_Audit_UU, String trxName)
    {
      super (ctx, ZZ_WF_Audit_UU, trxName);
      /** if (ZZ_WF_Audit_UU == null)
        {
			setAD_Table_ID (0);
			setActionTaken (null);
			setActor_AD_User_ID (0);
			setNewStatus (null);
			setOldStatus (null);
			setRecord_ID (0);
			setZZ_WF_Audit_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_WF_Audit (Properties ctx, String ZZ_WF_Audit_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_WF_Audit_UU, trxName, virtualColumns);
      /** if (ZZ_WF_Audit_UU == null)
        {
			setAD_Table_ID (0);
			setActionTaken (null);
			setActor_AD_User_ID (0);
			setNewStatus (null);
			setOldStatus (null);
			setRecord_ID (0);
			setZZ_WF_Audit_ID (0);
			setZZ_WF_Lines_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_WF_Audit (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System
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
      StringBuilder sb = new StringBuilder ("X_ZZ_WF_Audit[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_ID)
			.getPO(getAD_Table_ID(), get_TrxName());
	}

	/** Set Table.
		@param AD_Table_ID Database Table information
	*/
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1)
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Action Taken.
		@param ActionTaken Action Taken
	*/
	public void setActionTaken (String ActionTaken)
	{
		set_Value (COLUMNNAME_ActionTaken, ActionTaken);
	}

	/** Get Action Taken.
		@return Action Taken	  */
	public String getActionTaken()
	{
		return (String)get_Value(COLUMNNAME_ActionTaken);
	}

	public org.compiere.model.I_AD_User getActor_AD_User() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getActor_AD_User_ID(), get_TrxName());
	}

	/** Set Actor AD User ID.
		@param Actor_AD_User_ID Actor AD User ID
	*/
	public void setActor_AD_User_ID (int Actor_AD_User_ID)
	{
		if (Actor_AD_User_ID < 1)
			set_Value (COLUMNNAME_Actor_AD_User_ID, null);
		else
			set_Value (COLUMNNAME_Actor_AD_User_ID, Integer.valueOf(Actor_AD_User_ID));
	}

	/** Get Actor AD User ID.
		@return Actor AD User ID	  */
	public int getActor_AD_User_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Actor_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comment.
		@param Comment Comment
	*/
	public void setComment (String Comment)
	{
		set_Value (COLUMNNAME_Comment, Comment);
	}

	/** Get Comment.
		@return Comment	  */
	public String getComment()
	{
		return (String)get_Value(COLUMNNAME_Comment);
	}

	/** Set New Action.
		@param NewAction New Action
	*/
	public void setNewAction (String NewAction)
	{
		set_Value (COLUMNNAME_NewAction, NewAction);
	}

	/** Get New Action.
		@return New Action	  */
	public String getNewAction()
	{
		return (String)get_Value(COLUMNNAME_NewAction);
	}

	/** Set New Status.
		@param NewStatus New Status
	*/
	public void setNewStatus (String NewStatus)
	{
		set_Value (COLUMNNAME_NewStatus, NewStatus);
	}

	/** Get New Status.
		@return New Status	  */
	public String getNewStatus()
	{
		return (String)get_Value(COLUMNNAME_NewStatus);
	}

	/** Set Old Action.
		@param OldAction Old Action
	*/
	public void setOldAction (String OldAction)
	{
		set_Value (COLUMNNAME_OldAction, OldAction);
	}

	/** Get Old Action.
		@return Old Action	  */
	public String getOldAction()
	{
		return (String)get_Value(COLUMNNAME_OldAction);
	}

	/** Set Old Status.
		@param OldStatus Old Status
	*/
	public void setOldStatus (String OldStatus)
	{
		set_Value (COLUMNNAME_OldStatus, OldStatus);
	}

	/** Get Old Status.
		@return Old Status	  */
	public String getOldStatus()
	{
		return (String)get_Value(COLUMNNAME_OldStatus);
	}

	/** Set Record ID.
		@param Record_ID Direct internal record ID
	*/
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0)
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_WF_Audit.
		@param ZZ_WF_Audit_ID ZZ_WF_Audit
	*/
	public void setZZ_WF_Audit_ID (int ZZ_WF_Audit_ID)
	{
		if (ZZ_WF_Audit_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Audit_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Audit_ID, Integer.valueOf(ZZ_WF_Audit_ID));
	}

	/** Get ZZ_WF_Audit.
		@return ZZ_WF_Audit	  */
	public int getZZ_WF_Audit_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WF_Audit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ZZ_WF_Lines getZZ_WF_Lines() throws RuntimeException
	{
		return (I_ZZ_WF_Lines)MTable.get(getCtx(), I_ZZ_WF_Lines.Table_ID)
			.getPO(getZZ_WF_Lines_ID(), get_TrxName());
	}

	/** Set ZZ_WF_Lines.
		@param ZZ_WF_Lines_ID ZZ_WF_Lines
	*/
	public void setZZ_WF_Lines_ID (int ZZ_WF_Lines_ID)
	{
		if (ZZ_WF_Lines_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Lines_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_WF_Lines_ID, Integer.valueOf(ZZ_WF_Lines_ID));
	}

	/** Get ZZ_WF_Lines.
		@return ZZ_WF_Lines	  */
	public int getZZ_WF_Lines_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_WF_Lines_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}