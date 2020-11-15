package jp.daich.diffsellect.poi.cellid.bean;

public class LastColumnValueCellId extends CellId {

    /**
     * 最後のカラム値のセルID
     * 
     * @param howtime_select
     * @param whatnum_record
     * @param whatnum_column
     */
    public LastColumnValueCellId(int howtime_select, int whatnum_record) {
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
