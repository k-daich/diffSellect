package jp.daich.diffsellect.poi.procedure;

import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import jp.daich.diffsellect.poi.book.style.constants.StyleMapKey;
import jp.daich.diffsellect.poi.cell.CellDriver;
import jp.daich.diffsellect.poi.cellid.bean.FirstColumnValueCellId;
import jp.daich.diffsellect.poi.cellid.bean.LastColumnValueCellId;
import jp.daich.diffsellect.poi.sheet.OpeSheet;
import jp.daich.diffsellect.util.LogUtil;
import jp.daich.diffsellect.util.StringUtils;
import jp.daich.diffsellect.view.dto.ExcelViewDto;

public class ExcelBuildLogic {

    /**
     * Constructor
     */
    public ExcelBuildLogic(OpeSheet opeSheet, Map<String, CellStyle> styleMap) {
        this.opeSheet = opeSheet;
        // セルオブジェクトを取得する（初期位置：A1セル）
        cellDriver = new CellDriver(opeSheet.getSheet(), styleMap, 0, 0);
    }

    // 書き込み対象のシートオブジェクト
    private final OpeSheet opeSheet;
    // // スタイル設定用オブジェクト
    // private final Map<String, CellStyle> styleMap;
    // セル操作オブジェクト
    CellDriver cellDriver;

    /**
     * SELLECT1件分の内容をエクセルへ書き込む
     * 
     * @param dto
     */
    public void buildOneResult(ExcelViewDto dto) {
        // 開始ログ
        LogUtil.startLog(dto.toString());

        // 検索回数カウント値を設定する
        int _selectCount = setSelectCount(this.cellDriver);

        // カラム名を設定する
        setColumnNames(dto, this.cellDriver);

        // 検索結果値を設定する
        setColumnValues(dto, this.cellDriver, _selectCount);

        // 終了ログ
        LogUtil.endLog();
    }

    /**
     * 検索回数カウントを設定する
     * 
     * @param cellDriver
     * @return 検索回数カウント
     */
    private int setSelectCount(CellDriver cellDriver) {
        // 検索回数のカウント
        int selectCount;
        // A1セルが空の場合、1をカウント設定する
        if (StringUtils.isEmpty(cellDriver.getStringCellValue())) {
            selectCount = 1;
        }
        // A1セルにカウント値がある場合、インクリメントする
        else {
            selectCount = Integer.parseInt(cutCount(cellDriver.getStringCellValue())) + 1;
        }
        // カウント値をA1セルに設定する
        cellDriver.setCellValue(getSelectCountComment(selectCount));
        return selectCount;
    }

    /**
     * 
     * @param dto
     * @param cellDriver
     */
    private void setColumnNames(ExcelViewDto dto, CellDriver cellDriver) {
        // カラム名を羅列する初期位置（A2セル）を取得する
        cellDriver.move(0, 2);
        // カラム名記述済みの場合は当メソッドスキップする
        if (StringUtils.isNotEmpty(cellDriver.getStringCellValue()))
            return;

        // カラム名の数だけ繰り返す
        for (String _clmnName : dto.getColumnNames()) {
            // カラム名をセル値に設定する
            cellDriver.setCellValue(_clmnName);
            // セル位置を下に１つ移動する
            cellDriver.nextY();
        }
    }

