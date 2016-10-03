package fr.kwizzy.waroflegions.util.bukkit.safe;

import java.util.HashMap;
import java.util.List;

import fr.kwizzy.waroflegions.util.bukkit.builder.GUIBuilder;
import fr.kwizzy.waroflegions.util.bukkit.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;



/**
 * Par Alexis le 27/06/2016.
 */

public abstract class SafeConfirmation extends GUIBuilder
{

    private static HashMap<Player, SafeConfirmation> confirmationMap = new HashMap<>();
    public Player player;

    public SafeConfirmation(Player p)
    {
        super(p);
        this.player = p;
        confirmationMap.put(p, this);
    }

    public void execute(String nameConfirmation, List<String> loreConfirmation, String nameDeny){
        ItemStack isConf = new ItemBuilder(Material.STAINED_CLAY)
                .durability((short) 13)
                .displayname(nameConfirmation)
                .lore(loreConfirmation)
        .build();

        ItemStack isDeny = new ItemBuilder(Material.STAINED_CLAY)
                .durability((short) 14)
                .displayname(nameDeny)
                .build();

        show(3, "Confirmation");

        setItemPosition(isConf, 11);
        setItemPosition(isDeny, 15);
    }

    public void execute(String nameConfirmation, String nameDeny){
        ItemStack isConf = new ItemBuilder(Material.STAINED_CLAY)
                .durability((short) 13)
                .displayname(nameConfirmation)
                .build();

        ItemStack isDeny = new ItemBuilder(Material.STAINED_CLAY)
                .durability((short) 14)
                .displayname(nameDeny)
                .build();

        show(3, "Confirmation");

        setItemPosition(isConf, 11);
        setItemPosition(isDeny, 15);
    }

    @Override
    public void onClick(InventoryClickEvent e)
    {
        final Player p = (Player) e.getWhoClicked();
        if(confirmationMap.containsKey(p)) {
            if (e.getRawSlot() == 11) {
                onClick(ActionClick.ACCEPT);
                confirmationMap.remove(p);
                e.getWhoClicked().closeInventory();
            }

            if (e.getRawSlot() == 15) {
                onClick(ActionClick.DENY);
                e.getWhoClicked().closeInventory();
                confirmationMap.remove(p);
            }


        }
    }

    protected abstract void onClick(ActionClick event);

    @Override
    public void onClose(InventoryCloseEvent e)
    {
        final Player p = (Player) e.getPlayer();
        if(confirmationMap.containsKey(p)) {
            onClick(ActionClick.CLOSE);
            confirmationMap.remove(p);
        }
    }

    public enum ActionClick{
        ACCEPT,
        DENY,
        CLOSE
    }
}
