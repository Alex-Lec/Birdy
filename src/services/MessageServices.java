package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageServices {
	public static JSONObject addMessage(int id_user, String login_user, String text, String key_session) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isUserConnected(id_user, stmt).getBoolean("isUserConnected")) {
				return tools.MessageTools.addMessage(id_user, login_user, text);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error addMessage", -1);
		} catch(JSONException | SQLException e) {
			if (e.getClass() == JSONException.class) {
				return tools.ErrorJSON.serviceRefused("JSON error addMessage", 100);
			}
			return tools.ErrorJSON.serviceRefused("SQL error addMessage", 1000);
		}
	}
	
	public static JSONObject listMessage(int id_user) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.UserTools.isUserExist(id_user, stmt).getBoolean("isUserExist")) {
				return tools.MessageTools.listMessage(id_user);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error listMessage", -1);
		} catch(JSONException | SQLException e) {
			if (e.getClass() == JSONException.class) {
				return tools.ErrorJSON.serviceRefused("JSON error listMessage", 100);
			}
			return tools.ErrorJSON.serviceRefused("SQL error listMessage", 1000);
		}
	}
}
