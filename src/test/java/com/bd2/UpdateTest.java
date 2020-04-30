package com.bd2;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class UpdateTest {
	public static void main(String[] args) {
		try
		{
			DB db = ConnectorDB.getDatabase("test");
			DBCollection collection = db.getCollection("products");
			// Agrego un nuevo documento
			BasicDBObject newDoc = new BasicDBObject("nombre", "producto_a_actualizar").append("precio", 10);
			collection.insert(newDoc);		
			// Actualizo el documento agregado
			DBObject docQuery = new BasicDBObject("_id", newDoc.getObjectId("_id"));
			DBObject docToUpdate = (DBObject) newDoc.copy();	
			docToUpdate.put("nombre", "producto_actualizado");
			collection.update(docQuery, docToUpdate);
			// Imprimo el documento actualizado en la bd
			DBObject docUpdated = collection.findOne(docQuery);
			System.out.print(docUpdated);
		}
		catch(java.net.UnknownHostException  e)
		{
			e.printStackTrace();
		}
	}
}
