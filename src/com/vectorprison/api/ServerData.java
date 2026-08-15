package com.vectorprison.api;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ServerData {
	
	public void getTPS() {
		
	}
	
	public int getPlayerCount() {
		return Bukkit.getOnlinePlayers().size();
	}
	
	public int getMaxPlayers() {
		return Bukkit.getMaxPlayers();
	}

	public static boolean randomGenerator(int nextint) {
		Random r = new Random();
	    if (r.nextInt(nextint) == 1) {
	    	return true;
	    }else{
		    return false;
	    }
	}
	
	public static int randomNum(int nextint) {
		Random r = new Random();
	    return r.nextInt(nextint) ;
	}
	
	public static boolean isMineableBlock(Block b) {
		if (b.getType() == Material.COAL_ORE || (b.getType() == Material.COAL_BLOCK) 
				|| (b.getType() == Material.IRON_ORE) || (b.getType() == Material.IRON_BLOCK)
				|| (b.getType() == Material.GOLD_ORE) || (b.getType()==Material.GOLD_BLOCK)
				|| (b.getType() == Material.REDSTONE_ORE) || (b.getType() == Material.REDSTONE_BLOCK)
				|| (b.getType() == Material.GLOWING_REDSTONE_ORE)
				|| (b.getType() == Material.LAPIS_ORE) || (b.getType() == Material.LAPIS_BLOCK)
				|| (b.getType() == Material.DIAMOND_ORE) || (b.getType() ==Material.DIAMOND_BLOCK)
				|| (b.getType() == Material.EMERALD_ORE) || (b.getType() ==Material.EMERALD_BLOCK)) {
			
			return true;
			
		} else  {
			return false;
		}
		
	}
	
	public static boolean isOre(Material m) {
		if(m == Material.COAL_ORE 
				|| m == Material.IRON_ORE 
				|| m == Material.LAPIS_ORE 
				|| m == Material.GLOWING_REDSTONE_ORE 
				|| m == Material.REDSTONE_ORE 
				|| m == Material.GOLD_ORE 
				|| m == Material.DIAMOND_ORE 
				|| m == Material.EMERALD_ORE) {
			return true;
		}else{
		return false;
		}
	}
	
}
