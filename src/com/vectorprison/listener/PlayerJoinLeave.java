package com.vectorprison.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.vanquishnetwork.sql.MySQL;

import com.vectorprison.VectorPrison;
import com.vectorprison.PrisonTable;
import com.vectorprison.economy.Economy;
import com.vectorprison.sb.ScoreboardHandler;
import com.vectorprison.tools.GiveTools;
import com.vectorprison.util.SBManager;

public class PlayerJoinLeave implements Listener {
	private VectorPrison plugin;
	
	public PlayerJoinLeave(VectorPrison plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onLeave(PlayerQuitEvent event) {
		onLeave(event.getPlayer());
		event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&7" + "» &b" + event.getPlayer().getName() + " &7has left the &bVectorPrison &7- &cClosed Beta&7."));
	}
	
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onLeave(PlayerKickEvent event) {
		onLeave(event.getPlayer());
		event.setLeaveMessage(ChatColor.translateAlternateColorCodes('&', "&7" + "» &b" + event.getPlayer().getName() + " &7has left the &bVectorPrison &7- &cClosed Beta&7."));
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		final Player p = event.getPlayer();
		
		event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&7" + "» &b" + p.getName() + " &7has joined the &bVectorPrison &7- &cClosed Beta&7."));
		
		if (!p.hasPlayedBefore()) {
			
			GiveTools.giveTools(Material.WOOD_PICKAXE, p, true);
		
			
			MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.MININGXP.name, 0, PrisonTable.getTable());
			MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.BLOCKSBROKEN.name, 0, PrisonTable.getTable());
			MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.KILLS.name, 0, PrisonTable.getTable());
			MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.DEATHS.name, 0, PrisonTable.getTable());
			MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.SKILLPOINTS.name, 0, PrisonTable.getTable());
			MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.DQUESTCOMP.name, 0, PrisonTable.getTable());
			MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.WQUESTCOMP.name, 0, PrisonTable.getTable());
			MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.MONEY.name, 0, PrisonTable.getTable());
			
		}
		
		// Force the sql query for their money
		Economy.getMoney(p);

		//Prevent griefing
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
		
		//Update SB
		new ScoreboardHandler(p).setBoard();
		
	}
	
	private void onLeave(final Player player) {

		plugin.getMiscEvents().remove(player.getUniqueId());
		Economy.removePlayer(player);
	
	}
	
}