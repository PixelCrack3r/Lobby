package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class onFoodLevelChangeListener implements Listener {
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		if(!Config.getConfig().getBoolean("settings.player.food-level-change")) {
			e.setCancelled(true);
		}
		
	}

}
