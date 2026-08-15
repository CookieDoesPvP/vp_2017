package com.vectorprison.bases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.Tag;

import com.vectorprison.VectorPrison;

public class Schematic
{
 
    private short[] blocks;
    private byte[] data;
    private short width;
    private short length;
    private short height;
    private String name;
 
    public Schematic(String name, short[] blocks, byte[] data, short width, short length, short height)
    {
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.length = length;
        this.height = height;
        this.name = name;
    }
 
    /**
    * @return the blocks
    */
    public short[] getBlocks()
    {
        return blocks;
    }
    
    public String getName() {
    	return name;
    }
 
    /**
    * @return the data
    */
    public byte[] getData()
    {
        return data;
    }
 
    /**
    * @return the width
    */
    public short getWidth()
    {
        return width;
    }
 
    /**
    * @return the length
    */
    public short getLength()
    {
        return length;
    }
 
    /**
    * @return the height
    */
    public short getHeight()
    {
        return height;
    }
    
	public static int round(int number, int multiple){
		int newNumber = number;
		
		while(newNumber % multiple != 0){
			newNumber += 1;
		}
		
		return newNumber;
	}
	
    @SuppressWarnings("deprecation")
	public Set<Location> pasteSchematic(Location loc) {
    	Set<Location> l = new HashSet<Location>();
    	final HashMap<Block, Short> tempblocks = new HashMap<Block, Short>();
    	
    	for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    Location location = new Location(loc.getWorld(), x + loc.getX() - 1, y + loc.getY(), z + loc.getZ() - 1);
                     location.getBlock().setTypeIdAndData(blocks[index], data[index], false); 
                  
            }
        }
        }
        return l;
    }
	
    @SuppressWarnings("resource")
   	public static Schematic loadSchematic(File file) throws IOException
       {
           FileInputStream stream = new FileInputStream(file);
           NBTInputStream nbtStream = new NBTInputStream(stream);
    
           CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
           if (!schematicTag.getName().equals("Schematic")) {
               throw new IllegalArgumentException("Tag \"Schematic\" does not exist or is not first");
           }
    
           Map<String, Tag> schematic = schematicTag.getValue();
           if (!schematic.containsKey("Blocks")) {
               throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");
           }
    
           short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
           short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
           short height = getChildTag(schematic, "Height", ShortTag.class).getValue();
           
           // Get blocks
           byte[] blockId = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
           byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
           byte[] addId = new byte[0];
           short[] blocks = new short[blockId.length]; // Have to later combine IDs
    
           //supports 4096 block IDs using the same method as vanilla Minecraft, where
           // the highest 4 bits are stored in a separate byte array.
           if (schematic.containsKey("AddBlocks")) {
               addId = getChildTag(schematic, "AddBlocks", ByteArrayTag.class).getValue();
           }

           for (int index = 0; index < blockId.length; index++) {
               if ((index >> 1) >= addId.length) {
                   blocks[index] = (short) (blockId[index] & 0xFF);
               } else {
                   if ((index & 1) == 0) {
                       blocks[index] = (short) (((addId[index >> 1] & 0x0F) << 8) + (blockId[index] & 0xFF));
                   } else {
                       blocks[index] = (short) (((addId[index >> 1] & 0xF0) << 4) + (blockId[index] & 0xFF));
                   }
               }
           }
    
           return new Schematic(file.getName().replace(".schematic", ""), blocks, blockData, width, length, height);
       }
    
       private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws IllegalArgumentException
       {
           if (!items.containsKey(key)) {
               throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
           }
           Tag tag = items.get(key);
           if (!expected.isInstance(tag)) {
               throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
           }
           return expected.cast(tag);
       }

}
