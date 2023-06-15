package me.pixelgames.pixelcrack3r.lobby.events;

import org.bukkit.World;

import me.pixelgames.pixelcrack3r.lobby.enums.DayTime;

public class DayTimeChangeEvent {
	
	private boolean isCancelled = false;
	
	private int timeTick;
	
	private World world;
	
	private DayTime dayTime;
	
	public DayTimeChangeEvent(int timeTick, DayTime dayTime, World world) {
		this.timeTick = timeTick;
		this.world = world;
		this.dayTime = dayTime;
	}
	
	public int getTimeTick() {
		return timeTick;
	}
	
	public DayTime getTime() {
		return dayTime;
	}
	
	public void setTime(DayTime time) {
		this.dayTime = time;
		this.timeTick = (int) getTimeTickByDayTime(time);
	}
	
	public World getWorld() {
		return this.world;
	}
	
 	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean arg0) {
		isCancelled = arg0;
	}
	
	public static DayTime getCurrentTime(int timeTick) {
		DayTime time = DayTime.DAY;
		
		if(timeTick >= 0 && timeTick < 6500) {
			time = DayTime.NOON;
		} else if(timeTick >= 6500 && timeTick < 13000) {
			time = DayTime.DAY;
		} else if(timeTick >= 13000 && timeTick < 15000) {
			time = DayTime.EVENING;
		} else if(timeTick >= 15000 && timeTick < 0) {
			time = DayTime.NIGHT;
		}
		
		return time;
	}
	
	public static long getTimeTickByDayTime(DayTime time) {
		long tickTime = 0;
		
		if(time == DayTime.DAY) {
			tickTime = 7000;
		} else if(time == DayTime.EVENING) {
			tickTime = 13500;
		} else if(time == DayTime.NIGHT) {
			tickTime = 16000;
		} else if(time == DayTime.NOON) {
			tickTime = 1000;
		}
		
		return tickTime;
	}
	
	public static DayTime getDayTimeByName(String name) {
		DayTime time = null;
		
		if(name.equalsIgnoreCase("NOON")) {
			time = DayTime.NOON;
		} else if(name.equalsIgnoreCase("DAY")) {
			time = DayTime.DAY;
		} else if(name.equalsIgnoreCase("EVENING")) {
			time = DayTime.EVENING;
		} else if(name.equalsIgnoreCase("NIGHT")) {
			time = DayTime.NIGHT;
		}
		
		return time;
	}
}
