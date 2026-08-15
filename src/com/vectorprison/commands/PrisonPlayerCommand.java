package com.vectorprison.commands;

import org.bukkit.entity.Player;

public abstract class PrisonPlayerCommand extends PrisonCommand {

	public PrisonPlayerCommand(String name, String permission) {
		super(name, permission);
	}

	public PrisonPlayerCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}
	
	@Override
	public boolean canExecute(Player player, String permission) {
		if (player.hasPermission("prison.player") || player.hasPermission(permission)) {
			return true;
		} else {
			player.sendMessage(plugin.getMessage("no permission"));
			return false;
		}
	}
}