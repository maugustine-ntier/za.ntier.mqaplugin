package za.ntier.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.compiere.model.MOrder;
import org.compiere.process.SvrProcess;

import za.ntier.models.MInOut_New;
import za.ntier.models.MInvoice_New;

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
				String InvNo = resultSet.getString("Field1");
				Timestamp movementDate = resultSet.getTimestamp("DateTimeIn");
				if (InvNo != null) {
					MInvoice_New  mInvoice_New = MInvoice_New.get(getCtx(), InvNo, get_TrxName());
					MOrder mOrder = new MOrder(getCtx(), mInvoice_New.getC_Order_ID(), get_TrxName());
					MInOut_New mInOut_New = new MInOut_New (mInvoice_New, 0, movementDate, mOrder.getM_Warehouse_ID());
					
				}
			}

			selectStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection.toString();



	}
	
	
	private int getDriverID(Timestamp dateOfShipment) {
		int driverID = 0;
		
		return driverID;
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
