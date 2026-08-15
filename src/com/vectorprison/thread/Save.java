package com.vectorprison.thread;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.vectorprison.VectorPrison;
import com.vectorprison.economy.Economy;

public class Save extends BukkitRunnable {
	private VectorPrison plugin;
	private List<Player> playersToSave;
	private Player playerToSave;
	private CommandSender sender;
	
	public Save(VectorPrison plugin, List<Player> playersToSave, CommandSender sender) {
		this.plugin = plugin;
		this.playersToSave = playersToSave;
		this.sender = sender;
	}
	
	public Save(VectorPrison plugin, Player playerToSave, CommandSender sender) {
		this.plugin = plugin;
		this.playerToSave = playerToSave;
		this.sender = sender;
	}
	
	public Save(VectorPrison plugin, List<Player> playersToSave, Player playerToSave, CommandSender sender) {
		this.plugin = plugin;
		this.playersToSave = playersToSave;
		this.playerToSave = playerToSave;
		this.sender = sender;
	}
	@Override
	public void run() {
		if (playerToSave != null)
			try { save(playerToSave); } catch (Exception e) {}
		if (playersToSave != null && !playersToSave.isEmpty())
			for (Player player : playersToSave) {
				if (player == null || !player.isOnline())
					continue;
				
				try { save(player); } catch (Exception e) { continue; }
			}
		
		cancel();
	}
	
	private void save(Player player) {
		sender.sendMessage("Saving " + player.getName() + "...");
		 
		Economy.syncSave();
		
		/* Update any changes on the leveling items before saving them.
		manager.getItem(player, ItemType.PICKAXE).update();
		
		// Save the leveling items.
		manager.savePlayerItems(player);
		
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