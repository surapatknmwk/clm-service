package com.ktb.clmapiauthen.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.sql.Timestamp;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DateUtil {
	public static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_PATTERN = "dd/MM/yyyy";
	public static final String DATE_PATTERNYEAR = "yyyy/MM/dd";
	public static final String DATE_PATTERN2 = "dd-MM-yyyy";
	public static final String FMT_DB_YYYYMMDD = "yyyyMMdd";
	public static final String FMT_VIEW_TIMESTAMP = "HH:mm:ss";
	public static final String FMT_DB_DDMMYYYY = "ddMMyyyy";
	public static final String FMT_DATE_YYYY = "yyyy";
	public static final String DATE_TIME_PATTERN2 = "yyyy-MM-dd HH:mm:ss";

	public static final Locale LOCALE_TH = new Locale("th", "TH");
	public static final Locale LOCALE_EN = new Locale("en", "EN");
	public static final String TIME_ZONE = "GMT+07:00";

	public static Timestamp getCurrentDate() {
        Timestamp today = null;
        try {
            Date nowDate = Calendar.getInstance().getTime();
            today = new Timestamp(nowDate.getTime());
        } catch (Exception e) {
            log.error("error msg : {} ", e);
            throw new RuntimeException(e);
        }
        return today;
    }

	public static String dateToString(Date date, String pattern, Locale locale) {
		String dateTime = null;

		try {
			if (date != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, locale);
				dateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
				dateTime = dateFormat.format(date);
			}
		} catch (Exception e) {
		}

		return dateTime;
	}

	public static String dateToDateTime(Date date, String pattern, Locale locale) {
		String dateTime = null;

		try {
			if (date != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, locale);
				dateTime = dateFormat.format(date);
			}
		} catch (Exception e) {
		}

		return dateTime;
	}

	public static String dateToString(Date date) {
		String dateTime = null;

		try {
			if (date != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN, LOCALE_EN);
				dateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
				dateTime = dateFormat.format(date);
			}
		} catch (Exception e) {
		}

		return dateTime;
	}

	public static Date stringToDate(String s_date, String format, Locale locale) {

		Date date = new Date();
		try {
			SimpleDateFormat df = new SimpleDateFormat(format, locale);
			date = df.parse(s_date);
		} catch (Exception e) {
		}

		return date;

	}

	public static Date stringBEToDate(String s_date) {

		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN, LOCALE_TH);
			date = df.parse(s_date);
		} catch (Exception e) {
		}

		return date;

	}

	public static Date stringCEToDate(String s_date) {

		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN, LOCALE_EN);
			date = df.parse(s_date);
		} catch (Exception e) {
		}

		return date;

	}

	public static Date stringBEToDate2(String s_date) {

		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN2, LOCALE_TH);
			date = df.parse(s_date);
		} catch (Exception e) {
		}

		return date;

	}

	public static String convertCE_to_BE(String date, String pattern1, String pattern2) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern1, LOCALE_EN);
			Date dt = df.parse(date);

			df = new SimpleDateFormat(pattern2, LOCALE_TH);
			String str = df.format(dt);
			return str;
		} catch (Exception e) {
			return "";
		}
	}

	public static String strToDateTh2(String dateStr) {
		if (dateStr == null || dateStr.equals("")) {
			return "";
		} else {
			String dateTrim = dateStr.trim();
			String yy = dateTrim.substring(0, 4);
			String mm = dateTrim.substring(4, 6);
			String dd = dateTrim.substring(6, 8);
			int ddd = Integer.parseInt(dd);
			int mmm = Integer.parseInt(mm);
			int yyy = Integer.parseInt(yy) + 543;
			String yyyy = new Integer(yyy).toString();
			dateStr = dd + "/" + mm + "/" + yyyy;
			return dateStr;
		}
	}

	public static String strToDate(String dateStr) {
		if (dateStr == null || dateStr.equals("")) {
			return "";
		} else {
			String dateTrim = dateStr.trim();
			String yy = dateTrim.substring(0, 4);
			String mm = dateTrim.substring(4, 6);
			String dd = dateTrim.substring(6, 8);
			int ddd = Integer.parseInt(dd);
			int mmm = Integer.parseInt(mm);
			int yyy = Integer.parseInt(yy);
			String yyyy = new Integer(yyy).toString();
			dateStr = dd + "/" + mm + "/" + yyyy;
			return dateStr;
		}
	}

	public static String beDateToCEStringDate(String sBEDate, String beFormat, String ceFormat) {
		String sCEDate = "";
		if (sBEDate != null && !sBEDate.equalsIgnoreCase("")) {
			Date beDate = new Date();
			beDate = stringToDate(sBEDate, beFormat, new Locale("th", "TH"));
			sCEDate = dateToString(beDate, ceFormat, new Locale("en", "US"));
		}
		return sCEDate;
	}

	public static String beDateToBEStringDate(String sBEDate, String beFormat, String ceFormat) {
		String sCEDate = "";
		if (sBEDate != null && !sBEDate.equalsIgnoreCase("")) {
			if (isValidDate(sBEDate, beFormat)) {
				Date beDate = new Date();
				beDate = stringToDate(sBEDate, beFormat, new Locale("en", "US"));
				sCEDate = dateToString(beDate, ceFormat, new Locale("th", "TH"));
			}
		}
		return sCEDate;
	}

	public static String calAgeCBS(String DOB) {
		int resp = 0;
		try {
			String dateTrim = DOB.trim();
			String yy = dateTrim.substring(0, 4);
			String mm = dateTrim.substring(4, 6);
			String dd = dateTrim.substring(6, 8);
			int ddd = Integer.parseInt(dd);
			int mmm = Integer.parseInt(mm);
			int yyy = Integer.parseInt(yy);

			// direct age calculation
			LocalDate l = LocalDate.of(yyy, 01, 01); // specify year, month, date directly
			LocalDate now = LocalDate.now(); // gets localDate
			Period diff = Period.between(l, now); // difference between the dates is calculated
			System.out.println(diff.getYears() + "year");
			resp = diff.getYears();
		} catch (Exception e) {

		} finally {
			return String.valueOf(resp);
		}
	}

	public static String calAgeCBSFromDB(String DOB) {
		int resp = 0;
		try {
			String dateTrim = DOB.trim();
			String yy = dateTrim.substring(0, 2);
			String mm = dateTrim.substring(2, 4);
			String dd = dateTrim.substring(4, 8);
			int ddd = Integer.parseInt(dd);
			int mmm = Integer.parseInt(mm);
			int yyy = Integer.parseInt(yy);
			System.out.println("yy" + yy);
			System.out.println("mm" + mm);
			System.out.println("dd" + dd);
			// direct age calculation
			LocalDate l = LocalDate.of(yyy, 01, 01); // specify year, month, date directly
			LocalDate now = LocalDate.now(); // gets localDate
			Period diff = Period.between(l, now); // difference between the dates is calculated
			System.out.println(diff.getYears() + "year");
			resp = diff.getYears();
		} catch (Exception e) {

		} finally {
			return String.valueOf(resp);
		}
	}

	public static boolean isValidDate(String dateStr, String format) {
		DateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		try {
			sdf.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public static String strToDateCE(String dateStr) {
		if (dateStr == null || dateStr.equals("")) {
			return "";
		} else {
			dateStr = dateStr.replaceAll("/", "");
			String dateTrim = dateStr.trim();
			String yy = dateTrim.substring(4, 8);
			String mm = dateTrim.substring(2, 4);
			String dd = dateTrim.substring(0, 2);
			int ddd = Integer.parseInt(dd);
			int mmm = Integer.parseInt(mm);
			int yyy = Integer.parseInt(yy);
			String yyyy = new Integer(yyy - 543).toString();
			dateStr = dd + "/" + mm + "/" + yyyy;
			return dateStr;
		}
	}

	public static String getAddYear(int yearAdd) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, yearAdd); // to get previous year add -1
		Date changeYear = cal.getTime();
		SimpleDateFormat df = new SimpleDateFormat(FMT_DATE_YYYY, LOCALE_TH);
		return df.format(changeYear);
	}

}
