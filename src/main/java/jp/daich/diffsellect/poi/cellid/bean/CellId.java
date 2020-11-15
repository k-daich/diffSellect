package jp.daich.diffsellect.poi.cellid.bean;

import jp.daich.diffsellect.util.LogUtil;

public abstract class CellId {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CellId) {
            // 自クラスの名称＋自クラス内での識別用文字列を取得する
            String idStr = toString();
            LogUtil.debug("identifierStr : " + idStr);
            return idStr.equals(((CellId) obj).toString());
        }
        // CellId型じゃない場合は例外スロー
        throw new RuntimeException("Illeagal Arguments" + obj.getClass());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * 同じセルID型の内で識別するための文字列を返す
     * 
     * @return 同セルID型内での識別文字列
     */
    abstract String getUniqueStr();

    public String toString() {
        return this.getClass().getSimpleName() + getUniqueStr();
    }
}
