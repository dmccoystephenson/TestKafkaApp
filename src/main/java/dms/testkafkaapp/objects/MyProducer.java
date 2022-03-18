package dms.testkafkaapp.objects;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;

import dms.testkafkaapp.Constants;
import dms.testkafkaapp.utils.EVGetter;
import dms.testkafkaapp.utils.Logger;

public class MyProducer<A, B> {
    KafkaProducer<A, B> producer = createProducer();

    public MyProducer() {
        createProducer();
    }

    public KafkaProducer<A, B> getProducer() {
        return producer;
    }

    private KafkaProducer<A, B> createProducer() {
        Logger.getInstance().info("Creating producer...");
        Properties props = createProperties();
        producer = new KafkaProducer<A, B>(props);
        return producer;
    }

    private Properties createProperties() {
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
