package com.vectorprison.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.vectorprison.VectorPrison;
import com.vectorprison.api.ServerData;
import com.vectorprison.api.ToolLevels;
import com.vectorprison.tools.EnchantmentHandler;

import net.milkbowl.vault.item.Items;

public class InventoryEvents implements Listener {
	private VectorPrison plugin;

	public InventoryEvents(VectorPrison plugin) {
		this.plugin = plugin;
	}
	
	public static ArrayList<String> array = new ArrayList<String>();
	public static ArrayList<String> array2 = new ArrayList<String>();
	
	// Block item frame duping
	@EventHandler
	public void onInteractItemFrame(PlayerInteractEntityEvent event) {
		if (event.getRightClicked().getType().equals(EntityType.ITEM_FRAME) 
				&& event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		String m = event.getItemDrop().getItemStack().getType().toString().toLowerCase();
		Player p = event.getPlayer();

		if (m.contains("pickaxe")) {
			World w = p.getWorld();
			for(ProtectedRegion r : WGBukkit.getRegionManager(p.getWorld()).getApplicableRegions(event.getItemDrop().getLocation())) {
				if(r.getId().equalsIgnoreCase("drugportal")){
					
					ItemStack i = event.getItemDrop().getItemStack();
					ItemMeta meta = i.getItemMeta();
					String dNameLevel = ChatColor.stripColor(meta.getDisplayName()).replaceAll("[^0-9]", "");
					List<String> lore = meta.getLore();
					String lol = ChatColor.stripColor(lore.get(lore.size()-4));
					String[] parts = lol.split("/");
					String xp = parts[0].replaceAll("[^0-9]", "");		
					
					int lev = Integer.valueOf(dNameLevel);
					int nextlev = lev + 1;
			 		int xpnum = Integer.valueOf(xp); 
			 		
			 		if (xpnum == ToolLevels.getEssence(event.getItemDrop().getItemStack().getType(), lev)) {
			 			event.getItemDrop().remove();

			 			
			 			
			 			
			 			
			 			new EnchantmentHandler(p, "eff", i).refreshLore();
			 			
			 			//sort out the inv
			 			
			 			
			 			
			 			//Simple Enchant 1
			 			int slot= ServerData.randomNum(3);
			 			if (slot == 1) {
			 				
			 			} else if (slot == 2) {
			 				
			 			} else if (slot == 3) {
			 				
			 			} else if (slot == 4) {
			 			
			 			}
			 			
			 			//Should it be another simple or a second tier enchant
			 			int slot2 = ServerData.randomNum(3);
			 			if (slot2 == 1) {
			 				
			 			} else if (slot2 == 2) {
			 				
			 			} else if (slot2 == 3) {
			 				
			 			} else if (slot2 == 4) {
			 				
			 			}
			 			
			 			//Tier 2 enchants
			 			
			 			
			 			
			 			//ultimate enchants
			 			

						
						p.sendMessage(VectorPrison.getInstance().getPrefix() + "Succesfully enchanted your pick.");
					
			 		}
					
				return;
				}
			}
			
			
			
			
			
			if (array2.contains(p.getName())) {
				array2.remove(p.getName());
				return;
			}
			
			if (array.contains(p.getName())) {
				array.remove(p.getName());
			}
			
			
			array.add(p.getName());
			event.setCancelled(true);
			p.sendMessage(this.plugin.getMessageNoPrefix("prefix") + "Please confirm that you wish to drop your pickaxe by typing 'YES' into chat.");
			
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() { 
				public void run() {
					if (array.contains(p.getName())) {
						p.sendMessage(plugin.getMessageNoPrefix("prefix") + "You failed to confirm dropping your pickaxe within 10 seconds, please try again.");
						array.remove(p.getName());
					} else {
						return;
					}
					}
				}, 20 * 15); // 20 (one second in ticks) * 5 (seconds to wait)
			
		} else{
			return;
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		
	}
}