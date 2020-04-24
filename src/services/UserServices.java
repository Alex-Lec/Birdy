package services;

import org.json.JSONObject;

public class UserServices {
	public static JSONObject createUser(String login_user, String password_user, String prenom_user, String nom_user, String mail_user) {
		if (login_user == null || password_user == null || prenom_user == null || nom_user == null || mail_user == null) {
			return tools.ErrorJSON.serviceRefused("Arguments incorrects", -1);
		}
		else if (tools.UserTools.isUserExist(login_user)) {
			return tools.ErrorJSON.serviceRefused("L'utilisateur existe déjà", 1000);
		}
		return tools.UserTools.createUser(login_user, password_user, prenom_user, nom_user, mail_user);
	}
}