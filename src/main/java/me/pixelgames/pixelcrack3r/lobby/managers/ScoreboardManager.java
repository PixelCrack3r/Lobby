package me.pixelgames.pixelcrack3r.lobby.managers;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.pixelgames.pixelcrack3r.lobby.main.Main;

public class ScoreboardManager {
	
	private static ScoreboardManager defaultScoreboardManager;
	
	private Main plugin;
	
	public ScoreboardManager(Main plugin, boolean def) {
		this.plugin = plugin;
		if(def) {
			defaultScoreboardManager = this;
		}
	}
	
	public Scoreboard getScoreboard(Player player) {
		if(player.hasMetadata("scoreboard") && player.getMetadata("scoreboard").size() > 0) {
			return (Scoreboard) player.getMetadata("scoreboard").get(0).value();
		}
		
		Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
		plugin.setMetadata(player, "scoreboard", scoreboard);
		return scoreboard;
	}

	public void setSidebar(Player player, String title, HashMap<String, Integer> sidebar) {
		Scoreboard scoreboard = getScoreboard(player);
		Objective objective = scoreboard.getObjective(player.getName());
		
		if(objective != null) objective.unregister();
		
		objective = scoreboard.registerNewObjective(player.getName(), "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(title);
		
		for(String score : sidebar.keySet()) {
			objective.getScore(score).setScore(sidebar.get(score));
		}
		player.setScoreboard(scoreboard);
	}
	
	public void createTeam(Player player, String name, String entry, String prefix, String suffix) {
		Scoreboard scoreboard = getScoreboard(player);
		Team team = scoreboard.getTeam(name);
		if(team == null) {
			team = scoreboard.registerNewTeam(name);
		}
		team.addEntry(entry);
		team.setPrefix(prefix);
		team.setSuffix(suffix);
	}
	
	public static ScoreboardManager getScoreboardManager() {
		return defaultScoreboardManager;
	}
	
	public static ScoreboardManager createNewManager(Main plugin, boolean def) {
		return new ScoreboardManager(plugin, def);
	}
	
}
