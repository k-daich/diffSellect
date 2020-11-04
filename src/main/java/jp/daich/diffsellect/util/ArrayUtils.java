package jp.daich.diffsellect.util;

public class ArrayUtils {

    /**
     * Invalidate Constructor
     */
    private ArrayUtils() {
    }

    public static String toString(String[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append("[" + i + 1 + " : " + array[i].toString() + "] ");
        }
        return StringUtils.isEmpty(sb.toString()) ? "Empty" : sb.toString();
    }
}
