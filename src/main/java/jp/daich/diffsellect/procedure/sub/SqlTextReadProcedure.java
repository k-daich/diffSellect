package jp.daich.diffsellect.procedure.sub;

import jp.daich.diffsellect.common.util.StringUtils;
import jp.daich.diffsellect.procedure.constants.SqlTxtLineType;

public class SqlTextReadProcedure {

    /**
     * Constructor
     */
    private SqlTextReadProcedure() {
    }

    /**
     * 現在行の行タイプを判定して返す
     * 
     * @param line        現在行の文字列
     * @param preLineType 前回行の行タイプ
     */
    public static SqlTxtLineType getLineType(String line, SqlTxtLineType preLineType) {
        switch (preLineType) {
            case QUERY:
                // クエリの次の行はカラム名の行
                return SqlTxtLineType.COLUMNS_NAME;
            case COLUMNS_NAME:
                // カラム名の次の行はSELECT結果（値）の行
                return SqlTxtLineType.VALUES;
            case VALUES:
                // 現在行が空である場合
                if ("".equals(line)) {
                    // SELECT結果の最終行（空行）であるため、タイプはその他
                    return SqlTxtLineType.OTHER;
                }
                // 現在行に値がある場合はSELECT結果（値）の行
                return SqlTxtLineType.VALUES;
            case OTHER:
                // 現在行が">SQL"で始待っている場合、クエリの行
                if (line.startsWith(">SQL")) {
                    return SqlTxtLineType.QUERY;
                }
                return SqlTxtLineType.OTHER;
            // default:
            // throw new RuntimeException("System Error : Unknown Enum Type");
        }
        // 到達不可な箇所
        throw new RuntimeException("System Error : Unreachable Code");
    }

    /**
     * クエリからテーブル名を取得する
     * 
     * @param query SQLクエリ
     * @return テーブル名
     */
    public static String getTableName(String query) {
        return StringUtils.cut(query.toUpperCase(), "FROM ", " WHERE");
    }
}
