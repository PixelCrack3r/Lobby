package me.pixelgames.pixelcrack3r.lobby.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;

public class Command_lobby implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1 && args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl") && sender.hasPermission("lobby.command.reload")) {
			Config.reload();
			sender.sendMessage(Main.getPrefix() + "The §cFileConfiguration §7was §areloaded");
		} else if(!sender.hasPermission("lobby.reload")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.command-denied")).replaceAll("%prefix%", Main.getPrefix("")));
		}
		return true;
	}

}
