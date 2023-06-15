package me.pixelgames.pixelcrack3r.lobby.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.pixelgames.pixelcrack3r.lobby.utils.Config;

public class LocationManager {

	private String name;
	
	public LocationManager(String name) {
		this.name = name;
	}
	
	public void setPos(Location location) {
		Config.getLocations().set("locations." + this.name + ".Block-X", location.getX());
		Config.getLocations().set("locations." + this.name + ".Block-Y", location.getY());
		Config.getLocations().set("locations." + this.name + ".Block-Z", location.getZ());
		Config.getLocations().set("locations." + this.name + ".Yaw", location.getYaw());
		Config.getLocations().set("locations." + this.name + ".Pitch", location.getPitch());
		Config.getLocations().set("locations." + this.name + ".World", location.getWorld().getName());
		
		Config.saveLocations();
	}
	
	public boolean isValid() {
		return true;
	}
	
	public Location getLocation() {
		String world = Config.getLocations().getString("locations." + this.name + ".World");
		
		double x = Config.getLocations().getDouble("locations." + this.name + ".Block-X");
		double y = Config.getLocations().getDouble("locations." + this.name + ".Block-Y");
		double z = Config.getLocations().getDouble("locations." + this.name + ".Block-Z");
		
		float yaw = (float) Config.getLocations().getDouble("locations." + this.name + ".Yaw");
		float pitch = (float) Config.getLocations().getDouble("locations." + this.name + ".Pitch");
		
		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}
	
	@Override
	public String toString() {
		return getLocation().getWorld().getName() + " " + getLocation().getBlockX() + " " + getLocation().getBlockY() + " " + getLocation().getBlockZ();
	}
	
	public static LocationManager getLocation(String name) {
		return new LocationManager(name);
	}
	
}
