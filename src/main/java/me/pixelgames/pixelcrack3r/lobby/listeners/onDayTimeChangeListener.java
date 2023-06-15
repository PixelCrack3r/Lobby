package me.pixelgames.pixelcrack3r.lobby.listeners;

import me.pixelgames.pixelcrack3r.lobby.events.DayTimeChangeEvent;
import me.pixelgames.pixelcrack3r.lobby.utils.Config;

public class onDayTimeChangeListener {
	
	private static onDayTimeChangeListener list;
	
	public void onDayTimeChange(DayTimeChangeEvent e) {
		if(DayTimeChangeEvent.getTimeTickByDayTime(DayTimeChangeEvent.getDayTimeByName(Config.getConfig().getString("settings.world.day-time"))) != e.getTimeTick()) {
			e.setTime(DayTimeChangeEvent.getDayTimeByName(Config.getConfig().getString("settings.world.day-time")));
		} else {
			e.setCancelled(true);
		}
			
	}
	
	public static onDayTimeChangeListener getListener() {
		if(list == null) {
			list = new onDayTimeChangeListener();
		}
		
		return list != null ? list : null;
	}

}
