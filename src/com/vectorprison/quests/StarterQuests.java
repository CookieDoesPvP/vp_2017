package com.vectorprison.quests;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.vectorprison.util.ItemBuilder;

public class StarterQuests {
	

	public static void openInventory(Player p) {
		
		Inventory inv = Bukkit.createInventory(p, 54, "Starter Quests");
		
		
		ItemStack i = new ItemBuilder(Material.INK_SACK, 1)
					.setDyeColor(DyeColor.SILVER)
					.setName("Miner 1")
					.addLoreLine("Click here to start.")
					.addLoreLine(" ")
					.addLoreLine("&6Goal: &fBreak 100 blocks")
					.addLoreLine("&cTimer: &f1hour")
					.addLoreLine("&7&m--------------------")
					.addLoreLine("&e&lREWARDS:")
					.addLoreLine("&7- &a $0.10")
					.addLoreLine("&7- &a Small amount of mining XP (based on your level)")
					.addLoreLine("&7&m--------------------")
					.toItemStack();
		inv.setItem(10, i);
		
		ItemStack i2= new ItemBuilder(Material.COAL_BLOCK, 1)
				.setName("&cMiner 1++")
				.addLoreLine("&cQuest Locked")
				.addLoreLine(" ")
				.addLoreLine("&7You are unable to complete this quest, please fuck off.")
				.toItemStack();
		
		inv.setItem(11, i2);
		inv.setItem(12, i2);
		inv.setItem(13, i2);
		inv.setItem(14, i2);
		inv.setItem(15, i2);
		inv.setItem(16, i2);
		
		inv.setItem(19, i2);
		inv.setItem(20, i2);
		inv.setItem(21, i2);
		inv.setItem(22, i2);
		inv.setItem(23, i2);
		inv.setItem(24, i2);
		inv.setItem(25, i2);
		
		p.openInventory(inv);
		
	}
	

}
