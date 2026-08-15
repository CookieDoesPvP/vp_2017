package com.vectorprison.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.vectorprison.util.ActionBarMessageEvent;
import com.vectorprison.util.ReflectionUntils;

public class ActionBar {
	
	public static void sendActionBar(Player player, String message) {
		// Call the event, if cancelled don't send Action Bar
		ActionBarMessageEvent actionBarMessageEvent = new ActionBarMessageEvent(player, message);
		Bukkit.getPluginManager().callEvent(actionBarMessageEvent);
		if (actionBarMessageEvent.isCancelled())
			return;
		try {
			Class<?> c1 = ReflectionUntils.getCBClass("entity.CraftPlayer");
			Object p = c1.cast(player);
			Object ppoc;
			Class<?> c4 = ReflectionUntils.getNMSClass("PacketPlayOutChat");
			Class<?> c5 = ReflectionUntils.getNMSClass("Packet");
			Class<?> c2;
			c2 = Class.forName("net.minecraft.server.v1_8_R3.ChatComponentText");
			Class<?> c3 = Class.forName("net.minecraft.server.v1_8_R3.IChatBaseComponent");
			Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
			ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(o, (byte) 2);
			Method m1 = c1.getDeclaredMethod("getHandle");
			Object h = m1.invoke(p);
			Field f1 = h.getClass().getDeclaredField("playerConnection");
			Object pc = f1.get(h);
			Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
			m5.invoke(pc, ppoc);
			
		} catch (Exception ex) {
				Bukkit.getConsoleSender().sendMessage("Error with Action Bar.");
			}

	}

}
