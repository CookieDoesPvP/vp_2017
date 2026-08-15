package com.vectorprison.bases;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.vectorprison.VectorPrison;

public class Base {
	
    public static Set<Base> bases = new HashSet<>();
	
	VectorPrison plugin;
    private UUID owner;
    private UUID uniqueId;
    private Location core;
    private Location barracks;
    private Location quarry;
	
	public Base(OfflinePlayer creator, Location coreloc, Location barracksloc, Location quarryloc) {
		this.plugin = VectorPrison.getInstance(); 
		this.owner = creator.getUniqueId();
		this.core = coreloc;
		this.barracks = barracksloc;
		this.quarry = quarryloc;
		if(!hasBase(creator)) {
			bases.add(this);
		}
	}

    public static Base getBase(OfflinePlayer creator) {
        for (Base base : bases) {
            if (base.getOwner().equals(creator.getUniqueId())) {
                return base;
            }
        }
        return null;
    }
	
    public static Base getBase(UUID uuid) {
        for (Base base : bases) {
            if (base.getUniqueId() == uuid) {
                return base;
            }
        }
        return null;
    }
    
    public UUID getUniqueId() {
    	return uniqueId;
    }
    
    public Location getCoreLocation() {
    	return core;
    }
    
    public void setCoreLocation(Location coreloc) {
    	core = coreloc;
    }
    
    public Location getBarracksLocation() {
    	return barracks;
    }
    
    public void setBarracksLocation(Location baloc) {
    	barracks = baloc;
    }
    
    public Location getQuarryLocation() {
    	return quarry;
    }
    
    public void setQuarryLocation(Location quloc) {
    	quarry = quloc;
    }
    
    public void setUniqueId(UUID uuid) {
    	uniqueId = uuid;
    }
    
    public void setOwner(UUID uuid) {
    	owner = uuid;
    }
    
    public UUID getOwner() {
    	return owner;
    }
	
	public static boolean hasBase(OfflinePlayer creator) {
	        return getBase(creator) != null;
	}
	
}
