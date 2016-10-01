package fr.kwizzy.waroflegions.util.bukkit;

import org.bukkit.Location;

import java.util.Arrays;

public class BukkitUtil {

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
}
