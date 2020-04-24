package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONException;
import org.json.JSONObject;

public class UserTools {
	public static JSONObject createUser(String login_user, String password_user, String prenom_user, String nom_user, String mail_user) {
		try {
			Connection c = db.Database.getMySQLConnection();
			Statement stmt = c.createStatement();
			String query = "INSERT INTO user (login_user, password_user, prenom_user, nom_user, mail_user) VALUES ('" + login_user + "', '" + password_user + "', '" + prenom_user + "', '" + nom_user + "', '" + mail_user + "')";
			stmt.executeUpdate(query);
			stmt.close();
			c.close();
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("Impossible de créer l'utilisateur", 1000);
		}
		return tools.ErrorJSON.serviceAccepted();
	}
	/*
	public static JSONObject isUserExist(String login_user) {
		try {
			Connection c = db.Database.getMySQLConnection();
			Statement stmt = c.createStatement();
			String query = "SELECT login_user FROM user WHERE login_user = '" + login_user + "'";
			ResultSet res = stmt.executeQuery(query);
			if (res.next()) {
				res.close();
				stmt.close();
				c.close();
				return new JSONObject().put("isUserExist", true);
			}
			res.close();
			stmt.close();
			c.close();
			return new JSONObject ().put("isUserExist", false);
		} catch(SQLException |JSONException e) {
			if (e.getClass() == SQLException.class) {
				return tools.ErrorJSON.serviceRefused("Erreur de connexion à la base de données", 1000);
			}
			return tools.ErrorJSON.serviceRefused("JSON error isUserExist", 100);
		}
	}
	*/
	public static boolean isUserExist(String login_user) {
		try {
			Connection c = db.Database.getMySQLConnection();
			Statement stmt = c.createStatement();
			String query = "SELECT login_user FROM user WHERE login_user = '" + login_user + "'";
			ResultSet res = stmt.executeQuery(query);
			if (res.next()) {
				res.close();
				stmt.close();
				c.close();
				return true;
			}
			res.close();
			stmt.close();
			c.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}