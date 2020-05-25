package tests;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
	public static void main(String args[]) {
		MongoDatabase database = db.Database.getMongoDBConnection();
		MongoCollection<Document> message = database.getCollection("message");
		
		/*
		Document query = new Document("_id", new ObjectId("5ecb88d0c17efb4022d805cf"));
		query.append("Comments._id", new ObjectId("5ecb890dc17efb4022d805d5"));
		if (message.find(query).iterator().hasNext()) {
			System.out.println(true);
		}
		else {
			System.out.println(false);
		}
		*/
		
		Document query = new Document("_id", new ObjectId("5ecb88d0c17efb4022d805cf")).append("Comments._id", new ObjectId("5ecb890dc17efb4022d805d5"));
		
		Document update = new Document("$set", new Document("Comments.$.Text", "Ceci est un commentaire modifié"));
		
		message.updateOne(query, update);
	}
}
