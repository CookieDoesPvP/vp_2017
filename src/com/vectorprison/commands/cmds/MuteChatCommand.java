package com.vectorprison.commands.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vectorprison.VectorPrison;
import com.vectorprison.commands.PrisonAdminCommand;

public class MuteChatCommand extends PrisonAdminCommand {
	private static boolean muted = false;

	public MuteChatCommand(String name, String permission) {
		super(name, permission);
	}

	public MuteChatCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public void execute(Player player, String[] args) {
		if (muted) {
			muted = false;
			
		} else  {
			for (Player L : Bukkit.getOnlinePlayers()) {
				L.sendMessage(VectorPrison.getInstance().getMessage("chatutils.mutechat").replace("%player%", player.getDisplayName()).replace("%boolean%", muted ? "enabled" : "disabled"));
			}
			
			muted = true;
		}
	}

	@Override
	protected void execute(CommandSender console, String[] args) {
	
	}
	
	public static boolean isChatMuted() {
		if (muted) {
			return true;
		} else {
			return false;
		}
	}
	
	
}