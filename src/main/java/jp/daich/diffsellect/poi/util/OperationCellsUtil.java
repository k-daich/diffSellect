package jp.daich.diffsellect.common.io.poi.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import jp.daich.diffsellect.common.io.poi.entity.OpeCell;
import jp.daich.diffsellect.util.LogUtil;
import jp.daich.diffsellect.util.StringUtils;

public class OperationCellsUtil {

    /**
     * Invalide Construct
     */
    OperationCellsUtil() {
    }

    public static OpeCell scanEmptyCellForRight(OpeCell opeCell) {
        if (StringUtils.isEmpty(opeCell.getStringCellValue()))
            return opeCell;
        // startXを始点に空セルを走査する
        while (true) {
            // 右に１つ移動する
            opeCell = opeCell.nextX();
            // 空セルだったらセルオブジェクトを返す
            if (StringUtils.isEmpty(opeCell.getStringCellValue())) {
                return opeCell;
            }
        }
    }

    /**
     * 条件付き書式を設定する
     * 
     * @param conditionStr 条件文字列（例： $B$2=$C$2)
     * @param applyRange   適用範囲（例： $B$2:$C$2)
     * @param IndexedColors 条件を満たした場合の背景色指定
     * @param sheet
     */
    public static void setSheetConditionalFormat(String conditionStr, String applyRange, IndexedColors color ,Sheet sheet) {
        LogUtil.debug("conditionStr : " + conditionStr + ", applyRange : " + applyRange);
        SheetConditionalFormatting conditionFormat = sheet.getSheetConditionalFormatting();

        ConditionalFormattingRule rule1 = conditionFormat.createConditionalFormattingRule(conditionStr);
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(color.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        CellRangeAddress[] regions = { CellRangeAddress.valueOf(applyRange) };
        conditionFormat.addConditionalFormatting(regions, rule1);
    }

    /**
     * 条件付き書式を設定する
     * 
     * @param borderStyle BorderStyleオブジェクトによる定数指定
     * @param IndexedColors 枠線色指定
     * @param book
     * @return CellStyle 枠線のスタイルオブジェクト
     */
    public static CellStyle createBorderStyle(BorderStyle borderStyle , IndexedColors color ,Workbook book) {
        CellStyle style = book.createCellStyle();
        // 枠線のスタイルを設定する
        style.setBorderTop(borderStyle);
        style.setBorderBottom(borderStyle);
        style.setBorderLeft(borderStyle);
        style.setBorderRight(borderStyle);

        // 枠線の色を設定する
        style.setTopBorderColor(color.getIndex());
        style.setBottomBorderColor(color.getIndex());
        style.setLeftBorderColor(color.getIndex());
        style.setRightBorderColor(color.getIndex());

        return style;
    }

}
