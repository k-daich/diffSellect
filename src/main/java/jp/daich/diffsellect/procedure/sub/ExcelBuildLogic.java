package jp.daich.diffsellect.procedure.sub;

import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;

import jp.daich.diffsellect.poi.cell.CellDriver;
import jp.daich.diffsellect.util.LogUtil;
import jp.daich.diffsellect.util.StringUtils;
import jp.daich.diffsellect.view.dto.ExcelViewDto;

public class ExcelBuildLogic {

    /**
     * Constructor
     */
    public ExcelBuildLogic(Sheet sheet, Map<String, CellStyle> styleMap) {
        this.sheet = sheet;
        this.styleMap = styleMap;
    }

    // 書き込み対象のシートオブジェクト
    private final Sheet sheet;
    // スタイル設定用オブジェクト
    private final Map<String, CellStyle> styleMap;

    /**
     * SELLECT1件分の内容をエクセルへ書き込む
     * 
     * @param dto
     */
    public void buildOneResult(ExcelViewDto dto) {
        // 開始ログ
        LogUtil.startLog(dto.toString());

        // セルオブジェクトを取得する（初期位置：A1セル）
        CellDriver _cellDriver = new CellDriver(this.sheet, this.styleMap, 0, 0);

        // 検索回数カウント値を設定する
        int _selectCount = setSelectCount(_cellDriver);

        // カラム名を設定する
        setColumnNames(dto, _cellDriver);

        // 検索結果値を設定する
        setColumnValues(dto, _cellDriver, _selectCount);

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
        cellDriver.move(1, 0);
        // 初期値(B2セル)から右に空のセルが出るまで移動する
        cellDriver.goEmptyCellForRight();
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
            // 検索結果件数を設定する
            cellDriver.setCellValue(getResultsCountComment(selectCount, dto.getResultSets().size(), i + 1));
            // １つ下に移動する
            cellDriver.nextY();

            // 検索結果値を入れる開始位置を取得する
            String startCellVal = cellDriver.getCellAddressStr();
            // カラムの数の分だけ繰り返す
            for (String komkValue : dto.getResultSets().get(i)) {
                cellDriver.setCellValue(komkValue);
                cellDriver.nextY();
            }
            // 検索結果値を入れる開始位置を取得する
            String endCellVal = cellDriver.getCellAddressStr();
        }

        // if() {
        // cellDriver.setSheetConditionalFormat(startCellVal + "=" +
        // cellDriver.getStringNextCellValue(-1, 0),
        // startCellVal + ":" + endCellVal, IndexedColors.YELLOW);
        // }
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
