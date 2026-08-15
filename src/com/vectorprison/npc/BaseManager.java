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
import com.vectorprison.bases.Base;
import com.vectorprison.tutorial.Tutorial;
import com.vectorprison.util.ItemBuilder;

public class BaseManager
 extends Menu
{
  private static final String TITLE = FormatUtil.format("&6Tutorial Tutor", new Object[0]);  
  
  public BaseManager(Player player)
  {
    super(player, TITLE, 36);
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
      if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
    	  
    	  close();
    	  
    	  return;
    	  
      } 
  }
  
  protected void setContents(Inventory inventory)
  {
	inventory.setItem(4, new ItemBuilder(Material.NETHER_STAR, 1).setName(ChatColor.GOLD + player.getName() + "'s Info")
	.addLoreLine(ChatColor.GRAY + "Balance: " + ChatColor.AQUA + "$" + VectorPrison.getFormattedDouble(Eco.getMoney(player))).addLoreLine("§7Mining Level: §b" + PlayerData.getPlayerLevel(player))
	.toItemStack());	
    inventory.setItem(19, new ItemBuilder(Material.DIAMOND_PICKAXE, 1).setName("§bMining Tutorial")
    		.addLoreLine("§7This tutorial will help you understand our").addLoreLine("§7pickaxe, levelling and mining system.").addLoreLine(" ").addLoreLine("§3Summary:").addLoreLine("§8- §bPickaxe Essence").addLoreLine("§8- §bEnchanting Well").addLoreLine("§8- §bLeveling System").addLoreLine("§8- §bRandom Mining Events").toItemStack());
    inventory.setItem(21, new ItemBuilder(Material.EYE_OF_ENDER, 1).setName("§bGuard Tutorial")
    		.addLoreLine("§7This tutorial will help you understand our").addLoreLine("§7player guarding and protection system.").addLoreLine(" ").addLoreLine("§3Summary:").addLoreLine("§8- §bWardens").addLoreLine("§8- §bGuards").addLoreLine("§8- §bNeutral Zones").addLoreLine("§8- §bVillains").toItemStack());
    inventory.setItem(23, new ItemBuilder(Material.PAPER, 1).setName("§bShop Tutorial")
    		.addLoreLine("§7This tutorial will help you understand").addLoreLine("§7how to sell your items to our exchangers.").addLoreLine(" ").addLoreLine("§3Summary:").addLoreLine("§8- §bOre Exchangers").addLoreLine("§8- §bRandom Merchants").addLoreLine("§8- §bTax NPCs").toItemStack());
    inventory.setItem(25, new ItemBuilder(Material.OBSIDIAN, 1).setName("§bBase Tutorial")
    		.addLoreLine("§7This tutorial will help you understand").addLoreLine("§7our complex and state of the art base system.").addLoreLine(" ").addLoreLine("§3Summary:").addLoreLine("§8- §bBase Builder").addLoreLine("§8- §bWorkers").addLoreLine("§8- §bBase Quests").toItemStack());

	
  }
 
}
