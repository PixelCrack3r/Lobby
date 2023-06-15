package me.pixelgames.pixelcrack3r.lobby.gui;

import me.pixelgames.pixelcrack3r.lobby.enums.HiddenType;
import me.pixelgames.pixelcrack3r.lobby.managers.InventoryManager;
import me.pixelgames.pixelcrack3r.lobby.managers.PlayerManager;
import me.pixelgames.pixelcrack3r.lobby.utils.ItemGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiPlayerHider extends GuiScreen {

	private final static InventoryManager hider = InventoryManager.getInventorySlot("hider");
	
	private Inventory inv;
	
	private Player player;
	
	public GuiPlayerHider(Player player) {
		this.player = player;
	}
	
	@Override
	public Inventory init() {
		this.inv = Bukkit.createInventory(null, InventoryType.BREWING, hider.getHeadline());
		
		update();
		
		return this.inv;
	}
	
	@Override
	public void onClick(ItemStack itm) {
		PlayerManager manager = PlayerManager.getPlayer(this.player);
		
		String displayName = itm.getItemMeta().getDisplayName();
		
		if(displayName.equalsIgnoreCase("§aEveryone")) {
			manager.setHidden(HiddenType.EVERY, true, true);
		} else if(displayName.equalsIgnoreCase("§6Only Choosen")) {
			manager.setHidden(HiddenType.CHOOSE, true, true);
		} else if(displayName.equalsIgnoreCase("§cNobody")) {
			manager.setHidden(HiddenType.NOBODY, true, true);
		} else if(displayName.equalsIgnoreCase("§cReset")) {
			manager.setHidden(HiddenType.NOBODY, true, true);
		} else if(displayName.equalsIgnoreCase("§7Player")) {
			manager.setHidden(HiddenType.PLAYER, !manager.hasHidden(HiddenType.PLAYER), false);
		} else if(displayName.equalsIgnoreCase("§5VIPs")) {
			manager.setHidden(HiddenType.PREMIUM, !manager.hasHidden(HiddenType.PREMIUM), false);
		} else if(displayName.equalsIgnoreCase("§9Team")) {
			manager.setHidden(HiddenType.TEAM, !manager.hasHidden(HiddenType.TEAM), false);
		}
	}
	
	@Override
	public void update() {
		PlayerManager player = PlayerManager.getPlayer(this.player);
		
		ItemStack every = ItemGenerator.modify().setItemStack(new ItemStack(Material.INK_SACK, 1, (byte) 10)).setDisplayName("§aEveryone").build();
		ItemStack only_choosen = ItemGenerator.modify().setItemStack(new ItemStack(Material.INK_SACK, 1, (byte) 14)).setDisplayName("§6Only Choosen").build();
		ItemStack nobody = ItemGenerator.modify().setItemStack(new ItemStack(Material.INK_SACK, 1, (byte) 1)).setDisplayName("§cNobody").build();
		
		inv.setItem(0, every);
		inv.setItem(1, only_choosen);
		inv.setItem(2, nobody);
		
		if(!player.hasHidden(HiddenType.NOBODY)) {
			inv.setItem(3, ItemGenerator.modify().setItemStack(new ItemStack(Material.BARRIER)).setDisplayName("§cReset").build());
		} else {
			inv.setItem(3, ItemGenerator.generatePlaceHolder());
			inv.setItem(2, ItemGenerator.modify().setItemStack(nobody).addEnchantment(Enchantment.WATER_WORKER, 0, false).build());
		}
		
		if(player.hasHidden(HiddenType.CHOOSE)) {
			player.setHidden(HiddenType.PLAYER, true, false, true);
			player.setHidden(HiddenType.PREMIUM, true, false, true);
			player.setHidden(HiddenType.TEAM, true, false, true);
			
			ItemStack playerType = ItemGenerator.modify().setItemStack(new ItemStack(Material.INK_SACK, 1, (byte) 8)).setDisplayName("§7Player").build();
			ItemStack premiumType = ItemGenerator.modify().setItemStack(new ItemStack(Material.INK_SACK, 1, (byte) 5)).setDisplayName("§5VIPs").build();
			ItemStack teamType = ItemGenerator.modify().setItemStack(new ItemStack(Material.INK_SACK, 1, (byte) 6)).setDisplayName("§9Team").build();
			
			if(!player.hasHidden(HiddenType.PLAYER)) {
				playerType = ItemGenerator.modify().setItemStack(playerType).addEnchantment(Enchantment.WATER_WORKER, 0, false).build();
			}
			
			if(!player.hasHidden(HiddenType.PREMIUM)) {
				premiumType = ItemGenerator.modify().setItemStack(premiumType).addEnchantment(Enchantment.WATER_WORKER, 0, false).build();
			}
			
			if(!player.hasHidden(HiddenType.TEAM)) {
				teamType = ItemGenerator.modify().setItemStack(teamType).addEnchantment(Enchantment.WATER_WORKER, 0, false).build();
			}
			
			inv.setItem(0, playerType);
			inv.setItem(1, premiumType);
			inv.setItem(2, teamType);
		}
	
		if(player.hasHidden(HiddenType.EVERY)) {
			inv.setItem(0, ItemGenerator.modify().setItemStack(every).addEnchantment(Enchantment.WATER_WORKER, 0, false).build());
		}
	}
	
}
