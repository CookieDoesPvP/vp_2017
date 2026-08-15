package com.vectorprison.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * Util from mrCookieSlime's plugin, QuickSell: http://dev.bukkit.org/bukkit-plugins/quicksell/
 */

public class Clock {
	public static Date getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		Date date = new Date();
		try {
			return format.parse(format.format(date));
		} catch (ParseException e) {}
		return null;
	}

	public static String getFormattedTime() {
		return new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(getCurrentDate());
	}

	public static String format(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(date);
	}

	public static Date getFutureDate(int days, int hours, int minutes) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentDate());
		calendar.add(5, days);
		calendar.add(10, hours);
		calendar.add(12, minutes);
		try {
			return format.parse(format.format(calendar.getTime()));
		} catch (ParseException e) {}
		return null;
	}
}