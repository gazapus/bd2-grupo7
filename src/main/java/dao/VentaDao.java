package dao;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.net.UnknownHostException;

import com.bd2.ConnectorDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;

import datos.Venta;

public class VentaDao {
    private static DBCollection collection;
	private static Gson gson;
	private static DB db;
	
	public static void getInstance() throws UnknownHostException {
		db = ConnectorDB.getDatabase("prueba");
		collection = db.getCollection("ventas");
		gson = new GsonBuilder()
			.setPrettyPrinting()
        	.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        	.create();
	}
	
	public static void agregar(Venta venta) throws Exception {
		if(traer(venta.getNroTicket()) != null) {
			throw new Exception("ERROR: Ya existe una venta con este numero de ticket");
		}
		String json = gson.toJson(venta);
		collection.insert(BasicDBObject.parse(json));
	}
	
	public static void agregar(Venta[] ventas) throws Exception{
		for(Venta venta: ventas) {
			agregar(venta);
		}
	}
	
	public static Venta traer(int nroTicket) {
		DBObject result = collection.findOne( new BasicDBObject("nroTicket", nroTicket));
		if(result == null){
			return null;
		}
		return gson.fromJson(result.toString(), Venta.class);
	}

	public static ArrayList<Venta> traer() {
		DBCursor result = collection.find();
		if(result == null){
			return null;
		}
		ArrayList<Venta> ventas = new ArrayList<Venta>();
		if(result.hasNext()){
			DBObject obj = result.next();
			ventas.add(gson.fromJson(obj.toString(), Venta.class));
		}
		
		return ventas;
	}
	
	public static boolean modificar(Venta venta) {
		BasicDBObject query = new BasicDBObject("codigo", venta.getNroTicket());
		String json = gson.toJson(venta);
		WriteResult result = collection.update(query, BasicDBObject.parse(json));
		return result.getN() == 1;
	}
	
	public static boolean eliminar(Venta venta) {
		BasicDBObject query = new BasicDBObject("codigo", venta.getNroTicket());
		WriteResult result = collection.remove(query);
		return result.getN() == 1;
	}

	public static String PorSucursalYObraSocialEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta){
		// Filtro entre fechas
        DBObject cond = BasicDBObjectBuilder.start()
            .add("$gt", fechaDesde.toString())
            .add("$lt", fechaHasta.toString())
            .get();
        DBObject fields = new BasicDBObject("fecha", cond);
        DBObject match = new BasicDBObject("$match", fields);

        // Agrupo por surcursal y obra social
        DBObject group_id = new BasicDBObject("sucursal", "$sucursal.nombre");
        group_id.put("obra_social", "$cliente.obraSocial.nombre");
        fields = new BasicDBObject("_id", group_id);
        DBObject venta = new BasicDBObject("total", "$total");
        venta.put("detalle_venta", "$items");
        fields.put("ventas", new BasicDBObject("$addToSet", venta));
        DBObject group = new BasicDBObject("$group", fields);

        List<DBObject> myList = new ArrayList<DBObject>();
        myList.add(match);
        myList.add(group);
        

        List<DBObject> pipeline = myList;
        AggregationOutput output = collection.aggregate(pipeline);
		JsonElement jsonElement =  new JsonParser().parse(output.results().toString());
        return gson.toJson(jsonElement);
	}
}