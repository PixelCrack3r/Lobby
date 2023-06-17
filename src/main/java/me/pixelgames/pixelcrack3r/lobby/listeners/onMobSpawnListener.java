package me.pixelgames.pixelcrack3r.lobby.listeners;

import java.util.List;

import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class onMobSpawnListener implements Listener {

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {
		if(!Config.getConfig().getBoolean("settings.world.mob-spawning")) {
			e.setCancelled(true);
		} else if(!Config.getConfig().getBoolean("settings.world.monster-spawning")) {
			List<String> blacklist = Config.getCreatureBlackList();
			
			if(blacklist.contains(e.getEntityType().name()) || blacklist.contains(String.valueOf(e.getEntityType().getTypeId()))) {
				e.setCancelled(true);
			}
		}
		
	}

}
