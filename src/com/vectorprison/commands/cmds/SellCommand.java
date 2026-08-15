package com.vectorprison.commands.cmds;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.vectorprison.VectorPrison;
import com.vectorprison.commands.PrisonPlayerCommand;
import com.vectorprison.listener.SellListeners;

public class SellCommand extends PrisonPlayerCommand {

	public SellCommand(String name, String permission) {
		super(name, permission);
	}
	
	public SellCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public void execute(Player p, String[] args) {
			  if(args.length == 0) {
				  p.sendMessage(VectorPrison.getInstance().getPrefix() + "§7This shouldn't of happened.. contact a developer.");
				  return;
			  }else{
				  if(!p.getInventory().contains(Material.COAL) && !p.getInventory().contains(Material.COAL_ORE) && !p.getInventory().contains(Material.IRON_INGOT) && !p.getInventory().contains(Material.IRON_ORE) && !p.getInventory().contains(Material.LAPIS_ORE) && !p.getInventory().contains(Material.INK_SACK) && !p.getInventory().contains(Material.GOLD_INGOT) && !p.getInventory().contains(Material.GOLD_ORE) && !p.getInventory().contains(Material.DIAMOND) && !p.getInventory().contains(Material.DIAMOND_ORE) && !p.getInventory().contains(Material.EMERALD) && !p.getInventory().contains(Material.EMERALD_ORE) && !p.getInventory().contains(Material.REDSTONE_ORE) && !p.getInventory().contains(Material.REDSTONE)) {
					  p.sendMessage(plugin.getMessage("sell.empty_inv"));
					  return;
				  }else{
					  if(args[0].equalsIgnoreCase("Shop2")) {
				  confirmTax(p, args[0]);
			  }else{
				  confirm2(p, args[0]);
			  }
				  }
			  }
	}

	@Override
	protected void execute(CommandSender console, String[] args) {
		//console cmds
	}
	
	  public static void confirmTax(Player p, String rank) {
		  Inventory confirm = Bukkit.createInventory(p, 27, "§bConfirmation Menu §3(Tax)");
		  for(int i = 0; i < 27; i++) {
			  ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
			  ItemMeta glassm = glass.getItemMeta();
			  glassm.setDisplayName("§a");
			  glass.setItemMeta(glassm);
			  confirm.setItem(i, glass);
		  }
		  ItemStack paper = new ItemStack(Material.PAPER);
		  ItemMeta paperm = paper.getItemMeta();
		  ItemStack emeraldblock = new ItemStack(Material.EMERALD_BLOCK);
		  ItemMeta emeraldm = paper.getItemMeta();
		  emeraldm.setDisplayName("§a§lConfirm Sell");
		  emeraldblock.setItemMeta(emeraldm);
		  ItemStack redstoneblock = new ItemStack(Material.REDSTONE_BLOCK);
		  ItemMeta redstonem = paper.getItemMeta();
		  redstonem.setDisplayName("§c§lCancel Sell");
		  redstoneblock.setItemMeta(redstonem);
		  confirm.setItem(12, emeraldblock);
		  confirm.setItem(14, redstoneblock);
		  paperm.setDisplayName("§fSell Amount: §6§l$" + SellListeners.checksell(p, rank));
		  paper.setItemMeta(paperm);
		  confirm.setItem(4, paper);
		  p.openInventory(confirm);
	  }
	  public static void confirm2(Player p, String rank) {
		  Inventory confirm = Bukkit.createInventory(p, 27, "§bConfirmation Menu §3(Normal)");
		  for(int i = 0; i < 27; i++) {
			  ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
			  ItemMeta glassm = glass.getItemMeta();
			  glassm.setDisplayName("§a");
			  glass.setItemMeta(glassm);
			  confirm.setItem(i, glass);
		  }
		  ItemStack paper = new ItemStack(Material.PAPER);
		  ItemMeta paperm = paper.getItemMeta();
		  ItemStack emeraldblock = new ItemStack(Material.EMERALD_BLOCK);
		  ItemMeta emeraldm = paper.getItemMeta();
		  emeraldm.setDisplayName("§a§lConfirm Sell");
		  emeraldblock.setItemMeta(emeraldm);
		  ItemStack redstoneblock = new ItemStack(Material.REDSTONE_BLOCK);
		  ItemMeta redstonem = paper.getItemMeta();
		  redstonem.setDisplayName("§c§lCancel Sell");
		  redstoneblock.setItemMeta(redstonem);
		  confirm.setItem(12, emeraldblock);
		  confirm.setItem(14, redstoneblock);
		  paperm.setDisplayName("§fSell Amount: §6§l$" + SellListeners.checksell(p, rank));
		  paper.setItemMeta(paperm);
		  confirm.setItem(4, paper);
		  p.openInventory(confirm);
	  }
	  
	
	
}
