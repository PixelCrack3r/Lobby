package me.pixelgames.pixelcrack3r.lobby.commands;

import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;

public class Command_vanish implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			PlayerManager player = PlayerManager.getPlayer((Player) sender);
			
			if(player.getPlayer().hasPermission("lobby.command.vanish")) {
				player.setVanished(!player.isVanished());
				if(player.isVanished()) {
					player.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.vanish-enabled")).replaceAll("%prefix%", Main.getPrefix("")));
				} else {
					player.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.vanish-disabled")).replaceAll("%prefix%", Main.getPrefix("")));	
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
