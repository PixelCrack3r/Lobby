package me.pixelgames.pixelcrack3r.lobby.commands;

import java.util.Arrays;
import java.util.List;

import me.pixelgames.pixelcrack3r.lobby.managers.InventoryManager;
import me.pixelgames.pixelcrack3r.lobby.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;

public class Command_additem implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		
		if(player != null) {
			if(player.hasPermission("lobby.command.additem")) {
				if(args.length > 4) {
					String name = ChatColor.translateAlternateColorCodes('&', args[0]);
					Material material = Material.getMaterial(args[1].toUpperCase());
					int slot = 0;
					List<String> description = Arrays.asList(ChatColor.translateAlternateColorCodes('&', args[3]));
					String inventoryName = args[4];
					
					InventoryManager inventory = InventoryManager.getInventorySlot(inventoryName);
					
					String loc = "";
					
					for(int i = 0; i < name.toCharArray().length; i++) {
						char c = name.charAt(i);
						if(c == '§') {
							continue;
						}
						
						if(i > 0) {
							if(name.charAt(i - 1) == '§') {
								continue;
							}
						}
						
						loc+=c;
					}
					
					try {
						slot = Integer.valueOf(args[2]);
					} catch (NumberFormatException e) {
						player.sendMessage(Main.getPrefix() + "The slot format §e" + args[2] + " §7is not a number!");
						return true;
					}
					
					if(material == null) {
						player.sendMessage(Main.getPrefix() + "The material §e" + args[1].toUpperCase() + " §7was not §cfound§7.");
						return true;
					}
					
					if(inventory.exists()) {
						inventory.addItem(loc);
					} else {
						player.sendMessage(Main.getPrefix(" ") + "§7The inventory §e" + inventoryName + " §7was not §cfound§7.");
						return true;
					}
					
					ItemManager.addItem(loc, material, name, description, slot, null, false);
					player.sendMessage(Main.getPrefix(" ") + "The item §e" + loc + " §7was added to the inventory §6" + inventoryName + "§7.");
				} else {
					player.sendMessage(Main.getPrefix(" ") + "§cUsage: §7/additem <name> <material> <slot> <desciption> <inventory>");
				}
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.command-denied")).replaceAll("%prefix%", Main.getPrefix("")));
			}
			
		} else {
			sender.sendMessage("§cOnly ingame players can use this command.");
		}
		return false;
	}

}
