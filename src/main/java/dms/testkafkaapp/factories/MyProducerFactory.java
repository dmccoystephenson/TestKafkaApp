package dms.testkafkaapp.factories;

import java.util.Properties;

import dms.testkafkaapp.Constants;
import dms.testkafkaapp.exceptions.KafkaNotFoundException;
import dms.testkafkaapp.objects.MyProducer;
import dms.testkafkaapp.utils.EVGetter;
import dms.testkafkaapp.utils.Logger;

/**
 * @author Daniel McCoy Stephenson
 */
public class MyProducerFactory {
    private static MyProducerFactory instance;
    
    private MyProducerFactory() {

    }

    public static MyProducerFactory getInstance() {
        if (instance == null) {
            instance = new MyProducerFactory();
        }
        return instance;
    }

    public MyProducer createProducer() throws KafkaNotFoundException {
        Properties properties = getDefaultProperties();
        return createProducer(properties);
    }

    public MyProducer createProducer(Properties properties) throws KafkaNotFoundException {
        Logger.getInstance().info("Creating producer...");
        try {
            return new MyProducer(properties);
        }
        catch(Exception e) {
            throw new KafkaNotFoundException();
        }
    }

    private Properties getDefaultProperties() {
        Logger.getInstance().info("Creating properties...");
        Properties props = new Properties(); // create instance for properties to access producer configs
        props.put("bootstrap.servers", EVGetter.getEnvironmentVariable("KAFKA_HOST") + ":9092"); // assign localhost id
        props.put("acks", "all"); // set acknowledgements for producer requests.
        props.put("retries", 0); // if the request fails, the producer can automatically retry
        props.put("batch.size", 16384); // specify buffer size in config
        props.put("linger.ms", 1); // reduce the no of requests less than 0
        props.put("buffer.memory", 33554432); // the buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("transaction.timeout.ms", Constants.TIMEOUT_MS);
        return props;
    }
}