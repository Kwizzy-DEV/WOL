package fr.kwizzy.waroflegions.util.bukkit.builder;

import java.util.*;

import fr.kwizzy.waroflegions.WOL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Par Alexis le 27/09/2016.
 */

public abstract class GUIBuilder implements Listener {

	private static Map<Player, GUIBuilder> guiList = new HashMap<>();
	private static Plugin instance = WOL.getInstance();
	private static boolean isRegistered = false;

	private Integer size = 9;
	private String title = "Inventaire";
	private List<Inventory> inventoryList = new ArrayList<>();
	private Inventory currentInventory;
	private Player player;

	public GUIBuilder(Player player) {
		this.player = player;
	}

	/************************
	 * FUNCTIONS
	 ************************/

	/**
	 * Show the inventory to the player.
	 * 
	 * @param size * 9 of the inventory
	 * @param title name of the inventory
	 */
	public void show(Integer size, String title) {
		guiList.put(player, this);

		this.size = size * 9;
		this.title = title;
		this.inventoryList.add(Bukkit.createInventory(null, this.size, title));

		openInventory(0);
        register();
	}

	/**
	 * Show the inventory at index @index
	 * 
	 * @param index of the inventory (start from 0)
	 */
	public void openInventory(Integer index) {
		player.openInventory(this.inventoryList.get(index));
		currentInventory = this.inventoryList.get(index);
		indicator();
	}

	/**
	 * Add page(s) to the gui builder.
     *
	 * @param amount  of pages to add
	 */
	public void addPages(Integer amount) {
		for (int x = 0; x < amount; x++) {
			inventoryList.add(Bukkit.createInventory(null, size, title));
		}
		indicator();
	}

	/**
	 * Add page(s) to the gui builder with size & title.
	 * 
	 * @param amount of pages to add
	 * @param size * 9 of the inventory
	 * @param title of the inventory
	 */
	public void addPages(Integer amount, Integer size, String title) {
		for (int x = 0; x < amount; x++) {
			inventoryList.add(Bukkit.createInventory(null, size, title));
		}
		indicator();
	}

    /**
     * Place indicator if more than 1 page
     */
	public void indicator() {
		if (getPages()-1 <= 0)
            return;
        for (int i = 0; i < inventoryList.size(); i++) {
			Inventory inv = inventoryList.get(i);
			setItemPositionBypass(getMiddle(), inv.getSize()-5, i);
			setItemPositionBypass(new ItemStack(Material.AIR), inv.getSize()-9, i);
			setItemPositionBypass(new ItemStack(Material.AIR), inv.getSize()-1, i);
			if(currentIndex()+1 > 1) {
				setItemPositionBypass(getBack(), inv.getSize() - 9, i);
			}
            if(currentIndex()+1 != getPages()) {
				setItemPositionBypass(getNext(), inv.getSize() - 1, i);
			}
        }
	}

	/**
	 * register the current class
	 */
	private void register() {
		if (!isRegistered)
			instance.getServer().getPluginManager().registerEvents(this, instance);
		isRegistered = true;
	}

    /**
     * Set item in the gui
     *
     * @param is item to set
     * @param position position of the item
     */
    public void setItemPosition(ItemStack is, int position) {
        setItemPosition(is, position, 0);
    }

    /**
     * Set item in the gui
     *
     * @param is item to set
     * @param position position of the item
     * @param page to add items
     */
    public void setItemPosition(ItemStack is, int position, int page) {
		if ((getPages()-1) > 0 && position > size - 10)
            return;

        new BukkitRunnable() {
            @Override
            public void run() {
				inventoryList.get(page).setItem(position, is);
            }
        }.runTaskLater(instance, 1);
    }

    private void setItemPositionBypass(ItemStack is, int position, int page) {
		inventoryList.get(page).setItem(position, is);
    }

    /**
     * Add items to the first void slot.
     *
     * @param is items to add
     * @return a map of each items not give
     */
    public Map<Integer, ItemStack> addItem(ItemStack... is) {
        return addItem(0, is);
    }

