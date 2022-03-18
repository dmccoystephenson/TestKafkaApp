package dms.testkafkaapp.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import dms.testkafkaapp.Constants;
import dms.testkafkaapp.factories.MyProducerFactory;
import dms.testkafkaapp.objects.MyProducer;
import dms.testkafkaapp.utils.Logger;
import preponderous.ponder.system.abs.ApplicationCommand;
import preponderous.ponder.system.abs.CommandSender;

/**
 * @resource https://www.tutorialspoint.com/apache_kafka/apache_kafka_quick_guide.htm
 */
public class TestCommand extends ApplicationCommand {
    
    public TestCommand() {
        super(new ArrayList<>(Arrays.asList("test")), new ArrayList<>(Arrays.asList("tka.test")));
    }

    @Override
    public boolean execute(CommandSender sender) {
        Logger.getInstance().print("Using topic: " + Constants.TOPIC_NAME);
        MyProducer<String, String> producer = MyProducerFactory.getInstance().createProducer();
        sendTestMessage(producer, Constants.TOPIC_NAME);
        producer.getProducer().close();
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        return execute(sender);
    }

    private void sendTestMessage(MyProducer<String, String> producer, String topicName) {
        String value = "testvalue";
        boolean success = producer.sendMessage(topicName, value);
        if (success) {
            Logger.getInstance().print("Message sent successfully.");
        }
        else {
            Logger.getInstance().error("Something went wrong when attempting to send a message.");
        }
    }
}
