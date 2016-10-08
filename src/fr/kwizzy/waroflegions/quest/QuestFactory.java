package fr.kwizzy.waroflegions.quest;

import fr.kwizzy.waroflegions.player.PlayerQuest;

/**
 * Par Alexis le 08/10/2016.
 */

public class QuestFactory extends QuestExecutor implements IQuestFactory{

    private PlayerQuest questPlayer;

    private IQuest quest;
    private int progress = 0;
    private boolean finished = false;

    public QuestFactory(IQuest q, PlayerQuest questPlayer) {
        super(q.getEvent());
        this.quest = q;
        this.questPlayer = questPlayer;
        setQuestFactory(this);
    }

    @Override
    public void call(int i){
        progress += i;
        if(quest.getValue() <= progress) {
            finished = true;
            unregister();
        }
    }

    @Override
    public IQuest getQuest() {
        return quest;
    }

    @Override
    public int getProgress() {
        return progress;
    }

    @Override
    public boolean isFinish() {
        return finished;
    }

    @Override
    public PlayerQuest getPlayerQuest() {
        return questPlayer;
    }
}
