package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.managers.LocationManager;
import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class onPlayerMoveListener implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		PlayerManager player = PlayerManager.getPlayer(e.getPlayer());
		
		if(Config.getConfig().getBoolean("settings.player.double-jump-block") && !player.isFlying()) {
			if(player.getPlayer().getLocation().getBlock().getType() == Material.IRON_PLATE) {
				e.setCancelled(false);
				player.getPlayer().setAllowFlight(false);
				player.setFlying(false);
				player.getPlayer().setVelocity(player.getPlayer().getLocation().getDirection().multiply(2).add(new Vector(0, .2, 0)));
			}
		}
		
		if(player.getPlayer().getLocation().getBlockY() <= -200) {
			player.getPlayer().teleport(LocationManager.getLocation("spawn").getLocation());
		}
		
		if(player.getEffects() != null) {
			for(Effect effect : player.getEffects()) {
				
				if(effect == null) {
					continue;
				}
				player.getPlayer().getWorld().playEffect(e.getFrom().clone().subtract(0.0, 0.5, 0.0), effect, 3);
			}
			
		}
		
	}

}
