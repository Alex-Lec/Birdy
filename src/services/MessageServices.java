package services;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageServices {
	public static JSONObject addMessage(int id_user, String login_user, String text, String key_session) {
		try {
			if (tools.AuthentificationTools.isUserConnected(key_session, id_user).getBoolean("isUserConnected")) {
				return tools.MessageTools.addMessage(id_user, login_user, text);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error addMessage", -1);
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error addMessage", 100);
		}
	}
	
	public static JSONObject listMessage(int id_user) {
		try {
			if (tools.UserTools.isUserExist(id_user).getBoolean("isUserExist")) {
				return tools.MessageTools.listMessage(id_user);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error listMessage", -1);
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error listMessage", 100);
		}
	}
}
