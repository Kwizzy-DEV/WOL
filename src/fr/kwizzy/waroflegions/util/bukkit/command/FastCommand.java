package fr.kwizzy.waroflegions.util.bukkit.command;

import fr.kwizzy.waroflegions.util.bukkit.CommandMapUtil;
import org.bukkit.entity.Player;

import java.util.Arrays;


/**
 * Par Alexis le 02/10/2016.
 */

public abstract class FastCommand implements  CommandListener{

    String command = "none";

    public FastCommand(String command) {
        this.command = command;
        CommandRegisterer.register(this);
    }

    public FastCommand(String command, String ...aliases) {
        this.command = command;
        CommandRegisterer.register(this, aliases);
        org.bukkit.command.Command cmd = CommandMapUtil.getCommandMap().getCommand(command);
        cmd.setAliases(Arrays.asList(aliases));
    }

    @CommandHandler(args = "*", sender = Player.class, infinite = true)
    public void execution(Command<Player> c){
        command(c);
    }

    public abstract void command(Command<Player> c);

    public String getCommand() {
        return command;
    }
}
