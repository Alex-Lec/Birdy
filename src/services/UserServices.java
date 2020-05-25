package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class UserServices {
	public static JSONObject createUser(String login_user, String password_user, String prenom_user, String nom_user, String mail_user) {
		if (login_user == null || password_user == null || prenom_user == null || nom_user == null || mail_user == null) {
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		}
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.UserTools.isLoginExist(login_user, stmt)) {
				return tools.ErrorJSON.serviceRefused("L'utilisateur existe déjà", -1);
			}
			if (tools.UserTools.createUser(login_user, password_user, prenom_user, nom_user, mail_user, stmt)) {
				return tools.ErrorJSON.serviceAccepted();
			}
			return tools.ErrorJSON.serviceRefused("Erreur lors de l'nsertion du nouvel utilisateur dans la base de données", 1000);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject deleteUser(int id_user) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.UserTools.isUserExist(id_user, stmt)) {
				if (tools.UserTools.deleteUser(id_user, stmt)) {
					return tools.ErrorJSON.serviceAccepted();
				}
				return tools.ErrorJSON.serviceRefused("Erreur lors de la suppression de l'utilisateur dans la base de données", 1000);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject editUser(String key_session, String login_user, String password_user, String prenom_user, String nom_user, String mail_user) {
		if (key_session == null || login_user == null || password_user == null || prenom_user == null || nom_user == null || mail_user == null) {
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		}
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isSessionActive(key_session, stmt) && ! tools.UserTools.isLoginExist(login_user, stmt)) {
				int id = tools.AuthentificationTools.getUserId(key_session, stmt);
				if (tools.UserTools.editUser(id, login_user, password_user, prenom_user, nom_user, mail_user, stmt)) {
					return tools.ErrorJSON.serviceAccepted();
				}
				return tools.ErrorJSON.serviceRefused("Erreur lors de la modification du profil", 1000);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject getProfile(String login_user) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.UserTools.isLoginExist(login_user, stmt)) {
				MongoDatabase database = db.Database.getMongoDBConnection();
				MongoCollection<Document> message = database.getCollection("message");
				return tools.UserTools.getProfile(tools.UserTools.getId(login_user, stmt), stmt, message);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
}