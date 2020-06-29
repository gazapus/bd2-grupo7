package dao;

import java.net.UnknownHostException;

import com.bd2.ConnectorDB;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import datos.Empleado;

public class EmpleadoDao {

	private static DBCollection collection;
	private static Gson gson;
	private static DB db;
	
	public static void getInstance() throws UnknownHostException {
		db = ConnectorDB.getDatabase("farmacia");
		collection = db.getCollection("empleados");
		gson = new Gson();
	}
	
	public static boolean agregar(Empleado empleado) throws Exception {
		if(traer(empleado.getCuil()) != null || traer(empleado.getDni()) != null ) {
			throw new Exception("Ya existe un empleado con este cuil o dni");
		}
		String json = gson.toJson(empleado);
		BasicDBObject doc = BasicDBObject.parse(json);
		collection.insert(doc);
		return true;
	}
	
	public static void agregar(Empleado[] empleados) throws Exception {
		for(int i=0; i<empleados.length; i++) {
			agregar(empleados[i]);
		}
	}
	
	public static Empleado traer(String cuil) {
		DBObject result = collection.findOne(new BasicDBObject("cuil", cuil));
		if(result == null) {
			return null;
		}
		return gson.fromJson(result.toString(), Empleado.class);
	}
	
	public static Empleado traer(int dni) {
		DBObject result = collection.findOne(new BasicDBObject("dni", dni));
		if(result == null) {
			return null;
		}
		return gson.fromJson(result.toString(), Empleado.class);
	}
	
	public static boolean modificar(Empleado empleado) {
		BasicDBObject query = new BasicDBObject("cuil", empleado.getCuil());
		String json = gson.toJson(empleado);
		WriteResult result = collection.update(query, BasicDBObject.parse(json));
		return result.getN() == 1;
	}
	
	public static boolean eliminar(Empleado empleado) {
		BasicDBObject query = new BasicDBObject("cuil", empleado.getCuil());
		WriteResult result = collection.remove(query);
		return result.getN() == 1;
	}
}
