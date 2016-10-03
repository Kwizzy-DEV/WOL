package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.util.IMemory;
import fr.kwizzy.waroflegions.util.bukkit.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Par Alexis le 02/10/2016.
 */

public class PlayerEss extends PlayerData{

    private final static String GOD = "ess.god";
    private final static String AFK = "ess.afk";
    private final static String BACK = "ess.back";

    private Player player;


    PlayerEss(IMemory m, PlayerW w) {
        super(m, w);
        this.player = Bukkit.getPlayer(UUID.fromString(memory().get("uuid")));
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isGod() {
        if("null".equalsIgnoreCase(memory().get(GOD))) {
            setGod(false);
        }
        return Boolean.parseBoolean(memory().get(GOD));
    }

    public void setGod(boolean god) {
        memory().set(GOD, god);
    }

    public boolean isAfk(){
        if("null".equalsIgnoreCase(memory().get(AFK))) {
            setAfk(false);
        }
        return Boolean.parseBoolean(memory().get(AFK));
    }

    public void setAfk(boolean b) {
        memory().set(AFK, b);
    }

    public Location getBack(){
        return BukkitUtils.stringToLocation(memory().get(BACK));
    }

    public void setBack(Location l){
        memory().set(BACK, BukkitUtils.toStringLocation(l));
    }

    @Override
    public void save() {
        /********************
         Already save in methods
        ********************/
    }
}
