package fr.kwizzy.waroflegions.leveling;

import fr.kwizzy.waroflegions.player.PlayerLevel;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.util.bukkit.builder.GUIBuilder;
import fr.kwizzy.waroflegions.util.bukkit.builder.ItemBuilder;
import fr.kwizzy.waroflegions.util.java.MathsUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Par Alexis le 05/10/2016.
 */

public class LevelingVisualizer extends GUIBuilder {

    PlayerLevel pl;

    public LevelingVisualizer(Player player) {
        super(player);
        this.pl = WOLPlayer.get(player).getPlayerLeveling();
    }

    public void show(){
        Bukkit.broadcastMessage("open");
        show(1, "Expérience");
        setItems();
    }

    private void setItems() {
        int amount = getAmountItem();
        for (int i = 0; i < (amount == 0 ? 1 : amount); i++) {
            boolean b = (amount != 0) && (i != amount-1);
            setItemPosition(new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .durability(getColor(i))
                    .displayname("§a" + Double.toString(b ? (i+1)*10 : pl.getPercentageExp()) + " %")
                    .build(), i);
        }

    }

    private short getColor(int i){
        if(i <= 1)
            return 14;
        if(i <= 3)
            return 12;
        if(i <= 5)
            return 1;
        if(i <= 7)
            return 13;
        if(i == 9)
            return 5;
        return 0;
    }

    private int getAmountItem(){
        int i = MathsUtils.amountTimeFor(100, pl.getExp());
        Bukkit.broadcastMessage("nombre de case " + i);
        return i;

    }


    @Override
    public void onClickEvent(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onCloseEvent(InventoryCloseEvent e) {
        //nothing to do
    }


}
