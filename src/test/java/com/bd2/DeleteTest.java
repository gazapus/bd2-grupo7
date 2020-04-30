package com.bd2;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class DeleteTest {

	public static void main(String[] args) {
		try
		{
			DB db = ConnectorDB.getDatabase("test");
			DBCollection collection = db.getCollection("products");
			// Agrego un nuevo documento
			BasicDBObject newDoc = new BasicDBObject("nombre", "producto_a_eliminar").append("precio", 10);
			collection.insert(newDoc);
			// Elimino el ultimo documento previamente insertado
			DBObject docQuery = new BasicDBObject("_id", newDoc.getObjectId("_id"));
			WriteResult result = collection.remove(docQuery);
			// Imprimo el resultado
			System.out.println(result);
		}
		catch(java.net.UnknownHostException  e)
		{
			e.printStackTrace();
		}
	}

}
