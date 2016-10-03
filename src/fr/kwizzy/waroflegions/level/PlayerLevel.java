package fr.kwizzy.waroflegions.level;

import fr.kwizzy.waroflegions.player.PlayerData;
import fr.kwizzy.waroflegions.player.PlayerW;
import fr.kwizzy.waroflegions.util.IMemory;
import fr.kwizzy.waroflegions.util.java.MathsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Par Alexis le 02/10/2016.
 */

public class PlayerLevel extends PlayerData{

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

    private int level = 1;
    private double exp = 0;

    Player player;

    public PlayerLevel(IMemory m, PlayerW p){
        super(m, p);
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
        getMemory().set("leveling.level", level);
        getMemory().set("leveling.exp", exp);
    }
}
