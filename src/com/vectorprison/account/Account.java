package com.vectorprison.account;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.vectorprison.quests.Quest;

public class Account {

    public static Set<Account> accounts = new HashSet<>();

    private Player player;
    private String pclass;
    private Quest quest;
    private Integer questProgress;
    private Set<Quest> completedQuests = new HashSet<>();

    public Account(Player player) {
        this.player = player;

        accounts.add(this);
    }

    public static Account getAccount(Player player) {
        for (Account account : accounts) {
            if (account.getPlayer().getName().equals(player.getName())) {
                return account;
            }
        }
        return null;
    }

    public static Account getAccount(UUID uuid) {
        return getAccount(Bukkit.getPlayer(uuid));
    }
    
    public Player getPlayer() {
    	return player;
    }
    
    public String getClassType() {
    	return pclass;
    }
    
    public void setClassType(String s) {
    	pclass = s;
    }
    
    public void setPlayer(Player p) {
    	player = p;
    }
    
    public Quest getActiveQuest() {
    	return quest;
    }
    
    public void setActiveQuest(Quest q) {
    	quest = q;
    }
    
    public Integer getQuestProgress() {
		return questProgress;
    }
    
    public void setQuestProgress(Integer i) {
    	questProgress = i;
    }
    
    public void addQuestProgress(Integer i) {
    	questProgress = questProgress + i;
    }
    
    public void removeQuestProgress(Integer i) {
    	questProgress = questProgress - i;
    }
    
    public Set<Quest> getCompletedQuests() {
    	return completedQuests;
    }

}