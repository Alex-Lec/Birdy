package tools;

import java.sql.Timestamp;
import java.util.Date;

public class DateTools {
	public static Timestamp getCurrentDate() {
		return new Timestamp(new Date().getTime());
	}
	
	public static Timestamp getCurrentDatePlusXMinutes(int x) {
		Timestamp timestamp = getCurrentDate();
		timestamp.setTime(timestamp.getTime() + (x * 60 * 1000));
		return timestamp;
	}
}
