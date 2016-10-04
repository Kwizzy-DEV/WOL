package fr.kwizzy.waroflegions.util.bukkit;

import fr.kwizzy.waroflegions.util.java.ArraysUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Player> getPlayersAtLocation(Location location, int radius)
    {
        List<Player> playerList = Bukkit.getOnlinePlayers().stream().filter(p -> p.getLocation().distance(location) <= radius).collect(Collectors.toList());
        return playerList;
    }

    public static String toStringLocation(Location l) {
        if(l == null){
            return "none";
        }
        return String.format("%s,%s,%s,%s", l.getWorld().getName(), l.getX(), l.getY(), l.getZ());
    }

    public static Location stringToLocation(String s) {

        String w = null;
        Double x = null;
        Double y = null;
        Double z = null;
        try {
            String split[] = s.split(",");
            w = split[0];
            x = Double.parseDouble(split[1]);
            y = Double.parseDouble(split[2]);
            z = Double.parseDouble(split[3]);
        } catch (NumberFormatException e) {
            return null;
        }
        return new Location(Bukkit.getWorld(w), x, y, z);
    }

    public static void playNotification(Player p){
        p.playSound(p.getLocation(), Sound.NOTE_PLING, 5, 5);
    }

}
