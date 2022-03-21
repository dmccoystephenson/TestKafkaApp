package dms.testkafkaapp.tests;

import dms.testkafkaapp.TestKafkaApp;
import dms.testkafkaapp.misc.CommandSenderImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daniel McCoy Stephenson
 */
public class TestCommandSendTestValue {

    @Test
    public void initiateProducerTest() { // NOTE: kafka cluster must be running for this to pass
        TestKafkaApp testKafkaApp = new TestKafkaApp();
        CommandSenderImpl commandSender = new CommandSenderImpl();
        boolean success = testKafkaApp.onCommand(commandSender, "sendtestvalue", new String[0]);

        if (!success) {
            Assert.fail();
        }
    }
}
