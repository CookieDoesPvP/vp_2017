# vp_2017

VectorPrison / MineParadise was a Minecraft server project for which I was the Java developer.

## Project history
This project was originally developed in 2017, before I began using Git for version control. This repository was created retrospectively, so the Git history does not represent the project's original development timeline.

The repository contains a substantial amount of bespoke Java development work produced using the Spigot API, including custom progression, economy, data storage, gangs, NPC guards, scoreboards and chat systems.

## Features
* Player levelling and progression system
  * Custom progression system using HashMaps for short-term player data storage, with persistent data written to a SQL database.
* Custom economy system
  * Replaced the standard Bukkit economy functionality with a bespoke economy implementation designed to integrate with other server components.
* Persistent player data storage
  * Custom data handling using HashMaps while players were online, with data periodically persisted to SQL and written again when players disconnected.
* Gang system
  * A custom system inspired by the popular Factions game mode but designed specifically for the Prison environment. Included a bespoke command set, private gang chat and persistent gang data.
* Guard NPCs
  * NPC guards incorporating pathfinding behaviour to track and pursue target players.
* Custom scoreboard handler
  * Bespoke handling for in-game player scoreboards.
* Custom chat handler
  * Custom in-game chat formatting and functionality using the Fanciful library.
* YAML configuration
  * Configuration files allowing server behaviour and text to be customised, including error messages, help text and economy parameters.

## Technologies
- Java
- Spigot / Bukkit API
- SQL
- YAML
- Fanciful

## Compatibility 
**Note:** Development of this project concluded in 2017. Subsequent changes to Minecraft and the Bukkit/Spigot APIs mean that portions of the code are unlikely to function correctly on current server versions without modification.
