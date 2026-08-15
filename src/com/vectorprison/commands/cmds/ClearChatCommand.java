package com.vectorprison.commands.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vectorprison.VectorPrison;
import com.vectorprison.commands.PrisonAdminCommand;

public class ClearChatCommand extends PrisonAdminCommand {
	
	public ClearChatCommand(String name, String permission) {
		super(name, permission);
	}

	public ClearChatCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public void execute(Player player, String[] args) {
		 for (int x = 0; x < 160; x++)
         {
           Bukkit.broadcastMessage("");
          }
           Bukkit.broadcastMessage(VectorPrison.getInstance().getMessageNoPrefix("chatutils.clearchat").replace("%player%", player.getDisplayName()));
	}

	@Override
	protected void execute(CommandSender console, String[] args) {
		 for (int x = 0; x < 160; x++)
         {
           Bukkit.broadcastMessage("");
          }
           Bukkit.broadcastMessage(VectorPrison.getInstance().getMessageNoPrefix("chatutils.clearchat").replace("%player%", "CONSOLE"));
	}
}