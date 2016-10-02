package fr.kwizzy.waroflegions.util.bukkit.command;

import fr.kwizzy.waroflegions.WarOfLegions;
import fr.kwizzy.waroflegions.util.bukkit.CommandMapUtil;
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
        return Arrays.stream(instance.getClass().getMethods()).filter((m) -> m.isAnnotationPresent(CommandHandler.class)).anyMatch((m) -> {
            CommandHandler handler = m.getAnnotation(CommandHandler.class);
            if(!handler.infinite() && handler.args().length != args.length || !handler.sender().isInstance(sender))
                return false;
            String[] alias = handler.alias().length == handler.args().length ? handler.alias() : null;
            if(eq(args , handler.args() , alias , handler.pattern()))
                try {
                    m.setAccessible(true);
                    m.invoke(instance , new fr.kwizzy.waroflegions.util.bukkit.command.Command<>(args , sender , label , this));
                    return true;
                } catch (Exception e) {
                    WarOfLegions.getInstance().print("Error in method " + m + " : " + e.getMessage());
                }
            return false;
        });
    }


    private static boolean eq(String[] args , String[] expected , String[] alias , Boolean pattern) {
        if("*".equalsIgnoreCase(expected[0]))
            return true;
        if(pattern)
            return BiStream.wrap(expected , args).allMatch(Pattern::matches) ||
                (alias != null && BiStream.wrap(alias, args).allMatch(Pattern::matches));
        else
            return BiStream.wrap(expected , args).allMatch(String::equalsIgnoreCase) ||
                (alias != null && BiStream.wrap(alias, args).allMatch(String::equalsIgnoreCase));
    }



    public static void register(String command , CommandListener listener){
        CommandMapUtil.getCommandMap().register(command , new CommandRegisterer(command,listener));
    }

    public static void register(FastCommand cmd){
        register(cmd.getCommand(), cmd);
    }

    public static void register(String command , String description, String usageMessage, List<String> aliases ,CommandListener listener){
        CommandMapUtil.getCommandMap().register(command , new CommandRegisterer(command , description , usageMessage , aliases , listener));
    }


}