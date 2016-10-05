package fr.kwizzy.waroflegions.leveling;

import fr.kwizzy.waroflegions.player.PlayerLevel;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.util.bukkit.builder.GUIBuilder;
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
        show(3, "Expérience");
        setItems();
    }

    private void setItems() {

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
