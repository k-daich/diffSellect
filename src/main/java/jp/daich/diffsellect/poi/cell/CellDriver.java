package jp.daich.diffsellect.poi.cell;

import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.util.CellRangeAddress;

import jp.daich.diffsellect.poi.book.constants.StyleMapKey;
import jp.daich.diffsellect.util.LogUtil;
import jp.daich.diffsellect.util.StringUtils;

public class CellDriver {

    /**
     * Constructor
     * 
     * @param sheet
     * @param xPostion
     * @param yPosition
     */
    public CellDriver(Sheet sheet, Map<String, CellStyle> styleMap, int xPostion, int yPosition) {
        this.styleMap = styleMap;
        this.opeCell = new OpeCell(sheet, styleMap.get(StyleMapKey.NON_BORDER), xPostion, yPosition);
    }

    // セル操作用エンティティ
    private final OpeCell opeCell;
    // スタイル設定用オブジェクト
    private final Map<String, CellStyle> styleMap;

    public void move(int x, int y) {
        this.opeCell.move(x, y);
    }

    public String getStringCellValue() {
        return this.opeCell.getStringCellValue();
    }

    public void setCellValue(String value) {
        this.opeCell.setCellValue(value);
    }

    /**
     * 1つ上に移動する
     */
    public void preY() {
        this.opeCell.move(this.opeCell.getX(), this.opeCell.getY() - 1);
    }

    /**
     * 1つ下に移動する
     */
    public void nextY() {
        this.opeCell.move(this.opeCell.getX(), this.opeCell.getY() + 1);
    }

    /**
     * 1つ右に移動する
     */
    public void nextX() {
        this.opeCell.move(this.opeCell.getX() + 1, this.opeCell.getY());
    }

    /**
     * 
     * @param xMargin
     * @param yMargin
     * @return
     */
    public String getStringNextCellValue(int xMargin, int yMargin) {
        this.opeCell.move(this.opeCell.getX() + xMargin, this.opeCell.getY() + yMargin);
        return this.opeCell.getStringCellValue();
    }

    /**
     * 
     * @param xPosition
     * @return
     */
    private String getXAsAtoZ(int xPosition) {
        int n = xPosition + 1;
        StringBuilder buf = new StringBuilder();
        while (n != 0) {
            buf.append((char) ((n - 1) % 26 + 'A'));
            n = (n - 1) / 26;
        }
        return buf.reverse().toString();
    }

    public String getCellAddressStr() {
        return getXAsAtoZ(this.opeCell.getX()) + (this.opeCell.getY() + 1);
    }

    public String getCellAddressStr(int xPosition, int yPosition) {
        return getXAsAtoZ(xPosition) + (yPosition + 1);
    }

    /**
     * 空セルに到達するまで右に移動する
     */
    public void goEmptyCellForRight() {
        // 現在位置のセル値が空となるまで繰り返す
        while (StringUtils.isNotEmpty(this.opeCell.getStringCellValue())) {
            // 右に１つ移動する
            nextX();
        }
    }

    /**
     * 条件付き書式を設定する
     *
     * @param conditionStr  条件文字列（例： $B$2=$C$2)
     * @param applyRange    適用範囲（例： $B$2:$C$2)
     * @param IndexedColors 条件を満たした場合の背景色指定
     */
    public void setSheetConditionalFormat(String conditionStr, String applyRange, IndexedColors color) {
        LogUtil.debug("conditionStr : " + conditionStr + ", applyRange : " + applyRange);
        SheetConditionalFormatting conditionFormat = this.opeCell.getSheetConditionalFormatting();

        ConditionalFormattingRule rule1 = conditionFormat.createConditionalFormattingRule(conditionStr);
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(color.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        CellRangeAddress[] regions = { CellRangeAddress.valueOf(applyRange) };
        conditionFormat.addConditionalFormatting(regions, rule1);
    }

    public void setStyle(StyleMapKey key) {
        this.opeCell.setStyle(styleMap.get(key));
    }
}
