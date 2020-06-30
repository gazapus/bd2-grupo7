package dao;

import java.util.ArrayList;
import java.util.Arrays;
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

import pojos.Sucursal;
import pojos.Venta;

public class VentaDao {
	private static DBCollection collection;
	private static Gson gson;
	private static DB db;

	public static void getInstance() throws UnknownHostException {
		db = ConnectorDB.getDatabase("farmacia");
		collection = db.getCollection("ventas");
		gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
	}

	public static void agregar(Venta venta) throws Exception {
		if (traer(venta.getNroTicket()) != null) {
			throw new Exception("ERROR: Ya existe una venta con este numero de ticket");
		}
		String json = gson.toJson(venta);
		collection.insert(BasicDBObject.parse(json));
	}

	public static void agregar(Venta[] ventas) throws Exception {
		for (Venta venta : ventas) {
			agregar(venta);
		}
	}

	public static Venta traer(String nroTicket) {
		DBObject result = collection.findOne(new BasicDBObject("nroTicket", nroTicket));
		if (result == null) {
			return null;
		}
		return gson.fromJson(result.toString(), Venta.class);
	}

	public static ArrayList<Venta> traer() {
		DBCursor result = collection.find();
		if (result == null) {
			return null;
		}
		ArrayList<Venta> ventas = new ArrayList<Venta>();
		if (result.hasNext()) {
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

	public static String PorSucursalYObraSocialEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
		// Filtro entre fechas
		DBObject cond = BasicDBObjectBuilder.start().add("$gt", fechaDesde.toString())
				.add("$lt", fechaHasta.toString()).get();
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
		JsonElement jsonElement = new JsonParser().parse(output.results().toString());
		return gson.toJson(jsonElement);
	}

	// Ranking de clientes del total de la cadena, por monto y entre fechas
	public static String rankingDeClientesPorMonto(LocalDate fechaDesde, LocalDate fechaHasta) {
		return rankingDeClientesPorMonto(fechaDesde, fechaHasta, null);
	}

	// Ranking de clientes por sucursal, monto y fechas
	public static String rankingDeClientesPorMonto(LocalDate fechaDesde, LocalDate fechaHasta, Sucursal sucursal) {
		// Filtro entre fechas y sucursal
		DBObject matchFields = new BasicDBObject();
		matchFields.put("fecha", BasicDBObjectBuilder.start().add("$gt", fechaDesde.toString())
				.add("$lt", fechaHasta.toString()).get());
		matchFields.put("sucursal.codigo",
				(sucursal == null) ? (new BasicDBObject("$exists", true)) : sucursal.getCodigo());
		DBObject match = new BasicDBObject("$match", matchFields);
		// Agrupo por cliente y sumar total
		DBObject groupfields = new BasicDBObject("_id", new BasicDBObject("cliente", "$cliente"));
		groupfields.put("monto", new BasicDBObject("$sum", "$total"));
		DBObject group = new BasicDBObject("$group", groupfields);
		// Elijo lo que se va a mostrar
		DBObject projectFields = new BasicDBObject("_id", 0);
		projectFields.put("cliente", "$_id.cliente.dni");
		projectFields.put("monto", 1);
		DBObject project = new BasicDBObject("$project", projectFields);
		// Ordeno por monto
		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("monto", -1));
		// Realiza la consulta de agregacion
		List<DBObject> pipeline = Arrays.asList(match, group, project, sort);
		AggregationOutput output = collection.aggregate(pipeline);
		JsonElement jsonElement = new JsonParser().parse(output.results().toString());
		return gson.toJson(jsonElement);
	}

	public static String rankingDeProductosPorMonto(LocalDate fechaDesde, LocalDate fechaHasta){
		return rankingDeProductosPorMonto(fechaDesde, fechaHasta, null);
	}

	public static String rankingDeProductosPorMonto(LocalDate fechaDesde, LocalDate fechaHasta, Sucursal sucursal){
		// Filtro entre fechas y sucursal
		DBObject matchFields = new BasicDBObject();
		matchFields.put("fecha", BasicDBObjectBuilder.start().add("$gt", fechaDesde.toString())
				.add("$lt", fechaHasta.toString()).get());
		matchFields.put("sucursal.codigo",
				(sucursal == null) ? (new BasicDBObject("$exists", true)) : sucursal.getCodigo());
		DBObject match = new BasicDBObject("$match", matchFields);
		// Divido los documentos por cada item
		DBObject unwind = new BasicDBObject("$unwind", "$items");
		// Agrupo por producto y monto
		DBObject groupfields = new BasicDBObject("_id", new BasicDBObject("producto", "$items.producto"));
		groupfields.put("monto", new BasicDBObject("$sum", new BasicDBObject(
			"$multiply", new String [] { "$items.producto.precio", "$items.cantidad" })));
		DBObject group = new BasicDBObject("$group", groupfields);
		// Elijo lo que se va a mostrar
		DBObject projectFields = new BasicDBObject("_id", 0);
		projectFields.put("producto", "$_id.producto.descripcion");
		projectFields.put("monto", 1);
		DBObject project = new BasicDBObject("$project", projectFields);
		// Ordeno por monto
		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("monto", -1));
		// Realiza la consulta de agregacion
		List<DBObject> pipeline = Arrays.asList(match, unwind, group, project, sort);
		AggregationOutput output = collection.aggregate(pipeline);
		JsonElement jsonElement = new JsonParser().parse(output.results().toString());
		return gson.toJson(jsonElement);
	}
}