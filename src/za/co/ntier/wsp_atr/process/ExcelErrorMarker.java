package za.co.ntier.wsp_atr.process;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelErrorMarker {

    private CellStyle errorStyle; // cached per workbook

    public void markError(Workbook wb, Sheet sheet, Row row, int colIndex, String message) {
        if (row == null) return;

        Cell cell = row.getCell(colIndex);
        if (cell == null) cell = row.createCell(colIndex);

        // style
        CellStyle style = getOrCreateErrorStyle(wb);
        cell.setCellStyle(style);

        // comment (Excel popup)
        addOrReplaceComment(wb, sheet, cell, "Invalid:\n" + message);
    }

    private CellStyle getOrCreateErrorStyle(Workbook wb) {
        if (errorStyle != null) return errorStyle;

        CellStyle cs = wb.createCellStyle();
        cs.cloneStyleFrom(wb.createCellStyle());

        // Fill light red
        cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cs.setFillForegroundColor(IndexedColors.ROSE.getIndex());

        // Optional: red font
        Font f = wb.createFont();
        f.setColor(IndexedColors.RED.getIndex());
        cs.setFont(f);

        // Optional: border
        cs.setBorderBottom(BorderStyle.THIN);
        cs.setBorderTop(BorderStyle.THIN);
        cs.setBorderLeft(BorderStyle.THIN);
        cs.setBorderRight(BorderStyle.THIN);

        errorStyle = cs;
        return errorStyle;
    }

    private void addOrReplaceComment(Workbook wb, Sheet sheet, Cell cell, String text) {
        // .xlsm is XSSF-based; keep it safe
        if (!(wb instanceof XSSFWorkbook) || !(sheet instanceof XSSFSheet) || !(cell instanceof XSSFCell)) {
            return;
        }

        XSSFCell xcell = (XSSFCell) cell;
        XSSFSheet xsheet = (XSSFSheet) sheet;

        CreationHelper factory = wb.getCreationHelper();

        // If comment already exists, just update it (no orphaning)
        Comment existing = xcell.getCellComment();
        if (existing != null) {
            existing.setString(factory.createRichTextString(text));
            existing.setAuthor("WSP/ATR Validator");
            return;
        }

        // Create a new comment
        Drawing<?> drawing = xsheet.createDrawingPatriarch();

        ClientAnchor anchor = factory.createClientAnchor();
        anchor.setCol1(cell.getColumnIndex());
        anchor.setCol2(cell.getColumnIndex() + 3);
        anchor.setRow1(cell.getRowIndex());
        anchor.setRow2(cell.getRowIndex() + 4);

        Comment comment = drawing.createCellComment(anchor);
        comment.setString(factory.createRichTextString(text));
        comment.setAuthor("WSP/ATR Validator");

        xcell.setCellComment(comment);
    }
}

