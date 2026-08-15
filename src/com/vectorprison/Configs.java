package com.vectorprison;

import com.google.common.collect.Maps;
import com.vectorprison.Configs.ConfigType;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configs
{
  private static Map<ConfigType, File> configFiles = Maps.newHashMap();
  private static Map<ConfigType, FileConfiguration> configs = Maps.newHashMap();
  
  public static void loadConfigs()
  {
	VectorPrison plugin = VectorPrison.getInstance();
    if (!plugin.getDataFolder().exists()) {
      plugin.getDataFolder().mkdirs();
    }
    ConfigType[] arrayOfConfigType;
    int j = (arrayOfConfigType = ConfigType.values()).length;
    for (int i = 0; i < j; i++)
    {
      ConfigType type = arrayOfConfigType[i];
      loadConfig(type);
    }
  }
  
  private static FileConfiguration loadConfig(ConfigType type)
  {
	VectorPrison plugin = VectorPrison.getInstance();
    try
    {
      File f = new File(plugin.getDataFolder(), type.toString().toLowerCase() + ".yml");
      if (!f.exists()) {
        f.createNewFile();
      }
      FileConfiguration c = YamlConfiguration.loadConfiguration(f);
      configs.put(type, c);
      configFiles.put(type, f);
      return c;
    }
    catch (IOException e)
    {
      plugin.getLogger().log(Level.SEVERE, "Error on reading config: " + type.toString(), e);
    }
    return null;
  }
  
  public static void saveConfig(ConfigType type)
  {
    if ((configs.get(type) == null) || (configFiles.get(type) == null)) {
      return;
    }
    try
    {
      ((FileConfiguration)configs.get(type)).save((File)configFiles.get(type));
    }
    catch (IOException e)
    {
      VectorPrison.getInstance().getLogger().log(Level.SEVERE, "Could not save config: " + type.toString(), e);
    }
  }
  
  public static Configuration getConfig(ConfigType type)
  {
    return (Configuration)configs.get(type);
  }
  
  public static enum ConfigType
  {
    LEVELS;
  }
}
