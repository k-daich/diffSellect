package jp.daich.diffsellect.common.poi.procedure;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

import jp.daich.diffsellect.common.poi.entity.OpeCell;
import jp.daich.diffsellect.common.poi.util.OperationCellsUtil;
import jp.daich.diffsellect.common.util.LogUtil;
import jp.daich.diffsellect.common.util.StringUtils;

public class WriteOneResultProcedure {

    // SXSSF(xlsx)
    final Sheet sheet;
    // Operatable Cell
    OpeCell opeCell;

    public WriteOneResultProcedure(SXSSFWorkbook book, String tableName) {

        // 指定したテーブル名のシートがない場合は新規作成する
        if (book.getSheet(tableName) == null) {
            // create sheet
            sheet = book.createSheet(tableName);
        } else {
            // get existed sheet
            sheet = book.getSheet(tableName);
        }
        // OpeCellをA1位置で初期化
        this.opeCell = new OpeCell(sheet, 0, 0);
    }

    public void execute(String sellectResult) {
        LogUtil.debug(this.getClass(), "★★★start★★★");
        initProcedure();
        mainProcedure(sellectResult);
        LogUtil.debug(this.getClass(), "☆☆☆end☆☆☆");
    }

    private void initProcedure() {
        // A1セルを取得する
        this.opeCell = OperationCellsUtil.scanEmptyCellForRight(opeCell);
        this.opeCell.setCellValue(new SimpleDateFormat("yyyy/MM/dd(E) HH:mm:ssSSS").format(new Date()));
    }

    private void mainProcedure(String sellectResult) {
        for (String dbKomkVal : sellectResult.split("\t")) {
            if (this.opeCell.getX() > 0) {
                OperationCellsUtil.setSheetConditionalFormat(opeCell, this.sheet);
            }
            this.opeCell.nextY().setCellValue(dbKomkVal);
        }
    }
}
