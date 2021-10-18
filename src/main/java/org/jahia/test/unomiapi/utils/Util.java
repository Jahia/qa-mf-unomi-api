package org.jahia.test.unomiapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
    private static Logger logger = LoggerFactory.getLogger(Util.class);

    public static void waitForMillis(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            logger.error("Thread sleep error", e);
        }
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        if (str.isEmpty()) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (str.length() == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

}
