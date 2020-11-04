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

    public static String cut(String val, String cutStartStr, String cutEndStr) {
        // arguments check
        if (val == null || cutStartStr == null || cutEndStr == null) {
            throw new IllegalArgumentException("cutting target value is null.");
        }

        LogUtil.debug("indexOf start : " + val.indexOf(cutStartStr) + cutStartStr.length());
        LogUtil.debug("indexOf end : " + val.indexOf(cutEndStr));
        LogUtil.debug("cut result : "
                + val.substring(val.indexOf(cutStartStr) + cutStartStr.length(), val.indexOf(cutEndStr)));
        return val.substring(val.indexOf(cutStartStr) + cutStartStr.length(), val.indexOf(cutEndStr));
    }
}
