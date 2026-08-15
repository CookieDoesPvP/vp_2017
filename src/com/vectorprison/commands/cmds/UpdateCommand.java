package com.vectorprison.commands.cmds;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;
import com.vectorprison.commands.PrisonAdminCommand;
import com.vectorprison.thread.Update;

public class UpdateCommand extends PrisonAdminCommand {

	public UpdateCommand(String name, String permission) {
		super(name, permission);
	}

	public UpdateCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public void execute(Player player, String[] args) {
		updateAll(player);
	}

	@Override
	protected void execute(CommandSender console, String[] args) {
		updateAll(console);
	}

	public void updateAll(CommandSender sender) {
		final List<Player> players = Lists.newArrayList(Bukkit.getOnlinePlayers().toArray(new Player[0]));
		if (players.size() < 8) {
			new Update(plugin, players, sender).runTask(plugin);
			return;
		}
		
		int divisor = 8, indexFrom = -1;
		while (divisor > 1) {
			
			if (divisor > 2) divisor -= 2;
			else divisor -= 1;
			
			int tempFrom = indexFrom + 1;
			int tempTo = (players.size() / divisor) - 1;
			if (tempFrom == tempTo)
				new Update(plugin, players.get(tempFrom), sender).runTaskLater(plugin, 8 - divisor);
			else
				new Update(plugin, players.subList(tempFrom, tempTo + 1), sender).runTaskLater(plugin, 8 - divisor);
			
			indexFrom = (players.size() / divisor) - 1;
			
		}
	}
}