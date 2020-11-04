package jp.daich.diffsellect.view.dto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.daich.diffsellect.util.ObjectUtil;

/**
 * エクセルに表示する値を保持するDto
 */
public class ExcelViewDto {

    /**
     * Constructor
     */
    public ExcelViewDto() {
    }

    private Date sqlExecuteTime;

    private String tableName;

    private String sqlQuery;

    private String[] columnNames;

    private List<String[]> resultSets = new ArrayList<>();

    public Date getSqlExecuteTime() {
        return sqlExecuteTime;
    }

    public void setSqlExecuteTime(Date sqlExecuteTime) {
        this.sqlExecuteTime = sqlExecuteTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public List<String[]> addResultSets() {
        return resultSets;
    }

    public void addResultSet(String[] columnValues) {
        resultSets.add(columnValues);
    }

    /**
     * Dtoの内部変数の各項目名と値を文字列として返す
     * 
     * @return Dtoの内部変数の項目名と値
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        // Reflectを利用してDto各項目文だけ繰り返す
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                // Dtoの変数名と値を取得して文字列追加する
                sb.append("name [" + field.getName() + "] value [" + ObjectUtil.toString(field.get(this)) + "]\n");
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
