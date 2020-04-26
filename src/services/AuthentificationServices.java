package services;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthentificationServices {
	public static JSONObject login(String login_user, String password_user) {
		try {
			if (login_user == null || password_user == null) {
				return tools.ErrorJSON.serviceRefused("Arguments error login", -1);
			}
			int id_user = tools.UserTools.getId(login_user).getInt("id_user");
			String key_session = tools.AuthentificationTools.keyGenerator().getString("key_session");
			return tools.AuthentificationTools.createSession(id_user, key_session);
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error login", 100);
		}
	}
	
	public static JSONObject logout(String key_session) {
		if (key_session != null) {
			return tools.AuthentificationTools.deleteSession(key_session);
		}
		return tools.ErrorJSON.serviceRefused("Arguments error logout", -1);
	}
}
