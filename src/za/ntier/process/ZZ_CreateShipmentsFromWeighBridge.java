package za.ntier.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.X_M_InOut;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import za.ntier.models.MDriver;
import za.ntier.models.MInOut_New;
import za.ntier.models.MInvoice_New;
import za.ntier.models.MTransporters;

@org.adempiere.base.annotation.Process
public class ZZ_CreateShipmentsFromWeighBridge extends SvrProcess {



	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		Connection connection = null;
		String dbURL = "jdbc:sqlserver://41.76.221.102:5533;encrypt=true;trustServerCertificate=true;databaseName=WeighBridgeMng";
		String user = "sa";
		String pass = "LMISupport1!";

		try {
			connection = DriverManager.getConnection(dbURL, user, pass);



			// Displaying the outcome
			String selectQuery = "SELECT * FROM Transactions";   
			PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
			ResultSet resultSet = selectStatement.executeQuery();

			System.out.println("Outcome:");
			System.out.printf("%-10s %-20s %-20s %-30s %-20s%n", "Field1", "Field2", "Field3", "Field4", "Field5");

			while (resultSet.next()) {
				System.out.printf("%-10s %-20s %-20s %-30s %-20s%n",
						resultSet.getString("Field1"),
						resultSet.getString("Field2"),
						resultSet.getString("Field3"),
						resultSet.getString("Field4"),
						resultSet.getString("Field5"));				
				String invNo = resultSet.getString("Field1");
				String stockPileNo = resultSet.getString("Field2");
				Timestamp movementDate = resultSet.getTimestamp("DateTimeIn");
				String prod = resultSet.getString("Field3").substring(0,3);
				BigDecimal netMass = resultSet.getBigDecimal("NetMass");
				String truckRegNo = resultSet.getString("TruckRegNo");
				int transactionID = resultSet.getInt("TransactionID");
				if (MInOut_New.getCount(transactionID, get_TrxName()) > 0) {
					continue;
				}
				if (invNo != null) {
					MInvoice_New  mInvoice_New = MInvoice_New.get(getCtx(), invNo, get_TrxName());
					MInvoiceLine mInvoiceLine[] = mInvoice_New.getLines();
					int m_Product_ID = 0;
					if (mInvoiceLine != null && mInvoiceLine.length > 0) {
						m_Product_ID = mInvoiceLine[0].getM_Product_ID();
					}
					MOrder mOrder = new MOrder(getCtx(), mInvoice_New.getC_Order_ID(), get_TrxName());
					MInOut_New mInOut_New = new MInOut_New (mInvoice_New, 0, movementDate, mOrder.getM_Warehouse_ID());
					if (mInOut_New != null) {
						mInOut_New.setDeliveryViaRule(X_M_InOut.DELIVERYVIARULE_Shipper);
						Object objs [] = MDriver.getDriver(getCtx(),mInvoice_New.getC_BPartner_ID(),m_Product_ID,movementDate,truckRegNo,get_TrxName());
						MDriver mDriver = (MDriver) objs[0];
						MTransporters mTransporters = (MTransporters) objs[1];
						mInOut_New.setM_Warehouse_ID(mTransporters.getM_Warehouse_ID());
						mInOut_New.setM_Shipper_ID(mTransporters.getM_Shipper_ID());
						mInOut_New.setZZ_Driver_ID(mDriver.getZZ_Driver_ID());
						mInOut_New.setShipDate(movementDate);
						mInOut_New.setWB_TransactionID(transactionID);
						mInOut_New.setZZ_StockPile_ID(getStockPile_ID(stockPileNo));
						mInOut_New.saveEx();
						MInOutLine mInOutLine = new MInOutLine(mInOut_New);
						mInOutLine.setM_Product_ID(m_Product_ID);
						mInOutLine.setQty(netMass);
						mInOutLine.setC_UOM_ID(1000000);
					} 

				}
			}

			selectStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection.toString();



	}


	private int getStockPile_ID(String stockPileNo) {
		int zz_StockPile_ID = 0;
		if (stockPileNo != null && !stockPileNo.trim().equals("")) {
			String SQL = "select ZZ_StockPile_ID from ZZ_StockPile sp where sp.documentno = ?";
			zz_StockPile_ID = DB.getSQLValue(get_TrxName(), SQL, stockPileNo);
		}
		return zz_StockPile_ID;
	}

	public static void main(String[] args) {
		String dbURL = "jdbc:sqlserver://41.76.221.102:5533;encrypt=true;trustServerCertificate=true;databaseName=WeighBridgeMng";
		String user = "sa";
		String pass = "LMISupport1!";

		try {
			Connection connection = DriverManager.getConnection(dbURL, user, pass);



			// Displaying the outcome
			String selectQuery = "SELECT * FROM Transactions";
			PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
			ResultSet resultSet = selectStatement.executeQuery();

			System.out.println("Outcome:");
			System.out.printf("%-10s %-20s %-20s %-30s %-20s%n", "Field1", "Field2", "Field3", "Field4", "Field5");

			while (resultSet.next()) {
				System.out.printf("%-10s %-20s %-20s %-30s %-20s%n",
						resultSet.getString("Field1"),
						resultSet.getString("Field2"),
						resultSet.getString("Field3"),
						resultSet.getString("Field4"),
						resultSet.getString("Field5"));
			}

			selectStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
