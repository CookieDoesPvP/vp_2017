package com.vectorprison.commands.cmds;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vanquishnetwork.util.PlayerUtil;

import com.vectorprison.commands.PrisonPlayerCommand;
import com.vectorprison.economy.Economy;

public class MoneyCommand extends PrisonPlayerCommand {

	public MoneyCommand(String name, String permission) {
		super(name, permission);
	}
	
	public MoneyCommand(String name, String permissions, String[] aliases) {
		super(name, permissions, aliases);
	}

	@Override
	public void execute(Player player, String[] args) {
		OfflinePlayer target = player;
		if (args.length == 1) {
			target = PlayerUtil.matchOfflinePlayer(args[0]);
			if (target == null) {
				player.sendMessage(plugin.getMessage("misc.player not found"));
				return;
			}
		}
		
		player.sendMessage(plugin.getMessage("money.balance")
				.replaceAll("<player>", target.getName())
				.replaceAll("<money>", plugin.getFormattedDouble(Economy.getMoney(target)))
				.replaceAll("<total>", plugin.getFormattedDouble(Economy.getTotalMoney(target))));
	}

	@Override
	protected void execute(CommandSender console, String[] args) {
		if (args.length == 0) {
			console.sendMessage("You do not have a balance, but you can see the balance of someone else with /bal <player>.");
			return;
		}
		
		OfflinePlayer target = null;
		try {
			target = PlayerUtil.matchOfflinePlayer(args[0]);
			console.sendMessage(plugin.getMessage("money.balance")
					.replaceAll("<player>", target.getName())
					.replaceAll("<money>", plugin.getFormattedDouble(Economy.getMoney(target)))
					.replaceAll("<total>", plugin.getFormattedDouble(Economy.getTotalMoney(target))));
		} catch (NullPointerException e) {
			console.sendMessage("Player " + args[0] + " was not found. Perhaps he/she/it is not online?");
		}
	}
}