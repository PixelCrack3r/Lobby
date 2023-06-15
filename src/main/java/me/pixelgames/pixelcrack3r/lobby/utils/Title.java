package me.pixelgames.pixelcrack3r.lobby.utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class Title {

	private String title;
	private String subtitle;
	
	public Title(String title, String subtitle) {
		this.title = title;
		this.subtitle = subtitle;
	}
	
	public static Title createTitle(String title, String subtitle) {
		return new Title(title, subtitle);
	}
	
	public void send(Player p) {
		CraftPlayer player = (CraftPlayer) p;
		
		if(this.title != null) {
			player.getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + this.title + "\"}")));			
		}
		
		if(this.subtitle != null) {
			player.getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + this.subtitle + "\"}")));

		}
		
	}	
	
}
