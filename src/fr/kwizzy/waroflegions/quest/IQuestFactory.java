package fr.kwizzy.waroflegions.quest;

import fr.kwizzy.waroflegions.player.PlayerQuest;

/**
 * Par Alexis le 08/10/2016.
 */
public interface IQuestFactory {

    void call(int i);
    void hideCall(int i);
    PlayerQuest getPlayerQuest();
    IQuest getQuest();
    int getProgress();
    boolean isFinish();
    void setFinish(boolean b);
}
