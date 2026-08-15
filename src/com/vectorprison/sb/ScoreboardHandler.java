package com.vectorprison.sb;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.vanished.guards.Guards;
import com.vectorprison.VectorPrison;
import com.vectorprison.api.ActionBar;
import com.vectorprison.api.BalanceUpdateEvent;
import com.vectorprison.api.Eco;
import com.vectorprison.api.PlayerData;
import com.vectorprison.economy.Economy;
import com.vectorprison.util.ProgressBarUtil;
import com.vectorprison.util.SBManager;

public class ScoreboardHandler
  implements Listener
{
	private VectorPrison plugin;
	private Player p;
	
	public ScoreboardHandler(Player p) {
		this.p = p;
	}
  
	
  public ScoreboardHandler(VectorPrison vectorPrison) {
		this.plugin = plugin;
  }
  
  long timer;
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
	  Player p = e.getPlayer();
      if (System.currentTimeMillis() - this.timer < 500L) {
          return;
      }
      this.timer = System.currentTimeMillis();
      if(Guards.isVillain(p)) {
      	ActionBar.sendActionBar(p, "Status " + ChatColor.DARK_GRAY + "- " + ChatColor.RED + "Villain");
      	return;
      }
      if(Guards.getInstance().isGuardedbyWarden(p)) {
    	  ActionBar.sendActionBar(p, "Guarded " + ChatColor.DARK_GRAY + "- " + ChatColor.RED + "Warden");
      	return;
      }
      if(Guards.getInstance().isGuardedbyGuard(p)) {
      	ActionBar.sendActionBar(p, "Guarded " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Guard");
      }else{
    	ActionBar.sendActionBar(p, "Guarded " + ChatColor.DARK_GRAY + "- " + ChatColor.GREEN + "No Guards");
      }
  }
  
  public static String getPercent(Double percent)
  {
    return NumberFormat.getInstance().format(fix(percent.doubleValue())) + "%";
  }
  
  public static Double fix(double val)
  {
    try
    {
      DecimalFormat df = new DecimalFormat("###.#");
      return Double.valueOf(df.format(val));
    }
    catch (Exception e) {}
    return Double.valueOf(0.0D);
  }
  
  public void setBoard()
  {
    SBManager scoreboard = new SBManager(ChatColor.AQUA + "" + ChatColor.BOLD + "Vector Prison");
    scoreboard.blankLine();
    scoreboard.add(ChatColor.YELLOW + "" + ChatColor.BOLD  + "Mining Level");
    scoreboard.add("  " + PlayerData.getPlayerLevel(p));
    scoreboard.add(ChatColor.GREEN + "" + ChatColor.BOLD + "Mining XP");
   
    int levels = PlayerData.getLevel(p);
    String am = "" + levels;
    double amountsz = Double.parseDouble(am);

    String string = String.format("%,.2f", amountsz);
    scoreboard.add("  " + string.replace(".00", ""));
    int level = PlayerData.getPlayerLevel(p) + 1;
    scoreboard.add(ChatColor.GOLD + "" + ChatColor.BOLD  + "XP (to " + level + ")");
    int a = PlayerData.xpForLevel(PlayerData.getPlayerLevel(p)+1) - PlayerData.getLevel(p);
    String amount = "" + a;
    double amounts = Double.parseDouble(amount);
                                                
    String s = String.format("%,.2f", amounts); 
	float amountIntoLevel =  (PlayerData.getLevel(p) - PlayerData.xpForLevel(PlayerData.getPlayerLevel(p)));
	  
	float amountNeeded = ((PlayerData.xpForLevel(PlayerData.getPlayerLevel(p) + 1)) - PlayerData.xpForLevel(PlayerData.getPlayerLevel(p)));
	    
	float percent1 = ((amountIntoLevel / amountNeeded) * 100);
	  
	int percent2 = (int) percent1;
	
    scoreboard.add("  " + s.replace(".00", "") + " / " + percent2
    		+ "%");
    scoreboard.add("  " + ChatColor.DARK_GRAY + "[" + ProgressBarUtil.getProgressBar(PlayerData.xpForLevel(PlayerData.getPlayerLevel(p)),PlayerData.getLevel(p), (PlayerData.xpForLevel(PlayerData.getPlayerLevel(p)+1)), 10, "■", "&a", "&c") + ChatColor.DARK_GRAY + "]");
    scoreboard.add(ChatColor.GRAY+""+ChatColor.STRIKETHROUGH + "-------------");
    scoreboard.add("Balance " + ChatColor.DARK_GRAY + "- " + ChatColor.GREEN + "$" + VectorPrison.getFormattedDouble(Economy.getMoney(p)));
    scoreboard.add("Blocks Broken " + ChatColor.DARK_GRAY + "- " + ChatColor.GOLD + PlayerData.getBlocksBroken(p));
    scoreboard.blankLine();

    scoreboard.build();
    scoreboard.send(new Player[] { p });

  }
  
  public static String EssBalance(double amount)
  {
    int exp = (int)(Math.log(amount) / Math.log(1000.0D));
    if (amount >= 1.0E21D) {
      return "[Number Error]";
    }
    if (amount >= 1.0E18D) {
      return String.format("%.1f Quin", new Object[] { Double.valueOf(amount / Math.pow(1000.0D, exp)) });
    }
    if (amount >= 1.0E15D) {
      return String.format("%.1f Quad", new Object[] { Double.valueOf(amount / Math.pow(1000.0D, exp)) });
    }
    if (amount >= 1.0E12D) {
      return String.format("%.1f Trillion", new Object[] { Double.valueOf(amount / Math.pow(1000.0D, exp)) });
    }
    if (amount >= 1.0E9D) {
      return String.format("%.1f Billion", new Object[] { Double.valueOf(amount / Math.pow(1000.0D, exp)) });
    }
    if (amount >= 1000000.0D) {
      return String.format("%.1f Million", new Object[] { Double.valueOf(amount / Math.pow(1000.0D, exp)) });
    }
    if (amount >= 1000.0D) {
      return String.format("%.1fk", new Object[] { Double.valueOf(amount / Math.pow(1000.0D, exp)) });
    }
    return NumberFormat.getInstance().format(amount);
  }
}
