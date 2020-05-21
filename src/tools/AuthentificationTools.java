package tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthentificationTools {
	public static JSONObject createSession(int id_user, String key_session, Statement stmt) throws SQLException, JSONException {
		String query = "INSERT INTO session VALUES ('" + id_user + "', '" + key_session + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + 1000, FALSE)";   
		if (stmt.executeUpdate(query) == 1) {
			return tools.ErrorJSON.serviceAccepted();
		}
		return tools.ErrorJSON.serviceRefused("SQL error createSession", 1000);
	}
	
	public static JSONObject deleteSession(String key_session, Statement stmt) throws SQLException, JSONException {
		String query = "DELETE FROM session WHERE key_session = '" + key_session + "'";
		if (stmt.executeUpdate(query) == 1) {
			return tools.ErrorJSON.serviceAccepted();
		}
		return tools.ErrorJSON.serviceRefused("SQL error deleteSession", 1000);
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
	
	public static JSONObject isPasswordCorrect(String id_user, String password_user, Statement stmt) throws SQLException, JSONException {
		String query = "SELECT * FROM user WHERE id_user = '" + id_user + "' AND password_user = '" + password_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return new JSONObject().put("isPasswordCorrect", true);
			}
		}
		return new JSONObject().put("isPasswordCorrect", false);
	}
		
	public static JSONObject isSessionActive(String key_session, Statement stmt) throws SQLException, JSONException {
		String query = "SELECT * FROM session WHERE key_session = '" + key_session + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				Date time_out = res.getTimestamp("date_fin_session");
				if (time_out.getTime() - tools.DateTools.getCurrentDate().getTime() > 0) {
					return new JSONObject().put("isSessionActive", true);
				}
				tools.AuthentificationTools.deleteSession(key_session, stmt);
			}
		}
		return new JSONObject().put("isSessionActive", false);
	}
	
	public static JSONObject isUserConnected(int id_user, Statement stmt) throws SQLException, JSONException {
		String query = "SELECT * FROM session WHERE id_user = '" + id_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next() && isSessionActive(res.getString("key_session"), stmt).getBoolean("isSessionActive")) {
				return new JSONObject().put("isUserConnected", true);
			}
		}
		return new JSONObject().put("isUserConnected", false);
	}
	
	public static JSONObject getKeyUserSession(int id_user, Statement stmt) throws SQLException, JSONException {
		String query = "SELECT * FROM session WHERE = '" + id_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return new JSONObject().put("key_session", res.getString("key_session"));
			}
		}
		return tools.ErrorJSON.serviceRefused("SQL error getKeyUserSession", 1000);
	}
}
