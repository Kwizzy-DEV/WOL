package fr.kwizzy.waroflegions.common.essential;


import fr.kwizzy.waroflegions.player.PlayerEss;
import fr.kwizzy.waroflegions.player.PlayerW;
import fr.kwizzy.waroflegions.util.bukkit.FireworkUtil;
import fr.kwizzy.waroflegions.util.bukkit.command.Command;
import fr.kwizzy.waroflegions.util.bukkit.command.FastCommand;
import fr.kwizzy.waroflegions.util.bukkit.command.IFastCommand;
import fr.kwizzy.waroflegions.util.java.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * Par Alexis le 02/10/2016.
 */

public class EssCommands implements IFastCommand {


    /********************
     MESSAGES
    ********************/


    private static String gamemodeChange = "§7Tu es maintenant en mode '§a%s§7'.";
    private static String healM = "§7Ta vie est maintenant au §amaximum§7.";
    private static String feedM = "§7Ta nourriture est maintenant au §amaximum§7.";
    private static String fireworkM = "§7Et hop ! Un petit §afeu d'artifice§7.";
    private static String hatM = "§7Alors, la classe ce §achapeau ?§7.";
    private static String repairM = "§7Ton objet a été réparé.";
    private static String repairAllM = "§7Tous tes objets ont été réparés.";
    private static String clearM = "§7Ton inventaire a été supprimé.";
    private static String flyM = "§7Vol: %s";
    private static String godM = "§7Dieu: %s";
    private static String godForM = "§7Dieu pour %s: %s";
    private static String speedM = "§7Vitesse de déplacement défini à: §a%s§7.";
    private static String breakM = "§7Vous avez cassé le bloc que vous regardez.";
    private static String moreM = "§7Vous avez le nombre maximum pour l'objet dans votre main.";
    private static String skullM = "§7Tu as maintenant la tête de '§a%s§7'.";
    private static String pingForM = "§7Le ping de §a%s§7 est de: §a%s§7.";
    private static String pingM = "§7Ton ping est de: §a%s§7.";

    private static String afkM = "§7Le joueur §a%s§7 est maintenant afk.";
    private static String notAfkM = "§7Le joueur §a%s§7 n'est plus afk.";
    private static String backM = "§7Te voilà à ton anciennne location.";

    private static String notObject = "§cTu n'as pas d'objet dans ta main...";
    private static String needToBeOp = "§cVous n'avez pas la permission d'éxécuter cette commande.";
    private static String gamemodeBeInt = "§cL'argument doit être un entier compris entre 1 et 3.";
    private static String speedBeInt = "§cL'argument doit être un chifre compris entre 0 et 10.";
    private static String badPlayer = "§cLe joueur §e%s§c est introuvable.";
    private static String notBack = "§cTu n'as pas pas d'ancienne location.";


    /********************
     COMMANDS
    ********************/

