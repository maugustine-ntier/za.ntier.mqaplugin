package za.co.ntier.wsp_atr.process;

import org.apache.poi.ss.usermodel.Workbook;
import org.compiere.process.SvrProcess;

/**
 * Strategy interface for validating a specific sheet/tab
 * in the WSP/ATR template workbook.
 */
public interface ITemplateTabValidator {

    /**
     * @return the logical sheet name this validator targets (e.g. "Biodata").
     *         This is mostly informational; the validator can still decide
     *         how to find its sheet.
     */
    String getTargetSheetName();

    /**
     * Validate and/or modify the workbook for this tab.
     * Implementations may:
     *  - locate the sheet
     *  - read rows/columns
     *  - add error messages / helper columns
     * 
     * @param workbook the Excel workbook
     * @param process  the running SvrProcess (for ctx, trxName, addLog, etc.)
     * @throws Exception on any error
     */
    void validate(Workbook workbook, SvrProcess process) throws Exception;
}

