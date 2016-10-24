package fr.kwizzy.waroflegions.quest;

import fr.kwizzy.waroflegions.player.PlayerQuest;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.util.bukkit.builder.GUIBuilder;
import fr.kwizzy.waroflegions.util.bukkit.builder.ItemBuilder;
import fr.kwizzy.waroflegions.util.java.MathsUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.text.MessageFormat;
import java.util.Set;

/**
 * Par Alexis le 08/10/2016.
 */

public class QuestVisualizer extends GUIBuilder {


    private static final ItemBuilder BORDER = new ItemBuilder(Material.STAINED_GLASS_PANE).durability((short) 14);


    private static String palMessage = "{0}Quêtes du niveau §6{1} {0}à §6{2}";
    private static String percentage = "§eQuêtes accomplis à §a%s §e%%";

    PlayerQuest pq;

    public QuestVisualizer(Player player) {
        super(player);
        pq = WOLPlayer.get(player).getPlayerQuest();
    }

    public void show(){
        show(4, "Quêtes");
        Set<Integer> borderSlot = MathsUtils.getBorderSlot(4);
        borderSlot.forEach(e -> setItemPosition(BORDER.build(), e));
        setItems();
    }

    private void setItems(){
        int pal = pq.getPal();
        int slotIndex = 0;
        Integer[] slots = {11, 12, 13, 14, 15, 20, 21, 22, 23, 24};
        for (int i = 1; i < pal+1; i++) {
            boolean fullyCompleted = pq.isFullyCompleted(i);
            String name = MessageFormat.format(palMessage, "§e", Integer.toString((i * 10) - 10), Integer.toString(i * 10));
            if(fullyCompleted)
                setItemPosition(getPalCompleted().displayname(name.replace("§e", "§a")).lore(String.format(percentage, pq.getPercentageAchieve(i))).build(), slots[slotIndex]);
            else
                setItemPosition(getPalNotCompleted().displayname(name).lore(String.format(percentage, pq.getPercentageAchieve(i))).build(), slots[slotIndex]);
            slotIndex++;
        }
        for (int index = slotIndex; index < slots.length; index++) {
            pal = index+1;
            String name = MessageFormat.format(palMessage, "§c", Integer.toString((pal*10)-10), Integer.toString(pal*10));
            setItemPosition(getNotAccessible().displayname(name).lore("§7Requiert au minimum le niveau " + ((pal*10)-10)).build(), slots[index]);
        }
    }

    @Override
    public void onClickEvent(InventoryClickEvent e) {

    }

    @Override
    public void onCloseEvent(InventoryCloseEvent e) {

    }

    /********************
     Items methods
    ********************/

    private ItemBuilder getPalCompleted() { return new ItemBuilder(Material.MINECART); }
    private ItemBuilder getPalNotCompleted() { return new  ItemBuilder(Material.STORAGE_MINECART); }
    private ItemBuilder getNotAccessible(){ return new ItemBuilder(Material.POWERED_MINECART); }
}
