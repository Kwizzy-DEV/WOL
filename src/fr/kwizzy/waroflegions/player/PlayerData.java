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
    PlayerW player;

    PlayerData(IMemory memory, PlayerW player) {
        this.memory = memory;
        this.player = player;
        saveables.add(this);
    }

    IMemory memory() {
        return memory;
    }

    public PlayerW getWolPlayer() {
        return player;
    }

    public static void saveAll(){
        saveables.forEach(ISaveable::save);
    }
}
