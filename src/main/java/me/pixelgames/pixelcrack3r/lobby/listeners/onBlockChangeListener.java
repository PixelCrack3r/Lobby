package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;

public class onBlockChangeListener implements Listener {

	@EventHandler
	public void onBlockChange_FORM(BlockFormEvent e) {
		if(!Config.getConfig().getBoolean("settings.world.block-change")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockChange_FADE(BlockFadeEvent e) {
		if(!Config.getConfig().getBoolean("settings.world.block-change")) {
			e.setCancelled(true);
		}
	}
	
}
