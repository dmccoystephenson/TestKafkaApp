package dms.testkafkaapp.utils;

/**
 * @author Daniel McCoy Stephenson
 */
public class EVGetter {
    
    public static String getEnvironmentVariable(String variableName) {
        String value = System.getenv(variableName);
        if (value == null || value.equals("")) {
           System.out.println("Something went wrong retrieving the environment variable " + variableName);
        }
        return value;
     }
    
}
