package me.pixelgames.pixelcrack3r.lobby.utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class ActionBar {

	private String message;
	
	public ActionBar(String message) {
		this.message = message;
	}
	
	public static ActionBar createActionBar(String message) {
		return new ActionBar(message);
	}
	
	public void send(Player p) {
		CraftPlayer player = (CraftPlayer) p;
		
		if(this.message != null) {
			player.getHandle().playerConnection.sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + this.message + "\"}"), (byte) 2));	
		}
		
	}	
	
}
