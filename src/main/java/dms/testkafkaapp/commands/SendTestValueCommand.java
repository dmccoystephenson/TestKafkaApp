package dms.testkafkaapp.commands;

import java.util.ArrayList;
import java.util.Arrays;

import dms.testkafkaapp.Constants;
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
        MyProducer producer = MyProducerFactory.getInstance().createProducer();
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
