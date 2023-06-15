package me.pixelgames.pixelcrack3r.lobby.main;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.pixelgames.pixelcrack3r.lobby.listeners.onPlayerQuitListener;
import me.pixelgames.pixelcrack3r.lobby.managers.InventoryManager;
import me.pixelgames.pixelcrack3r.lobby.managers.ItemManager;
import me.pixelgames.pixelcrack3r.lobby.managers.ScoreboardManager;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import me.pixelgames.pixelcrack3r.lobby.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.pixelgames.pixelcrack3r.lobby.commands.Command_additem;
import me.pixelgames.pixelcrack3r.lobby.commands.Command_build;
import me.pixelgames.pixelcrack3r.lobby.commands.Command_editItem;
import me.pixelgames.pixelcrack3r.lobby.commands.Command_fly;
import me.pixelgames.pixelcrack3r.lobby.commands.Command_lobby;
import me.pixelgames.pixelcrack3r.lobby.commands.Command_setpos;
import me.pixelgames.pixelcrack3r.lobby.commands.Command_spawn;
import me.pixelgames.pixelcrack3r.lobby.commands.Command_vanish;
import me.pixelgames.pixelcrack3r.lobby.listeners.onAsyncPlayerChatListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onBlockBreakListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onBlockChangeListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onBlockExplodeListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onEntityDamageListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onFoodLevelChangeListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onInventoryClickListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onItemDropListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onMobSpawnListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onPickupItemListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onPlayerAchievementAwardedListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onPlayerInteractListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onPlayerJoinListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onPlayerMoveListener;
import me.pixelgames.pixelcrack3r.lobby.listeners.onPlayerToggleFlightListener;

public class Main extends JavaPlugin {
	
	private static String prefix = "§7[§9Lobby§7]";
	
	private static PluginDescriptionFile description;
	
	private static Main plugin;
	
