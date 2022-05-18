package dms.testkafkaapp.commands;

import java.util.ArrayList;
import java.util.Arrays;

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
public class SendCommand extends ApplicationCommand {
    
    public SendCommand() {
        super(new ArrayList<>(Arrays.asList("send")), new ArrayList<>(Arrays.asList("tka.send")));
    }

    @Override
    public boolean execute(CommandSender sender) {
        sender.sendMessage("Usage: send <topic> <message>");
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return execute(sender);
        }
        String topic = args[0];
        String message= args[1];

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
        sendMessage(producer, topic, message);
        producer.getProducer().close();
        return true;
    }

    private boolean sendMessage(MyProducer producer, String topicName, String message) {
        boolean success = producer.sendMessage(topicName, message);
        if (success) {
            Logger.getInstance().print("Message sent successfully.");
        }
        else {
            Logger.getInstance().error("Something went wrong when attempting to send a message.");
        }
        return success;
    }
}