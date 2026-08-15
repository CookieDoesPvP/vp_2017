package com.vectorprison;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.vanquishnetwork.core.VectorPlugin;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.vectorprison.bases.Base;
import com.vectorprison.chat.ChatFormat;
import com.vectorprison.commands.CommandManager;
import com.vectorprison.commands.PrisonCommand;
import com.vectorprison.economy.Economy;
import com.vectorprison.listener.BlockEvents;
import com.vectorprison.listener.InventoryEvents;
import com.vectorprison.listener.MiscEvents;
import com.vectorprison.listener.PlayerJoinLeave;
import com.vectorprison.listener.SellListeners;
import com.vectorprison.quests.NPCClickListener;
import com.vectorprison.sb.ScoreboardHandler;
import com.vectorprison.util.FileUtils;
import com.vectorprison.util.ItemUtil;
import com.vectorprison.util.ItemUtils;

public class VectorPrison extends VectorPlugin implements Listener {
	
	/*
	 * Classes:
	 * 
	 * Miner - 5.0%+ XP Gain
	 * Warrior - Health Boost I - Permanent
	 * Trader - 5.0%+ Sell Boost
	 * 
	 */
	
	/*
	 * Features:
	 * 
	 * - Quest System (Maybe Challenges?)
	 * - 
	 */
	
	private FileUtils files;
	private ItemUtil itemUtil;
	private CommandManager commands;
	private Scoreboard sb;
	
	private PlayerJoinLeave playerLeave;
	private InventoryEvents invEvents;
	private MiscEvents miscEvents;
	private ChatFormat chatFormat;
	private BlockEvents blockEvents;
	private NPCClickListener npcListener;
	private ScoreboardHandler scoreboardListener;
	private SellListeners sellListeners;

	private File baseSchematicsFile;
	
	private ProtocolManager protocolManager;
	
	@Override
	public void onLoad() {
	    Bukkit.getServicesManager().register(Economy.class, Economy.getInstance(), this, ServicePriority.Normal);
	}
	
	public void log(String msg){
		this.getLogger().info(msg);
	}
	
