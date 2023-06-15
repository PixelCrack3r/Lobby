package me.pixelgames.pixelcrack3r.lobby.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import gq.pixelgames.pixelcrack3r.configuration.MySQLConfiguration;
import me.pixelgames.pixelcrack3r.lobby.enums.Group;
import me.pixelgames.pixelcrack3r.lobby.enums.HiddenType;
import me.pixelgames.pixelcrack3r.lobby.gui.GuiScreen;
import me.pixelgames.pixelcrack3r.lobby.main.Main;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import me.pixelgames.pixelcrack3r.lobby.utils.ItemGenerator;
import net.md_5.bungee.api.ChatColor;

public class PlayerManager {

	private static HashMap<String, PlayerManager> players = new HashMap<String, PlayerManager>();

	private HashMap<HiddenType, Boolean> hiddenOptions = new HashMap<HiddenType, Boolean>();
	private HashMap<String, String> scoreboardValues = new HashMap<String, String>();
	
	private Player player;
	
	private boolean building;
	private boolean vanished;
	private boolean flying;
	
	private List<Effect> effects;
	
	private GuiScreen currentScreen;
	
	public PlayerManager(Player player) {
		this.player = player;
		players.put(player.getName(), this);
		this.setHidden(HiddenType.NOBODY, true, true);
	}
	
	public void setBuildMode(boolean buildmode) {
		this.building = buildmode;
	}
	
	public boolean isInBuildMode() {
		return this.building;
	}
	
	public void setVanished(boolean isVanish) {
		this.getUserConfig().set("lobby.silent.vanished", isVanish + "");
		this.vanished = isVanish;
		if(isVanish) {
			
			for(Player all : Bukkit.getServer().getOnlinePlayers()) {
				all.hidePlayer(this.player);
			}
		} else {
			for(Player all : Bukkit.getServer().getOnlinePlayers()) {
				all.showPlayer(this.player);
			}
		}
		
	}
	
	public boolean isFlying() {
		return this.flying;
	}
	
	public void setFlying(boolean flying) {
		this.flying = flying;
	}
	
	public void setHidden(HiddenType type, boolean boo, boolean clear) {
		if(clear) {
			hiddenOptions.clear();
		}
		
		hiddenOptions.put(type, boo);
	}
	
