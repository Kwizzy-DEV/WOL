package fr.kwizzy.waroflegions.quest.gui;

import fr.kwizzy.waroflegions.player.PlayerQuest;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.util.bukkit.builder.GUIBuilder;
import fr.kwizzy.waroflegions.util.bukkit.builder.ItemBuilder;
import fr.kwizzy.waroflegions.util.bukkit.classmanager.message.Message;
import fr.kwizzy.waroflegions.util.java.MathsUtils;
import fr.kwizzy.waroflegions.util.java.bistream.BiStream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * Par Alexis le 08/10/2016.
 */

public class QuestVisualizer extends GUIBuilder
{


    private static final ItemBuilder BORDER = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 14);


    @Message private static String palMessage = "{0}Quêtes du niveau §6{1} {0}à §6{2}";
    @Message private static String percentage = "§eQuêtes accomplis à §a%s §e%%";
    @Message private static String requierLevel = "§7Requiert au minimum le niveau §c%s";

    PlayerQuest pq;

    public QuestVisualizer(Player player) {
        super(player);
        pq = WOLPlayer.get(player).getPlayerQuest();
    }

    public void show(){
        show(4, "Quêtes");
        Set<Integer> borderSlot = MathsUtils.getBorderSlot(4);
        borderSlot.forEach(e -> setItemPosition(BORDER.toItemStack(), e));
        setItems();
    }

    private void setItems(){
        int pal = pq.getPal();
        int slotIndex = 0;
        Integer[] slots = {11, 12, 13, 14, 15, 20, 21, 22, 23, 24};
        for (int i = 1; i < pal+1; i++) {
            boolean fullyCompleted = pq.isFullyCompleted(i);
            setItemPosition(getCustomItem(fullyCompleted, i), slots[slotIndex]);
            slotIndex++;
        }
        for (int index = slotIndex; index < slots.length; index++) {
            pal = index+1;
            String name = MessageFormat.format(palMessage, "§c", Integer.toString((pal*10)-10), Integer.toString(pal*10));
            setItemPosition(getNotAccessible().setName(name).addLoreLine(String.format(requierLevel, (pal*10)-10)).toItemStack(), slots[index]);
        }
    }

    @Override
    public void onClickEvent(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();
        if(currentItem == null || currentItem.getType().equals(Material.AIR) || currentItem.getType().equals(BORDER.toItemStack().getType()))
            return;
        end();
        QuestViewer q = new QuestViewer(getPlayer(), pq, e.getCurrentItem());
        q.show();
    }

    @Override
    public void onCloseEvent(InventoryCloseEvent e) {
        /* Nothing to do here */
    }

    /********************
     Items methods
    ********************/

    private ItemBuilder getPalCompleted() { return new ItemBuilder(Material.MINECART); }
    private ItemBuilder getPalNotCompleted() { return new  ItemBuilder(Material.STORAGE_MINECART); }
    private ItemBuilder getNotAccessible(){ return new ItemBuilder(Material.POWERED_MINECART); }

    private ItemStack getCustomItem(boolean fullyCompleted, int i)
    {

        ItemStack is = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta m = is.getItemMeta();
        String name = MessageFormat.format(palMessage, "§e", Integer.toString((i * 10) - 10), Integer.toString(i * 10));
        if(fullyCompleted)
        {
            is.setType(getPalCompleted().toItemStack().getType());
            m.setDisplayName(name.replace("§e", "§a"));
            m.setLore(Arrays.asList(String.format(percentage, pq.getPercentageAchieve(i))));
        }
        else
        {
            is.setType(getPalNotCompleted().toItemStack().getType());
            m.setDisplayName(name);
            m.setLore(Arrays.asList(String.format(percentage, pq.getPercentageAchieve(i))));
        }
        is.setItemMeta(m);
        return is;
    }


}
