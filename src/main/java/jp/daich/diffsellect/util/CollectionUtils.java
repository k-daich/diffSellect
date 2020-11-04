package jp.daich.diffsellect.util;

import java.util.Collection;

public class CollectionUtils {
    
    /**
     * Invalitated Constructor
     */
    private CollectionUtils() {}

    public static String toString(Collection<?> collection){
        StringBuffer sb = new StringBuffer("Collection ->");
        int i = 0;
        for (Object obj : collection) {
            sb.append("  index [" + ++i + "] value [" + ObjectUtil.toString(obj) + "]\n");
        }
        return sb.toString();
    }
}
