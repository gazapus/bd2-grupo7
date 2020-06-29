package dao;

import java.net.UnknownHostException;

import com.bd2.ConnectorDB;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import datos.FormaDePago;

public class FormaDePagoDao {
	
	private static DBCollection collection;
	private static Gson gson;
	private static DB db;
	
	public static void getInstance() throws UnknownHostException {
		db = ConnectorDB.getDatabase("farmacia");
		collection = db.getCollection("formasDePago");
		gson = new Gson();
	}
	
	public static void agregar(FormaDePago forma) throws Exception {
		if(traer(forma.getCodigo()) != null) {
			throw new Exception("ERROR: Ya existe una forma de pago con este codigo");
		}
		String json = gson.toJson(forma);
		collection.insert(BasicDBObject.parse(json));
	}

	public static FormaDePago traer(int codigo) {
		DBObject result = collection.findOne( new BasicDBObject("codigo", codigo));
		if(result == null){
			return null;
		}
		return gson.fromJson(result.toString(), FormaDePago.class);
	}
	
	public static boolean modificar(FormaDePago forma) {
		BasicDBObject query = new BasicDBObject("codigo", forma.getCodigo());
		String json = gson.toJson(forma);
		WriteResult result = collection.update(query, BasicDBObject.parse(json));
		return result.getN() == 1;
	}
	
	public static boolean eliminar(FormaDePago forma) {
		BasicDBObject query = new BasicDBObject("cuit", forma.getCodigo());
		WriteResult result = collection.remove(query);
		return result.getN() == 1;
	}
}
