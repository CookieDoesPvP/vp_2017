package com.vectorprison.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class SBManager
{
  private Scoreboard scoreboard;
  private String title;
  private Map<String, Integer> scores;
  private List<Team> teams;
  
  public SBManager(String title)
  {
    this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    this.title = title;
    this.scores = Maps.newLinkedHashMap();
    this.teams = Lists.newArrayList();
  }
  
  public void blankLine()
  {
    add("§r ");
  }
  
  public void add(String text)
  {
    add(text, null);
  }
  
  public void add(String text, Integer score)
  {
    Preconditions.checkArgument(text.length() < 48, "text cannot be over 48 characters in length");
    text = fixDuplicates(text);
    this.scores.put(text, score);
  }
  
  private String fixDuplicates(String text)
  {
    while (this.scores.containsKey(text)) {
      text = text + "§r";
    }
    if (text.length() > 48) {
      text = text.substring(0, 47);
    }
    return text;
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
private Map.Entry<Team, String> createTeam(String text)
  {
    String result = "";
    if (text.length() <= 16) {
      return new AbstractMap.SimpleEntry(null, text);
    }
    Team team = this.scoreboard.registerNewTeam("text-" + this.scoreboard.getTeams().size());
    Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();
    team.setPrefix((String)iterator.next());
    result = (String)iterator.next();
    if (text.length() > 32) {
      team.setSuffix((String)iterator.next());
    }
    this.teams.add(team);
    return new AbstractMap.SimpleEntry(team, result);
  }
  
  public void build()
  {
    Objective obj = this.scoreboard.registerNewObjective(this.title.length() > 16 ? this.title.substring(0, 15) : this.title, "dummy");
    obj.setDisplayName(this.title);
    obj.setDisplaySlot(DisplaySlot.SIDEBAR);
    
    int index = this.scores.size();
    for (Map.Entry<String, Integer> text : this.scores.entrySet())
    {
      Map.Entry<Team, String> team = createTeam((String)text.getKey());
      Integer score = Integer.valueOf(text.getValue() != null ? ((Integer)text.getValue()).intValue() : index);
      
      String pla = (String)team.getValue();
      if (team.getKey() != null) {
        ((Team)team.getKey()).addEntry(pla);
      }
      obj.getScore(pla).setScore(score.intValue());
      index--;
    }
  }
  
  public void reset()
  {
    this.title = null;
    this.scores.clear();
    for (Team t : this.teams) {
      t.unregister();
    }
    this.teams.clear();
  }
  
  public Scoreboard getScoreboard()
  {
    return this.scoreboard;
  }
  
  public void send(Player... players)
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = players).length;
    for (int i = 0; i < j; i++)
    {
      Player p = arrayOfPlayer[i];
      p.setScoreboard(this.scoreboard);
    }
  }
}
