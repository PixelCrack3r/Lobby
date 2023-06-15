package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import net.md_5.bungee.api.ChatColor;

public class onPlayerQuitListener implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.quit")).replaceAll("%player%", e.getPlayer().getName()));
		PlayerManager.getPlayer(e.getPlayer()).setBuildMode(false);
		PlayerManager.getPlayer(e.getPlayer()).setFlying(false);
	}

}
