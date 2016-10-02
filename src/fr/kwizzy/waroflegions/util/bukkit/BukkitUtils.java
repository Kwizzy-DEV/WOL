package fr.kwizzy.waroflegions.util.bukkit;

import fr.kwizzy.waroflegions.util.java.ArraysUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static List<ItemStack> getTotalInventory(Player p){
        ItemStack[] combined = ArraysUtils.combine(p.getInventory().getContents(), p.getInventory().getArmorContents());
        return Arrays.asList(combined);
    }

}
