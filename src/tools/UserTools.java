package tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;

public class UserTools {
	public static boolean createUser(String login_user, String password_user, String prenom_user, String nom_user, String mail_user, Statement stmt) throws SQLException {
		String query = "INSERT INTO user (login_user, password_user, prenom_user, nom_user, mail_user) VALUES ('" + login_user + "', '" + password_user + "', '" + prenom_user + "', '" + nom_user + "', '" + mail_user + "')";
		if (stmt.executeUpdate(query) == 1) {
			return true;
		}
		return false;
	}
	
	public static boolean deleteUser(int id_user, Statement stmt) throws SQLException {
		String query = "DELETE FROM user WHERE id_user = '" + id_user + "'";
		if (stmt.executeUpdate(query) == 1) {
			return true;
		}
		return false;
	}
	
	public static boolean editUser(int id_user, String login_user, String password_user, String prenom_user, String nom_user, String mail_user, Statement stmt) throws SQLException {
		String query = "UPDATE user SET login_user = '" + login_user + "', password_user = '" + password_user + "', prenom_user = '" + prenom_user + "', nom_user = '" + nom_user + "', mail_user = '" + mail_user + "' WHERE id_user = '" + id_user + "'";
		if (stmt.executeUpdate(query) == 1) {
			return true;
		}
		return false;
	}

	public static boolean isLoginExist(String login_user, Statement stmt) throws SQLException {
		String query = "SELECT login_user FROM user WHERE login_user = '" + login_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) { 
			if (res.next()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isUserExist(int id_user, Statement stmt) throws SQLException {
		String query = "SELECT id_user FROM user WHERE id_user = '" + id_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) { 
			if (res.next()) {
				return true;
			}
		}
		return false;
	}
	
	public static int getId(String login_user, Statement stmt) throws SQLException {
		String query = "SELECT * FROM user WHERE login_user = '" + login_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) { 
			if (res.next()) {
				return res.getInt("id_user");
			}
		}
		return -1;
	}
	
	public static String getLogin(int id_user, Statement stmt) throws SQLException {
		String query = "SELECT * FROM user WHERE id_user = '" + id_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) {
			if (res.next()) {
				return res.getString("login_user");
			}
		}
		return "";
	}
	
	public static JSONObject getProfile(int id_user, Statement stmt, MongoCollection<Document> collection) throws SQLException {
		String query = "SELECT * FROM user WHERE id_user = '" + id_user + "'";
		JSONObject json = new JSONObject();
		try (ResultSet res = stmt.executeQuery(query)) { 
			if (res.next()) {
				json.put("id_user", res.getInt("id_user"));
				json.put("login_user", res.getString("login_user"));
				json.put("prenom_user", res.getString("prenom_user"));
				json.put("nom_user", res.getString("nom_user"));
				json.put("mail_user", res.getString("mail_user"));
				json.append("Friends", tools.FriendsTools.getListFriend(id_user, stmt));
				json.append("Messages", tools.MessageTools.listMessage(id_user, collection));
			}
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error : " + e.getMessage(), 100);
		}
		return json;
	}
}