package jp.daich.diffsellect.poi.cellid.bean;

public class ColumnValueCellId extends CellId {

    /**
     * カラム値のセルID
     * 
     * @param howtime_select
     * @param whatnum_record
     * @param whatnum_column
     */
    public ColumnValueCellId(int howtime_select, int whatnum_record, int whatnum_column) {
        this.howtime_select = howtime_select;
        this.whatnum_record = whatnum_record;
        this.whatnum_column = whatnum_column;
    }

    // 何回目のSELECT
    private final int howtime_select;
    // 何件目のレコード
    private final int whatnum_record;
    // 何番目のカラムか
    private final int whatnum_column;

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUniqueStr() {
        // 「何回目のSELECTか」と「何件目のレコードか」と「何番目のカラムか」を組み合わせて識別する
        return howtime_select + ":" + whatnum_record + ":" + whatnum_column;
    }

}
