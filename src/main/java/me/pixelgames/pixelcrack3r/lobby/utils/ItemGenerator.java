package me.pixelgames.pixelcrack3r.lobby.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;

public class ItemGenerator {

	private ItemStack itm;
	
	private static HashMap<String, net.minecraft.server.v1_8_R3.ItemStack> skullMap = new HashMap<String, net.minecraft.server.v1_8_R3.ItemStack>();
	
	public static ItemStack generateItem(Material material, int amount, int short_damage) {
		ItemStack itm = new ItemStack(material, amount, (byte) short_damage);
		
		return itm;
	}
	
	public static ItemStack generatePlaceHolder() {
		return ItemGenerator.modify().setItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7)).setDisplayName(" ").build();
	}
	
	public ItemGenerator setItemStack(ItemStack itm) {
		this.itm = itm;
		return this;
	}
	
	public ItemGenerator setDisplayName(String name) {
		ItemMeta itemMeta = this.itm.getItemMeta();
		itemMeta.setDisplayName(name);
		this.itm.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemGenerator setLore(List<String> lore) {
		ItemMeta itemMeta = this.itm.getItemMeta();
		
		List<String> des = new ArrayList<String>();
		
		for(String s : lore) {
			des.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		
		itemMeta.setLore(des);
		this.itm.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemGenerator setOwner(String ownerName) {
		if(!skullMap.containsKey(ownerName)) {
			SkullMeta meta = (SkullMeta) this.itm.getItemMeta();
			meta.setOwner(ownerName);
			this.itm.setItemMeta(meta);
			skullMap.put(ownerName, CraftItemStack.asNMSCopy(this.itm));
		} else this.itm = CraftItemStack.asBukkitCopy(skullMap.get(ownerName));
		return this;
	}
	
	public ItemGenerator addEnchantment(Enchantment enchantment, int power, boolean arg2) {
		if(enchantment == null) {
			return this;
		}
		
		ItemMeta meta = this.itm.getItemMeta();
		meta.addEnchant(enchantment, power, arg2);
		this.itm.setItemMeta(meta);
		
		return this;
	}
	
	public ItemStack build() {
		return itm;
	}
	
	public static ItemGenerator modify() {
		return new ItemGenerator();
	}
	
}
