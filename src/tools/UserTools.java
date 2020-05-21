package tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONException;
import org.json.JSONObject;

public class UserTools {
	public static JSONObject createUser(String login_user, String password_user, String prenom_user, String nom_user, String mail_user, Statement stmt) throws JSONException, SQLException {
		String query = "INSERT INTO user (login_user, password_user, prenom_user, nom_user, mail_user) VALUES ('" + login_user + "', '" + password_user + "', '" + prenom_user + "', '" + nom_user + "', '" + mail_user + "')";
		if (stmt.executeUpdate(query) == 1) {
			return tools.ErrorJSON.serviceAccepted();
		}
		return tools.ErrorJSON.serviceRefused("SQL error createUser", 100);
	}
	
	public static JSONObject deleteUser(int id_user, Statement stmt) throws JSONException, SQLException {
		String query = "DELETE FROM user WHERE id_user = '" + id_user + "'";
		if (stmt.executeUpdate(query) == 1) {
			return tools.ErrorJSON.serviceAccepted();
		}
		return tools.ErrorJSON.serviceRefused("SQL error deleteUser", 1000);
	}

	public static JSONObject isLoginExist(String login_user, Statement stmt) throws JSONException, SQLException {
		String query = "SELECT login_user FROM user WHERE login_user = '" + login_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) { 
			if (res.next()) {
				return new JSONObject().put("isLoginExist", true);
			}
		}
		return new JSONObject ().put("isLoginExist", false);
	}
	
	public static JSONObject isUserExist(int id_user, Statement stmt) throws SQLException, JSONException {
		String query = "SELECT id_user FROM user WHERE id_user = '" + id_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) { 
			if (res.next()) {
				return new JSONObject().put("isUserExist", true);
			}
		}
		return new JSONObject ().put("isUserExist", false);
	}
	
	public static JSONObject getId(String login_user, Statement stmt) throws SQLException, JSONException {
		String query = "SELECT * FROM user WHERE login_user = '" + login_user + "'";
		try (ResultSet res = stmt.executeQuery(query)) { 
			res.next();
			return new JSONObject().put("id_user", res.getInt("id_user"));
		}
	}
	
	public static JSONObject getProfile(int id_user, Statement stmt) throws SQLException, JSONException {
		String query = "SELECT * FROM user WHERE id_user = '" + id_user + "'";
		JSONObject json = new JSONObject();
		try (ResultSet res = stmt.executeQuery(query)) { 
			res.next();
			json.put("id_user", res.getInt(1));
			json.put("login_user", res.getString(2));
			json.put("prenom_user", res.getString(4));
			json.put("nom_user", res.getString(5));
			json.put("mail_user", res.getString(6));
			return json;
		}
	}
}