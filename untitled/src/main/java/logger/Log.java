package logger;

import org.apache.log4j.Logger;

public class Log {

    static final Logger LOG = Logger.getLogger(Log.class.getName());

    // Initialize Log4j logs

    /**
     * Method to log Test case name in the logger file.
     *
     */

    public static void startTestCase(String testCaseName) {

        LOG.info("****************************************************************************************");

        LOG.info("****************************************************************************************");

        LOG.info("$$$$$$$$$$$$$$$$$$$$$                 " + testCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");

        LOG.info("****************************************************************************************");

        LOG.info("****************************************************************************************");

    }

    /**
     * Method to print log for the ending of the test case.
     *
     **/

    public static void endTestCase(String scenarioStatus) {

        LOG.info("XXXXXXXXXXXXXXXXXXXXXXX              SCENARIO STATUS            XXXXXXXXXXXXXXXXXXXXXX" + scenarioStatus);

        LOG.info("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");

        LOG.info("X");

        LOG.info("X");

        LOG.info("X");

        LOG.info("X");
        
    }

    /**
     * Method to print info log.
     *
     */

    public static void info(String message) {

        LOG.info(message);

    }

    /**
     * Method to print warning log.
     *
     */

    public static void warn(String message) {

        LOG.warn(message);

    }

    /**
     * Method to print error log.
     *
     */

    public static void error(String message) {

        LOG.error(message);

    }

    /**
     * Method to print fatal log.
     *
     */
    public static void fatal(String message) {

        LOG.fatal(message);

    }

    /**
     * Method to print debug log.
     *
     */
    public static void debug(String message) {

        LOG.debug(message);

    }

}