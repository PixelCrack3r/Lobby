package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.gui.GuiGadgets;
import me.pixelgames.pixelcrack3r.lobby.gui.GuiLobbySwitcher;
import me.pixelgames.pixelcrack3r.lobby.gui.GuiNavigator;
import me.pixelgames.pixelcrack3r.lobby.gui.GuiPlayerHider;
import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class onPlayerInteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		//SOIL BREAK
		if(!Config.getConfig().getBoolean("settings.world.soil-break")) {
			if(e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL) {
				e.setCancelled(true);
			}
		}
		
		//BLOCK BREAK
		if(!Config.getConfig().getBoolean("settings.world.block-break")) {
			if(!PlayerManager.getPlayer(e.getPlayer()).isInBuildMode() && e.getAction() == Action.LEFT_CLICK_BLOCK) {
				e.setCancelled(true);
			}
		}
		
		//Inventory
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = e.getItem();
			
			PlayerManager player = PlayerManager.getPlayer(e.getPlayer());
			
			if(!player.isInBuildMode() && !Config.getConfig().getBoolean("settings.world.block-place")) {
				e.setCancelled(true);
			}
			
			if(item != null && item.hasItemMeta()) {
				if(Config.getConfig().getBoolean("settings.inventory.navigator.enabled") && item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.inventory.navigator.name")))) {
					player.displayGuiScreen(new GuiNavigator());
				} else if(Config.getConfig().getBoolean("settings.inventory.lobby-switcher.enabled") && item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.inventory.lobby-switcher.name")))) {
					player.displayGuiScreen(new GuiLobbySwitcher());
				} else if(Config.getConfig().getBoolean("settings.inventory.player-hider.enabled") && item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.inventory.player-hider.name")))) {
					player.displayGuiScreen(new GuiPlayerHider(e.getPlayer()));
				} else if(Config.getConfig().getBoolean("settings.inventory.custom.enabled") && item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.inventory.custom.name")))) {
					player.getPlayer().chat(Config.getConfig().getString("settings.inventory.custom.command"));
				} else if(Config.getConfig().getBoolean("settings.inventory.gadgets.enabled") && item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.inventory.gadgets.name")))) {
					player.displayGuiScreen(new GuiGadgets());
				}
				
			}
			
		}
		
	}
	
}