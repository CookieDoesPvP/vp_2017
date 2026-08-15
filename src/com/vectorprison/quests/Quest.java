package com.vectorprison.quests;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.vectorprison.account.Account;
import com.vectorprison.economy.Economy;

public class Quest implements Listener {
	
	public static Set<Quest> quests = new HashSet<>();
	
	private String questName;
	private Material questMaterial;
	private EntityType questEntity;
	private QuestType questType;
	private RewardType questRewardType;
	private Integer questAmountNeeded;
	private String questRewardCommand;
	private Integer questRewardMoney;
	private ItemStack questRewardItem;
	private Integer questRewardXP;
	private String questRewardMessage;
	
	enum QuestType {
		BlockBreak, BlockPlace, KillOthers, KillMobs
	}
	
	enum RewardType {
		Command, Item, Money, XP
	}
	
	public Quest(String name, QuestType qt) {
		questName = name;
		questType = qt;
		
		quests.add(this);
	}
	
    public static Quest getQuest(String questname) {
        for (Quest quest : quests) {
            if (quest.getQuestName().equals(questname)) {
                return quest;
            }
        }
        return null;
    }
	
	public void setQuestMaterial(Material mat) {
		questMaterial = mat;
	}
	
	public Material getQuestMaterial() {
		return questMaterial;
	}
	
	public String getQuestName() {
		return questName;
	}
	
	public QuestType getQuestType() {
		return questType;
	}
	
	public Integer getQuestAmountNeeded() {
		return questAmountNeeded;
	}
	
	public void setQuestAmountNeeded(Integer i) {
		questAmountNeeded = i;
	}
	
	public void setQuestRewardMessage(String s) {
		questRewardMessage = s;
	}
	
	public RewardType getQuestRewardType() {
		return questRewardType;
	}
	
	public Integer getQuestRewardXP() {
		return questRewardXP;
	}
	
	public void setQuestRewardType(RewardType rt) {
		questRewardType = rt;
	}
	
	public void setQuestRewardMoney(Integer i) {
		questRewardMoney = i;
	}

	public void setQuestRewardItem(ItemStack is) {
		questRewardItem = is;
	}
	
	public void setQuestRewardCommand(String s) {
		questRewardCommand = s;
	}
	
	public void setQuestRewardXP(Integer i) {
		questRewardXP = i;
	}
	
	public void setQuestEntity(EntityType e) {
		questEntity = e;
	}
	
	public EntityType getQuestEntity() {
		return questEntity;
	}
	
	/*
	 * Block Breaking Task
	 */
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(questType == QuestType.BlockBreak) {
			if(e.getBlock().getType() == questMaterial) {
				Account account = Account.getAccount(e.getPlayer());
				assert account != null;
				if(account.getActiveQuest() != this) {
					return;
				}
				
				if(account.getCompletedQuests().contains(this)) {
					return;
				}
				
				if(account.getQuestProgress() == questAmountNeeded - 1) {
					account.setActiveQuest(null);
					account.setQuestProgress(0);
					account.getCompletedQuests().add(this);
					e.getPlayer().sendMessage(questRewardMessage);
					if(questRewardType == RewardType.Money) {
						Economy.addMoney(e.getPlayer(), questRewardMoney);
						return;
					}
					if(questRewardType == RewardType.Command) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), questRewardCommand);
						return;
					}
					if(questRewardType == RewardType.Item) {
						e.getPlayer().getInventory().addItem(questRewardItem);
						return;
					}
					if(questRewardType == RewardType.XP) {
						return;
					}
				}
				
				account.addQuestProgress(1);
				return;
			}
		}
	}
	
	/*
	 * Block Placing Task
	 */
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(questType == QuestType.BlockPlace) {
			if(e.getBlock().getType() == questMaterial) {
				Account account = Account.getAccount(e.getPlayer());
				assert account != null;
				if(account.getActiveQuest() != this) {
					return;
				}
				
				if(account.getCompletedQuests().contains(this)) {
					return;
				}
				
				if(account.getQuestProgress() == questAmountNeeded - 1) {
					account.setActiveQuest(null);
					account.setQuestProgress(0);
					account.getCompletedQuests().add(this);
					e.getPlayer().sendMessage(questRewardMessage);
					if(questRewardType == RewardType.Money) {
						Economy.addMoney(e.getPlayer(), questRewardMoney);
						return;
					}
					if(questRewardType == RewardType.Command) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), questRewardCommand);
						return;
					}
					if(questRewardType == RewardType.Item) {
						e.getPlayer().getInventory().addItem(questRewardItem);
						return;
					}
					if(questRewardType == RewardType.XP) {
						return;
					}
				}
				
				account.addQuestProgress(1);
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(EntityDeathEvent e) {
		if(questType == QuestType.KillOthers) {
			if(e.getEntityType() == questEntity) {
				if(e.getEntity().getKiller().getType() == EntityType.PLAYER) {
					Account account = Account.getAccount(e.getEntity().getKiller());
					assert account != null;
					if(account.getActiveQuest() != this) {
						return;
					}
					
					if(account.getCompletedQuests().contains(this)) {
						return;
					}
					
					if(account.getQuestProgress() == questAmountNeeded - 1) {
						account.setActiveQuest(null);
						account.setQuestProgress(0);
						account.getCompletedQuests().add(this);
						e.getEntity().getKiller().sendMessage(questRewardMessage);
						if(questRewardType == RewardType.Money) {
							Economy.addMoney(e.getEntity().getKiller(), questRewardMoney);
							return;
						}
						if(questRewardType == RewardType.Command) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), questRewardCommand);
							return;
						}
						if(questRewardType == RewardType.Item) {
							e.getEntity().getKiller().getInventory().addItem(questRewardItem);
							return;
						}
						if(questRewardType == RewardType.XP) {
							return;
						}
					}
					
					account.addQuestProgress(1);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onMobDeath(EntityDeathEvent e) {
		if(questType == QuestType.KillMobs) {
			if(e.getEntityType() == questEntity) {
				if(e.getEntity().getKiller().getType() == EntityType.PLAYER) {
					Account account = Account.getAccount(e.getEntity().getKiller());
					assert account != null;
					if(account.getActiveQuest() != this) {
						return;
					}
					
					if(account.getCompletedQuests().contains(this)) {
						return;
					}
					
					if(account.getQuestProgress() == questAmountNeeded - 1) {
						account.setActiveQuest(null);
						account.setQuestProgress(0);
						account.getCompletedQuests().add(this);
						e.getEntity().getKiller().sendMessage(questRewardMessage);
						if(questRewardType == RewardType.Money) {
							Economy.addMoney(e.getEntity().getKiller(), questRewardMoney);
							return;
						}
						if(questRewardType == RewardType.Command) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), questRewardCommand);
							return;
						}
						if(questRewardType == RewardType.Item) {
							e.getEntity().getKiller().getInventory().addItem(questRewardItem);
							return;
						}
						if(questRewardType == RewardType.XP) {
							return;
						}
					}
					
					account.addQuestProgress(1);
					return;
				}
			}
		}
	}
	
	
	

}
