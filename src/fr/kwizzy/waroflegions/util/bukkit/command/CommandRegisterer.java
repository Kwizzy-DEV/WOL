package fr.kwizzy.waroflegions.util.bukkit.command;

import fr.kwizzy.waroflegions.WarOfLegions;
import fr.kwizzy.waroflegions.util.bukkit.CommandMapUtil;
import fr.kwizzy.waroflegions.util.java.ArraysUtils;
import fr.kwizzy.waroflegions.util.java.bistream.BiStream;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.command.Command;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandRegisterer extends Command{

    private CommandListener instance;

    private CommandRegisterer(String name , CommandListener instance) {
        super(name);
        this.instance = instance;
    }

    private CommandRegisterer(String name, String description, String usageMessage, List<String> aliases , CommandListener instance) {
        super(name, description, usageMessage, aliases);
        this.instance = instance;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return Arrays.stream(instance.getClass().getMethods()).filter(m -> m.isAnnotationPresent(CommandHandler.class)).anyMatch(m -> {
            CommandHandler handler = m.getAnnotation(CommandHandler.class);
            if(!handler.sender().isInstance(sender))
                return false;
            if(eq(args , handler.args()))
                try {
                    m.setAccessible(true);
                    m.invoke(instance , new fr.kwizzy.waroflegions.util.bukkit.command.Command<>(args , sender , label , this));
                    return true;
                } catch (Exception e) {
                    WarOfLegions.getInstance().print("Error in method " + m + " : " + e.getMessage());
                    e.printStackTrace();
                }
            return false;
        });
    }


    private static boolean eq(String[] args , String expected) {
        return ("*".equalsIgnoreCase(expected))
                || (args.length == 0 && expected.isEmpty())
                || (args.length >= 1 && args[0].equalsIgnoreCase(expected));
    }



    public static void register(String command , CommandListener listener){
        CommandMapUtil.getCommandMap().register(command , new CommandRegisterer(command,listener));
    }

    public static void register(FastCommand cmd){
        register(cmd.getCommand(), cmd);
    }

    public static void register(FastCommand cmd, String... aliases){
        List<String> ali = Arrays.asList(aliases);
        register(cmd.getCommand(), cmd.getCommand(), cmd.getCommand(), ali, cmd);
    }

    public static void register(String command , String description, String usageMessage, List<String> aliases ,CommandListener listener){
        CommandMapUtil.getCommandMap().register(command , new CommandRegisterer(command , description , usageMessage , aliases , listener));
    }


}