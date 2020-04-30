package com.bd2;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class FindOneTest {

	public static void main(String[] args) {
		try
		{
			DB db = ConnectorDB.getDatabase("test");
			DBCollection collection = db.getCollection("products");
			// Agrego un nuevo documento
			BasicDBObject newDoc = new BasicDBObject("nombre", "producto_a_traer").append("precio", 5);
			collection.insert(newDoc);		
			// Busco e imprimo el documento agregado
			BasicDBObject objectQuery = new BasicDBObject("_id", newDoc.getObjectId("_id"));
			DBObject docBuscado = collection.findOne(objectQuery);
			System.out.print(docBuscado);
		}
		catch(java.net.UnknownHostException  e){
			e.printStackTrace();
		}
	}

}
