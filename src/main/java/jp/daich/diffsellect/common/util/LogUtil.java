package jp.daich.diffsellect.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {

    /**
     * Invalidate Construct
     */
    private LogUtil() {
    }

    private static final Logger logger = LogManager.getLogger("debug");

    public static void debug(Class clazz, String message) {
        logger.debug("[@" + clazz.getSimpleName() + "] " + message);
    }
}
