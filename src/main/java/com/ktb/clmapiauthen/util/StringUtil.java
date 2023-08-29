package com.ktb.clmapiauthen.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

public class StringUtil {

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty() || str.equalsIgnoreCase("null");
	}

	public static Long strEmptyToLong(String str) {
		Long n = Long.valueOf(0);
		if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
			n = Long.valueOf(0);
		} else {
			n = Long.valueOf(str);
		}
		return n;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String val(String s) {
		return s == null || s.isEmpty() ? "" : s.trim();
	}

	public static String webString(String str) {
		return isNotEmpty(str) && !"undefined".equals(str) ? str : null;
	}

	public static boolean isBlankValue(Object object) {
		if (object == null) {
			return true;
		}

		if (object instanceof String) {
			String objString = (String) object;

			if (objString == null || objString.length() == 0 || "".equals(objString.trim())) {
				return true;
			} else {
				if ("?".equalsIgnoreCase(objString.trim()))
					return true;
			}
		}

		return false;
	}

	public static String substr(String str, int maxLength) {
		if (isEmpty(str)) {
			return "";
		}

		if (str.length() < maxLength) {
			return str;
		}

		return str.substring(0, maxLength);
	}

	public static String[] splitData(String data, String delimiter) {
		String[] dataSplit = null;
		String[] xxx = new String[1];

		try {
			if (!StringUtil.isBlankValue(data)) {
				if (data.indexOf(delimiter) > -1) {
					dataSplit = data.split("\\" + delimiter);
				} else {
					xxx[0] = data;
					dataSplit = xxx;
				}
			}

			return dataSplit;
		} catch (Exception e) {
			return null;
		} finally {
			xxx = null;
		}
	}

	public static String getPropertyAsString(Properties prop) {
		StringWriter writer = new StringWriter();
		prop.list(new PrintWriter(writer));
		return writer.getBuffer().toString();
	}

	public static String escapeXml(String s) {
		if (isNotEmpty(s)) {
			return val(s).replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;")
					.replaceAll("\"", "&quot;").replaceAll("'", "&apos;");
		}
		return s;
	}

	public static void removeLastString(StringBuilder sb) {
		try {
			if (sb.length() > 0) {
				sb.setLength(sb.length() - 1);
			}
		} catch (Exception e) {
		}
	}

	public static String checkNullString(String field) {
		if (field == null || field.equals("")) {
			return "";
		}
		return field;
	}

	public static String checkNullStringVal(String field) {
		if (field == "null" || field.equals("")) {
			return "";
		}
		return field;
	}

	public static Double checkNullDoubleVal(Double field) {
		if (field == null || field.equals("")) {
			return 0.00;
		}
		return field;
	}

	public static String checkNullLong(Long field) {
		if (field == null) {
			return "";
		}
		return field.toString();
	}

	public static String checkNullDouble(Double field) {
		if (field == null) {
			return "";
		}
		return field.toString();
	}

	public static String unescapeXml(String field) {
		if (field == null || field.equals("")) {
			return "";
		}

		return StringEscapeUtils.unescapeXml(StringEscapeUtils.escapeSql(field));
	}

	public static String getInputValue(String defineValue, String value) {
		String tmpInputValue = "";
		tmpInputValue = "INPUT_VALUE_" + value + " " + defineValue + "Price";
		return tmpInputValue;
	}

	public static String convertLongToString(Long str) {
		String resp = isBlankValue(str) ? "" : str.toString();
		return resp;
	}

	public static String convertStringToString(String str) {
		String resp = isBlankValue(str) ? "" : str.toString();
		return resp;
	}

	public static Long String2Long(String numberAsString) {
		Long number = null;
		if (numberAsString == null) {
			return null;
		}
		DecimalFormat decimalFormat = new DecimalFormat("#");
		try {
			number = decimalFormat.parse(numberAsString).longValue();
			System.out.println("The number : " + number);
		} catch (ParseException e) {
			System.out.println(numberAsString + " is not a valid number.");
		}

		return number;
	}

	public static Long Cell2Long(Cell numberAsString) {
		Long number = null;
		if (numberAsString == null) {
			return null;
		}
		DecimalFormat decimalFormat = new DecimalFormat("#");
		try {
			number = decimalFormat.parse(String.valueOf(numberAsString)).longValue();
			System.out.println("The number is [" + numberAsString.getColumnIndex() + "] : " + number);
		} catch (ParseException e) {
			System.out.println(numberAsString + " is not a valid number.");
		}

		return number;
	}

	public static String Cell2String(Cell numberAsString) {
		String number = null;
		if (numberAsString == null) {
			return null;
		}
		try {
			DataFormatter formatter = new DataFormatter ();
			number = formatter.formatCellValue(numberAsString);

			System.out.println("The number is [" + numberAsString.getColumnIndex() + "] : " + number);
		} catch (Exception e) {
			e.printStackTrace ();
			System.out.println(numberAsString + " is not a valid number.");
			return null;
		}

		return number;
	}

	public static Double Cell2Double(Cell numberAsString) {
		Double number = null;
		if (numberAsString == null) {
			return null;
		}
		try {
			number = Double.valueOf(String.valueOf(numberAsString).trim());
			System.out.println("The number is [" + numberAsString.getColumnIndex() + "] : " + number);
		} catch (Exception e) {
			System.out.println(numberAsString + " is not a valid number.");
		}

		return number;
	}

	public static Date Cell2Date(Cell dateAsString) {
		Date date = null;
		if (dateAsString == null ) {
			return null;
		}
		try {
			SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MMM-yyyy");
			date = formatterDate.parse(String.valueOf(dateAsString).trim());
			System.out.println("The date is: " + date);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(dateAsString + " is not a valid date.");
			return null;
		}

		return date;
	}

	public static Date Cell2Date(Cell dateAsString,String format) {
		Date date = null;
		if (dateAsString == null ) {
			return null;
		}
		try {
			SimpleDateFormat formatterDate = new SimpleDateFormat(format);
			date = formatterDate.parse(String.valueOf(dateAsString).trim());
			System.out.println("The date is: " + date);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(dateAsString + " is not a valid date.");
			return null;
		}

		return date;
	}

	public static String tranformJson(Object obj) {
		String json = null;
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return json;
		}

	}

	public static String tranformJsonWithoutRootName(Object obj) {
		String json = null;
		try {
			ObjectWriter ow = new ObjectMapper().writer().withoutRootName();
			json = ow.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return json;
		}

	}

	public static Long strNullToLong(String str) {
		Long n = Long.valueOf(0);
		if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
			n = null;
		} else {
			n = Long.valueOf(str);
		}
		return n;
	}

	public static Long Cell2Long(Cell numberAsString , Integer len) {
		Long number = null;
		if (numberAsString == null) {
			return null;
		}
		DecimalFormat decimalFormat = new DecimalFormat("#");
		try {
			number = decimalFormat.parse(String.valueOf(numberAsString)).longValue();
			System.out.println("The number is [" + numberAsString.getColumnIndex() + "] : " + number);

			if(String.valueOf(number).length() <= len) {

			}

		} catch (ParseException e) {
			System.out.println(numberAsString + " is not a valid number.");
		}

		return number;
	}

}
