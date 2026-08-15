package com.vectorprison.commands.cmds;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vanquishnetwork.util.PlayerUtil;

import com.vectorprison.commands.PrisonAdminCommand;
import com.vectorprison.economy.Economy;

public class EconomyCommand extends PrisonAdminCommand {

	public EconomyCommand(String name, String permission) {
		super(name, permission);
	}
	
	public EconomyCommand(String name, String permissions, String[] aliases) {
		super(name, permissions, aliases);
	}

	@Override
	public void execute(Player player, String[] args) {
		if (args.length < 3) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Incorrect usage! Use &e/eco give/givem/take/set/reset <player> <amount>&7."));
			return;
		}
		
		OfflinePlayer target = getTarget(player, args[1], ChatColor.translateAlternateColorCodes('&', "&7No player found! Use &e/eco give/givem/take/set/reset <player> <amount>&7."));
		double amount = getAmount(player, args[2], ChatColor.translateAlternateColorCodes('&', "&7Invalid money amount! Use &e/eco give/givem/take/set/reset <player> <amount>&7."));
		if (target == null || amount < 0)
			return;
		
		player.sendMessage(getMessage(args,target, amount).replaceAll("<amount>", plugin.getFormattedDouble(amount)).replaceAll("<player>", target.getName()));
	}

	@Override
	protected void execute(CommandSender console, String[] args) {
		if (args.length < 3) {
			console.sendMessage("Incorrect usage! Use /eco give/givem/take/set/reset <player> <amount>.");
			return;
		}
		
		OfflinePlayer target = getTarget(console, args[1], ChatColor.translateAlternateColorCodes('&', "&7No player found! Use &e/eco give/givem/take/set/reset <player> <amount>&7."));
		double amount = getAmount(console, args[2], ChatColor.translateAlternateColorCodes('&', "&7Invalid money amount! Use &e/eco give/givem/take/set/reset <player> <amount>&7."));
		if (target == null || amount < 0)
			return;
		
		console.sendMessage(getMessage(args, target, amount).replaceAll("<amount>", plugin.getFormattedDouble(amount)).replaceAll("<player>", target.getName()));
	}
	
	private OfflinePlayer getTarget(CommandSender sender, String target, String errorMessage) {
		OfflinePlayer targetPlayer = PlayerUtil.matchOfflinePlayer(target);
		if (targetPlayer == null) {
			sender.sendMessage(errorMessage);
		}
		
		return targetPlayer;
	}
	
	private double getAmount(CommandSender sender, String amount, String errorMessage) {
		double value = -1;
		try {
			value = Double.valueOf(amount);
		} catch (NumberFormatException e) {
			sender.sendMessage(errorMessage);
		}
		
		return value;
	}
	
	private String getMessage(String[] args, OfflinePlayer target, double amount) {
		boolean extraMessage = args.length > 3 && (args[3].equalsIgnoreCase("-announce") || args[3].equalsIgnoreCase("-a"));
		String message;
		
		switch (args[0].toLowerCase()) {
		case "give":
			Economy.addMoney(target, amount);
			message = plugin.getMessage("admin.money given");
			if (extraMessage && target.isOnline())
				target.getPlayer().sendMessage(plugin.getMessage("money.added").replaceAll("<money>", plugin.getFormattedDouble(amount)).replaceAll("<player>", target.getName()));
			break;
		case "take":
			Economy.subtractMoney(target, amount);
			message = plugin.getMessage("admin.money taken");
			if (extraMessage && target.isOnline())
				target.getPlayer().sendMessage(plugin.getMessage("money.subtracted").replaceAll("<money>", plugin.getFormattedDouble(amount)).replaceAll("<player>", target.getName()));
			break;
		case "set":
			Economy.setMoney(target, amount);
			message = plugin.getMessage("admin.money set");
			break;
		case "reset":
			Economy.setMoney(target, amount);
			Economy.setTotalMoney(target, amount);
			message = plugin.getMessage("admin.money reset");
			break;
		default:
			message = ChatColor.translateAlternateColorCodes('&', "&7Incorrect usage! Use &e/eco give/take/set/reset <player> <amount>&7.");
			break;
		}
		
		return message;
	}

}