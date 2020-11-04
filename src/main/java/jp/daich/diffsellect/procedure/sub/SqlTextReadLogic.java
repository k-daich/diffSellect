package jp.daich.diffsellect.procedure.sub;

import jp.daich.diffsellect.common.io.reader.TextReader;
import jp.daich.diffsellect.util.LogUtil;
import jp.daich.diffsellect.util.StringUtils;
import jp.daich.diffsellect.procedure.constants.SqlTxtLineType;
import jp.daich.diffsellect.view.dto.ExcelViewDto;

public class SqlTextReadLogic {

    /**
     * Constructor
     */
    public SqlTextReadLogic(TextReader reader) {
        this.reader = reader;
    }

    // SQLテキストのReaderクラス
    private final TextReader reader;

    /**
     * SELECT1件分を読み込み、内容をDtoに保持して返す
     */
    public ExcelViewDto readOneResult() {
        // 読み込んだSELECT結果を保持するDto
        ExcelViewDto _dto = new ExcelViewDto();
        // 読み込んだ1行の文字列
        String _lineStr;
        // 読み込んだ行のタイプ（初期値：クエリより前に位置するその他行）
        SqlTxtLineType _lineType = SqlTxtLineType.OTHER_QUERY_BEFORE;

        // SELLECT1件分の最終行に到達する or ファイル最終行に到達するまで1行ずつ読み込み
        while ((_lineStr = reader.readLine()) != null) {
            // 行タイプを判定し、設定する
            _lineType = judgeLineType(_lineStr, _lineType);

            // 読み込んだ行がクエリ行だった場合
            if (_lineType == SqlTxtLineType.QUERY) {
                // クエリ内容をDtoへ設定する
                setQueryInfo(_dto, _lineStr);
            }
            // 読み込んだ行がカラム名の行だった場合
            else if (_lineType == SqlTxtLineType.COLUMNS_NAME) {
                // カラム名をDtoへ設定する
                setColumnNamesInfo(_dto, _lineStr);
            }
            // 読み込んだ行がSELECT結果値の行だった場合
            else if (_lineType == SqlTxtLineType.VALUES) {
                // カラム名をDtoへ設定する
                setColumnValuesInfo(_dto, _lineStr);
            }
            // 読み込んだ行がSELECT1件分の最終行だった場合
            else if (_lineType == SqlTxtLineType.FIN_OF_ONE_RESULT) {
                // 繰り返し処理を中断し、後続処理へ
                break;
            }
            // その他行タイプの場合は何もせず、次の行の読み込みへ
        }

        // 読み込みを終えた時点での行タイプを以て、返り値を決定する
        switch (_lineType) {
            // 1件分を正常に読み込み終わっていればDtoを返す
            // 検索結果行が最終行だった場合はVALUESで終わることもある
            case VALUES:
            case FIN_OF_ONE_RESULT:
                return _dto;
            // クエリ行が見つからないまま最終行まで読み込んだ場合はnullを返す
            case OTHER_QUERY_BEFORE:
                return null;
            // 以下行タイプで読み込みを終えた場合、テキスト内容の並び、またはロジックが不正なのでエラーを投げる
            case QUERY:
            case COLUMNS_NAME:
                throw new RuntimeException("Unexcepected Order of lines : Latest line type [" + _lineType + "]");
        }
        // 到達不可な箇所
        throw new RuntimeException("System Error : Unreachable Code");
    }

    /**
     * クエリ行を読み込んだ際のDtoへの設定処理
     * 
     * @param viewDto
     * @param lineStr
     */
    private void setQueryInfo(ExcelViewDto viewDto, String lineStr) {
        // クエリの内容を設定する
        viewDto.setSqlQuery(lineStr);
        // クエリの「FROM」「WHERE」の間の文字列（テーブル名）を切り出し、テーブル名を取得する
        String tableName = StringUtils.cut(lineStr.toUpperCase(), "FROM ", " WHERE");
        // テーブル名をDtoへ設定する
        viewDto.setTableName(tableName);
        // シート名（テーブル名+クエリのハッシュ値）を設定する
        viewDto.setSheetName(tableName + lineStr.hashCode());
    }

    /**
     * カラム名行を読み込んだ際のDtoへの設定処理
     * 
     * @param viewDto
     * @param lineStr
     */
    private void setColumnNamesInfo(ExcelViewDto viewDto, String lineStr) {
        // カラム名を設定する
        viewDto.setColumnNames(lineStr.split("\t"));
    }

    /**
     * SELECT結果値の行を読み込んだ際のDtoへの設定処理
     * 
     * @param viewDto
     * @param lineStr
     */
    private void setColumnValuesInfo(ExcelViewDto viewDto, String lineStr) {
        // カラム名を設定する
        viewDto.addResultSet(lineStr.split("\t"));
    }

    /**
     * 現在行の行タイプを判定して返す
     * 
     * @param line        現在行の文字列
     * @param preLineType 前回行の行タイプ
     */
    public static SqlTxtLineType judgeLineType(String line, SqlTxtLineType preLineType) {
        LogUtil.debug("preLineType [" + preLineType + "] , line String [" + line + "]");
        switch (preLineType) {
            case OTHER_QUERY_BEFORE:
                // 現在行が">SQL"で始待っている場合、クエリの行
                if (line.startsWith(">SQL")) {
                    return SqlTxtLineType.QUERY;
                }
                return SqlTxtLineType.OTHER_QUERY_BEFORE;
            case QUERY:
                // クエリの次の行はカラム名の行
                return SqlTxtLineType.COLUMNS_NAME;
            case COLUMNS_NAME:
                // 現在行が空である場合
                if ("".equals(line)) {
                    // SELECT結果は0件であるためタイプはその他
                    return SqlTxtLineType.FIN_OF_ONE_RESULT;
                }
                // カラム名の次の行はSELECT結果（値）の行
                return SqlTxtLineType.VALUES;
            case VALUES:
                // 現在行が空である場合
                if ("".equals(line)) {
                    // SELECT結果の最終行（空行）であるため、タイプはその他
                    return SqlTxtLineType.FIN_OF_ONE_RESULT;
                }
                // 現在行に値がある場合はSELECT結果（値）の行
                return SqlTxtLineType.VALUES;
            case FIN_OF_ONE_RESULT:
                // 1件の最終行到達後に当メソッドを使用される想定内ため例外をスローする
                throw new RuntimeException("System Error : Unreachable FIN_OF_ONE_RESULT status");
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
