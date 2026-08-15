package com.vectorprison.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.vanquishnetwork.sql.MySQL;

import com.vectorprison.PrisonTable;
import com.vectorprison.VectorPrison;
import com.vectorprison.api.PlayerData;
import com.vectorprison.api.ServerData;
import com.vectorprison.api.ToolLevels;
import com.vectorprison.sb.ScoreboardHandler;
import com.vectorprison.util.ParticleEffect;
import com.vectorprison.util.TitleSender;

import net.milkbowl.vault.item.Items;

public class BlockEvents implements Listener {
	private VectorPrison plugin;
    
    private Server server;
    private BukkitScheduler scheduler;
    
	public BlockEvents(VectorPrison plugin) {
		this.plugin = plugin;
        this.server = plugin.getServer();
        this.scheduler = server.getScheduler();
	}
	
	public static HashMap<Location, Material> blocks = new HashMap<Location, Material>();
	
	@EventHandler
	public void onDamage(BlockDamageEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		Location loc = e.getBlock().getLocation();
		
		Material m = e.getItemInHand().getType();
		
		ItemStack item = p.getItemInHand();
		if(item == null || item.getType() == Material.AIR) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
			return;
		}
		ItemMeta meta = item.getItemMeta();
		
		String dName = meta.getDisplayName();
		String dNameStrip = ChatColor.stripColor(dName);
		String dNameLevel = "";
		dNameLevel = dNameStrip.replaceAll("[^0-9]", "");
		List<String> lore = meta.getLore();
		String lol = ChatColor.stripColor(lore.get(lore.size()-4));
		String[] parts = lol.split("/");
		String part1 = parts[0];
		String xp = part1.replaceAll("[^0-9]", "");		
		int lev = Integer.valueOf(dNameLevel);
		
 		int xpnum = Integer.valueOf(xp); 
 		
 		if (xpnum == ToolLevels.getEssence(item.getType(), lev)) {
 			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
 			p.sendMessage(VectorPrison.getInstance().getPrefix() + "Your pickaxe is surging! Visit the Enchant Well to level up your pickaxe!");
 			p.sendMessage(VectorPrison.getInstance().getPrefix() + "You need to enchant your pickaxe to continue mining.");
 			e.setCancelled(true);
 			return;
 		}
		
		int level = PlayerData.getPlayerLevel(p);
		
		p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
		
