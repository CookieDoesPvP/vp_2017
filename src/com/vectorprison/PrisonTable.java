package com.vectorprison;

import java.util.Map;

import org.vanquishnetwork.sql.DataType;
import org.vanquishnetwork.sql.Table;

import com.google.common.collect.Maps;

public enum PrisonTable {
	UUID("uuid", "char(36)", "<uuid>"),
	TOTAL_MONEY("total_money", DataType.DOUBLE),
	MONEY("money", DataType.DOUBLE),
	MININGXP("xp", DataType.DOUBLE),
	BLOCKSBROKEN("bbroken", DataType.DOUBLE),
	KILLS("kills", DataType.DOUBLE),
	DEATHS("deaths", DataType.DOUBLE),
	SKILLPOINTS("skillpoints", DataType.DOUBLE),
	WQUESTCOMP("weeklycomp", DataType.DOUBLE),
	QUESTCOMP("quetscomp", DataType.DOUBLE),
	DQUESTCOMP("dailycomp", DataType.DOUBLE),
	BASE("base", DataType.TEXT);
	
	// Traits/buyables
	// Tool info? total mined, total xp, slot?
	
	public static final String TABLE_NAME = "PRISON_PLAYERS";
	
	public final String name;
	public final String type;
	public final String def;
	PrisonTable(String columnName, String dataType) {
		this(columnName, dataType, "");
	}
		
	PrisonTable(String columnName, String dataType, String def) {
		this.name = columnName;
		this.type = dataType;
		this.def = def;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static Table getTable() {
		return Table.getTable(TABLE_NAME);
	}
	
	public static final Table constructTable() {
		// If this executes at any point after onLoad() then will not affect anything
		Map<String, String> columns = Maps.newLinkedHashMap();
		for (PrisonTable column : values())
			columns.put(column.name + " " + column.type, column.def);
		return new Table(TABLE_NAME, columns);
	}
}