    /**
     * Add items to the first void slot.
     *
     * @param is items to add
     * @param page to add item
     * @return a map of each items not give
     */
    public Map<Integer, ItemStack> addItem(Integer page, ItemStack... is) {
        return inventoryList.get(page).addItem(is);
    }

    /**
     * Page back
     */
    public void back(){
        if(currentIndex() > 0)
            openInventory(currentIndex()-1);
    }

    /**
     * Page next
     */
    public void next(){
        if(currentIndex() < getPages()-1)
            openInventory(currentIndex()+1);
    }

	/*************************
	 * GETTERS
	 *************************/

	public int getSize() {
		return size;
	}

	public int getSize(int page) {
		return inventoryList.get(page).getSize();
	}

	public String getTitle() {
		return title;
	}

	public Player getPlayer() {
		return player;
	}

	public List<Inventory> getInventoryList() {
		return inventoryList;
	}

	public int getPages() {
		return inventoryList.size();
	}

	public Inventory getCurrentInventory() {
		return currentInventory;
	}

	private int currentIndex() {
		for (int i = 0; i < inventoryList.size(); i++) {
			if (inventoryList.get(i).equals(currentInventory)) {
				return i;
			}
		}
		return 0;
	}

	private ItemStack getBack() {
		ItemStack is = new ItemStack(Material.STONE_BUTTON);
		ItemMeta itemMeta = is.getItemMeta();
		itemMeta.setDisplayName("§e< Précédent");
		is.setItemMeta(itemMeta);
		return is;
	}

	private ItemStack getNext() {
		ItemStack is = new ItemStack(Material.STONE_BUTTON);
		ItemMeta itemMeta = is.getItemMeta();
		itemMeta.setDisplayName("§eSuivant >");
		is.setItemMeta(itemMeta);
		return is;
	}

	private ItemStack getMiddle() {
		ItemStack is = new ItemStack(Material.ENDER_PEARL);
		ItemMeta itemMeta = is.getItemMeta();
		itemMeta.setDisplayName("§ePage : " + (currentIndex()+1));
		itemMeta.setLore(Arrays.asList("§7" + (currentIndex() + 1) + "/" + (getPages())));
		is.setItemMeta(itemMeta);
		return is;
	}

	/*************************
     * ABSTRACTS
     *************************/

    public abstract void onClickEvent(InventoryClickEvent e);

    public abstract void onCloseEvent(InventoryCloseEvent e);

    /*************************
     * EVENTS
     *************************/

    @EventHandler
    public void onClick(InventoryClickEvent e){
        HumanEntity whoClicked = e.getWhoClicked();
        if(whoClicked instanceof Player){
            Player p = (Player) whoClicked;
            if(!guiList.containsKey(p))
                return;

            GUIBuilder guiBuilder = guiList.get(p);

            Inventory cuInventory = guiBuilder.getCurrentInventory();
            Inventory clInventory = e.getInventory();

            if(clInventory.equals(cuInventory) || clInventory.getName().equalsIgnoreCase(cuInventory.getName())){
                e.setCancelled(true);
                ItemStack is = e.getCurrentItem();
                if(is == null || is.getType().equals(Material.AIR))
                    return;
                if(is.isSimilar(getBack()))
                    back();
                else if(is.isSimilar(getNext()))
                    next();
                else if(!is.isSimilar(getMiddle()))
                    onClickEvent(e);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        if(e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            GUIBuilder g = guiList.get(p);
            new BukkitRunnable(){
                @Override
                public void run()
                {
                    if (p.getOpenInventory() == null || !p.getOpenInventory().getTitle().equalsIgnoreCase(g.getCurrentInventory().getTitle())) {
                        onCloseEvent(e);
                        g.currentInventory = null;
                        guiList.remove(p);
                    }
                }
            }.runTaskLater(instance, 10);
        }
    }
}
