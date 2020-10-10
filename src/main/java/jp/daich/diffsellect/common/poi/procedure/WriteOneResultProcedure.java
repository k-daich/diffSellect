package jp.daich.diffsellect.common.poi.procedure;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import jp.daich.diffsellect.common.util.StringUtils;

public class WriteOneResultProcedure {

    // X座標
    private int xPosition = 0;
    // Y座標
    private int yPosition = 0;

    // SXSSF(xlsx)
    final SXSSFSheet sheet;

    public WriteOneResultProcedure(SXSSFWorkbook book, String tableName) {

        // 指定したテーブル名のシートがない場合は新規作成する
        if (book.getSheet(tableName) == null) {
            // create sheet
            sheet = book.createSheet(tableName);
        } else {
            // get existed sheet
            sheet = book.getSheet(tableName);
        }

    }

    // シート新規作成フラグ（一旦falseで、後続で新規作成した場合のみtrueにする）
    private boolean isNewSheet;

    public void execute(String sellectResult) {
        initProcedure();
        // mainProcedure(sellectResult);
    }

    private void initProcedure() {
        // A1セルを取得する
        Cell cell = scanEmptyCellForRight(xPosition, yPosition);
        cell.setCellValue(new SimpleDateFormat("yyyy/MM/dd(E) HH:mm:ss").format(new Date()));
    }

    private void mainProcedure(String sellectResult) {
        for(String dbKomkVal : sellectResult.split("\t")) {
            setNextYCellValue(dbKomkVal);
        }
    }

    private Cell getCell(int x, int y) {
        return sheet.createRow(x).createCell(y);
    }

    private void setNextYCellValue(String value) {
        getCell(xPosition, ++yPosition).setCellValue(value);
    }

    private Cell scanEmptyCellForRight(int startX, int y) {
        Cell cell = null;
        // startXを始点に空セルを走査する
        for (int x = startX;; x++) {
            cell = getCell(x, y);
            // 空セルだったらセルオブジェクトを返す
            if (StringUtils.isEmpty(cell.getStringCellValue()))
                return cell;
        }
    }
}
