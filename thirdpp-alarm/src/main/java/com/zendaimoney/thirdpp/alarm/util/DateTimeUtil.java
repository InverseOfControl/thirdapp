package com.zendaimoney.thirdpp.alarm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateTimeUtil {

	public static Log logger = LogFactory.getLog(DateTimeUtil.class);
	
	public static Date getStartTime(String startTime) {
		startTime = startTime + " 00:00:01";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sf.parse(startTime);
		} catch (ParseException e) {
			logger.error(e);
		}
		return date;
	}

	public static Date getEndtTime(String endTime) {
		endTime = endTime + " 23:59:59";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sf.parse(endTime);
		} catch (ParseException e) {
			logger.error(e);
		}
		return date;
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static int getCurrentDateNumber() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		int currentDateNumber = 0;
		currentDateNumber = Integer.parseInt(sf.format(date));
		return currentDateNumber;
	}

	public static Date stringToDate(String str, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date date = null;
		try {
			if(str == null ||str.isEmpty()){
				return null;
			}
			date = sf.parse(str);
		} catch (ParseException e) {
			logger.error(e);
			return null;
		}
		return date;
	}

	public static String dateToString(Date date, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		String str = null;
		try {
			if(date == null){
				return null;
			}
			str = sf.format(date);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return str;
	}

	public static String getCurrentDateString() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = null;
		try {
			str = sf.format(new Date());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return str;
	}
	
	public static String getCurrentDateString(String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		String str = null;
		try {
			str = sf.format(new Date());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return str;
	}

	public static String getCurrentDateBefore(int minutes) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = null;
		Date date = new Date();
		Calendar now = Calendar.getInstance();
		try {
			now.setTime(date);
			now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - minutes);
			str = sf.format(now.getTime());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return str;
	}

	/**
	 * 
	 * @param timePoint
	 * @return
	 */
	public static long getSleepTime(int timePoint) {
		long sleepTime = 0;
		// 当前时间.
		Calendar curCal = Calendar.getInstance();
		// 运行时间.
		Calendar runCal = Calendar.getInstance();
		// 如果目前的时间点小于设定时间点.
		if (curCal.get(Calendar.HOUR_OF_DAY) < timePoint) {
			runCal.set(Calendar.HOUR_OF_DAY, timePoint);
			runCal.set(Calendar.MINUTE, 0);
			runCal.set(Calendar.SECOND, 0);
			sleepTime = runCal.getTimeInMillis() - curCal.getTimeInMillis();
			// 如果目前的时间点大于设定的时间点,就按照明天的这个时间点进行计算.
		} else {
			runCal.set(Calendar.DAY_OF_MONTH,
					runCal.get(Calendar.DAY_OF_MONTH) + 1);
			runCal.set(Calendar.HOUR_OF_DAY, timePoint);
			runCal.set(Calendar.MINUTE, 0);
			runCal.set(Calendar.SECOND, 0);
			sleepTime = runCal.getTimeInMillis() - curCal.getTimeInMillis();
		}
		return sleepTime;
	}
}
