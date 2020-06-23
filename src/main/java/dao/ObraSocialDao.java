package dao;

import java.net.UnknownHostException;

import com.bd2.ConnectorDB;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import datos.ObraSocial;

public class ObraSocialDao {
	
	private static DBCollection collection;
	private static Gson gson;
	private static DB db;
	
	public static void getInstance() throws UnknownHostException {
		db = ConnectorDB.getDatabase("prueba");
		collection = db.getCollection("obraSocial");
		gson = new Gson();
	}
	
	public static boolean agregar(ObraSocial obraSocial) throws Exception {
		// Verifica que no exista un cliente con el mismo dni
		if(traer( obraSocial.getCuit() ) != null) {
			throw new Exception("Ya existe una obra social con el mismo cuit");
		}
		String json = gson.toJson(obraSocial);
		BasicDBObject doc = BasicDBObject.parse(json);
		WriteResult result = collection.insert(doc);
	    return result.getN() == 1;
	}
	
	public static ObraSocial traer(String cuit) {
	    DBObject result = collection.findOne(new BasicDBObject("cuit", cuit));
		if(result == null)
			return null;
	    return gson.fromJson(result.toString(), ObraSocial.class);
	}
	
	public static boolean modificar(ObraSocial obraSocial) {
		DBObject query = new BasicDBObject("cuit", obraSocial.getCuit());
		String json = gson.toJson(obraSocial);
		DBObject doc = BasicDBObject.parse(json);
		WriteResult result = collection.update(query, doc);
		return result.getN() == 1;
	}
	
	public static boolean eliminar(ObraSocial obraSocial) {
		DBObject query = new BasicDBObject("cuit", obraSocial.getCuit());
		WriteResult result = collection.remove(query);
		return result.getN() == 1;
	}
	
}
