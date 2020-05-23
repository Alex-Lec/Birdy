package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

public class AuthentificationServices {
	public static JSONObject login(String login_user, String password_user) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (login_user == null || password_user == null) {
				return tools.ErrorJSON.serviceRefused("Arguments error", -1);
			}
			
			int id_user = tools.UserTools.getId(login_user, stmt);
			
			if (tools.AuthentificationTools.isUserConnected(id_user, stmt) &&
					tools.AuthentificationTools.updateSessionTimeOut(tools.AuthentificationTools.getKeyUserSession(id_user, stmt), stmt)) {
				return tools.ErrorJSON.serviceAccepted();
			}
			
			String key_session = tools.AuthentificationTools.keyGenerator();
			
			if (tools.AuthentificationTools.createSession(id_user, key_session, stmt)) {
				return tools.ErrorJSON.serviceAccepted();
			}
			return tools.ErrorJSON.serviceRefused("Erreur lors de la création de la session", 1000);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject logout(String key_session) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (key_session != null) {
				if (tools.AuthentificationTools.deleteSession(key_session, stmt)) {
					return tools.ErrorJSON.serviceAccepted();
				}
				return tools.ErrorJSON.serviceRefused("Erreur lors de la fermeture de la session", 1000);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}	
	}
}
