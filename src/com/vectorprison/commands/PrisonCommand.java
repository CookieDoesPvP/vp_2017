package com.vectorprison.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vectorprison.VectorPrison;

public abstract class PrisonCommand {
	protected VectorPrison plugin;
	protected final String name;
	protected final String permission;
	protected final String[] aliases;
	
	public PrisonCommand(String name, String permission) {
		this.name = name;
		this.permission = permission;
		aliases = new String[0];
	}
	
	public PrisonCommand(String name, String permission, String[] aliases) {
		this.name = name;
		this.permission = permission;
		this.aliases = aliases;
	}
	
	protected abstract void execute(Player player, String[] args);
	protected abstract void execute(CommandSender console, String[] args);
	
	public abstract boolean canExecute(Player player, String permission);
	
	public void tryExecute(Player player, String[] args) {
		if (canExecute(player, permission))
			execute(player, args);
	}
	
	public void tryExecute(CommandSender console, String[] args) {
		execute(console, args);
	}
	
	public String getName() {
		return name;
	}
	
	public String getPermission() {
		return permission;
	}

	public String[] getAliases() {
		return aliases;
	}
	
	public boolean isAcceptedLabel(String label) {
		if (name.equalsIgnoreCase(label))
			return true;
		
		for (String alias : aliases) {
			if (alias.equalsIgnoreCase(label))
				return true;
		}
		return false;
	}
	
	public void setPlugin(VectorPrison plugin) {
		this.plugin = plugin;
	}
}