package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.quest.IQuest;
import fr.kwizzy.waroflegions.quest.IQuestFactory;
import fr.kwizzy.waroflegions.quest.QuestManager;
import fr.kwizzy.waroflegions.util.Memory;
import fr.kwizzy.waroflegions.util.java.MathsUtils;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Par Alexis le 08/10/2016.
 */

public class PlayerQuest extends PlayerData {

	private Player player;
	private LinkedList<IQuestFactory> questFactoryList = new LinkedList<>();

	public PlayerQuest(Memory m, WOLPlayer wol) {
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
                    iQuestFactory.hideCall(o);
                }
            }
        }
        int reachQuest = MathsUtils.nextDecade(WOLPlayer.get(getPlayer()).getPlayerLeveling().getLevel());
        for (int i = 0; i < reachQuest; i++) {
            Collection<IQuest> questsList = QuestManager.getQuests(i);
            questsList.forEach(e -> {
                if(!containQuest(e))
                    addQuest(e);
            });
        }
        questFactoryList.forEach(e -> e.getQuest().toString());
    }

	public void addQuest(IQuest t) {
		questFactoryList.add(t.create(this));
	}

	public void addQuest(IQuestFactory t) {
		questFactoryList.add(t);
	}

	public Collection<IQuestFactory> getQuests() {
		return questFactoryList;
	}

	public boolean isFullyCompleted(int pal) {
		pal *= 10;
		for (int i = 0; i < pal; i++) {
            Collection<IQuestFactory> quests = getQuests(i);
            for (IQuestFactory quest : quests) {
                if(!quest.isFinish())
                    return false;
            }
        }
        return true;
	}

	public boolean containQuest(int id) {
		for (IQuestFactory iQuestFactory : questFactoryList) {
			if (iQuestFactory.getQuest().getId() == id)
				return true;
		}
		return false;
	}

	public boolean containQuest(IQuest quest) {
		for (IQuestFactory iQuestFactory : questFactoryList) {
			if (iQuestFactory.getQuest().equals(quest))
				return true;
		}
		return false;
	}

	public IQuestFactory getQuest(int id) {
        for (IQuestFactory iQuestFactory : questFactoryList) {
            if (iQuestFactory.getQuest().getId() == id)
                return iQuestFactory;
        }
        return null;
	}

    public int getPal(){
        return (MathsUtils.nextDecade(WOLPlayer.get(getPlayer()).getPlayerLeveling().getLevel()))/10;
    }

    public int getPercentageAchieve(int pal){
        pal *= 10;
        double total = 0;
        double achieve = 0;
        for (int i = 1; i < pal; i++) {
            Collection<IQuestFactory> quests = getQuests(i);
            for (IQuestFactory quest : quests) {
                total += quest.getQuest().getValue();
                achieve += quest.getProgress();
            }
        }
        return (int)((achieve/total)*100);
    }

    public Collection<IQuestFactory> getQuests(int level) {
        return questFactoryList.stream()
                .filter(iQuestFactory -> iQuestFactory.getQuest().getLevel() == level)
                .collect(Collectors.toCollection(ArrayList::new));
    }

	private JSONObject getJsonQuest() {
		JSONObject json = memory().getJson();
		JSONObject quests = null;
		try {
			quests = json.getJSONObject("quests");
		} catch (Exception e) {
			return null;
		}
		return quests == null ? null : quests;
	}

	@CheckForNull
	public Player getPlayer() {
		return player;
	}

	@Override
	public void save() {
		for (IQuestFactory iQuestFactory : questFactoryList) {
			memory().put("quests." + iQuestFactory.getQuest().getId(), iQuestFactory.getProgress());
		}
	}
}
