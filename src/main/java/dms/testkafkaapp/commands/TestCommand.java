package dms.testkafkaapp.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import dms.testkafkaapp.utils.Logger;
import preponderous.ponder.system.abs.ApplicationCommand;
import preponderous.ponder.system.abs.CommandSender;

/**
 * @resource https://www.tutorialspoint.com/apache_kafka/apache_kafka_quick_guide.htm
 */
public class TestCommand extends ApplicationCommand {
    private static final String TOPIC_NAME = "test";
    private static final int TIMEOUT_MS = 10000;
    
    public TestCommand() {
        super(new ArrayList<>(Arrays.asList("test")), new ArrayList<>(Arrays.asList("tka.test")));
    }

    @Override
    public boolean execute(CommandSender sender) {
        Logger.getInstance().print("Using topic: " + TOPIC_NAME);
        Producer<String, String> producer = createProducer();
        sendMessage(producer, TOPIC_NAME);
        producer.close();
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        return execute(sender);
    }

    private Producer<String, String> createProducer() {
        Logger.getInstance().info("Creating producer...");
        Properties props = createProperties();
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        return producer;
    }

    private void sendMessage(Producer<String, String> producer, String topicName) {
        Logger.getInstance().info("Attempting to send message.");
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicName, "testkey", "testvalue");
        Logger.getInstance().debug("Created ProducerRecord: " + record.toString());
        Future<RecordMetadata> futureRecordMetadata = producer.send(record);
        try {
            futureRecordMetadata.get();
            Logger.getInstance().info("Message sent successfully.");
        } catch(Exception e) {
            Logger.getInstance().error("Something went wrong when attempting to send a message.");
        }
    }

    private Properties createProperties() {
        Logger.getInstance().info("Creating properties...");
        Properties props = new Properties(); // create instance for properties to access producer configs
        props.put("bootstrap.servers", getEnvironmentVariable("KAFKA_HOST") + ":9092"); // assign localhost id
        props.put("acks", "all"); // set acknowledgements for producer requests.
        props.put("retries", 0); // if the request fails, the producer can automatically retry
        props.put("batch.size", 16384); // specify buffer size in config
        props.put("linger.ms", 1); // reduce the no of requests less than 0
        props.put("buffer.memory", 33554432); // the buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("transaction.timeout.ms", TIMEOUT_MS);
        return props;
    }

    private static String getEnvironmentVariable(String variableName) {
        String value = System.getenv(variableName);
        if (value == null || value.equals("")) {
           System.out.println("Something went wrong retrieving the environment variable " + variableName);
        }
        return value;
     }
}
