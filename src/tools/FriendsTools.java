package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendsTools {
	public static JSONObject addFriend(int id_user_1, int id_user_2) {
		String query = "INSERT INTO friendship VALUES ('" + id_user_1 + "', '" + id_user_2 + "', CURRENT_TIMESTAMP)";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			stmt.executeUpdate(query);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error addFriend", 1000);
		}
		return tools.ErrorJSON.serviceAccepted();
	}
	
	public static JSONObject removeFriend(int id_user_1, int id_user_2) {
		String query = "DELETE FROM friendship WHERE id_user_1 = '" + id_user_1 + "' AND id_user_2 = '" + id_user_2 + "'";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			stmt.executeUpdate(query);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error removeFriend", 1000);
		}
		return tools.ErrorJSON.serviceAccepted();
	}
	
	public static JSONObject isFriendshipExist(int id_user_1, int id_user_2) {
		String query = "SELECT * FROM friendship WHERE id_user_1 = '" + id_user_1 + "' AND id_user_2 = '" + id_user_2 + "'";
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement(); ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return new JSONObject().put("isFriendshipExist", true);
			}
			return new JSONObject().put("isFriendshipExist", false);
		} catch(SQLException | JSONException e) {
			if (e.getClass() == SQLException.class) {
				return tools.ErrorJSON.serviceRefused("SQL error isFriendshipExist", 1000);
			}
			return tools.ErrorJSON.serviceRefused("JSON error isFriendshipExist", 100);
		}
	}
	
	public static JSONObject getListFriend(int id_user) {
		String query = "SELECT * FROM friendship WHERE id_user_1 = '" + id_user + "' OR id_user_2 = '" + id_user + "'";
		JSONObject json = new JSONObject();
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement(); ResultSet res = stmt.executeQuery(query)) {
			int cpt = 1;
			while (res.next()) {
				if (res.getInt(1) == id_user) {
					json.put("Friend n°" + cpt++, tools.UserTools.getProfile(res.getInt(2)));
				}
				else {
					json.put("Friend n°" + cpt++, tools.UserTools.getProfile(res.getInt(1)));
				}
			}
			return json;
		} catch(SQLException | JSONException e) {
			if (e.getClass() == SQLException.class) {
				return tools.ErrorJSON.serviceRefused("SQL error tools.getListFriend", 1000);
			}
			return tools.ErrorJSON.serviceRefused("JSON error tools.getListFriend", 100);
		}
	}
}
