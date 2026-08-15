package com.vectorprison.bases;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.vectorprison.VectorPrison;

public class BaseBuilder {

	public static void buildBase(Location nextloc, Player player) throws IOException {
		//BuildingUtils.setBuilding(player, BuildingUtils.getBuilding("KOTHEmerald"));
	    //BuildingUtils.getPlayerBuilding(player).build(nextloc);
		//Schematic.initSchematic("KOTHEmerald");
		Schematic.loadSchematic(new File(VectorPrison.getInstance().getDataFolder() + "/schematics/" + "KOTHEmerald.schematic")).pasteSchematic(nextloc);
	}

}
