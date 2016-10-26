package fr.kwizzy.waroflegions.util.bukkit;

import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

/**
 * Par Alexis le 24/10/2016.
 */

public class ItemStackJson
{

    public static JSONObject toJson(ItemStack item)
    {
        return new JSONObject(item.serialize());
    }

    public static ItemStack fromJson(JSONObject item)
    {
        return ItemStack.deserialize(item.toMap());
    }
}
