package jp.daich.diffsellect.common.util;

public class ArrayUtils {

    /**
     * Invalidate Constructor
     */
    private ArrayUtils() {
    }

    public static String toString(String[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append(i + 1);
            sb.append(" : ");
            sb.append(array[i].toString());
        }
        return StringUtils.isEmpty(sb.toString()) ? "Empty" : sb.toString();
    }
}
