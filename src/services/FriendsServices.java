package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendsServices {
	public static JSONObject addFriend(int id_user_1, int id_user_2) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isUserConnected(id_user_1, stmt).getBoolean("isUserConnected") &&
					tools.FriendsTools.isFriendshipExist(id_user_1, id_user_2, stmt).getBoolean("isFriendshipExist") == false) {
				return tools.FriendsTools.addFriend(id_user_1, id_user_2, stmt);			
			}
			return tools.ErrorJSON.serviceRefused("Arguments error addFriend", -1);
		} catch(JSONException | SQLException e) {
			if (e.getClass() == JSONException.class) {
				return tools.ErrorJSON.serviceRefused("JSON error addFriend", 100);
			}
			return tools.ErrorJSON.serviceRefused("SQL error addFriend", 1000);
		}
	}
	
	public static JSONObject removeFriend(int id_user_1, int id_user_2) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isUserConnected(id_user_1, stmt).getBoolean("isUserConnected") &&
					tools.FriendsTools.isFriendshipExist(id_user_1, id_user_2, stmt).getBoolean("isFriendshipExist")) {
				return tools.FriendsTools.removeFriend(id_user_1, id_user_2, stmt);			
			}
			return tools.ErrorJSON.serviceRefused("Arguments error removeFriend", -1);
		} catch(JSONException | SQLException e) {
			if (e.getClass() == JSONException.class) {
				return tools.ErrorJSON.serviceRefused("JSON error removeFriend", 100);
			}
			return tools.ErrorJSON.serviceRefused("SQL error removeFriend", 1000);
		}
	}
	
	public static JSONObject getListFriend(String login_user) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.UserTools.isLoginExist(login_user, stmt).getBoolean("isLoginExist")) {
				int id_user = tools.UserTools.getId(login_user, stmt).getInt("id_user");
				return tools.FriendsTools.getListFriend(id_user, stmt);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error services getListFriend", -1);
		} catch(JSONException | SQLException e) {
			if (e.getClass() == JSONException.class) {
				return tools.ErrorJSON.serviceRefused("JSON error getListFriend", 100);
			}
			return tools.ErrorJSON.serviceRefused("SQL error getListFriend", 1000);
		}
	}
}
