package jp.daich.diffsellect.procedure.sub;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.daich.diffsellect.common.io.poi.entity.OpeCell;
import jp.daich.diffsellect.common.io.poi.util.OperationCellsUtil;
import jp.daich.diffsellect.common.util.LogUtil;

public class WriteOneResultProcedure {

    // SXSSF(xlsx)
    final Workbook book;
    // SXSSF(xlsx)
    final Sheet sheet;
    // Operatable Cell
    OpeCell opeCell;

    static final Map<Integer, Sheet> sheetMap = new HashMap<Integer, Sheet>();

    public WriteOneResultProcedure(XSSFWorkbook book, String tableName) {
        // bookオブジェクトを設定する
        this.book = book;
        LogUtil.debug("[table name] : " + tableName + " ,[hash code] : " + tableName.hashCode());
        // 指定したテーブル名のシートがない場合は新規作成する
        if (sheetMap.get(tableName.hashCode()) == null) {
            // create sheet
            sheet = book.createSheet(tableName);
            sheetMap.put(tableName.hashCode(), sheet);
        } else {
            // get existed sheet
            sheet = sheetMap.get(tableName.hashCode());
            // sheet = book.getSheet(tableName);
        }
        // シートの行の高さのデフォルト設定を行う
        sheet.setDefaultRowHeightInPoints((short) 12);
        // シートの列幅のデフォルト設定を行う
        sheet.setDefaultColumnWidth((short) 32);
        // OpeCellをA1位置で初期化
        this.opeCell = new OpeCell(sheet, 0, 0);
    }

    public void execute(String sellectResult) {
        LogUtil.startLog(sellectResult);;
        initProcedure();
        mainProcedure(sellectResult);
        LogUtil.endLog();
    }

    private void initProcedure() {
        // A1セルを取得する
        this.opeCell = OperationCellsUtil.scanEmptyCellForRight(opeCell);
        this.opeCell.setCellValue(new SimpleDateFormat("yyyy/MM/dd(E) HH:mm:ssSSS").format(new Date()));
    }

    private void mainProcedure(String sellectResult) {
        LogUtil.startLog(sellectResult);
        // フォント設定オブジェクトの生成
        Font font = this.book.createFont();
        font.setFontName("Meiryo UI");
        font.setFontHeightInPoints((short) 9);

        // セルスタイル設定オブジェクトの生成
        CellStyle style = OperationCellsUtil.createBorderStyle(BorderStyle.THIN, IndexedColors.BLACK, this.book);
        style.setFont(font);

        for (String dbKomkVal : sellectResult.split("\t")) {
            // 現在位置から1つ下に移動し、DB項目値を設定する
            this.opeCell.nextY().setCellValue(dbKomkVal);
            // セルの周囲に罫線を付ける
            opeCell.getCell().setCellStyle(style);

            // 初回（一番左列）以外の時は、条件付き書式（前回DB項目値との比較確認）を設定する
            if (this.opeCell.getX() > 0) {
                OperationCellsUtil.setSheetConditionalFormat(
                        this.opeCell.getCellAddressStr(this.opeCell.getX() - 1, this.opeCell.getY()) + " = "
                                + this.opeCell.getCellAddressStr(this.opeCell.getX(), this.opeCell.getY()),
                        this.opeCell.getCellAddressStr(this.opeCell.getX(), this.opeCell.getY()),
                        IndexedColors.LEMON_CHIFFON, this.sheet);
            }
        }
        LogUtil.endLog();
    }
}
