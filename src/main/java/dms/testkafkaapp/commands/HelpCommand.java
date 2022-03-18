package dms.testkafkaapp.commands;

import preponderous.ponder.system.abs.ApplicationCommand;
import preponderous.ponder.system.abs.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Daniel Stephenson
 */
public class HelpCommand extends ApplicationCommand {

    public HelpCommand() {
        super(new ArrayList<>(Arrays.asList("help")), new ArrayList<>(Arrays.asList("tka.help")));
    }

    @Override
    public boolean execute(CommandSender sender) {
        sender.sendMessage("=== Test Kafka App Commands ===");
        sender.sendMessage("help - View a list of useful commands.");
        sender.sendMessage("test - Run a test.");
        sender.sendMessage("info - View information about the application.");
        sender.sendMessage("quit - Quit the application.");
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        return execute(sender);
    }
}