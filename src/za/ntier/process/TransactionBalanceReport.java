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

		String sql = """
				    WITH open_balance AS (
				      SELECT 'O' AS row_type,
				             NULL::numeric AS m_transaction_id,
				             max(t.ad_client_id)::numeric AS ad_client_id,
				             max(t.ad_org_id)::numeric AS ad_org_id,
				             NULL::char AS isactive,
				             NULL::timestamp AS created,
				             NULL::numeric AS createdby,
				             NULL::timestamp AS updated,
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
				      WHERE t.movementdate < ? AND t.m_product_id = ?
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
				      WHERE t.movementdate BETWEEN ? AND ? AND t.m_product_id = ?
				    ),
				    closing_balance AS (
				      SELECT 'C' AS row_type,
				             NULL::numeric AS m_transaction_id,
				             max(t.ad_client_id)::numeric AS ad_client_id,
				             max(t.ad_org_id)::numeric AS ad_org_id,
				             NULL::char AS isactive,
				             NULL::timestamp AS created,
				             NULL::numeric AS createdby,
				             NULL::timestamp AS updated,
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
				      WHERE t.movementdate > ? AND t.m_product_id = ?
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
			pstmt.setTimestamp(4, startDate); // transactions
			pstmt.setTimestamp(5, endDate);
			pstmt.setInt(6, mProductId);
			pstmt.setInt(7, mProductId);   // closing balance
			pstmt.setTimestamp(8, endDate);
			pstmt.setInt(9, mProductId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String insertSql = """
							    INSERT INTO adempiere.t_transactions_report (
							      ad_pinstance_id, row_type, m_transaction_id, ad_client_id, ad_org_id, isactive, created,
							      createdby, updated, updatedby, movementtype, m_locator_id, m_product_id, movementdate,
							      movementqty, m_inventoryline_id, m_movementline_id, m_inoutline_id
							    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
							""";

					try (PreparedStatement insertPstmt = DB.prepareStatement(insertSql, get_TrxName())) {
						insertPstmt.setInt(1, pInstanceId);
						insertPstmt.setString(2, rs.getString("row_type"));
						insertPstmt.setObject(3, rs.getObject("m_transaction_id"));
						insertPstmt.setObject(4, rs.getObject("ad_client_id"));
						insertPstmt.setObject(5, rs.getObject("ad_org_id"));
						insertPstmt.setObject(6, rs.getObject("isactive"));
						insertPstmt.setObject(7, rs.getObject("created"));
						insertPstmt.setObject(8, rs.getObject("createdby"));
						insertPstmt.setObject(9, rs.getObject("updated"));
						insertPstmt.setObject(10, rs.getObject("updatedby"));
						insertPstmt.setObject(11, rs.getObject("movementtype"));
						insertPstmt.setObject(12, rs.getObject("m_locator_id"));
						insertPstmt.setObject(13, rs.getObject("m_product_id"));
						insertPstmt.setObject(14, rs.getObject("movementdate"));
						insertPstmt.setObject(15, rs.getObject("movementqty"));
						insertPstmt.setObject(16, rs.getObject("m_inventoryline_id"));
						insertPstmt.setObject(17, rs.getObject("m_movementline_id"));
						insertPstmt.setObject(18, rs.getObject("m_inoutline_id"));
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