    /**
     * 
     * @param dto
     * @param cellDriver
     */
    private void setColumnValues(ExcelViewDto dto, CellDriver cellDriver, int selectCount) {
        // カラム名を羅列する初期位置（A2セル）を取得する
        cellDriver.move(1, 1);
        // 初期値(B2セル)から右に空のセルが出るまで移動する
        cellDriver.goEmptyCellForRight();
        // １つ上に移動する
        cellDriver.preY();
        // SQL実行時刻を設定する
        cellDriver.setCellValue(dto.getSqlExecuteTime());
        // １つ下に移動する
        cellDriver.nextY();

        // 検索結果件数の分だけ繰り返す
        for (int i = 0; i < dto.getResultSets().size(); i++) {
            // カラム名を羅列する初期位置（A2セル）を取得する
            cellDriver.move(1, 1);
            // 初期値(B2セル)から右に空のセルが出るまで移動する
            cellDriver.goEmptyCellForRight();
            // スタイルを設定し、検索結果件数をセル値に設定する
            setTopCellStyle(cellDriver, i + 1, dto.getResultSets().size());
            cellDriver.setCellValue(getResultsCountComment(selectCount, dto.getResultSets().size(), i + 1));
            // １つ下に移動する
            cellDriver.nextY();
            // 検索結果値を入れる開始位置をセルMapに登録する
            this.opeSheet.putCellPostion(new FirstColumnValueCellId(selectCount, i + 1),
                    cellDriver.getCellAddressStr());

            // カラムの数の分だけ繰り返す
            for (String komkValue : dto.getResultSets().get(i)) {
                // セルのスタイルを設定する
                setMiddleCellStyle(cellDriver, i + 1, dto.getResultSets().size());
                // セルの値を設定する
                cellDriver.setCellValue(komkValue);
                // 1つ下に移動する
                cellDriver.nextY();
            }
            // １つ上に移動する
            cellDriver.preY();
            // セルのスタイルを設定する
            setBottomCellStyle(cellDriver, i + 1, dto.getResultSets().size());
            // 検索結果値を入れる開始位置をセルMapに登録する
            this.opeSheet.putCellPostion(new LastColumnValueCellId(selectCount, i + 1), cellDriver.getCellAddressStr());

            // 2回目以降のSELECT結果の出力である場合、前回レコードと差異があるかの条件付き書式を設定する
            if (selectCount != 1) {
                LogUtil.debug("条件付き書式 [条件] : "
                        + this.opeSheet.getCellPostion(new FirstColumnValueCellId(selectCount - 1, i + 1)) + "<>"
                        + this.opeSheet.getCellPostion(new FirstColumnValueCellId(selectCount, i + 1)));
                LogUtil.debug(
                        "条件付き書式 [範囲] : " + this.opeSheet.getCellPostion(new FirstColumnValueCellId(selectCount, i + 1))
                                + ":" + this.opeSheet.getCellPostion(new LastColumnValueCellId(selectCount, i + 1)));
                cellDriver.setSheetConditionalFormat(
                        this.opeSheet.getCellPostion(new FirstColumnValueCellId(selectCount - 1, i + 1)) + "<>"
                                + this.opeSheet.getCellPostion(new FirstColumnValueCellId(selectCount, i + 1)),
                        this.opeSheet.getCellPostion(new FirstColumnValueCellId(selectCount, i + 1)) + ":"
                                + this.opeSheet.getCellPostion(new LastColumnValueCellId(selectCount, i + 1)),
                        IndexedColors.YELLOW);
            }
        }
        this.opeSheet.drawBorder(
                this.opeSheet.getCellPostion(new FirstColumnValueCellId(selectCount, 1)) + ":"
                        + this.opeSheet
                                .getCellPostion(new LastColumnValueCellId(selectCount, dto.getResultSets().size())),
                BorderStyle.MEDIUM);
    }

    private void setTopCellStyle(CellDriver cellDriver, int whatNumRecord, int allRecordNum) {
        // 全レコード数が1の場合
        if (allRecordNum == 1) {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_ONE_COLUMN_TOP);
        }
        // 複数レコードの最初だった場合
        else if (whatNumRecord == 1) {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_LEFT_TOP);
        }
        // 複数レコードの最後だった場合
        else if (whatNumRecord == allRecordNum) {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_RIGHT_TOP);
        }
        // 複数レコードの中間だった場合
        else {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_MIDDLE_TOP);
        }
    }

    private void setMiddleCellStyle(CellDriver cellDriver, int whatNumRecord, int allRecordNum) {
        // 全レコード数が1の場合
        if (allRecordNum == 1) {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_ONE_COLUMN_MIDDLE);
        }
        // 複数レコードの最初だった場合
        else if (whatNumRecord == 1) {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_LEFT_MIDDLE);
        }
        // 複数レコードの最後だった場合
        else if (whatNumRecord == allRecordNum) {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_RIGHT_MIDDLE);
        }
        // 複数レコードの中間だった場合
        else {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_MIDDLE_MIDDLE);
        }
    }

    private void setBottomCellStyle(CellDriver cellDriver, int whatNumRecord, int allRecordNum) {
        // 全レコード数が1の場合
        if (allRecordNum == 1) {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_ONE_COLUMN_BOTTOM);
        }
        // 複数レコードの最初だった場合
        else if (whatNumRecord == 1) {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_LEFT_BOTTOM);
        }
        // 複数レコードの最後だった場合
        else if (whatNumRecord == allRecordNum) {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_RIGHT_BOTTOM);
        }
        // 複数レコードの中間だった場合
        else {
            cellDriver.setStyle(StyleMapKey.RANGE_BOX_MIDDLE_BOTTOM);
        }
    }

    /**
     * 
     * @param resultNum
     * @return
     */
    private String getResultsCountComment(int selectCount, int resultsCount, int thNum) {
        if (thNum == 1) {
            return selectCount + "回目のSELECT\n" + resultsCount + "件中" + thNum + "件目のデータ";
        }
        return "\n" + resultsCount + "件中" + thNum + "件目のデータ";
    }

    private String getSelectCountComment(int selectCount) {
        return "全体で同じSELECT分を" + Integer.toString(selectCount) + "回流しました。";
    }

    private String cutCount(String countComment) {
        return StringUtils.cut(countComment, "全体で同じSELECT分を", "回流しました。");
    }

}
