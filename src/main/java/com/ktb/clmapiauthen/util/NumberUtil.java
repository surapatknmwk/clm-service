package com.ktb.clmapiauthen.util;

import java.text.DecimalFormat;

public class NumberUtil {
	private final static String NUMBER_NORMAL = "#,###,###,##0";

	public static String setStrFormat(Object str, int digit) {
		try {
			if (str != null && !StringUtil.isBlankValue(str.toString())) {
				String val = (str.toString().trim()).replace(",", "");

				DecimalFormat df = getFormat(digit);
				return df.format(Double.valueOf(val));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	private static DecimalFormat getFormat(int digit) {
		DecimalFormat df = new DecimalFormat(NUMBER_NORMAL);

		if (digit > 0) {
			String temp = NUMBER_NORMAL + ".";
			for (int i = 0; i < digit; i++)
				temp += "0";
			
			return new DecimalFormat(temp);
		} else if (digit == 0){
			return df;
		}

		return new DecimalFormat(NUMBER_NORMAL + ".##");
	}
}
