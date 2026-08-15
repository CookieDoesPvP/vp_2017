package com.vectorprison.chat;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import com.vectorprison.VectorPrison;
import com.vectorprison.api.Eco;
import com.vectorprison.api.PlayerData;
import com.vectorprison.commands.cmds.MuteChatCommand;
import com.vectorprison.listener.InventoryEvents;
import com.vectorprison.util.FancyMessage;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatFormat
  implements Listener
{
  private VectorPrison plugin;
  
  public ChatFormat(VectorPrison plugin)
  {
    this.setPlugin(plugin);
  }
  
  public String getSuffix(Player p)
  {
    if (!PermissionsEx.getUser(p).getSuffix().equalsIgnoreCase("")) {
      return PermissionsEx.getUser(p).getSuffix().replace("&", "§");
    }
    return "";
  }
   
  public String getPrefix(Player p)
  {
    return PermissionsEx.getUser(p).getPrefix().replace("&", "§");
  }
  
  public String setMsgColor(String msg, String color)
  {
    if (msg.contains(" "))
    {
      String[] parts = msg.split(" ");
      String temp = "";
      String[] arrayOfString1;
      int j = (arrayOfString1 = parts).length;
      for (int i = 0; i < j; i++)
      {
        String w = arrayOfString1[i];
        temp = temp + color + w + " ";
      }
      msg = temp;
    }
    else
    {
      char[] stringArray = msg.toCharArray();
      StringBuilder sb = new StringBuilder();
      for (int index = 0; index < stringArray.length; index++) {
        sb.append(color + stringArray[index]);
      }
      msg = sb.toString();
    }
    return msg;
  }
  
  public String hovermsg(Player p)
  {
    
    return 
    
     // "§2§m**§a§m-----§2§m*§a§m-----§a» §7" + p.getName() + "'s Info §a §a§m----§2§m*§a§m-----§2§m**§r".trim() + 
      ChatColor.translateAlternateColorCodes('&', "&e&m**&b&m-----&e» " + p.getName() + "'s Info " + "&e«&b&m-----&e&m**".trim()) +     
      System.getProperty("line.separator") + "§e» §bRank§7 " + getPrefix(p) + 
      System.getProperty("line.separator") + "§e» §bGang§7 " + " " +
      System.getProperty("line.separator") + "§e» §bK/D Ratio§7 " + "1.0 (1K, 1D)" + 
      System.getProperty("line.separator") + "§e» §bBlocks Broken§7 " + PlayerData.getBlocksBroken(p) + 
      System.getProperty("line.separator") + "§e» §bMining Level§7 " + (PlayerData.getPlayerLevel(p) + " (" + PlayerData.getLevel(p) +"XP)") + 
      System.getProperty("line.separator") + "§e» §bBalance§7 $" + plugin.getFormattedDouble(Eco.getMoney(p));
  }
  
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
  public void onChat(AsyncPlayerChatEvent e)
  {
    String msg = e.getMessage();
    
	if (msg.equalsIgnoreCase("yes") && InventoryEvents.array.contains(e.getPlayer().getName())) {
		e.getRecipients().clear();
		InventoryEvents.array.remove(e.getPlayer().getName());
		e.getPlayer().sendMessage(plugin.getMessageNoPrefix("prefix") + "Pickaxe drop confirmed. Please redrop your tool within 15 seconds!");
		InventoryEvents.array2.add(e.getPlayer().getName());
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() { 
			public void run() { 
				if (InventoryEvents.array2.contains(e.getPlayer().getName())){
				InventoryEvents.array2.remove(e.getPlayer().getName());
				e.getPlayer().sendMessage(plugin.getMessageNoPrefix("prefix") + "You failed to drop your pickaxe within 10 seconds, please try again.");
				} else {
					return;
				}
			} 
			}, 20 * 10); // 20 (one second in ticks) * 5 (seconds to wait)
		
		
	}
    
	if (!e.getPlayer().hasPermission("vectorprison.groups.helper") && (MuteChatCommand.isChatMuted())) {
		e.getRecipients().clear();
		e.getPlayer().sendMessage(VectorPrison.getInstance().getMessage("chatutils.chatmuted"));
		return;
	}
    
    if (!e.getPlayer().isOp()) {
      msg = msg.replace("&", "");
    }
    if (e.getPlayer().isOp()) {
      msg = msg.replace("&", "§");
    }
    try
    {
      Player p = e.getPlayer();
      e.setFormat(getPrefix(p) + p.getDisplayName() + getSuffix(p) + "§8:§b" + 
        " " + msg);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    if ((!StringUtils.containsIgnoreCase(msg, "http://")) && (!StringUtils.containsIgnoreCase(msg, "https://")))
    {
      sendChat(e.getPlayer(), msg, e.getRecipients());
      e.getRecipients().clear();
    }
  }
  
  private void sendChat(Player p, String msg, Set<Player> recip)
  {
    String pre = getPrefix(p);
    String suf = "§8:";
    
    FancyMessage fm = new FancyMessage(pre);
    
    fm.tooltip(hovermsg(p));
    fm.then(ChatColor.translateAlternateColorCodes('&', p.getDisplayName() + getSuffix(p)));
    fm.tooltip(hovermsg(p));
    fm.suggest("/msg " + p.getName() + " ");
    fm.tooltip(hovermsg(p));
    fm.then(ChatColor.translateAlternateColorCodes('&', suf));
    fm.tooltip(hovermsg(p));
    if(!p.hasPermission("vectorprison.groups.admin")) {
    fm.then(ChatColor.translateAlternateColorCodes('&', " " + (
      (msg.contains("&")) || (msg.contains("§")) ? msg : setMsgColor(msg, "§7"))));
    }else{
    	 fm.then(ChatColor.translateAlternateColorCodes('&', " " + (
    		      (msg.contains("&")) || (msg.contains("§")) ? msg : setMsgColor(msg, "§c"))));
    }
    fm.send(recip);
  }

public VectorPrison getPlugin() {
	return plugin;
}

public void setPlugin(VectorPrison plugin) {
	this.plugin = plugin;
}
}
