package me.pixelgames.pixelcrack3r.lobby.listeners;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.pixelgames.pixelcrack3r.lobby.enums.HiddenType;
import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.managers.LocationManager;
import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import me.pixelgames.pixelcrack3r.lobby.utils.ActionBar;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import me.pixelgames.pixelcrack3r.lobby.utils.Title;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import gq.pixelgames.pixelcrack3r.configuration.MySQLConfiguration;
import net.md_5.bungee.api.ChatColor;

public class onPlayerJoinListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.join")).replaceAll("%player%", player.getName()));
		
		if(Config.getConfig().getBoolean("settings.join.teleport")) {
			if(Config.getLocations().isDouble("locations.spawn.Block-X") && Config.getLocations().isDouble("locations.spawn.Block-Y") && Config.getLocations().isDouble("locations.spawn.Block-Z")) {
				player.teleport(LocationManager.getLocation("spawn").getLocation());
				
			} else if(player.hasPermission("lobby.admin.setup")) {
				player.sendMessage(Main.getPrefix() + "Please set the §espawn §7location with: §6/setpos SPAWN");
			} else {
				player.kickPlayer("§cThe lobby setup is not completed!");
			}
			
		}
		
		if(Config.getConfig().getBoolean("settings.join.title")) {
			Title.createTitle(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.title")), ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.subtitle"))).send(player);
		}
		
		if(Config.getConfig().getBoolean("settings.join.action-bar")) {
			ActionBar.createActionBar(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.message.action-bar"))).send(player);
		}
		
		if(Config.getConfig().getBoolean("settings.mysql.enabled")) {
			PlayerManager playerManager = PlayerManager.getPlayer(player);
			MySQLConfiguration config = playerManager.getUserConfig();
			playerManager.setVanished(config.getBoolean("lobby.silent.vanished"));
			List<Effect> effects = new ArrayList<Effect>();
			String rs = config.get("lobby.gadgets.effects");
			if(rs != null) {
				for(String effect : rs.split(";")) if(effect != null && !effect.equals("NON")) effects.add(Effect.valueOf(effect));
				playerManager.setEffects(effects);
			}
			rs = config.get("lobby.silent.hidden");
			if(rs != null) {

				if(rs.equalsIgnoreCase("§aEveryone")) {
					playerManager.setHidden(HiddenType.EVERY, true, true);
				} else if(rs.equalsIgnoreCase("§6Only Choosen")) {
					playerManager.setHidden(HiddenType.CHOOSE, true, true);
				} else if(rs.equalsIgnoreCase("§cNobody")) {
					playerManager.setHidden(HiddenType.NOBODY, true, true);
				} else if(rs.equalsIgnoreCase("§cReset")) {
					playerManager.setHidden(HiddenType.NOBODY, true, true);
				} else if(rs.equalsIgnoreCase("§7Player")) {
					playerManager.setHidden(HiddenType.PLAYER, !playerManager.hasHidden(HiddenType.PLAYER), false);
				} else if(rs.equalsIgnoreCase("§5VIPs")) {
					playerManager.setHidden(HiddenType.PREMIUM, !playerManager.hasHidden(HiddenType.PREMIUM), false);
				} else if(rs.equalsIgnoreCase("§9Team")) {
					playerManager.setHidden(HiddenType.TEAM, !playerManager.hasHidden(HiddenType.TEAM), false);
				}
			}

		}
		
		int level = 0;
		
		player.getInventory().clear();
		PlayerManager.getPlayer(player).setJoinInventory();
		
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Calendar c = df.getCalendar();
		c.setTimeInMillis(System.currentTimeMillis());
		
		if(Config.getConfig().getString("settings.player.level").equalsIgnoreCase("YEAR")) {
			level = c.get(Calendar.YEAR);
		} else if(Config.getConfig().getString("settings.player.level").equalsIgnoreCase("MONTH")) {
			level = c.get(Calendar.MONTH);
		} else if(Config.getConfig().getString("settings.player.level").equalsIgnoreCase("DAYY")) {
			level = c.get(Calendar.DAY_OF_YEAR);
		} else if(Config.getConfig().getString("settings.player.level").equalsIgnoreCase("DAY")) {
			level = c.get(Calendar.DAY_OF_MONTH);
		} else {
			level = Integer.parseInt(Config.getConfig().getString("settings.player.level"));
		}
		
		player.setLevel(level);
		player.setFoodLevel(20);
		
		GameMode gamemode = GameMode.ADVENTURE;
		
		if(Config.getConfig().getString("settings.player.gamemode").equalsIgnoreCase("SURVIVAL")) {
			gamemode = GameMode.SURVIVAL;
		} else if(Config.getConfig().getString("settings.player.gamemode").equalsIgnoreCase("CREATIVE")) {
			gamemode = GameMode.CREATIVE;
		} else if(Config.getConfig().getString("settings.player.gamemode").equalsIgnoreCase("ADVENTURE")) {
			gamemode = GameMode.ADVENTURE;
		} else if(Config.getConfig().getString("settings.player.gamemode").equalsIgnoreCase("SPECTATOR")) {
			gamemode = GameMode.SPECTATOR;
		}
		
		player.setGameMode(gamemode);
		PlayerManager.getPlayer(player).loadDefaultScoreboard();
		
	}
	
}
