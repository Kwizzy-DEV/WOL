package fr.kwizzy.waroflegions.quest;

import fr.kwizzy.waroflegions.WarOfLegions;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Par Alexis le 08/10/2016.
 */

public class QuestManager {

    private static Map<Integer, IQuest> questList = new HashMap<>();

    private QuestManager() { }

    public static void register(IQuestList c){
        Field[] fields = c.getClass().getFields();
        for (Field field : fields) {
            Object instance = null;
            try {
                instance = field.get(null);
                if(instance != null && instance instanceof IQuest){
                    IQuest q = (IQuest) instance;
                    questList.put(q.getId(), q);
                }
            } catch (IllegalAccessException e) {
                WarOfLegions.getInstance().print("Error on registering field from " + c.getClass());
            }

        }
    }

    static public IQuest getQuest(int i){
        return questList.get(i);
    }

}
