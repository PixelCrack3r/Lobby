package me.pixelgames.pixelcrack3r.lobby.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.pixelgames.pixelcrack3r.lobby.utils.Config;
import me.pixelgames.pixelcrack3r.lobby.utils.ItemGenerator;

public class ItemManager {
	
	private final static Map<String, String> itms = new HashMap<String, String>();
	private final static Map<String, ItemManager> customs = new HashMap<String, ItemManager>();
	
	private String name;
	private String server;
	private boolean custom;
	
	public ItemManager(String name) {
		if(name == null || name.isEmpty()) name = null;
		this.name = name;
	}
	
	public static void addItem(String loc, Material material, String name, List<String> description, int slot, Location location, boolean locNu, boolean def) {
		if(def) {
			if(locNu) {
				Config.getItems().addDefault("items." + loc + ".material", material.name());
				Config.getItems().addDefault("items." + loc + ".name", name);
				Config.getItems().addDefault("items." + loc + ".description", description);
				Config.getItems().addDefault("items." + loc + ".slot", slot);
			} else {
				Config.getItems().addDefault("items." + loc + ".material", material.name());
				Config.getItems().addDefault("items." + loc + ".name", name);
				Config.getItems().addDefault("items." + loc + ".description", description);
				Config.getItems().addDefault("items." + loc + ".slot", slot);
				Config.getItems().addDefault("items." + loc + ".Block-X", location.getBlockX() + .5);
				Config.getItems().addDefault("items." + loc + ".Block-Y", location.getBlockY() + .0);
				Config.getItems().addDefault("items." + loc + ".Block-Z", location.getBlockZ() + .5);
				Config.getItems().addDefault("items." + loc + ".Yaw", location.getYaw());
				Config.getItems().addDefault("items." + loc + ".Pitch", location.getPitch());
				Config.getItems().addDefault("items." + loc + ".World", location.getWorld().getName());
			}
		} else {
			if(locNu) {
				Config.getItems().set("items." + loc + ".material", material.name());
				Config.getItems().set("items." + loc + ".name", name);
				Config.getItems().set("items." + loc + ".description", description);
				Config.getItems().set("items." + loc + ".slot", slot);
			} else {
				Config.getItems().set("items." + loc + ".material", material.name());
				Config.getItems().set("items." + loc + ".name", name);
				Config.getItems().set("items." + loc + ".description", description);
				Config.getItems().set("items." + loc + ".slot", slot);
				Config.getItems().set("items." + loc + ".Block-X", location.getBlockX() + .5);
				Config.getItems().set("items." + loc + ".Block-Y", location.getBlockY() + .0);
				Config.getItems().set("items." + loc + ".Block-Z", location.getBlockZ() + .5);
				Config.getItems().set("items." + loc + ".Yaw", location.getYaw());
				Config.getItems().set("items." + loc + ".Pitch", location.getPitch());
				Config.getItems().set("items." + loc + ".World", location.getWorld().getName());
			}
		}
		
		Config.saveItems();
		
	}
	
	public static void addItem(String loc, Material material, String name, List<String> description, int slot, List<String> effects, boolean def, String permission) {
		if(def) {
			if(effects == null) {
				Config.getItems().addDefault("items." + loc + ".material", material.name());
				Config.getItems().addDefault("items." + loc + ".name", name);
				Config.getItems().addDefault("items." + loc + ".description", description);
				Config.getItems().addDefault("items." + loc + ".slot", slot);
				Config.getItems().addDefault("items." + loc + ".effect", Arrays.asList(Effect.SMOKE.name()));
			} else {
				Config.getItems().addDefault("items." + loc + ".material", material.name());
				Config.getItems().addDefault("items." + loc + ".name", name);
				Config.getItems().addDefault("items." + loc + ".description", description);
				Config.getItems().addDefault("items." + loc + ".slot", slot);
				Config.getItems().addDefault("items." + loc + ".effects", effects);
			}
		} else {
			if(effects == null) {
				Config.getItems().set("items." + loc + ".material", material.name());
				Config.getItems().set("items." + loc + ".name", name);
				Config.getItems().set("items." + loc + ".description", description);
				Config.getItems().set("items." + loc + ".slot", slot);
				Config.getItems().set("items." + loc + ".effect", Arrays.asList(Effect.SMOKE.name()));
			} else {
				Config.getItems().set("items." + loc + ".material", material.name());
				Config.getItems().set("items." + loc + ".name", name);
				Config.getItems().set("items." + loc + ".description", description);
				Config.getItems().set("items." + loc + ".slot", slot);
				Config.getItems().set("items." + loc + ".effects", effects);
			}
		}
		
		Config.saveItems();
		
	}
	
