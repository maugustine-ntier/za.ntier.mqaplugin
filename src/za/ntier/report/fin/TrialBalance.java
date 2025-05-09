/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package za.ntier.report.fin;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import org.compiere.model.MAcctSchemaElement;
import org.compiere.model.MElementValue;
import org.compiere.model.MPeriod;
import org.compiere.model.MProcessPara;
import org.compiere.model.MYear;
import org.compiere.model.X_Fact_Acct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.MReportTree;
import org.compiere.util.DB;
import org.compiere.util.Language;
import org.compiere.util.Msg;

/**
 *	Trial Balance Report
 *	
 *  @author Jorg Janke
 *
 *  @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org 
 *			@see https://sourceforge.net/p/adempiere/feature-requests/631/ 
 *  @version $Id: TrialBalance.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
@org.adempiere.base.annotation.Process
public class TrialBalance extends SvrProcess
{
	/** AcctSchame Parameter			*/
	private int					p_C_AcctSchema_ID = 0;
	/**	Period Parameter				*/
	private int					p_C_Period_ID = 0;
	private Timestamp			p_DateAcct_From = null;
	private Timestamp			p_DateAcct_To = null;
	/**	Org Parameter					*/
	private int					p_AD_Org_ID = 0;
	/**	Account Parameter				*/
	private int					p_Account_ID = 0;
	private String				p_AccountValue_From = null;
	private String				p_AccountValue_To = null;
	/**	BPartner Parameter				*/
	private int					p_C_BPartner_ID = 0;
	/**	Product Parameter				*/
	private int					p_M_Product_ID = 0;
	/**	Project Parameter				*/
	private int					p_C_Project_ID = 0;
	/**	Activity Parameter				*/
	private int					p_C_Activity_ID = 0;
	/**	SalesRegion Parameter			*/
	private int					p_C_SalesRegion_ID = 0;
	/**	Campaign Parameter				*/
	private int					p_C_Campaign_ID = 0;
	/** Posting Type					*/
	private String				p_PostingType = "A";
	/** Hierarchy						*/
	private int					p_PA_Hierarchy_ID = 0;
	
	private int					p_AD_OrgTrx_ID = 0;
	private int					p_C_LocFrom_ID = 0;
	private int					p_C_LocTo_ID = 0;
	private int					p_User1_ID = 0;
	private int					p_User2_ID = 0;
	private boolean				p_IsGroupByOrg = false;
	private StringBuffer		m_parameterWhere = new StringBuffer();
	private StringBuffer		m_parameterWhereBudget = new StringBuffer();
	private StringBuffer		m_parameterWhereActuals = new StringBuffer();
	
	private static String		s_insert = "INSERT INTO T_TrialBalance_Ntier "
			+ "(AD_PInstance_ID, Fact_Acct_ID,"
			+ " AD_Client_ID, AD_Org_ID, Created,CreatedBy, Updated,UpdatedBy,"
			+ " C_AcctSchema_ID, Account_ID, AccountValue, DateTrx, DateAcct, C_Period_ID,"
			+ " AD_Table_ID, Record_ID, Line_ID,"
			+ " GL_Category_ID, GL_Budget_ID, C_Tax_ID, M_Locator_ID, PostingType,"
			+ " C_Currency_ID, AmtSourceDr, AmtSourceCr, AmtSourceBalance,"
			+ " AmtAcctDr, AmtAcctCr, AmtAcctBalance, C_UOM_ID, Qty,"
			+ " M_Product_ID, C_BPartner_ID, AD_OrgTrx_ID, C_LocFrom_ID,C_LocTo_ID,"
			+ " C_SalesRegion_ID, C_Project_ID, C_Campaign_ID, C_Activity_ID,"
			+ " User1_ID, User2_ID, A_Asset_ID, Description, LevelNo, T_TrialBalance_Ntier_UU,"
			+ " ZZ_Account_Description,ZZ_YTD_Current,ZZ_YTD_Prior,ZZ_Prior_Year_Full,ZZ_Budget_YTD,ZZ_Total_Budget)";
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		StringBuilder sb = new StringBuilder ("AD_PInstance_ID=")
				.append(getAD_PInstance_ID());
			//	Parameter
			ProcessInfoParameter[] para = getParameter();
			for (int i = 0; i < para.length; i++)
			{
				String name = para[i].getParameterName();
				if (para[i].getParameter() == null && para[i].getParameter_To() == null)
					;
				else if (name.equals("C_AcctSchema_ID"))
					p_C_AcctSchema_ID = ((BigDecimal)para[i].getParameter()).intValue();
				else if (name.equals("C_Period_ID"))
					p_C_Period_ID = ((BigDecimal)para[i].getParameter()).intValue();
				else if (name.equals("DateAcct"))
				{
					p_DateAcct_From = (Timestamp)para[i].getParameter();
					p_DateAcct_To = (Timestamp)para[i].getParameter_To();
				}
				else if (name.equals("PA_Hierarchy_ID"))
					p_PA_Hierarchy_ID = para[i].getParameterAsInt();
				else if (name.equals("AD_Org_ID"))
					p_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
				else if (name.equals("Account_ID"))
					p_Account_ID = ((BigDecimal)para[i].getParameter()).intValue();
				else if (name.equals("AccountValue"))
				{
					p_AccountValue_From = (String)para[i].getParameter();
					p_AccountValue_To = (String)para[i].getParameter_To();
				}
				else if (name.equals("C_BPartner_ID"))
					p_C_BPartner_ID = ((BigDecimal)para[i].getParameter()).intValue();
				else if (name.equals("M_Product_ID"))
					p_M_Product_ID = ((BigDecimal)para[i].getParameter()).intValue();
				else if (name.equals("C_Project_ID"))
					p_C_Project_ID = ((BigDecimal)para[i].getParameter()).intValue();
				else if (name.equals("C_Activity_ID"))
					p_C_Activity_ID = ((BigDecimal)para[i].getParameter()).intValue();
				else if (name.equals("C_SalesRegion_ID"))
					p_C_SalesRegion_ID = ((BigDecimal)para[i].getParameter()).intValue();
				else if (name.equals("C_Campaign_ID"))
					p_C_Campaign_ID = ((BigDecimal)para[i].getParameter()).intValue();
				else if (name.equals("PostingType"))
					p_PostingType = (String)para[i].getParameter();
				else if (name.equals("IsGroupByOrg"))
					p_IsGroupByOrg = para[i].getParameterAsBoolean();
				else
					MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para[i]);
			}
			//	Mandatory C_AcctSchema_ID
			m_parameterWhere.append("f.C_AcctSchema_ID=").append(p_C_AcctSchema_ID);
			//	Optional Account_ID
			if (p_Account_ID != 0)
				m_parameterWhere.append(" AND f.").append(MReportTree.getWhereClause(getCtx(), 
					p_PA_Hierarchy_ID,MAcctSchemaElement.ELEMENTTYPE_Account, p_Account_ID));
			if (p_AccountValue_From != null && p_AccountValue_From.length() == 0)
				p_AccountValue_From = null;
			if (p_AccountValue_To != null && p_AccountValue_To.length() == 0)
				p_AccountValue_To = null;
			if (p_AccountValue_From != null && p_AccountValue_To != null)
				m_parameterWhere.append(" AND (f.Account_ID IS NULL OR EXISTS (SELECT * FROM C_ElementValue ev ")
					.append("WHERE f.Account_ID=ev.C_ElementValue_ID AND ev.Value >= ")
					.append(DB.TO_STRING(p_AccountValue_From)).append(" AND ev.Value <= ")
					.append(DB.TO_STRING(p_AccountValue_To)).append("))");
			else if (p_AccountValue_From != null && p_AccountValue_To == null)
				m_parameterWhere.append(" AND (f.Account_ID IS NULL OR EXISTS (SELECT * FROM C_ElementValue ev ")
				.append("WHERE f.Account_ID=ev.C_ElementValue_ID AND ev.Value >= ")
				.append(DB.TO_STRING(p_AccountValue_From)).append("))");
			else if (p_AccountValue_From == null && p_AccountValue_To != null)
				m_parameterWhere.append(" AND (f.Account_ID IS NULL OR EXISTS (SELECT * FROM C_ElementValue ev ")
				.append("WHERE f.Account_ID=ev.C_ElementValue_ID AND ev.Value <= ")
				.append(DB.TO_STRING(p_AccountValue_To)).append("))");
			//	Optional Org
			if (p_AD_Org_ID != 0)
				m_parameterWhere.append(" AND f.").append(MReportTree.getWhereClause(getCtx(), 
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Organization, p_AD_Org_ID));
			//	Optional BPartner
			if (p_C_BPartner_ID != 0)
				m_parameterWhere.append(" AND f.").append(MReportTree.getWhereClause(getCtx(), 
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_BPartner, p_C_BPartner_ID));
			//	Optional Product
			if (p_M_Product_ID != 0)
				m_parameterWhere.append(" AND f.").append(MReportTree.getWhereClause(getCtx(), 
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Product, p_M_Product_ID));
			//	Optional Project
			if (p_C_Project_ID != 0)
				m_parameterWhere.append(" AND f.").append(MReportTree.getWhereClause(getCtx(), 
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Project, p_C_Project_ID));
			//	Optional Activity
			if (p_C_Activity_ID != 0)
				m_parameterWhere.append(" AND f.").append(MReportTree.getWhereClause(getCtx(), 
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Activity, p_C_Activity_ID));
			//	Optional Campaign
			if (p_C_Campaign_ID != 0)
				m_parameterWhere.append(" AND f.C_Campaign_ID=").append(p_C_Campaign_ID);
			//	m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(), 
			//		MAcctSchemaElement.ELEMENTTYPE_Campaign, p_C_Campaign_ID));
			//	Optional Sales Region
			if (p_C_SalesRegion_ID != 0)
				m_parameterWhere.append(" AND f.").append(MReportTree.getWhereClause(getCtx(), 
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_SalesRegion, p_C_SalesRegion_ID));
			m_parameterWhereBudget.append(m_parameterWhere.toString().replaceAll("f.", "fp."));
			m_parameterWhereActuals.append(m_parameterWhere.toString().replaceAll("f.", "fp."));
			//	Mandatory Posting Type
		//	m_parameterWhere.append(" AND PostingType='").append(p_PostingType).append("'");
			m_parameterWhereBudget.append(" AND fp.PostingType='").append(X_Fact_Acct.POSTINGTYPE_Budget).append("'");
			//
		//	setDateAcct();
		//	sb.append(" - DateAcct ").append(p_DateAcct_From).append("-").append(p_DateAcct_To);
			sb.append(" - Where=").append(m_parameterWhere);
			if (log.isLoggable(Level.FINE)) log.fine(sb.toString());
	}	//	prepare

	
	
	/**
	 *  Insert reporting data to T_TrialBalance
	 *  @return empty string
	 */
	protected String doIt()
	{
		MPeriod mPeriodSelected = new MPeriod(getCtx(), p_C_Period_ID, get_TrxName());
		String SQL = "Select C_Period_ID from C_Period p where p.C_Year_ID = ? and p.periodNo = ?";
		int startID = DB.getSQLValue(get_TrxName(), SQL, mPeriodSelected.getC_Year_ID(),1);
		MPeriod startPeriod = new MPeriod(getCtx(), startID, get_TrxName());
		int lastID = DB.getSQLValue(get_TrxName(), SQL, mPeriodSelected.getC_Year_ID(),12);
		MPeriod lastPeriod = new MPeriod(getCtx(), lastID, get_TrxName());
		MYear currYear = new MYear(getCtx(), startPeriod.getC_Year_ID(), get_TrxName());
		int prevYear = Integer.parseInt(currYear.getFiscalYear());
		prevYear--;
		String SQL2 = "Select y.C_Year_ID from C_Year y where y.ad_client_id = ? and y.fiscalYear = ?";
		int prev_C_Year_ID = DB.getSQLValue(get_TrxName(), SQL2, getAD_Client_ID(),prevYear + "");
		int priorStartID = DB.getSQLValue(get_TrxName(), SQL, prev_C_Year_ID,1);
		MPeriod priorStartPeriod = new MPeriod(getCtx(), priorStartID, get_TrxName());
		int priorEndID = DB.getSQLValue(get_TrxName(), SQL, prev_C_Year_ID,mPeriodSelected.getPeriodNo());
		MPeriod priorEndPeriod = new MPeriod(getCtx(), priorEndID, get_TrxName());
		int priorLastID = DB.getSQLValue(get_TrxName(), SQL, prev_C_Year_ID,12);
		MPeriod priorLastPeriod = new MPeriod(getCtx(), priorLastID, get_TrxName());
		createBalanceLine(startPeriod.getStartDate(), mPeriodSelected.getEndDate(), lastPeriod.getEndDate(),priorStartPeriod.getStartDate(),priorEndPeriod.getEndDate(),priorLastPeriod.getEndDate());
		
		return "";
	}	//	doIt
	
	
	private void createBalanceLine(Timestamp fromDate, Timestamp toDate, Timestamp lastDate,Timestamp priorStartDate,Timestamp priorEndDate, Timestamp priorLastDate)
	{
		StringBuilder sql = new StringBuilder (s_insert);
		//	(AD_PInstance_ID, Fact_Acct_ID,
		sql.append("SELECT ").append(getAD_PInstance_ID()).append(",0,");
		//	AD_Client_ID, AD_Org_ID, Created,CreatedBy, Updated,UpdatedBy,
		sql.append(getAD_Client_ID()).append(",");
		if (p_IsGroupByOrg)
			sql.append("AD_Org_ID");
		else if (p_AD_Org_ID == 0)
			sql.append("0");
		else
			sql.append(p_AD_Org_ID);
		sql.append(", getDate(),").append(getAD_User_ID())
			.append(",getDate(),").append(getAD_User_ID()).append(",");
		//	C_AcctSchema_ID, Account_ID, AccountValue, DateTrx, DateAcct, C_Period_ID,
		sql.append(p_C_AcctSchema_ID).append(",");
		sql.append("Account_ID");
		if (p_AccountValue_From != null)
			sql.append(",").append(DB.TO_STRING(p_AccountValue_From));
		else if (p_AccountValue_To != null)
			sql.append(",' '");
		else
			sql.append(",null");
		Timestamp balanceDay = p_DateAcct_From;
		sql.append(",null,").append(DB.TO_DATE(balanceDay, true)).append(",");
		if (p_C_Period_ID == 0)
			sql.append("null");
		else
			sql.append(p_C_Period_ID);
		sql.append(",");
		//	AD_Table_ID, Record_ID, Line_ID,
		sql.append("null,null,null,");
		//	GL_Category_ID, GL_Budget_ID, C_Tax_ID, M_Locator_ID, PostingType,
		sql.append("null,null,null,null,'").append(p_PostingType).append("',");
		//	C_Currency_ID, AmtSourceDr, AmtSourceCr, AmtSourceBalance,
		sql.append("null,null,null,null,");
		//	AmtAcctDr, AmtAcctCr, AmtAcctBalance, C_UOM_ID, Qty,
		sql.append(" COALESCE(SUM(AmtAcctDr),0),COALESCE(SUM(AmtAcctCr),0),"
				  + "COALESCE(SUM(AmtAcctDr),0)-COALESCE(SUM(AmtAcctCr),0),"
			+ " null,COALESCE(SUM(Qty),0),");
		//	M_Product_ID, C_BPartner_ID, AD_OrgTrx_ID, C_LocFrom_ID,C_LocTo_ID,
		if (p_M_Product_ID == 0)
			sql.append ("null");
		else
			sql.append (p_M_Product_ID);
		sql.append(",");
		if (p_C_BPartner_ID == 0)
			sql.append ("null");
		else
			sql.append (p_C_BPartner_ID);
		sql.append(",");
		if (p_AD_OrgTrx_ID == 0)
			sql.append ("null");
		else
			sql.append (p_AD_OrgTrx_ID);
		sql.append(",");
		if (p_C_LocFrom_ID == 0)
			sql.append ("null");
		else
			sql.append (p_C_LocFrom_ID);
		sql.append(",");
		if (p_C_LocTo_ID == 0)
			sql.append ("null");
		else
			sql.append (p_C_LocTo_ID);
		sql.append(",");
		//	C_SalesRegion_ID, C_Project_ID, C_Campaign_ID, C_Activity_ID,
		if (p_C_SalesRegion_ID == 0)
			sql.append ("null");
		else
			sql.append (p_C_SalesRegion_ID);
		sql.append(",");
		if (p_C_Project_ID == 0)
			sql.append ("null");
		else
			sql.append (p_C_Project_ID);
		sql.append(",");
		if (p_C_Campaign_ID == 0)
			sql.append ("null");
		else
			sql.append (p_C_Campaign_ID);
		sql.append(",");
		if (p_C_Activity_ID == 0)
			sql.append ("null");
		else
			sql.append (p_C_Activity_ID);
		sql.append(",");
		//	User1_ID, User2_ID, A_Asset_ID, Description)
		if (p_User1_ID == 0)
			sql.append ("null");
		else
			sql.append (p_User1_ID);
		sql.append(",");
		if (p_User2_ID == 0)
			sql.append ("null");
		else
			sql.append (p_User2_ID);
		sql.append(", null, '");
		sql.append(Msg.getMsg(getCtx(), "opening.balance") + "', 0, generate_uuid() ");
		
		sql.append(",");
		sql.append("(Select e.description from C_ElementValue e where e.C_ElementValue_ID = Account_ID)");
		
		sql.append(",");
		sql.append("(Select COALESCE(SUM(AmtAcctDr),0)-COALESCE(SUM(AmtAcctCr),0) from Fact_Acct fp where ")
		   .append(" fp.ad_client_id = f.ad_client_id")
		   .append(" AND fp.Account_ID = f.Account_ID" )
		   .append(" AND fp.DateAcct >= ").append(DB.TO_DATE(fromDate, true))
	       .append(" AND fp.DateAcct < (").append(DB.TO_DATE(toDate, true))
	       .append(" + 1)")
	       .append(" AND ").append(m_parameterWhereActuals)
		   .append(" AND fp.PostingType='").append(p_PostingType).append("'");
		sql.append(")");
		
		sql.append(",");
		sql.append("(Select COALESCE(SUM(AmtAcctDr),0)-COALESCE(SUM(AmtAcctCr),0) from Fact_Acct fp where ")

		   .append(" fp.ad_client_id = f.ad_client_id")
		   .append(" AND fp.Account_ID = f.Account_ID" )
		   .append(" AND fp.DateAcct >= ").append(DB.TO_DATE(priorStartDate, true))
	       .append(" AND fp.DateAcct < (").append(DB.TO_DATE(priorEndDate, true))
	       .append(" + 1)")
	       .append(" AND ").append(m_parameterWhereActuals)
		   .append(" AND fp.PostingType='").append(p_PostingType).append("'");
		sql.append(")");
		
		sql.append(",");
		sql.append("(Select COALESCE(SUM(AmtAcctDr),0)-COALESCE(SUM(AmtAcctCr),0) from Fact_Acct fp where ")
		   .append(" fp.ad_client_id = f.ad_client_id")
		   .append(" AND fp.Account_ID = f.Account_ID" )
		   .append(" AND fp.DateAcct >= ").append(DB.TO_DATE(priorStartDate, true))
	       .append(" AND fp.DateAcct < (").append(DB.TO_DATE(priorLastDate, true))
	       .append(" + 1)")
	       .append(" AND ").append(m_parameterWhereActuals)
	       .append(" AND fp.PostingType='").append(p_PostingType).append("'");
		sql.append(")");
		
		sql.append(",");
		sql.append("(Select COALESCE(SUM(AmtAcctDr),0)-COALESCE(SUM(AmtAcctCr),0) from Fact_Acct fp where ")
		   .append(" fp.ad_client_id = f.ad_client_id")
		   .append(" AND fp.Account_ID = f.Account_ID" )
		   .append(" AND fp.DateAcct >= ").append(DB.TO_DATE(fromDate, true))
	       .append(" AND fp.DateAcct < (").append(DB.TO_DATE(toDate, true))
	       .append(" + 1)")
	       .append (" AND ").append(m_parameterWhereBudget);
		sql.append(")");
		
		sql.append(",");
		sql.append("(Select COALESCE(SUM(AmtAcctDr),0)-COALESCE(SUM(AmtAcctCr),0) from Fact_Acct fp where ")
		   .append(" fp.ad_client_id = f.ad_client_id")
		   .append(" AND fp.Account_ID = f.Account_ID" )
		   .append(" AND fp.DateAcct >= ").append(DB.TO_DATE(fromDate, true))
	       .append(" AND fp.DateAcct < (").append(DB.TO_DATE(lastDate, true))
	       .append(" + 1)")
	       .append (" AND ").append(m_parameterWhereBudget);
		sql.append(")");
		
		;
		
		//
		sql.append(" FROM Fact_Acct f WHERE AD_Client_ID=").append(getAD_Client_ID())
			.append (" AND ").append(m_parameterWhere)
			.append(" AND DateAcct >= ").append(DB.TO_DATE(priorStartDate, true))
		    .append(" AND DateAcct < (").append(DB.TO_DATE(toDate, true))
		    .append(" + 1)");;
		//	Start Beginning of Year
		
		sql.append(" GROUP BY Ad_Client_ID,Account_ID ");
		if (p_IsGroupByOrg)
			sql.append(", AD_Org_ID ");

		//
		int no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no == 0)
			if (log.isLoggable(Level.FINE)) log.fine(sql.toString());
		if (log.isLoggable(Level.FINE)) log.fine("#" + no + " (Account_ID=" + p_Account_ID + ")");
	}	//	createBalanceLine


	

}	//	TrialBalance
