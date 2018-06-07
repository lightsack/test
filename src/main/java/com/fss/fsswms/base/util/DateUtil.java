package com.fss.fsswms.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public class DateUtil {

	private static ConcurrentHashMap<String, SimpleDateFormat> cache = new ConcurrentHashMap<String, SimpleDateFormat>();

	private static final String DEFAULT_FULL_PARSE_PATTERN = "yyyyMMddHHmmssSSS";
	private static final int DEFAULT_FULL_PARSE_MAXLENGTH = DEFAULT_FULL_PARSE_PATTERN.length();
	private static final String[] DEFAULT_PARSE_PATTERN = new String[DEFAULT_FULL_PARSE_MAXLENGTH + 1];
	static {
		DEFAULT_PARSE_PATTERN[DEFAULT_FULL_PARSE_MAXLENGTH] = DEFAULT_FULL_PARSE_PATTERN;
		DEFAULT_PARSE_PATTERN[14] = DEFAULT_FULL_PARSE_PATTERN.substring(0, 14);
		DEFAULT_PARSE_PATTERN[12] = DEFAULT_FULL_PARSE_PATTERN.substring(0, 12);
		DEFAULT_PARSE_PATTERN[10] = DEFAULT_FULL_PARSE_PATTERN.substring(0, 10);
		DEFAULT_PARSE_PATTERN[8] = DEFAULT_FULL_PARSE_PATTERN.substring(0, 8);
		DEFAULT_PARSE_PATTERN[6] = DEFAULT_FULL_PARSE_PATTERN.substring(0, 6);
		DEFAULT_PARSE_PATTERN[4] = DEFAULT_FULL_PARSE_PATTERN.substring(0, 4);
		DEFAULT_PARSE_PATTERN[0] = "";
	}
	private static final String DEFAULT_FORMAT_PATTERN = DEFAULT_PARSE_PATTERN[8];


	private DateUtil() {
	}

	private static SimpleDateFormat getDateFormat(String pattern) {
		SimpleDateFormat sdf = cache.get(pattern);
		if (sdf == null) {
			sdf = new SimpleDateFormat(pattern);
			cache.putIfAbsent(pattern, sdf);
		}
		return sdf;
	}

	public static Date parse(String source) throws ParseException {
		if(source == null) {
			return null;
		}
		int len = source.length();
		int rlen = len;
		if (len > DEFAULT_FULL_PARSE_MAXLENGTH) {
			rlen = DEFAULT_FULL_PARSE_MAXLENGTH;
		}
		while (DEFAULT_PARSE_PATTERN[rlen] == null) {
			rlen--;
		}
		return getDateFormat(DEFAULT_PARSE_PATTERN[rlen]).parse(source);
	}


	public static Date parse(String source, String pattern) throws ParseException {
		if (source == null) {
			return null;
		}
		return getDateFormat(pattern).parse(source);
	}

	public static String format(Date date) {
		if (date == null) {
			return null;
		}
		return getDateFormat(DEFAULT_FORMAT_PATTERN).format(date);
	}

	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return getDateFormat(pattern).format(date);
	}

	public static Date addDate(Date date, int amount) {
	    return add(date, Calendar.DATE, amount);
	}

	public static Date addMonth(Date date, int amount) {
	    return add(date, Calendar.MONTH, amount);
	}

	public static Date addYear(Date date, int amount) {
	    return add(date, Calendar.YEAR, amount);
	}

	public static Date add(Date date, int field, int amount) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(field, amount);
	    return cal.getTime();
    }

	public static Date addHour(Date date, int amount) {
	    return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	/**
	 * 시스템의 밀리초 구하기.(국제표준시각(UTC, GMT) 1970/1/1/0/0/0 으로부터 경과한 시각)
	 */
	public static long getCurrentMillis() {
		// 밀리초 단위(*1000은 1초), 음수이면 이전 시각
		long time = System.currentTimeMillis();
		// System.out.println(time);
		return time;
	}

	/**
	 * 현재 시각을 가져오기.
	 */
	public static Date getCurrentDate() {
		Date today = new Date();
		// System.out.println(today);
		return today;
	}

	/**
	 * 경과시간(초) 구하기
	 */
	public static double getElapstedTime() {
		long time1 = System.currentTimeMillis();
		long time2 = System.currentTimeMillis();
		// System.out.println((time2 - time1) / 1000.0);
		return ((time2 - time1) / 1000.0);
	}

	/**
	 * Date를 Calendar로 맵핑하기
	 */
	public static void getCalandar() {
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
	}

	/**
	 * 날짜(년/월/일) 구하기
	 *
	 * @return
	 */
	public static String getDateYYYYMMDD() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date();
		String dTime = formatter.format(currentTime);
		return dTime;
	}

	/**
	 * 날짜(년/월/일/시/분/초) 구하기
	 *
	 * @return
	 */
	public static String getDateYYYYMMDDHH24MISS() {
		// SimpleDateFormat formatter = new SimpleDateFormat (
		// "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

		// Format df = new SimpleDateFormat("yyyymmddhh24mmss");
		Date currentTime = new Date();
		// String dTime = df.format(currentTime);
		String dTime = formatter.format(currentTime);
		// System.out.println ( dTime );
		return dTime;
	}

	public static String getDateYYYYMMDDHH24MISS(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss",	Locale.KOREA);
		String dTime = "";
		try {
			dTime = ((Date)formatter.parse(strDate)).toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dTime;
	}

	public static String getDateYYYYMMDDHH24MIssSSS() {
		// SimpleDateFormat formatter = new SimpleDateFormat (
		// "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.KOREA);

		// Format df = new SimpleDateFormat("yyyymmddhh24mmss");
		Date currentTime = new Date();
		// String dTime = df.format(currentTime);
		String dTime = formatter.format(currentTime);
		// System.out.println ( dTime );
		return dTime;
	}

	/**
	 * 날짜(년/월/일/시/분/초) 구하기2
	 */
	/*
	public static void getDateYYYYMMDDHH24MISS_2() {
		GregorianCalendar today = new GregorianCalendar();
		int year = today.get(today.YEAR);
		int month = today.get(today.MONTH) + 1;
		int yoil = today.get(today.DAY_OF_MONTH);
		GregorianCalendar gc = new GregorianCalendar();
		System.out.println(gc.get(Calendar.YEAR));
		System.out.println(String.valueOf(gc.get(Calendar.MONTH) + 1));
		System.out.println(gc.get(Calendar.DATE));
		System.out.println(gc.get(today.DAY_OF_MONTH));
	}
	 */

	/**
	 * 날짜(년/월/일/시/분/초) 구하기3
	 */
	/*
	public static void getDateYYYYMMDDHH24MISS_3() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.KOREA);
		Calendar cal = Calendar.getInstance(Locale.KOREA);
		String date = df.format(cal.getTime());
	}
	*/

	public static void main(String[] args) {
		// System.out.println("today ==" + getDateYYYYMMDDHH24MISS());
	}
}