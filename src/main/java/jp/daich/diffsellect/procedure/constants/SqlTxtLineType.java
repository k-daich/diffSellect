package jp.daich.diffsellect.procedure.constants;

public enum SqlTxtLineType {
        // クエリ行より前に位置するその他の行
        OTHER_QUERY_BEFORE,
        // クエリ行より前に位置するSQL実行時刻の行
        TIME_QUERY_BEFORE,
        // クエリの行
        SELECT_QUERY,
        // カラム名の行
        COLUMNS_NAME,
        // 項目値の行
        VALUES,
        // 1件のSELECT結果の終端行
        FIN_OF_ONE_RESULT
}
