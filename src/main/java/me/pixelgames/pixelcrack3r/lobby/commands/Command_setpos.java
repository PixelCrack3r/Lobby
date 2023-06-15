package me.pixelgames.pixelcrack3r.lobby.commands;

import me.pixelgames.pixelcrack3r.lobby.managers.LocationManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;

public class Command_setpos implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		
		if(player == null) {
			sender.sendMessage(Main.getPrefix() + "§cYou must be a ingame player!");
		} else {
			if(player.hasPermission("lobby.command.setpos")) {
				if(args.length == 1) {
					String name = args[0];
					
					LocationManager.getLocation(name.toLowerCase()).setPos(player.getLocation());
					player.sendMessage(Main.getPrefix() + "§7The new position of §e" + name.toUpperCase() + " §7is §6" + player.getLocation().getWorld().getName() + " " + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ());
				} else {
					player.sendMessage(Main.getPrefix() + "§cUsage: /setpos <name>");
				}
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.command-denied")).replaceAll("%prefix%", Main.getPrefix("")));
			}
		}
		return true;
	}
	
}
