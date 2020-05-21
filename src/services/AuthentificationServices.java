package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthentificationServices {
	public static JSONObject login(String login_user, String password_user) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (login_user == null || password_user == null) {
				return tools.ErrorJSON.serviceRefused("Arguments error login", -1);
			}
			int id_user = tools.UserTools.getId(login_user, stmt).getInt("id_user");
			String key_session = tools.AuthentificationTools.keyGenerator().getString("key_session");
			return tools.AuthentificationTools.createSession(id_user, key_session, stmt);
		} catch(JSONException | SQLException e) {
			if (e.getClass() == JSONException.class) {
				return tools.ErrorJSON.serviceRefused("JSON error login", 100);	
			}
			return tools.ErrorJSON.serviceRefused("SQL error login", 1000);
		}
	}
	
	public static JSONObject logout(String key_session) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (key_session != null) {
				return tools.AuthentificationTools.deleteSession(key_session, stmt);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error logout", -1);
		} catch(JSONException | SQLException e) {
			if (e.getClass() == JSONException.class) {
				return tools.ErrorJSON.serviceRefused("JSON error logout", 100);
			}
			return tools.ErrorJSON.serviceRefused("SQL error logout", 1000);
		}	
	}
}
