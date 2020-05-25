package tests;

import java.sql.Timestamp;

public class DateTest {
	public static void main(String args[]) {
		Timestamp currentTimestamp = tools.DateTools.getCurrentDate();
		Timestamp currentTimestampPlus10 = tools.DateTools.getCurrentDatePlusXMinutes(10);
		System.out.println(currentTimestamp);
		System.out.println(currentTimestampPlus10);
	}
}
