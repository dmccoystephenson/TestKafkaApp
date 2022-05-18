package dms.testkafkaapp.objects;

import java.util.Properties;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import dms.testkafkaapp.utils.Logger;

/**
 * @author Daniel McCoy Stephenson
 */
public class MyProducer {
    private KafkaProducer<String, String> producer;

    public MyProducer(Properties properties) {
        producer = new KafkaProducer<>(properties);
    }

    public KafkaProducer<String, String> getProducer() {
        return producer;
    }

    public boolean sendMessage(String topicName, String value) {
        Logger.getInstance().print("Attempting to send message with value: " + value);
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicName, "testkey", value);
        Logger.getInstance().debug("Created ProducerRecord: " + record);
        Future<RecordMetadata> futureRecordMetadata = producer.send(record);
        try {
            futureRecordMetadata.get();
            return true;
        } catch(CancellationException e) {
            Logger.getInstance().error("A cancellation exception has occurred: " + e.getMessage());
            return false;
        } catch(ExecutionException e) {
            Logger.getInstance().error("An execution exception has occurred: " + e.getMessage());
            return false;
        } catch(InterruptedException e) {
            Logger.getInstance().error("An interrupted exception has occurred: " + e.getMessage());
            return false;
        }
    }
}
