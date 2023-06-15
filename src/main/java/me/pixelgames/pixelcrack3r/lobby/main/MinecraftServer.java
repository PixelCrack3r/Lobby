package me.pixelgames.pixelcrack3r.lobby.main;

import net.minecraft.server.v1_8_R3.WorldData;
import net.minecraft.server.v1_8_R3.WorldServer;

public class MinecraftServer {

	public static WorldData getWorldData(String name) {
		for(WorldServer world : net.minecraft.server.v1_8_R3.MinecraftServer.getServer().worldServer) {
			if(world.getWorldData().getName().equals(name)) return world.getWorldData();
		}
		
		return net.minecraft.server.v1_8_R3.MinecraftServer.getServer().worldServer[0].getWorldData();
	}
	
}
