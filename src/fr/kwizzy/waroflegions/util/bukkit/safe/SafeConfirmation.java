package fr.kwizzy.waroflegions.util.bukkit.safe;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import fr.kwizzy.waroflegions.util.bukkit.builder.GUIBuilder;
import fr.kwizzy.waroflegions.util.bukkit.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Par Alexis le 27/06/2016.
 */

public class SafeConfirmation
{

    private static HashMap<Player, SafeConfirmation> confirmationMap = new HashMap<>();
    public Player player;

    public SafeConfirmation(Player p)
    {
        this.player = p;
        confirmationMap.put(p, this);
    }

    public void safeConfirmation(String nameConfirmation, String nameDeny, Consumer accept, Consumer refuse, Consumer close)
    {
        Object t = new Object();
        SafeConfirmationGui s = new SafeConfirmationGui(player)
        {
            @Override
            protected void onClick(ActionClick event)
            {
                if(event == ActionClick.ACCEPT)
                    accept.accept(t);
                else if(event == ActionClick.REFUSE)
                    refuse.accept(t);
                else
                    close.accept(t);
            }
        };
        s.execute(nameConfirmation, nameDeny);
    }

    public void safeConfirmation(String nameConfirmation, String nameDeny, Consumer accept)
    {
        safeConfirmation(nameConfirmation, nameDeny, accept, null, null);
    }

    public void safeConfirmation(String nameConfirmation, String nameDeny, Consumer accept, Consumer deny)
    {
        safeConfirmation(nameConfirmation, nameDeny, accept, deny, null);
    }

    abstract class SafeConfirmationGui extends GUIBuilder
    {

        SafeConfirmationGui(Player player)
        {
            super(player);
        }

        @Override
        public void onClickEvent(InventoryClickEvent e)
        {
            final Player p = (Player) e.getWhoClicked();
            if(confirmationMap.containsKey(p)) {
                if (e.getRawSlot() == 11)
                {
                    onClick(ActionClick.ACCEPT);
                    confirmationMap.remove(p);
                    e.getWhoClicked().closeInventory();
                }

                if (e.getRawSlot() == 15)
                {
                    onClick(ActionClick.REFUSE);
                    e.getWhoClicked().closeInventory();
                    confirmationMap.remove(p);
                }
            }
        }

        @Override
        public void onCloseEvent(InventoryCloseEvent e)
        {
            final Player p = (Player) e.getPlayer();
            if(confirmationMap.containsKey(p))
            {
                onClick(ActionClick.CLOSE);
                confirmationMap.remove(p);
            }
        }

        abstract void onClick(ActionClick event);

        void execute(String nameConfirmation, List<String> loreConfirmation, String nameDeny){
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

        void execute(String nameConfirmation, String nameDeny)
        {
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
    }

    enum ActionClick
    {
        ACCEPT,
        REFUSE,
        CLOSE
    }
}
