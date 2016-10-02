package fr.kwizzy.waroflegions.level;

import fr.kwizzy.waroflegions.player.MemoryPlayer;
import fr.kwizzy.waroflegions.util.Saveable;
import fr.kwizzy.waroflegions.util.java.MathsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Par Alexis le 02/10/2016.
 */

public class LevelPlayer implements Saveable{

    private static HashMap<Integer, Double> levelToExp = new HashMap<>();
    private static final double COEFF = 1.044663;

    static {
        double exp = 50;
        levelToExp.put(1, 50.0);
        for (int i = 2; i < 5000; i++) {
            exp *= COEFF;
            levelToExp.put(i, MathsUtils.roundDouble(exp, 1));
        }
    }

    MemoryPlayer memory;

    int level = 1;
    double exp = 0;

    Player player;

    public LevelPlayer(MemoryPlayer m){
        this.memory = m;
        this.level = Integer.parseInt(m.get("leveling.level"));
        this.exp = Integer.parseInt(m.get("leveling.exp"));
        this.player = Bukkit.getPlayer(UUID.fromString(m.get("uuid")));
    }

    public int getLevel() {
        return level;
    }

    public double getExp() {
        return exp;
    }

    public Player getPlayer() {
        return player;
    }

    public void addLevel(int i){
        level += i;
    }

    public void addExp(double d){
        if(exp + d >= levelToExp.get(level)){
            addLevel(getLevelPassed(d));
            // TODO: 02/10/2016 level up 
        }
    }

    private int getLevelPassed(double d){
        double xp = this.exp;
        double reach = levelToExp.get(level);
        double toAdd = d;
        int levelPassed = 0;
        while (xp + toAdd > reach){
            xp = 0;
            toAdd = xp - reach;
            levelPassed++;
            reach = levelToExp.get(level + levelPassed);
        }
        return levelPassed;
    }

    @Override
    public void save() {
        memory.set("leveling.level", level);
        memory.set("leveling.exp", exp);
    }
}
