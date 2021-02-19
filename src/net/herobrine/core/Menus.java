package net.herobrine.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menus {
	public static void applyGameModeSelection(Player player) {
		Inventory selectionMenu = Bukkit.createInventory(null, 27, ChatColor.GRAY + "Game Selection");

		ItemStack sw = new ItemStack(Material.EYE_OF_ENDER, 1);
		ItemMeta swMeta = sw.getItemMeta();
		swMeta.setDisplayName(ChatColor.GREEN + "Skywars");
		sw.setItemMeta(swMeta);
		ItemStack std = new ItemStack(Material.LAVA_BUCKET, 1);
		ItemMeta stdMeta = std.getItemMeta();
		stdMeta.setDisplayName(ChatColor.RED + "Survive The Disaster");
		std.setItemMeta(stdMeta);
		ItemStack oitc = new ItemStack(Material.GRASS, 1);
		ItemMeta oitcMeta = oitc.getItemMeta();
		oitcMeta.setDisplayName(ChatColor.GREEN + "Block Hunt");
		oitc.setItemMeta(oitcMeta);
		ItemStack sg = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta sgMeta = sg.getItemMeta();
		sgMeta.setDisplayName(ChatColor.AQUA + "Survival Games");
		sg.setItemMeta(sgMeta);
		ItemStack bw = new ItemStack(Material.BED, 1);
		ItemMeta bwMeta = bw.getItemMeta();
		bwMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Bed Wars");
		bw.setItemMeta(bwMeta);
		selectionMenu.setItem(4, sg);
		selectionMenu.setItem(12, sw);
		selectionMenu.setItem(13, oitc);
		selectionMenu.setItem(14, bw);
		selectionMenu.setItem(22, std);
		player.openInventory(selectionMenu);
	}
}
