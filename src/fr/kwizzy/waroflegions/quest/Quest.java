package fr.kwizzy.waroflegions.quest;

import fr.kwizzy.waroflegions.player.PlayerQuest;
import org.bukkit.event.Event;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Par Alexis le 08/10/2016.
 */

public class Quest <T extends Event> implements IQuest{

    private int value;
    private int level;
    private int id;
    private String name;
    private Class<T> event;
    private Function<T, Integer> tester;

    public Quest(Class<T> event, int callAmount, int id, int level, String name, Function<T, Integer> tester) {
        this.level = level;
        this.id = id;
        this.value = callAmount;
        this.name = name;
        this.event = event;
        this.tester = tester;
    }

    public Quest(Class<T> event, int callAmount, String name, int id, int level, Predicate<T> tester) {
        this.level = level;
        this.id = id;
        this.value = callAmount;
        this.name = name;
        this.event = event;
        this.tester = t -> tester.test(t) ? 1 : 0;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class getEvent() {
        return event;
    }

    @Override
    public Function<T, Integer> getTester() {
        return tester;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public IQuestFactory create(PlayerQuest pq) {
        return new QuestFactory(this, pq);
    }
}
