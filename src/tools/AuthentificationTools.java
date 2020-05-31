package tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class AuthentificationTools {
	public static boolean createSession(int id_user, String key_session, Statement stmt) throws SQLException {
		String query = "INSERT INTO session VALUES ('" + id_user + "', '" + key_session + "', '" + tools.DateTools.getCurrentDate() + "', '" + tools.DateTools.getCurrentDatePlusXMinutes(10) + "', FALSE)";   
		if (stmt.executeUpdate(query) == 1) {
			return true;
		}
		return false;
	}
	
	public static boolean deleteSession(String key_session, Statement stmt) throws SQLException {
		String query = "DELETE FROM session WHERE key_session = '" + key_session + "'";
		if (stmt.executeUpdate(query) == 1) {
			return true;
		}
		return false;
	}
	
	public static String keyGenerator() {
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder key = new StringBuilder();
		for (int x = 0; x < 32; x++) {
			key.append(alphabet.charAt((int) (Math.floor(Math.random() * 62))));
		}
		return key.toString();
	}
	
	public static boolean isPasswordCorrect(String id_user, String password_user, Statement stmt) throws SQLException {
		String query = "SELECT * FROM user WHERE id_user = '" + id_user + "' AND password_user = '" + password_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return true;
			}
		}
		return false;
	}
		
	public static boolean isSessionActive(String key_session, Statement stmt) throws SQLException {
		String query = "SELECT * FROM session WHERE key_session = '" + key_session + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				if (res.getInt("root_session") == 1) {
					return true;
				}
				Date time_out = res.getTimestamp("date_fin_session");
				if (time_out.getTime() - tools.DateTools.getCurrentDate().getTime() > 0) {
					updateSessionTimeOut(key_session, stmt);
					return true;
				}
				tools.AuthentificationTools.deleteSession(key_session, stmt);
			}
		}
		return false;
	}
	
	public static boolean updateSessionTimeOut(String key_session, Statement stmt) throws SQLException {
		String query = "UPDATE session SET date_fin_session = '" + tools.DateTools.getCurrentDatePlusXMinutes(10) + "' WHERE key_session = '" + key_session + "'";
		if (stmt.executeUpdate(query) == 1) {
			return true;
		}
		return false;
	}
	
	public static boolean isUserConnected(int id_user, Statement stmt) throws SQLException {
		String query = "SELECT * FROM session WHERE id_user = '" + id_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next() && isSessionActive(res.getString("key_session"), stmt)) {
				return true;
			}
		}
		return false;
	}
	
	public static int getUserId(String key_session, Statement stmt) throws SQLException {
		String query = "SELECT * FROM session WHERE key_session = '" + key_session + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return res.getInt("id_user");
			}
		}
		return -1;
	}
	
	public static String getKeyUserSession(int id_user, Statement stmt) throws SQLException {
		String query = "SELECT * FROM session WHERE id_user = '" + id_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return res.getString("key_session");
			}
		}
		return "";
	}
}
