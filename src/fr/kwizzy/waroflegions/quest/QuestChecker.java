package fr.kwizzy.waroflegions.quest;

import org.bukkit.Material;
import org.bukkit.event.inventory.FurnaceExtractEvent;

/**
 * Par Alexis le 08/10/2016.
 */

public class QuestChecker {

    /********************
     STATIC METHODS
    ********************/

    public static int getAmountFurnaceExtractAmount(Material m, FurnaceExtractEvent e) {
        if (!e.getItemType().equals(m))
            return 0;
        if (e.getItemType().equals(m)) {
            return e.getItemAmount();
        }
        return 0;
    }

}
