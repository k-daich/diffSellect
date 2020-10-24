package jp.daich.diffsellect.procedure.sub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SqlTextReadProcedure {

    // 読み込みReaderオブジェクト
    private final BufferedReader reader;

    private enum SQL_TXT_LINE_TYPE {
        // クエリの行
        QUERY,
        // カラム名の行
        COLUMNS_NAME,
        // 項目値の行
        VALUES,
        // その他
        OTHER
    }

    // 現在読み込んでいるSQLテキストの行種類
    private SQL_TXT_LINE_TYPE currentLineType = SQL_TXT_LINE_TYPE.OTHER;

    // 読み込みファイルの最終行に到達したフラグ
    private boolean isEndLine = false;

    // 読み込み中の文字列
    private String lineStr;

    /**
     * Constructor
     */
    public SqlTextReadProcedure(String filePath) {
        try {
            reader = new BufferedReader(new FileReader(new File(filePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 次の行を読み込み、trueを返す。<br>
     * 最終行に到達済みだった場合はfalseを返す。
     * 
     * @return 次行の読み込み対象が存在したか
     */
    public boolean fetchNext() throws IOException {
        this.lineStr = reader.readLine();
        return this.lineStr != null;
    }

    /**
     * 読み込んだ行の文字列を返す
     * 
     * @return 読み込んだ行の文字列
     */
    public String getLineStr() {
        return this.lineStr;
    }

    /**
     * 1行分の文字列を返す<br>
     * 最終行まで到達していた場合はnullを返す
     * 
     * @return lineString
     */
    public String readLine() {
        try {
            // 読み込んだ行をシステム標準出力する
            return nextTargetLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 次の読み込み対象行の文字列を返す<br>
     * 最終行まで到達した場合はnullを返す
     * 
     * @return nextTargetLine
     */
    private String nextTargetLine() throws IOException {
        switch (currentLineType) {
            case QUERY:
                // QUERYの次の行はカラム名の行であるため、currentLineTypeを変更する
                currentLineType = SQL_TXT_LINE_TYPE.COLUMNS_NAME;
                // 1行読み込む
                return reader.readLine();
            case COLUMNS_NAME:
                // カラム名の次の行はSELECT結果（値）の行であるため、currentLineTypeを変更する
                currentLineType = SQL_TXT_LINE_TYPE.VALUES;
                // 1行読み込む
                return reader.readLine();
            case VALUES:
                String col_line;
                if ((col_line = reader.readLine()) != "") {
                    // 空行（SELECT結果の全件読み込み済み）でない場合はSELECT結果1件の文字列を返す
                    return col_line;
                }
                return scanQueryLine();
            case OTHER:
                return scanQueryLine();
            // default:
            // throw new RuntimeException("System Error : Unknown Enum Type");
            // break;
        }
        throw new RuntimeException("System Error : Unreachable Code");
    }

    /**
     * 次のQUERY行の文字列を返す<br>
     * QUERY行が存在しなかった場合はnullを返す
     * 
     * @return QUERY行の文字列
     */
    private String scanQueryLine() throws IOException {
        String line;
        // 最終行に到達するまで1行ずつ読み込む
        while ((line = reader.readLine()) != null) {
            // QUERY行の場合は読み込んだ文字列を返す
            if (line.startsWith(">SQL")) {
                currentLineType = SQL_TXT_LINE_TYPE.QUERY;
                return line;
            }
        }
        return null;
    }

    /**
     * ファイルをクローズする
     */
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
