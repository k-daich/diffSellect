package jp.daich.diffsellect.procedure;

import jp.daich.diffsellect.common.poi.ExcelWriter;

/**
 * 処理実行手続きクラス
 */
public class MainProcedure {

    public void execute(String tableName, String sellectResult) {
        ExcelWriter eWriter = new ExcelWriter(tableName);
        eWriter.flush(sellectResult);
    }
}