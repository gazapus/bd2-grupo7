package dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.bd2.ConnectorDB;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import datos.Sucursal;

public class SucursalDao {
	private static DBCollection collection;
	private static Gson gson;
	private static DB db;

	public static void getInstance() throws UnknownHostException {
		db = ConnectorDB.getDatabase("farmacia");
		collection = db.getCollection("sucursales");
		gson = new Gson();
	}

	public static void agregar(Sucursal sucursal) throws Exception {
		if(traer(sucursal.getCodigo()) != null) {
			throw new Exception("ERROR: Ya existe una sucursal con este codigo");
		}
		String json = gson.toJson(sucursal);
		collection.insert(BasicDBObject.parse(json));
	}

	public static Sucursal traer(int codigo) {
		DBObject result = collection.findOne( new BasicDBObject("codigo", codigo));
		if(result == null){
			return null;
		}
		return gson.fromJson(result.toString(), Sucursal.class);
	}

	public static List<Sucursal> traer() {
		List<Sucursal> sucursales = new ArrayList<Sucursal>();
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			DBObject sucursalDoc = cursor.next();
			sucursales.add(gson.fromJson( sucursalDoc.toString(), Sucursal.class));
		}		
		return sucursales;
	}

	public static boolean modificar(Sucursal sucursal) {
		BasicDBObject query = new BasicDBObject("codigo", sucursal.getCodigo());
		String json = gson.toJson(sucursal);
		WriteResult result = collection.update(query, BasicDBObject.parse(json));
		return result.getN() == 1;
	}

	public static boolean eliminar(Sucursal sucursal) {
		BasicDBObject query = new BasicDBObject("codigo", sucursal.getCodigo());
		WriteResult result = collection.remove(query);
		return result.getN() == 1;
	}
}
