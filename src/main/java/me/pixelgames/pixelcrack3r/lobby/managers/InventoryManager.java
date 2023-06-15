package me.pixelgames.pixelcrack3r.lobby.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.BridgeServiceProperty;
import me.pixelgames.pixelcrack3r.lobby.gui.Gui;
import me.pixelgames.pixelcrack3r.lobby.gui.GuiScreen;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import me.pixelgames.pixelcrack3r.lobby.utils.ItemGenerator;

public class InventoryManager {

	private String name;
	private List<String> customItems = new ArrayList<String>();
	
	public InventoryManager(String name) {
		this.name = name;
	}
	
	public static void createNewInventory(String name, String headline, int size, boolean placeHolders, List<String> items, boolean def) {
		if(def) {
			Config.getInventory().addDefault("inventory." + name + ".name", headline);
			Config.getInventory().addDefault("inventory." + name + ".size", size);
			Config.getInventory().addDefault("inventory." + name + ".placeholders", placeHolders);
			if(items != null) {
				Config.getInventory().addDefault("inventory." + name + ".items", items);
			}
		} else {
			Config.getInventory().set("inventory." + name + ".name", headline);
			Config.getInventory().set("inventory." + name + ".size", size);
			Config.getInventory().set("inventory." + name + ".placeholders", placeHolders);
			if(items != null) {
				Config.getInventory().set("inventory." + name + ".items", items);
			}

		}
		
		Config.saveInventory();
	}
	
	public boolean exists() {
		return Config.getInventory().isSet("inventory." + this.name + ".name");
	}
	
	public String getHeadline() {
		return ChatColor.translateAlternateColorCodes('&', Config.getInventory().getString("inventory." + this.name + ".name"));
	}
	
	public int getSize() {
		if(this.isLobbySwitcher() && this.canAutoModified()) {
			int slots = 0;
			if(Config.getConfig().getBoolean("settings.plugin.dependencies.cloudnet3")) {
				int count = CloudNetDriver.getInstance().getCloudServiceProvider().getServicesCountByTask(Config.getConfig().getString("settings.lobby.group.premium"));
				while(count >= slots) slots += 9;
				
				count = CloudNetDriver.getInstance().getCloudServiceProvider().getServicesCountByTask(Config.getConfig().getString("settings.lobby.group.default"));
				while(count >= slots) slots += 9;
			}
			return slots <= 0 ? 9 : slots;
		}
		return Config.getInventory().getInt("inventory." + this.name + ".size");
	}
	
	public boolean isPlaceHolderEnabled() {
		return Config.getInventory().getBoolean("inventory." + this.name + ".placeholders");
	}
	
	public boolean isLobbySwitcher() {
		return Config.getInventory().isSet("inventory." + this.name + ".switcher") && Config.getInventory().getBoolean("inventory." + this.name + ".switcher");
	}
	
	public boolean canAutoModified() {
		return Config.getInventory().isSet("inventory." + this.name + ".automodify") && Config.getInventory().getBoolean("inventory." + this.name + ".automodify");
	}
	
	public List<String> getItemNames() {
		if(this.isLobbySwitcher() && this.canAutoModified()) {
			this.getItems();
			return this.customItems;
		}
		
		return Config.getInventory().getStringList("inventory." + this.name + ".items");
	}
	
