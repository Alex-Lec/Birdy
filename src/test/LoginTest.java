package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONException;
import db.Database;

public class LoginTest {
	public static void main(String args[]) {
		String insert_session = "INSERT INTO session VALUES ('2', '11111111111111111111111111111111', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + 600, FALSE)";
		try (Connection c = Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			int getId = tools.UserTools.getId("Alex").getInt("id_user");
			System.out.println(getId);
			int res = stmt.executeUpdate(insert_session);
			System.out.println(res);
		} catch(SQLException | JSONException e) {
			e.printStackTrace();
		}
	}
}