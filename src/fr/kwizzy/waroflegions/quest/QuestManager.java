package fr.kwizzy.waroflegions.quest;

import fr.kwizzy.waroflegions.WOL;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Par Alexis le 08/10/2016.
 */

public class QuestManager {

    private static Map<Integer, IQuest> questList = new HashMap<>();

    private QuestManager() { }

    public static void register(IQuestList c){

        Field[] fields = c.getClass().getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object instance = null;
            try {
                instance = field.get(null);
                if(instance != null && instance instanceof IQuest){
                    Bukkit.broadcastMessage("+1");
                    IQuest q = (IQuest) instance;
                    questList.put(q.getId(), q);
                }
            } catch (IllegalAccessException e) {
                WOL.getInstance().print("Error on registering field from " + c.getClass());
            }

        }
    }

    public static IQuest getQuest(int id){
        return questList.get(id);
    }

    public static Collection<IQuest> getQuests(int level){
        return questList.values().stream()
                .filter(e -> e.getLevel() == level)
                .collect(Collectors.toList());
    }

    public static Collection<IQuest> getQuests(int start, int end)
    {
        Collection<IQuest> quests = new ArrayList<>();
        for (int i = start; i < end; i++)
        {
            quests.addAll(getQuests(i));
        }
        return quests;
    }

}
