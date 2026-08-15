package com.vectorprison.commands.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vectorprison.commands.PrisonPlayerCommand;

public class PayCommand extends PrisonPlayerCommand {
	
	public PayCommand(String name, String permission) {
		super(name, permission);
	}
	
	public PayCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public void execute(Player player, String[] args) {
		player.sendMessage(plugin.getMessageNoPrefix("prefix") + ChatColor.translateAlternateColorCodes('&', "&c/pay is disabled!"));
		/*
		if (args.length < 2) {
			player.sendMessage(plugin.getMessage("pay.incorrect usage"));
			return;
		}
		
		Player target = getTarget(player, args[0], plugin.getMessage("pay.incorrect usage"));
		double amount = getAmount(player, args[1], plugin.getMessage("pay.incorrect usage"));
		if (target == null || amount < 0)
			return;
		
		if (target == player) {
			player.sendMessage(plugin.getMessage("pay.no paying yourself"));
			return;
		}
		
		Economy eco = plugin.getEconomy();
		if (eco.getMoney(player) < amount) {
			player.sendMessage(plugin.getMessage("pay.not enough").replaceAll("<amount>", plugin.getFormattedDouble(amount)).replaceAll("<player>", target.getName()));
			return;
		}
		
		eco.addMoney(target, amount);
		eco.subtractMoney(player, amount);
		
		player.sendMessage(plugin.getMessage("pay.successful").replaceAll("<amount>", plugin.getFormattedDouble(amount)).replaceAll("<player>", target.getName()));
		target.sendMessage(plugin.getMessage("money.added").replaceAll("<money>", plugin.getFormattedDouble(amount)).replaceAll("<player>", target.getName()) + " from " + player.getName());
		*/
	}

	@Override
	protected void execute(CommandSender console, String[] args) {
		console.sendMessage("You do not have a balance, however you can use /eco give <player> <amount> if you would like to give someone some money.");
	}
}