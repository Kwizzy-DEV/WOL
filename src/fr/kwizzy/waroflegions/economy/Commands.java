package fr.kwizzy.waroflegions.economy;

import fr.kwizzy.waroflegions.player.PlayerW;
import fr.kwizzy.waroflegions.util.bukkit.command.Command;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandHandler;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandListener;
import fr.kwizzy.waroflegions.util.java.ArraysUtils;
import org.bukkit.Bukkit;

/**
 * Par Alexis le 01/10/2016.
 */

public class Commands implements CommandListener {

    private static String money = "§7Tu as §e%s %s§7.";
    private static String playerNotOnline = "§cLe joueur '§e%s§c' n'est pas en ligne.";
    private static String isNotNumber = "§cL'agument '§e%s§c' n'est pas un chiffre.";

    @CommandHandler(args = {"money"}, sender = org.bukkit.entity.Player.class)
    public void money(Command<org.bukkit.entity.Player> command){
        org.bukkit.entity.Player sender = command.getSender();
        PlayerEconomy ep = PlayerW.load(command.getSender()).getEconomyPlayer();
        sender.sendMessage(String.format(money, ep.getMoney(), Economy.MONEY_NAME));
    }

    @CommandHandler(args = {"pay", CommandHandler.UNDEFINED, }, sender = org.bukkit.entity.Player.class)
    public void pay(Command<org.bukkit.entity.Player> command){
        org.bukkit.entity.Player sender = command.getSender();
        PlayerEconomy ep = PlayerW.load(command.getSender()).getEconomyPlayer();
        org.bukkit.entity.Player target = Bukkit.getPlayer(command.getArgs()[1]);
        if(target == null){
            sender.sendMessage(String.format(playerNotOnline, command.getArgs()[1]));
            return;
        }
        int money = 0;
        try {
            money = Integer.parseInt(command.getArgs()[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(String.format(isNotNumber, command.getArgs()[2]));
            return;
        }
        ep.transaction(money, PlayerW.load(target));

    }

    @CommandHandler(args = {}, sender = org.bukkit.entity.Player.class)
    public void defaultCommand(Command<org.bukkit.entity.Player> command){
        org.bukkit.entity.Player sender = command.getSender();
        if(sender.isOp()){
            sender.sendMessage(ArraysUtils.toArray(
                    "§e/eco help - §7Aide sur la commande eco",
                    "§e/eco money - §7Regardes ton argent",
                    "§e/eco pay <player> - §7Paye un joueur",
                    "§e/eco quota - §7Regardes ton quota",
                    "§e/eco give <player> <montant> - §7Donner de l'argent a un joueur",
                    "§e/eco remove <player> <montant> - §7Retirer de l'argent a un joueur",
                    "§e/eco check <player> - §7Regardes l'argent d'un joueur"
            ));
            return;
        }
        sender.sendMessage(ArraysUtils.toArray(
                "§e/eco help - §7Aide sur la commande eco",
                "§e/eco money - §7Regardes ton argent",
                "§e/eco pay <player> - §7Paye un joueur",
                "§e/eco quota - §7Regardes ton quota")
        );
    }

    @CommandHandler(args = {"help"}, sender = org.bukkit.entity.Player.class)
    public void help(Command<org.bukkit.entity.Player> command){
        defaultCommand(command);
    }

}
