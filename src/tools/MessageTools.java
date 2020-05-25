package tools;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class MessageTools {
	public static void addMessage(int id_user, String login_user, String text, MongoCollection<Document> collection) {
		Document query = new Document();
		query.append("Author_id", id_user);
		query.append("Author_name", login_user);
		query.append("Date", tools.DateTools.getCurrentDate());
		query.append("Text", text);
		BasicDBList commentaires = new BasicDBList();
		query.append("Comments", commentaires);
		BasicDBList likes = new BasicDBList();
		query.append("Likes", likes);
		
		collection.insertOne(query);
	}
	
	public static void removeMessage(ObjectId objectId, MongoCollection<Document> collection) {
		collection.deleteOne(new Document("_id", objectId));
	}
	
	public static void editMessage(ObjectId objectId, String text, MongoCollection<Document> collection) {
		Document getMessageQuery = new Document().append("_id", objectId);
		MongoCursor<Document> cursor = collection.find(getMessageQuery).iterator();
		
		if (cursor.hasNext()) {
			Document new_text = new Document("Text", text);
			Document query = new Document("$set", new_text);
			
			collection.updateOne(cursor.next(), query);
		}
	}
	
	public static void addComment(int id_user, String login_user, String text, ObjectId objectId, MongoCollection<Document> collection) {		
		Document getMessageQuery = new Document().append("_id", objectId);
		MongoCursor<Document> cursor = collection.find(getMessageQuery).iterator();
				
		if (cursor.hasNext()) {
			Document comment = new Document();
			comment.append("_id", new ObjectId());
			comment.append("Author_id", id_user);
			comment.append("Author_name", login_user);
			comment.append("Date", tools.DateTools.getCurrentDate());
			comment.append("Text", text);
			
			Document query = new Document();
			query.append("$push", new Document().append("Comments", comment));
			collection.updateOne(cursor.next(), query);
		}
	}
	
	public static void removeComment(ObjectId objectId_message, ObjectId objectId_comment, MongoCollection<Document> collection) {
		Document getMessageQuery = new Document().append("_id", objectId_message);
		MongoCursor<Document> cursor = collection.find(getMessageQuery).iterator();
		
		if (cursor.hasNext()) {
			Document id = new Document("_id", objectId_comment);
			Document remove = new Document("Comments", id);
			Document query = new Document("$pull", remove);
			
			collection.updateOne(cursor.next(), query);
		}
	}
	
	public static void editComment(String text, ObjectId objectId_message, ObjectId objectId_comment, MongoCollection<Document> collection) {
		Document check = new Document("_id", objectId_message);
		check.append("Comments._id", objectId_comment);
		
		if (collection.find(check).iterator().hasNext()) {
			Document query = new Document("_id", objectId_message).append("Comments._id", objectId_comment);
			Document update = new Document("$set", new Document("Comments.$.Text", text));
			
			collection.updateOne(query, update);
		}
	}
	
	public static void addLike(int id_user, ObjectId objectId, MongoCollection<Document> collection) {	
		Document getMessageQuery = new Document().append("_id", objectId);
		MongoCursor<Document> cursor = collection.find(getMessageQuery).iterator();
		
		if (cursor.hasNext()) {
			Document like = new Document();
			like.append("Author_id", id_user);
			like.append("Date", tools.DateTools.getCurrentDate());
			
			Document query = new Document();
			query.append("$push", new Document().append("Likes", like));
			collection.updateOne(cursor.next(), query);
		}
	}
	
	public static void removeLike(int id_user, ObjectId objectId, MongoCollection<Document> collection) {
		Document getMessageQuery = new Document().append("_id", objectId);
		MongoCursor<Document> cursor = collection.find(getMessageQuery).iterator();
		
		if (cursor.hasNext()) {			
			Document author = new Document("Author_id", id_user);
			Document unlike = new Document("Likes", author);
			Document query = new Document("$pull", unlike);
				
			collection.updateOne(cursor.next(), query);
		}
	}
	
	public static JSONObject listMessage(int id_user, MongoCollection<Document> collection) throws JSONException {
		Document query = new Document().append("Author_id", id_user);
		MongoCursor<Document> cursor = collection.find(query).iterator();
		
		JSONObject json = new JSONObject();
		List<Document> list = new ArrayList<>();
		
		while (cursor.hasNext()) {
			list.add(cursor.next());
		}
		
		json.put("Liste message user " + id_user, list);
		return json;
	}
	
	public static JSONObject getMessage(ObjectId objectId, MongoCollection<Document> collection) throws JSONException {
		Document query = new Document().append("_id", objectId);
		MongoCursor<Document> cursor = collection.find(query).iterator();
		
		JSONObject json = new JSONObject();
		
		if (cursor.hasNext()) {
			json.put("Message " + objectId, cursor.next());
		}
		
		return json;
	}
	
	public static boolean checkMessageAuthor(int id_user, ObjectId objectId, MongoCollection<Document> collection) {
		Document getMessageQuery = new Document().append("_id", objectId);
		MongoCursor<Document> cursor = collection.find(getMessageQuery).iterator();
		
		if (cursor.hasNext()) {
			if (id_user == Integer.parseInt(cursor.next().get("Author_id").toString())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkCommentAuthor(int id_user, ObjectId objectId_message, ObjectId objectId_comment, MongoCollection<Document> collection) {
		Document query = new Document("_id", objectId_message);
		query.append("Comments._id", objectId_comment);
		
		if (collection.find(query).iterator().hasNext()) {
			return true;
		}
		return false;
	}
}
