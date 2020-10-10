package jp.daich.diffsellect.common.util;

public class StringUtils {

    /**
     * Invalide Construct
     */
    private StringUtils() {
    }

    public static boolean isEmpty(String val) {
        return (val == null || val.isEmpty());
    }

    public static boolean isNotEmpty(String val) {
        return !isEmpty(val);
    }

}
