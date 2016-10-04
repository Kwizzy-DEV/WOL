package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.util.IMemory;
import fr.kwizzy.waroflegions.util.ISaveable;

import java.util.ArrayList;
import java.util.List;

/**
 * Par Alexis le 03/10/2016.
 */

public abstract class PlayerData implements ISaveable {

    private static List<ISaveable> saveables = new ArrayList<>();

    IMemory memory;
    WOLPlayer wolPlayer;

    PlayerData(IMemory memory, WOLPlayer player) {
        this.memory = memory;
        this.wolPlayer = player;
        saveables.add(this);
    }

    IMemory memory() {
        return memory;
    }

    public WOLPlayer getWolPlayer() {
        return wolPlayer;
    }

    public static void saveAll(){
        saveables.forEach(ISaveable::save);
    }
}