    public static final FastCommand GM = new FastCommand("gm") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
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
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            if(hasntPemission(p, "wol.heal"))
                return;
            p.setHealth(p.getMaxHealth());
            p.sendMessage(healM);
        }
    };

    public static final FastCommand FEED = new FastCommand("feed") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            if(hasntPemission(p, "wol.feed"))
                return;
            p.setFoodLevel(20);
            p.sendMessage(feedM);
        }
    };

    public static final FastCommand FIREWORK = new FastCommand("firework") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            if(hasntPemission(p, "wol.firework"))
                return;
            FireworkUtil.playFirework(p.getLocation(), 20*2);
            p.sendMessage(fireworkM);
        }
    };

    public static final FastCommand HAT = new FastCommand("hat") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
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

    public static final FastCommand MORE = new FastCommand("more") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            if(hasntPemission(p, "wol.more"))
                return;
            ItemStack m = p.getItemInHand();
            if(m != null && !m.getType().equals(Material.AIR)) {
                m.setAmount(m.getMaxStackSize());
                p.sendMessage(moreM);
                return;
            }
            p.sendMessage(notObject);

        }
    };

    public static final FastCommand CLEAR = new FastCommand("clear") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
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
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
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
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
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

        private void setSpeed(float speed, org.bukkit.entity.Player p){
            if(p.isFlying())
                p.setFlySpeed(speed);
            else
                p.setWalkSpeed(speed);
        }
    };

    public static final FastCommand GOD = new FastCommand("god") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            PlayerEss essPlayer = PlayerW.get(p).getEssPlayer();
            if(hasntPemission(p, "wol.god"))
                return;
            if(c.getArgs().length > 0){
                org.bukkit.entity.Player target = Bukkit.getPlayer(c.getArgs()[0]);
                if(target == null) {
                    p.sendMessage(String.format(badPlayer, c.getArgs()[0]));
                    return;
                }
                boolean b = setGod(PlayerW.get(target).getEssPlayer());
                p.sendMessage(String.format(godForM, target.getName(), b ? "§aON" : "§cOFF"));
                return;
            }
            setGod(essPlayer);
        }

        private boolean setGod(PlayerEss e){
            if(e.isGod()){
                e.setGod(false);
                e.getPlayer().sendMessage(String.format(godM, "§cOFF"));
                return false;
            }
            e.setGod(true);
            e.getPlayer().sendMessage(String.format(godM, "§aON"));
            return true;

        }
    };

    public static final FastCommand SKULL = new FastCommand("skull") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            PlayerEss essPlayer = PlayerW.get(p).getEssPlayer();
            if(c.getArgs().length == 0){
                p.sendMessage("§7/skull §a<joueur>");
                return;
            }
            if(hasntPemission(p, "wol.skull"))
                return;
            if(c.getArgs().length > 0){
                String name = c.getArgs()[0];
                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setOwner(name);
                skullMeta.setDisplayName("§7" + name);
                skull.setItemMeta(skullMeta);
                p.getInventory().addItem(skull);
                p.sendMessage(String.format(skullM, name));
            }
        }
    };

    public static final FastCommand ENDERCHEST = new FastCommand("enderchest", "ec") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            PlayerEss essPlayer = PlayerW.get(p).getEssPlayer();
            if(hasntPemission(p, "wol.enderchest"))
                return;
            if(c.getArgs().length > 1){
                if(hasntPemission(p, "wol.enderchest.other"))
                    return;
                org.bukkit.entity.Player target = Bukkit.getPlayer(c.getArgs()[0]);
                if(target == null) {
                    p.sendMessage(String.format(badPlayer, c.getArgs()[0]));
                    return;
                }
                p.openInventory(target.getEnderChest());
                return;
            }
            p.openInventory(p.getEnderChest());
        }
    };

    public static final FastCommand BREAK = new FastCommand("break") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            if(hasntPemission(p, "wol.break"))
                return;
            Block targetBlock = p.getTargetBlock((Set<Material>) null, 10);
            targetBlock.breakNaturally();
            p.sendMessage(breakM);
        }
    };

    public static final FastCommand BROADCAST = new FastCommand("broadcast", "br", "broad") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
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

    public static final FastCommand AFK = new FastCommand("afk", "away-from-keyboard") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            if(hasntPemission(p, "wol.afk"))
                return;
            PlayerEss essPlayer = PlayerW.get(p).getEssPlayer();
            if(!essPlayer.isAfk()) {
                Bukkit.broadcastMessage(String.format(afkM, p.getName()));
                essPlayer.setAfk(true);
            }

            else {
                Bukkit.broadcastMessage(String.format(notAfkM, p.getName()));
                essPlayer.setAfk(false);
            }
        }
    };

    public static final FastCommand KICK = new FastCommand("kick", "expulse") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            if(hasntPemission(p, "wol.kick"))
                return;
            if(c.getArgs().length < 2){
                p.sendMessage("§7/kick §a<joueur> <raison>");
                return;
            }
            if(c.getArgs().length > 1){
                org.bukkit.entity.Player target = Bukkit.getPlayer(c.getArgs()[0]);
                if(target == null){
                    p.sendMessage(String.format(badPlayer, c.getArgs()[0]));
                    return;
                }
                String textFromArg = getTextFromArg(1, c.getArgs());
                target.kickPlayer(textFromArg.replace("&", "§"));
            }
        }
    };

    public static final FastCommand PING = new FastCommand("ping", "p") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            if(hasntPemission(p, "wol.kick"))
                return;
            if(c.getArgs().length >= 1){
                org.bukkit.entity.Player target = Bukkit.getPlayer(c.getArgs()[0]);
                if(target == null){
                    p.sendMessage(String.format(badPlayer, c.getArgs()[0]));
                    return;
                }
                p.sendMessage(String.format(pingForM, target.getName(), getPing(target)));
                return;
            }
            p.sendMessage(String.format(pingM, getPing(p)));
        }

        private int getPing(org.bukkit.entity.Player player) {
            try {
                int ping = 0;
                Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + getServerVersion() + ".entity.CraftPlayer");
                Object converted = craftPlayer.cast(player);
                Method handle = converted.getClass().getMethod("getHandle", new Class[0]);
                Object entityPlayer = handle.invoke(converted, new Object[0]);
                Field pingField = entityPlayer.getClass().getField("ping");
                ping = pingField.getInt(entityPlayer);
                return ping;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        private String getServerVersion() {
            Pattern brand = Pattern.compile("(v|)[0-9][_.][0-9][_.][R0-9]*");

            String pkg = Bukkit.getServer().getClass().getPackage().getName();
            String version = pkg.substring(pkg.lastIndexOf('.') + 1);
            if (!brand.matcher(version).matches()) {
                version = "";
            }
            return version;
        }
    };

    public static final FastCommand BACK = new FastCommand("back", "return") {
        @Override
        public void command(Command<org.bukkit.entity.Player> c) {
            org.bukkit.entity.Player p = c.getSender();
            if(hasntPemission(p, "wol.back"))
                return;
            PlayerEss ep = PlayerW.get(p).getEssPlayer();
            if(ep.getBack() == null){
                p.sendMessage(notBack);
                return;
            }
            // TODO: 03/10/2016 Safe isTeleported
            p.teleport(ep.getBack());
            p.sendMessage(backM);
        }
    };

    //    public static final FastCommand REPAIR = new FastCommand("repair") {
