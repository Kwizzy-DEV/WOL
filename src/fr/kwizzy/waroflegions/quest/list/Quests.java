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

/**
 * Par Alexis le 08/10/2016.
 */

public class Quests implements IQuestList {

    IQuest q00 = new Quest<>(
            BlockBreakEvent.class, 20, 0, 1, 30,
            "Coupe {amount} bûches de bois",
            (BlockBreakEvent e) -> e.getBlock().getType().name().toLowerCase().contains("log")
    );

    IQuest q01 = new Quest<>(
            BlockBreakEvent.class, 30, 1, 1, 40,
            "Miner {amount} pierres",
            (BlockBreakEvent e) -> e.getBlock().getType().name().toLowerCase().contains("stone")
    );

    IQuest q02 = new Quest<>(
            BlockPlaceEvent.class, 1, 2, 2, 40,
            "Placer une table de craft",
            (BlockPlaceEvent e) -> e.getBlock().getType().equals(Material.WORKBENCH)
    );

    IQuest q03 = new Quest<>(
            CraftItemEvent.class, 1, 3, 2, 30,
            "Faire une pioche en pierre",
            (CraftItemEvent e) -> e.getCurrentItem().getType().equals(Material.STONE_PICKAXE)
    );

    IQuest q04 = new Quest<>(
            BlockBreakEvent.class, 5, 4, 2, 70,
            "Miner {amount} minérais de fer",
            (BlockBreakEvent e) -> e.getBlock().getType().equals(Material.IRON_ORE)
    );

    IQuest q05 = new Quest<>(
            FurnaceExtractEvent.class, 5, 5, 3, 30,
            "Cuir {amount} minérais de fer",
            (FurnaceExtractEvent e) -> QuestChecker.getAmountFurnaceExtractAmount(Material.IRON_INGOT, e)
    );

}
