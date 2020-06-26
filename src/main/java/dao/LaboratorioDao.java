package dao;

import java.net.UnknownHostException;

import com.bd2.ConnectorDB;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import datos.Laboratorio;

public class LaboratorioDao {
	
	private static DBCollection collection;
	private static Gson gson;
	private static DB db;
	
	public static void getInstance() throws UnknownHostException {
		db = ConnectorDB.getDatabase("farmacia");
		collection = db.getCollection("laboratorios");
		gson = new Gson();
	}
	
	public static void agregar(Laboratorio laboratorio) throws Exception {
		if(traer(laboratorio.getCuit()) != null) {
			throw new Exception("ERROR: Ya existe un laboratio con este cuit");
		}
		String json = gson.toJson(laboratorio);
		collection.insert(BasicDBObject.parse(json));
	}
	
	public static void agregar(Laboratorio[] laboratorios) throws Exception {
		for(Laboratorio laboratorio: laboratorios){
			agregar(laboratorio);
		}
	}
	
	public static Laboratorio traer(String cuit) {
		DBObject result = collection.findOne( new BasicDBObject("cuit", cuit));
		if(result == null){
			return null;
		}
		return gson.fromJson(result.toString(), Laboratorio.class);
	}
	
	public static boolean modificar(Laboratorio laboratorio) {
		BasicDBObject query = new BasicDBObject("cuit", laboratorio.getCuit());
		String json = gson.toJson(laboratorio);
		WriteResult result = collection.update(query, BasicDBObject.parse(json));
		return result.getN() == 1;
	}
	
	public static boolean eliminar(Laboratorio laboratorio) {
		BasicDBObject query = new BasicDBObject("cuit", laboratorio.getCuit());
		WriteResult result = collection.remove(query);
		return result.getN() == 1;
	}
}
