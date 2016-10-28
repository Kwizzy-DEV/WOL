package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.util.storage.Memory;
import fr.kwizzy.waroflegions.util.bukkit.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Par Alexis le 02/10/2016.
 */

public class PlayerEss extends PlayerData {

    private final static String GOD = "ess.god";
    private final static String AFK = "ess.afk";
    private final static String BACK = "ess.back";

    PlayerEss(Memory m, WOLPlayer w) {
        super(m, w);
    }

    public boolean isGod() {
        if("null".equalsIgnoreCase((String) memory().get(GOD))) {
            setGod(false);
        }
        return (boolean) memory().get(GOD);
    }

    public void setGod(boolean god) {
        memory().put(GOD, god);
    }

    public boolean isAfk(){
        if("null".equalsIgnoreCase((String) memory().get(AFK))) {
            setAfk(false);
        }
        return (boolean) memory().get(AFK);
    }

    public void setAfk(boolean b) {
        memory().put(AFK, b);
    }

    public Location getBack(){
        return BukkitUtils.stringToLocation((String) memory().get(BACK));
    }

    public void setBack(Location l){
        memory().put(BACK, BukkitUtils.toStringLocation(l));
    }

    @Override
    public void save() {
        /********************
         Already save in methods
        ********************/
    }
}
