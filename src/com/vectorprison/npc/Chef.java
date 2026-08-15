package com.vectorprison.npc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.vanquishnetwork.menu.Menu;
import org.vanquishnetwork.util.FormatUtil;

import com.vectorprison.VectorPrison;
import com.vectorprison.api.Eco;
import com.vectorprison.util.ItemBuilder;

public class Chef
 extends Menu
{
  private static final String TITLE = FormatUtil.format("&b&lChef", new Object[0]);  
  
  public Chef(Player player)
  {
    super(player, TITLE, 27);
  }
  
  protected void onInvClick(InventoryClickEvent event)
  {
    if ((event.getCurrentItem() == null) || (event.getCurrentItem().getType() == null) || 
      (!event.getCurrentItem().hasItemMeta()) || 
      (!event.getCurrentItem().getItemMeta().hasDisplayName())) {
      return;
    }
    event.setCancelled(true);
    if (event.getCurrentItem().getType() == Material.WOOL)
    {
      close();
      return;
    }
      if (event.getCurrentItem().getType().equals(Material.COOKIE)) {
    	  
    	Player p = (Player) event.getWhoClicked();
    	  
    	if(Eco.getMoney(p) < 0.10) {
    		p.sendMessage(VectorPrison.getInstance().getMessage("pay.not enough"));
    	}else{
    		Eco.removeMoney(p, 0.10);
    		p.getInventory().addItem(new ItemStack(Material.COOKIE));
    		p.playSound(p.getLocation(), Sound.NOTE_PLING, 1L, 1L);
    	}
    	 
    	 
      } 
      if (event.getCurrentItem().getType().equals(Material.APPLE)) {
    	  
      	Player p = (Player) event.getWhoClicked();
      	  
      	if(Eco.getMoney(p) < 0.50) {
      		p.sendMessage(VectorPrison.getInstance().getMessage("pay.not enough"));
      	}else{
      		Eco.removeMoney(p, 0.50);
      		p.getInventory().addItem(new ItemStack(Material.APPLE));
      		p.playSound(p.getLocation(), Sound.NOTE_PLING, 1L, 1L);
      	}
      	 
      	 
        }
      if (event.getCurrentItem().getType().equals(Material.BREAD)) {
    	  
        	Player p = (Player) event.getWhoClicked();
        	  
        	if(Eco.getMoney(p) < 1.00) {
        		p.sendMessage(VectorPrison.getInstance().getMessage("pay.not enough"));
        	}else{
        		Eco.removeMoney(p, 1.00);
        		p.getInventory().addItem(new ItemStack(Material.BREAD));
        		p.playSound(p.getLocation(), Sound.NOTE_PLING, 1L, 1L);
        	}
        	 
        	 
          }
      if (event.getCurrentItem().getType().equals(Material.COOKED_CHICKEN)) {
    	  
        	Player p = (Player) event.getWhoClicked();
        	  
        	if(Eco.getMoney(p) < 1.50) {
        		p.sendMessage(VectorPrison.getInstance().getMessage("pay.not enough"));
        	}else{
        		Eco.removeMoney(p, 1.50);
        		p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN));
        		p.playSound(p.getLocation(), Sound.NOTE_PLING, 1L, 1L);
        	}
        	 
        	 
          }
      if (event.getCurrentItem().getType().equals(Material.COOKED_BEEF)) {
    	  
        	Player p = (Player) event.getWhoClicked();
        	  
        	if(Eco.getMoney(p) < 2.50) {
        		p.sendMessage(VectorPrison.getInstance().getMessage("pay.not enough"));
        	}else{
        		Eco.removeMoney(p, 2.50);
        		p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF));
        		p.playSound(p.getLocation(), Sound.NOTE_PLING, 1L, 1L);
        	}
        	 
        	 
          }
  }
  
  protected void setContents(Inventory inventory)
  {
	inventory.setItem(4, new ItemBuilder(Material.NETHER_STAR, 1).setName(ChatColor.GOLD + player.getName() + "'s Info")
	.addLoreLine(ChatColor.GRAY + "Balance: " + ChatColor.AQUA + "$" + VectorPrison.getFormattedDouble(Eco.getMoney(player)))
	.toItemStack());	
    inventory.setItem(11, new ItemBuilder(Material.COOKIE, 1).setName("§eCookie")
    		.addLoreLine("§7Click to purchase for $0.10").toItemStack());
    inventory.setItem(12, new ItemBuilder(Material.APPLE, 1).setName("§eApple")
    		.addLoreLine("§7Click to purchase for $0.50").toItemStack());
    inventory.setItem(13, new ItemBuilder(Material.BREAD, 1).setName("§eBread")
    		.addLoreLine("§7Click to purchase for $1.00").toItemStack());
    inventory.setItem(14, new ItemBuilder(Material.COOKED_CHICKEN, 1).setName("§eCooked Chicken")
    		.addLoreLine("§7Click to purchase for $1.50").toItemStack());
    inventory.setItem(15, new ItemBuilder(Material.COOKED_BEEF, 1).setName("§eSteak")
    		.addLoreLine("§7Click to purchase for $2.50").toItemStack());
    
	

	
  }
 
}
