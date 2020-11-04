package jp.daich.diffsellect.procedure;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.daich.diffsellect.common.io.reader.TextReader;
import jp.daich.diffsellect.view.dto.ExcelViewDto;
import jp.daich.diffsellect.common.util.LogUtil;
import jp.daich.diffsellect.common.poi.ExcelWriter;
import jp.daich.diffsellect.poi.controler.OpeWorkBookControlerFactory;
import jp.daich.diffsellect.procedure.constants.SqlTxtLineType;
import jp.daich.diffsellect.procedure.sub.SqlTextReadLogic;
import jp.daich.diffsellect.poi.controler.OpeWorkBookControler;

/**
 * 処理実行手続きクラス
 */
public class MainProcedure {

    public void execute(String sqlFilePath) {
        // 開始ログ書き込み
        LogUtil.startLog(sqlFilePath);
        // sql結果ファイル読込ロジック
        TextReader reader = null;
        // ワークブック操作ロジックの生成
        OpeWorkBookControler bookContrler = new OpeWorkBookControlerFactory().create(
                // ワークブック作成
                ".\\outfile" + new SimpleDateFormat("yyyy_MM_dd(E)HH_mm_ss").format(new Date()) + ".xlsx");

        try {
            // SQLファイル読込オブジェクトの生成
            reader = new TextReader(sqlFilePath);
            // SQLファイル読み込み業務ロジックの生成
            SqlTextReadLogic sqlReadLogic = new SqlTextReadLogic(reader);
            // 出力するエクセルオブジェクトを生成する
            ExcelWriter eWriter = new ExcelWriter();
            // 読み込んだ現在行の文字列
            ExcelViewDto _dto;
            // 操作対象シートオブジェクト
            // OpeSheet sheet;
            // SELECT対象のテーブル名
            String tableName = null;

            // SQL結果ファイルを最終行まで1行ずつ読み込む
            while ((_dto = sqlReadLogic.readOneResult()) != null) {
                // TODO: dtoをもとにエクセル書き込み処理の実装
                LogUtil.debug("[success to create ExcelViewDto] " + _dto);
            }
            // エクセルファイルに書き込む
            // eWriter.flush();
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
