package tools;

import java.sql.Timestamp;
import java.util.Date;

public class DateTools {
	public static Timestamp getCurrentDate() {
		return new Timestamp(new Date().getTime());
	}
}
