package tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendsTools {
	public static boolean addFriend(int id_user_1, int id_user_2, Statement stmt) throws SQLException {
		String query = "INSERT INTO friendship VALUES ('" + id_user_1 + "', '" + id_user_2 + "', CURRENT_TIMESTAMP)";
		if (stmt.executeUpdate(query) == 1) {
			return true;
		}	
		return false;
	}
	
	public static boolean removeFriend(int id_user_1, int id_user_2, Statement stmt) throws SQLException {
		String query = "DELETE FROM friendship WHERE id_user_1 = '" + id_user_1 + "' AND id_user_2 = '" + id_user_2 + "'";
		if (stmt.executeUpdate(query) == 1) {
			return true;
		}
		return false;
	}
	
	public static boolean isFriendshipExist(int id_user_1, int id_user_2, Statement stmt) throws SQLException {
		String query = "SELECT * FROM friendship WHERE id_user_1 = '" + id_user_1 + "' AND id_user_2 = '" + id_user_2 + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return true;
			}
		}
		return false;
	}
	
	public static JSONObject getListFriend(int id_user, Statement stmt) throws SQLException, JSONException {
		String query = "SELECT * FROM friendship WHERE id_user_1 = '" + id_user + "'";
		JSONObject json = new JSONObject();
		try (ResultSet res = stmt.executeQuery(query)) {
			int cpt = 1;
			while (res.next()) {
				json.put("Friend n°" + cpt++, res.getInt("id_user_2"));
			}
		}
		return json;
	}
	
	public static List<Integer> getFriendsIdList(int id_user, Statement stmt) throws SQLException, JSONException {
		String query = "SELECT * FROM friendship WHERE id_user_1 = '" + id_user + "'";
		List<Integer> list = new ArrayList<>();
		try (ResultSet res = stmt.executeQuery(query)) {
			while (res.next()) {
				list.add(res.getInt("id_user_2"));
			}
		}
		return list;
	}
}
