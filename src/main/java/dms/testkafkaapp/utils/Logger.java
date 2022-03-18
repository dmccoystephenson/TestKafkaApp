package dms.testkafkaapp.utils;

import dms.testkafkaapp.TestKafkaApp;

/**
 * @author Daniel McCoy Stephenson
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
    public void debug(String message) {
        if (TestKafkaApp.getInstance().isDebugEnabled()) {
            print("[debug] " + message);
        }
    }

    public void info(String message) {
        print("[info] " + message);
    }

    public void error(String message) {
        print("[ERROR] " + message);
    }

    public void print(String message) {
        System.out.println(message);
    }
}