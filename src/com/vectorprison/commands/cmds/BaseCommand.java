package com.vectorprison.commands.cmds;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.vectorprison.VectorPrison;
import com.vectorprison.bases.Base;
import com.vectorprison.commands.PrisonAdminCommand;
import com.vectorprison.gangs2.Main;

public class BaseCommand extends PrisonAdminCommand {
	
	public BaseCommand(String name, String permission) {
		super(name, permission);
	}

	public BaseCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public void execute(Player player, String[] args) {
		if(Base.hasBase(player)) {
			
			Base base = Base.getBase(player);
			assert base != null;
			
			if(args.length < 1) {
				
				player.sendMessage("Base Commands:");
				player.sendMessage(" ");
				player.sendMessage("/base portal - Create a base portal at your feet!");
				return;
				
			}
			
			if(args[0].equalsIgnoreCase("portal")) {
				
				Location playerloc = player.getLocation().getBlock().getLocation();
				playerloc.add(0.5, 2, 0.5);
				
				final Hologram hologram = HologramsAPI.createHologram(VectorPrison.getPlugin(VectorPrison.class), playerloc);
				hologram.insertTextLine(0, "§c§l" + player.getName() + "'s Base Portal");
				hologram.insertTextLine(1, "§7Jump in to teleport to their base!");
				
				Location playerloc2 = playerloc;
				playerloc2.add(0, -2, 0);
				
				Material m = player.getWorld().getBlockAt(playerloc2).getType();
				player.getWorld().getBlockAt(playerloc2).setType(Material.PORTAL);
				player.getWorld().playEffect(playerloc2, Effect.STEP_SOUND, 90, 30);
				VectorPrison.baseportals.put(playerloc2.getBlock(), base.getCoreLocation());
				
				Bukkit.getScheduler().scheduleSyncDelayedTask((Main.getPlugin(Main.class)), new Runnable() {

					@Override
					public void run() {
						player.getWorld().getBlockAt(playerloc2).setType(m);
						player.getWorld().playEffect(playerloc2, Effect.STEP_SOUND, 90, 30);
						VectorPrison.baseportals.remove(playerloc2.getBlock());
						hologram.delete();
					}
					
				}, 200);
				
			}
			
		}
	}

	@Override
	protected void execute(CommandSender console, String[] args) {
		console.sendMessage("You cannot use this command.");
	}
}