	public void setHidden(HiddenType type, boolean boo, boolean clear, boolean def) {
		if(clear) {
			hiddenOptions.clear();
		}
		
		if(def && !hiddenOptions.containsKey(type)) {
			hiddenOptions.put(type, boo);
		} else if(!def) {
			hiddenOptions.put(type, boo);
		}
		
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setJoinInventory() {
		if(Config.getConfig().getBoolean("settings.inventory.navigator.enabled")) {
			this.player.getInventory().setItem(Config.getConfig().getInt("settings.inventory.navigator.slot") - 1, ItemGenerator.modify()
					.setItemStack(new ItemStack(Material.getMaterial(Config.getConfig().getString("settings.inventory.navigator.material")), 1, (byte) Config.getConfig().getInt("settings.inventory.navigator.short-damage")))
					.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.inventory.navigator.name")))
					.setLore(Config.getConfig().getStringList("settings.inventory.navigator.description"))
					.build());			
		}
		
		if(Config.getConfig().getBoolean("settings.inventory.custom.enabled")) {
			this.player.getInventory().setItem(Config.getConfig().getInt("settings.inventory.custom.slot") - 1, ItemGenerator.modify()
					.setItemStack(new ItemStack(Material.getMaterial(Config.getConfig().getString("settings.inventory.custom.material")), 1, (byte) Config.getConfig().getInt("settings.inventory.custom.short-damage")))
					.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.inventory.custom.name")))
					.setLore(Config.getConfig().getStringList("settings.inventory.custom.description"))
					.build());
		}
		
		if(Config.getConfig().getBoolean("settings.inventory.lobby-switcher.enabled")) {
			this.player.getInventory().setItem(Config.getConfig().getInt("settings.inventory.lobby-switcher.slot") - 1, ItemGenerator.modify().setItemStack(new ItemStack(Material.getMaterial(Config.getConfig().getString("settings.inventory.lobby-switcher.material")), 1, (byte) Config.getConfig().getInt("settings.inventory.lobby-switcher.short-damage"))).setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.inventory.lobby-switcher.name"))).setLore(Config.getConfig().getStringList("settings.inventory.lobby-switcher.description")).build());
		}
		
		if(Config.getConfig().getBoolean("settings.inventory.player-hider.enabled")) {
			this.player.getInventory().setItem(Config.getConfig().getInt("settings.inventory.player-hider.slot") - 1, ItemGenerator.modify().setItemStack(new ItemStack(Material.getMaterial(Config.getConfig().getString("settings.inventory.player-hider.material")), 1, (byte) Config.getConfig().getInt("settings.inventory.player-hider.short-damage"))).setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.inventory.player-hider.name"))).setLore(Config.getConfig().getStringList("settings.inventory.player-hider.description")).build());
		}
		
		if(Config.getConfig().getBoolean("settings.inventory.gadgets.enabled")) {
			this.player.getInventory().setItem(Config.getConfig().getInt("settings.inventory.gadgets.slot") - 1, ItemGenerator.modify().setItemStack(new ItemStack(Material.getMaterial(Config.getConfig().getString("settings.inventory.gadgets.material")), 1, (byte) Config.getConfig().getInt("settings.inventory.gadgets.short-damage"))).setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.inventory.gadgets.name"))).setLore(Config.getConfig().getStringList("settings.inventory.gadgets.description")).build());
		}
	}
	
	public HashMap<String, String> getScoreboardValues() {
		return scoreboardValues;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void connect(String server) {
		Main.connect(this.player, server);
	}
	
	public List<Effect> getEffects() {
		return effects;
	}
	
	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}
	
	public void displayGuiScreen(GuiScreen guiScreen) {
		this.player.openInventory(guiScreen.init());
		this.currentScreen = guiScreen;
	}
	
	public HashMap<HiddenType, Boolean> hiderOptions() {
		return hiddenOptions;
	}
	
	public boolean hasHidden(HiddenType type) {
		return hiddenOptions.containsKey(type) ? hiddenOptions.get(type) : false;
	}
	
	public Group getGroup() {
		if(this.player.hasPermission("lobby.group.team")) {
			return Group.TEAM;
		} else if(this.player.hasPermission("lobby.group.vip")) {
			return Group.VIP;
		} else {
			return Group.PLAYER;
		}
	}
	
	public boolean isVanished() {
		return this.vanished;
	}
	
	public void loadDefaultScoreboard() {
		ScoreboardManager scoreboard = ScoreboardManager.getScoreboardManager();
		
		List<String> used = new ArrayList<String>();
		
		List<String> content = Config.getConfig().getStringList("settings.scoreboard.content");
		
		HashMap<String, Integer> contentHash = new HashMap<String, Integer>();
		
		int scorecount = content.size() - 1;
		
		for(String str : content) {
			
			if(!str.contains("%")) {
				contentHash.put(ChatColor.translateAlternateColorCodes('&', str), scorecount);
			} else {
				String key = " ";
				while(used.contains(key)) key+="§r";
				contentHash.put(key, scorecount);
				this.scoreboardValues.put(key, str);
				used.add(key);
			}
			scorecount--;
		}
		
		scoreboard.setSidebar(this.player, ChatColor.translateAlternateColorCodes('&', Config.getConfig().getString("settings.scoreboard.title")), contentHash);
		
	}
	
	public GuiScreen getCurrentScreen() {
		return currentScreen;
	}
	
	public MySQLConfiguration getUserConfig() {
		if(!Config.getConfig().getBoolean("settings.mysql.enabled")) return null;
		
		MySQLConfiguration config = new MySQLConfiguration(Config.getMySQLPRovider(), "playerdata", this.player.getUniqueId().toString());
		config.createConfigTable("playerdata");
		return config;
	}
	
	public static ArrayList<String> getVanished() {
		ArrayList<String> vanished = new ArrayList<String>();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(PlayerManager.getPlayer(player).isVanished()) {
				vanished.add(player.getName());
			}
		}
		
		return vanished;
	}
	
	public static PlayerManager getPlayer(Player player) {
		PlayerManager manager = players.containsKey(player.getName()) ? players.get(player.getName()) : new PlayerManager(player);
		
		manager.setPlayer(player);
		
		return manager;
	}
	
}
