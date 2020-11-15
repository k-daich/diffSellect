package jp.daich.diffsellect.poi.cellid.bean;

public class FirstColumnValueCellId extends CellId {

    /**
     * 最初のカラム値のセルID
     * 
     * @param howtime_select
     * @param whatnum_record
     * @param whatnum_column
     */
    public FirstColumnValueCellId(int howtime_select, int whatnum_record) {
        this.howtime_select = howtime_select;
        this.whatnum_record = whatnum_record;
    }

    // 何回目のSELECT
    private final int howtime_select;
    // 何件目のレコード
    private final int whatnum_record;

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUniqueStr() {
        // 「何回目のSELECTか」と「何件目のレコードか」を組み合わせて識別する
        return howtime_select + ":" + whatnum_record;
    }

}
