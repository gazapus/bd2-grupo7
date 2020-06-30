package com.bd2;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class ConnectorDB 
{	
	public static DB getDatabase(String dbName) throws UnknownHostException{
		String uri = "mongodb://localhost:27017";
    	MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
	    DB database = mongoClient.getDB(dbName);
	    return database;
	}
}