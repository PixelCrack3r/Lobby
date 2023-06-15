package me.pixelgames.pixelcrack3r.lobby.commands;

import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;

public class Command_build implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		
		if(player == null) {
			sender.sendMessage(Main.getPrefix() + "§cYou must be a ingame player!");
		} else {
			if(player.hasPermission("lobby.command.build")) {
				if(args.length == 0) {
					if(PlayerManager.getPlayer(player).isInBuildMode()) {
						PlayerManager.getPlayer(player).setBuildMode(false);
						player.sendMessage(Main.getPrefix() + "You have §cdeactivated §7the §eBuild-Mode.");
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
						player.getInventory().clear();
						PlayerManager.getPlayer(player).setJoinInventory();
					} else {
						PlayerManager.getPlayer(player).setBuildMode(true);
						player.sendMessage(Main.getPrefix() + "You have §aactivated §7the §eBuild-Mode.");
						player.playSound(player.getLocation(), Sound.LEVEL_UP, 20, 1);
						player.setGameMode(GameMode.CREATIVE);
						player.getInventory().clear();
					}
				} else {
					Player target = Bukkit.getPlayer(args[0]);
					
					if(target == null) {
						player.sendMessage(Main.getPrefix() + "The player §e" + args[0] + " §7is §coffline§7.");
					} else {
						if(PlayerManager.getPlayer(target).isInBuildMode()) {
							PlayerManager.getPlayer(target).setBuildMode(false);
							player.sendMessage(Main.getPrefix() + "You have §cdeactivated §7the §eBuild-Mode.");
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
							
							target.setGameMode(gamemode);
							target.getInventory().clear();
							PlayerManager.getPlayer(target).setJoinInventory();
						} else {
							PlayerManager.getPlayer(target).setBuildMode(true);
							player.sendMessage(Main.getPrefix() + "You have §aactivated §7the §eBuild-Mode.");
							target.playSound(target.getLocation(), Sound.LEVEL_UP, 20, 1);
							target.setGameMode(GameMode.CREATIVE);
							target.getInventory().clear();
						}
					}
				}
				
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.command-denied")).replaceAll("%prefix%", Main.getPrefix("")));
			}
			
		}
		return true;
	}

}
