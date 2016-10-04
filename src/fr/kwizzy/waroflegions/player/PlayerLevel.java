package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.economy.Economy;
import fr.kwizzy.waroflegions.util.IMemory;
import fr.kwizzy.waroflegions.util.bukkit.ActionBar;
import fr.kwizzy.waroflegions.util.bukkit.BukkitUtils;
import fr.kwizzy.waroflegions.util.bukkit.FireworkUtil;
import fr.kwizzy.waroflegions.util.bukkit.centered.CenteredMessage;
import fr.kwizzy.waroflegions.util.java.MathsUtils;
import fr.kwizzy.waroflegions.util.java.StringUtils;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Par Alexis le 02/10/2016.
 */

public class PlayerLevel extends PlayerData{

    private static HashMap<Integer, Double> levelToExp = new HashMap<>();
    private static final double COEFF = 1.044663;


    private static String addExp = "§a+ %s d'exp" + StringUtils.parenthesisText("%s");
    private static String removeExp = "§c- %s d'exp " + StringUtils.parenthesisText("%s");
    private static String levelUp00 = "§a+ §l§fNIVEAU SUPERIEUR §a+";
    private static String levelUp01 = "§eTu es maintenant niveau §a§l%s§e !";
    private static String levelUp02 = "§eIl te manque §a%s §ed'expérience.";

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

    private Player player;

    PlayerLevel(IMemory m, WOLPlayer p){
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
            Pair<Integer, Double> rest = getRest(d);
            addLevel(rest.getKey());
            exp += rest.getValue();
            roundExp();
            levelUpFunction();
            ActionBar.sendActionBar(String.format(addExp, d, level));
            return;
        }
        exp += d;
        ActionBar.sendActionBar(String.format(addExp, d, level));
    }

    public Player getPlayer() {
        return player;
    }

    private Pair<Integer, Double> getRest(double d){
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
        return new Pair<>(levelPassed, toAdd);
    }

    private void roundExp(){
        exp = MathsUtils.roundDouble(exp, 2);
    }

    private void levelUpFunction(){
        if(player == null)
            return;
        FireworkUtil.playFirework(player.getLocation(), 20*2);
        BukkitUtils.playNotification(player);
        player.sendMessage(StringUtils.LINE);
        CenteredMessage.sendCenteredMessage(levelUp00);
        CenteredMessage.sendCenteredMessage(String.format(levelUp01, level));
        CenteredMessage.sendCenteredMessage(String.format(levelUp02, levelToExp.get(level)));
        player.sendMessage(StringUtils.LINE);
    }

    @Override
    public void save() {
        memory().set("leveling.level", level);
        memory().set("leveling.exp", exp);
    }
}
