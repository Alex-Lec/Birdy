package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.Database;

public class DBTest {
	public static void main(String args[]) {
		try {
			Connection c = Database.getMySQLConnection();
			Statement stmt = c.createStatement();
			String query = "SELECT * FROM user";
			ResultSet res = stmt.executeQuery(query);
			System.out.println(res.toString());
			res.close();
			stmt.close();
			c.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}