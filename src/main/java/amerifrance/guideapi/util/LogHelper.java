package amerifrance.guideapi.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import amerifrance.guideapi.ConfigHandler;
import amerifrance.guideapi.ModInformation;

public class LogHelper {

    private static Logger logger = LogManager.getLogger(ModInformation.NAME);

    /**
     * @param info - String to log to the info level
     */

    public static void info(Object info) {
        if (ConfigHandler.enableLogging) logger.info(info);
    }

    /**
     * @param error - String to log to the error level
     */

    public static void error(Object error) {
        if (ConfigHandler.enableLogging) logger.error(error);
    }

    /**
     * @param debug - String to log to the debug level
     */

    public static void debug(Object debug) {
        if (ConfigHandler.enableLogging) logger.debug(debug);
    }
}
