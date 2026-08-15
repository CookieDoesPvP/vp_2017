package com.vectorprison.bases;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.vanquishnetwork.menu.Menu;
import org.vanquishnetwork.util.FormatUtil;

import com.vectorprison.VectorPrison;
import com.vectorprison.api.Eco;
import com.vectorprison.economy.Economy;
import com.vectorprison.util.ItemBuilder;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.trait.LookClose;

public class BaseCreator
 extends Menu
{
	
  VectorPrison plugin = VectorPrison.getInstance();
  private static final String TITLE = FormatUtil.format("&b&lBase Creator", new Object[0]);  
  
  public BaseCreator(Player player)
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
    
    if (event.getCurrentItem().getType() == Material.PAPER)
    {
    	
      if(Economy.getMoney(player) < 100000) {
    	  player.sendMessage(plugin.getMessage("pay.not enough"));
      }else{
      World world = Bukkit.getWorld(plugin.getConfig().getString("Locations.NextBaseLocation.World"));
      int x = plugin.getConfig().getInt("Locations.NextBaseLocation.X");
      int y = plugin.getConfig().getInt("Locations.NextBaseLocation.Y");
      int z = plugin.getConfig().getInt("Locations.NextBaseLocation.Z");
      
      Location nextloc = new Location(world, x, y, z);
      
      if(Base.hasBase(player)) {
    	  player.sendMessage("You have already got a base!");
    	  close();
    	  return;
      }
      
      // How to work out locations: Location coreloc = new Location(world, x + 36, y + 12, z + 4);
      // add economy checks
      Base base = new Base(player, null, null, null);
      base.setOwner(player.getUniqueId());
      base.setUniqueId(UUID.randomUUID());
      Location coreloc = new Location(world, nextloc.getX() + 13.5D, nextloc.getY() + 9.0D, nextloc.getZ() + 7.5D);
      Location bankloc = new Location(world, nextloc.getX() + 15.5D, nextloc.getY() + 7.0D, nextloc.getZ() + 15.5D);
      NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "&6Bank");
      NPC npc2 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "&6Worker");
      NPC npc3 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "&6Merchant");
      NPC npc4 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "&6Base Manager");
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc select " + npc.getId());
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc lookclose");
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc skin Bankerr");
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc select " + npc2.getId());
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc lookclose");
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc skin Slaves");
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc select " + npc3.getId());
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc lookclose");
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc skin merchant");
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc select " + npc4.getId());
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc lookclose");
      plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "npc skin managers");
      npc.spawn(bankloc);
      npc.setProtected(true);
      Location workerloc = new Location(world, nextloc.getX() + 11.5D, nextloc.getY() + 7.0D, nextloc.getZ() + 15.5D);
      npc2.spawn(workerloc);
      npc2.setProtected(true);
      
      Location merchantlocs = new Location(world, nextloc.getX() + 13.5D, nextloc.getY() + 7.0D, nextloc.getZ() + 15.5D);
      Location basemanagerloc = new Location(world, nextloc.getX() + 13.5D, nextloc.getY() + 4.0D, nextloc.getZ() + 15.5D);
      npc3.spawn(merchantlocs);
      npc3.setProtected(true);
      npc4.spawn(basemanagerloc);
      npc4.setProtected(true);
      coreloc.setPitch(0.0F);
      coreloc.setYaw(0.0F);
      base.setCoreLocation(coreloc);
      event.setCancelled(true);
      
      try {
		BaseBuilder.buildBase(nextloc, player);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      plugin.getConfig().set("Locations.NextBaseLocation.X", x + 200);
      plugin.getConfig().set("Locations.NextBaseLocation.Y", y);
      plugin.getConfig().set("Locations.NextBaseLocation.Z", z + 200);
      plugin.saveConfig();
      new BukkitRunnable()
      {
        public void run()
        {
          BaseCreator.this.player.sendMessage(plugin.getPrefix() + "We have successfully built your base, teleporting now...");
          BaseCreator.this.player.teleport(base.getCoreLocation());
        }
      }.runTaskLater(VectorPrison.getInstance(), 60L);
      this.player.sendMessage(plugin.getPrefix() + "We have received your base request and will begin building it.");
      Economy.subtractMoney(player, 100000);
      close();
      return;
      }
    }
    if (event.getCurrentItem().getType() == Material.STAINED_CLAY)
    {
      Base base = Base.getBase(player);
      if(base == null) {
    	  //should never be null
    	  return;
      }
      
      player.teleport(base.getCoreLocation());
      player.sendMessage(plugin.getPrefix() + "Teleporting to your base...");
      return;
    }
    
  }
  
  protected void setContents(Inventory inventory)
  {
	inventory.setItem(4, new ItemBuilder(Material.NETHER_STAR, 1).setName(ChatColor.GOLD + player.getName() + "'s Info")
	.addLoreLine(ChatColor.GRAY + "Balance: " + ChatColor.AQUA + "$" + VectorPrison.getFormattedDouble(Eco.getMoney(player)))
	.toItemStack());	
	if(Base.hasBase(player)) {
	    inventory.setItem(13, new ItemBuilder(Material.STAINED_CLAY, 1).setName("§eTeleporter")
	    		.addLoreLine("§7Click to teleport to your base.").toItemStack());
	}else{
	    inventory.setItem(13, new ItemBuilder(Material.PAPER, 1).setName("§eCreate a Base")
	    		.addLoreLine("§7Click to purchase a base for $100,000.").toItemStack());
	}
  }
 
}
