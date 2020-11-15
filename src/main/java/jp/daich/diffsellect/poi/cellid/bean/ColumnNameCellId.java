package jp.daich.diffsellect.poi.cellid.bean;

public class ColumnNameCellId extends CellId {

    /**
     * カラム名のセルID
     * 
     * @param whatnum_column
     */
    public ColumnNameCellId(String whatnum_column) {
        this.whatnum_column = whatnum_column;
    }

    // 何番目のカラムか
    private final String whatnum_column;

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUniqueStr() {
        // 何番目のカラムかで識別する
        return whatnum_column;
    }

}
