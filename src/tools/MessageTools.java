package tools;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MessageTools {
	public static JSONObject addMessage(int id_user, String login_user, String text) {
		MongoDatabase database = db.Database.getMongoDBConnection();
		MongoCollection<Document> message = database.getCollection("message");
		
		Document query = new Document();
		query.append("User id", id_user);
		query.append("Author", login_user);
		query.append("Date", tools.DateTools.getCurrentDate());
		query.append("Text", text);
		
		message.insertOne(query);
		
		return tools.ErrorJSON.serviceAccepted();
	}
	
	public static JSONObject listMessage(int id_user) {
		MongoDatabase database = db.Database.getMongoDBConnection();
		MongoCollection<Document> message = database.getCollection("message");
		
		Document query = new Document().append("User id", id_user);
		MongoCursor<Document> cursor = message.find(query).iterator();
		
		JSONObject json = new JSONObject();
		List<Document> list = new ArrayList<>();
		
		while (cursor.hasNext()) {
			list.add(cursor.next());
		}
		
		try {
			json.put("Liste message user " + id_user, list);
		} catch(JSONException e) {
			return tools.ErrorJSON.serviceRefused("JSON error listMessage", 100);
		}
		return json;
	}
}
