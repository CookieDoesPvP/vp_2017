
package com.vectorprison.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BalanceUpdateEvent extends Event implements Cancellable {
	
	private static Player player;
    private boolean isCancelled;
	
	public BalanceUpdateEvent(Player player) {
		this.player = player;
		this.isCancelled = false;
	}
	
	  public boolean isCancelled() {
	        return this.isCancelled;
	    }

	    public void setCancelled(boolean isCancelled) {
	        this.isCancelled = isCancelled;
	    }
	private static final HandlerList HANDLERS = new HandlerList();
	
	  public HandlerList getHandlers() {
	        return HANDLERS;
	    }

	  public static HandlerList getHandlerList() {
	        return HANDLERS;
	  }
	        
	  public static Player getPlayer() {
	        return player;
	     }
	}