	private static Timer timer;
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "§aEnabling §9" + super.getDescription().getName() + " §av-" + super.getDescription().getVersion() + " §7by PixelCrack3r");
		load();
		loadConfig();
		registerListeners();
		registerCommands();
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "§aEnabling Successful");
		
		timer = new Timer(Bukkit.getWorld(Config.getConfig().getString("settings.world.name")));
		timer.start();
	}
	
	public void load() {
		plugin = this;
		
		setDescription(super.getDescription());
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		ScoreboardManager.createNewManager(this, true);
	}
	
	public void registerListeners() {
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "§7loading §aListeners");
		
		Bukkit.getPluginManager().registerEvents(new onAsyncPlayerChatListener(), this);
		Bukkit.getPluginManager().registerEvents(new onBlockBreakListener(), this);
		Bukkit.getPluginManager().registerEvents(new onEntityDamageListener(), this);
		Bukkit.getPluginManager().registerEvents(new onFoodLevelChangeListener(), this);
		Bukkit.getPluginManager().registerEvents(new onInventoryClickListener(), this);
		Bukkit.getPluginManager().registerEvents(new onItemDropListener(), this);
		Bukkit.getPluginManager().registerEvents(new onMobSpawnListener(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerAchievementAwardedListener(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerInteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerQuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerToggleFlightListener(), this);
		Bukkit.getPluginManager().registerEvents(new onPickupItemListener(), this);
		Bukkit.getPluginManager().registerEvents(new onBlockExplodeListener(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerMoveListener(), this);
		Bukkit.getPluginManager().registerEvents(new onBlockChangeListener(), this);
	}
	
	public void registerCommands() {
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "§7loading §aCommands");
		
		this.getCommand("lobby").setExecutor(new Command_lobby());
		this.getCommand("setpos").setExecutor(new Command_setpos());
		this.getCommand("build").setExecutor(new Command_build());
		this.getCommand("spawn").setExecutor(new Command_spawn());
		this.getCommand("vanish").setExecutor(new Command_vanish());
		this.getCommand("v").setExecutor(new Command_vanish());
		this.getCommand("fly").setExecutor(new Command_fly());
		this.getCommand("edititem").setExecutor(new Command_editItem());
		this.getCommand("additem").setExecutor(new Command_additem());
	}
	
	public void loadConfig() {
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "§7loading §aconfig.yml");
		
		List<String> bl = new ArrayList<String>();
		List<String> gDescription = new ArrayList<String>();
		List<String> lDescription = new ArrayList<String>();
		List<String> nDescription = new ArrayList<String>();
		List<String> pDescription = new ArrayList<String>();
		List<String> scoreboardContent = new ArrayList<String>();
		
		bl.add(EntityType.ZOMBIE.name());
		bl.add(EntityType.CREEPER.name());
		bl.add(EntityType.SKELETON.name());
		bl.add(EntityType.WITCH.name());
		
		nDescription.add("&8&m-----------------");
		nDescription.add("&7Teleports your to the");
		nDescription.add("&7Games of this Server.");
		nDescription.add("&8&m-----------------");
		
		pDescription.add("&8&m-----------------");
		pDescription.add("&7Hide some player and don't");
		pDescription.add("&7let them bother you.");
		pDescription.add("&8&m-----------------");
		
		lDescription.add("&8&m-----------------");
		lDescription.add("&7Switch the Lobby");
		lDescription.add("&7if this is enabled.");
		lDescription.add("&8&m-----------------");
		
		scoreboardContent.add("&c");
		scoreboardContent.add("Your Rank:");
		scoreboardContent.add("%rank%");
		scoreboardContent.add("&e");
		scoreboardContent.add("Your Coins:");
		scoreboardContent.add("%coins%");
		scoreboardContent.add("&f");
		scoreboardContent.add("Our TeamSpeak:");
		scoreboardContent.add("YourServer.de");
		
		Config.getConfig().addDefault("settings.mysql.enabled", false);
		Config.getConfig().addDefault("settings.mysql.internal", false);
		Config.getConfig().addDefault("settings.mysql.host", "localhost");
		Config.getConfig().addDefault("settings.mysql.user", "root");
		Config.getConfig().addDefault("settings.mysql.password", "iAmSecure");
		Config.getConfig().addDefault("settings.mysql.database", "lobby");
		
		Config.getConfig().addDefault("settings.world.name", "world");
		Config.getConfig().addDefault("settings.world.weather", "CLEAR");
		Config.getConfig().addDefault("settings.world.biom", Biome.FOREST.name());
		Config.getConfig().addDefault("settings.world.biomX", 500);
		Config.getConfig().addDefault("settings.world.biomZ", 500);
		Config.getConfig().addDefault("settings.world.day-time", "DAY");
		Config.getConfig().addDefault("settings.world.mob-spawning", true);
		Config.getConfig().addDefault("settings.world.monster-spawning", false);
		Config.getConfig().addDefault("settings.world.mob-damage", false);
		Config.getConfig().addDefault("settings.world.soil-break", false);
		Config.getConfig().addDefault("settings.world.block-break", false);
		Config.getConfig().addDefault("settings.world.block-place", false);
		Config.getConfig().addDefault("settings.world.block-explosions", false);
		Config.getConfig().addDefault("settings.world.block-change", false);
		
		Config.getConfig().addDefault("settings.inventory.navigator.enabled", true);
		Config.getConfig().addDefault("settings.inventory.navigator.material", Material.COMPASS.name());
		Config.getConfig().addDefault("settings.inventory.navigator.name", "&6&lNavigator");
		Config.getConfig().addDefault("settings.inventory.navigator.description", nDescription);
		Config.getConfig().addDefault("settings.inventory.navigator.slot", 1);
		
		Config.getConfig().addDefault("settings.inventory.custom.enabled", true);
		Config.getConfig().addDefault("settings.inventory.custom.material", Material.ENDER_CHEST.name());
		Config.getConfig().addDefault("settings.inventory.custom.name", "&6YourName");
		Config.getConfig().addDefault("settings.inventory.custom.description", gDescription);
		Config.getConfig().addDefault("settings.inventory.custom.slot", 2);
		Config.getConfig().addDefault("settings.inventory.custom.command", "your command");
		
		Config.getConfig().addDefault("settings.inventory.lobby-switcher.enabled", true);
		Config.getConfig().addDefault("settings.inventory.lobby-switcher.material", Material.NETHER_STAR.name());
		Config.getConfig().addDefault("settings.inventory.lobby-switcher.name", "&bLobby-Switcher");
		Config.getConfig().addDefault("settings.inventory.lobby-switcher.description", lDescription);
		Config.getConfig().addDefault("settings.inventory.lobby-switcher.slot", 5);
		
		Config.getConfig().addDefault("settings.inventory.player-hider.enabled", true);
		Config.getConfig().addDefault("settings.inventory.player-hider.material", Material.BLAZE_ROD.name());
		Config.getConfig().addDefault("settings.inventory.player-hider.name", "&6&lPlayer-Hider");
		Config.getConfig().addDefault("settings.inventory.player-hider.description", pDescription);
		Config.getConfig().addDefault("settings.inventory.player-hider.slot", 8);
		
		Config.getConfig().addDefault("settings.inventory.gadgets.enabled", true);
		Config.getConfig().addDefault("settings.inventory.gadgets.material", Material.CHEST.name());
		Config.getConfig().addDefault("settings.inventory.gadgets.name", "&6Gadgets");
		Config.getConfig().addDefault("settings.inventory.gadgets.description", gDescription);
		Config.getConfig().addDefault("settings.inventory.gadgets.slot", 9);
		
		Config.getConfig().addDefault("settings.player.damage", false);
		Config.getConfig().addDefault("settings.player.double-jump", false);
		Config.getConfig().addDefault("settings.player.double-jump-block", false);
		Config.getConfig().addDefault("settings.player.food-level-change", false);
		Config.getConfig().addDefault("settings.player.level", "YEAR");
		Config.getConfig().addDefault("settings.player.need-chat-perm", true);
		Config.getConfig().addDefault("settings.player.chat-permissions", "lobby.player.chat");
		Config.getConfig().addDefault("settings.player.pvp", false);
		Config.getConfig().addDefault("settings.player.gamemode", "ADVENTURE");
		Config.getConfig().addDefault("settings.player.achievment-award", false);
		
		Config.getConfig().addDefault("settings.entity.pve", false);
		Config.getConfig().addDefault("settings.entity.ai", true);
		Config.getConfig().addDefault("settings.entity.blacklist", bl);
		
		Config.getConfig().addDefault("settings.join.title", false);
		Config.getConfig().addDefault("settings.join.action-bar", false);
		Config.getConfig().addDefault("settings.join.teleport", true);
		
		Config.getConfig().addDefault("settings.scoreboard.title", "&e&lYour&b&lServer&c.de");
		Config.getConfig().addDefault("settings.scoreboard.content", scoreboardContent);
		
		Config.getConfig().addDefault("settings.messages.prefix", "&7[&9Lobby&7]");
		Config.getConfig().addDefault("settings.messages.action-bar", "&cWelcome");
		Config.getConfig().addDefault("settings.messages.title", "&e&lYour&b&lServer");
		Config.getConfig().addDefault("settings.messages.subtitle", "&cWelcome on &e&lYour&b&lServer");
		Config.getConfig().addDefault("settings.messages.chat-denied", "%prefix% &cYou need a rank to write in the lobby.");
		Config.getConfig().addDefault("settings.messages.item-denied", "%prefix% &cYou do not have permission to interact with it.");
		Config.getConfig().addDefault("settings.messages.command-denied", "%prefix% &cYou do not have permission execute this command.");
		Config.getConfig().addDefault("settings.messages.join", "&7[&a+&7] %player%");
		Config.getConfig().addDefault("settings.messages.quit", "&7[&c-&7] %player%");
		Config.getConfig().addDefault("settings.messages.vanish-enabled", "%prefix% You are in &eVanish-Mode &7now.");
		Config.getConfig().addDefault("settings.messages.vanish-disabled", "%prefix% You are not in the &eVanish-Mode &7anymore.");
		Config.getConfig().addDefault("settings.messages.fly-enabled", "%prefix% You can &efly &7now.");
		Config.getConfig().addDefault("settings.messages.fly-disabled", "%prefix% You can not &efly &7anymore");
		
		Config.getConfig().addDefault("settings.server.updates.changebar", 20*10);
		Config.getConfig().addDefault("settings.server.updates.actionbar", "&9Message 1;&cMessage 2;&bMessage 3;&aMessage 3");
		
		Config.getConfig().addDefault("settings.plugin.dependencies.pex", false);
		Config.getConfig().addDefault("settings.plugin.dependencies.essentials", false);
		Config.getConfig().addDefault("settings.plugin.dependencies.cloudnet3", false);
		
		Config.getConfig().addDefault("settings.lobby.group.default", "Lobby");
		Config.getConfig().addDefault("settings.lobby.group.premium", "Premiumlobby");
		
		Config.getConfig().options().copyDefaults(true);
		Config.getConfig().options().copyHeader(true);
		
		Config.saveConfig();
		
		Config.getLocations().addDefault("locations.spawn.X", "-");
		Config.getLocations().addDefault("locations.spawn.Y", "-");
		Config.getLocations().addDefault("locations.spawn.Z", "-");
		Config.getLocations().addDefault("locations.spawn.Yaw", "-");
		Config.getLocations().addDefault("locations.spawn.Pitch", "-");
		Config.getLocations().addDefault("locations.spawn.World", "-");
	
		Config.getLocations().options().copyDefaults(true);
		Config.getLocations().options().copyHeader(true);
		
		Config.saveLocations();

		setPrefix(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.messages.prefix")));
		
		List<String> items = new ArrayList<String>();
		
		items.add("spawn");
		
		List<String> lobbies = new ArrayList<String>();
		
		lobbies.add("Lobby-1");
		
		List<String> plh = new ArrayList<String>();
		
		plh.add("Player");
		plh.add("Premium");
		plh.add("Team");
		
		InventoryManager.createNewInventory("navigator", "&6&lNavigator", 27, true, items, true);
		InventoryManager.createNewInventory("lobby-switcher", "&bLobby-Switcher", 9, true, lobbies, true);
		InventoryManager.createNewInventory("hider", "&6Player-Hider", 3, false, null, true);
		InventoryManager.createNewInventory("gadgets", "&6Gadgets", 9, true, Arrays.asList("effects"), true);
		
		InventoryManager.createNewInventory("effects", "&aEffects", 9, true, null, true);
		
		ItemManager.addItem("spawn", Material.BLAZE_POWDER, "&6&lSpawn", null, ItemManager.getSlotId(2, 5), null, true, true);
		ItemManager.addItem("effects", Material.FIREWORK, "&aGadgets", null, ItemManager.getSlotId(1, 1), "effects", true);
		
		ItemManager.addServer("Lobby-1", Material.SKULL_ITEM, "&6&lLobby-1", null, 0, "Lobby-1", true);
		
		Config.getInventory().options().copyDefaults(true);
		Config.getInventory().options().copyHeader(true);
		
		Config.saveInventory();
		
		
		List<String> groups = new ArrayList<String>();
		
		groups.add("Owner: &4Owner");
		groups.add("Player: &7Player");
		
		List<String> perms = new ArrayList<String>();
		
		perms.add("server.group.Owner: &4Owner");
		perms.add("server.group.Player: &7Player");
		
		Config.getPerm().options().header("The group-list needs a dependency plugin for player groups");
		
		Config.getPerm().addDefault("scoreboard.groups", groups);
		
		Config.getPerm().addDefault("scoreboard.perms", perms);
		
		Config.getPerm().options().copyDefaults(true);
		Config.getPerm().options().copyHeader(true);
		
		Config.savePerm();
		
		Config.registerTags();
	}
	
	public void removeMetadata(Entity entity, String key) {
		if(entity.hasMetadata(key)) entity.removeMetadata(key, this);
	}
	
	public void setMetadata(Entity entity, String key, Object object) {
		removeMetadata(entity, key);
		entity.setMetadata(key, new FixedMetadataValue(this, object));
	}
	
	public static void connect(Player player, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		try {
			out.writeUTF("Connect");
			
			out.writeUTF(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
	}
	
	public static String getPrefix() {
		return prefix + " ";
	}
	
	public static String getPrefix(String space) {
		return prefix + space;
	}
	
	public static void setPrefix(String prefix) {
		Main.prefix = prefix;
	}
	
	public static String getVersion() {
		return getPluginDescription().getVersion();
	}
	
	public static String getPluginName() {
		return getPluginDescription().getName();
	}
	
	public static Main getInstance() {
		return plugin;
	}

	public static PluginDescriptionFile getPluginDescription() {
		return description;
	}

	public static void setDescription(PluginDescriptionFile description) {
		Main.description = description;
	}

}
