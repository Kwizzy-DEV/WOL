package fr.kwizzy.waroflegions.quest;

import fr.kwizzy.waroflegions.player.PlayerQuest;
import fr.kwizzy.waroflegions.util.bukkit.ActionBar;
import fr.kwizzy.waroflegions.util.bukkit.FireworkUtil;
import fr.kwizzy.waroflegions.util.bukkit.centered.CenteredMessage;
import fr.kwizzy.waroflegions.util.java.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Par Alexis le 08/10/2016.
 */

public class QuestFactory extends QuestExecutor implements IQuestFactory {

    private PlayerQuest questPlayer;

    private IQuest quest;
    private int progress = 0;
    private boolean finished = false;

    QuestFactory(IQuest q, PlayerQuest questPlayer) {
        super(q.getEvent());
        this.quest = q;
        this.questPlayer = questPlayer;
        setQuestFactory(this);
    }

    @Override
    public void call(int i){
        if(isFinish())
            return;
        progress += i;
        if(quest.getValue() <= progress) {
            finished = true;
            questPlayer.getWolPlayer().getPlayerLeveling().addExp(quest.getExp());
            finishQuestMessage();
            unregister();
        }
        ActionBar.sendActionBar(getQuestMessage(), questPlayer.getPlayer());
    }

    @Override
    public void hideCall(int i) {
        if(isFinish())
            return;
        progress += i;
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

    @Override
    public void setFinish(boolean b) {
        if(b)
            progress = quest.getValue();
        finished = b;
    }

    public void finishQuestMessage(){
        Player p = questPlayer.getPlayer();
        if(p != null) {
            FireworkUtil.playFirework(p.getLocation(), 20);
            p.sendMessage(StringUtils.LINE);
            p.sendMessage("");
            CenteredMessage.sendCenteredMessage("§a§l⇪".toUpperCase() + " §f§lQUETE FINI " + "§a§l⇪".toUpperCase(), p);
            CenteredMessage.sendCenteredMessage(getQuestMessage(), p);
            p.sendMessage("");
            p.sendMessage(StringUtils.LINE);
        }
    }

    private String getQuestMessage(){
        return "§e" + quest.getName() + ": §c" + progress + "§e/§a" + quest.getValue();
    }

    @Override
    public String toString() {
        return "QuestFactory{" +
                "quest=" + quest.getId() +
                ", progress=" + progress +
                ", finished=" + finished +
                '}';
    }
}
