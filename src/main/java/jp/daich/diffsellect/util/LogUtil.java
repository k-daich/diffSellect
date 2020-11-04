package jp.daich.diffsellect.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jp.daich.diffsellect.util.ArrayUtils;

public class LogUtil {

    /**
     * Invalidate Construct
     */
    private LogUtil() {
    }

    private static final Logger logger = LogManager.getLogger("LogUtil");

    private static String indent = "";

    private static String incrementIndent() {
        return indent + "  ";
    }

    private static String decrementIndent() {
        return indent.substring(indent.length() -2);
    }

    public static void startLog(String... arguments) {
        StackTraceElement stackTrcEle = Thread.currentThread().getStackTrace()[2];
        logger.debug(indent + "[" + stackTrcEle.getFileName() + "@" + stackTrcEle.getMethodName() + "] >>>> start >>>>");
        logger.debug(indent + " Args > " + ArrayUtils.toString(arguments));
        // インデントを足す
        indent = incrementIndent();
    }

    public static void endLog() {
        StackTraceElement stackTrcEle = Thread.currentThread().getStackTrace()[2];
        // インデントを減らす
        indent = decrementIndent();
        logger.debug(indent + "[" + stackTrcEle.getFileName() + "@" + stackTrcEle.getMethodName() + "] <<<< end <<<<");
    }

    public static void debug(String message) {
        StackTraceElement stackTrcEle = Thread.currentThread().getStackTrace()[2];
        logger.debug(indent + "[" + stackTrcEle.getFileName() + "@" + stackTrcEle.getLineNumber() + "] " + message);
    }
}
