package jp.daich.diffsellect.poi.sheet;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import jp.daich.diffsellect.poi.cellid.bean.CellId;
import jp.daich.diffsellect.util.LogUtil;

public class OpeSheet {

    public OpeSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    // シート
    private final Sheet sheet;

    // エクセルに書き込んだ位置情報を保持するMap
    private final Map<CellId, String> cellPositionMap = new HashMap<>();

    /**
     * return sheet
     * 
     * @return
     */
    public Sheet getSheet() {
        return this.sheet;
    }

    /**
     * 
     * @param cellPosition
     * @param cellId
     */
    public void putCellPostion(CellId cellId, String cellPosition) {
        LogUtil.debug("[cellPositionMap keySet] " + cellPositionMap.keySet());
        LogUtil.debug("[PUT getCellPostion] cellId [" + cellId.toString() + "], cellPosition [" + cellPosition + "]");
        this.cellPositionMap.put(cellId, cellPosition);
    }

    /**
     * 
     * @param cellId
     * @return
     */
    public String getCellPostion(CellId cellId) {
        LogUtil.debug("[GET getCellPostion] cellId [" + cellId.toString() + "], cellPosition ["
                + this.cellPositionMap.get(cellId) + "]");
        return this.cellPositionMap.get(cellId);
    }

    /**
     * 
     * @param range
     * @param style
     */
    public void drawBorder(String range, BorderStyle style) {
        LogUtil.debug("drawBorder [range : " + range + "]");
        CellRangeAddress region = CellRangeAddress.valueOf(range);
        RegionUtil.setBorderTop(style, region, this.sheet);
        RegionUtil.setBorderBottom(style, region, this.sheet);
        RegionUtil.setBorderLeft(style, region, this.sheet);
        RegionUtil.setBorderRight(style, region, this.sheet);
    }
}
