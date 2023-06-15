package me.pixelgames.pixelcrack3r.lobby.commands;

import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;

public class Command_fly implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			PlayerManager player = PlayerManager.getPlayer((Player) sender);
			
			if(player.getPlayer().hasPermission("lobby.command.fly")) {
				if(player.getPlayer().hasPermission("lobby.command.fly.other") && args.length >= 1) {
					Player t = Bukkit.getPlayer(args[0]);
					
					if(t == null) {
						return true;
					}
					
					
				} else {
					player.setFlying(!player.isFlying());
					player.getPlayer().setAllowFlight(player.isFlying());
					player.getPlayer().setFlying(player.getPlayer().getAllowFlight());
					if(player.getPlayer().getAllowFlight()) {
						player.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.fly-enabled")).replaceAll("%prefix%", Main.getPrefix("")));
					} else {
						player.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.fly-disabled")).replaceAll("%prefix%", Main.getPrefix("")));
					}
				}
			} else {
				player.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.command-denied")).replaceAll("%prefix%", Main.getPrefix("")));
			}
		} else {
			sender.sendMessage(Main.getPrefix() + "§cYou must be a ingame player!");
		}
		return true;
	}

}
