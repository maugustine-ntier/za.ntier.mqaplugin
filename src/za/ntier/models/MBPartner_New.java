package za.ntier.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Package_UUID_Map;
import org.compiere.model.X_I_BPartner;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util;

public class MBPartner_New extends MBPartner {

	private static final long serialVersionUID = 4154740391812230437L;

	public MBPartner_New(Properties ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, String C_BPartner_UU, String trxName) {
		super(ctx, C_BPartner_UU, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, int C_BPartner_ID, String trxName) {
		super(ctx, C_BPartner_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(X_I_BPartner impBP) {
		super(impBP);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(MBPartner copy) {
		super(copy);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, MBPartner copy) {
		super(ctx, copy);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, MBPartner copy, String trxName) {
		super(ctx, copy, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPartner_New(Properties ctx, int C_BPartner_ID, String trxName, String... virtualColumns) {
		super(ctx, C_BPartner_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}
	
	public static MBPartner getUpper (Properties ctx, String Value, String trxName)
	{
		Value = Value.toUpperCase();
		if (Value == null || Value.length() == 0) {
			return null;
		}
		final String whereClause = "Upper(Value)=? AND AD_Client_ID=?";
		MBPartner retValue = new Query(ctx, I_C_BPartner.Table_Name, whereClause, trxName)
		.setParameters(Value,Env.getAD_Client_ID(ctx))
		.firstOnly();
		return retValue;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		boolean result = super.afterSave(newRecord, success);
		if (!result) {
			return false;
		}
		if (getAD_Client_ID() != 1000018) {
			return true;
		}
		MClient[] mclients = MClient.getAll(getCtx());
		PO.setCrossTenantSafe();
		for (MClient mClient:mclients) {
			if (mClient.getAD_Client_ID() == 0 || ) {
				continue;
			}
			//MBPartner mBPartner 
		}
		PO.clearCrossTenantSafe();
		return true;
	}	
	
	/**
	 * Conduct data validations before proceeding with the actual inserts
	 */
	private void validate() {
	

		// create list of tables to ignore
		// validate tables
		// for each source table not excluded
		StringBuilder sqlTablesSB = new StringBuilder()
				.append("SELECT TableName FROM AD_Table WHERE IsActive='Y' AND IsView='N' AND ")
				.append("TableName in ('C_BPartner')")
				.append(" ORDER BY TableName");

		String sqlRemoteTables = DB.getDatabase().convertStatement(sqlTablesSB.toString());
		PreparedStatement stmtRT = null;
		ResultSet rsRT = null;
		try {
			stmtRT = DB.prepareStatement(sqlRemoteTables, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rsRT = stmtRT.executeQuery();
			while (rsRT.next()) {
				String tableName = rsRT.getString(1);
				validateExternalTable(tableName);
			}
		} catch (SQLException e) {
			throw new AdempiereException("Could not execute external query: " + sqlRemoteTables + "\nCause = " + e.getLocalizedMessage());
		} finally {
			DB.close(rsRT, stmtRT);
		}

		if (! p_IsSkipSomeValidations) {
			for (String tableName : p_tablesVerifiedList) {
				validateOrphan(tableName);
			}
		}

	}

	/**
	 * Conduct validations on a specific table
	 * @param tableName
	 */
	private void validateExternalTable(String tableName) {
		statusUpdate("Validating table " + tableName);

		// if table is not present in target
		// inform blocking as it has client data
		MTable localTable = MTable.get(getCtx(), tableName);
		if (localTable == null || localTable.getAD_Table_ID() <= 0) {
			p_errorList.add("Table " + tableName + " doesn't exist");
			return;
		}

		// if table doesn't have client data (taking into account include/exclude) in the source DB
		// add to the list of tables to ignore
		// ignore and continue with next table
		if (! "AD_Client".equalsIgnoreCase(tableName)) {
			StringBuilder sqlCountData = new StringBuilder()
					.append("SELECT COUNT(*) FROM ").append(tableName);
			if ("AD_Attribute_Value".equalsIgnoreCase(tableName)) {
				sqlCountData.append(" JOIN AD_Attribute ON (AD_Attribute_Value.AD_Attribute_ID=AD_Attribute.AD_Attribute_ID)");
				sqlCountData.append(" JOIN AD_Client ON (AD_Attribute.AD_Client_ID=AD_Client.AD_Client_ID)");
			} else if ("AD_PInstance_Log".equalsIgnoreCase(tableName)) {
				sqlCountData.append(" JOIN AD_PInstance ON (AD_PInstance_Log.AD_PInstance_ID=AD_PInstance.AD_PInstance_ID)");
				sqlCountData.append(" JOIN AD_Client ON (AD_PInstance.AD_Client_ID=AD_Client.AD_Client_ID)");
			} else {
				if (MColumn.get(getCtx(), tableName, "AD_Client_ID") == null) {
					if (log.isLoggable(Level.WARNING)) {
						log.warning("Ignoring " + tableName + ", doesn't have column AD_Client_ID");
					}
					return;
				}
				sqlCountData.append(" JOIN AD_Client ON (").append(tableName).append(".AD_Client_ID=AD_Client.AD_Client_ID)");
			}
			sqlCountData.append(" WHERE ").append(p_whereClient);
			int cntCD = countInExternal(sqlCountData.toString());
			if (cntCD == 0) {
				if (log.isLoggable(Level.INFO)) {
					log.info("Ignoring " + tableName + ", doesn't have tenant data");
				}
				return;
			}
			if (cntCD > 0 && "AD_Attribute_Value".equalsIgnoreCase(tableName)) {
				throw new AdempiereUserError("Table " + tableName + " has data, migration not supported");
			}
		}

		// for each source column
		final String sqlRemoteColumnsST = ""
				+ " SELECT AD_Column.ColumnName, AD_Column.AD_Reference_ID, AD_Column.FieldLength"
				+ " FROM AD_Column"
				+ " JOIN AD_Table ON (AD_Table.AD_Table_ID=AD_Column.AD_Table_ID)"
				+ " WHERE UPPER(AD_Table.TableName)=? AND AD_Column.IsActive='Y' AND AD_Column.ColumnSQL IS NULL"
				+ " ORDER BY AD_Column.ColumnName";
		String sqlRemoteColumns = DB.getDatabase().convertStatement(sqlRemoteColumnsST);
		PreparedStatement stmtRC = null;
		ResultSet rsRC = null;
		try {
			stmtRC = externalConn.prepareStatement(sqlRemoteColumns, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmtRC.setString(1, tableName.toUpperCase());
			rsRC = stmtRC.executeQuery();
			while (rsRC.next()) {
				String columnName = rsRC.getString(1);
				int refID = rsRC.getInt(2);
				int length = rsRC.getInt(3);
				if (columnName.equalsIgnoreCase("AD_Client_ID")) {
					p_columnsVerifiedList.add(tableName.toUpperCase() + "." + columnName.toUpperCase());
				} else {
					validateExternalColumn(tableName, columnName, refID, length);
				}
			}
		} catch (SQLException e) {
			throw new AdempiereException("Could not execute external query: " + sqlRemoteColumns + "\nCause = " + e.getLocalizedMessage());
		} finally {
			DB.close(rsRC, stmtRC);
		}
		p_tablesVerifiedList.add(tableName.toUpperCase());
	}

	/**
	 * Conduct validations for a specific column
	 * @param tableName
	 * @param columnName
	 * @param refID
	 * @param length
	 */
	private void validateExternalColumn(String tableName, String columnName, int refID, int length) {
		// inform if column is not present in target (blocking as it has client data)
		// statusUpdate("Validating column " + tableName + "." + columnName);
		MColumn localColumn = MColumn.get(getCtx(), tableName, columnName);
		if (localColumn == null || localColumn.getAD_Column_ID() <= 0) {
			p_errorList.add("Column " + tableName + "." + columnName +  " doesn't exist");
			return;
		}

		// inform if db type is different (blocking as it has client data)
		if (refID <= MTable.MAX_OFFICIAL_ID
				&& localColumn.getAD_Reference_ID() <= MTable.MAX_OFFICIAL_ID 
				&& refID != localColumn.getAD_Reference_ID()) {
			p_errorList.add("Column " + tableName + "." + columnName +  " has different type in dictionary, external: " + refID + ", local: " + localColumn.getAD_Reference_ID());
		}

		// inform blocking if external length is bigger than local
		if (length > localColumn.getFieldLength()) {
			p_errorList.add("Column " + tableName + "." + columnName +  " has different length in dictionary, external: " + length + ", local: " + localColumn.getFieldLength());
		}

		StringBuilder sqlDataNotNullInColumn = new StringBuilder()
				.append("SELECT COUNT(*) FROM ").append(tableName)
				.append(" JOIN AD_Client ON (").append(tableName).append(".AD_Client_ID=AD_Client.AD_Client_ID)")
				.append(" WHERE ").append(tableName).append(".").append(columnName).append(" IS NOT NULL")
				.append(" AND ").append(p_whereClient);
		if (!p_IsSkipSomeValidations && refID > MTable.MAX_OFFICIAL_ID) {
			int cntET = countInExternal(sqlDataNotNullInColumn.toString());
			if (cntET > 0) {
				// TODO: Implement support for non-official data types (must implement how to obtain the foreign table with MColumn.getReferenceTableName)
				throw new AdempiereUserError("There is data in unsupported non-official data type for column " + tableName + "." + columnName);
			}
		}

		// when the column is a foreign key
		String foreignTableName = localColumn.getReferenceTableName();
		if (   foreignTableName != null 
			&& (foreignTableName.equalsIgnoreCase(tableName) || "AD_PInstance_Log".equalsIgnoreCase(tableName))) {
			foreignTableName = "";
		}
		if (! Util.isEmpty(foreignTableName)) {
			// verify all foreign keys pointing to a different client
			// if pointing to a different client non-system
			//   inform cross-client data corruption error
			// if pointing to system
			//   add to list of columns with system foreign keys
			//   inform if the system record is not in target database using uuid - blocking
			String uuidCol = PO.getUUIDColumnName(foreignTableName);
			StringBuilder sqlForeignClientSB = new StringBuilder();
			if ("AD_Ref_List".equalsIgnoreCase(foreignTableName)) {
				sqlForeignClientSB
				.append("SELECT DISTINCT AD_Ref_List.AD_Client_ID, AD_Ref_List.AD_Ref_List_ID, AD_Ref_List.").append(uuidCol)
				.append(" FROM ").append(tableName);
				if (! "AD_Client".equalsIgnoreCase(tableName)) {
					sqlForeignClientSB.append(" JOIN AD_Client ON (").append(tableName).append(".AD_Client_ID=AD_Client.AD_Client_ID)");
				}
				sqlForeignClientSB.append(" JOIN AD_Ref_List ON (").append(tableName).append(".").append(columnName).append("=AD_Ref_List.");
				if ("AD_Ref_List_ID".equalsIgnoreCase(columnName)) {
					sqlForeignClientSB.append("AD_Ref_List_ID");
				} else {
					sqlForeignClientSB.append("Value");
				}
				sqlForeignClientSB.append(" AND AD_Ref_List.AD_Reference_ID=")
				.append(" (SELECT AD_Column.AD_Reference_Value_ID FROM AD_Column")
				.append(" JOIN AD_Table ON (AD_Column.AD_Table_ID=AD_Table.AD_Table_ID)")
				.append(" WHERE UPPER(AD_Table.TableName)='").append(tableName.toUpperCase())
				.append("' AND UPPER(AD_Column.ColumnName)='").append(columnName.toUpperCase()).append("'))")
				.append(" WHERE ").append(p_whereClient)
				.append(" AND ").append(foreignTableName).append(".AD_Client_ID!=").append(tableName).append(".AD_Client_ID")
				.append(" ORDER BY 2");
			} else {
				sqlForeignClientSB.append("SELECT DISTINCT ").append(foreignTableName).append(".AD_Client_ID, ");
				MTable foreignTable = MTable.get(getCtx(), foreignTableName);
				if (foreignTable.isUUIDKeyTable()) {
					sqlForeignClientSB.append(foreignTableName).append(".").append(uuidCol).append(", ");
				} else {
					sqlForeignClientSB.append(foreignTableName).append(".").append(foreignTableName).append("_ID, ");
				}
				sqlForeignClientSB.append(foreignTableName).append(".").append(uuidCol).append(" FROM ").append(tableName);
				if (! "AD_Client".equalsIgnoreCase(tableName)) {
					sqlForeignClientSB.append(" JOIN AD_Client ON (").append(tableName).append(".AD_Client_ID=AD_Client.AD_Client_ID)");
				}
				if ("AD_Client".equalsIgnoreCase(foreignTableName)) { // fix issue with foreign AD_Client_ID like AD_Replication.Remote_Client_ID
					sqlForeignClientSB.append(" JOIN ").append(foreignTableName)
					.append(" c ON (").append(tableName).append(".").append(columnName).append("=c.");
				} else {
					sqlForeignClientSB.append(" JOIN ").append(foreignTableName)
					.append(" ON (").append(tableName).append(".").append(columnName).append("=").append(foreignTableName).append(".");
				}
				if ("AD_Language".equalsIgnoreCase(foreignTableName) && !columnName.equalsIgnoreCase("AD_Language_ID")) {
					sqlForeignClientSB.append("AD_Language");
				} else if ("AD_EntityType".equalsIgnoreCase(foreignTableName) && !columnName.equalsIgnoreCase("AD_EntityType_ID")) {
					sqlForeignClientSB.append("EntityType");
				} else {
					if (foreignTable.isUUIDKeyTable()) {
						sqlForeignClientSB.append(uuidCol);
					} else {
						sqlForeignClientSB.append(foreignTableName).append("_ID");
					}
				}
				sqlForeignClientSB.append(")")
				.append(" WHERE ").append(p_whereClient)
				.append(" AND ").append(foreignTableName).append(".AD_Client_ID!=").append(tableName).append(".AD_Client_ID")
				.append(" ORDER BY 2");
			}
			String sqlForeignClient = DB.getDatabase().convertStatement(sqlForeignClientSB.toString());
			PreparedStatement stmtFC = null;
			ResultSet rsFC = null;
			try {
				stmtFC = externalConn.prepareStatement(sqlForeignClient, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				rsFC = stmtFC.executeQuery();
				while (rsFC.next()) {
					int clientID = rsFC.getInt(1);
					Object foreignID = rsFC.getObject(2);
					String foreignUU = rsFC.getString(3);
					if (clientID > 0) {
						p_errorList.add("Column " + tableName + "." + columnName +  " has invalid cross-tenant reference to tenant " + clientID + " on ID=" + foreignID);
						continue;
					}
					if (foreignID != null) {
						if (! p_idSystemConversionList.contains(foreignTableName.toUpperCase() + "." + foreignID)) {
							Object localID = getFromUUID(foreignTableName, uuidCol, tableName, columnName, foreignUU, foreignID);
							if (localID == null || (localID instanceof Number && ((Number)localID).intValue() < 0)) {
								continue;
							}
						}
					}
				}
			} catch (SQLException e) {
				throw new AdempiereException("Could not execute external query: " + sqlForeignClient + "\nCause = " + e.getLocalizedMessage());
			} finally {
				DB.close(rsFC, stmtFC);
			}
		}
		// add to the list of verified columns
		p_columnsVerifiedList.add(tableName.toUpperCase() + "." + columnName.toUpperCase());
	}

	/**
	 * Check for orphan records in a table
	 * @param tableName
	 */
	private void validateOrphan(String tableName) {
		MTable table = MTable.get(getCtx(), tableName);
		for (MColumn column : table.getColumns(false)) {
			if (!column.isActive() || column.getColumnSQL() != null) {
				continue;
			}
			String columnName = column.getColumnName();
			if ("AD_Client_ID".equalsIgnoreCase(columnName)) {
				continue;
			}
			String foreignTableName = column.getReferenceTableName();
			if (! Util.isEmpty(foreignTableName) && ! "AD_Ref_List".equalsIgnoreCase(foreignTableName)) {
				MTable tableFK = MTable.get(getCtx(), foreignTableName);
				if (tableFK == null || MTable.ACCESSLEVEL_SystemOnly.equals(tableFK.getAccessLevel())) {
					continue;
				}
				// validate if the table has columns where the constraint is not created in database, like most AD_Org_ID for example
				StringBuilder sqlVerifFKSB = new StringBuilder()
						.append("SELECT COUNT(*) ")
						.append("FROM   AD_Table t ")
						.append("       JOIN AD_Column c ")
						.append("         ON ( c.AD_Table_ID = t.AD_Table_ID ) ")
						.append("WHERE  UPPER(t.TableName)=").append(DB.TO_STRING(tableName.toUpperCase()))
						.append("       AND UPPER(c.ColumnName)=").append(DB.TO_STRING(columnName.toUpperCase()))
						.append("       AND ( c.FKConstraintType IS NULL OR c.FKConstraintType=").append(DB.TO_STRING(MColumn.FKCONSTRAINTTYPE_DoNotCreate_Ignore)).append(")");
				int cntFk = countInExternal(sqlVerifFKSB.toString());
				if (cntFk > 0) {
					statusUpdate("Validating orphans for " + table.getTableName() + "." + columnName);
					// target database has not defined a foreign key, validate orphans
					MTable foreignTable = MTable.get(getCtx(), foreignTableName);
					StringBuilder sqlExternalOrgOrphanSB = new StringBuilder("SELECT COUNT(*) FROM ").append(tableName);
					if (! "AD_Client".equalsIgnoreCase(tableName)) {
						sqlExternalOrgOrphanSB.append(" JOIN AD_Client ON (").append(tableName).append(".AD_Client_ID=AD_Client.AD_Client_ID)");
					}
					sqlExternalOrgOrphanSB.append(" WHERE ").append(tableName).append(".").append(columnName);
					if (foreignTable.isUUIDKeyTable()) {
						sqlExternalOrgOrphanSB.append(" IS NOT NULL AND ");
					} else {
						sqlExternalOrgOrphanSB.append(">0 AND ");
					}
					sqlExternalOrgOrphanSB.append(" ").append(tableName).append(".").append(columnName).append(" NOT IN (")
						.append(" SELECT ").append(foreignTableName).append(".").append(foreignTable.getKeyColumns()[0])
						.append(" FROM ").append(foreignTableName)
						.append(" WHERE ").append(foreignTableName).append(".AD_Client_ID IN (0,").append(tableName).append(".AD_Client_ID)")
						.append(")")
						.append(" AND ").append(p_whereClient);
					int cntOr = countInExternal(sqlExternalOrgOrphanSB.toString());
					if (cntOr > 0) {
						p_errorList.add("Column " + tableName + "." + columnName +  " has orphan records in table " + foreignTableName);
					}
				}
			}
		}

	}

	/**
	 * Execute a count query in the external connection and return the count
	 * @param sql
	 * @return
	 */
	private int countInExternal(String sql) {
		int cnt = 0;
		sql = DB.getDatabase().convertStatement(sql);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = externalConn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new AdempiereException("Could not execute external query: " + sql + "\nCause = " + e.getLocalizedMessage());
		} finally {
			DB.close(rs, stmt);
		}
		return cnt;
	}
	
	private void moveClient() {
		// first do the validation, process cannot be executed if there are blocking situations
		// validation construct the list of tables and columns to process
		// NOTE that the whole process will be done in a single transaction, foreign keys will be validated on commit

		List<MTable> tables = new Query(getCtx(), MTable.Table_Name,
				"IsView='N' AND TableName in ('C_BPartner')",
				get_TrxName())
				.setOnlyActiveRecords(true)
				.setOrderBy("TableName")
				.list();

		// create/verify the ID conversions
		for (MTable table : tables) {
			String tableName = table.getTableName();
			if (! p_tablesVerifiedList.contains(tableName.toUpperCase())) {
				continue;
			}
			String uuidCol = PO.getUUIDColumnName(tableName);
			String keyCol;
			if (table.isUUIDKeyTable()) {
				keyCol = uuidCol.toUpperCase();
			} else {
				keyCol = tableName.toUpperCase() + "_ID";
			}
			if (! p_columnsVerifiedList.contains(tableName.toUpperCase() + "." + keyCol)) {
				continue;
			}
			
			statusUpdate("Converting IDs for table " + tableName);
			StringBuilder selectVerifyIdSB = new StringBuilder()
					.append("SELECT ").append(keyCol).append(" FROM ").append(tableName)
					.append(" WHERE ").append(keyCol).append("=?");
			StringBuilder selectGetIdsSB = new StringBuilder()
					.append("SELECT ").append(tableName).append(".").append(keyCol).append(" FROM ").append(tableName);
			if (! "AD_Client".equalsIgnoreCase(tableName)) {
				selectGetIdsSB.append(" JOIN AD_Client ON (").append(tableName).append(".AD_Client_ID=AD_Client.AD_Client_ID)");
			}
			selectGetIdsSB.append(" WHERE ").append(p_whereClient)
			.append(" ORDER BY ").append(keyCol);
			String selectGetIds = DB.getDatabase().convertStatement(selectGetIdsSB.toString());
			PreparedStatement stmtGI = null;
			ResultSet rsGI = null;
			try {
				stmtGI = externalConn.prepareStatement(selectGetIds, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				rsGI = stmtGI.executeQuery();
				while (rsGI.next()) {
					Object source_Key = rsGI.getObject(1);
					Object target_Key = null;
					if (p_isPreserveAll || p_tablesToPreserveIDsList.contains(tableName.toUpperCase())) {
						List<Object> list = DB.getSQLValueObjectsEx(get_TrxName(), selectVerifyIdSB.toString(), source_Key);
						Object localID = null;
						if (list != null && list.size() == 1) {
							localID = list.get(0);
						}
						if (localID == null || (localID instanceof Number && ((Number)localID).intValue() < 0)) {
							target_Key = source_Key;
						} else {
							throw new AdempiereException("In " + tableName + "." + tableName + "_ID already exist the ID=" + source_Key);
						}
					} else {
						if ("AD_ChangeLog".equalsIgnoreCase(tableName)) {
							// AD_ChangeLog_ID is not really a unique key - validate if it was already converted before
							int clId = DB.getSQLValueEx(get_TrxName(),
									queryT_MoveClient,
									getAD_PInstance_ID(), "AD_CHANGELOG", String.valueOf(source_Key));
							if (clId == -1) {
								target_Key = DB.getNextID(getAD_Client_ID(), tableName, get_TrxName());
							}
						} else {
							if (table.isUUIDKeyTable()) {
								target_Key = UUID.randomUUID().toString();
							} else {
								target_Key = DB.getNextID(getAD_Client_ID(), tableName, get_TrxName());
							}
						}
					}
					if (target_Key != null || (target_Key instanceof Number && ((Number)target_Key).intValue() >= 0)) {
						DB.executeUpdateEx(insertConversionId,
								new Object[] {getAD_PInstance_ID(), tableName.toUpperCase(), source_Key, target_Key},
								get_TrxName());
					}
				}
			} catch (SQLException e) {
				throw new AdempiereException("Could not execute external query: " + selectGetIds + "\nCause = " + e.getLocalizedMessage());
			} finally {
				DB.close(rsGI, stmtGI);
			}

		}

		try {
			commitEx(); // commit the T_MoveClient table to analyze potential problems
		} catch (SQLException e1) {
			throw new AdempiereException(e1);
		} 

		int newADClientID = -1;
		String oldClientValue = null;
		if (p_IsCopyClient) {
			int clientInt;
			try {
				clientInt = Integer.parseInt(p_ClientsToInclude);
			} catch (NumberFormatException e) {
				throw new AdempiereException("Error in parameter Tenants to Include, must be just one integer");
			}
			newADClientID = DB.getSQLValueEx(get_TrxName(),
					queryT_MoveClient,
					getAD_PInstance_ID(), "AD_CLIENT", String.valueOf(clientInt));
			oldClientValue = DB.getSQLValueStringEx(get_TrxName(),
					"SELECT Value FROM AD_Client WHERE AD_Client_ID=?", clientInt);
		}

		// get the source data and insert into target converting the IDs
		for (MTable table : tables) {
			String tableName = table.getTableName();
			if (! p_tablesVerifiedList.contains(tableName.toUpperCase())) {
				continue;
			}
			statusUpdate("Inserting data for table " + tableName);
			StringBuilder valuesSB = new StringBuilder();
			StringBuilder columnsSB = new StringBuilder();
			StringBuilder qColumnsSB = new StringBuilder();
			int ncols = 0;
			List<MColumn> columns = new ArrayList<MColumn>();
			for (MColumn column : table.getColumns(false)) {
				if (!column.isActive() || column.getColumnSQL() != null) {
					continue;
				}
				String columnName = column.getColumnName();
				if (! p_columnsVerifiedList.contains(tableName.toUpperCase() + "." + columnName.toUpperCase())) {
					continue;
				}
				if (columnsSB.length() > 0) {
					qColumnsSB.append(",");
					columnsSB.append(",");
					valuesSB.append(",");
				}
				String quoteColumnName = DB.getDatabase().quoteColumnName(columnName);
				qColumnsSB.append(tableName).append(".").append(quoteColumnName);
				columnsSB.append(quoteColumnName);
				valuesSB.append("?");
				columns.add(column);
				ncols++;
			}
			StringBuilder insertSB = new StringBuilder()
					.append("INSERT INTO ").append(tableName).append("(").append(columnsSB).append(") VALUES (").append(valuesSB).append(")");
			StringBuilder selectGetDataSB = new StringBuilder()
					.append("SELECT ").append(qColumnsSB)
					.append(" FROM ").append(tableName);
			if ("AD_PInstance_Log".equalsIgnoreCase(tableName)) {
				selectGetDataSB.append(" JOIN AD_PInstance ON (AD_PInstance_Log.AD_PInstance_ID=AD_PInstance.AD_PInstance_ID)");
				selectGetDataSB.append(" JOIN AD_Client ON (AD_PInstance.AD_Client_ID=AD_Client.AD_Client_ID)");
			} else if (! "AD_Client".equalsIgnoreCase(tableName)) {
				selectGetDataSB.append(" JOIN AD_Client ON (").append(tableName).append(".AD_Client_ID=AD_Client.AD_Client_ID)");
			}
			selectGetDataSB.append(" WHERE ").append(p_whereClient);
			String selectGetData = DB.getDatabase().convertStatement(selectGetDataSB.toString());
			PreparedStatement stmtGD = null;
			ResultSet rsGD = null;
			Object[] parameters = new Object[ncols];
			try {
				stmtGD = externalConn.prepareStatement(selectGetData, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				rsGD = stmtGD.executeQuery();
				while (rsGD.next()) {
					boolean insertRecord = true;
					for (int i = 0; i < ncols; i++) {
						MColumn column = columns.get(i);
						String columnName = column.getColumnName();

						// Obtain which is the table to convert the ID (the foreign table)
						String convertTable = column.getReferenceTableName();
						if ((tableName + "_ID").equalsIgnoreCase(columnName)) {
							convertTable = tableName;
						} else if (DisplayType.isMultiID(column.getAD_Reference_ID())) {
							convertTable = column.getMultiReferenceTableName();
						} else if (convertTable != null
								&& ("AD_Ref_List".equalsIgnoreCase(convertTable)
										|| "AD_Language".equalsIgnoreCase(columnName)
										|| "EntityType".equalsIgnoreCase(columnName))) {
							convertTable = "";
						} else if (("Record_ID".equalsIgnoreCase(columnName) || "Record_UU".equalsIgnoreCase(columnName)) && table.columnExistsInDB("AD_Table_ID")) {
							// Special case for Record_ID or Record_UU
							int tableId = rsGD.getInt("AD_Table_ID");
							if (tableId > 0) {
								convertTable = getExternalTableName(tableId);
							} else {
								convertTable = "";
							}
						} else if ("Line_ID".equalsIgnoreCase(columnName) && "Fact_Acct".equalsIgnoreCase(tableName)) {
							// Special case for Fact_Acct.Line_ID
							int tableId = rsGD.getInt("AD_Table_ID");
							if (tableId > 0) {
								convertTable = getAcctDetailTableName(tableId);
							} else {
								convertTable = "";
							}
						} else if ("Parent_ID".equalsIgnoreCase(columnName) && "AD_Tree_Favorite_Node".equalsIgnoreCase(tableName)) {
							// Special case for AD_Tree_Favorite_Node.Parent_ID
							convertTable = "AD_Tree_Favorite_Node";
						} else if ("Node_ID".equalsIgnoreCase(columnName) && "AD_TreeBar".equalsIgnoreCase(tableName)) {
							// Special case for AD_TreeBar.Node_ID
							convertTable = "AD_Menu";
						} else if (("Node_ID".equalsIgnoreCase(columnName) || "Parent_ID".equalsIgnoreCase(columnName))
								&& "AD_TreeNodeMM".equalsIgnoreCase(tableName)) {
							// Special case for AD_TreeNodeMM.Node/Parent_ID
							convertTable = "AD_Menu";
						} else if (("Node_ID".equalsIgnoreCase(columnName) || "Parent_ID".equalsIgnoreCase(columnName))
								&& "AD_TreeNodeBP".equalsIgnoreCase(tableName)) {
							// Special case for AD_TreeNodeBP.Node/Parent_ID
							convertTable = "C_BPartner";
						} else if (("Node_ID".equalsIgnoreCase(columnName) || "Parent_ID".equalsIgnoreCase(columnName))
								&& "AD_TreeNodeCMC".equalsIgnoreCase(tableName)) {
							// Special case for AD_TreeNodeCMC.Node/Parent_ID
							convertTable = "CM_Container";
						} else if (("Node_ID".equalsIgnoreCase(columnName) || "Parent_ID".equalsIgnoreCase(columnName))
								&& "AD_TreeNodeCMM".equalsIgnoreCase(tableName)) {
							// Special case for AD_TreeNodeCMM.Node/Parent_ID
							convertTable = "CM_Media";
						} else if (("Node_ID".equalsIgnoreCase(columnName) || "Parent_ID".equalsIgnoreCase(columnName))
								&& "AD_TreeNodeCMS".equalsIgnoreCase(tableName)) {
							// Special case for AD_TreeNodeCMS.Node/Parent_ID
							convertTable = "CM_CStage";
						} else if (("Node_ID".equalsIgnoreCase(columnName) || "Parent_ID".equalsIgnoreCase(columnName))
								&& "AD_TreeNodeCMT".equalsIgnoreCase(tableName)) {
							// Special case for AD_TreeNodeCMT.Node/Parent_ID
							convertTable = "CM_Template";
						} else if (("Node_ID".equalsIgnoreCase(columnName) || "Parent_ID".equalsIgnoreCase(columnName))
								&& "AD_TreeNodePR".equalsIgnoreCase(tableName)) {
							// Special case for AD_TreeNodePR.Node/Parent_ID
							convertTable = "M_Product";
						} else if (("Node_ID".equalsIgnoreCase(columnName) || "Parent_ID".equalsIgnoreCase(columnName)) 
								&& (       "AD_TreeNodeU1".equalsIgnoreCase(tableName)
										|| "AD_TreeNodeU2".equalsIgnoreCase(tableName) 
										|| "AD_TreeNodeU3".equalsIgnoreCase(tableName)
										|| "AD_TreeNodeU4".equalsIgnoreCase(tableName))) {
							// Special case for AD_TreeNodeU*.Node/Parent_ID
							convertTable = "C_ElementValue";
						} else if (("Node_ID".equalsIgnoreCase(columnName) || "Parent_ID".equalsIgnoreCase(columnName))
								&& "AD_TreeNode".equalsIgnoreCase(tableName)) {
							// Special case for AD_TreeNode.Node/Parent_ID - depends on AD_Tree -> TreeType and AD_Table_ID
							int treeId = rsGD.getInt("AD_Tree_ID");
							convertTable = getExternalTableFromTree(treeId);
						} else if ("AD_Preference".equalsIgnoreCase(tableName) && "Value".equalsIgnoreCase(columnName)) {
							// Special case for AD_Preference.Value
							String att = rsGD.getString("Attribute");
							if (att.toUpperCase().endsWith("_ID")) {
								convertTable = att.substring(0, att.length()-3);
								if ("C_DocTypeTarget".equals(convertTable)) {
									convertTable = "C_DocType";
								} else {
									// validate that AD_Preference points to a valid table, ignore otherwise
									if (MTable.getTable_ID(convertTable, get_TrxName()) <= 0) {
										convertTable = "";
									}
								}
							} else {
								convertTable = "";
							}
						}

						// Fill the target value
						if (! Util.isEmpty(convertTable)) {
							// Foreign - potential ID conversion
							Object key = rsGD.getObject(i + 1);
							if (rsGD.wasNull()) {
								parameters[i] = null;
							} else {
								if (   ! (key instanceof Number && ((Number)key).intValue() == 0 && ("Parent_ID".equalsIgnoreCase(columnName) || "Node_ID".equalsIgnoreCase(columnName)))  // Parent_ID/Node_ID=0 is valid
									&& (key instanceof String || (key instanceof Number && ((Number)key).intValue() >= MTable.MAX_OFFICIAL_ID) || p_IsCopyClient)) {
									Object convertedId = null;

									if (DisplayType.isMultiID(column.getAD_Reference_ID())) {
										// multiple IDs or UUIDs separated by commas
										String[] multiKeys = ((String)key).split(",");
										for (String multiKey : multiKeys) {
											Object keyToConvert;
											if (Util.isUUID(multiKey)) {
												keyToConvert = multiKey;
											} else {
												keyToConvert = Integer.valueOf(multiKey);
											}
											Object multiConvertedId = getConvertedId(convertTable, keyToConvert, tableName, columnName);
											if (multiConvertedId == null || (multiConvertedId instanceof Number && ((Number)multiConvertedId).intValue() < 0)) {
												if (canIgnoreNullConvertedId(table, tableName, columnName, convertTable)) {
													insertRecord = false;
													break;
												} else {
													throw new AdempiereException("Found orphan record in " + tableName + "." + columnName + ": " + multiKey + " related to table " + convertTable);
												}
											}
											if (convertedId == null) {
												convertedId = "";
											} else {
												convertedId += ",";
											}
											convertedId += String.valueOf(multiConvertedId);
										}
									} else {
										// single ID or UUID to convert
										convertedId = getConvertedId(convertTable, key, tableName, columnName);
										if (convertedId == null || (convertedId instanceof Number && ((Number)convertedId).intValue() < 0)) {
											if (canIgnoreNullConvertedId(table, tableName, columnName, convertTable)) {
												insertRecord = false;
												break;
											} else {
												throw new AdempiereException("Found orphan record in " + tableName + "." + columnName + ": " + key + " related to table " + convertTable);
											}
										}
									}
									key = convertedId instanceof Number ? ((Number)convertedId).intValue() : convertedId.toString();
								}
								if ("AD_Preference".equalsIgnoreCase(tableName) && "Value".equalsIgnoreCase(columnName)) {
									parameters[i] = String.valueOf(key);
								} else {
									if (DisplayType.isText(column.getAD_Reference_ID())) {
										parameters[i] = key.toString();
									} else {
										parameters[i] = Integer.valueOf(key.toString());
									}
								}
							}
						} else {
							parameters[i] = rsGD.getObject(i + 1);
							if (rsGD.wasNull()) {
								parameters[i] = null;
							}
							if (p_IsCopyClient) {
								String uuidCol = PO.getUUIDColumnName(tableName);
								if (columnName.equals(uuidCol)) {
									String oldUUID = (String) parameters[i];
									// it is possible that the UUID has been resolved before because of a foreign key Record_UU, so search in T_MoveClient first
									String newUUID = DB.getSQLValueStringEx(get_TrxName(), queryT_MoveClient, getAD_PInstance_ID(), tableName.toUpperCase(), oldUUID);
									if (newUUID == null) {
										newUUID = UUID.randomUUID().toString();
									}
									parameters[i] = newUUID;
									if (! Util.isEmpty(oldUUID)) {
										X_AD_Package_UUID_Map map = new X_AD_Package_UUID_Map(getCtx(), 0, get_TrxName());
										map.setAD_Table_ID(table.getAD_Table_ID());
										map.set_ValueNoCheck("AD_Client_ID", newADClientID);
										map.setSource_UUID(oldUUID);
										map.setTarget_UUID(newUUID);
										map.saveEx();
									}
								} else if ("AD_Client".equalsIgnoreCase(tableName) && "Value".equalsIgnoreCase(columnName)) {
									parameters[i] = p_ClientValue;
								} else if ("AD_Client".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName)) {
									parameters[i] = p_ClientName;
								} else if (
										   ("W_Store".equalsIgnoreCase(tableName) && "WebContext".equalsIgnoreCase(columnName))
										|| ("AD_User".equalsIgnoreCase(tableName) && "Value".equalsIgnoreCase(columnName))
										) {
									parameters[i] = p_ClientValue.toLowerCase();
								} else if (
										   ("AD_User".equalsIgnoreCase(tableName) && "Password".equalsIgnoreCase(columnName))
										|| ("AD_User".equalsIgnoreCase(tableName) && "Salt".equalsIgnoreCase(columnName))
										) {
									parameters[i] = null; // do not assign passwords to new users, must be managed by SuperUser or plugin
								} else if (
										   ("AD_Org".equalsIgnoreCase(tableName) && "Value".equalsIgnoreCase(columnName))
										|| ("AD_Org".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName))
										|| ("AD_Role".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName))
										|| ("AD_Tree".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName))
										|| ("AD_User".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName))
										|| ("AD_User".equalsIgnoreCase(tableName) && "Description".equalsIgnoreCase(columnName))
										|| ("C_AcctProcessor".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName))
										|| ("C_AcctSchema".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName))
										|| ("C_BPartner".equalsIgnoreCase(tableName) && "Value".equalsIgnoreCase(columnName))
										|| ("C_BPartner".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName))
										|| ("C_Calendar".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName))
										|| ("C_Element".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName))
										|| ("M_CostType".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName))
										|| ("R_RequestProcessor".equalsIgnoreCase(tableName) && "Name".equalsIgnoreCase(columnName))
										) {
									if (parameters[i] != null) {
										String value = parameters[i].toString();
										parameters[i] = value.replaceFirst(oldClientValue, p_ClientValue);
									}
								}
							}
						}
					}
					if (insertRecord) {
						try {
							DB.executeUpdateEx(insertSB.toString(), parameters, get_TrxName());
						} catch (Exception e) {
							throw new AdempiereException("Could not execute: " + insertSB + "\nCause = " + e.getLocalizedMessage());
						}
					}
				}
			} catch (SQLException e) {
				throw new AdempiereException("Could not execute external query: " + selectGetData + "\nCause = " + e.getLocalizedMessage());
			} finally {
				DB.close(rsGD, stmtGD);
			}

		}

		// commit - here it can throw errors because of foreign keys, verify and inform
		statusUpdate("Committing.  Validating foreign keys");
		try {
			commitEx();
		} catch (SQLException e) {
			throw new AdempiereException("Could not commit,\nCause: " + e.getLocalizedMessage());
		}
	}
	

}
