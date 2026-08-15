package com.vectorprison.util;

import org.bukkit.ChatColor;

public class ProgressBarUtil {

	  public static String getProgressBar(int startoflevel, int current, int max, int totalBars, String symbol, String completedColor, String notCompletedColor){

	        
		  float amountIntoLevel = current - startoflevel;
		  
		  float amountNeeded = max - startoflevel;
		  
		    float percent = (float) amountIntoLevel / amountNeeded;

	        int progressBars = (int) ((int) totalBars * percent);

	        int leftOver = (totalBars - progressBars);

	        StringBuilder sb = new StringBuilder();
	        sb.append(ChatColor.translateAlternateColorCodes('&', completedColor));
	        for (int i = 0; i < progressBars; i++) {
	            sb.append(symbol);
	        }
	        sb.append(ChatColor.translateAlternateColorCodes('&', notCompletedColor));
	        for (int i = 0; i < leftOver; i++) {
	            sb.append(symbol);
	        }
	        return sb.toString();
	    }
	  
	  public static float getProgressPerecentage(int need, int current) {
		  
		    float percent = (need / current) * 100;
		    
			return percent;
	  }

}
