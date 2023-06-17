package me.pixelgames.pixelcrack3r.lobby.utils;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import me.pixelgames.pixelcrack3r.lobby.managers.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.earth2me.essentials.api.Economy;

import me.pixelgames.pixelcrack3r.lobby.enums.DayTime;
import me.pixelgames.pixelcrack3r.lobby.enums.Group;
import me.pixelgames.pixelcrack3r.lobby.enums.HiddenType;
import me.pixelgames.pixelcrack3r.lobby.enums.Weather;
import me.pixelgames.pixelcrack3r.lobby.events.DayTimeChangeEvent;
import me.pixelgames.pixelcrack3r.lobby.listeners.onDayTimeChangeListener;
import me.pixelgames.pixelcrack3r.lobby.main.Main;
import gq.pixelgames.pixelcrack3r.utils.AdvancedEntityModifier;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.WorldData;
import net.minecraft.server.v1_8_R3.WorldServer;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Timer implements Runnable {

	private String worldName;
	
	private int scheduler;
	
	private AtomicInteger ticker;
	private boolean calculating;
	
	private int currentIndexBarMessage;
	
	public Timer(World world) {
		this.worldName = world.getName();
		this.ticker = new AtomicInteger();
		
		Biome biom = Config.weather == Weather.SNOW ? Biome.ICE_PLAINS : Biome.valueOf(Config.getConfig().getString("settings.world.biom"));
		for (int x = -Config.getConfig().getInt("settings.world.biomX"); x < Config.getConfig().getInt("settings.world.biomX"); x++) {
			for (int z = -Config.getConfig().getInt("settings.world.biomZ"); z < Config.getConfig().getInt("settings.world.biomZ"); z++) {
				if (world.getBiome(x, z) != biom) {
					world.setBiome(x, z, biom);
				}
			}
		}
	}
	
	public void start() {
		this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), this, 0, 1);
	}
	

	@Override
	public void run() {
		World world = Bukkit.getWorld(worldName);
		
		int current = this.ticker.addAndGet(1);
		
		if(Config.getConfig().getInt("settings.server.updates.changebar") != -1 && current % Config.getConfig().getInt("settings.server.updates.changebar") == 0) this.currentIndexBarMessage++;
		String message = Config.getConfig().getString("settings.server.updates.actionbar");
		if(this.currentIndexBarMessage >= message.split(";").length) this.currentIndexBarMessage = 0;
		message = message.split(";")[this.currentIndexBarMessage];

		
		if(current % 10 == 0 && !Timer.this.calculating) {
			this.calculating = true;
			for(Entity entity : world.getEntities()) {
				if(!Config.getConfig().getBoolean("settings.entity.ai") && !(entity instanceof Player)) {
					AdvancedEntityModifier.modify(entity, Main.getInstance()).setNoAI(true);	
				}
			}
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(p.getWorld().getName().equals(world.getName())) {
					ScoreboardManager scoreboard = ScoreboardManager.getScoreboardManager();
					PlayerManager player = PlayerManager.getPlayer(p);
					
					if(Config.getConfig().getInt("settings.server.updates.changebar") != -1) {												
						ActionBar.createActionBar(ChatColor.translateAlternateColorCodes('&', message)).send(p);
					}
					
					for(String key : player.getScoreboardValues().keySet()) {
						String prefix = player.getScoreboardValues().get(key);
						
						if(Config.getConfig().getBoolean("settings.plugin.dependencies.essentials")) {
							try {
								BigDecimal money = Economy.getMoneyExact(player.getPlayer().getUniqueId());
								prefix = prefix.replaceAll("%coins%", money.toString());
							} catch (Exception e) {
								prefix = prefix.replaceAll("%coins%", "");
							}
						}
						
						if(Config.getConfig().getBoolean("settings.plugin.dependencies.pex")) {
							for(String hash : Config.getPerm().getStringList("scoreboard.groups")) {
								String group = hash.split(": ")[0];
								String name = hash.split(": ")[1];
								
								if(PermissionsEx.getUser(player.getPlayer()).inGroup(group)) {
									prefix = prefix.replaceAll("%rank%", ChatColor.translateAlternateColorCodes('&', name));
									break;
								}
							}
						}
						
						for(String hash : Config.getPerm().getStringList("scoreboard.perms")) {
							String perm = hash.split(": ")[0];
							String group = hash.split(": ")[1];
							
							if(player.getPlayer().hasPermission(perm)) {
								prefix = prefix.replaceAll("%rank%", ChatColor.translateAlternateColorCodes('&', group));
								break;
							}
						}
						
						prefix = prefix.replaceAll("%online%", (Bukkit.getOnlinePlayers().size() - PlayerManager.getVanished().size()) + "");
						prefix = prefix.replaceAll("%max%", Bukkit.getMaxPlayers() + "");
						
						String name = player.getScoreboardValues().get(key).split("%").length > 1 ? player.getScoreboardValues().get(key).split("%")[1] : key;
						
						if(prefix.contains("%rank%") || prefix.contains("%coins%")) {
							prefix = "null";
						}
						
						prefix = ChatColor.translateAlternateColorCodes('&', prefix);
						
						scoreboard.createTeam(player.getPlayer(), name, key, prefix, "");
					}
				}
				
				for(Player o : Bukkit.getOnlinePlayers()) {
					PlayerManager player = PlayerManager.getPlayer(p);
					PlayerManager other = PlayerManager.getPlayer(o);
					
					if(!o.getName().equalsIgnoreCase(p.getName()) && !player.isVanished() && !other.isVanished()) {
						
						if(player.hasHidden(HiddenType.EVERY)) {
							p.hidePlayer(o);
						} else if(player.hasHidden(HiddenType.NOBODY)) {
							p.showPlayer(o);
						} else if(player.hasHidden(HiddenType.CHOOSE)) {
							if(player.hasHidden(HiddenType.PLAYER) && other.getGroup() == Group.PLAYER) {
								p.hidePlayer(o);
							}
							
							if(player.hasHidden(HiddenType.PREMIUM) && other.getGroup() == Group.VIP) {
								p.hidePlayer(o);
							}
							
							if(player.hasHidden(HiddenType.TEAM) && other.getGroup() == Group.TEAM) {
								p.hidePlayer(o);
							}
							
							if(!player.hasHidden(HiddenType.PLAYER) && other.getGroup() == Group.PLAYER) {
								p.showPlayer(o);
							}
							
							if(!player.hasHidden(HiddenType.PREMIUM) && other.getGroup() == Group.VIP) {
								p.showPlayer(o);
							}
							
							if(!player.hasHidden(HiddenType.TEAM) && other.getGroup() == Group.TEAM) {
								p.showPlayer(o);
							}
						}
						
						if(other.hasHidden(HiddenType.EVERY)) {
							o.hidePlayer(p);
						} else if(other.hasHidden(HiddenType.NOBODY)) {
							o.showPlayer(p);
						} else if(other.hasHidden(HiddenType.CHOOSE)) {
							if(other.hasHidden(HiddenType.PLAYER) && player.getGroup() == Group.PLAYER) {
								o.hidePlayer(p);
							}
							
							if(other.hasHidden(HiddenType.PREMIUM) && player.getGroup() == Group.VIP) {
								o.hidePlayer(p);
							}
							
							if(other.hasHidden(HiddenType.TEAM) && player.getGroup() == Group.TEAM) {
								o.hidePlayer(p);
							}
							
							if(!other.hasHidden(HiddenType.PLAYER) && player.getGroup() == Group.PLAYER) {
								o.showPlayer(p);
							}
							
							if(!other.hasHidden(HiddenType.PREMIUM) && player.getGroup() == Group.VIP) {
								o.showPlayer(p);
							}
							
							if(!other.hasHidden(HiddenType.TEAM) && player.getGroup() == Group.TEAM) {
								o.showPlayer(p);
							}
						}
					} else if(player.isVanished()) {
						o.hidePlayer(p);
					} else if(other.isVanished()) {
						p.hidePlayer(o);
					}
				}
			}
			
			Timer.this.calculating = false;
		}
		
		long timeTick = world.getTime();
		DayTime time = DayTimeChangeEvent.getCurrentTime((int) timeTick);
		
		DayTimeChangeEvent event = new DayTimeChangeEvent((int) timeTick, time, world);
		onDayTimeChangeListener.getListener().onDayTimeChange(event);
		
		if(event.isCancelled()) {
			world.setTime(timeTick);
			world.setFullTime(timeTick);
		}

		if(event.getTimeTick() != timeTick) {
			world.setTime(event.getTimeTick());
			world.setFullTime(event.getTimeTick());
		}
		
		if(Config.weather != null && Config.weather != Weather.NON) {
			int i = (300 + new Random().nextInt(600)) * 20;
			
		    WorldServer localWorldServer = MinecraftServer.getServer().worlds.get(0);
		    WorldData localWorldData = localWorldServer.getWorldData();
			if((Config.weather == Weather.CLEAR && (world.isThundering() || world.hasStorm()))) {
				localWorldData.i(i);  
				localWorldData.setWeatherDuration(0);
				localWorldData.setThunderDuration(0);
				localWorldData.setStorm(false);
				localWorldData.setThundering(false);
			} else if((Config.weather == Weather.RAIN || Config.weather == Weather.SNOW) && (world.isThundering() || !world.hasStorm())) {
				localWorldData.i(0);  
				localWorldData.setWeatherDuration(i);
				localWorldData.setThunderDuration(i);
				localWorldData.setStorm(true);
				localWorldData.setThundering(false);
			} else if((Config.weather == Weather.THUNDER && (!world.isThundering() || !world.hasStorm()))) {
				localWorldData.i(00);  
				localWorldData.setWeatherDuration(i);
				localWorldData.setThunderDuration(i);
				localWorldData.setStorm(true);
				localWorldData.setThundering(true);
			}
			
		}
	}
	
	public int getSchuduler() {
		return this.scheduler;
	}
	
}
