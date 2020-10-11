package jp.daich.diffsellect.common.poi.entity;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class OpeCell {

    public OpeCell(Sheet sheet, int xPostion, int yPosition) {
        this.sheet = sheet;
        cell = getCell(xPostion, yPosition);
    }

    // SXSSF(xlsx)
    private final Sheet sheet;
    // cell
    private Cell cell;

    public OpeCell nextX() {
        this.cell = getCell(this.cell.getColumnIndex() + 1, this.cell.getRowIndex());
        return this;
    }

    public OpeCell nextY() {
        this.cell = getCell(this.cell.getColumnIndex(), this.cell.getRowIndex() + 1);
        return this;
    }

    public String getStringCellValue() {
        return this.cell.getStringCellValue();
    }

    public String getStringNextCellValue(int xMargin, int yMargin) {
        Cell marginedCell = this.getCell(this.getX() + xMargin, this.getY() + yMargin);
        return marginedCell.getStringCellValue();
    }

    public void setCellValue(String value) {
        this.cell.setCellValue(value);
    }

    public String getXAsAtoZ() {
        int n = this.getX() + 1;
        StringBuilder buf = new StringBuilder();
        while (n != 0) {
            buf.append((char) ((n - 1) % 26 + 'A'));
            n = (n - 1) / 26;
        }
        return buf.reverse().toString();
    }

    public int getX() {
        return this.cell.getColumnIndex();
    }

    public int getY() {
        return this.cell.getRowIndex();
    }

    public void setX(int xPosition) {
        getCell(xPosition, this.cell.getRowIndex());
    }

    public void setY(int yPosition) {
        getCell(this.cell.getColumnIndex(), yPosition);
    }

    public int incrementX() {
        int x = this.cell.getColumnIndex() + 1;
        getCell(x, this.cell.getRowIndex());
        return x;
    }

    public int incrementY() {
        int y = this.cell.getRowIndex() + 1;
        getCell(this.cell.getColumnIndex(), y);
        return y;
    }

    public int decrementX() {
        int x = this.cell.getColumnIndex() - 1;
        getCell(x, this.cell.getRowIndex());
        return x;
    }

    public int decrementY() {
        int y = this.cell.getRowIndex() - 1;
        getCell(this.cell.getColumnIndex(), y);
        return y;
    }

    private Cell getCell(int x, int y) {
        return getCell(getRow(y), x);
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
        }
        return row;
    }
}
