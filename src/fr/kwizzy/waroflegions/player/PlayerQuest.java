package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.quest.IQuest;
import fr.kwizzy.waroflegions.quest.IQuestFactory;
import fr.kwizzy.waroflegions.quest.QuestManager;
import fr.kwizzy.waroflegions.util.IMemory;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import javax.annotation.CheckForNull;
import java.util.LinkedList;
import java.util.Set;

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
        JSONObject json = memory().getJson();
        JSONObject quests = json.getJSONObject("quests");
        if(quests == null)
            return;
        Set<String> strings = quests.keySet();
        for (String string : strings) {
            int o = quests.getInt(string);
            int questId = Integer.parseInt(string);
            IQuest quest = QuestManager.getQuest(questId);
            if(quest != null){
                IQuestFactory iQuestFactory = quest.create(this);
                iQuestFactory.call(o);
                addQuest(iQuestFactory);
            }
        }
    }

    public void addQuest(IQuest t){
        questFactoryList.add(t.create(this));
    }

    public void addQuest(IQuestFactory t){
        questFactoryList.add(t);
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
        for (IQuestFactory iQuestFactory : questFactoryList) {
            memory().set("quests." + iQuestFactory.getQuest().getId(), iQuestFactory.getProgress());
        }

    }
}
