package dms.testkafkaapp.objects;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;

public class MyProducer<A, B> {
    KafkaProducer<A, B> producer;

    public MyProducer(Properties properties) {
        producer = new KafkaProducer<A, B>(properties);
    }

    public KafkaProducer<A, B> getProducer() {
        return producer;
    }
}
