package dao;

import java.net.UnknownHostException;

import com.bd2.ConnectorDB;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import datos.TipoDeProducto;

public class TipoDeProductoDao {
	
	private static DBCollection collection;
	private static Gson gson;
	private static DB db;
	
	public static void getInstance() throws UnknownHostException {
		db = ConnectorDB.getDatabase("prueba");
		collection = db.getCollection("tiposDeProductos");
		gson = new Gson();
	}
	
	public static void agregar(TipoDeProducto tipo) throws Exception {
		if(traer(tipo.getCodigo()) != null) {
			throw new Exception("ERROR: Ya existe un tipo con este codigo");
		}
		String json = gson.toJson(tipo);
		collection.insert(BasicDBObject.parse(json));
	}
	
	public static TipoDeProducto traer(int codigo) {
		DBObject result = collection.findOne( new BasicDBObject("codigo", codigo));
		if(result == null){
			return null;
		}
		return gson.fromJson(result.toString(), TipoDeProducto.class);
	}
	
	public static boolean modificar(TipoDeProducto tipo) {
		BasicDBObject query = new BasicDBObject("codigo", tipo.getCodigo());
		String json = gson.toJson(tipo);
		WriteResult result = collection.update(query, BasicDBObject.parse(json));
		return result.getN() == 1;
	}
	
	public static boolean eliminar(TipoDeProducto tipo) {
		BasicDBObject query = new BasicDBObject("cuit", tipo.getCodigo());
		WriteResult result = collection.remove(query);
		return result.getN() == 1;
	}
}
