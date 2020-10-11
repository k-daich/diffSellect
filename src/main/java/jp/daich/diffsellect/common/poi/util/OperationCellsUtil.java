package jp.daich.diffsellect.common.poi.util;

import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.util.CellRangeAddress;

import jp.daich.diffsellect.common.poi.entity.OpeCell;
import jp.daich.diffsellect.common.util.LogUtil;
import jp.daich.diffsellect.common.util.StringUtils;

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

    public static void setSheetConditionalFormat(OpeCell opeCell, Sheet sheet) {
        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        // Condition 1: Cell Value is equal to green (Green Fill)
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(" = " + opeCell.getStringNextCellValue(-1, 0));
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.GREEN.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
        LogUtil.debug(OperationCellsUtil.class,
                "opeCell.getXAsAtoZ() + (opeCell.getY() + 1) : " + opeCell.getXAsAtoZ() + (opeCell.getY() + 1));

        CellRangeAddress[] regions = { CellRangeAddress.valueOf("A1:B5") };
        sheetCF.addConditionalFormatting(regions, rule1);
    }
}
