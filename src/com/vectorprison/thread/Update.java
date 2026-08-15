package com.vectorprison.thread;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.vectorprison.VectorPrison;
import com.vectorprison.economy.Economy;

public class Update extends BukkitRunnable {
	private VectorPrison plugin;
	private List<Player> playersToUpdate;
	private Player playerToUpdate;
	private CommandSender sender;
	
	public Update(VectorPrison plugin, List<Player> playersToUpdate, CommandSender sender) {
		this.plugin = plugin;
		this.playersToUpdate = playersToUpdate;
		this.sender = sender;
	}
	
	public Update(VectorPrison plugin, Player playerToUpdate, CommandSender sender) {
		this.plugin = plugin;
		this.playerToUpdate = playerToUpdate;
		this.sender = sender;
	}
	
	public Update(VectorPrison plugin, List<Player> playersToUpdate, Player playerToUpdate, CommandSender sender) {
		this.plugin = plugin;
		this.playersToUpdate = playersToUpdate;
		this.playerToUpdate = playerToUpdate;
		this.sender = sender;
	}
	
	@Override
	public void run() {
		if (playerToUpdate != null)
			try { update(playerToUpdate); } catch (Exception e) {}
		if (playersToUpdate != null && !playersToUpdate.isEmpty())
			for (Player player : playersToUpdate) {
				if (player == null || !player.isOnline())
					continue;
				
				try { update(player); } catch (Exception e) { continue; }
			}
		
		cancel();
	}
	
	private void update(Player player) {
		sender.sendMessage("Updating " + player.getName() + "...");
		
		// Sync money to MySQL.
		Economy.syncSave();
		
		/* Sync any changes on the leveling items before updating them.
		manager.getItem(player, ItemType.PICKAXE).update();
		
		// Set the levels to their current levels to sync the rewards for leveling up from the config (for in case the rewards were edited).
		manager.getItem(player, ItemType.PICKAXE).setLevel(manager.getItem(player, ItemType.PICKAXE).getItemInfo().getLevel());
		
		// Update the leveling items again so that they are loaded from the file into local memory and can be easily placed in an inventory.
		manager.getItem(player, ItemType.PICKAXE).update();
		
		// Set the items in the player's inventory.
		player.getInventory().setItem(0, manager.getItem(player, ItemType.PICKAXE).getItemInfo().getItem());
		
		// Update the items one final time now that they're in the player's inventory.
		manager.getItem(player, ItemType.PICKAXE).update(); */

		// Update the player's inventory.
		player.updateInventory();
	}
}