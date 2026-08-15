package com.vectorprison.listener;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.vanquishnetwork.sql.MySQL;

import com.google.common.collect.Maps;
import com.vectorprison.PrisonTable;
import com.vectorprison.VectorPrison;
import com.vectorprison.api.BalanceUpdateEvent;
import com.vectorprison.sb.ScoreboardHandler;

public class MiscEvents implements Listener {
	private VectorPrison plugin;
	private Map<UUID, Long> lastRegen = Maps.newHashMap();
	private Map<UUID, Integer> warnings = Maps.newHashMap();

	public MiscEvents(VectorPrison plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		
		Player p = (Player) event.getEntity();
		
		if (event.getEntity().getKiller() == null) {
			Player t = event.getEntity().getKiller();
			double deaths = (double)MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.DEATHS.toString(), PrisonTable.getTable());
			MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.DEATHS.name, (deaths + 1), PrisonTable.getTable());
			return;
		} else {
			Player t = event.getEntity().getKiller();
			double deaths = (double)MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.DEATHS.toString(), PrisonTable.getTable());
			MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.DEATHS.name, (deaths + 1), PrisonTable.getTable());
		}
		
		double kills = (double) MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.KILLS.toString(), PrisonTable.getTable());
		MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.KILLS.name, (kills + 1), PrisonTable.getTable());
		
		
	}

    /*@EventHandler
    public void onRightClick(PlayerInteractEvent e){
    if (e.getAction().equals(null)) { }
    
    if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)||e.getAction().equals(Action.RIGHT_CLICK_AIR)){
    	
	if(e.getClickedBlock().getType() == null) {}
    
	if(e.getClickedBlock().getType() == Material.WORKBENCH){  
		e.setCancelled(true);       
			}
    	}
    }
	
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() == event.getEntity()) {
			// Self damage
			return;
		}

		if (event.getDamager().getType() != EntityType.PLAYER
				|| event.getEntity().getType() != EntityType.PLAYER) {
			// Non-pvp
			return;
		}

		Player damager = (Player) event.getDamager();

		RegionManager rg = MineParadise.getWorldGuard().getRegionManager(damager.getWorld());
		ApplicableRegionSet from = rg.getApplicableRegions(damager.getLocation());
		ApplicableRegionSet to = rg.getApplicableRegions(event.getEntity().getLocation());
		LocalPlayer lp = MineParadise.getWorldGuard().wrapPlayer(damager);

		// If pvp flags are different between 2 locations
		if (from.testState(lp, DefaultFlag.PVP) != to.testState(lp, DefaultFlag.PVP)) {
			// Launch damager towards target
			damager.setVelocity(damager.getLocation().subtract(event.getEntity().getLocation())
					.toVector().normalize().add(new Vector(0, 1, 0)).multiply(3));
		}
		
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		
		Player damaged = (Player) event.getEntity();
		if (damaged.getHealth() - event.getDamage() <= 0) {
			// dead
			List<List<Object>> rows;
			try {
				rows = MySQL.getInstance().get("SELECT "
						+ "(player_kills/deaths) FROM PLAYER_STATISTICS WHERE "
						+ "uuid='" + damaged.getUniqueId() +"'");
			} catch (Exception e) {
				return;
			}
			
			if (rows.isEmpty() || rows.get(0).isEmpty() || rows.get(0).get(0) == null) {
				return;
			}
			
			double kdr = ((BigDecimal) rows.get(0).get(0)).doubleValue();
			double value = 0;
			if (kdr <= 1.5) {
				value = 2000 * kdr;
			} else if (kdr <= 20000) {
				value = 3000 * Math.log(kdr) + 1784;
			}
			
			Economy.addMoney(damager, value, true);
			damager.sendMessage(FormatUtil.format("&eYou just earned &c${0} &efor "
					+ "killing &c{1}.", value, damaged.getDisplayName()));
		}
	}
*/
	@EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem() == null) {
            return;
        }

        if(event.getItem().getType().equals(Material.COOKIE)) {
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setSaturation(5);
        }
        if(event.getItem().getType().equals(Material.APPLE)) {
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setSaturation(10);
        }
        if(event.getItem().getType().equals(Material.BREAD)) {
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setSaturation(20);
        }
        if(event.getItem().getType().equals(Material.COOKED_CHICKEN)) {
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setSaturation(45);
        }
        if(event.getItem().getType().equals(Material.COOKED_BEEF)) {
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setSaturation(60);
        }

        if(!event.getItem().getType().equals(Material.POTION)) {
            return;
        }

        if (event.getPlayer().hasPermission("prison.usepotion")) {
            return;
        }

        event.setCancelled(true);
    }

	@EventHandler
	public void onPotionSplash(PotionSplashEvent event) {
		if (!(event.getPotion().getShooter() instanceof Player)
				|| !((Player) event.getPotion().getShooter()).hasPermission("prison.usepotion")) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onServerPingEvent(ServerListPingEvent e) {
		e.setMotd("§bVector§3Prison§8 - §cClosed Beta v1.0\n§cNews: §fPublic Beta - Sunday 26th November");
	}
 
	@EventHandler
	public void onBalance(BalanceUpdateEvent e) {
		new ScoreboardHandler(e.getPlayer()).setBoard();
	}
	
	@EventHandler
	public void onHealthRegen(EntityRegainHealthEvent event) {
		if (!(event.getEntity() instanceof Player)
				|| event.getRegainReason() != RegainReason.SATIATED)
			return;

		Player player = (Player) event.getEntity();
		UUID uuid = player.getUniqueId();
		long elapsed = 99999;

		if (lastRegen.containsKey(uuid)) {
			elapsed = System.currentTimeMillis() - lastRegen.get(uuid);
			lastRegen.remove(uuid);
		}

		if (elapsed > 500) {
			if (warnings.containsKey(uuid))
				warnings.remove(uuid);
			lastRegen.put(uuid, System.currentTimeMillis());
			return;
		}

		int warningAmount = 0;
		if (warnings.containsKey(uuid)) {
			warningAmount = warnings.get(uuid);
			warnings.remove(uuid);
		}

		if (warningAmount > 1) {
			player.damage(1.0);
			event.setCancelled(true);
		}

		warningAmount++;
		warnings.put(uuid, warningAmount);
		lastRegen.put(uuid, System.currentTimeMillis());
	}

	@EventHandler (ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String command = event.getMessage().split(" ")[0].replace("/", "").toLowerCase();
		
		/*if (command.equalsIgnoreCase("fix")) {
			RegionManager rg = MineParadise.getWorldGuard().getRegionManager(player.getWorld());
			ApplicableRegionSet regionSet = rg.getApplicableRegions(player.getLocation());
			LocalPlayer lp = MineParadise.getWorldGuard().wrapPlayer(player);

			// If pvp area
			if (regionSet.testState(lp, DefaultFlag.PVP)) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You cannot fix items in the heat of PvP! Try leaving the PvP zone first.");
			}
			return;
		} */
		
		if (!command.equalsIgnoreCase("reload") && !command.equalsIgnoreCase("rl"))
			return;
		event.setCancelled(true);
		if (!player.isOp() && !player.hasPermission("*"))
			return;

		Bukkit.dispatchCommand(player, "save");
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload");
			}
		}.runTaskLater(plugin, 10 + (Bukkit.getOnlinePlayers().size() * 2));
	}

	@EventHandler
	public void onCraft(PrepareItemCraftEvent event) {
		String matString = event.getRecipe().getResult().getType().toString().toLowerCase();
		if (matString.contains("axe") || matString.contains("shovel"))
			event.getView().close();
	}
	
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock() == null) {
				return;
			}
			
			if(e.getClickedBlock().getType() == Material.WORKBENCH 
					|| e.getClickedBlock().getType() == Material.ENDER_CHEST 
					|| e.getClickedBlock().getType() == Material.CHEST 
					|| e.getClickedBlock().getType() == Material.TRAPPED_CHEST
					|| e.getClickedBlock().getType() == Material.ANVIL
					|| e.getClickedBlock().getType() == Material.BEACON
					|| e.getClickedBlock().getType() == Material.BURNING_FURNACE
					|| e.getClickedBlock().getType() == Material.FURNACE
					|| e.getClickedBlock().getType() == Material.STORAGE_MINECART) {
				
				e.setCancelled(true);
				e.setUseInteractedBlock(Result.DENY);
				return;
				
			}
		}
	}

	public void remove(UUID uuid) {
		if (lastRegen.containsKey(uuid))
			lastRegen.remove(uuid);
		if (warnings.containsKey(uuid))
			warnings.remove(uuid);
	}
}