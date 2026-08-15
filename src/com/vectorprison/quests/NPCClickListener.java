package com.vectorprison.quests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.vectorprison.VectorPrison;
import com.vectorprison.api.PlayerData;
import com.vectorprison.bases.BaseCreator;
import com.vectorprison.commands.cmds.SellCommand;
import com.vectorprison.npc.BlackSmith;
import com.vectorprison.npc.Chef;
import com.vectorprison.npc.TutorialInv;
import com.vectorprison.util.ItemBuilder;
import com.vectorprison.util.ItemUtil;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class NPCClickListener implements Listener {
	private VectorPrison plugin;

	public NPCClickListener(VectorPrison plugin) {
		this.plugin = plugin;
	} 

	@EventHandler
	public void onInteractNPC(NPCRightClickEvent e){
		if (e.getNPC().getName() == null) {
			return;
		}
		
		Player p = e.getClicker().getPlayer();
		
		if (e.getNPC().getName().contains(ChatColor.stripColor("Quests"))) {
			Inventory inv = Bukkit.createInventory(e.getClicker().getPlayer(), 27, "Quests");
			
			ItemStack penis = new ItemBuilder(Material.BOOK, 1)
					.setName(ChatColor.YELLOW + "Quest Statistics")
					.addLoreLine(" ")
					.addLoreLine(ChatColor.GRAY + "Quests Completed: " + ChatColor.YELLOW + (PlayerData.getQuests(p, "daily") + PlayerData.getQuests(p, "special") + PlayerData.getQuests(p, "monthly")))
					.addLoreLine(" ")
					.addLoreLine(ChatColor.GRAY + "Starter Quests Completed: " + ChatColor.YELLOW + PlayerData.getQuests(p, "regular"))
					.addLoreLine(ChatColor.GRAY + "Daily Quests Completed: " + ChatColor.YELLOW + PlayerData.getQuests(p, "daily"))
					.addLoreLine(ChatColor.GRAY + "Weekly Quests Completed: " + ChatColor.YELLOW + PlayerData.getQuests(p, "weekly"))
					.addLoreLine(" ")
					.toItemStack();
			
			ItemStack placeholder = new ItemBuilder(Material.EMERALD_BLOCK, 1)
					.setName(ChatColor.YELLOW + "Weekly Quests")
					.addLoreLine(" ")
					.addLoreLine(ChatColor.GRAY + "These Quests run from Sunday to Sunday.")
					.addLoreLine(ChatColor.GRAY + "You are able to complete these for one week only.")
					.addLoreLine(" ")
					.addLoreLine(ChatColor.GRAY + "Click here to view the quests avaliable to you!")
					.addLoreLine(" ")
					.toItemStack();
			
			ItemStack placeholder2 = new ItemBuilder(Material.DIAMOND_BLOCK, 1)
					.setName(ChatColor.YELLOW + "Daily Quests")
					.addLoreLine(" ")
					.addLoreLine(ChatColor.GRAY + "These Quests run for 24 hours only!")
					.addLoreLine(ChatColor.GRAY + "There will be a variety of quests every single day!")
					.addLoreLine(" ")
					.addLoreLine(ChatColor.GRAY + "Click here to view the quests of the day!")
					.addLoreLine(" ")
					.toItemStack();
			
			ItemStack placeholder3 = new ItemBuilder(Material.GOLD_BLOCK, 1)
					.setName(ChatColor.YELLOW + "Starter Quests")
					.addLoreLine(" ")
					.addLoreLine(ChatColor.RED + "These quests will very rarely update..")
					.addLoreLine(" ")
					.addLoreLine(ChatColor.GRAY + "Click here to view the avaliable quests.")
					.addLoreLine(" ")
					.toItemStack();
			
			ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
					.setDyeColor(DyeColor.YELLOW)
					.toItemStack();
			
			for(int i = 0; i < 27; i++) {
			inv.setItem(i, glass);
			}
		
			inv.setItem(4, penis);
			inv.setItem(11, placeholder);
			inv.setItem(13, placeholder2);
			inv.setItem(15, ItemUtil.addGlow(placeholder3));

			
			
			p.openInventory(inv);
			
		} else if (e.getNPC().getName().contains(ChatColor.stripColor("Chef"))) {
			new Chef(p).open();
			
		} else if (e.getNPC().getName().contains(ChatColor.stripColor("Blacksmith"))) {
			new BlackSmith(p).open();
		} else if (e.getNPC().getName().contains(ChatColor.stripColor("Base Creator"))) {
			new BaseCreator(p).open();
		} else if (e.getNPC().getName().contains(ChatColor.stripColor("Tutorial"))) {
			new TutorialInv(p).open();
		} else if (e.getNPC().getName().contains(ChatColor.stripColor("Base Merchant"))) {
			if(p.hasPermission("vectorprison.groups.admin")) {
			new BaseCreator(p).open();
			}else{
				p.sendMessage("§c§lSorry! §7This feature is in development. Stay tuned on our forums for updates.");
			}
		} else if(e.getNPC().getName().contains(ChatColor.stripColor("Worker"))) {
			p.sendMessage("§c§lSorry! §7This feature is in development. Stay tuned on our forums for updates.");
		} else if(e.getNPC().getName().contains(ChatColor.stripColor("Bank"))) {
			p.sendMessage("§c§lSorry! §7This feature is in development. Stay tuned on our forums for updates.");
		} else if(e.getNPC().getName().contains(ChatColor.stripColor("Base Manager"))) {
			p.sendMessage("§c§lSorry! §7This feature is in development. Stay tuned on our forums for updates.");
		} else if (e.getNPC().getName().contains(ChatColor.stripColor("Merchant (Tax)"))) {
			 if(!p.getInventory().contains(Material.COAL) && !p.getInventory().contains(Material.COAL_ORE) && !p.getInventory().contains(Material.IRON_INGOT) && !p.getInventory().contains(Material.IRON_ORE) && !p.getInventory().contains(Material.LAPIS_ORE) && !p.getInventory().contains(Material.INK_SACK) && !p.getInventory().contains(Material.GOLD_INGOT) && !p.getInventory().contains(Material.GOLD_ORE) && !p.getInventory().contains(Material.DIAMOND) && !p.getInventory().contains(Material.DIAMOND_ORE) && !p.getInventory().contains(Material.EMERALD) && !p.getInventory().contains(Material.EMERALD_ORE) && !p.getInventory().contains(Material.REDSTONE_ORE) && !p.getInventory().contains(Material.REDSTONE)) {
			        p.sendMessage(plugin.getMessage("sell.empty_inv"));
				  return;
			  }else{
			  SellCommand.confirmTax(p, "Tax");
			  }
		} else if(e.getNPC().getName().contains(ChatColor.stripColor("Merchant"))) {
			if(!p.getInventory().contains(Material.COAL) && !p.getInventory().contains(Material.COAL_ORE) && !p.getInventory().contains(Material.IRON_INGOT) && !p.getInventory().contains(Material.IRON_ORE) && !p.getInventory().contains(Material.LAPIS_ORE) && !p.getInventory().contains(Material.INK_SACK) && !p.getInventory().contains(Material.GOLD_INGOT) && !p.getInventory().contains(Material.GOLD_ORE) && !p.getInventory().contains(Material.DIAMOND) && !p.getInventory().contains(Material.DIAMOND_ORE) && !p.getInventory().contains(Material.EMERALD) && !p.getInventory().contains(Material.EMERALD_ORE) && !p.getInventory().contains(Material.REDSTONE_ORE) && !p.getInventory().contains(Material.REDSTONE)) {
				 p.sendMessage(plugin.getMessage("sell.empty_inv"));
				  return;
			  }else{
			  SellCommand.confirm2(p, "Shop");
			  }
		}
		
		
		
		
	}
	
	@EventHandler
	public void inInt(InventoryClickEvent e){
		if (e.getInventory().getName() == null) {
			
		}
		
		if (ChatColor.stripColor(e.getInventory().getName()).contains("Quests")) {
			e.setCancelled(true);
			if (e.getCurrentItem().getType() == Material.GOLD_BLOCK) {
				StarterQuests.openInventory((Player)e.getWhoClicked());
			}
			
		}
		
		
		
		if (e.getInventory().getName().contains("Robin's Rewards")) {
			
		}
	}
}
