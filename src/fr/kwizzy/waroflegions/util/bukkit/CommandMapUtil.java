package fr.kwizzy.waroflegions.util.bukkit;

import fr.kwizzy.waroflegions.util.java.Log;
import fr.kwizzy.waroflegions.util.nms.NMSVer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class CommandMapUtil {
    private static CommandMap commandMap;

    static {
        try {
            Class<?> craftServer = Class.forName("org.bukkit.craftbukkit."+ NMSVer.getCurrent() +".CraftServer");
            if(craftServer.isInstance(Bukkit.getServer())){
                Field f = craftServer.getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap)f.get(Bukkit.getServer());
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | ClassNotFoundException e) {
            Log.printException(e);
        }
    }

    public static CommandMap getCommandMap() {
        return commandMap;
    }
}