	public static void addItem(String loc, Material material, String name, List<String> description, int slot, String inventory, boolean def) {
		if(def) {
			if(inventory == null) {
				Config.getItems().addDefault("items." + loc + ".material", material.name());
				Config.getItems().addDefault("items." + loc + ".name", name);
				Config.getItems().addDefault("items." + loc + ".description", description);
				Config.getItems().addDefault("items." + loc + ".slot", slot);
			} else {
				Config.getItems().addDefault("items." + loc + ".material", material.name());
				Config.getItems().addDefault("items." + loc + ".name", name);
				Config.getItems().addDefault("items." + loc + ".description", description);
				Config.getItems().addDefault("items." + loc + ".slot", slot);
				Config.getItems().addDefault("items." + loc + ".inventory", inventory);
			}
		} else {
			if(inventory == null) {
				Config.getItems().set("items." + loc + ".material", material.name());
				Config.getItems().set("items." + loc + ".name", name);
				Config.getItems().set("items." + loc + ".description", description);
				Config.getItems().set("items." + loc + ".slot", slot);
			} else {
				Config.getItems().set("items." + loc + ".material", material.name());
				Config.getItems().set("items." + loc + ".name", name);
				Config.getItems().set("items." + loc + ".description", description);
				Config.getItems().set("items." + loc + ".slot", slot);
				Config.getItems().set("items." + loc + ".inventory", inventory);
			}
		}
		
		Config.saveItems();
		
	}

	public static void addServer(String loc, Material material, String name, List<String> description, int slot, String server, boolean def) {
		if(def) {
			Config.getItems().addDefault("items." + loc + ".material", material.name());
			Config.getItems().addDefault("items." + loc + ".name", name);
			Config.getItems().addDefault("items." + loc + ".description", description);
			Config.getItems().addDefault("items." + loc + ".slot", slot);
			Config.getItems().addDefault("items." + loc + ".server", server);
		} else {
			Config.getItems().set("items." + loc + ".material", material.name());
			Config.getItems().set("items." + loc + ".name", name);
			Config.getItems().set("items." + loc + ".description", description);
			Config.getItems().set("items." + loc + ".slot", slot);
			Config.getItems().set("items." + loc + ".server", server);
		}
		
		Config.saveItems();
		
	}
	
	public ItemManager registerAsync() {
		itms.put(this.getName(), this.name);
		return this;
	}
	
	public ItemManager registerCustom() {
		this.custom = true;
		customs.put(this.name, this);
		return this;
	}
	
	public void editPos(String location) {
		this.setValue("location", location);
	}
	
	public Material getMaterial() {
		return Material.getMaterial(Config.getItems().getString("items." + this.name + ".material"));
	}
	
	public String getOwnerName() {
		return Config.getItems().isSet("items." + this.name + ".owner") ? Config.getItems().getString("items." + this.name + ".owner") : null;
	}
	
	public Enchantment getEnchantment() {
		String enchantment = Config.getItems().isSet("items." + this.name + ".enchantment") ? Config.getItems().getString("items." + this.name + ".enchantment") : null;
		
		if(enchantment == null) {
			return null;
		}

		return Enchantment.getByName(enchantment);
	}
	
	public ItemStack getItemStack() {
		ItemStack itm = new ItemStack(getMaterial(), getSize(), (byte) 0);
		
		if(getOwnerName() != null) {
			itm = new ItemStack(getMaterial(), getSize(), (byte) 3);
		}
		
		String name = ChatColor.translateAlternateColorCodes('&', getName());
		
		if(getMaterial() == Material.SKULL_ITEM) {
			return ItemGenerator.modify().setItemStack(itm).setOwner(getOwnerName()).setDisplayName(name).setLore(getDescription()).addEnchantment(getEnchantment(), 0, false).build();
		}
		
		return ItemGenerator.modify().setItemStack(itm).setDisplayName(name).setLore(getDescription()).addEnchantment(getEnchantment(), 0, false).build();
	}
	
