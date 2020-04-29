package com.bd2;

import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class FindAllTest {

	public static void main(String[] args) {
		try
		{
			DB db = ConnectorDB.getDatabase("test");
			DBCollection collection = db.getCollection("products");
			DBCursor cursor = collection.find();
			List <DBObject> productos = cursor.toArray();
			for(DBObject producto: productos){
				System.out.println(producto);
			}
		}
		catch(java.net.UnknownHostException  e)
		{
			e.printStackTrace();
		}

	}

}
