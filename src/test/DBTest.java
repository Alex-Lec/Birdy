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
			String query = "INSERT INTO user (login_user, password_user, prenom_user, nom_user, mail_user) VALUES ('Alex', '1234', 'Alexis', 'LECUYER', 'alexislecuyer14@gmail.com')";
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