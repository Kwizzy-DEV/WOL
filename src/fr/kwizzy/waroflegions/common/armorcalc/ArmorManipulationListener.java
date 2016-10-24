package fr.kwizzy.waroflegions.common.armorcalc;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

/**
 * Par Alexis le 24/10/2016.
 */

public class ArmorManipulationListener implements Listener
{

    @EventHandler
    public void click(InventoryClickEvent e)
    {
        setPoint(e.getCurrentItem());
        setPoint(e.getCursor());
    }

    @EventHandler
    public void click(PlayerInteractEvent e)
    {
        setPoint(e.getItem());
    }

    private void setPoint(ItemStack i)
    {
        if(conformItem(i))
            new ArmorCalculator(i).addProtPoint();
    }

    private boolean conformItem(ItemStack i)
    {
        if(i != null)
        {
            ArmorCalculator a = new ArmorCalculator(i);
            if (i.getType() != Material.AIR && a.isArmor())
                return true;
        }
        return false;
    }

}
