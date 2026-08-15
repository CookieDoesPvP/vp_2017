package com.vectorprison.listener;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ListIterator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.vectorprison.VectorPrison;
import com.vectorprison.api.Eco;

public class SellListeners implements Listener {
	
	  private static VectorPrison plugin;
	
	  public SellListeners(VectorPrison plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	  public void onInventoryClick(InventoryClickEvent e) {
		  if(e.getInventory().getName().contains("§bConfirmation Menu §3(Tax)")) {
			  e.setCancelled(true);
			  if(e.getSlot() == 12) {
				  sellTax((Player) e.getWhoClicked(), "Tax");
				  e.getWhoClicked().closeInventory();
			  }
			  if(e.getSlot() == 14) {
				  e.getWhoClicked().closeInventory();
			  }
		  }
		  if(e.getInventory().getName().contains("§bConfirmation Menu §3(Normal)")) {
			  e.setCancelled(true);
			  if(e.getSlot() == 12) {
				  sell((Player) e.getWhoClicked(), "Shop");
				  e.getWhoClicked().closeInventory();
			  }
			  if(e.getSlot() == 14) {
				  e.getWhoClicked().closeInventory();
			  }
		  }
	  }
	  
		public static String checksell(Player p, String rank)
		  {
		    String sellKey = ChatColor.stripColor(rank);
		    if (sellKey != null)
		    {
		      double amount = 0;
		      for (String key : plugin.getConfig().getConfigurationSection(sellKey).getKeys(false)) {
		        if (p.getInventory().contains(Material.valueOf(key.toUpperCase())))
		        {
		          int item = 0;
		          ListIterator<ItemStack> it = p.getInventory().iterator();
		          while (it.hasNext())
		          {
		            ItemStack current = (ItemStack)it.next();
		            if ((current != null) && (current.getType().equals(Material.valueOf(key)))) {
		              item += current.getAmount();
		            }
		            if (!it.hasNext())
		            {
		              amount += item * plugin.getConfig().getDouble(sellKey + "." + key);
		            }
		          }
		        }
		      }
		      if (amount > 0)
		      {
		    	  double amount2 = round(amount, 2);
		    	  return String.valueOf(amount2);
		      }
		      else
		      {
		        p.sendMessage(plugin.getMessage("sell.empty_inv"));
		        p.closeInventory();
		      }
		    }
			return "Confused :/";
			
		  }
		
		public static double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    BigDecimal bd = new BigDecimal(value);
		    bd = bd.setScale(places, RoundingMode.HALF_UP);
		    return bd.doubleValue();
		}
	public static void sell(Player p, String rank)
	  {
	    String sellKey = ChatColor.stripColor(rank);
	    if (sellKey != null)
	    {
	      double amount = 0;
	      for (String key : plugin.getConfig().getConfigurationSection(sellKey).getKeys(false)) {
	        if (p.getInventory().contains(Material.valueOf(key.toUpperCase())))
	        {
	          int item = 0;
	          ListIterator<ItemStack> it = p.getInventory().iterator();
	          while (it.hasNext())
	          {
	            ItemStack current = (ItemStack)it.next();
	            if ((current != null) && (current.getType().equals(Material.valueOf(key)))) {
	              item += current.getAmount();
	            }
	            if (!it.hasNext())
	            {
	              amount += item * plugin.getConfig().getDouble(sellKey + "." + key);
	              p.getInventory().remove(Material.valueOf(key));
	              p.updateInventory();
	            }
	          }
	        }
	      }
	      if (amount > 0)
	      {
		        p.sendMessage(plugin.getMessage("sell.sold_msg")
		        		.replaceAll("%total%", removeNotation(amount)
		        		.replaceAll("%player%", p.getName())));
	        Eco.addMoney(p, amount);
	      }
	      else
	      {
	    	  p.sendMessage(plugin.getMessage("sell.empty_inv"));
	      }
	    }
	  }
	
	public static void sellTax(Player p, String rank)
	  {
	    String sellKey = ChatColor.stripColor(rank);
	    if (sellKey != null)
	    {
	      double amount = 0;
	      for (String key : plugin.getConfig().getConfigurationSection(sellKey).getKeys(false)) {
	        if (p.getInventory().contains(Material.valueOf(key.toUpperCase())))
	        {
	          int item = 0;
	          ListIterator<ItemStack> it = p.getInventory().iterator();
	          while (it.hasNext())
	          {
	            ItemStack current = (ItemStack)it.next();
	            if ((current != null) && (current.getType().equals(Material.valueOf(key)))) {
	              item += current.getAmount();
	            }
	            if (!it.hasNext())
	            {
	              amount += item * plugin.getConfig().getDouble(sellKey + "." + key);
	              p.getInventory().remove(Material.valueOf(key));
	              p.updateInventory();
	            }
	          }
	        }
	      }
	      if (amount > 0)
	      {
	        p.sendMessage(plugin.getMessage("sell.sold_msg_taxed")
	        		.replaceAll("%total%", removeNotation(amount)
	        		.replaceAll("%player%", p.getName())));
	        Eco.addMoney(p, amount);
	      }
	      else
	      {
	        p.sendMessage(plugin.getMessage("sell.empty_inv"));
	      }
	    }
	  }

	  public static String removeNotation(double number)
	  {
	    return NumberFormat.getInstance().format(number);
	  }
	
}