	public String getName() {
		if(custom) return this.name;
		
		String name = Config.getItems().isSet("items." + this.name + ".name") ? Config.getItems().getString("items." + this.name + ".name").replace('&', '§') : null;
		
		if(name == null) {
			return null;
		}

		return name;
	}

	public int getSlot() {
		return Config.getItems().getInt("items." + this.name + ".slot");
	}
	
	public int getSize() {
		return Config.getItems().isInt("items." + this.name + ".size") ? Config.getItems().getInt("items." + this.name + ".size") : 1;
	}
	
	public Location getLocation() {
		if(!this.hasValidLocation()) {
			return null;
		}
		
		LocationManager location = LocationManager.getLocation(Config.getItems().getString("items." + this.name + ".location"));
		return location.getLocation();
	}
	
	public boolean hasValidLocation() {
		if(!Config.getItems().isSet("items." + this.name + ".location")) {
			return false;
		}
		
		LocationManager location = LocationManager.getLocation(Config.getItems().getString("items." + this.name + ".location"));
		if(!location.isValid()) {
			return false;
		}
		
		return true;
	}
	
	public List<String> getDescription() {
		List<String> description = new ArrayList<String>();
		
		for(String line : Config.getItems().getStringList("items." + this.name + ".description")) {
			line = line.replace('&', '§');
			
			description.add(ChatColor.translateAlternateColorCodes('&', line));
		}
		
		return description;
	}
	
	public boolean hasEffects() {
		return Config.getItems().isSet("items." + this.name + ".effects");
	}
	
	public List<String> getEffectNames() {
		return Config.getItems().getStringList("items." + this.name + ".effects");
	}
	
	public List<Effect> getEffects() {
		List<Effect> effects = new ArrayList<Effect>();
		
		for(String effect : getEffectNames()) {
			effects.add(Effect.valueOf(effect));
		}
		
		return effects;
	}
	
	public String getState() {
		return Config.getItems().isSet("items." + this.name + ".name") ? this.name : null;
	}
	
	public String getInventory() {
		return Config.getItems().getString("items." + this.name + ".inventory");
	}
	
	public boolean hasInventory() {
		return Config.getItems().isSet("items." + this.name + ".inventory");
	}
	
	public boolean hasCommand() {
		return Config.getItems().isSet("items." + this.name + ".command");
	}
	
	public String getCommand() {
		return "/" + Config.getItems().getString("items." + this.name + ".command");
	}
	
	public boolean needPermissions() {
		return Config.getItems().isSet("items." + this.name + ".permission");
	}
	
	public boolean hasServer() {
		return this.server != null || Config.getItems().isSet("items." + this.name + ".server");
	}
	
	public String getPermission() {
		return Config.getItems().getString("items." + this.name + ".permission");
	}
	
	public String getServer() {
		return this.server != null ? this.server : Config.getItems().getString("items." + this.name + ".server");
	}
	
	public boolean exists() {
		return this.custom || Config.getItems().isSet("items." + this.name + ".name") && this.name != null && !this.name.equals("null");
	}
	
	public void setValue(String section, String value) {
		if(this.custom) return;
		
		Config.getItems().set("items." + this.name + "." + section, value);
		this.save();
	}
	
	public void save() {
		Config.saveItems();
	}
	
	public void setCustomServer(String server) {
		this.server = server;
	}
	
	public static ItemManager getItemSlot(String name) {
		if(customs.containsKey(name)) {
			return customs.get(name);
		}
		return new ItemManager(name);
	}
	
	public static ItemManager getAsyncItemSlot(String name) {
		return getItemSlot(itms.containsKey(name) ? itms.get(name) : null);
	}
	
	public static ItemManager getCustom(String name) {
		ItemManager manager = getItemSlot(name).registerCustom();
		return manager;
	}
	
	public static int getSlotId(int posY, int posX) {
		return ((9*posY) + posX - 9) - 1;
	}
	
}
