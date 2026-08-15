package com.vectorprison.tutorial;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.vectorprison.VectorPrison;

public class Tutorial {
	
	Player player;
	String tutorial;
	
	public Tutorial(Player p, String type) {
		this.player = p;
		this.tutorial = type;
	}
	
	public void setPlayer(Player p) {
		player = p;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void start() {
		if(tutorial.equalsIgnoreCase("mining")) {
			player.sendMessage("§d§lTutorial Tutor§8: §7I will be conducting this §eMining Tutorial§7 today.");
			new BukkitRunnable() {
				public void run() {
					player.sendMessage(" ");
					player.sendMessage("§d§lTutorial Tutor§8: §7I will help you as much as I can, but if you have any §equestions§7 after the tutorial, feel free to ask a §estaff member §7in chat!");
				}
			}.runTaskLater(VectorPrison.getInstance(), 60L);
			new BukkitRunnable() {
				public void run() {
					player.sendMessage(" ");
					player.sendMessage("§d§lTutorial Tutor§8: §7Let's get started!");
				}
			}.runTaskLater(VectorPrison.getInstance(), 160L);
			new BukkitRunnable() {
				public void run() {
					player.sendMessage(" ");
					player.sendMessage(" §e§l*PICKAXES / ESSENCE*");
					player.sendMessage(" ");
					player.sendMessage("§d§lTutorial Tutor§8: §7Pickaxe's can be used on ore, and only ore. When mining ore, you will gain §ePickaxe Essence §7which can be used to enchant your pickaxe/items. It is a very useful resource! You will gain a §eset amount §7depending on the §eore/block§7 you mine.");
				}
			}.runTaskLater(VectorPrison.getInstance(), 200L);
			new BukkitRunnable() {
				public void run() {
					player.sendMessage(" ");
					player.sendMessage(" §e§l*ENCHANTING WELL*");
					player.sendMessage(" ");
					player.sendMessage("§d§lTutorial Tutor§8: §7There is a §eEnchanting Well §7just behind the §espawn point§7, if you throw your surging pickaxe into the §eEnchanting Well§7, it will display your possible enchants, and then give you a §esuccess and failure §7rate for each one. You can pick out of the enchants by clicking on them!");
				}
			}.runTaskLater(VectorPrison.getInstance(), 400L);
			new BukkitRunnable() {
				public void run() {
					player.sendMessage(" ");
					player.sendMessage(" §e§l*LEVELLING SYSTEM*");
					player.sendMessage(" ");
					player.sendMessage("§d§lTutorial Tutor§8: §7You might have noticed your \'§eMining Level§7\' on the scoreboard, you will gain §eXP §7for each block you mine in §ePrison§7, once you reach the required amount of §eXP §7for that level, you will §elevel up§7. Certain items have §elevel restrictions§7, and you will need to be a §ehigh enough level§7 to use them. The §ehighest level §7you can achieve is §eMining Level 110§7.");
				}
			}.runTaskLater(VectorPrison.getInstance(), 600L);
			new BukkitRunnable() {
				public void run() {
					player.sendMessage(" ");
					player.sendMessage(" §e§l*RANDOM MINING EVENTS*");
					player.sendMessage(" ");
					player.sendMessage("§d§lTutorial Tutor§8: §7When mining, §erandom events §7will occur, these will range from §erefined ores §7to §evexors§7. Refined ores are pure versions of ores, i.e. §eRefined Iron = Iron Ingot§7. You will also find §eVexors§7, which give you §eawesome items§7!");
				}
			}.runTaskLater(VectorPrison.getInstance(), 800L);
			
			new BukkitRunnable() {
				public void run() {
					player.sendMessage(" ");
					player.sendMessage("§d§lTutorial Tutor§8: §7That\'s all from me in this §eMining Tutorial§7, I hope I have helped you understand some of our §eawesome features §7we have to offer. §eHappy Mining :)");
				}
			}.runTaskLater(VectorPrison.getInstance(), 900L);
		}
		
		if(tutorial.equalsIgnoreCase("guards")) {
			player.sendMessage("§c§lSorry! §7This feature is in development. Stay tuned on our forums for updates!");
		}
		
		if(tutorial.equalsIgnoreCase("selling")) {
			player.sendMessage("§c§lSorry! §7This feature is in development. Stay tuned on our forums for updates!");
		}
		
		if(tutorial.equalsIgnoreCase("bases")) {
			player.sendMessage("§c§lSorry! §7This feature is in development. Stay tuned on our forums for updates!");
		}
	}
	
	

}
