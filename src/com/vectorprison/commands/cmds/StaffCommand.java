package com.vectorprison.commands.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vectorprison.VectorPrison;
import com.vectorprison.commands.PrisonPlayerCommand;

public class StaffCommand extends PrisonPlayerCommand {
	
	public StaffCommand(String name, String permission) {
		super(name, permission);
	}

	public StaffCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public void execute(Player player, String[] args) {
		if(getStaff().equalsIgnoreCase(ChatColor.DARK_RED + "There are no staff online currently.")) {
			player.sendMessage(ChatColor.AQUA + "There is no staff members online currently.");
			return;
		}
		player.sendMessage(ChatColor.AQUA + "Staff that are currently online: " + getStaff());
	}

	@Override
	protected void execute(CommandSender console, String[] args) {
		console.sendMessage(getStaff());
	}
	
	  public String getStaff()
	  {
	    String staff = "";
	    for (Player player : Bukkit.getOnlinePlayers())
	    {
	      if (player.hasPermission("vectorprison.groups.helper")) {
	    	  
	    	  if(!player.isOp()) {
	  	        staff = staff + ChatColor.DARK_AQUA + player.getName() + ", ";
	    	  }
	    	  
	      }
	    }
	    if (staff.length() > 4) {
	      staff = staff.substring(0, staff.length() - 2);
	    }
	    if (staff.length() == 0) {
	      staff = ChatColor.DARK_RED + "There are no staff online currently.";
	    }
	    return staff;
	  }

}
