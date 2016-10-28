package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.util.storage.Memory;
import fr.kwizzy.waroflegions.util.storage.ISaveable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Par Alexis le 03/10/2016.
 */

public abstract class PlayerData implements ISaveable {

    private static List<ISaveable> saveables = new ArrayList<>();

    Memory memo;
    WOLPlayer wolPlayer;

    PlayerData(Memory memory, WOLPlayer player) {
        this.memo = memory;
        this.wolPlayer = player;
        saveables.add(this);
    }

    Memory memory() {
        return memo;
    }

    public WOLPlayer getWolPlayer() {
        return wolPlayer;
    }

    public static void saveAll(){
        saveables.forEach(ISaveable::save);
    }

    public Player getPlayer()
    {
        return wolPlayer.getPlayer();
    }
}
