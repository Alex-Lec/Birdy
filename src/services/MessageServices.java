package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

public class MessageServices {
	public static JSONObject addMessage(String key_session, String text) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isSessionActive(key_session, stmt)) {
				int id = tools.AuthentificationTools.getUserId(key_session, stmt);
				tools.MessageTools.addMessage(id, tools.UserTools.getLogin(id, stmt), text);
				return tools.ErrorJSON.serviceAccepted();
			}
			return tools.ErrorJSON.serviceRefused("Arguments error addMessage", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject listMessage(int id_user) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.UserTools.isUserExist(id_user, stmt)) {
				return tools.MessageTools.listMessage(id_user);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
}