	@Override
	public void onEnable() {
	   sb = Bukkit.getScoreboardManager().getMainScoreboard();
	   
	   if(sb.getObjective("health") !=null) {
		 sb.getObjective("health").unregister();
	   }
		 
	    Objective o = sb.registerNewObjective("health", "health");
	    o.setDisplayName(ChatColor.RED + "�?�");
	    o.setDisplaySlot(DisplaySlot.BELOW_NAME);
	    
		files = new FileUtils(this);
	    Configs.loadConfigs();
	  
	    registerTable(PrisonTable.constructTable());  
	      
	    Economy.load();
	    
	    ItemUtils.initItems();
	    
	    UUID random = UUID.randomUUID();
	    
	    loadBases();
	    
        baseSchematicsFile = new File(this.getDataFolder() + File.separator + "schematics");
		
		if(!(baseSchematicsFile.exists())){
			baseSchematicsFile.mkdirs();
		}
		
		log("Enabled!");
	    
		itemUtil = new ItemUtil();
		commands = new CommandManager(this);
		
		playerLeave = new PlayerJoinLeave(this);
		invEvents = new InventoryEvents(this);
		miscEvents = new MiscEvents(this);
		chatFormat = new ChatFormat(this);
		blockEvents = new BlockEvents(this);
		npcListener = new NPCClickListener(this);
		scoreboardListener = new ScoreboardHandler(this);
		sellListeners = new SellListeners(this);
		
		new BukkitRunnable() {
	        public void run() {
	            DateFormat dateformat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            if (dateformat.format(date).equals("10:06:00")) {
	                Bukkit.broadcastMessage("It is 09:59:00 AM GMT");
	            } else if (dateformat.format(date) == "14:00:00") {
	                //Code
	            } else if (dateformat.format(date) == "22:00:00") {
	                //Code
	            }
	        }
	    }.runTaskTimer(VectorPrison.getInstance(), 0L, 20L);
	
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(playerLeave, this);
		manager.registerEvents(invEvents, this);
		manager.registerEvents(miscEvents, this);
		manager.registerEvents(chatFormat, this);
		manager.registerEvents(blockEvents, this);
		manager.registerEvents(npcListener, this);
		manager.registerEvents(scoreboardListener, this);
		manager.registerEvents(sellListeners, this);

		//Using protocol lib, this removes particle effects.
		protocolManager = ProtocolLibrary.getProtocolManager();
		ProtocolLibrary.getProtocolManager().addPacketListener(
    new PacketAdapter(this, ConnectionSide.SERVER_SIDE, Packets.Server.ENTITY_METADATA) {
        public void onPacketSending(PacketEvent event) {
            PacketContainer packet = event.getPacket();
            Entity entity = packet.getEntityModifier(event.getPlayer().getWorld()).read(0);
            if (entity instanceof Zombie) {
                for (WrappedWatchableObject obj : packet.getWatchableCollectionModifier().read(0)) {
                    if (obj.getIndex() == 7) {
                        obj.setValue((int) 0);
                    }
                }
            }
        }
    }
);
		
		
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					// If any players online after reload get their money.
					Economy.getMoney(player);
				}
			}
		}.runTaskLater(this, 5l);
	}
	
	public void onDisable() {		
	
		InventoryEvents.array.clear();
		
		Economy.syncSave();
		
		saveBases();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		for (PrisonCommand prisonCmd : commands.getRegisteredCommands()) {
			if (prisonCmd.isAcceptedLabel(cmd.getName()) || prisonCmd.isAcceptedLabel(label)) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					prisonCmd.tryExecute(player, args);
				} else {
					prisonCmd.tryExecute(sender, args);
				}
				return true;
			}
		}
		return true;
	}

	public FileUtils getFiles() {
		return files;
	}
	
	public ItemUtil getItemUtil() {
		return itemUtil;
	}
	
	public CommandManager getCommands() {
		return commands;
	}
	
	
	// Event classes, can be used to dupe a import / ext variable usage (api )
	public PlayerJoinLeave getLeaveEvent() { return playerLeave; }
	public InventoryEvents getInvEvents() { return invEvents; }
	public MiscEvents getMiscEvents() { return miscEvents; }
	public ChatFormat getChatFormat() { return chatFormat; }
	public BlockEvents getBlockEvents() { return blockEvents;}
	
	
	public String getMessage(String message) {
		return ChatColor.translateAlternateColorCodes('&', files.getMessages().getString("prefix") + files.getMessages().getString(message));
	}
	
	public String getMessageNoPrefix(String message) {
		return ChatColor.translateAlternateColorCodes('&', files.getMessages().getString(message));
	}
	
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', files.getMessages().getString("prefix"));
	}
	
	public static String getFormattedDouble(double number) {
		double n = Math.abs(number);
		
		if (n >= 1e15) {
			return new DecimalFormat("#,###.##Qa").format((number / 1e15));
		} else if (n >= 1e12) {
			return new DecimalFormat("#,###.##T").format((number / 1e12));
		} else if (n >= 1e9) {
			return new DecimalFormat("#,###.##B").format((number / 1e9));
		} else if (n >= 1e6) {
			return new DecimalFormat("#,###.##M").format((number / 1e6));
		} else if (n >= 1e3) {
			return new DecimalFormat("#,###.##K").format((number / 1e3));
		}
		
		return new DecimalFormat("#.##").format(number);
	}
	
	public String getFormattedMultiplier(double mult) {
		return new DecimalFormat("#,###,###.#").format(mult);
	}
	

	public static VectorPrison getInstance()
	{
	  return (VectorPrison)getPlugin(VectorPrison.class);
	}
	
	public static WorldGuardPlugin getWorldGuard()
	{
	  return (WorldGuardPlugin)getPlugin(WorldGuardPlugin.class);
	
	}
	
	/* Base Stuff */
	
	public static HashMap<Block, Location> baseportals = new HashMap<Block,Location>();
	

    public void save(Base base) {
    	if(base.getUniqueId() == null) {
    		return;
    	}
        getConfig().set("BASES." + base.getUniqueId() + ".CREATOR", base.getOwner().toString());
        getConfig().set("BASES." + base.getUniqueId() + ".CORE.World", "Terrain2");
        getConfig().set("BASES." + base.getUniqueId() + ".CORE.X", base.getCoreLocation().getX());
        getConfig().set("BASES." + base.getUniqueId() + ".CORE.Y", base.getCoreLocation().getY());
        getConfig().set("BASES." + base.getUniqueId() + ".CORE.Z", base.getCoreLocation().getZ());
        saveConfig();
        saveConfig();
    }
    
    public void saveBases() {
    	for(Base bases : Base.bases) {
    		save(bases);
    	}
    }
    
    public void loadBases() {
    	for(String uuidToString : getConfig().getConfigurationSection("BASES").getKeys(false)) {
    		String worldName = getConfig().getString("BASES." + uuidToString + ".CORE.World");
    		int x = getConfig().getInt("BASES." + uuidToString + ".CORE.X");
    		int y = getConfig().getInt("BASES." + uuidToString + ".CORE.Y");
    		int z = getConfig().getInt("BASES." + uuidToString + ".CORE.Z");
    		Location coreloc = new Location(Bukkit.getWorld(worldName), x, y, z);
    		OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(getConfig().getString("BASES." + uuidToString + ".CREATOR")));
    		Base base = new Base(player, null, null, null);
    		base.setOwner(player.getUniqueId());
    		base.setCoreLocation(coreloc);
    	}
    }
}