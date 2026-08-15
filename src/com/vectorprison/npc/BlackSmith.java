package com.vectorprison.npc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.vanquishnetwork.menu.Menu;
import org.vanquishnetwork.util.FormatUtil;

import com.vectorprison.VectorPrison;
import com.vectorprison.api.Eco;
import com.vectorprison.api.PlayerData;
import com.vectorprison.tools.GiveTools;
import com.vectorprison.util.ItemBuilder;

public class BlackSmith
 extends Menu
{
  private static final String TITLE = FormatUtil.format("&b&lBlack Smith", new Object[0]);  
  
  public BlackSmith(Player player)
  {
    super(player, TITLE, 54);
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
    
    if (!event.getClickedInventory().getName().contains("Smith")) {
    	return;
    }
    
      if (event.getCurrentItem().getType().equals(Material.WOOD_PICKAXE)) {
    	  
    	 GiveTools.giveTools(Material.WOOD_PICKAXE, player, false);
    	 
      } else if (event.getCurrentItem().getType().equals(Material.STONE_PICKAXE))  {
    	  
    	 GiveTools.giveTools(Material.STONE_PICKAXE, player, false);
    	 
      }  else if (event.getCurrentItem().getType().equals(Material.GOLD_PICKAXE)) {
    	  
    	  GiveTools.giveTools(Material.GOLD_PICKAXE, player, false);
    	  
      }
  }
  
  protected void setContents(Inventory inventory)
  {
	inventory.setItem(4, new ItemBuilder(Material.NETHER_STAR, 1).setName(ChatColor.GOLD + player.getName() + "'s Info")
	    	.addLoreLine(ChatColor.GRAY + "Balance: " + ChatColor.AQUA + "$" + VectorPrison.getFormattedDouble(Eco.getMoney(player)))
	    	.addLoreLine(ChatColor.GRAY + "Mining Level: " + ChatColor.AQUA + PlayerData.getPlayerLevel(player))
	    	.toItemStack());
	    
    inventory.setItem(10, new ItemBuilder(Material.WOOD_PICKAXE, 1).setName("Wooden Pickaxe")
    		.addLoreLine("This pickaxe can mine COAL and IRON")
    		.addLoreLine("You must be mining level 10 to use this pickaxe")
    		.addLoreLine("It costs $15, click here to purchase").toItemStack());
	
    inventory.setItem(19, new ItemBuilder(Material.STONE_PICKAXE, 1).setName("Stone Pickaxe")
    		.addLoreLine("This pickaxe can mine COAL, IRON and LAPIS")
    		.addLoreLine("You must be mining level 30 to use this pickaxe")
    		.addLoreLine("It costs $200, click here to purchase").toItemStack());
    
    inventory.setItem(28, new ItemBuilder(Material.GOLD_PICKAXE, 1).setName("Gold Pickaxe")
    		.addLoreLine("This pickaxe can mine COAL, IRON, LAPIS and GOLD")
    		.addLoreLine("You must be mining level 50 to use this pickaxe")
    		.addLoreLine("It costs $2000, click here to purchase").toItemStack());
    
    
    inventory.setItem(21, new ItemBuilder(Material.LEATHER_HELMET, 1).setName("Leather Helmet")
    		.addLoreLine("Coming Soon!").toItemStack());

    inventory.setItem(22, new ItemBuilder(Material.GOLD_HELMET, 1).setName("Gold Helmet")
    		.addLoreLine("Coming Soon!").toItemStack());
    
    inventory.setItem(23, new ItemBuilder(Material.CHAINMAIL_HELMET, 1).setName("Chain Helmet")
    		.addLoreLine("Coming Soon!").toItemStack());
    
    
    inventory.setItem(30, new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setName("Leather Chestplate")
    		.addLoreLine("Coming Soon!").toItemStack());
    
    inventory.setItem(31, new ItemBuilder(Material.GOLD_CHESTPLATE, 1).setName("Gold Chestplate")
    		.addLoreLine("Coming Soon!").toItemStack());
   
    inventory.setItem(32, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, 1).setName("Chain Chestplate")
    		.addLoreLine("Coming Soon!").toItemStack());
    
    
    
    inventory.setItem(39, new ItemBuilder(Material.LEATHER_LEGGINGS, 1).setName("Leather Leggings")
    		.addLoreLine("Coming Soon!").toItemStack());
    
    inventory.setItem(40, new ItemBuilder(Material.GOLD_LEGGINGS, 1).setName("Gold Leggings")
    		.addLoreLine("Coming Soon!").toItemStack());  
    
    inventory.setItem(41, new ItemBuilder(Material.CHAINMAIL_LEGGINGS, 1).setName("Chain Leggings")
    		.addLoreLine("Coming Soon!").toItemStack());    
    
    
    
    
    inventory.setItem(48, new ItemBuilder(Material.LEATHER_BOOTS, 1).setName("Leather Boots")
    		.addLoreLine("Coming Soon!").toItemStack());
    
    inventory.setItem(49, new ItemBuilder(Material.GOLD_BOOTS, 1).setName("Gold Boots")
    		.addLoreLine("Coming Soon!").toItemStack());
    
    inventory.setItem(50, new ItemBuilder(Material.CHAINMAIL_BOOTS, 1).setName("Chain Boots")
    		.addLoreLine("Coming Soon!").toItemStack());
    

    
    inventory.setItem(16, new ItemBuilder(Material.WOOD_SWORD, 1).setName("Wooden Sword")
    		.addLoreLine("Coming Soon!").toItemStack());
    
    inventory.setItem(25, new ItemBuilder(Material.STONE_SWORD, 1).setName("Stone Sword")
    		.addLoreLine("Coming Soon!").toItemStack());
    
    inventory.setItem(34, new ItemBuilder(Material.GOLD_SWORD, 1).setName("Gold Sword")
    		.addLoreLine("Coming Soon!").toItemStack());

  }
 
}
