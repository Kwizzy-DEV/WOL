package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.quest.IQuest;
import fr.kwizzy.waroflegions.quest.IQuestFactory;
import fr.kwizzy.waroflegions.quest.QuestManager;
import fr.kwizzy.waroflegions.util.IMemory;
import fr.kwizzy.waroflegions.util.java.MathsUtils;
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

    public PlayerQuest(IMemory m, WOLPlayer wol) {
        super(m, wol);
        this.player = wol.getPlayer();
        loadQuests();
    }

    private void loadQuests() {
        JSONObject quests = getJsonQuest();
        if(quests != null) {
            Set<String> strings = quests.keySet();
            for (String string : strings) {
                int o = quests.getInt(string);
                int questId = Integer.parseInt(string);
                IQuest quest = QuestManager.getQuest(questId);
                if (quest != null) {
                    IQuestFactory iQuestFactory = quest.create(this);
                    if (o >= quest.getValue())
                        iQuestFactory.setFinish(true);
                    addQuest(iQuestFactory);
                }
            }
        }
        int reachQuest = MathsUtils.nextDecade(WOLPlayer.get(getPlayer()).getPlayerLeveling().getLevel());
        for (int i = 0; i < reachQuest; i++) {
            if(!containQuest(i)){
                IQuest quest = QuestManager.getQuest(i);
                addQuest(quest);
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

    public boolean containQuest(int id){
        for (IQuestFactory iQuestFactory : questFactoryList) {
            if(iQuestFactory.getQuest().getId() == id)
                return true;
        }
        return false;
    }

    private JSONObject getJsonQuest(){
        JSONObject json = memory().getJson();
        return json.getJSONObject("quests");
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
