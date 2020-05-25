package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendsServices {
	public static JSONObject addFriend(int id_user_1, int id_user_2) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isUserConnected(id_user_1, stmt) &&
					! tools.FriendsTools.isFriendshipExist(id_user_1, id_user_2, stmt)) {
				if (tools.FriendsTools.addFriend(id_user_1, id_user_2, stmt)) {
					return tools.ErrorJSON.serviceAccepted();
				}
				return tools.ErrorJSON.serviceRefused("Erreur lors de l'ajout de l'ami", 1000);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject removeFriend(int id_user_1, int id_user_2) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isUserConnected(id_user_1, stmt) &&
					! tools.FriendsTools.isFriendshipExist(id_user_1, id_user_2, stmt)) {
				if (tools.FriendsTools.removeFriend(id_user_1, id_user_2, stmt)) {
					return tools.ErrorJSON.serviceAccepted();
				}
				return tools.ErrorJSON.serviceRefused("Erreur lors de la suppression de l'ami", 1000);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject getListFriend(String login_user) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.UserTools.isLoginExist(login_user, stmt)) {
				int id = tools.UserTools.getId(login_user, stmt);
				return tools.FriendsTools.getListFriend(id, stmt);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(JSONException | SQLException e) {
			if (e.getClass() == JSONException.class) {
				return tools.ErrorJSON.serviceRefused("JSON error : " + e.getMessage(), 100);
			}
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
}
