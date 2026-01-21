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
        // Only XSSF supports rich comment handling nicely; .xlsm is XSSF-based
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        CreationHelper factory = wb.getCreationHelper();

        ClientAnchor anchor = factory.createClientAnchor();
        anchor.setCol1(cell.getColumnIndex());
        anchor.setCol2(cell.getColumnIndex() + 3);
        anchor.setRow1(cell.getRowIndex());
        anchor.setRow2(cell.getRowIndex() + 4);

        Comment comment = drawing.createCellComment(anchor);
        comment.setString(factory.createRichTextString(text));
        comment.setAuthor("WSP/ATR Validator");

        cell.removeCellComment();
        cell.setCellComment(comment);
    }
}

