package jp.daich.diffsellect.procedure;

import jp.daich.diffsellect.common.io.poi.ExcelWriter;
import jp.daich.diffsellect.common.util.LogUtil;
import jp.daich.diffsellect.procedure.sub.SqlTextReadProcedure;

/**
 * 処理実行手続きクラス
 */
public class MainProcedure {

    public void execute(String tableName, String sellectResult) {
        // 開始ログ書き込み
        LogUtil.startLog(tableName, sellectResult);
        SqlTextReadProcedure sqlReader = null;
        try {
            // sql結果ファイルを読み込む
            sqlReader = new SqlTextReadProcedure("./test/sql.txt");
            // 出力するエクセルオブジェクトを生成する
            ExcelWriter eWriter = new ExcelWriter();
            String lineStr;
            // SQL結果ファイル1行ずつ読み込み
            while ((lineStr = sqlReader.readLine()) != null) {
                // Sellect結果をBookオブジェクトに設定していく
                eWriter.writeSellectResult(tableName, lineStr);
            }
            // エクセルファイルに書き込む
            eWriter.flush();
        } catch (Exception e) {
            LogUtil.debug("Exception Message :  " + e.getMessage());
            LogUtil.debug("Exception Message :  " + e.getStackTrace());
        } finally {
            if (sqlReader != null) {
                sqlReader.close();
            }
        // 終了ログ書き込み
        LogUtil.endLog();
            ;
        }
    }
}
