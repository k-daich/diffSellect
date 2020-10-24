package jp.daich.diffsellect.common.util;

public class ArrayUtils {
    
    /**
     * Invalidate Constructor
     */
    private ArrayUtils() {
    }

    public static String toString(Object... array) {
        StringBuffer sb = new StringBuffer();
        for(int i = 1; i < array.length; i++){
            sb.append(i);
            sb.append(" : ");
            sb.append(array[i].toString());
        }
        return StringUtils.isEmpty(sb.toString()) ? "Empty" : sb.toString();
    }
}
