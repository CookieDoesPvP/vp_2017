package com.vectorprison.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.vectorprison.VectorPrison;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

public class TitleSender{

	private VectorPrison plugin;

	public TitleSender(VectorPrison plugin) {
		this.plugin = plugin;
	}
	
	public static String color(String msg)
    {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
	
	public static void sendTitle(Player player, String text, int fadein, int displaytime, int fadeout)
	{
		text = color(text);
		fadein = 20 * fadein;
		displaytime = 20 * displaytime;
		fadeout = 20 * fadeout;

		PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
				IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), fadein, displaytime, fadeout);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
	}

	public static void sendSubTitle(Player player, String text, int fadein, int displaytime, int fadeout)
	{
		text = color(text);
		fadein = 20 * fadein;
		displaytime = 20 * displaytime;
		fadeout = 20 * fadeout;

		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
				IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), fadein, displaytime, fadeout);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitle);
	}

	public void removeTitle(Player player)
	{
		PacketPlayOutTitle clear = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.CLEAR,
				IChatBaseComponent.ChatSerializer.a("{\"text\":\"Clear\"}"), 20, 40, 20);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(clear);
	}

	public void resetTitle(Player player)
	{
		PacketPlayOutTitle reset = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET,
				IChatBaseComponent.ChatSerializer.a("{\"text\":\"Reset\"}"), 20, 40, 20);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(reset);
}
	
}
