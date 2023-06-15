package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

public class onPlayerAchievementAwardedListener implements Listener {
	
	@EventHandler
	public void onAchievementGet(PlayerAchievementAwardedEvent e) {
		if(!Config.getConfig().getBoolean("settings.player.achievment-award")) {
			e.setCancelled(true);
		}
	}

}
