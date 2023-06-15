package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onAsyncPlayerChatListener implements Listener {
	
	@EventHandler
	public void onAsnycPlayerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		
		if(Config.getConfig().getBoolean("settings.player.need-chat-perm")) {
			if(!player.hasPermission(Config.getConfig().getString("settings.player.chat-permissions"))) {
				e.setCancelled(true);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.chat-denied").replaceAll("%prefix%", Main.getPrefix(""))));
			}
			
		}
		
	}

}
