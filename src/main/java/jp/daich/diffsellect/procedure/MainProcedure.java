package jp.daich.diffsellect.procedure;

import jp.daich.diffsellect.common.poi.ExcelWriter;
import jp.daich.diffsellect.common.util.LogUtil;
import jp.daich.diffsellect.procedure.sub.SqlTextReadProcedure;

/**
 * 処理実行手続きクラス
 */
public class MainProcedure {

    public void execute(String tableName, String sellectResult) {
        LogUtil.debug(this.getClass(), "★★★start★★★");
        SqlTextReadProcedure sqlReader = null;
        try {
            sqlReader = new SqlTextReadProcedure("./test/sql.txt");
            ExcelWriter eWriter = new ExcelWriter(tableName);

            String lineStr;
            while ((lineStr = sqlReader.readLine()) != null) {
                // Sellect結果をBookオブジェクトに設定していく
                eWriter.writeSellectResult(tableName, lineStr);
            }
            // エクセルファイルに書き込む
            eWriter.flush();
        } catch (Exception e) {
            LogUtil.debug(this.getClass(), "Exception Message :  " + e.getMessage());
            LogUtil.debug(this.getClass(), "Exception Message :  " + e.getStackTrace());
        } finally {
            if (sqlReader != null) {
                sqlReader.close();
            }
            LogUtil.debug(this.getClass(), "★★★end★★★");
        }
    }
}
