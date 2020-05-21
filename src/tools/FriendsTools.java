package tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendsTools {
	public static JSONObject addFriend(int id_user_1, int id_user_2, Statement stmt) throws SQLException, JSONException {
		String query = "INSERT INTO friendship VALUES ('" + id_user_1 + "', '" + id_user_2 + "', CURRENT_TIMESTAMP)";
		if (stmt.executeUpdate(query) == 1) {
			return tools.ErrorJSON.serviceAccepted();
		}	
		return tools.ErrorJSON.serviceRefused("SQL error addFriend", 1000);
	}
	
	public static JSONObject removeFriend(int id_user_1, int id_user_2, Statement stmt) throws SQLException, JSONException {
		String query = "DELETE FROM friendship WHERE id_user_1 = '" + id_user_1 + "' AND id_user_2 = '" + id_user_2 + "'";
		if (stmt.executeUpdate(query) == 1) {
			return tools.ErrorJSON.serviceAccepted();
		}
		return tools.ErrorJSON.serviceRefused("SQL error removeFriend", 1000);
	}
	
	public static JSONObject isFriendshipExist(int id_user_1, int id_user_2, Statement stmt) throws SQLException, JSONException {
		String query = "SELECT * FROM friendship WHERE id_user_1 = '" + id_user_1 + "' AND id_user_2 = '" + id_user_2 + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return new JSONObject().put("isFriendshipExist", true);
			}
		}
		return new JSONObject().put("isFriendshipExist", false);
	}
	
	public static JSONObject getListFriend(int id_user, Statement stmt) throws JSONException {
		String query = "SELECT * FROM friendship WHERE id_user_1 = '" + id_user + "' OR id_user_2 = '" + id_user + "'";
		JSONObject json = new JSONObject();
		List<Integer> listUserId = new ArrayList<>();
		try (ResultSet res = stmt.executeQuery(query)) {
			while (res.next()) {
				if (res.getInt("id_user_1") == id_user) {
					listUserId.add(res.getInt("id_user_2"));
				}
				else {
					listUserId.add(res.getInt("id_user_1"));
				}
			}
			int cpt = 1;
			for (int id : listUserId) {
				json.put("Friend n°" + cpt++, tools.UserTools.getProfile(id, stmt));
			}
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error getListFriend tools", 1000);
		}
		return json;
	}
}
