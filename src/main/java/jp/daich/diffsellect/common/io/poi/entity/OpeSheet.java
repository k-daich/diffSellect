package jp.daich.diffsellect.common.io.poi.entity;

import org.apache.poi.ss.usermodel.Sheet;

public class OpeSheet {
    
    /**
     * Constructor
     */
    public OpeSheet() {

    }

    // SELECTクエリ文のハッシュ値
    private String selectQueryHashCode;
    // SELECTクエリ文のテーブル名
    private String tableName;
    // シートに記述済みの列数
    private String printedColumnNum;
    // poiシートオブジェクト
    private Sheet sheet;

    public String getSelectQueryHashCode() {
        return selectQueryHashCode;
    }

    public void setSelectQueryHashCode(String selectQueryHashCode) {
        this.selectQueryHashCode = selectQueryHashCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrintedColumnNum() {
        return printedColumnNum;
    }

    public void setPrintedColumnNum(String printedColumnNum) {
        this.printedColumnNum = printedColumnNum;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }
}
