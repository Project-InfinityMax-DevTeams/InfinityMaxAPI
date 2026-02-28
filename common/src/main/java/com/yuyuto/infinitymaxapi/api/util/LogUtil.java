package com.yuyuto.infinitymaxapi.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 繝ｭ繧ｰ蜃ｺ蜉帷ｵｱ荳
 */
public final class LogUtil {

    private static final Logger LOGGER =
            LoggerFactory.getLogger("infinitymaxapi");

    private LogUtil() {}

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void error(String msg) {
        LOGGER.error(msg);
    }
}
