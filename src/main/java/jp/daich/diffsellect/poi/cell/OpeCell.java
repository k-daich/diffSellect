package jp.daich.diffsellect.poi.cell;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;

import jp.daich.diffsellect.util.LogUtil;

public class OpeCell {

    /**
     * Constructor
     *
     * @param sheet
     * @param xPostion
     * @param yPosition
     */
    OpeCell(Sheet sheet, CellStyle style, int xPostion, int yPosition) {
        this.sheet = sheet;
        this.cell = getCell(getRow(yPosition), xPostion);
        this.style = style;
    }

    // SXSSF(xlsx)
    private final Sheet sheet;

    // cell
    private Cell cell;

    // style
    private CellStyle style;

    String getStringCellValue() {
        return this.cell.getStringCellValue();
    }

    void setCellValue(String value) {
        LogUtil.debug("Cell Action : setCellValue [" + this.cell.getStringCellValue() + "] → [" + value + "]");
        this.cell.setCellStyle(style);
        this.cell.setCellValue(value);
    }

    int getX() {
        return this.cell == null ? -1 : this.cell.getColumnIndex();
    }

    int getY() {
        return this.cell.getRowIndex();
    }

    void move(int x, int y) {
        LogUtil.debug("Cell Action : move [" + getX() + ", " + getY() + "] → [" + x + ", " + y + "]");
        this.cell = getCell(getRow(y), x);
    }

    private Cell getCell(Row row, int x) {
        Cell cell = row.getCell(x);
        if (cell == null) {
            cell = row.createCell(x);
        }
        return cell;
    }

    private Row getRow(int y) {
        Row row = this.sheet.getRow(y);
        if (row == null) {
            row = this.sheet.createRow(y);
            row.setHeight((short)-1);
        }
        return row;
    }

    void moveX(int xPosition) {
        move(xPosition, this.cell.getRowIndex());
    }

    void moveY(int yPosition) {
        move(this.cell.getColumnIndex(), yPosition);
    }

    SheetConditionalFormatting getSheetConditionalFormatting() {
        return this.sheet.getSheetConditionalFormatting();
    }

    void setStyle(CellStyle style) {
        this.style = style;
    }
}
