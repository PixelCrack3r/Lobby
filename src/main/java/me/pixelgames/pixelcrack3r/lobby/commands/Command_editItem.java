package me.pixelgames.pixelcrack3r.lobby.commands;

import me.pixelgames.pixelcrack3r.lobby.managers.ItemManager;
import me.pixelgames.pixelcrack3r.lobby.managers.LocationManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;

public class Command_editItem implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		
		if(player != null) {
			if(player.hasPermission("lobby.command.edititem")) {
				if(args.length > 2) {
					String name = args[0];
					ItemManager itm = ItemManager.getItemSlot(name);
					if(!itm.exists()) {
						player.sendMessage(Main.getPrefix() + "§7The item §e" + name + " §7does not exists.");
						return true;
					}
					
					if(args[1].equalsIgnoreCase("location")) {
						LocationManager location = LocationManager.getLocation(args[2]);
						
						if(location.isValid()) {
							itm.editPos(args[2]);
							player.sendMessage(Main.getPrefix() + "§7The new position of §e" + name + " §7is §6" + location.toString());
						} else {
							player.sendMessage(Main.getPrefix() + "§7The location §e" + args[2] + " §7is invaled.");
						}
					} else {
						itm.setValue(args[1].toLowerCase(), ChatColor.translateAlternateColorCodes('&', args[2]));
						player.sendMessage(Main.getPrefix() + "§7The new value of §e" + args[1].toLowerCase() + " §7is §6" + ChatColor.translateAlternateColorCodes('&', args[2]));
					}
					itm.save();
				} else {
					player.sendMessage(Main.getPrefix() + "§cUsage: §7/edititem <name> <section> <value>");
				}
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.command-denied")).replaceAll("%prefix%", Main.getPrefix("")));
			}
		} else {
			sender.sendMessage("§cOnly ingame players can use this command.");
		}
		return true;
	}
	
}
