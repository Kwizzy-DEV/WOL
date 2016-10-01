package fr.kwizzy.waroflegions.economy;

import fr.kwizzy.waroflegions.player.MemoryPlayer;
import fr.kwizzy.waroflegions.player.WPlayer;

/**
 * Par Alexis le 01/10/2016.
 */

// TODO: 01/10/2016 Quota

public class EconomyPlayer {

    Integer money;

    public EconomyPlayer(MemoryPlayer m) {
        this.money = Integer.parseInt(m.get("economy.changes"));
    }

    public void set(int i){
        this.money = i;
    }

    public void add(int i) {
        this.money += i;
    }

    public void remove(int i){
        if(!hasMoney(i))
            set(0);
        this.money -= i;
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
        return false;
    }

    public Integer getMoney() {
        return money;
    }
}
