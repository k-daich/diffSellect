package jp.daich.diffsellect.poi.cell.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CellStyleBuilder {
    public CellStyleBuilder(XSSFWorkbook book, IndexedColors bgColor, boolean isWrapText, Font font) {
        this.style = book.createCellStyle();
        this.style.setFillBackgroundColor(bgColor.getIndex());
        this.style.setWrapText(isWrapText);
        this.style.setFont(font);
    }

    private final CellStyle style;

    public CellStyle build() {
        return this.style;
    }

    public CellStyleBuilder borderTop(BorderStyle borderStyle) {
        this.style.setBorderTop(borderStyle);
        return this;
    }

    public CellStyleBuilder borderBottom(BorderStyle borderStyle) {
        this.style.setBorderBottom(borderStyle);
        return this;
    }

    public CellStyleBuilder borderLeft(BorderStyle borderStyle) {
        this.style.setBorderLeft(borderStyle);
        return this;
    }

    public CellStyleBuilder borderRight(BorderStyle borderStyle) {
        this.style.setBorderRight(borderStyle);
        return this;
    }
}
