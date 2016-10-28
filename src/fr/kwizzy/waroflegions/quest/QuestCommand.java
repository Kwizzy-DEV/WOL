package fr.kwizzy.waroflegions.quest;

import fr.kwizzy.waroflegions.player.PlayerQuest;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.quest.gui.QuestVisualizer;
import fr.kwizzy.waroflegions.util.bukkit.command.Command;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandHandler;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Par Alexis le 08/10/2016.
 */

public class QuestCommand implements CommandListener {

    @CommandHandler(args = "debug", sender = Player.class)
    public void main(Command<Player> co){
        Bukkit.broadcastMessage("debug");
        Player sender = co.getSender();
        PlayerQuest playerQuest = WOLPlayer.get(sender).getPlayerQuest();
        QuestVisualizer qv = new QuestVisualizer(sender);
        qv.show();
    }

    @Override
    public String getCommand()
    {
        return "quest";
    }
}
