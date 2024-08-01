package za.ntier.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.compiere.process.SvrProcess;

@org.adempiere.base.annotation.Process
public class ZZ_CreateVehicleReg_No_On_WB extends SvrProcess {



	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}
	int cnt = 0;

	@Override
	protected String doIt() throws Exception {
		Connection connection = null;
		String dbURL = "jdbc:sqlserver://41.76.221.102:5533;encrypt=true;trustServerCertificate=true;databaseName=WMS_Stating";
		String user = "sa";
		String pass = "LMISupport1!";
		Statement sqlStatement = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(dbURL, user, pass);



			// Displaying the outcome
			String insertQuery = "Insert into SingleWeighSetup (RowGUID,TruckRegNo) Values (NEWID(),'KSN329NW')";   
			sqlStatement = connection.createStatement();
			sqlStatement.execute(insertQuery);
			cnt++;
			sqlStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (sqlStatement != null) {
				sqlStatement.close();
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