		switch(m) {
			case WOOD_PICKAXE:
				break;
			case STONE_PICKAXE:
				if (level < 30) { e.setCancelled(true);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You must be mining level 30 to use this pickaxe.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				} 
				break;
			case GOLD_PICKAXE:
				if (level < 50) { e.setCancelled(true);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You must be mining level 50 to use this pickaxe.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
				break;
			case IRON_PICKAXE:
				if (level < 70) { e.setCancelled(true);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You must be mining level 70 to use this pickaxe.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
				break;
			case DIAMOND_PICKAXE:
				if (level < 90) { e.setCancelled(true);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You must be mining level 90 to use this pickaxe.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
			}
			default:
				e.setCancelled(true);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
				return;
				
		}
		
		switch(b.getType()) {
			case COAL_ORE:
			case COAL_BLOCK:
				break;
			case IRON_ORE:
			case IRON_BLOCK:
				if (level < 10) { e.setCancelled(true);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You are not high enough level to mine Iron! You must be 10+.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
				break;
			case LAPIS_ORE:
			case LAPIS_BLOCK:
				if(!(p.getItemInHand().getType() == Material.STONE_PICKAXE) || !(p.getItemInHand().getType() == Material.IRON_PICKAXE) || !(p.getItemInHand().getType() == Material.DIAMOND_PICKAXE) || !(p.getItemInHand().getType() == Material.GOLD_PICKAXE)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
				if (level < 30) { e.setCancelled(true);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You are not high enough level to mine Lapis! You must be 30+.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
				break;
			case REDSTONE_ORE:
			case GLOWING_REDSTONE_ORE:
			case REDSTONE_BLOCK:
				if(!(p.getItemInHand().getType() == Material.IRON_PICKAXE) || !(p.getItemInHand().getType() == Material.DIAMOND_PICKAXE) || !(p.getItemInHand().getType() == Material.GOLD_PICKAXE)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
				if (level < 50) { e.setCancelled(true);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You are not high enough level to mine Redstone! You must be 50+.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
				break;
			case GOLD_ORE:
			case GOLD_BLOCK:
				if(!(p.getItemInHand().getType() == Material.IRON_PICKAXE) || !(p.getItemInHand().getType() == Material.DIAMOND_PICKAXE)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
				if (!(level >= 70)) { e.setCancelled(true);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You are not high enough level to mine Gold! You must be 70+.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
				break;
			case DIAMOND_ORE:
			case DIAMOND_BLOCK:
				if(!(p.getItemInHand().getType() == Material.DIAMOND_PICKAXE)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
				if (!(level >= 90)) { e.setCancelled(true);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You are not high enough level to mine Diamond! You must be 90+.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
			    break;
			case EMERALD_ORE:
			case EMERALD_BLOCK:
				if(!(p.getItemInHand().getType() == Material.DIAMOND_PICKAXE)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
				}
				if (!(level >= 102)) { e.setCancelled(true);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You are not high enough level to mine Emerald! You must be 102+.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
					return;
			}
		    break;
		    
			default:
				e.setCancelled(true);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1));
				return;
				
		} 

			p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		Location loc = e.getBlock().getLocation();
		
		if (p.getGameMode() != GameMode.SURVIVAL) {
			return;
		}

		if (!ServerData.isMineableBlock(b)) {
			e.setCancelled(true);
			return;
		}
		
		ItemStack item = p.getItemInHand();
		ItemMeta meta = item.getItemMeta();
		
		String dName = meta.getDisplayName();
		String dNameStrip = ChatColor.stripColor(dName);
		String dNameLevel = "";
		dNameLevel = dNameStrip.replaceAll("[^0-9]", "");
		
		List<String> lore = meta.getLore();
		String lol = ChatColor.stripColor(lore.get(lore.size()-4));
		String[] parts = lol.split("/");
		String part1 = parts[0];
		String xp = part1.replaceAll("[^0-9]", "");		
		int lev = Integer.valueOf(dNameLevel);
		
 		int xpnum = Integer.valueOf(xp); 
 		
 		if (xpnum == ToolLevels.getEssence(item.getType(), lev)) {
 			p.sendMessage(VectorPrison.getInstance().getPrefix() + "Your pickaxe is surging! Visit the Enchant Well to level up your pickaxe!");
 			p.sendMessage(VectorPrison.getInstance().getPrefix() + "You need to enchant your pickaxe to continue mining.");
 			return;
 		}
		
		e.setCancelled(true);

		
		breakBlock(p, b, loc);
		updatePick(p, b);   
		//p.sendMessage("Debug: isnt cancelling");
		e.setExpToDrop(0);
		

		//heres where to do custom enchants
		
		if (ServerData.isOre(b.getType())) {
			if(b.getType() == Material.GLOWING_REDSTONE_ORE) {
				p.getInventory().addItem(new ItemStack(Material.REDSTONE_ORE));
			}else{
			p.getInventory().addItem(new ItemStack(b.getType(), 1));
			}
		} else {
			switch(b.getType()) {
				case COAL_BLOCK:
					p.getInventory().addItem(new ItemStack(Material.COAL, 1));	
					break;
				case IRON_BLOCK:
					p.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 1));
					break;
				case REDSTONE_BLOCK:
					p.getInventory().addItem(new ItemStack(Material.REDSTONE, 1));
					break;
				case GOLD_BLOCK:
					p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 1));
					break;
				case DIAMOND_BLOCK:
					p.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));	
					break;
				case EMERALD_BLOCK:
					p.getInventory().addItem(new ItemStack(Material.EMERALD, 1));
					break;
				 default:
					return;
			} 
		}
		
		startRegenMechanism(b);
		
	}
	
	public void breakBlock(Player p, Block b, Location loc){
		
		String m = b.getType().toString().toLowerCase();
		int add = 0;
		
		if(MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.MININGXP.toString(), PrisonTable.getTable()) == null) {
			MySQL.getInstance().set(p.getUniqueId().toString(), "uuid", PrisonTable.MININGXP.name, 0, PrisonTable.getTable());
			MySQL.getInstance().set(p.getUniqueId().toString(), "uuid", PrisonTable.BLOCKSBROKEN.name, 0, PrisonTable.getTable());
		}
		
		
		
		double nCurrentXP = (double) MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.MININGXP.toString(), PrisonTable.getTable());
		double nCurrentBlocks = (double)MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.BLOCKSBROKEN.toString(), PrisonTable.getTable());
		
