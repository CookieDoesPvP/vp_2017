package com.vectorprison.api;

import org.bukkit.entity.Player;

import com.vectorprison.economy.Economy;

public class Eco {
	
	  public static double getMoney(Player p)
	  {
	    return Economy.getMoney(p);
	  }
	
	public static void addMoney(Player p, double amount) {
		Economy.addMoney(p, amount, true);
		//BalanceUpdateEvent event = new BalanceUpdateEvent(p);
		//MineParadise.getInstance().getServer().getPluginManager().callEvent(event);
	}
	
	public static void removeMoney(Player p, double amount) {
		Economy.subtractMoney(p, amount, false);
		//BalanceUpdateEvent event = new BalanceUpdateEvent(p);
		//MineParadise.getInstance().getServer().getPluginManager().callEvent(event);
	}
	
	
	
}