	public HashMap<Integer, ItemStack> getItems() {
		HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
		
		if(this.isLobbySwitcher() && this.canAutoModified()) {
			if(Config.getConfig().getBoolean("settings.plugin.dependencies.cloudnet3")) {
				Collection<ServiceInfoSnapshot> premiumLobbies = CloudNetDriver.getInstance().getCloudServiceProvider().getCloudServicesByGroup(Config.getConfig().getString("settings.lobby.group.premium"));
				if(premiumLobbies != null) {
					int slot = 0;
					for(ServiceInfoSnapshot serviceInfo : premiumLobbies) {
						ItemStack itm = new ItemStack(Material.STAINED_GLASS, 1, (byte) 1);
						ItemMeta meta = itm.getItemMeta();
						meta.setDisplayName("§6" + serviceInfo.getName());
						meta.setLore(Arrays.asList("§7» §a" + serviceInfo.getProperty(BridgeServiceProperty.ONLINE_COUNT).orElse(0) + " §7Spieler"));
						itm.setItemMeta(meta);
						
						if(serviceInfo.getServiceId().getUniqueId().toString().equalsIgnoreCase(CloudNetDriver.getInstance().getCloudServiceProvider(Bukkit.getServerName()).getServiceInfoSnapshot().getServiceId().getUniqueId().toString())) itm = ItemGenerator.modify().setItemStack(itm).addEnchantment(Enchantment.KNOCKBACK, 0, false).build();
						items.put(slot, itm);
						ItemManager manager = ItemManager.getCustom(itm.getItemMeta().getDisplayName());
						manager.setCustomServer(serviceInfo.getName());
						this.customItems.add(itm.getItemMeta().getDisplayName());
						slot++;
					}
				}
				
				Collection<ServiceInfoSnapshot> lobbies = CloudNetDriver.getInstance().getCloudServiceProvider().getCloudServicesByGroup(Config.getConfig().getString("settings.lobby.group.default"));
				if(lobbies != null) {
					int slot = 0;
					for(ServiceInfoSnapshot serviceInfo : lobbies) {
						ItemStack itm = new ItemStack(Material.STAINED_GLASS, 1, (byte) 1);
						ItemMeta meta = itm.getItemMeta();
						meta.setDisplayName("§6" + serviceInfo.getName());
						meta.setLore(Arrays.asList("§7» §a" + serviceInfo.getProperty(BridgeServiceProperty.ONLINE_COUNT).orElse(0) + " §7Spieler"));
						itm.setItemMeta(meta);
						
						if(serviceInfo.getServiceId().getUniqueId().toString().equalsIgnoreCase(CloudNetDriver.getInstance().getCloudServiceProvider(Bukkit.getServerName()).getServiceInfoSnapshot().getServiceId().getUniqueId().toString())) itm = ItemGenerator.modify().setItemStack(itm).addEnchantment(Enchantment.KNOCKBACK, 0, false).build();
						items.put(slot, itm);
						ItemManager manager = ItemManager.getCustom(itm.getItemMeta().getDisplayName());
						manager.setCustomServer(serviceInfo.getName());
						this.customItems.add(itm.getItemMeta().getDisplayName());
						slot++;
					}
				}
			}

			return items;
		}
		
		for(String itemName : this.getItemNames()) {
			ItemManager item = ItemManager.getItemSlot(itemName);
			items.put(item.getSlot(), item.getItemStack());
		}
		return items;
	}
	
	public static InventoryManager getInventorySlot(String name) {
		return new InventoryManager(name);
	}
	
	public String getItemByName(String name) {
		HashMap<String, String> hash = new HashMap<String, String>();
		
		for(String item : getItemNames()) {
			if(ItemManager.getItemSlot(item).getName().equalsIgnoreCase(name) || item.equalsIgnoreCase(name)) {
				return item;
			}
			hash.put(ItemManager.getItemSlot(item).getName(), item);
		}
		
		return hash.get(name);
	}
	
	public void addItem(String item) {
		List<String> itms = getItemNames();
		itms.add(item);
		Config.getInventory().set("inventory." + this.name + ".items", itms);
		Config.saveInventory();
	}
	
	public GuiScreen createCustom() {
		return new Gui() {
			
			@Override
			public Inventory init() {
				Inventory inv = Bukkit.createInventory(null, getSize(), getHeadline());
				
				if(isPlaceHolderEnabled()) {
					for(int i = 0; i < getSize(); i++) {
						inv.setItem(i, ItemGenerator.modify().setItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7)).setDisplayName(" ").build());
					}
				}
				
				for(String item : getItemNames()) {
					ItemManager itm = ItemManager.getItemSlot(item).registerAsync();
					
					inv.setItem(itm.getSlot(), itm.getItemStack());
				}
				
				return inv;
			}
		};
	}
	
}
