package jp.daich.diffsellect.procedure;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Sheet;

import jp.daich.diffsellect.common.io.reader.TextReader;
import jp.daich.diffsellect.view.dto.ExcelViewDto;
import jp.daich.diffsellect.util.LogUtil;
import jp.daich.diffsellect.poi.ExcelWriter;
import jp.daich.diffsellect.procedure.constants.SqlTxtLineType;
import jp.daich.diffsellect.procedure.sub.ExcelBuildLogic;
import jp.daich.diffsellect.procedure.sub.SqlTextReadLogic;

/**
 * 処理実行手続きクラス
 */
public class MainProcedure {

    public void execute(String sqlFilePath) {
        // 開始ログ書き込み
        LogUtil.startLog(sqlFilePath);
        // sql結果ファイル読込ロジック
        TextReader reader = null;
        // 出力する差分エクセルファイルのPath
        String outputExcelFilePath = ".\\outfile" + new SimpleDateFormat("yyyy_MM_dd(E)HH_mm_ss").format(new Date()) + ".xlsx";

        try {
            // SQLファイル読込オブジェクトの生成
            reader = new TextReader(sqlFilePath);
            // SQLファイル読み込み業務ロジックの生成
            SqlTextReadLogic _readLogic = new SqlTextReadLogic(reader);
            // 出力するエクセルオブジェクトを生成する
            ExcelWriter _eWriter = new ExcelWriter(outputExcelFilePath);
            // 読み込んだ現在行の文字列
            ExcelViewDto _dto;

            // SQL結果ファイルを最終行まで1行ずつ読み込む
            while ((_dto = _readLogic.readOneResult()) != null) {
                // シート名（テーブル名@クエリのハッシュ値）を取得する
                String _sheetName = _dto.getTableName() + "@" + _dto.getSqlQuery().hashCode();
                Sheet _sheet = _eWriter.getSheet(_sheetName);
                // シートがnullの場合
                if(_sheet == null) {
                    // シートを作成する
                    _sheet = _eWriter.createSheet(_sheetName);
                }
                // Dtoの内容を元にエクセルのシートオブジェクトへ設定する
                new ExcelBuildLogic(_sheet, _eWriter.getFont()).buildOneResult(_dto);;
            }
            // エクセルファイルを出力する
            _eWriter.flush();
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
