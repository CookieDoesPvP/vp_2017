package com.vectorprison.commands.cmds;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;
import com.vectorprison.commands.PrisonAdminCommand;
import com.vectorprison.thread.Save;

public class SaveCommand extends PrisonAdminCommand {
	
	public SaveCommand(String name, String permission) {
		super(name, permission);
	}

	public SaveCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public void execute(Player player, String[] args) {
		saveAll(player);
	}

	@Override
	protected void execute(CommandSender console, String[] args) {
		saveAll(console);
	}
	
	public void saveAll(CommandSender sender) {
		final List<Player> players = Lists.newArrayList(Bukkit.getOnlinePlayers().toArray(new Player[0]));
		if (players.size() < 8) {
			new Save(plugin, players, sender).runTask(plugin);
			return;
		}
		
		int divisor = 8, indexFrom = -1;
		while (divisor > 1) {
			
			if (divisor > 2) divisor -= 2;
			else divisor -= 1;
			
			int tempFrom = indexFrom + 1;
			int tempTo = (players.size() / divisor) - 1;
			if (tempFrom == tempTo)
				new Save(plugin, players.get(tempFrom), sender).runTaskLater(plugin, 8 - divisor);
			else
				new Save(plugin, players.subList(tempFrom, tempTo + 1), sender).runTaskLater(plugin, 8 - divisor);
			
			indexFrom = (players.size() / divisor) - 1;
			
		}
	}
}