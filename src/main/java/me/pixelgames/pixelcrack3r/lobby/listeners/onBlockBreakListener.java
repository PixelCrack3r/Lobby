package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class onBlockBreakListener implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		PlayerManager team = PlayerManager.getPlayer(e.getPlayer());
		if(!team.isInBuildMode()) {
			if(!Config.getConfig().getBoolean("settings.world.block-break")) {
				e.setCancelled(true);
			}
			
		}
		
	}

}
