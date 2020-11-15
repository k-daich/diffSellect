package jp.daich.diffsellect.poi.cellid.bean;

public class ExecuteTimeCellId extends CellId {

    /**
     * 
     * @param howtime_select 何回目のSELECTか
     */
    public ExecuteTimeCellId(int howtime_select) {
        this.howtime_select = howtime_select;
    }

    private final int howtime_select;

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUniqueStr() {
        // 何回目のSELECTかで識別する
        return Integer.toString(howtime_select);
    }
}
