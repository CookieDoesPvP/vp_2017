package com.vectorprison.commands;

import org.bukkit.entity.Player;

public abstract class PrisonAdminCommand extends PrisonCommand {

	public PrisonAdminCommand(String name, String permission) {
		super(name, permission);
	}
	
	public PrisonAdminCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public boolean canExecute(Player player, String permission) {
		if (player.hasPermission("prison.admin") || player.hasPermission(permission)) {
			return true;
		} else {
			player.sendMessage(plugin.getMessage("no permission"));
			return false;
		}
	}
}