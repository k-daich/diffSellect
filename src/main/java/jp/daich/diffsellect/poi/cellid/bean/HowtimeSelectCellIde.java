package jp.daich.diffsellect.poi.cellid.bean;

public class HowtimeSelectCellIde extends CellId {

    public HowtimeSelectCellIde() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUniqueStr() {
        // 当セルは1つしかないためユニーク情報不要
        return "";
    }
}

