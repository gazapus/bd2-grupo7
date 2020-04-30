package com.bd2;

import java.net.UnknownHostException;

import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class ConnectionTest 
{
    @Test
    public void connect() throws UnknownHostException
    {
    	try{
    		String uri = "mongodb://localhost:27017";
	    	MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
	    	System.out.println("Conectado\nBases de datos:");
	    	for(String bdName: mongoClient.getDatabaseNames())
	    		System.out.println(bdName);
	    	mongoClient.close();
    	}catch(UnknownHostException e){
    		e.printStackTrace();
    	}
    }
}
