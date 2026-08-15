package com.vectorprison.economy;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.vanquishnetwork.sql.MySQL;
import org.vanquishnetwork.sql.SQLAction;
import org.vanquishnetwork.sql.SQLSet;
import org.vanquishnetwork.sql.Table;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vectorprison.VectorPrison;
import com.vectorprison.PrisonTable;
import com.vectorprison.api.BalanceUpdateEvent;

public class Economy implements net.milkbowl.vault.economy.Economy {
	// 0 - Current money, 1 - Total all time
	private static Map<UUID, Double[]> cache = Maps.newHashMap();
	private static Economy instance;
	
	public static double getMoney(final OfflinePlayer player) {
		return getMoney(player.getUniqueId());
	}
	
	public static double getMoney(final UUID uuid) {
		if (!getInstance().isEnabled()) {
			return 0;
		}
		
		double money = 0;
		
		if (cache.containsKey(uuid)) {
			money = cache.get(uuid)[0];
		} else {
			updateMoney(uuid);
		}
		
		return money;
	}
	
	public static double getTotalMoney(final OfflinePlayer player) {
		return getTotalMoney(player.getUniqueId());
	}
	
	public static double getTotalMoney(final UUID uuid) {
		double totalMoney = 0;
		
		if (cache.containsKey(uuid)) {
			totalMoney = cache.get(uuid)[1];
		} else {
			updateMoney(uuid);
		}
		
		return totalMoney;
	}
	
	private static void updateMoney(final UUID uuid) {
		new SQLAction() {
			private double money, totalMoney;
			
			@Override
			public void run() {
				Table t = PrisonTable.getTable();
				Object[] o = { 
						MySQL.getInstance().get(uuid.toString(), PrisonTable.MONEY.name, t),
						MySQL.getInstance().get(uuid.toString(), PrisonTable.TOTAL_MONEY.name, t)
						};
				
				money = (o[0] == null ? 0.0 : (double) o[0]);
				totalMoney = (o[1] == null ? 0.0 : (double) o[1]);
			}
			
			@Override
			protected void done() {
				cache.put(uuid, new Double[] { money, totalMoney });
			}			
		};
	}
	
	public static void setMoney(OfflinePlayer player, double money) {
		setMoney(player.getUniqueId(), money);	
		
		BalanceUpdateEvent event = new BalanceUpdateEvent(player.getPlayer());
		VectorPrison.getInstance().getServer().getPluginManager().callEvent(event);
		
		
	}
	
	public static void setMoney(UUID uuid, double money) {
		if (cache.containsKey(uuid)) {
			new SQLSet(uuid, PrisonTable.MONEY.name, money, PrisonTable.getTable());
			Double[] info = cache.get(uuid);
			info[0] = money;
			cache.put(uuid, info);
			
		}	
	}
	
	public static void setTotalMoney(OfflinePlayer player, double money) {
		setTotalMoney(player.getUniqueId(), money);
	}
	
	public static void setTotalMoney(UUID uuid, double money) {	
		double original = getTotalMoney(uuid);
		
		if (cache.containsKey(uuid)) {
			new SQLSet(uuid, PrisonTable.TOTAL_MONEY.name, money, PrisonTable.getTable());
			Double[] info = cache.get(uuid);
			info[1] = money;
			cache.put(uuid, info);
		}
		
		//Ranks.updateRank(uuid);
		
		if (original > 1000) {
			Player p = null;
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.getUniqueId().equals(uuid)) {
					p = pl;
				}
			}
			
