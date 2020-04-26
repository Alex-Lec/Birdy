package tools;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTools {
	public static Date getCurrentDate() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.HOUR, 2);
		return calendar.getTime();
	}
}
