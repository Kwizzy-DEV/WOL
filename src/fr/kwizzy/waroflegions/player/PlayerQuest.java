package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.quest.IQuest;
import fr.kwizzy.waroflegions.quest.IQuestContent;
import fr.kwizzy.waroflegions.quest.QuestManager;
import fr.kwizzy.waroflegions.util.storage.Memory;
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
	private LinkedList<IQuestContent> questContents = new LinkedList<>();

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
                    IQuestContent iQuestContent = quest.create(this);
                    if (o >= quest.getValue())
                        iQuestContent.setFinish(true);
                    addQuest(iQuestContent);
                    iQuestContent.hideCall(o);
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
        questContents.forEach(e -> e.getQuest().toString());
    }

	public void addQuest(IQuest t) {
		questContents.add(t.create(this));
	}

	public void addQuest(IQuestContent t) {
		questContents.add(t);
	}

	public Collection<IQuestContent> getQuests()
    {
		return questContents;
	}

	public boolean isFullyCompleted(int pal)
    {
		pal *= 10;
        for (int i = pal-10; i < pal; i++) {
            Collection<IQuestContent> quests = getQuests(i);
            for (IQuestContent quest : quests) {
                if(!quest.isFinish())
                    return false;
            }
        }
        return true;
	}

	public boolean containQuest(int id)
    {
		for (IQuestContent iQuestContent : questContents) {
			if (iQuestContent.getQuest().getId() == id)
				return true;
		}
		return false;
	}

	public boolean containQuest(IQuest quest)
    {
		for (IQuestContent iQuestContent : questContents) {
			if (iQuestContent.getQuest().equals(quest))
				return true;
		}
		return false;
	}

	public IQuestContent getQuest(int id)
    {
        for (IQuestContent iQuestContent : questContents) {
            if (iQuestContent.getQuest().getId() == id)
                return iQuestContent;
        }
        return null;
	}

    public Collection<IQuestContent> getQuests(int level)
    {
        return questContents.stream()
                .filter(iQuestFactory -> iQuestFactory.getQuest().getLevel() == level)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public IQuestContent getQuestContent(IQuest q)
    {
        for (IQuestContent iQuestContent : questContents)
        {
            if(iQuestContent.getQuest() == q)
                return iQuestContent;
        }
        return null;
    }

    public int getPal()
    {
        return (MathsUtils.nextDecade(WOLPlayer.get(getPlayer()).getPlayerLeveling().getLevel()))/10;
    }

    public int getPercentageAchieve(int pal)
    {
        pal *= 10;
        double total = 0;
        double achieve = 0;
        for (int i = pal-10; i < pal; i++) {
            Collection<IQuestContent> quests = getQuests(i);
            for (IQuestContent quest : quests) {
                total += quest.getQuest().getValue();
                achieve += quest.getProgress();
            }
        }
        if(total <= 0)
            return 100;
        final int amount = (int)((achieve/total)*100);
        return  amount > 100 ? 100 : amount;
    }

    public int getPercentageAchieve(IQuest q)
    {
        IQuestContent questContent = getQuestContent(q);
        if(questContent == null)
            return 0;
        double total = q.getValue();
        double achieve = questContent.getProgress();
        if(total <= 0)
            return 100;
        final int amount = (int)((achieve/total)*100);
        return  amount > 100 ? 100 : amount;
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
		for (IQuestContent iQuestContent : questContents) {
			memory().put("quests." + iQuestContent.getQuest().getId(), iQuestContent.getProgress());
		}
	}
}
