package com.vectorprison.commands.cmds;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vanquishnetwork.sql.MySQL;
import org.vanquishnetwork.util.PlayerUtil;

import com.vectorprison.PrisonTable;
import com.vectorprison.api.PlayerData;
import com.vectorprison.commands.PrisonAdminCommand;

public class XPAddCommand extends PrisonAdminCommand {

	public XPAddCommand(String name, String permission) {
		super(name, permission);
	}

	public XPAddCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public void execute(Player player, String[] args) {
		
		if (args.length == 0) {
			for (int i = 0; i < 105; i++) {
			    player.sendMessage(i + ": " + PlayerData.xpForLevel(i));
			}
		}
		OfflinePlayer target = PlayerUtil.matchOfflinePlayer(args[0]);
		
		if (args.length == 1) {
			target = PlayerUtil.matchOfflinePlayer(args[0]);
			if (target == null) {
				player.sendMessage(plugin.getMessage("misc.player not found"));
				return;
			}
			
	}
		MySQL.getInstance().set(target.getUniqueId().toString(), PrisonTable.MININGXP.name, args[1], PrisonTable.getTable());
		
	}
	

	@Override
	protected void execute(CommandSender console, String[] args) {
		
	}

}