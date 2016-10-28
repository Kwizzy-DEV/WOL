package fr.kwizzy.waroflegions.quest.list;

import fr.kwizzy.waroflegions.quest.IQuest;
import fr.kwizzy.waroflegions.quest.IQuestList;
import fr.kwizzy.waroflegions.quest.Quest;
import fr.kwizzy.waroflegions.quest.QuestChecker;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Par Alexis le 08/10/2016.
 */

public class Quests implements IQuestList {

    public static IQuest q00 = new Quest<>(
            BlockBreakEvent.class, 20, 0, 1, 30,
            "Couper {amount} bûches de bois",
            (BlockBreakEvent e) -> e.getBlock().getType().name().toLowerCase().contains("log")
    );

    public static IQuest q01 = new Quest<>(
            BlockBreakEvent.class, 30, 1, 1, 40,
            "Miner {amount} pierres",
            (BlockBreakEvent e) -> e.getBlock().getType().name().toLowerCase().contains("stone")
    );

    public static IQuest q02 = new Quest<>(
            BlockPlaceEvent.class, 1, 2, 2, 40,
            "Placer une table de craft",
            (BlockPlaceEvent e) -> e.getBlock().getType().equals(Material.WORKBENCH)
    );

    public static IQuest q03 = new Quest<>(
            CraftItemEvent.class, 1, 3, 2, 30,
            "Faire une pioche en pierre",
            (CraftItemEvent e) -> e.getCurrentItem().getType().equals(Material.STONE_PICKAXE)
    );

    public static IQuest q04 = new Quest<>(
            BlockBreakEvent.class, 5, 4, 2, 70,
            "Miner {amount} minérais de fer",
            (BlockBreakEvent e) -> e.getBlock().getType().equals(Material.IRON_ORE)
    );

    public static IQuest q05 = new Quest<>(
            InventoryClickEvent.class, 5, 5, 3, 30,
            "Cuir {amount} minérais de fer",
            (InventoryClickEvent e) -> QuestChecker.getAmountFurnaceExtractAmount(Material.IRON_INGOT, e)
    );

}
