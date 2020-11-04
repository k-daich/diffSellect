package jp.daich.diffsellect.procedure.sub;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;

import jp.daich.diffsellect.util.LogUtil;
import jp.daich.diffsellect.view.dto.ExcelViewDto;

public class ExcelBuildLogic {
    
    /**
     * Constructor
     */
    public ExcelBuildLogic(Sheet sheet, Font font) {
        this.sheet = sheet;
        this.font = font;
    }

    // 書き込み対象のシートオブジェクト
    private final Sheet sheet;
    // フォント設定用オブジェクト
    private final Font font;

    /**
     * SELLECT1件分の内容をエクセルへ書き込む
     * 
     * @param dto
     */
    public void buildOneResult(ExcelViewDto dto) {
        LogUtil.debug("TODO : implements to buld Logic");
    }
}
