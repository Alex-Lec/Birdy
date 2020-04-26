package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONException;
import org.json.JSONObject;

public class UserTools {
	public static JSONObject createUser(String login_user, String password_user, String prenom_user, String nom_user, String mail_user) {
		String query = "INSERT INTO user (login_user, password_user, prenom_user, nom_user, mail_user) VALUES ('" + login_user + "', '" + password_user + "', '" + prenom_user + "', '" + nom_user + "', '" + mail_user + "')";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) { 
			stmt.executeUpdate(query);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error createUser", 1000);
		}
		return tools.ErrorJSON.serviceAccepted();
	}
	
	public static JSONObject deleteUser(int id_user) {
		String query = "DELETE FROM user WHERE id_user = '" + id_user + "'";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) { 
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error deleteUser", 1000);
		}
		return tools.ErrorJSON.serviceAccepted();
	}

	public static JSONObject isUserExist(String login_user) {
		String query = "SELECT login_user FROM user WHERE login_user = '" + login_user + "'";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement(); ResultSet res = stmt.executeQuery(query)) { 
			if (res.next()) {
				return new JSONObject().put("isUserExist", true);
			}
			return new JSONObject ().put("isUserExist", false);
		} catch(SQLException |JSONException e) {
			if (e.getClass() == SQLException.class) {
				return tools.ErrorJSON.serviceRefused("Erreur de connexion � la base de donn�es", 1000);
			}
			return tools.ErrorJSON.serviceRefused("JSON error isUserExist", 100);
		}
	}
	
	public static JSONObject isUserExist(int id_user) {
		String query = "SELECT id_user FROM user WHERE id_user = '" + id_user + "'";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement(); ResultSet res = stmt.executeQuery(query)) { 
			if (res.next()) {
				return new JSONObject().put("isUserExist", true);
			}
			return new JSONObject ().put("isUserExist", false);
		} catch(SQLException |JSONException e) {
			if (e.getClass() == SQLException.class) {
				return tools.ErrorJSON.serviceRefused("Erreur de connexion � la base de donn�es", 1000);
			}
			return tools.ErrorJSON.serviceRefused("JSON error isUserExist", 100);
		}
	}
	
	public static JSONObject getId(String login_user) {
		String query = "SELECT * FROM user WHERE login_user = '" + login_user + "'";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement(); ResultSet res = stmt.executeQuery(query)) { 
			res.next();
			return new JSONObject().put("id_user", res.getInt(1));
		} catch(SQLException | JSONException e) {
			if (e.getClass() == SQLException.class) {
				return tools.ErrorJSON.serviceRefused("SQL error getId", 1000);
			}
			return tools.ErrorJSON.serviceRefused("JSON error getId", 100);
		}
	}
}