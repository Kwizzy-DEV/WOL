package fr.kwizzy.waroflegions.util.command;


import fr.kwizzy.waroflegions.WarOfLegions;
import fr.kwizzy.waroflegions.util.bukkit.CommandMapUtil;
import fr.kwizzy.waroflegions.util.java.bistream.BiStream;
import org.bukkit.command.*;
import org.bukkit.command.Command;

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
            if(!handler.infinite() && handler.args().length != args.length) return false;
            else if(!handler.sender().isInstance(sender))return false;
            boolean exec;
            if(handler.pattern())exec =
                    BiStream.wrap(handler.args() , args)
                            .allMatch((s1 , s2) -> s1 == null || Pattern.matches(s1 , s2)) ||
                            BiStream.wrap(handler.alias() , args).allMatch((s1 , s2) -> s1 == null || Pattern.matches(s1 , s2));
            else exec = BiStream.wrap(handler.args() , args)
                    .allMatch((s1 , s2) -> s1 == null || s1.equals(s2)) ||
                    BiStream.wrap(handler.alias() , args).allMatch((s1 , s2) -> s1 == null || s1.equalsIgnoreCase(s2));
            if(exec) try {
                m.setAccessible(true);
                m.invoke(instance , new fr.kwizzy.waroflegions.util.command.Command<>(args , sender , label , this));
            } catch (Exception e) {
                WarOfLegions.getInstance().print("Error in method " + m + " : " + e.getMessage());
                e.printStackTrace();
            }
            return exec;
        });
    }

    public static void register(String command , CommandListener listener){
        CommandMapUtil.getCommandMap().register(command , new CommandRegisterer(command,listener));
    }

    public static void register(String command , String description, String usageMessage, List<String> aliases ,CommandListener listener){
        CommandMapUtil.getCommandMap().register(command , new CommandRegisterer(command , description , usageMessage , aliases , listener));
    }
}
