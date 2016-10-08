package fr.kwizzy.waroflegions.quest.list;

import fr.kwizzy.waroflegions.quest.IQuest;
import fr.kwizzy.waroflegions.quest.IQuestList;
import fr.kwizzy.waroflegions.quest.Quest;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Par Alexis le 08/10/2016.
 */

public class Quests implements IQuestList {

    IQuest q01 = new Quest<>(
            BlockBreakEvent.class, 20, 0, 1, 50,
            "Coupe {amount} bûches de bois",
            (BlockBreakEvent e) -> e.getBlock().getType().name().toLowerCase().contains("log")
    );

}
