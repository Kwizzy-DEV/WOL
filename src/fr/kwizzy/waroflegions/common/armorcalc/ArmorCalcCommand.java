package fr.kwizzy.waroflegions.common.armorcalc;

import fr.kwizzy.waroflegions.util.bukkit.command.Command;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandHandler;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Par Alexis le 24/10/2016.
 */

public class ArmorCalcCommand implements CommandListener
{


    @CommandHandler()
    public void main(Command<Player> c)
    {
        Player sender = c.getSender();
        ItemStack itemInHand = sender.getItemInHand();
        ArmorCalculator armorCalculator = new ArmorCalculator(itemInHand);
    }

    @Override
    public String getCommand()
    {
        return "calcarmor";
    }
}
