package com.li.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import com.li.mongodb.vo.Log;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

@SuppressWarnings({ "rawtypes", "unused" })
public class MyMongoDBUtils {

	private static MongoClient mongoClient;
	private static MongoDatabase db;
	// private static MongoCollection collection;
	

	static {

		mongoClient = new MongoClient("localhost", 27017);
		db = mongoClient.getDatabase("ifangchou");

	}
	public static void main(String[] args) {
//		 testDelete();
		// testListQuerySize();
//		testListQuery(2, 10);
		// testOneQuery();
		 testInsert();
//		testListQuerySize();
	}

	private static void testListQuery(int page, int total) {
		System.err.println("===========going to select==========");
		if (page <= 0) {
			page = 1;
		}
		page = (page - 1) * total;
		MongoCollection collection = db.getCollection("payLog");
		FindIterable iterable = collection.find().skip(page).limit(total);
		MongoCursor cursor = iterable.iterator();

		while (cursor.hasNext()) {
			Document user = (Document) cursor.next();
			// System.out.println(user.get("file"));
			System.out.println(user.toString());
		}
		cursor.close();
	}

	private static void testListQuerySize() {
		System.err.println("===========going to select==========");
		MongoCollection collection = db.getCollection("payLog");
		System.out.println(collection.count());
	}

	private static void testOneQuery() {
		System.err.println("===========going to select==========");
		MongoCollection collection = db.getCollection("payLog");
		BasicDBObject query = new BasicDBObject("cust_id", "A111");
		// BasicDBObject query = new BasicDBObject("file", "FileInputStream");
		// BasicDBObject returnField1 = new BasicDBObject("cust_id", 1);
		// BasicDBObject returnField2 = new BasicDBObject("status", 1);
		FindIterable iterable = collection.find(query);
		MongoCursor cursor = iterable.iterator();

		while (cursor.hasNext()) {
			Document user = (Document) cursor.next();
			// System.out.println(user.get("file"));
			System.out.println(user.toString());
		}
		cursor.close();
	}

	@SuppressWarnings({ "unchecked" })
	private static void testInsert() {
		System.err.println("===========going to insert==========");
		MongoCollection collection = db.getCollection("payLog");

//		Document doc = new Document();
//		doc.put("orderNo", "A111");
//		doc.put("phoneNo", 210);
//		doc.put("errorCode", "C");
		
		
		Log log=new Log();
		log.setOrderNo("12312454564");
		log.setPhoneNo("13421747372");
		log.setErrorCode("0000");
		log.setErrorMessage("success");
		
		
		collection.insertOne(log);

		
		// FindIterable iterable = collection.find();
		// MongoCursor cursor = iterable.iterator();
		// while (cursor.hasNext()) {
		// org.bson.Document user = (Document) cursor.next();
		// System.out.println(user.toString());
		// }

	}

	@SuppressWarnings({ "unchecked" })
	private static void testBatchInsert() {
		System.err.println("===========going to insert==========");
		MongoCollection collection = db.getCollection("payLog");

		List<Document> list = new ArrayList<Document>();

		for (int i = 0; i < 20; i++) {
			Document doc = new Document();
			doc.put("cust_id", "A" + i);
			doc.put("amount", 100 * i);
			if (i % 2 == 0) {
				doc.put("status", "C" + i);
			} else {
				doc.put("status", "D" + i);

			}
			list.add(doc);
		}
		collection.insertMany(list);

	}


	private static void testDelete() {
		System.err.println("===========going to delete==========");
		MongoCollection collection = db.getCollection("payLog");

		BasicDBObject query = new BasicDBObject("amount" , 200);
//		BasicDBObject query = new BasicDBObject("amount", "0");
//		collection.deleteMany(query);
		collection.deleteOne(query);
		FindIterable iterable = collection.find();
		MongoCursor cursor = iterable.iterator();
		while (cursor.hasNext()) {
			Document user = (Document) cursor.next();
			System.out.println(user.toString());
		}
	}


	private static void testToJsonObject() {
		System.err.println("===========to json object==========");
		String jsonString = "{  'title' : 'NoSQL Overview', 'description' : 'No sql database is very fast', 'by_user' : 'tutorials point', 'url' : 'http://www.tutorialspoint.com', 'tags' : [ 'mongodb', 'database', 'NoSQL' ], 'likes' : 10 }";
		BasicDBObject doc = (BasicDBObject) JSON.parse(jsonString);
		System.out.println(doc);
		System.out.println(doc.get("tags").getClass().getCanonicalName());
	}

	// test the map reduce

	private static void testMapReduce() {
		System.err.println("===========test map reduce==========");
		// MongoClient mongoClient = new MongoClient( "localhost" , 27017);
		// MongoDatabase db = mongoClient.getDatabase( "UFS" );
		MongoCollection collection = db.getCollection("payLog");

		String map = "function(){emit(this.cust_id,this.amount);}";
		String reduce = "function(key, values){return Array.sum(values)}";

		// MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce,
		// null, MapReduceCommand.OutputType.INLINE, null);

		MapReduceIterable out = collection.mapReduce(map, reduce);

		MongoCursor cursor = out.iterator();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}

		BasicDBObject query = new BasicDBObject("status", "C");

		DBCollection dbcol = (DBCollection) collection;
		MapReduceCommand cmd = new MapReduceCommand(dbcol, map, reduce,
				"outputCollection", MapReduceCommand.OutputType.INLINE, query);
		MapReduceOutput out2 = dbcol.mapReduce(cmd);

		for (DBObject o : out2.results()) {
			System.out.println(o.toString());
		}
		System.out.println("Done");

	}

}
