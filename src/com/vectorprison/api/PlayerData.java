package com.vectorprison.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.vanquishnetwork.sql.MySQL;

import com.vectorprison.PrisonTable;

public class PlayerData {
	
	/**
	 * Gets players deaths
	 * @param p - player
	 * @return deaths of player
	 */
	public static int getTotalDeaths(Player p) {
		return (int) (double)MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.DEATHS.toString(), PrisonTable.getTable());
	}
	
	/**
	 * Gets blocks broken
	 * @param p - player
	 * @return blocks broken
	 */
	public static int getBlocksBroken(Player p) {
		return (int) (double)MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.BLOCKSBROKEN.toString(), PrisonTable.getTable());
	}

	
	/**
	 * Gets skill points
	 * @param p - player
	 * @return skill points
	 */
	public static int getSkillPoints(Player p) {
		return  (int) (double)MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.SKILLPOINTS.toString(), PrisonTable.getTable());
	}
	
	/**
	 * Gets players xp 
	 * @param p - player
	 * @retur nxp
	 */
	public static int getLevel(Player p) {
		return (int) (double)MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.MININGXP.toString(), PrisonTable.getTable());
	}
	
	/**
	 * Gets players level
	 * @param p - player
	 * @return level
	 */
	
	public static int getPlayerLevel(Player p) {
		int nCurrentXP = getLevel(p);
		
		for (int i = 0; i < 105; i++) {
			if (nCurrentXP >= xpForLevel(i) && nCurrentXP < xpForLevel(i+1)){
				return i;
		
			}
		}
		return 0;
		
		
	}
	
			
	public static int xpForLevel(int l) {		
		
		if (l < 82) {
		
			switch (l) {

			case 1:
				return 0;
			case 2: 
				return 24;
			case 3: 
				return 54;
			case 4: 
				return 96;
			case 5: 
				return 160;
			case 6: 
				return 250;
			case 7: 
				return 420;
			case 8: 
				return 640;
			case 9: 
				return 850;
			case 10:
				return 1220;
			case 11:
				return 1944;
			case 12:
				return 2452;
			case 13:
				return 3216;
			case 14:
				return 5054;
			case 15: 
				return 7562;
			case 16:
				return 11329;
			case 17:
				return 13690;
			case 18:
				return 15889;
			case 19:
				return 17500;
			case 20:
				return 24150;
			case 21:
				return 32024;
			case 22:
				return 40444;
			case 23:
				return 48024;
			case 24:
				return 52024;
			case 25:
				return 55104;
			case 26:
				return 66000;
			case 27:
				return 77555;
			case 28:
				return 85988;
			case 29:
				return 94624;
			case 30:
				return 110093;
			case 31:
				return 125666;
			case 32: 
				return 144644;
			case 33:
				return 175444;
			case 34:
				return 200000;
			case 35:
				return 222284;
			case 36:
				return 240032;
			case 37:
				return 266122;
			case 38:
				return 290654;
			case 39:
				return 320947;
			case 40:
				return 354555;
			case 41:
				return 386052;
			case 42:
				return 435063;
			case 43:
				return 475052;
			case 44:
				return 494053;
			case 45:
				return 535041;
			case 46:
				return 587293;
			case 47:
				return 645395;
			case 48:
				return 700123;
			case 49:
				return 776989;
			case 50:
				return 845921;
			case 51:
				return 945921;
			case 52:
				return 1065921;
			case 53:
				return 1149567;
			case 54:
				return 1339145;
			case 55:
				return 1539102;
			case 56:
				return 1710294;
			case 57:
				return 1930329;
			case 58:
				return 2190031;
			case 59:
				return 2342901;
			case 60:
				return 2539242;
			case 61:
				return 2734392;
			case 62:
				return 2930230;
			case 63:
				return 3239252;
			case 64:
				return 3420939;
			case 65:
				return 3753032;
			case 66:
				return 4003290;
			case 67:
				return 4302393;
			case 68:
				return 4639201;
			case 69:
				return 4942032;
			case 70:
				return 5291023;
			case 71:
				return 5990234;
			case 72:
				return 6842434;
			case 73:
				return 7995031;
			case 74:
				return 8927921;
			case 75:
				return 9798537;
			case 76:
				return 11015031;
			case 77:
				return 12458292;
			case 78:
				return 13510319;
			case 79:
				return 14692302;
			case 80:
				return 16410219;
			case 81:
				return 17582912;
			}
		}
		return l;
		

	}
	
	
	
	/**
	 * Checks if a player has ever completed a quest, if not returns false allowing for all values too be set to 0.
	 * @param p - Player.
	 * @return True (player has completed a quest) or False (player has not completed a quest)
	 */
	public static boolean questCompleted(Player p) {
	Object sql1 = MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.QUESTCOMP.toString(), PrisonTable.getTable());
	Object sql2 = MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.WQUESTCOMP.toString(), PrisonTable.getTable());
	Object sql3 = MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.DQUESTCOMP.toString(), PrisonTable.getTable());
	
	if (sql1 == null && sql2 == null && sql3 == null) {
		return false;
	} else {
		return true;
		}	
	}
	
	/**
	 * Gets quests completed, converts to integer.
	 * @param p - player
	 * @param t - type of quest
	 * @return daily completed quests as an integer.
	 */
	public static int getQuests(Player p, String t) {
		
		if (!questCompleted(p)) {
			return 0;
		}
		
		switch (t) {
			case "daily":
				double nQuests = (double) MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.DQUESTCOMP.toString(), PrisonTable.getTable());
				return (int) nQuests;
			case "weekly":
				double nQuests1 = (double) MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.WQUESTCOMP.toString(), PrisonTable.getTable());
				return (int) nQuests1;
			case "regular":
				double nQuests2 = (double) MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.QUESTCOMP.toString(), PrisonTable.getTable());
				return (int) nQuests2;
			default:
				return 0;
				
		
		}

	}
	
	/**
	 * Gets completed quests (t) for (p). Private for usage primarily inside of this API class.
	 * @param p
	 * @param t
	 * @return completed quests as double
	 */
	private static double getQuestsd(Player p, String t) 
	{
		if (t == "daily") {
			double nQuests = (double) MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.DQUESTCOMP.toString(), PrisonTable.getTable());
			
			return nQuests;			
			
		} else if (t == "weekly") {
			double nQuests = (double) MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.WQUESTCOMP.toString(), PrisonTable.getTable());
			
			return nQuests;
			
		} else if (t == "regular") {
			double nQuests = (double) MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.QUESTCOMP.toString(), PrisonTable.getTable());
			
			return nQuests;
		} else {
			return 0;			
		}
		
	}
	
	/**
	 * Increments quest amount.
	 * @param p - Player
	 * @param t - Type of quest (Format: PrisonTable.THINGTOINCREMENT.toString())
	 */
	public static void incrementQuest(Player p, String t) {
	if (questCompleted(p)) {
		MySQL.getInstance().set(p.getUniqueId().toString(), t, getQuestsd(p, t) + 1, PrisonTable.getTable());
		
	} else {
		MySQL.getInstance().set(p.getUniqueId().toString(), t, 0, PrisonTable.getTable());
		MySQL.getInstance().set(p.getUniqueId().toString(), t, 0, PrisonTable.getTable());
		MySQL.getInstance().set(p.getUniqueId().toString(), t, 0, PrisonTable.getTable());
		
		MySQL.getInstance().set(p.getUniqueId().toString(), t, getQuestsd(p, t) + 1, PrisonTable.getTable());
	}
		
		
	}
	
	/**
	 * Gets true/false from base.
	 */
	
	public static boolean hasBase(Player p) {
		String s = (String) MySQL.getInstance().get(p.getUniqueId().toString(), PrisonTable.BASE.toString(), PrisonTable.getTable());
			
		if (s == "T") {
			return true;
			
		} else if (s == "F") {
			return false;
		}
		MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.BASE.toString(), "F", PrisonTable.getTable());
		return false;
		
	}
	
	/**
	 * Sets true of false to base.
	 * @param p 
	 * 
	 */
	
	public static void setBase(Player p, String state) {
		if (state != "T" || state != "F") {
			Bukkit.getConsoleSender().sendMessage("[VectorPrisonGame] Could not set " + p + "'s base, incorrect format.");
			return;
		}
		MySQL.getInstance().set(p.getUniqueId().toString(), PrisonTable.BASE.toString(), state, PrisonTable.getTable());
		
	}
	
	
}
