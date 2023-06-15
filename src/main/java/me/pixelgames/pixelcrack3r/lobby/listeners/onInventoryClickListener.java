package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.managers.InventoryManager;
import me.pixelgames.pixelcrack3r.lobby.managers.ItemManager;
import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class onInventoryClickListener implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		PlayerManager team = PlayerManager.getPlayer(player);
		
		if(!team.isInBuildMode()) {
			e.setCancelled(true);
		}
		
		Inventory inv = e.getInventory();
		ItemStack itm = null;
		if(e.getCurrentItem() != null) {
			itm = e.getCurrentItem();
		}
		
		InventoryManager guiNavigator = InventoryManager.getInventorySlot("navigator");
		InventoryManager guiSwitcher = InventoryManager.getInventorySlot("lobby-switcher");
		InventoryManager guiHider = InventoryManager.getInventorySlot("hider");
		InventoryManager guiGadgets = InventoryManager.getInventorySlot("gadgets");
		
		if(itm != null && inv.getName().equalsIgnoreCase(guiNavigator.getHeadline())) {
			ItemManager item = ItemManager.getItemSlot(guiNavigator.getItemByName(itm.getItemMeta().getDisplayName()));
			
			if(item.exists()) {
				if(item.needPermissions() && !player.hasPermission(item.getPermission())) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.item-denied")).replaceAll("%prefix%", Main.getPrefix("")));
					return;
				}
				
				if(item.hasCommand()) {
					player.chat(item.getCommand());
				} else if(item.hasServer()) {
					PlayerManager.getPlayer(player).connect(item.getServer());
				} else if(item.hasInventory()) {
					PlayerManager.getPlayer(player).displayGuiScreen(InventoryManager.getInventorySlot(item.getInventory()).createCustom());
				} else {
					if(item.hasValidLocation()) {
						player.teleport(item.getLocation());
						player.setCompassTarget(item.getLocation());
					} else {
						player.sendMessage(Main.getPrefix() + "Please validate the §eLocation §7of " + item.getName());
					}
				}
				
			}
			
		} else if(itm != null && inv.getName().equalsIgnoreCase(guiSwitcher.getHeadline())) {
			ItemManager item = ItemManager.getItemSlot(guiSwitcher.getItemByName(itm.getItemMeta().getDisplayName()));
			
			if(item.exists()) {
				if(item.needPermissions() && !player.hasPermission(item.getPermission())) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.item-denied")).replaceAll("%prefix%", Main.getPrefix("")));
					return;
				}
				
				if(item.hasCommand()) {
					player.chat(item.getCommand());
				} else if(item.hasServer()) {
					PlayerManager.getPlayer(player).connect(item.getServer());
				} else if(item.hasInventory()) {
					PlayerManager.getPlayer(player).displayGuiScreen(InventoryManager.getInventorySlot(item.getInventory()).createCustom());
				} else {
					if(item.hasValidLocation()) {
						player.teleport(item.getLocation());
						player.setCompassTarget(item.getLocation());
					} else {
						player.sendMessage(Main.getPrefix() + "Please validate the §eLocation §7of " + item.getName());
					}
				}
				
			}
			
		} else if(itm != null && inv.getName().equalsIgnoreCase(guiHider.getHeadline())) {
			PlayerManager manager = PlayerManager.getPlayer(player);
		
			manager.getCurrentScreen().onClick(itm);
			manager.getCurrentScreen().update();
			
		} else if(itm != null && itm.hasItemMeta() && inv.getName().equalsIgnoreCase(guiGadgets.getHeadline())) {
			ItemManager item = ItemManager.getItemSlot(guiGadgets.getItemByName(itm.getItemMeta().getDisplayName()));
			
			if(!item.exists()) {
				return;
			}
			
			if(item.needPermissions() && !player.hasPermission(item.getPermission())) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.item-denied")).replaceAll("%prefix%", Main.getPrefix("")));
				return;
			}
			
			if(item.hasCommand()) {
				player.chat(item.getCommand());
			} else if(item.hasServer()) {
				PlayerManager.getPlayer(player).connect(item.getServer());
			} else if(item.hasInventory()) {
				PlayerManager.getPlayer(player).displayGuiScreen(InventoryManager.getInventorySlot(item.getInventory()).createCustom());
			} else {
				if(item.hasValidLocation()) {
					player.teleport(item.getLocation());
					player.setCompassTarget(item.getLocation());
				} else {
					player.sendMessage(Main.getPrefix() + "Please validate the §eLocation §7of " + item.getName());
				}
			}
			
		} else if(itm != null && itm.getItemMeta() != null && ItemManager.getAsyncItemSlot(itm.getItemMeta().getDisplayName()).exists()) {
			ItemManager item = ItemManager.getAsyncItemSlot(itm.getItemMeta().getDisplayName());
			
			if(item.needPermissions() && !player.hasPermission(item.getPermission())) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.item-denied")).replaceAll("%prefix%", Main.getPrefix("")));
				return;
			}
			
			if(item.hasCommand()) {
				player.chat(item.getCommand());
			} else if(item.hasServer()) {
				PlayerManager.getPlayer(player).connect(item.getServer());
			} else if(item.hasInventory()) {
				PlayerManager.getPlayer(player).displayGuiScreen(InventoryManager.getInventorySlot(item.getInventory()).createCustom());
			} else if(item.hasEffects()) {
				PlayerManager.getPlayer(player).setEffects(item.getEffects());
			} else {
				if(item.hasValidLocation()) {
					player.teleport(item.getLocation());
					player.setCompassTarget(item.getLocation());
				} else {
					player.sendMessage(Main.getPrefix() + "Please validate the §eLocation §7of " + item.getName());
				}
			}
		}
		
	}

}