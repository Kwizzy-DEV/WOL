package fr.kwizzy.waroflegions.player;

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

import java.util.HashMap;
import java.util.UUID;

/**
 * Par Alexis le 02/10/2016.
 */

public class PlayerLevel extends PlayerData{

    private static HashMap<Integer, Double> levelToExp = new HashMap<>();
    private static final double COEFF = 1.044663;


    private static String addExp = "§a+ %s d'exp" + StringUtils.parenthesisText("%s");
    private static String levelUp00 = "§a+ §f§lNIVEAU SUPERIEUR §a+";
    private static String levelUp01 = "§eTu es maintenant niveau §a%s§e !";
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

    public void addLevel(int i){
        level += i;
    }

    public void addExp(double d){
        if(exp + d >= levelToExp.get(level)){
            addLevel(getRest(d));
            levelModification();
            ActionBar.sendActionBar(String.format(addExp, d, level), player);
            return;
        }
        exp += d;
        ActionBar.sendActionBar(String.format(addExp, d, level), player);
    }

    public void setLevel(int level){
        this.level = level;
        levelModification();
    }

    private void roundExp(){
        exp = MathsUtils.roundDouble(exp, 2);
    }

    public Player getPlayer() {
        return player;
    }

    public int getLevel() {
        return level;
    }

    private Integer getRest(double d){
        exp += d;
        int levelPassed = 0;
        while (exp >= getExpFor(levelPassed + level)){
            exp -= getExpFor(levelPassed + level);
            levelPassed++;
            roundExp();
        }
        return levelPassed;
    }

    public double getRemainingExp(){
        return (levelToExp.get(level))-exp;
    }

    public double getExp() {
        return exp;
    }

    public double getPercentageExp(){
        return MathsUtils.roundDouble((exp/(levelToExp.get(level)))*100, 1);
    }

    public double getExpFor(int i){
        return levelToExp.get(i);
    }

    public double getTotalExp(){
        return getExpFor(level);
    }

    private void levelModification(){
        if(player == null)
            return;
        FireworkUtil.playFirework(player.getLocation(), 20*2);
        BukkitUtils.playNotification(player);
        player.sendMessage(StringUtils.LINE);
        player.sendMessage("");
        CenteredMessage.sendCenteredMessage(levelUp00, player);
        CenteredMessage.sendCenteredMessage(String.format(levelUp01, level), player);
        CenteredMessage.sendCenteredMessage(String.format(levelUp02, getRemainingExp(), player));
        player.sendMessage("");
        player.sendMessage(StringUtils.LINE);
    }

    @Override
    public void save() {
        memory().set("leveling.level", level);
        memory().set("leveling.exp", exp);
    }
}
