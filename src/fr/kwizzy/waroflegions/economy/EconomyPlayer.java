package fr.kwizzy.waroflegions.economy;

import fr.kwizzy.waroflegions.player.MemoryPlayer;
import fr.kwizzy.waroflegions.player.WPlayer;
import fr.kwizzy.waroflegions.util.Saveable;
import fr.kwizzy.waroflegions.util.bukkit.ActionBar;
import fr.kwizzy.waroflegions.util.java.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.UUID;

/**
 * Par Alexis le 01/10/2016.
 */

// TODO: 01/10/2016 Quota

public class EconomyPlayer implements Saveable{

    private static final String addMoney = "§a+ %s " + Economy.MONEY_NAME_LOWERCASE + "§a. " + StringUtils.parenthesisText("%s");
    private static final String removeMoney = "§c- %s " + Economy.MONEY_NAME_LOWERCASE + "§c. " + StringUtils.parenthesisText("%s");
    private static final String notEnoughtMoney = "§cTu n'as pas assez de " + Economy.MONEY_NAME_LOWERCASE + "§c.";

    MemoryPlayer memory;

    Integer money;
    Player player;

    public EconomyPlayer(MemoryPlayer m) {
        this.memory = m;
        this.money = Integer.parseInt(m.get("economy.changes"));
        this.player = Bukkit.getPlayer(UUID.fromString(m.get("uuid")));
    }

    public void set(int i){
        this.money = i;
    }

    public void add(int i) {
        this.money += i;
        ActionBar.sendActionBar(String.format(addMoney, i, this.money), player);
    }

    public void remove(int i){
        if(!hasMoney(i))
            set(0);
        this.money -= i;
        ActionBar.sendActionBar(String.format(removeMoney, i, this.money), player);
    }

    public boolean hasMoney(int i){
        return i > money;
    }

    public boolean transaction(int i, WPlayer w){
        EconomyPlayer economyPlayer = w.getEconomyPlayer();
        if(hasMoney(i)){
            economyPlayer.add(i);
            remove(i);
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
        memory.set("economy.changes", money);
    }
}
