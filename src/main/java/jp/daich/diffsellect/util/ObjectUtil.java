package jp.daich.diffsellect.util;

import java.util.Collection;

import jp.daich.diffsellect.common.util.ArrayUtils;

public class ObjectUtil {

    /**
     * Invalited Constructor
     */
    private ObjectUtil() {
    }

    /**
     * オブジェクトの型に応じたtoStringを実施する
     * 
     * @param obj
     * @return 型に応じたtoString
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "null";
        }
        // objがString型の場合
        else if (obj instanceof String) {
            return obj.toString();
        }
        // objがString型の配列の場合
        else if (obj instanceof String[]) {
            return ArrayUtils.toString((String[])obj);
        }
        else if (obj instanceof Collection) {
            CollectionUtils.toString((Collection)obj);
        }
        return obj.toString();
    }
}
