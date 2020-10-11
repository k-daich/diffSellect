package jp.daich.diffsellect.procedure;

import jp.daich.diffsellect.common.poi.ExcelWriter;
import jp.daich.diffsellect.common.util.LogUtil;

/**
 * 処理実行手続きクラス
 */
public class MainProcedure {

    public void execute(String tableName, String sellectResult) {
        LogUtil.debug(this.getClass(), "★★★start★★★");
        try {
            ExcelWriter eWriter = init(tableName);

            // Sellect結果をBookオブジェクトに設定していく
            eWriter.writeSellectResult(tableName, sellectResult);

            // エクセルファイルに書き込む
            eWriter.flush();
        } finally {
            LogUtil.debug(this.getClass(), "★★★end★★★");
        }
    }

    private ExcelWriter init(String tableName) {
        return new ExcelWriter(tableName);
    }
}
