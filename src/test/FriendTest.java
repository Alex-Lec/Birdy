package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FriendTest {
	public static void main(String args[]) {
		String query = "SELECT * FROM friendship WHERE id_user_1 = '3' AND id_user_2 = '4'";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement(); ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				System.out.println("1");
			}
			System.out.println("0");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
