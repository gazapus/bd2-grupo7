package dao;

import java.net.UnknownHostException;

import com.bd2.ConnectorDB;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import datos.Cliente;

public class ClienteDao {
	private static DBCollection collection;
	private static Gson gson;
	private static DB db;
	
	public static void getInstance() throws UnknownHostException {
		db = ConnectorDB.getDatabase("prueba");
		collection = db.getCollection("clientes");
		gson = new Gson();
	}
	
	public static boolean agregar(Cliente objetoCliente) throws Exception {
		// Verifica que no exista un cliente con el mismo dni
		if(traer( objetoCliente.getDni() ) != null) {
			throw new Exception("Ya existe un cliente con el mismo dni");
		}
		String jsonCliente = gson.toJson(objetoCliente);
		BasicDBObject docCliente = BasicDBObject.parse(jsonCliente);
		WriteResult result = collection.insert(docCliente);
	    return result.getN() == 0;
	}
	
	// Agrega una lista de clientes
	public static boolean agregar(Cliente[] clientes) throws Exception {
		boolean resultado = true;
		for(int i=0; i<clientes.length; i++) {
			boolean agregado = agregar(clientes[i]);
			resultado = resultado && agregado;
		}
		return resultado;
	}
	
	public static Cliente traer(int dni) {
	    DBObject result = collection.findOne(new BasicDBObject("dni", dni));
	    if(result == null)
	    	return null;
	    Cliente cliente = gson.fromJson(result.toString(), Cliente.class);
	    return cliente;
	}
	
	public static boolean modificar(Cliente clienteModificado) {
		DBObject query = new BasicDBObject("dni", clienteModificado.getDni() );
		String jsonCliente = gson.toJson(clienteModificado);
		DBObject updateCliente = BasicDBObject.parse(jsonCliente);
		WriteResult result = collection.update(query, updateCliente);
		return result.getN() == 1;
	}
	
	public static boolean eliminar(Cliente clienteAEliminar) {
		DBObject query = new BasicDBObject("dni", clienteAEliminar.getDni() );
		WriteResult result = collection.remove(query);
		return result.getN() == 1;
	}
}
