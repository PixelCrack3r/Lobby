package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class onItemDropListener implements Listener {
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		PlayerManager player = PlayerManager.getPlayer(e.getPlayer());
		
		if(!player.isInBuildMode()) {
			e.setCancelled(true);
		}
	}

}
