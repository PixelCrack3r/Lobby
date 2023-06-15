package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

public class onBlockExplodeListener implements Listener {
	
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent e) {
		if(!Config.getConfig().getBoolean("settings.world.block-explosions")) {
			e.blockList().clear();
			e.setCancelled(true);
		}
	}

}
