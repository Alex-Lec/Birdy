package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class DateTest {
	public static void main(String args[]) {
		String query = "SELECT * FROM session WHERE id_user = 19";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement(); ResultSet res = stmt.executeQuery(query)) {
			res.next();
			Timestamp time_out = res.getTimestamp(4);
			Timestamp current_date = tools.DateTools.getCurrentDate();
			System.out.println(current_date + " / " + time_out);
			System.out.println(time_out.getTime() - current_date.getTime());
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
