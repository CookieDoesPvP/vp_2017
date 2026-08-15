package com.vectorprison.api;

import org.bukkit.Material;

public class ToolLevels {
	
	public static int getEssence(Material m, int i) {
		if (m == Material.WOOD_PICKAXE) {
			return  (3500 + (9600 * (i-1)));
		} else if (m == Material.STONE_PICKAXE) {
			return  (5600 + (10800 * (i-1)));
		} else if (m == Material.GOLD_PICKAXE) {
			return  (7000 + (12000 * (i-1)));
		} else if (m == Material.IRON_PICKAXE) {
			return  (8200 + (13200 * (i-1)));
		} else if (m == Material.DIAMOND_PICKAXE) {
			return  (9500 + (14400 * (i-1)));
		}
		return 0;
		
			
	}

}
