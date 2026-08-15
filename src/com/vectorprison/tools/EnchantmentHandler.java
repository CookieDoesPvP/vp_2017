package com.vectorprison.tools;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.vectorprison.VectorPrison;
import com.vectorprison.api.ServerData;
import com.vectorprison.api.ToolLevels;

public class EnchantmentHandler {
	
	private Player p;
	private String enchant;
	private ItemStack i;
	private ItemMeta meta;
	private List<String> lores;
	
	
	public EnchantmentHandler(Player p, String enchant, ItemStack i) {
		this.p = p;
		this.enchant = enchant;
		this.i = i;
		this.meta = i.getItemMeta();
		this.lores = meta.getLore();
	}
	
	public int getEnchantMaxValue(String enchantocheck) {
		switch (enchantocheck) {
		
		case "nourishment":
			return 1;
		
		case "ore":
		case "enricher":
		case "enhancer":	
			return 3;
		
		case "replenish":
		case "eff":
		case "essence":			
		case "collector":
		case "drain":
		case "shard":
			return 5;	
		}
		return 0;
	}
	
	public int getEnchantmentValue(String enchantocheck) {
		for(String line : lores) {
			if(ChatColor.stripColor(line).toLowerCase().contains(enchantocheck)) {
				String[] parts = ChatColor.stripColor(line).split(" ");
				
				if (parts.length > 2) {
					int x = Integer.parseInt(parts[2]);
					return x;
				}
					int x = Integer.parseInt(parts[1]);
					return x;
				
			} 
		}
		return 0;
		
	}
	
	public void refreshLore() {	
		String dName2 = ChatColor.stripColor(meta.getDisplayName()).replaceAll("[^0-9]", "");
		int level = Integer.valueOf(dName2);
		
		String line1 = lores.get(0);
		String fnal = lores.get(lores.size()-1);

		//tier1
		int havoc = getEnchantmentValue("havoc");
		int enricher = getEnchantmentValue("enricher");
		int essence = getEnchantmentValue("collector");
		
		//tier2
		int enhancer = getEnchantmentValue("enhancer");
		int pulveriser = getEnchantmentValue("pulveriser");
		int xpdrain = getEnchantmentValue("drain");
		int replenish = getEnchantmentValue("replenish");
		
		//tier3
		int nourishment = getEnchantmentValue("nourishment");
		int shardcollector = getEnchantmentValue("shard");
		int orediscoverer = getEnchantmentValue("ore");
		int efficiency = 0;
		for(String line : lores) {
		if (ChatColor.stripColor(line).contains("efficiency")) {
			efficiency = meta.getEnchantLevel(Enchantment.DIG_SPEED);
			//efficiency = 0;
			
			}
		}
			
		
		//add enchant
			switch (enchant) {
			case "eff":
				if (efficiency+1 <= 5) {
					meta.addEnchant(Enchantment.DIG_SPEED, efficiency+1, true);
					efficiency++;
					//p.sendMessage("debug, new enchant is" + efficiency);
					meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						
					}
				break;
			case "enricher":
				if (enricher+1 <= 3) {
					enricher++;
				}
				break;
			case "essence":
				if (essence+1 <= 5) {
					essence++;
				}
				break;
			}
			
			//sort out name, lore and upgrading pick to incremental level.
			meta.setDisplayName("§bWooden Pickaxe §7<§b" + level++ + "§7>");
			lores.clear();
			lores.add(line1);
			lores.add("");
			
			//enchants.
			if (enricher != 0) {
				lores.add(ChatColor.translateAlternateColorCodes('&', "&6&lOre Enricher " + enricher));
			}
			
			if (essence != 0) {
				lores.add(ChatColor.translateAlternateColorCodes('&', "&6&lEssence Collector " + essence));	
			}
			
			if (replenish != 0) {
				lores.add(ChatColor.translateAlternateColorCodes('&', "&9&lReplenish " + replenish));	
			}
			
			if (efficiency != 0) {
				lores.add(ChatColor.translateAlternateColorCodes('&', "&f&lEfficiency " + efficiency));
			}
			
			lores.add("");
			lores.add("§7Pickaxe Essence: 0"  + " / " + ToolLevels.getEssence(i.getType(), level++));
			//p.sendMessage((i.getType().toString()));
			//p.sendMessage("dbug"  + (level + (level+1)));
			lores.add("§7XP to level up: " + ToolLevels.getEssence(i.getType(), level++));
			lores.add("");
			lores.add(fnal);
			
			meta.setLore(lores);
			i.setItemMeta(meta);

			p.getInventory().setItemInHand(i);
			p.updateInventory();
			
		
	}
	
	
	public String getRandomSimpleEnchant() {
		boolean valid = false;
		
		while (!valid) {
			int slot= ServerData.randomNum(4);
		
			switch (slot) {
				case 1:
					return "eff";
				case 2:
					return "ore";
				case 3:
					return "nourishment";
				case 4:
					return "shard";
		
		}
		}
		return "eff";
		
		
	}
	
	public String getRandomTier2Enchant() {
		boolean valid = false;
		
		while (!valid) {
			int slot= ServerData.randomNum(4);
		
			switch (slot) {
				case 1:
					return "replenish";
				case 2:
					return "drain";
				case 3:
					return "pulveriser";
				case 4:
					return "enhancer";
		
		}
		}
		return "pulveriser";
		
		
	}
	
	public void getRandomTier3Enchant() {
		int slot= ServerData.randomNum(3);
		
		
		
	}
	
	
	
	
		
}
