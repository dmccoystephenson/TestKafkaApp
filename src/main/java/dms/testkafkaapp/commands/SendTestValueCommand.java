package dms.testkafkaapp.commands;

import java.util.ArrayList;
import java.util.Arrays;

import dms.testkafkaapp.Constants;
import dms.testkafkaapp.exceptions.KafkaClusterNotFoundException;
import dms.testkafkaapp.exceptions.KafkaHostNotDefinedException;
import dms.testkafkaapp.factories.MyProducerFactory;
import dms.testkafkaapp.objects.MyProducer;
import dms.testkafkaapp.utils.Logger;
import preponderous.ponder.system.abs.ApplicationCommand;
import preponderous.ponder.system.abs.CommandSender;

/**
 * @author Daniel McCoy Stephenson
 */
public class SendTestValueCommand extends ApplicationCommand {
    
    public SendTestValueCommand() {
        super(new ArrayList<>(Arrays.asList("sendtestvalue")), new ArrayList<>(Arrays.asList("tka.sendtestvalue")));
    }

    @Override
    public boolean execute(CommandSender sender) {
        Logger.getInstance().print("Using topic: " + Constants.TOPIC_NAME);
        MyProducer producer;
        try {
            producer = MyProducerFactory.getInstance().createProducer();
        } catch (KafkaHostNotDefinedException e) {
            Logger.getInstance().error("The KAFKA_HOST environment variable was not set.");
            sender.sendMessage("You need to set the KAFKA_HOST environment variable to the IP of the host running your kafka cluster.");
            return false;
        } catch (KafkaClusterNotFoundException e) {
            Logger.getInstance().error("A kafka cluster was not found and was expected to be.");
            sender.sendMessage("You need to have a kafka cluster running in order for this command to work.");
            return false;
        }
        sendTestMessage(producer, Constants.TOPIC_NAME);
        producer.getProducer().close();
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        return execute(sender);
    }

    private boolean sendTestMessage(MyProducer producer, String topicName) {
        String value = "testvalue";
        boolean success = producer.sendMessage(topicName, value);
        if (success) {
            Logger.getInstance().print("Message sent successfully.");
        }
        else {
            Logger.getInstance().error("Something went wrong when attempting to send a message.");
        }
        return success;
    }
}
