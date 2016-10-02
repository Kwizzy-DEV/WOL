package fr.kwizzy.waroflegions.util.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BukkitUtils {

    @SuppressWarnings("unchecked")
    public static <T> T getEntityAtBlock(Location loc , Class<T> entityClass){
        return (T) Arrays.stream(loc.getChunk().getEntities()).filter((e) -> entityClass.isAssignableFrom(e.getClass()) &&
                e.getLocation().getBlockX() == loc.getBlockX() &&
                e.getLocation().getBlockY() == loc.getBlockY() &&
                e.getLocation().getBlockZ() == loc.getBlockZ()).findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public static  <T> T getEntityAt(Location loc , Class<T> entityClass){
        return (T) Arrays.stream(loc.getChunk().getEntities()).filter((e) -> entityClass.isAssignableFrom(e.getClass()) &&
                e.getLocation().equals(loc)).findFirst().orElse(null);
    }

    public static void playerExecuteCommand(Player p, String command){
        Bukkit.getServer().dispatchCommand(p, command);
    }

}
