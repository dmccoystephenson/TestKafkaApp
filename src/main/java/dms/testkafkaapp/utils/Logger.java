package dms.testkafkaapp.utils;

import dms.testkafkaapp.TestKafkaApp;

/**
 * @author Daniel Stephenson
 */
public class Logger {
    private static Logger instance;

    private Logger() {

    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * This can be used to send a debug message to the console.
     * @param message The message to log to the console.
     */
    public void log(String message) {
        if (TestKafkaApp.getInstance().isDebugEnabled()) {
            System.out.println("[DEBUG] " + message);
        }
    }
}