package jp.daich.diffsellect.procedure.constants;

public enum SqlTxtLineType {
        // クエリの行
        QUERY,
        // カラム名の行
        COLUMNS_NAME,
        // 項目値の行
        VALUES,
        // クエリ行より前に位置するその他の行
        OTHER_QUERY_BEFORE,
        // 1件のSELECT結果の終端行
        FIN_OF_ONE_RESULT
}
