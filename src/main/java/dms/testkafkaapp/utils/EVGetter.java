package dms.testkafkaapp.utils;

import dms.testkafkaapp.exceptions.KafkaHostNotDefinedException;

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

     public static String getKafkaHost() throws KafkaHostNotDefinedException {
        String kafkaHost = getEnvironmentVariable("KAFKA_HOST");
        if (kafkaHost == null || kafkaHost.equals("")) {
           throw new KafkaHostNotDefinedException();
        }
        return kafkaHost;
     }
    
}
