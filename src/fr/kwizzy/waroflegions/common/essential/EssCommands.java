package fr.kwizzy.waroflegions.common.essential;


import fr.kwizzy.waroflegions.player.WPlayer;
import fr.kwizzy.waroflegions.util.bukkit.FireworkUtil;
import fr.kwizzy.waroflegions.util.bukkit.command.Command;
import fr.kwizzy.waroflegions.util.bukkit.command.FastCommand;
import fr.kwizzy.waroflegions.util.bukkit.command.IFastCommand;
import fr.kwizzy.waroflegions.util.java.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.Set;


/**
 * Par Alexis le 02/10/2016.
 */

public class EssCommands implements IFastCommand {


    /********************
     MESSAGES
    ********************/


    private static final String gamemodeChange = "§7Tu es maintenant en mode '§a%s§7'.";
    private static final String healM = "§7Ta vie est maintenant au §amaximum§7.";
    private static final String feedM = "§7Ta nourriture est maintenant au §amaximum§7.";
    private static final String fireworkM = "§7Et hop ! Un petit §afeu d'artifice§7.";
    private static final String hatM = "§7Alors, la classe ce §achapeau ?§7.";
    private static final String repairM = "§7Ton objet a été réparé.";
    private static final String repairAllM = "§7Tous tes objets ont été réparés.";
    private static final String clearM = "§7Ton inventaire a été supprimé.";
    private static final String flyM = "§7Vol: %s";
    private static final String godM = "§7Dieu: %s";
    private static final String godForM = "§7Dieu pour %s: %s";
    private static final String speedM = "§7Vitesse de déplacement défini à: §a%s§7.";
    private static final String breakM = "§7Vous avez cassé le bloc que vous regardez.";

    private static final String notObject = "§cTu n'as pas d'objet dans ta main...";
    private static final String needToBeOp = "§cVous n'avez pas la permission d'éxécuter cette commande.";
    private static final String gamemodeBeInt = "§cL'argument doit être un entier compris entre 1 et 3.";
    private static final String speedBeInt = "§cL'argument doit être un chifre compris entre 0 et 10.";
    private static final String badPlayer = "§cLe joueur §e%s§c est introuvable.";


    /********************
     COMMANDS
    ********************/

    public static final FastCommand GAMEMODE = new FastCommand("gm") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            if(c.getArgs().length == 0){
                p.sendMessage("§7/gm §a<nombre>");
                return;
            }
            if(hasntPemission(p, "wol.gm"))
                return;
            int level = getInt(c.getArgs()[0], gamemodeBeInt, p);
            if(level == -1)
                return;
            if(level > 3 || level < 0){
                p.sendMessage(gamemodeBeInt);
                return;
            }
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
            if(m != null && !m.getType().equals(Material.AIR)) {
                ItemStack helmet = p.getInventory().getHelmet();
                if(helmet != null)
                    p.getInventory().addItem(helmet);
                p.getInventory().setHelmet(m);
                p.getInventory().remove(m);
                p.sendMessage(hatM);
                return;
            }
            p.sendMessage(notObject);
        }
    };

//    public static final FastCommand REPAIR = new FastCommand("repair") {
//        @Override
//        public void command(Command<Player> c) {
//            Player p = c.getSender();
//            if(hasntPemission(p, "wol.repair"))
//                return;
//            ItemStack m = p.getItemInHand();
//            if(m != null && !m.getType().equals(Material.AIR)) {
//                m.setDurability((short) 0);
//                p.sendMessage(repairM);
//                return;
//            }
//            p.sendMessage(notObject);
//        }
//    };
//
//    public static final FastCommand REPAIRALL = new FastCommand("repairall") {
//        @Override
//        public void command(Command<Player> c) {
//            Player p = c.getSender();
//            if(hasntPemission(p, "wol.repairall"))
//                return;
//            List<ItemStack> m = BukkitUtils.getTotalInventory(p);
//            m.stream().filter(stack -> stack != null && !stack.getType().equals(Material.AIR)).forEach(stack -> {
//                stack.setDurability((short) 0);
//            });
//            p.sendMessage(repairAllM);
//        }
//    };

    public static final FastCommand CLEAR = new FastCommand("clear") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            if(hasntPemission(p, "wol.clear"))
                return;
            p.getInventory().clear();
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);
            p.sendMessage(clearM);
        }
    };

    public static final FastCommand FLY = new FastCommand("fly") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            if(hasntPemission(p, "wol.fly"))
                return;
            if(p.getAllowFlight()){
                p.setAllowFlight(false);
                p.setFlying(false);
                p.sendMessage(String.format(flyM, "§cOFF"));
            }
            else {
                p.setAllowFlight(true);
                p.setFlying(true);
                p.sendMessage(String.format(flyM, "§aON"));
            }
        }
    };

    public static final FastCommand SPEED = new FastCommand("speed", "sp", "fast") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            if(c.getArgs().length == 0){
                p.sendMessage("§7/speed §a<nombre>");
                return;
            }
            if(hasntPemission(p, "wol.speed"))
                return;
            double speed = getDouble(c.getArgs()[0], speedBeInt, p);
            if(speed > 10 || speed < 0){
                p.sendMessage(speedBeInt);
                return;
            }
            setSpeed((float) speed/10, p);
            p.sendMessage(String.format(speedM, speed));
        }
    };

    private static void setSpeed(float speed, Player p){
        if(p.isFlying())
            p.setFlySpeed(speed);
        else
            p.setWalkSpeed(speed);
    }

    public static final FastCommand GOD = new FastCommand("god") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            EssPlayer essPlayer = WPlayer.load(p).getEssPlayer();
            if(hasntPemission(p, "wol.god"))
                return;
            if(c.getArgs().length > 0){
                Player target = Bukkit.getPlayer(c.getArgs()[0]);
                if(target == null) {
                    p.sendMessage(String.format(badPlayer, c.getArgs()[0]));
                    return;
                }
                boolean b = setGod(WPlayer.load(target).getEssPlayer());
                p.sendMessage(String.format(godForM, target.getName(), b ? "§aON" : "§cOFF"));
                return;
            }
            setGod(essPlayer);
        }
    };

    private static boolean setGod(EssPlayer e){
        if(e.isGod()){
            e.setGod(false);
            e.getPlayer().sendMessage(String.format(godM, "§cOFF"));
            return false;
        }
        e.setGod(true);
        e.getPlayer().sendMessage(String.format(godM, "§aON"));
        return true;

    }

    public static final FastCommand BREAK = new FastCommand("break") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            if(hasntPemission(p, "wol.break"))
                return;
            Block targetBlock = p.getTargetBlock((Set<Material>) null, 10);
            targetBlock.breakNaturally();
            p.sendMessage(breakM);
        }
    };

    public static final FastCommand BROADCAST = new FastCommand("broadcast", "br", "broad") {
        @Override
        public void command(Command<Player> c) {
            Player p = c.getSender();
            if(hasntPemission(p, "wol.broad"))
                return;

            String[] args = c.getArgs();
            StringBuilder ar = new StringBuilder();
            for (String arg : args) {
                ar.append(arg.replace("&", "§")).append(" ");
            }
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(StringUtils.parenthesisText(p.getName()) + " §6Annonce: §e" + ar.toString());
            Bukkit.broadcastMessage("");
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

    private static double getDouble(String s, String error, Player p){
        double level = 0;
        try {
            level = Double.parseDouble(s);
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
