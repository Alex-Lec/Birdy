package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

public class UserServices {
	public static JSONObject createUser(String login_user, String password_user, String prenom_user, String nom_user, String mail_user) {
		if (login_user == null || password_user == null || prenom_user == null || nom_user == null || mail_user == null) {
			return tools.ErrorJSON.serviceRefused("Arguments incorrects", -1);
		}
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.UserTools.isLoginExist(login_user, stmt).getBoolean("isLoginExist")) {
				return tools.ErrorJSON.serviceRefused("L'utilisateur existe déjà", 1000);
			}
			return tools.UserTools.createUser(login_user, password_user, prenom_user, nom_user, mail_user, stmt);
		} catch(JSONException | SQLException e) {
			if (e.getClass() == JSONException.class) {
				return tools.ErrorJSON.serviceRefused("JSON error createUser", 100);
			}
			return tools.ErrorJSON.serviceRefused("SQL error createUser", 1000);
		}
	}
	
	public static JSONObject deleteUser(int id_user) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.UserTools.isUserExist(id_user, stmt).getBoolean("isUserExist")) {
				return tools.UserTools.deleteUser(id_user, stmt);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error deleteUser", -1);
		} catch(JSONException | SQLException e) {
			if (e.getClass() == JSONException.class) {
				return tools.ErrorJSON.serviceRefused("JSON error deleteUser", 100);
			}
			return tools.ErrorJSON.serviceRefused("SQL error deleteUser", 1000);
		}
	}
	
	public static JSONObject getProfile(String login_user) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.UserTools.isLoginExist(login_user, stmt).getBoolean("isLoginExist")) {
				int id_user = tools.UserTools.getId(login_user, stmt).getInt("id_user");
				return tools.UserTools.getProfile(id_user, stmt);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error getProfile", -1);
		} catch(JSONException | SQLException e) {
			if (e.getClass() == JSONException.class) {
				return tools.ErrorJSON.serviceRefused("JSON error getProfileServices", 100);
			}
			return tools.ErrorJSON.serviceRefused("SQL error getProfileServices", 1000);
		}
	}
}