		switch(m) {
		case "coal_ore":
		case "coal_block":
			add = 2;
			break;
		case "iron_ore":
		case "iron_block":
			add = 7;
			break;
		case "lapis_ore":
		case "lapis_block":
			add = 20;
			break;
		case "redstone_ore":
		case "glowing_redstone_ore":
		case "redstone_block":
			add = 58;
			break;
		case "gold_ore":
		case "gold_block":
			add = 175;
			break;
		case "diamond_ore":
		case "diamond_block":
			add = 526;
			break;
		case "emerald_ore":
		case "emerald_block":
			add = 1000;
			break;
		default:
			add = 0;
			break;
	}
		
		
		if ((nCurrentXP + add) >= PlayerData.xpForLevel((PlayerData.getPlayerLevel(p)+1)))  {
			
			ParticleEffect.SPELL_WITCH.display(0.1F, 0.1F, 0.5F, 0.5F, 50, p.getLocation(), 10.0D);
			TitleSender.sendTitle(p, "&fYou have reached level &b" + (PlayerData.getPlayerLevel(p)+1) , 2, 5, 3);
			
			if ((PlayerData.getPlayerLevel(p)+1) % 10 == 0) {
				
				p.sendMessage(VectorPrison.getInstance().getPrefix() + "You have recieved a &b'Vector Crystal'.");
				p.getInventory().addItem(new ItemStack(Material.COOKIE, 1));
				
				if ((PlayerData.getPlayerLevel(p)+1) == 10) {
					
					TitleSender.sendSubTitle(p, "&fUnlocked: &bMining *Iron Ore*", 2, 5, 3);
					
					} else if ((PlayerData.getPlayerLevel(p)+1) == 20) {
						
						TitleSender.sendSubTitle(p, "&fUnlocked: &bPvP *Gold Armour + Stone Sword*", 2, 5, 3);
						
					} else if ((PlayerData.getPlayerLevel(p)+1) == 30) {
						
						TitleSender.sendSubTitle(p, "&fUnlocked: &bMining *Lapis Ore + Stone Pickaxe*", 2, 5, 3);
						
					} else if ((PlayerData.getPlayerLevel(p)+1) == 40) {
						
						TitleSender.sendSubTitle(p, "&fUnlocked: &bPvP *Chain Armour + Gold Sword*", 2, 5, 3);
						
					} else if ((PlayerData.getPlayerLevel(p)+1) == 50) {
						
						TitleSender.sendSubTitle(p, "&fUnlocked: &Mining *Gold Pickaxe, Redstone Ore*", 2, 5, 3);
						
					} else if ((PlayerData.getPlayerLevel(p)+1) == 70) {
						
						TitleSender.sendSubTitle(p, "&fUnlocked: &Mining *Iron Pickaxe, Gold Ore*", 2, 5, 3);
						
					} else if ((PlayerData.getPlayerLevel(p)+1) == 90) {
						
						TitleSender.sendSubTitle(p, "&fUnlocked: &Mining *Diamond Pickaxe, Diamond Ore*", 2, 5, 3);
						
					} else if ((PlayerData.getPlayerLevel(p)+1) == 100) {
						
						TitleSender.sendSubTitle(p, "&fUnlocked: &Mining *Emerald Ore*", 2, 5, 3);
					}
						
				
			} else if ((PlayerData.getPlayerLevel(p)+1) % 5 == 0) {
				
				if ((PlayerData.getPlayerLevel(p)+1) == 5) {
					
					TitleSender.sendSubTitle(p, "&fUnlocked: &bPvP *Leather Armour + Wood Sword*" , 2, 5, 3);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You have recieved a &b'Vector Crystal'.");
					p.getInventory().addItem(new ItemStack(Material.COOKIE, 1));
					
				} else {
					
					TitleSender.sendSubTitle(p, "&fYou have reached level &b" + (PlayerData.getPlayerLevel(p)+1) , 2, 5, 3);
					p.sendMessage(VectorPrison.getInstance().getPrefix() + "You have recieved a &b'Vector Crystal'.");
					p.getInventory().addItem(new ItemStack(Material.COOKIE, 1));
					
				}
			
			} 
		}

		MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.MININGXP.name, nCurrentXP + add, PrisonTable.getTable());
		MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.BLOCKSBROKEN.name, nCurrentBlocks + 1, PrisonTable.getTable());
		new ScoreboardHandler(p).setBoard();
		


	}
	
	public static void updatePick(Player p, Block b) {
		
		if (p.getItemInHand() == null) {
			return;
		}
	
		if (!p.getItemInHand().getType().toString().toLowerCase().contains("pickaxe")) {
			return;
		}
			
		//read pick data
		ItemMeta meta = p.getItemInHand().getItemMeta();
		String dName = meta.getDisplayName();
		String dNameStrip = ChatColor.stripColor(dName);
		String dNameLevel = "";
		try {
		dNameLevel = dNameStrip.replaceAll("[^0-9]", "");
		} catch (NullPointerException n) {
			Bukkit.broadcastMessage(p.getName() + " has got a fucking default pick, sort it out.");
		}

		List<String> lore = meta.getLore();
		String lol = ChatColor.stripColor(lore.get(lore.size()-4));
		String[] parts = lol.split("/");
		String part1 = parts[0];
		String xp = part1.replaceAll("[^0-9]", "");		
		
		int lev = Integer.valueOf(dNameLevel);
		int nextlevel = lev + 1;
 		int xpnum = Integer.valueOf(xp);
		int add = (70 + ServerData.randomNum(30));
		int newxp = xpnum + add;
		
		String lorelast = lore.get(lore.size()-1);
		
	
	if (xpnum == ToolLevels.getEssence(p.getItemInHand().getType(), lev)) {
		p.sendMessage(VectorPrison.getInstance().getPrefix() + "Your pickaxe is surging! Visit the Enchant Well to level up your pickaxe!");
		p.sendMessage(VectorPrison.getInstance().getPrefix() + "You need to enchant your pickaxe to continue mining.");
		return;
	}
		
		
	 if (newxp >= ToolLevels.getEssence(p.getItemInHand().getType(), lev)) {
			newxp = ToolLevels.getEssence(p.getItemInHand().getType(), lev);
 
			p.sendMessage(VectorPrison.getInstance().getPrefix() + "Your pickaxe is packed with essence. Visit 'Mr Enchant' in spawn to enchant your pickaxe!");
			p.sendMessage(VectorPrison.getInstance().getPrefix() + "You need to enchant your pickaxe to continue gaining Pickaxe Essense.");
			
			String output = Items.itemByType(p.getItemInHand().getType()).getName();
			
			//set pick data
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b" + output + " &7<&b" + lev + "&7>"));
			List<String> newlore = meta.getLore();
			
			newlore.remove(newlore.size()-1);
			newlore.remove(newlore.size()-1);
			newlore.remove(newlore.size()-1);
			newlore.remove(newlore.size()-1);//this removes last 4 linEs of the lore.
			
			newlore.add("§7Pickaxe Essence: " + ToolLevels.getEssence(p.getItemInHand().getType(), lev) + "/" + ToolLevels.getEssence(p.getItemInHand().getType(), lev));
			newlore.add("§7XP to level up: 0");
			newlore.add("§7 ");
			newlore.add(lorelast);
			meta.setLore(newlore);
			
			
		} else {
			
			String output = Items.itemByType(p.getItemInHand().getType()).getName();
			
			//set pick data
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b" + output + " &7<&b" + lev + "&7>"));
			List<String> newlore = meta.getLore();
			
			newlore.remove(newlore.size()-1);
			newlore.remove(newlore.size()-1);
			newlore.remove(newlore.size()-1);
			newlore.remove(newlore.size()-1);//this removes last 4 linEs of the lore.
			
			newlore.add("§7Pickaxe Essence: " + newxp + "/" + ToolLevels.getEssence(p.getItemInHand().getType(), lev));
			newlore.add("§7XP to level up: " + (ToolLevels.getEssence(p.getItemInHand().getType(), lev) - newxp));
			newlore.add("§7 ");
			newlore.add(lorelast);
			meta.setLore(newlore);
			
		}
		
		//update
		p.getItemInHand().setItemMeta(meta);
		p.updateInventory();
		}

	public static void startRegenMechanism(Block b) {
		
		blocks.put(b.getLocation(), b.getType());
		
		Material m = b.getType();
		
				b.setType(Material.STONE);
				b.getDrops().clear();

				Bukkit.getScheduler().scheduleSyncDelayedTask(VectorPrison.getInstance(), new Runnable() {

					@Override
					public void run() {
						
						b.setType(Material.COBBLESTONE);
						b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getTypeId(), 30); 
					}
					
				}, regenTime(m) / 2);
				
		Bukkit.getScheduler().scheduleSyncDelayedTask(VectorPrison.getInstance(), new Runnable() {

			@Override
			public void run() {
				
				if (!ServerData.randomGenerator(25)) {
					if (ServerData.isOre(m)) {
						b.setType(m); 
					} else {
						switch(m) {
						case COAL_BLOCK:
							b.setType(Material.COAL_ORE);
							break;
						case IRON_BLOCK:
							b.setType(Material.IRON_ORE);
							break;
						case REDSTONE_BLOCK:
							b.setType(Material.REDSTONE_ORE);
							break;
						case GOLD_BLOCK:
							b.setType(Material.GOLD_ORE);
							break;
						case DIAMOND_BLOCK:
							b.setType(Material.DIAMOND_ORE);
							break;
						case EMERALD_BLOCK:
							b.setType(Material.EMERALD_ORE);
							break;
					 default:
						return;
					}
					}
				} else {
					switch(m) {
						case COAL_ORE:
							b.setType(Material.COAL_BLOCK);
							break;
						case IRON_ORE:
							b.setType(Material.IRON_BLOCK);
							break;
						case REDSTONE_ORE:
							b.setType(Material.REDSTONE_BLOCK);
							break;
						case GOLD_ORE:
							b.setType(Material.GOLD_BLOCK);
							break;
						case DIAMOND_ORE:
							b.setType(Material.DIAMOND_BLOCK);
							break;
						case EMERALD_ORE:
							b.setType(Material.EMERALD_BLOCK);
							break;
					 default:
						return;
					}
				}
				
				b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getTypeId(), 30); 
				blocks.remove(b);
			}
			
		}, regenTime(m));
	}
	
	
	public static Integer regenTime(Material m) {
		if(m.toString().toLowerCase().contains("coal")) {
			return 60;
		}
		if(m.toString().toLowerCase().contains("iron")) {
			return 100;
		}
		if(m.toString().toLowerCase().contains("lapis")) {
			return 160;
		}
		if(m.toString().toLowerCase().contains("redstone")) {
			return 240;
		}
		if(m.toString().toLowerCase().contains("gold")) {
			return 400;
		}
		if(m.toString().toLowerCase().contains("diamond")) {
			return 600;
		}
		if(m.toString().toLowerCase().contains("emerald")) {
			return 800;
		}
		return 0;
	}
}

	
	/* PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(0, new BlockPosition(p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ()), 3);
                    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
                    */