//        @Override
//        public void command(Command<PlayerW> c) {
//            PlayerW p = c.getSender();
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
//        public void command(Command<PlayerW> c) {
//            PlayerW p = c.getSender();
//            if(hasntPemission(p, "wol.repairall"))
//                return;
//            List<ItemStack> m = BukkitUtils.getTotalInventory(p);
//            m.stream().filter(stack -> stack != null && !stack.getType().equals(Material.AIR)).forEach(stack -> {
//                stack.setDurability((short) 0);
//            });
//            p.sendMessage(repairAllM);
//        }
//    };

    /********************
     STATIC USEFUL METHODS
    ********************/

    private static boolean hasntPemission(org.bukkit.entity.Player p, String perm){
        if(!p.isOp() || !p.hasPermission(perm)){
            p.sendMessage(needToBeOp);
            return true;
        }
        return false;
    }

    private static int getInt(String s, String error, org.bukkit.entity.Player p){
        int level = 0;
        try {
            level = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            p.sendMessage(error);
            return -1;
        }
        return level;
    }

    private static double getDouble(String s, String error, org.bukkit.entity.Player p){
        double level = 0;
        try {
            level = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            p.sendMessage(error);
            return -1;
        }
        return level;
    }

    private static String getTextFromArg(int start, String[] args){
        StringBuilder r = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            r.append(args[i]).append(" ");
        }
        return r.toString();
    }



    /********************
     IMPLEMENTED METHOD
    ********************/

    @Override
    public void init() {}
}
