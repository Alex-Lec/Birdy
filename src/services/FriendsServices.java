package services;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendsServices {
	public static JSONObject addFriend(int id_user_1, int id_user_2) {
		try {
			if (tools.FriendsTools.isFriendshipExist(id_user_1, id_user_2).getBoolean("isFriendshipExist") == false) {
				return tools.FriendsTools.addFriend(id_user_1, id_user_2);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error addFriend", -1);
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error addFriend", 100);
		}
	}
	
	public static JSONObject removeFriend(int id_user_1, int id_user_2) {
		try {
			if (tools.FriendsTools.isFriendshipExist(id_user_1, id_user_2).getBoolean("isFriendshipExist") == true) {
				return tools.FriendsTools.removeFriend(id_user_1, id_user_2);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error addFriend", -1);
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error addFriend", 100);
		}
	}
}
