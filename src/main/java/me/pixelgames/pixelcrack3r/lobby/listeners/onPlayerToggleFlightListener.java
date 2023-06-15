package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class onPlayerToggleFlightListener implements Listener {
	
	@EventHandler
	public void onPlayerJump(PlayerToggleFlightEvent e) {
		Player player = e.getPlayer();
		
		if(PlayerManager.getPlayer(player).isFlying()) {
			return;
		}
	
		if(Config.getConfig().getBoolean("settings.player.double-jump")) {
			if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
				e.setCancelled(true);
				player.setAllowFlight(false);
				player.setFlying(false);
				player.setVelocity(player.getLocation().getDirection().multiply(2).add(new Vector(0, .2, 0)));
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		
		if(PlayerManager.getPlayer(player).isFlying()) {
			return;
		}
		
		if(Config.getConfig().getBoolean("settings.player.double-jump")) {
			if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
				if(player.getLocation().add(0, -1, 0).getBlock().getType() != Material.AIR) {
					player.setAllowFlight(true);
					player.setFlying(false);
				}
				
			}
			
		}
		
	}
	

}
