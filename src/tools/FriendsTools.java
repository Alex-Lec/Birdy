package tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	public static List<Integer> getListIdFriend(int id_user, Statement stmt) throws SQLException {
		String query = "SELECT * FROM friendship WHERE id_user_1 = '" + id_user + "' OR id_user_2 = '" + id_user + "'";
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
		}
		return listUserId;
	}
}
