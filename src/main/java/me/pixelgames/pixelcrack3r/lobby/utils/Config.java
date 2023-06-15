package me.pixelgames.pixelcrack3r.lobby.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import gq.pixelgames.pixelcrack3r.database.MySQL;
import me.pixelgames.pixelcrack3r.lobby.enums.Weather;
import me.pixelgames.pixelcrack3r.lobby.main.Main;
import gq.pixelgames.pixelcrack3r.main.PixelGames;

public class Config {

	public static Weather weather;
	
	private static File cfgF = getFileType("config.yml");
	private static FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgF);

	private static File locF = getFileType("locations.yml");
	private static FileConfiguration locations = YamlConfiguration.loadConfiguration(locF);
	
	private static File invF = getFileType("inventory.yml");
	private static FileConfiguration inventory = YamlConfiguration.loadConfiguration(invF);
	
	private static File permF = getFileType("permissions.yml");
	private static FileConfiguration perm = YamlConfiguration.loadConfiguration(permF);
	
	private static File itmF = getFileType("items.yml");
	private static FileConfiguration items = YamlConfiguration.loadConfiguration(itmF);
	
	private static List<String> blackList = new ArrayList<String>();
	
	private static MySQL mySQLPRovider;
	
	private static File getFileType(String file) {
		return new File("plugins/" + Main.getPluginName(), file);
	}
	
	public static List<String> getCreatureBlackList() {
		return blackList;
	}
	
	public static FileConfiguration getConfig() {
		return cfg;
	}
	
	public static FileConfiguration getLocations() {
		return locations;
	}
	
	public static FileConfiguration getInventory() {
		return inventory;
	}
	
	public static FileConfiguration getPerm() {
		return perm;
	}
	
	public static FileConfiguration getItems() {
		return items;
	}
	
	public static void saveConfig() {
		try {
			cfg.save(cfgF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveLocations() {
		try {
			locations.save(locF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveInventory() {
		try {
			inventory.save(invF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveItems() {
		getItems().options().copyDefaults(true);
		getItems().options().copyHeader(true);
		
		try {
			items.save(itmF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void savePerm() {
		try {
			perm.save(permF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void reload() {
		locations = YamlConfiguration.loadConfiguration(locF);
		cfg = YamlConfiguration.loadConfiguration(cfgF);
		inventory = YamlConfiguration.loadConfiguration(invF);
		perm = YamlConfiguration.loadConfiguration(permF);
		items = YamlConfiguration.loadConfiguration(itmF);
		
		try {
			Thread.sleep(100L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Main.setPrefix(ChatColor.translateAlternateColorCodes('&', cfg.getString("settings.messages.prefix")));
	}
		
	public static void registerTags() {
		weather = Weather.valueOf(getConfig().getString("settings.world.weather"));
		
		blackList = getConfig().getStringList("settings.entity.blacklist");
		
		String host = getConfig().getString("settings.mysql.host");
		String user = getConfig().getString("settings.mysql.user");
		String password = getConfig().getString("settings.mysql.password");
		String database = getConfig().getString("settings.mysql.databse");
		
		if(Config.getConfig().getBoolean("settings.mysql.enabled")) setMySQLPRovider(Config.getConfig().getBoolean("settings.mysql.internal") ? PixelGames.getDefaultMySQL() : new MySQL(host, database, user, password));
	}
	
	public static void addLocation(String name, Location location) {
		getLocations().set("locations." + name + ".X", location.getBlockX());
		getLocations().set("locations." + name + ".Y", location.getBlockY());
		getLocations().set("locations." + name + ".Z", location.getBlockZ());
		
		getLocations().set("locations." + name + ".Yaw", location.getYaw());
		getLocations().set("locations." + name + ".Pitch", location.getPitch());
		
		getLocations().set("locations." + name + ".World", location.getWorld().getName());
		
		saveLocations();
	}

	public static MySQL getMySQLPRovider() {
		return mySQLPRovider;
	}

	public static void setMySQLPRovider(MySQL mySQLPRovider) {
		Config.mySQLPRovider = mySQLPRovider;
	}
	
}
