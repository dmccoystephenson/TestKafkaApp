package dms.testkafkaapp.commands;

import preponderous.ponder.system.abs.ApplicationCommand;
import preponderous.ponder.system.abs.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Daniel Stephenson
 */
public class InfoCommand extends ApplicationCommand {
    public InfoCommand() {
        super(new ArrayList<>(Arrays.asList("info")), new ArrayList<>(Arrays.asList("tka.info")));
    }

    @Override
    public boolean execute(CommandSender abstractCommandSender) {
        abstractCommandSender.sendMessage("=== Test Kafka App Info ===");
        abstractCommandSender.sendMessage("Developer: Daniel Stephenson");
        abstractCommandSender.sendMessage("Developed with: Ponder");
        return true;
    }

    @Override
    public boolean execute(CommandSender abstractCommandSender, String[] strings) {
        return execute(abstractCommandSender);
    }
}
