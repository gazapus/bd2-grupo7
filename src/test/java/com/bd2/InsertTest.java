package com.bd2;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class InsertTest {

	public static void main(String[] args) {
		try
		{
			DB db = ConnectorDB.getDatabase("test");
			DBCollection collection = db.getCollection("products");
			DBObject product1 = new BasicDBObject("nombre", "producto_uno").append("precio", 14);
		    WriteResult result = collection.insert(product1);
		    System.out.print(result);
		}
		catch(java.net.UnknownHostException  e){
			e.printStackTrace();
		}
	}

}
