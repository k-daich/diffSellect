package jp.daich.diffsellect.procedure;

import jp.daich.diffsellect.common.io.poi.ExcelWriter;
import jp.daich.diffsellect.common.io.poi.entity.OpeSheet;
import jp.daich.diffsellect.common.io.reader.TextReader;
import jp.daich.diffsellect.common.util.LogUtil;
import jp.daich.diffsellect.procedure.constants.SqlTxtLineType;
import jp.daich.diffsellect.procedure.sub.SqlTextReadProcedure;

/**
 * 処理実行手続きクラス
 */
public class MainProcedure {

    public void execute(String sqlFilePath) {
        // System.out.println(1 + 112 * 7 / 23 + 974 * 69 - 765 + 37 / 901);
        // 開始ログ書き込み
        LogUtil.startLog(sqlFilePath);
        // sql結果ファイルを読み込む
        TextReader sqlReader = null;
        try {
            sqlReader = new TextReader(sqlFilePath);
            // 出力するエクセルオブジェクトを生成する
            ExcelWriter eWriter = new ExcelWriter();
            // 読み込んだ現在行の文字列
            String lineStr;
            // 操作対象シートオブジェクト
            OpeSheet sheet;
            // SELECT対象のテーブル名
            String tableName = null;
            // 読み込んだ行のタイプ
            SqlTxtLineType lineType = SqlTxtLineType.OTHER;

            // SQL結果ファイルを最終行まで1行ずつ読み込む
            while ((lineStr = sqlReader.readLine()) != null) {
                // 現在行の行タイプを判定する
                switch (lineType = SqlTextReadProcedure.getLineType(lineStr, lineType)) {
                    case QUERY:
                        // エクセルにクエリの内容を書き込む
                        tableName = SqlTextReadProcedure.getTableName(lineStr);
                        eWriter.writeQuery(lineStr);
                        break;
                    case COLUMNS_NAME:
                        // エクセルにカラム名の内容を書き込む
                        eWriter.writeSellectResult(tableName, lineStr);
                        break;
                    case VALUES:
                        // エクセルにSELECT結果(値)の内容を書き込む
                        eWriter.writeSellectResult(tableName, lineStr);
                        break;
                    case OTHER:
                        break;
                    default:
                        throw new RuntimeException("System Error : Unknown Enum Type");
                }
                // // Sellect結果をBookオブジェクトに設定していく
                // eWriter.writeSellectResult(tableName, lineStr);
            }
            // エクセルファイルに書き込む
            eWriter.flush();
        } catch (Exception e) {
            LogUtil.debug("Exception Message :  " + e.getMessage());
            e.printStackTrace();
        } finally {
            // ファイルオープン中であればクローズする
            if (sqlReader != null) {
                sqlReader.close();
            }
            // 終了ログ書き込み
            LogUtil.endLog();
        }
    }
}