			if (p == null) {
				return;
			}
			
			
			}
	}
	
	public static void addMoney(OfflinePlayer player, double money, boolean updateTotal) {
		if (!getInstance().isEnabled()) {
			return;
		}

		setMoney(player, getMoney(player) + money);
		if (updateTotal) {
			setTotalMoney(player, getTotalMoney(player) + money);
		}
	}
	
	public static void addMoney(OfflinePlayer player, double money) {
		addMoney(player, money, true);
	}
	
	public static void subtractMoney(OfflinePlayer player, double money, boolean updateTotal) {
		addMoney(player, -money, updateTotal);
	}
	
	public static void subtractMoney(OfflinePlayer player, double money) {
		subtractMoney(player, money, true);
	}
	
	public static void load() {
		cache.clear();
	}
	
	/**
	 * Make sure to call this method from the main thread if you want to save synchronously.
	 */
	public static void syncSave() {
		for (Entry<UUID, Double[]> entry : cache.entrySet()) {
			MySQL.getInstance().set(entry.getKey().toString(), "uuid", PrisonTable.MONEY.name, 
					entry.getValue()[0], PrisonTable.getTable());
			MySQL.getInstance().set(entry.getKey().toString(), "uuid", PrisonTable.TOTAL_MONEY.name, 
					entry.getValue()[1], PrisonTable.getTable());
		}
	}
	
	public static void removePlayer(Player player) {
		UUID uuid = player.getUniqueId();
		cache.remove(uuid);
	}
	
	public static Economy getInstance() {
		if (instance == null) {
			instance = new Economy();
		}
		
		return instance;
	}

	@Override
	public EconomyResponse bankBalance(String arg0) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse bankDeposit(String arg0, double arg1) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse bankHas(String arg0, double arg1) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse bankWithdraw(String arg0, double arg1) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse createBank(String arg0, String arg1) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public boolean createPlayerAccount(String arg0) {
		return false;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer arg0) {
		return false;
	}

	@Override
	public boolean createPlayerAccount(String arg0, String arg1) {
		return false;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer arg0, String arg1) {
		return false;
	}

	@Override
	public String currencyNamePlural() {
		return "Dollars";
	}

	@Override
	public String currencyNameSingular() {
		return "Dollar";
	}

	@Override
	public EconomyResponse deleteBank(String arg0) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse depositPlayer(String name, double amount) {
		return depositPlayer(name, null, amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		return depositPlayer(player, null, amount);
	}

	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse depositPlayer(String name, String world, double amount) {
		return depositPlayer(Bukkit.getOfflinePlayer(name), world, amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String world,
			double amount) {
		// Error with quicksell calling this method even though doing eco givem as well
//		addMoney(player, amount, false);
		return new EconomyResponse(amount, getMoney(player), EconomyResponse.ResponseType.SUCCESS, "");
	}

	@Override
	public String format(double amount) {
		return VectorPrison.getInstance().getFormattedDouble(amount);
	}

	@Override
	public int fractionalDigits() {
		return 0;
	}

	@Override
	public double getBalance(String name) {
		return getBalance(name, null);
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		return getMoney(player);
	}

	@SuppressWarnings("deprecation")
	@Override
	public double getBalance(String name, String world) {
		return getBalance(Bukkit.getOfflinePlayer(name), world);
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		return getMoney(player);
	}

	@Override
	public List<String> getBanks() {
		return Lists.newArrayList();
	}

	@Override
	public String getName() {
		return "VectorPrison";
	}

	@Override
	public boolean has(String name, double amount) {
		return has(name, null, amount);
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		return has(player, null, amount);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean has(String name, String world, double amount) {
		return has(Bukkit.getOfflinePlayer(name), null, amount);
	}

	@Override
	public boolean has(OfflinePlayer player, String world, double amount) {
		return getMoney(player) > amount;
	}

	@Override
	public boolean hasAccount(String name) {
		return hasAccount(name, null);
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		return hasAccount(player, null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean hasAccount(String name, String world) {
		return hasAccount(Bukkit.getOfflinePlayer(name), world);
	}

	@Override
	public boolean hasAccount(OfflinePlayer arg0, String arg1) {
		return true;
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public EconomyResponse isBankMember(String arg0, String arg1) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, String arg1) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public boolean isEnabled() {
		return VectorPrison.getInstance().isEnabled();
	}

	@Override
	public EconomyResponse withdrawPlayer(String name, double amount) {
		return withdrawPlayer(name, null, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		return withdrawPlayer(player, null, amount);
	}

	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse withdrawPlayer(String name, String world, double amount) {
		return withdrawPlayer(Bukkit.getOfflinePlayer(name), world, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String world,
			double amount) {
//		subtractMoney(player, amount, false);
		return new EconomyResponse(amount, getMoney(player), EconomyResponse.ResponseType.SUCCESS, "");
	}
}