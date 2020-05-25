package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MessageServices {
	public static JSONObject addMessage(String key_session, String text) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isSessionActive(key_session, stmt)) {
				MongoDatabase database = db.Database.getMongoDBConnection();
				MongoCollection<Document> message = database.getCollection("message");
				int id = tools.AuthentificationTools.getUserId(key_session, stmt);
				tools.MessageTools.addMessage(id, tools.UserTools.getLogin(id, stmt), text, message);
				return tools.ErrorJSON.serviceAccepted();
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject removeMessage(String key_session, ObjectId objectId) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isSessionActive(key_session, stmt)) {
				MongoDatabase database = db.Database.getMongoDBConnection();
				MongoCollection<Document> message = database.getCollection("message");
				int id = tools.AuthentificationTools.getUserId(key_session, stmt);
				if (tools.MessageTools.checkMessageAuthor(id, objectId, message)) {
					tools.MessageTools.removeMessage(objectId, message);
					return tools.ErrorJSON.serviceAccepted();
				}
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject editMessage(String key_session, ObjectId objectId, String text) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isSessionActive(key_session, stmt)) {
				MongoDatabase database = db.Database.getMongoDBConnection();
				MongoCollection<Document> message = database.getCollection("message");
				int id = tools.AuthentificationTools.getUserId(key_session, stmt);
				if (tools.MessageTools.checkMessageAuthor(id, objectId, message)) {
					tools.MessageTools.editMessage(objectId, text, message);
					return tools.ErrorJSON.serviceAccepted();
				}
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject addComment(String key_session, ObjectId objectId, String text) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isSessionActive(key_session, stmt)) {
				MongoDatabase database = db.Database.getMongoDBConnection();
				MongoCollection<Document> message = database.getCollection("message");
				int id = tools.AuthentificationTools.getUserId(key_session, stmt);
				tools.MessageTools.addComment(id, tools.UserTools.getLogin(id, stmt), text, objectId, message);
				return tools.ErrorJSON.serviceAccepted();
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject removeComment(String key_session, ObjectId objectId_message, ObjectId objectId_comment) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isSessionActive(key_session, stmt)) {
				int id = tools.AuthentificationTools.getUserId(key_session, stmt);
				MongoDatabase database = db.Database.getMongoDBConnection();
				MongoCollection<Document> message = database.getCollection("message");
				if (tools.MessageTools.checkCommentAuthor(id, objectId_message, objectId_comment, message)) {
					tools.MessageTools.removeComment(objectId_message, objectId_comment, message);
					return tools.ErrorJSON.serviceAccepted();
				}
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject editComment(String key_session, ObjectId objectId_message, ObjectId objectId_comment, String text) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isSessionActive(key_session, stmt)) {
				int id = tools.AuthentificationTools.getUserId(key_session, stmt);
				MongoDatabase database = db.Database.getMongoDBConnection();
				MongoCollection<Document> message = database.getCollection("message");
				
				if (tools.MessageTools.checkCommentAuthor(id, objectId_message, objectId_comment, message)) {
					tools.MessageTools.editComment(text, objectId_message, objectId_comment, message);
					return tools.ErrorJSON.serviceAccepted();
				}
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject addLike(String key_session, ObjectId objectId) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isSessionActive(key_session, stmt)) {
				MongoDatabase database = db.Database.getMongoDBConnection();
				MongoCollection<Document> message = database.getCollection("message");
				int id = tools.AuthentificationTools.getUserId(key_session, stmt);
				tools.MessageTools.addLike(id, objectId, message);
				return tools.ErrorJSON.serviceAccepted();
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject removeLike(String key_session, ObjectId objectId) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.AuthentificationTools.isSessionActive(key_session, stmt)) {
				MongoDatabase database = db.Database.getMongoDBConnection();
				MongoCollection<Document> message = database.getCollection("message");
				int id = tools.AuthentificationTools.getUserId(key_session, stmt);
				tools.MessageTools.removeLike(id, objectId, message);
				return tools.ErrorJSON.serviceAccepted();
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(SQLException e) {
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject listMessage(int id_user) {
		try (Connection c = db.Database.getMySQLConnection(); Statement stmt = c.createStatement()) {
			if (tools.UserTools.isUserExist(id_user, stmt)) {
				MongoDatabase database = db.Database.getMongoDBConnection();
				MongoCollection<Document> message = database.getCollection("message");
				return tools.MessageTools.listMessage(id_user, message);
			}
			return tools.ErrorJSON.serviceRefused("Arguments error", -1);
		} catch(JSONException | SQLException e) {
			if (JSONException.class == e.getClass()) {
				return tools.ErrorJSON.serviceRefused("JSON error : " + e.getMessage(), 100);
			}
			return tools.ErrorJSON.serviceRefused("SQL error : " + e.getMessage(), 1000);
		}
	}
	
	public static JSONObject getMessage(ObjectId objectId) {
		try {
			MongoDatabase database = db.Database.getMongoDBConnection();
			MongoCollection<Document> message = database.getCollection("message");
			return tools.MessageTools.getMessage(objectId, message);
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error : " + e.getMessage(),  100);
		}
	}
}
