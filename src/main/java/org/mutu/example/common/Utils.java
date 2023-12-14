package org.mutu.example.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mutu.example.zconfig.MessageCode;
import org.mutu.example.zconfig.exception.BusinessLogicException;

public class Utils {
	public static Date getDate(String value) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		try {
			return format.parse(value);
		} catch (ParseException e) {
			throw new BusinessLogicException(MessageCode.UNEXPECTED_ERROR, "Invalid Date Format");
		}
	}
}
