package com.vectorprison.pickaxe;

import org.bukkit.inventory.ItemStack;

import com.vectorprison.VectorPrison;

public class Pickaxes {
	
	private VectorPrison plugin;
	
	public Pickaxes(VectorPrison pl) {
		plugin = pl;
	}
	
	public Integer getCurrentLevel(ItemStack i) {
		
		if(!i.hasItemMeta()) {
			return null;
		}
		
		if(!i.getItemMeta().hasDisplayName()) {
			return null;
		}
		
		if(i.getItemMeta().getDisplayName().contains("")) {
			
		}
		return null;
	}

}
