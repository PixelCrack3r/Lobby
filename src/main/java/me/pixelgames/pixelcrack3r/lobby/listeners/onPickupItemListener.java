package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class onPickupItemListener implements Listener {
	
	@EventHandler
	public void onPickupItem(PlayerPickupItemEvent e) {
		PlayerManager player = PlayerManager.getPlayer(e.getPlayer());
		
		if(!player.isInBuildMode()) {
			e.setCancelled(true);
		}
	}

}
