package services;

import org.json.JSONException;
import org.json.JSONObject;

public class UserServices {
	public static JSONObject createUser(String login_user, String password_user, String prenom_user, String nom_user, String mail_user) {
		try {
			if (login_user == null || password_user == null || prenom_user == null || nom_user == null || mail_user == null) {
				return tools.ErrorJSON.serviceRefused("Arguments incorrects", -1);
			}
			else if (tools.UserTools.isLoginExist(login_user).getBoolean("isUserExist")) {
				return tools.ErrorJSON.serviceRefused("L'utilisateur existe déjà", 1000);
			}
			return tools.UserTools.createUser(login_user, password_user, prenom_user, nom_user, mail_user);
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error createUser", 100);
		}
	}
	
	public static JSONObject deleteUser(int id_user) {
		try {
			if (tools.UserTools.isUserExist(id_user).getBoolean("isUserExist")) {
				return tools.UserTools.deleteUser(id_user);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error deleteUser", -1);
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error deleteUser", 100);
		}
	}
	
	public static JSONObject getProfile(String login_user) {
		try {
			if (tools.UserTools.isLoginExist(login_user).getBoolean("isLoginExist")) {
				int id_user = tools.UserTools.getId(login_user).getInt("id_user");
				return tools.UserTools.getProfile(id_user);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error getProfile", -1);
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error getProfileServices", 100);
		}
	}
}