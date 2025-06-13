package za.ntier.process;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;

import org.adempiere.base.annotation.Parameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

@org.adempiere.base.annotation.Process(name="za.ntier.process.TransactionBalanceReport")
public class TransactionBalanceReport extends SvrProcess {
	@Parameter(name="StartDate")
	protected Timestamp startDate;
	@Parameter(name="EndDate")
	protected Timestamp endDate;
	@Parameter(name="M_Product_ID")
	protected int mProductId;




	private int pInstanceId;

	@Override
	protected void prepare() {  
		pInstanceId = getAD_PInstance_ID();
	}

	@Override
	protected String doIt() throws Exception {
		int lineNo = 0;

		String sql = """
				    WITH open_balance AS (
				      SELECT 'O' AS row_type,
				             NULL::numeric AS m_transaction_id,
				             1000000::numeric AS ad_client_id,
				             0::numeric AS ad_org_id,
				             NULL::char AS isactive,
				             now()::timestamp AS created,
				             NULL::numeric AS createdby,
				             now()::timestamp AS updated,
				             NULL::numeric AS updatedby,
				             NULL::char AS movementtype,
				             NULL::numeric AS m_locator_id,
				             ?::numeric AS m_product_id,
				             NULL::timestamp AS movementdate,
				             SUM(t.movementqty) AS movementqty,
				             NULL::numeric AS m_inventoryline_id,
				             NULL::numeric AS m_movementline_id,
				             NULL::numeric AS m_inoutline_id
				      FROM adempiere.m_transaction t
				      WHERE t.movementdate < ? AND t.m_product_id = ? AND t.AD_Client_ID = ?
				    ),
				    transactions AS (
				      SELECT 'T' AS row_type,
				             t.m_transaction_id,
				             t.ad_client_id,
				             t.ad_org_id,
				             t.isactive,
				             t.created,
				             t.createdby,
				             t.updated,
				             t.updatedby,
				             t.movementtype,
				             t.m_locator_id,
				             t.m_product_id,
				             t.movementdate,
				             t.movementqty,
				             t.m_inventoryline_id,
				             t.m_movementline_id,
				             t.m_inoutline_id
				      FROM adempiere.m_transaction t
				      WHERE t.movementdate BETWEEN ? AND ? AND t.m_product_id = ? AND t.ad_client_ID = ?
				    ),
				    closing_balance AS (
				      SELECT 'C' AS row_type,
				             NULL::numeric AS m_transaction_id,
				             1000000::numeric AS ad_client_id,
				             0::numeric AS ad_org_id,
				             NULL::char AS isactive,
				             now()::timestamp AS created,
				             NULL::numeric AS createdby,
				             now()::timestamp AS updated,
				             NULL::numeric AS updatedby,
				             NULL::char AS movementtype,
				             NULL::numeric AS m_locator_id,
				             ?::numeric AS m_product_id,
				             NULL::timestamp AS movementdate,
				             SUM(t.movementqty) AS movementqty,
				             NULL::numeric AS m_inventoryline_id,
				             NULL::numeric AS m_movementline_id,
				             NULL::numeric AS m_inoutline_id
				      FROM adempiere.m_transaction t
				      WHERE t.movementdate <= ? AND t.m_product_id = ? AND t.AD_Client_ID = ?
				    )
				    SELECT * FROM open_balance
				    UNION ALL
				    SELECT * FROM transactions
				    UNION ALL
				    SELECT * FROM closing_balance
				""";

		try (PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName())) {
			pstmt.setInt(1, mProductId);   // open balance
			pstmt.setTimestamp(2, startDate);
			pstmt.setInt(3, mProductId);
			pstmt.setInt(4, getAD_Client_ID());
			pstmt.setTimestamp(5, startDate); // transactions
			pstmt.setTimestamp(6, endDate);
			pstmt.setInt(7, mProductId);
			pstmt.setInt(8, getAD_Client_ID());
			pstmt.setInt(9, mProductId);   // closing balance
			pstmt.setTimestamp(10, endDate);
			pstmt.setInt(11, mProductId);
			pstmt.setInt(12, getAD_Client_ID());

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String insertSql = """
							    INSERT INTO adempiere.t_transactions_report (
							      ad_pinstance_id, row_type, m_transaction_id, ad_client_id, ad_org_id, isactive, created,
							      createdby, updated, updatedby, movementtype, m_locator_id, m_product_id, movementdate,
							      movementqty, m_inventoryline_id, m_movementline_id, m_inoutline_id,t_transactions_report_uu,
							      LineNo
							    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)
							""";

					try (PreparedStatement insertPstmt = DB.prepareStatement(insertSql, get_TrxName())) {
						insertPstmt.setInt(1, pInstanceId);
						insertPstmt.setString(2, rs.getString("row_type"));
						insertPstmt.setObject(3, rs.getObject("m_transaction_id"));
						insertPstmt.setObject(4, rs.getObject("ad_client_id"));
						insertPstmt.setObject(5, rs.getObject("ad_org_id"));
						insertPstmt.setObject(6, "Y");  // isactive
						insertPstmt.setObject(7, rs.getObject("created"));
						insertPstmt.setObject(8, getAD_User_ID());
						insertPstmt.setObject(9, rs.getObject("updated"));
						insertPstmt.setObject(10, getAD_User_ID());
						insertPstmt.setObject(11, rs.getObject("movementtype"));
						insertPstmt.setObject(12, rs.getObject("m_locator_id"));
						insertPstmt.setObject(13, rs.getObject("m_product_id"));
						insertPstmt.setObject(14, rs.getObject("movementdate"));
						insertPstmt.setObject(15, rs.getInt("movementqty"));
						insertPstmt.setObject(16, rs.getObject("m_inventoryline_id"));
						insertPstmt.setObject(17, rs.getObject("m_movementline_id"));
						insertPstmt.setObject(18, rs.getObject("m_inoutline_id"));
						insertPstmt.setObject(19, DB.getSQLValueStringEx(null, "SELECT Generate_UUID() FROM Dual"));
						insertPstmt.setObject(20, ++lineNo);
						insertPstmt.executeUpdate();
					}
				}
			}
		} catch (SQLException e) {
			log.severe("Error: " + e.getMessage());
			throw e;
		}

		return "Transaction report generated successfully.";
	}


}



