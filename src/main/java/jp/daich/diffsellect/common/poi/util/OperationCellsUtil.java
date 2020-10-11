package jp.daich.diffsellect.common.poi.util;

import jp.daich.diffsellect.common.poi.entity.OpeCell;
import jp.daich.diffsellect.common.util.StringUtils;

public class OperationCellsUtil {

    /**
     * Invalide Construct
     */
    OperationCellsUtil() {
    }

    public static OpeCell scanEmptyCellForRight(OpeCell opeCell) {
        if(StringUtils.isEmpty(opeCell.getStringCellValue())) return opeCell;
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
}
