package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthentificationTools {
	public static JSONObject createSession(int id_user, String key_session) {
		String query = "INSERT INTO session VALUES ('" + id_user + "', '" + key_session + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + 1000, FALSE)";   
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			stmt.executeUpdate(query);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error createSession", 1000);
		}
		return tools.ErrorJSON.serviceAccepted();
	}
	
	public static JSONObject deleteSession(String key_session) {
		String query = "DELETE FROM session WHERE key_session = '" + key_session + "'";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			stmt.executeUpdate(query);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error deleteSession", 1000);
		}
		return tools.ErrorJSON.serviceAccepted();
	}
	
	public static JSONObject keyGenerator() {
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder key = new StringBuilder();
		for (int x = 0; x < 32; x++) {
			key.append(alphabet.charAt((int) (Math.floor(Math.random() * 62))));
		}
		try {
			return new JSONObject().put("key_session", key);
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error keyGenerator", 100);
		}
	}
	
	public static JSONObject isPasswordCorrect(String id_user, String password_user) {
		String query = "SELECT * FROM user WHERE id_user = '" + id_user + "' AND password_user = '" + password_user + "'";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement(); ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return new JSONObject().put("isPasswordCorrect", true);
			}
			return new JSONObject().put("isPasswordCorrect", false);
		} catch(SQLException | JSONException e) {
			if (e.getClass() == SQLException.class) {
				return tools.ErrorJSON.serviceRefused("SQL error isPasswordCorrect", 1000);
			}
			return tools.ErrorJSON.serviceRefused("JSON error isPasswordCorrect", 100);
		}
	}
	
	public static JSONObject isSessionActive(String key_session) {
		String query = "SELECT * FROM session WHERE key_session = '" + key_session + "'";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement(); ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return new JSONObject().put("isSessionActive", true);
			}
			return new JSONObject().put("isSessionActive", false);
		} catch(SQLException | JSONException e) {
			if (e.getClass() == SQLException.class) {
				return tools.ErrorJSON.serviceRefused("SQL error isSessionActive", 1000);
			}
			return tools.ErrorJSON.serviceRefused("JSON error isSessionActive", 100);
		}
	}
	
	public static JSONObject isUserConnected(String key_session, int id_user) {
		String query = "SELECT * FROM session WHERE key_session = '" + key_session + "' AND id_user = '" + id_user + "'";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement(); ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return new JSONObject().put("isUserConnected", true);
			}
			return new JSONObject().put("isUserConnected", false);
		} catch(SQLException | JSONException e) {
			if (e.getClass() == SQLException.class) {
				return tools.ErrorJSON.serviceRefused("SQL error isUserConnected", 1000);
			}
			return tools.ErrorJSON.serviceRefused("JSON error isUserConnected", 100);
		}
	}
}
