package fr.kwizzy.waroflegions.quest.gui;

import fr.kwizzy.waroflegions.player.PlayerQuest;
import fr.kwizzy.waroflegions.quest.IQuest;
import fr.kwizzy.waroflegions.quest.IQuestContent;
import fr.kwizzy.waroflegions.quest.QuestManager;
import fr.kwizzy.waroflegions.util.bukkit.builder.GUIBuilder;
import fr.kwizzy.waroflegions.util.bukkit.builder.ItemBuilder;
import fr.kwizzy.waroflegions.util.bukkit.classmanager.message.Message;
import fr.kwizzy.waroflegions.util.java.bistream.BiStream;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class QuestViewer extends GUIBuilder
{
	/********************
	 * Messages
	 ********************/
	@Message private static String title = "Quêtes %s à %s";

	private final PlayerQuest pq;
	private final ItemStack itemStack;
	private final BiStream.BiValue<Integer, Integer> minMax;

	public QuestViewer(Player p, PlayerQuest pq, ItemStack itemStack) {
		super(p);

		this.pq = pq;
		this.itemStack = itemStack;
		this.minMax = parsing();
	}

	public void show() {
		getPlayer().closeInventory();
		final int amountQuest = QuestManager.getQuests(minMax.getKey(), minMax.getValue()).size();
		int ceil = (int) Math.ceil((double) amountQuest / 18.);
        show(4, String.format(title, minMax.getKey(), minMax.getValue()));
		if(ceil>1)
            addPages(ceil-1);
    	setItemsQ();
	}

	private void setItemsQ() {
		Collection<IQuest> quests = QuestManager.getQuests(minMax.getKey(), minMax.getValue());
		int index = 0;
		for (IQuest quest : quests) {
			setItemPosition(buildQuestItem(quest), index);
			index++;
			if (index > 3 * 9)
				index = 0;
		}

	}

	@Override
	public void onClickEvent(InventoryClickEvent e) {
		e.setCancelled(true);
	}

	@Override
	public void onCloseEvent(InventoryCloseEvent e) {

	}

	private BiStream.BiValue<Integer, Integer> parsing() {
		String displayName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
		String[] split = displayName.split(" ");
		return new BiStream.BiValue<>(Integer.parseInt(split[3]), Integer.parseInt(split[5]));
	}

	private ItemStack buildQuestItem(IQuest q) {
		String name = q.getName();
		double exp = q.getReward();
		IQuestContent questContent = pq.getQuestContent(q);
		int percentage = 0;
		if (pq.containQuest(q))
			percentage = pq.getPercentageAchieve(q);

		return new ItemBuilder(Material.PAPER).setName("§7" + name)
				.addLoreLine(String.format("§eAvancement: §a%d§e/§a%d", questContent.getProgress(), q.getValue()))
				.addLoreLine(String.format("§ePourcentage: §a%d §e%%", percentage)).addLoreLine("")
				.addLoreLine(String.format("§dRécompense: §e %s %s", Integer.toString((int) exp), "points d'exp"))
				.toItemStack();
	}
}