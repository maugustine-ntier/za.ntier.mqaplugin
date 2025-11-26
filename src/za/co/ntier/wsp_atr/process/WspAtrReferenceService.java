package za.co.ntier.wsp_atr.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MColumn;
import org.compiere.model.MRefTable;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DisplayType;
import org.compiere.util.Util;

public class WspAtrReferenceService {

    /**
     * Lookup ID in the reference table for a Table / TableDir column.
     * Uses AD_Reference_ID -> AD_Ref_Table -> AD_Table.
     *
     * @param ctx           context
     * @param column        AD_Column
     * @param text          search text
     * @param useValueForRef if true, match on Value, else on Name
     * @param trxName       trx
     * @return ID or null if not found
     */
    public Integer lookupReferenceId(Properties ctx,
                                     MColumn column,
                                     String text,
                                     boolean useValueForRef,
                                     String trxName) {

        if (Util.isEmpty(text, true)) {
            return null;
        }
        text = text.trim();
        if (Util.isEmpty(text, true)) {
            return null;
        }

        // Only makes sense for Table / TableDir
        int displayType = column.getAD_Reference_ID();
        if (!(displayType == DisplayType.Table || displayType == DisplayType.TableDir)) {
            return null;
        }

        int adReferenceId = column.getAD_Reference_ID();
        MRefTable refTable = MRefTable.get(ctx, adReferenceId);
        if (refTable == null || refTable.getAD_Table_ID() <= 0) {
            return null;
        }

        int refTableId = refTable.getAD_Table_ID();
        MTable mTable = MTable.get(ctx, refTableId);
        if (mTable == null || mTable.getAD_Table_ID() <= 0) {
            return null;
        }

        String tableName = mTable.getTableName();
        String where = useValueForRef
                ? "UPPER(Value)=UPPER(?)"
                : "UPPER(Name)=UPPER(?)";

        int id = new Query(ctx, tableName, where, trxName)
                .setParameters(text)
                .firstId();

        return id > 0 ? id : null;
    }

    /**
     * Create a reference record if it doesn't exist yet.
     * Uses Value + Name from the sheet.
     *
     * @param ctx           context
     * @param parentPO      header/detail PO (for AD_Client_ID / AD_Org_ID)
     * @param column        AD_Column being populated
     * @param lookupKey     text used in error messages
     * @param valueText     string to put in Value (if column exists)
     * @param nameText      string to put in Name  (if column exists)
     * @param useValueForRef true if the column uses Value for validation
     * @param trxName       trx
     * @return ID of created (or existing) record
     * 
     *     /**
     * Ensure a reference record exists for the given (Value, Name) pair,
     * creating it in the validation table if necessary.
     *
     * @return AD_Reference_Value_ID (FK to ref table's key column)
     */
    public Integer createReferenceIfMissing(Properties ctx,
                                            PO parentPO,
                                            MColumn column,
                                            String lookupKey,
                                            String valueText,
                                            String nameText,
                                            boolean useValueForRef,
                                            String trxName) {

        // Prefer the column's validation reference (AD_Reference_Value_ID)
        int adReferenceId = column.getAD_Reference_Value_ID();
        if (adReferenceId <= 0) {
            // Fallback to AD_Reference_ID for legacy / generic cases
            adReferenceId = column.getAD_Reference_ID();
        }
        if (adReferenceId <= 0) {
            throw new AdempiereException("Column " + column.getColumnName()
                    + " has no AD_Reference_ID / AD_Reference_Value_ID configured.");
        }

        MRefTable refTable = MRefTable.get(ctx, adReferenceId);
        if (refTable == null || refTable.get_ID() <= 0) {
            throw new AdempiereException("No AD_Ref_Table for AD_Reference_ID=" + adReferenceId
                    + " (column " + column.getColumnName() + ")");
        }

        String tableName = refTable.getAD_Table().getTableName();
        MTable table = MTable.get(ctx, tableName);
        if (table == null || table.getAD_Table_ID() <= 0) {
            throw new AdempiereException("Reference table " + tableName + " not found for AD_Reference_ID=" + adReferenceId);
        }

        // Decide how to search: by Value or Name
        String value = Util.isEmpty(valueText, true) ? null : valueText.trim();
        String name  = Util.isEmpty(nameText,  true) ? null : nameText.trim();

        if (value == null && name == null) {
            throw new AdempiereException("No value or name supplied for reference creation (" + lookupKey + ")");
        }

        // Look up existing by Value or Name
        StringBuilder where = new StringBuilder();
        List<Object> params = new ArrayList<>();

        if (useValueForRef && value != null) {
            where.append("UPPER(Value)=UPPER(?)");
            params.add(value);
        } else if (name != null) {
            where.append("UPPER(Name)=UPPER(?)");
            params.add(name);
        }

        int existingId = new Query(ctx, tableName, where.toString(), trxName)
                .setParameters(params)
                .firstId();

        if (existingId > 0) {
            return existingId;
        }

        // Create new record
        PO refPO = table.getPO(0, trxName);
        if (value != null) {
            refPO.set_ValueOfColumn("Value", value);
        }
        if (name != null) {
            refPO.set_ValueOfColumn("Name", name);
        }

        // You can also copy client/org from parentPO if needed
        if (parentPO != null) {
            refPO.set_ValueOfColumn("AD_Client_ID",parentPO.getAD_Client_ID());
            refPO.setAD_Org_ID(parentPO.getAD_Org_ID());
        }

        refPO.saveEx();
        return refPO.get_ID();
    }

}
