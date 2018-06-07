package com.fss.fsswms.base.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class FormatUtil {

	private static final String FRACTION_FULL_PATTERN_CHARS = "0000000000000000000";
	private static String [] cacheAmountPattern = new String [FRACTION_FULL_PATTERN_CHARS.length() + 1];
	public static final char DEFAULT_EXPRESSION_DATE_PATTERN_DELIM_CHAR = '-';
	public static final String DEFAULT_EXPRESSION_DATE_PATTERN = "yyyy" + DEFAULT_EXPRESSION_DATE_PATTERN_DELIM_CHAR + "MM" + DEFAULT_EXPRESSION_DATE_PATTERN_DELIM_CHAR + "dd";

	private FormatUtil() {
	}
	
	public static String now(String pattern) {
		return DateUtil.format(new Date(), pattern);
	}
	
	public static Date parseDate(String source) throws Exception {
		return DateUtil.parse(source);
	}
	
	public static Date parseDate(String source, String pattern) throws Exception {
		return DateUtil.parse(source, pattern);
	}
	
	public static String formatDate(Date date) {
		return DateUtil.format(date);
	}
	
	public static String formatDate(Date date, String pattern) {
		return DateUtil.format(date, pattern);
	}
	
	public static String today() {
		return now("yyyyMMdd");
	}
	
	public static String todayFmt() {
		return now("yyyy-MM-dd");
	}
	
	public static String formatAmount(String amount) {
		String trimmedAmount = StringUtil.trim(amount);
		if(StringUtil.isEmpty(trimmedAmount)) {
			return amount;
		}
		return formatAmount(NumberUtil.parse(trimmedAmount));
	}
	
	public static String formatAmount(Number amount) {
		if (amount instanceof BigDecimal) {
			return NumberUtil.format(amount, getInnerAmountPattern(Math.max(((BigDecimal) amount).scale(), 0)));
		} else if (amount instanceof Long || amount instanceof Integer || amount instanceof Short || amount instanceof Byte || amount instanceof BigInteger) {
			return NumberUtil.format(amount, getInnerAmountPattern(0));
		}
		return NumberUtil.format(amount, getInnerAmountPattern(Math.max(new BigDecimal(amount.toString()).scale(), 0)));
	}
	
	private static String getInnerAmountPattern(int fractionDigits) {
		if (fractionDigits < 0 || fractionDigits >= cacheAmountPattern.length) {
			throw new IllegalArgumentException(String.valueOf(fractionDigits));
		}
		String pattern = cacheAmountPattern[fractionDigits];
		if (pattern == null) {
			pattern = createAmountPattern(fractionDigits);
		}
		return pattern;
	}
	
	private synchronized static String createAmountPattern(int fractionDigits) {
		String pattern = cacheAmountPattern[fractionDigits];
		if (pattern == null) {
			if (fractionDigits > 0) {
				pattern = "#,##0." + FRACTION_FULL_PATTERN_CHARS.substring(0, fractionDigits);
			} else {
				pattern = "#,##0";
			}
			cacheAmountPattern[fractionDigits] = pattern;
		}
		return pattern;
	}
	
	public static boolean isValidEmailAddress(String email) {
		if(StringUtil.isEmpty(email)) {
			return false;
		}
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			return false;
		}
		return true;
	}
	
}
