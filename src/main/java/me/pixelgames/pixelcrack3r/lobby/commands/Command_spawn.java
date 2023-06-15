package me.pixelgames.pixelcrack3r.lobby.commands;

import me.pixelgames.pixelcrack3r.lobby.managers.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;

public class Command_spawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		
		if(player != null && args.length == 0) {
			if(Config.getLocations().isDouble("locations.spawn.Block-X") && Config.getLocations().isDouble("locations.spawn.Block-Y") && Config.getLocations().isDouble("locations.spawn.Block-Z")) {
				player.teleport(LocationManager.getLocation("spawn").getLocation());
				
			} else if(player.hasPermission("lobby.admin.setup")) {
				player.sendMessage(Main.getPrefix() + "Please set the §espawn §7location with: §6/setpos SPAWN");
			} else {
				player.kickPlayer("§cThe lobby setup is not completed!");
			}
		} else {
			sender.sendMessage("§cOnly ingame players can use this command.");
		}
		return true;
	}

}
