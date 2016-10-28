package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.economy.Economy;
import fr.kwizzy.waroflegions.util.bukkit.classmanager.message.Message;
import fr.kwizzy.waroflegions.util.storage.Memory;
import fr.kwizzy.waroflegions.util.bukkit.ActionBar;
import fr.kwizzy.waroflegions.util.bukkit.BukkitUtils;
import fr.kwizzy.waroflegions.util.java.StringUtils;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Par Alexis le 01/10/2016.
 */

// TODO: 01/10/2016 Quota

public class PlayerEconomy extends PlayerData{

    @Message private static String addMoney = "§a+ %s " + Economy.MONEY_NAME_LOWERCASE + "§a " + StringUtils.parenthesisText("%s");
    @Message private static String removeMoney = "§c- %s " + Economy.MONEY_NAME_LOWERCASE + "§c " + StringUtils.parenthesisText("%s");
    @Message private static String notEnoughtMoney = "§cTu n'as pas assez de " + Economy.MONEY_NAME_LOWERCASE + "§c.";

    private Integer money;
    @Ignore
    private Integer quota;

    private Player player;

    PlayerEconomy(Memory m, WOLPlayer w) {
        super(m, w);
        this.money = (Integer) m.get("economy.changes");
        this.player = Bukkit.getPlayer(UUID.fromString((String) m.get("uuid")));
    }

    public void set(int i){
        this.money = i;
    }

    public void add(int i) {
        this.money += i;
        ActionBar.sendActionBar(String.format(addMoney, i, this.money), player);
        BukkitUtils.playNotification(player);
    }

    public void remove(int i){
        if(!hasMoney(i))
            set(0);
        this.money -= i;
        ActionBar.sendActionBar(String.format(removeMoney, i, this.money), player);
        BukkitUtils.playNotification(player);
    }

    public boolean hasMoney(int i){
        return i <= money;
    }

    public boolean transaction(int i, WOLPlayer w){
        PlayerEconomy economyPlayer = w.getEconomyPlayer();
        if(hasMoney(i)){
            remove(i);
            economyPlayer.add(i);

            return true;
        }
        player.sendMessage(StringUtils.messageWithLine(notEnoughtMoney));
        return false;
    }

    public Integer getMoney() {
        return money;
    }

    @Override
    public void save() {
        memory().put("economy.changes", money);
    }
}
