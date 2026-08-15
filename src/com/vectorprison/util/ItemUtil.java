package com.vectorprison.util;

import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class ItemUtil {
	
	public ItemStack getItem(String item) {
		String[] data;
		if (item.contains("/"))
			data = item.split("/");
		else
			data = new String[] {item};
		Material material = Material.getMaterial(data[0].toUpperCase().replaceAll(" ", "_"));
		ItemStack itemstack = new ItemStack(Material.AIR, 1);
		if (material != null)
			itemstack = new ItemStack(material, 1);

		try {
			if (data.length > 1) {
				itemstack = addEnchantments(data, itemstack);
				itemstack = addColor(data, itemstack);
			}
		} catch (Exception exception) {
			Bukkit.broadcastMessage(arrayToString(data));
			Bukkit.getLogger().log(Level.WARNING, "Error with " + arrayToString(data) + ": " + exception.getMessage());
		}

		return itemstack;
	}
	
	public ItemStack addEnchantments(String[] data, ItemStack item) throws InvalidInputException {
		if (data == null)
			return item;;
		
		String[] newData;
		if (arrayToString(data).contains("!")) {
			newData = data;
		} else {
			newData = new String[data.length + 1];
			for (int i = 0; i < data.length; i++)
				newData[i] = data[i];
			newData[data.length] = data[data.length - 1];
		}
		
		return addEnchantmentsWithoutItemName(newData[newData.length - 1].split("!"), item);
	}
	
	public ItemStack addEnchantments(String data, ItemStack item) throws InvalidInputException {
		if (data.contains("/"))
			return addEnchantments(data.split("/"), item);
		else
			return addEnchantments(new String[] {data}, item);
	}
	
	public ItemStack addEnchantmentsWithoutItemName(String[] enchantNames, ItemStack item) throws InvalidInputException {
		for (String enchant : enchantNames) {
			item = addEnchantment(enchant, item);
		}
		
		return item;
	}
	
	public ItemStack addEnchantment(String enc, ItemStack item) throws InvalidInputException {
		String[] enchant = null;
		try {
			enchant = enc.split(Pattern.quote("*"));
		} catch (PatternSyntaxException exception) {
			throw new InvalidInputException("The input " + enc + " did not contain an enchantment level preceded by a *.");
		}
		
		item.addUnsafeEnchantment(getEnchantment(enchant[0]), Integer.parseInt(enchant[1]));
		return item;
	}
	
	public ItemStack addColor(String[] data, ItemStack item) throws InvalidInputException {
		if (!item.getType().name().contains("leather"))
			return item;
		
		return addColorAfterCheck(data[data.length - 1].split("!"), item);
	}

	public ItemStack addColor(String data, ItemStack item) throws InvalidInputException {
		if (data.contains("/"))
			return addColor(data.split("/"), item);
		else
			return addColor(new String[] {data}, item);
	}
	
	public ItemStack addColorAfterCheck(String[] data, ItemStack item) throws InvalidInputException {
		ItemMeta meta = item.getItemMeta();
		LeatherArmorMeta leatherarmor = (LeatherArmorMeta) meta;
		try {
			leatherarmor.setColor(Color.fromBGR(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])));
		} catch (NumberFormatException exception) {
			throw new InvalidInputException("The input " + data + " contained something other than an integer where an integer was expected.");
		}
		meta = leatherarmor;
		item.setItemMeta(meta);
		
		return item;
	}
	
	public Enchantment getEnchantment(String name) throws InvalidInputException {
		String newName = name;
		switch (newName.toLowerCase().replaceAll("_", "").replaceAll(" ", "")) {
		case "protection":
			return Enchantment.PROTECTION_ENVIRONMENTAL;
		case "fireprotection":
		case "protectionfire":
			return Enchantment.PROTECTION_FIRE;
		case "blastprotection":
		case "protectionblast":
			return Enchantment.PROTECTION_EXPLOSIONS;
		case "projectileprotection":
		case "protectionprojectile":
			return Enchantment.PROTECTION_PROJECTILE;
		case "featherfalling":
			return Enchantment.PROTECTION_FALL;
		case "respiration":
			return Enchantment.OXYGEN;
		case "aquaaffinity":
			return Enchantment.WATER_WORKER;
		case "thorns":
			return Enchantment.THORNS;
		case "sharpness":
		case "damageall":
			return Enchantment.DAMAGE_ALL;
		case "smite":
			return Enchantment.DAMAGE_UNDEAD;
		case "baneofarthropods":
			return Enchantment.DAMAGE_ARTHROPODS;
		case "knockback":
			return Enchantment.KNOCKBACK;
		case "fireaspect":
			return Enchantment.FIRE_ASPECT;
		case "looting":
			return Enchantment.LOOT_BONUS_MOBS;
		case "efficiency":
			return Enchantment.DIG_SPEED;
		case "silktouch":
			return Enchantment.SILK_TOUCH;
		case "unbreaking":
			return Enchantment.DURABILITY;
		case "fortune":
			return Enchantment.LOOT_BONUS_BLOCKS;
		case "power":
			return Enchantment.ARROW_DAMAGE;
		case "punch":
			return Enchantment.ARROW_KNOCKBACK;
		case "flame":
			return Enchantment.ARROW_FIRE;
		case "infinity":
			return Enchantment.ARROW_INFINITE;
		case "luckofthesea":
		case "luckofsea":
			return Enchantment.LUCK;
		case "lure":
			return Enchantment.LURE;
		default:
			throw new InvalidInputException("No enchantment was found for: " + name + ". Was everything spelled correctly?");
		}
	}
	
	private String arrayToString(Object[] array) {
		String string = array[0].toString();
		for (int i = 1; i < array.length; i++)
			string += ", " + array[i];
		
		return string;
	}
	
	public static ItemStack addGlow(ItemStack item){ 
		  net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		  NBTTagCompound tag = null;
		  if (!nmsStack.hasTag()) {
		      tag = new NBTTagCompound();
		      nmsStack.setTag(tag);
		  }
		  if (tag == null) tag = nmsStack.getTag();
		  NBTTagList ench = new NBTTagList();
		  tag.set("ench", ench);
		  nmsStack.setTag(tag);
		  return CraftItemStack.asCraftMirror(nmsStack);
		}
	
	private class InvalidInputException extends Exception {
		private static final long serialVersionUID = -7700986873885669523L;

		private String message;
		
		public InvalidInputException(String message) {
			super(message);
			this.message = message;
		}
		
		@Override
		public String getMessage() {
			return message;
		}
		
		@Override
		public String toString() {
			return message;
		}
	}
}