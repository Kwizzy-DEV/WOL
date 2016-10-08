package fr.kwizzy.waroflegions.quest;

import fr.kwizzy.waroflegions.player.PlayerQuest;
import org.bukkit.event.Event;
import java.util.function.Function;

/**
 * Par Alexis le 08/10/2016.
 */
public interface IQuest<T extends Event> {

    int getValue();
    int getLevel();
    int getId();
    double getExp();
    String getName();
    Class<T> getEvent();
    Function<T, Integer> getTester();

    IQuestFactory create(PlayerQuest pq);

}
