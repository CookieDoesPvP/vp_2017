# vp_2017

VectorPrison / MineParadise was a Minecraft server project I was the main developer for in 2017.

This repo contains a large amount of bespoke Java development work I produced in 2017 using the Spigot API for this project.

### Features** ###
-- Player Leveling & Progression System (hashmaps for short-term storage and writing to SQL database for long-term storage) 
-- Economy (replacing the basic Bukkit economy functionality) allowing for flexible intergration with other components.
-- Fully bespoke data storage. Using hashmaps for short-term storage (whilst players are logged on) and then writing to SQL database when they log off and intermittently.
-- Gangs (based on the popular "Factions" game mode but integrated with Prison - fully customized command set including private "gang" chat and data storage)
-- Guards (NPC path finding tracking the target player)
-- Custom in-game scoreboard handler
-- Custom in-game chat handler using the Fanciful library
-- YAML files to allow customization (i.e error messages, help text, economic parameters) 


** Note: This project was concluded in 2017 and updates to Minecraft (and subsequently Bukkit/Spigot) will have broken a lot of this code.
