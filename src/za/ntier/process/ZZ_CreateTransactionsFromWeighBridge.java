package za.ntier.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.compiere.process.SvrProcess;

import za.ntier.models.MZZWBTransaction;

@org.adempiere.base.annotation.Process
public class ZZ_CreateTransactionsFromWeighBridge extends SvrProcess {



	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}
	int cnt = 0;

	@Override
	protected String doIt() throws Exception {
		Connection connection = null;
		String dbURL = "jdbc:sqlserver://41.76.221.102:5533;encrypt=true;trustServerCertificate=true;databaseName=WeighBridgeMng";
		String user = "sa";
		String pass = "LMISupport1!";
		PreparedStatement selectStatement = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(dbURL, user, pass);



			// Displaying the outcome
			String selectQuery = "SELECT * FROM Transactions";   
			selectStatement = connection.prepareStatement(selectQuery);
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
				Timestamp movementDate = resultSet.getTimestamp("DateTimeOut");
				//	String prod = resultSet.getString("Field3").substring(0,3);
				BigDecimal netMass = resultSet.getBigDecimal("NetMass");
				String truckRegNo = resultSet.getString("TruckRegNo");
				int transactionID = resultSet.getInt("TransactionID");
				if (MZZWBTransaction.getCount(transactionID, get_TrxName()) > 0) {
					continue;
				}
				MZZWBTransaction  mZZWBTransaction = new MZZWBTransaction(getCtx(),0,get_TrxName());
				mZZWBTransaction.setWB_TransactionID(transactionID);
				mZZWBTransaction.setField1(invNo);
				mZZWBTransaction.setField2(stockPileNo);
				mZZWBTransaction.setField3(resultSet.getString("Field3"));
				mZZWBTransaction.setField4(resultSet.getString("Field4"));
				mZZWBTransaction.setField5(resultSet.getString("Field5"));
				mZZWBTransaction.setDateTimeOut(movementDate);
				mZZWBTransaction.setNetMass(netMass);
				mZZWBTransaction.setTruckRegNo(truckRegNo);
				mZZWBTransaction.saveEx();
				cnt++;
			}

			selectStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (selectStatement != null) {
				selectStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return "Number of Transactions Records Loaded : " + cnt;



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
