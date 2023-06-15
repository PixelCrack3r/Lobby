package me.pixelgames.pixelcrack3r.lobby.gui;

import java.util.HashMap;

import me.pixelgames.pixelcrack3r.lobby.managers.InventoryManager;
import me.pixelgames.pixelcrack3r.lobby.utils.ItemGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiLobbySwitcher extends GuiScreen {
	
	@Override
	public Inventory init() {
		InventoryManager man = InventoryManager.getInventorySlot("lobby-switcher");
		
		Inventory inv = Bukkit.createInventory(null, man.getSize(), man.getHeadline());
		
		if(man.isPlaceHolderEnabled()) {
			for(int i = 0; i < man.getSize(); i++) {
				inv.setItem(i, ItemGenerator.modify().setItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7)).setDisplayName(" ").build());
			}
		}
		
		HashMap<Integer, ItemStack> items = man.getItems();
		
		for(int slot : items.keySet()) {
			inv.setItem(slot, items.get(slot));
		}
		
		return inv;
	}

}
