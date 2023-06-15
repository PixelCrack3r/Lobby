package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class onEntityDamageListener implements Listener {
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		Player player = null;
		if(e.getEntity() instanceof Player) {
			player = (Player) e.getEntity();
		}
		
		if(player == null && Config.getConfig().getBoolean("settings.world.mob-damage")) {
			e.setCancelled(false);
			e.setDamage(e.getDamage());
			return;
		}
		
		if(player != null) {
			PlayerManager t1 = PlayerManager.getPlayer(player);
			
			if(t1.isInBuildMode()) {
				return;
			}
		}
		
		if(player != null && !Config.getConfig().getBoolean("settings.player.damage")) {
			e.setCancelled(true);
			e.setDamage(0);
		}
	
		if(player == null && !Config.getConfig().getBoolean("settings.world.mob-damage")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		Player player = null;
		
		if(e.getEntity() instanceof Player) {
			player = (Player) e.getEntity();
		}
		
		if(player != null) {
			PlayerManager t1 = PlayerManager.getPlayer(player);
			
			if(t1.isInBuildMode()) {
				return;
			}
		}
		
		Player attacker = null;
		if(e.getDamager() instanceof Player) {
			attacker = (Player) e.getDamager();
		}
	
		if(player == null && Config.getConfig().getBoolean("settings.world.mob-damage")) {
			e.setCancelled(false);
			e.setDamage(e.getDamage());
			return;
		}
		
		if(player != null && attacker != null) {
			PlayerManager t1 = PlayerManager.getPlayer(player);
			PlayerManager t2 = PlayerManager.getPlayer(attacker);
			
			if(t2.isInBuildMode() && t1.isInBuildMode()) {
				return;
			}
		}
		
		if(player != null && !Config.getConfig().getBoolean("settings.player.damage")) {
			e.setCancelled(true);
			e.setDamage(0);
		}
		
		if(attacker != null && player != null && !Config.getConfig().getBoolean("settings.player.pvp")) {
			e.setCancelled(true);
			e.setDamage(0);
		} else if(player == null && attacker == null && !Config.getConfig().getBoolean("settings.world.mob-damage")) {
			e.setCancelled(true);
			e.setDamage(0);
		}
		
		if(player == null && !Config.getConfig().getBoolean("settings.entity.pve")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByBlock(EntityDamageByBlockEvent e) {
		Player player = null;
		if(e.getEntity() instanceof Player) {
			player = (Player) e.getEntity();
		}
		
		if(player == null && Config.getConfig().getBoolean("settings.world.mob-damage")) {
			e.setCancelled(false);
			e.setDamage(e.getDamage());
			return;
		}
		
		if(player != null) {
			PlayerManager t1 = PlayerManager.getPlayer(player);
			
			if(t1.isInBuildMode()) {
				return;
			}
		}
		
		if(player != null && !Config.getConfig().getBoolean("settings.player.damage")) {
			e.setCancelled(true);
			e.setDamage(0);
		}
		
	}

}
