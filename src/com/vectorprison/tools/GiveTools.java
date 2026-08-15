package com.vectorprison.tools;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.vectorprison.VectorPrison;
import com.vectorprison.api.Eco;
import com.vectorprison.util.ItemBuilder;

public class GiveTools {
	private VectorPrison plugin; 
	
	
	public GiveTools(VectorPrison plugin) {
		this.plugin = plugin;
	}
	
	public static void giveTools(Material t, Player p, boolean free) {
		switch(t.toString()) {
			case "WOOD_PICKAXE":
				
				if (Eco.getMoney(p) >= 15 || free) {
					
					if (!free) { 
					Eco.removeMoney(p, 15);
					
					}
					
					ItemStack is =  new ItemBuilder(t, 1)
							.setName("§bWooden Pickaxe §7<§b1§7>")
							.addLoreLine("§7I can only mine Coal and Iron.")
							.addLoreLine("  ")
							.addLoreLine("§7Pickaxe Essence: 0 / 3500")
							.addLoreLine("§7XP to level up: 3500")
							.addLoreLine("&7 ")
							.addLoreLine("§7Required: §7Mining Level §e0").toItemStack();
					
					ItemMeta im = is.getItemMeta();
					im.spigot().setUnbreakable(true);
					is.setItemMeta(im);
							
							p.getInventory().addItem(is);
							
							
					
				} else {
					p.sendMessage(VectorPrison.getInstance().getMessage("pickaxe.notenough").replaceAll("<amount>", "$200"));
				}
				
				break;
			case "STONE_PICKAXE":
				if (Eco.getMoney(p) >= 200 || free) {
					
					if (!free) { 
					Eco.removeMoney(p, 200);
					
					}
					
					ItemStack is =  new ItemBuilder(t, 1)
							.setName("§bStone Pickaxe §7<§b1§7>")
							.addLoreLine("§7I can mine Coal, Iron and Lapis!")
							.addLoreLine("  ")
							.addLoreLine("§7Pickaxe Essence: 0 / 5600")
							.addLoreLine("§7XP to level up: 5600")
							.addLoreLine("&7 ")
							.addLoreLine("§7Required: §7Mining Level §e30").toItemStack();
					
					ItemMeta im = is.getItemMeta();
					im.spigot().setUnbreakable(true);
					is.setItemMeta(im);
							
							p.getInventory().addItem(is);
							
							
					
				} else {
					p.sendMessage(VectorPrison.getInstance().getMessage("pickaxe.notenough").replaceAll("<amount>", "$200"));
				}
				break;
			case "GOLD_PICKAXE":
				if (Eco.getMoney(p) >= 2000 || free) {
					
					if (!free) { 
					Eco.removeMoney(p, 2000);
					
					}
					
					ItemStack is =  new ItemBuilder(t, 1)
							.setName("§bGold Pickaxe §7<§b1§7>")
							.addLoreLine("§7I can mine Coal, Iron, Lapis and Redstone!")
							.addLoreLine("  ")
							.addLoreLine("§7Pickaxe Essence: 0 / 7100")
							.addLoreLine("§7XP to level up: 7100")
							.addLoreLine("&7 ")
							.addLoreLine("§7Required: §7Mining Level §e50").toItemStack();
					
					
					ItemMeta im = is.getItemMeta();
					im.spigot().setUnbreakable(true);
					is.setItemMeta(im);
							
							p.getInventory().addItem(is);
							
							
					
				} else {
					p.sendMessage(VectorPrison.getInstance().getMessage("pickaxe.notenough").replaceAll("<amount>", "$2000"));
				}
				break;
			case "IRON_PICKAXE":
				if (Eco.getMoney(p) >= 999999999 || free) {
					
					if (!free) { 
					Eco.removeMoney(p, 2000);
					
					}
					
				ItemStack is =  new ItemBuilder(t, 1)
							.setName("§bIron Pickaxe §7<§b1§7>")
							.addLoreLine("§7I can mine Coal, Iron, Lapis, Redstone and Gold!")
							.addLoreLine("  ")
							.addLoreLine("§7Pickaxe Essence: 0 / 8400")
							.addLoreLine("§7XP to level up: 8400")
							.addLoreLine("&7 ")
							.addLoreLine("§7Required: §7Mining Level §e70").toItemStack();
				
				
				ItemMeta im = is.getItemMeta();
				im.spigot().setUnbreakable(true);
				is.setItemMeta(im);
							
							p.getInventory().addItem(is);
				} else {
					p.sendMessage(VectorPrison.getInstance().getMessage("pickaxe.notenough").replaceAll("<amount>", "$99999999"));
				}
			break;		
						
					
			case "DIAMOND_PICKAXE":
				
				ItemStack is =  new ItemBuilder(t, 1)
						.setName("§bDiamond Pickaxe §7<§b1§7>")
						.addLoreLine("§7I can mine every material!")
						.addLoreLine("  ")
						.addLoreLine("§7Pickaxe Essence: 0 / 10000")
						.addLoreLine("§7XP to level up: 10000")
						.addLoreLine("§7Required: §7Mining Level §e90").toItemStack();
				
				ItemMeta im = is.getItemMeta();
				im.spigot().setUnbreakable(true);
				is.setItemMeta(im);
				
				p.getInventory().addItem(is);
				break;
				
			default:
				break;
				
				
		}
	}
		
		
		
	}
	
