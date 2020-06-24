package dao;

import java.net.UnknownHostException;

import com.bd2.ConnectorDB;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import datos.Producto;

public class ProductoDao {
	
	private static DBCollection collection;
	private static Gson gson;
	private static DB db;
	
	public static void getInstance() throws UnknownHostException {
		db = ConnectorDB.getDatabase("prueba");
		collection = db.getCollection("productos");
		gson = new Gson();
	}
	
	public static void agregar(Producto producto) throws Exception {
		if(traer(producto.getCodigo()) != null) {
			throw new Exception("ERROR: Ya existe un producto con este codigo");
		}
		String json = gson.toJson(producto);
		collection.insert(BasicDBObject.parse(json));
	}
	
	public static void agregar(Producto[] productos) throws Exception{
		for(Producto producto: productos) {
			agregar(producto);
		}
	}
	
	public static Producto traer(int codigo) {
		DBObject result = collection.findOne( new BasicDBObject("codigo", codigo));
		if(result == null){
			return null;
		}
		return gson.fromJson(result.toString(), Producto.class);
	}
	
	public static boolean modificar(Producto producto) {
		BasicDBObject query = new BasicDBObject("codigo", producto.getCodigo());
		String json = gson.toJson(producto);
		WriteResult result = collection.update(query, BasicDBObject.parse(json));
		return result.getN() == 1;
	}
	
	public static boolean eliminar(Producto producto) {
		BasicDBObject query = new BasicDBObject("codigo", producto.getCodigo());
		WriteResult result = collection.remove(query);
		return result.getN() == 1;
	}
}
