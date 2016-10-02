package fr.kwizzy.waroflegions.common.essential;

import fr.kwizzy.waroflegions.util.bukkit.FireworkUtil;
import fr.kwizzy.waroflegions.util.bukkit.command.Command;
import fr.kwizzy.waroflegions.util.bukkit.command.FastCommand;
import fr.kwizzy.waroflegions.util.bukkit.command.IFastCommand;
import fr.kwizzy.waroflegions.util.java.ArraysUtils;
import fr.kwizzy.waroflegions.util.java.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Par Alexis le 02/10/2016.
 */

public class EssCommands implements IFastCommand {

    
    /********************
     MESSAGES
    ********************/

    private static final String gamemodeBeInt = "§cL'argument doit être un entier compris entre 1 et 3.";
    private static final String gamemodeChange = "§7Tu es maintenant en mode '§a%s§7'.";

    private static final String healM = "§7Ta vie est maintenant au §amaximum§7.";
    private static final String feedM = "§7Ta nourriture est maintenant au §amaximum§7.";
    private static final String fireworkM = "§7Et hop ! Un petit §afeu d'artifice§7.";

    private static final String hatM = "§7Alors, la classe ce §achapeau ?§7.";
    private static final String hatError = "§cTu n'as pas d'objet dans ta main...";

    private static final String needToBeOp = "§cVous n'avez pas la permission d'éxécuter cette commande.";


    /********************
     COMMANDS
    ********************/

    public static final FastCommand GAMEMODE = new FastCommand("gm") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            if(hasntPemission(p, "wol.gm"))
                return;
            int level = getInt(c.getArgs()[0], gamemodeBeInt, p);
            if(level == -1)
                return;
            GameMode g = GameMode.getByValue(level);
            p.setGameMode(g);
            p.sendMessage(String.format(gamemodeChange, g.name().toLowerCase()));
        }
    };

    public static final FastCommand HEAL = new FastCommand("heal") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            if(hasntPemission(p, "wol.heal"))
                return;
            p.setHealth(p.getMaxHealth());
            p.sendMessage(healM);
        }
    };

    public static final FastCommand FEED = new FastCommand("feed") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            if(hasntPemission(p, "wol.feed"))
                return;
            p.setFoodLevel(20);
            p.sendMessage(feedM);
        }
    };

    public static final FastCommand FIREWORK = new FastCommand("firework") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            if(hasntPemission(p, "wol.firework"))
                return;
            FireworkUtil.playFirework(p.getLocation(), 20*2);
            p.sendMessage(fireworkM);
        }
    };

    public static final FastCommand HAT = new FastCommand("hat") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            if(hasntPemission(p, "wol.hat"))
                return;
            ItemStack m = p.getItemInHand();
            if(m != null && !m.equals(Material.AIR)) {
                ItemStack helmet = p.getInventory().getHelmet();
                p.getInventory().addItem(helmet);
                p.getInventory().setHelmet(m);
                p.sendMessage(hatM);
            }
        }
    };
    
    /********************
     STATIC USEFUL METHODS
    ********************/

    private static boolean hasntPemission(Player p, String perm){
        if(!p.isOp() || !p.hasPermission(perm)){
            p.sendMessage(needToBeOp);
            return true;
        }
        return false;
    }

    private static int getInt(String s, String error, Player p){
        int level = 0;
        try {
            level = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            p.sendMessage(error);
            return -1;
        }
        return level;
    }

    /********************
     IMPLEMENTED METHOD
    ********************/

    @Override
    public void init() {}
}