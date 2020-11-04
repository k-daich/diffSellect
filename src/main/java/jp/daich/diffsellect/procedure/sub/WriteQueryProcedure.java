package jp.daich.diffsellect.procedure.sub;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jp.daich.diffsellect.common.io.poi.entity.OpeCell;
import jp.daich.diffsellect.common.util.LogUtil;

public class WriteQueryProcedure {

    // SXSSF(xlsx)
    final Workbook book;
    // SXSSF(xlsx)
    final Sheet sheet;
    // Operatable Cell
    OpeCell opeCell;
    
    static final Map<Integer, Sheet> sheetMap = new HashMap<Integer, Sheet>();

    public WriteQueryProcedure(XSSFWorkbook book, String tableName) {
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

    public void execute(String query) {
        LogUtil.startLog(query);
        // A1セルに移動する
        opeCell.setXY(1,1);
        opeCell.setCellValue(query);
        LogUtil.endLog();
    }

}
