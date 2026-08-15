package com.vectorprison.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.google.common.collect.Lists;
import com.vectorprison.VectorPrison;
import com.vectorprison.commands.cmds.BaseCommand;
import com.vectorprison.commands.cmds.ClearChatCommand;
import com.vectorprison.commands.cmds.EconomyCommand;
import com.vectorprison.commands.cmds.MoneyCommand;
import com.vectorprison.commands.cmds.MuteChatCommand;
import com.vectorprison.commands.cmds.PayCommand;
import com.vectorprison.commands.cmds.SaveCommand;
import com.vectorprison.commands.cmds.StaffCommand;
import com.vectorprison.commands.cmds.UpdateCommand;
import com.vectorprison.commands.cmds.XPAddCommand;

public class CommandManager {
	private VectorPrison plugin;
	private List<PrisonCommand> commands;
	
	public CommandManager(VectorPrison plugin) {
		this.plugin = plugin;
		commands = Lists.newArrayList();
		registerDefaultCommands();
	}
	
	public <X extends PrisonCommand, Type extends X> Type registerCommand(Class<? extends X> cmd, Object... args) {
		try {
			Class<?>[] params = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				params[i] = args[i].getClass();
			}
			
			Type command = (Type) cmd.getConstructor(params).newInstance(args);
			command.setPlugin(plugin);
			return command;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			return null;
		}
	}
	
	public List<PrisonCommand> getRegisteredCommands() {
		return commands;
	}

	@SuppressWarnings("unchecked")
	public <X extends PrisonCommand, Type extends X> Type getCommand(Class<? extends X> cmdClass) {
		for (PrisonCommand cmd : commands.toArray(new PrisonCommand[0])) {
			if (cmd.getClass() == cmdClass)
				return (Type) cmd;
		}
		return null;
	}

	public String getName(Class<? extends PrisonCommand> cmdClass) {
		return getCommand(cmdClass) == null ? null : getCommand(cmdClass).getName();
	}
	
	public String getPermission(Class<? extends PrisonCommand> cmdClass) {
		return getCommand(cmdClass) == null ? null : getCommand(cmdClass).getPermission();
	}
	
	public void registerDefaultCommands() {
		
		commands.add(registerCommand(MoneyCommand.class, "money", "vectorprison.groups.player", new String[] { "balance", "bal", "ebalance", "ebal", "emoney" }));
		commands.add(registerCommand(PayCommand.class, "pay", "vectorprison.groups.player", new String[] { "pay", "epay" }));
		commands.add(registerCommand(EconomyCommand.class, "economy", "vectorprison.groups.admin", new String[] { "eco", "eeco" }));
		commands.add(registerCommand(SaveCommand.class, "save", "vectorprison.groups.admin", new String[] { "saveitems", "savelevels" }));
		commands.add(registerCommand(UpdateCommand.class, "update", "vectorprison.groups.admin", new String[] { "updateall", "updatelevelingitems", "updateitems" }));
		commands.add(registerCommand(XPAddCommand.class, "setxp", "vectorprison.groups.admin", new String[] { "setexp"}));
		commands.add(registerCommand(MuteChatCommand.class, "mutec", "vectorprison.groups.admin",  new String[] { "mutechat", "chatmute"}));
		commands.add(registerCommand(ClearChatCommand.class, "clearc", "vectorprison.groups.helper", new String[] { "clearchat", "cc", "cchat" }));
		commands.add(registerCommand(StaffCommand.class, "staff", "vectorprison.groups.player", new String[] { "who", "list", "stafflist" }));
		commands.add(registerCommand(BaseCommand.class, "base", "vectorprison.groups.player", new String[] { "bases", "basemanager", "basetp" }));
		

	}
}