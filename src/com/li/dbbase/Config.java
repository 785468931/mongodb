package com.li.dbbase;

import java.util.Locale;
import java.util.ResourceBundle;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;



public class Config {
	
	
	public final static String MONGODB_URL;
	public final static int MONGODB_URL_PORT;
	public final static String MONGODB_DATABASES;

	
	public static MongoClient mongoClient;
	public static MongoDatabase db;


	static {
		ResourceBundle rb = ResourceBundle.getBundle("config",Locale.getDefault());
		
		MONGODB_URL=rb.getString("MONGODB_URL");
		MONGODB_URL_PORT=Integer.parseInt(rb.getString("MONGODB_URL_PORT"));
		MONGODB_DATABASES=rb.getString("MONGODB_DATABASES");
		
		mongoClient = new MongoClient(MONGODB_URL, MONGODB_URL_PORT);
		db = mongoClient.getDatabase(MONGODB_DATABASES);

	}
	
}



