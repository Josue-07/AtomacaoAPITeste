package br.ce.wcaquino.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;

public class DataUtils {

	public static String getDiferencaDias(Integer qtdDias) {
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, qtdDias);
		
		// formatando para string
		
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		return format.format(cal.getTime());
	}
}
