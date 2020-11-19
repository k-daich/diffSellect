package jp.daich.diffsellect.procedure;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Sheet;

import jp.daich.diffsellect.common.io.reader.TextReader;
import jp.daich.diffsellect.view.dto.ExcelViewDto;
import jp.daich.diffsellect.util.LogUtil;
import jp.daich.diffsellect.poi.book.OpeBook;
import jp.daich.diffsellect.poi.sheet.OpeSheet;
import jp.daich.diffsellect.procedure.constants.SqlTxtLineType;
import jp.daich.diffsellect.poi.procedure.ExcelBuildLogic;
import jp.daich.diffsellect.procedure.sub.SqlTextReadLogic;

/**
 * 処理実行手続きクラス
 */
public class MainProcedure {

    public void execute(String sqlFilePath, String outPutFilePath) {
        // 開始ログ書き込み
        LogUtil.startLog(sqlFilePath);
        // sql結果ファイル読込ロジック
        TextReader reader = null;

        try {
            // SQLファイル読込オブジェクトの生成
            reader = new TextReader(sqlFilePath);
            // SQLファイル読み込み業務ロジックの生成
            SqlTextReadLogic _readLogic = new SqlTextReadLogic(reader);
            // 読み込んだ現在行の文字列
            ExcelViewDto _dto;
            // 操作するブックオブジェクト
            OpeBook _book = new OpeBook(outPutFilePath);

            // SQL結果ファイルを最終行まで1行ずつ読み込む
            while ((_dto = _readLogic.readOneResult()) != null) {
                // シート名（テーブル名@クエリのハッシュ値）を取得する
                String _sheetName = _dto.getTableName() + "@" + _dto.getSqlQuery().hashCode();
                OpeSheet _opeSheet = _book.getSheet(_sheetName);
                // シートがnullの場合
                if (_opeSheet == null) {
                    // シートを作成する
                    _opeSheet = _book.createSheet(_sheetName);
                }
                // Dtoの内容を元にエクセルのシートオブジェクトへ設定する
                new ExcelBuildLogic(_opeSheet, _book.getStyleMap()).buildOneResult(_dto);
            }
            // エクセルファイルを出力する
            _book.flush();
        } catch (Exception e) {
            LogUtil.debug("Exception Message :  " + e.getMessage());
            e.printStackTrace();
        } finally {
            // ファイルオープン中であればクローズする
            if (reader != null) {
                reader.close();
            }
            // 終了ログ書き込み
            LogUtil.endLog();
        }
    }
}
