package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.player.PlayerData;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.quest.IQuest;
import fr.kwizzy.waroflegions.quest.IQuestFactory;
import fr.kwizzy.waroflegions.quest.QuestFactory;
import fr.kwizzy.waroflegions.util.IMemory;
import org.bukkit.entity.Player;

import javax.annotation.CheckForNull;
import java.util.LinkedList;

/**
 * Par Alexis le 08/10/2016.
 */

public class PlayerQuest extends PlayerData{


    private Player player;
    private LinkedList<IQuestFactory> questFactoryList = new LinkedList<>();

    public PlayerQuest(Player player, IMemory m) {
        super(m, WOLPlayer.get(player));
        this.player = player;
        loadQuests();
    }

    private void loadQuests() {

    }

    public void addQuest(IQuest t){
        questFactoryList.add(t.create(this));
    }

    public void removeQuest(IQuestFactory qf){
        if(questFactoryList.contains(qf))
            questFactoryList.remove(qf);
    }

    @CheckForNull
    public Player getPlayer() {
        return player;
    }

    @Override
    public void save() {

    }
}
