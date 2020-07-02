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

	// Consulta 2
	public static String porSucursalYObraSocial(LocalDate fechaDesde, LocalDate fechaHasta) {
		// Filtro entre fechas
		DBObject cond = BasicDBObjectBuilder.start().add("$gt", fechaDesde.toString())
				.add("$lt", fechaHasta.toString()).get();
		DBObject fields = new BasicDBObject("fecha", cond);
		DBObject match = new BasicDBObject("$match", fields);

		// Unwind de items
		DBObject unwindItems = new BasicDBObject("$unwind", "$items");

		// Project de la informacion que me interesa
		DBObject detalle = new BasicDBObject("producto", "$items.producto.descripcion");
		detalle.put("precio", "$items.producto.precio");
		detalle.put("cantidad", "$items.cantidad");
		fields = new BasicDBObject("ticket", "$nroTicket");
		fields.put("sucursal", "$sucursal.nombre");
		fields.put("total", "$total");
		fields.put("obra_social", "$cliente.obraSocial.nombre");
		fields.put("detalle", detalle);
		DBObject project = new BasicDBObject("$project", fields);

		// Reagrupo con la informacion que tengo
		fields = new BasicDBObject("_id", new BasicDBObject("ticket", "$ticket"));
		fields.put("sucursal", new BasicDBObject("$addToSet", "$sucursal"));
		fields.put("obra_social", new BasicDBObject("$addToSet", "$obra_social"));
		fields.put("total", new BasicDBObject("$addToSet", "$total"));
		fields.put("detalle", new BasicDBObject("$addToSet", "$detalle"));
		DBObject groupUno = new BasicDBObject("$group", fields);

		// Hago unwind del total para sumarlo por sucursal
		DBObject unwindTotal = new BasicDBObject("$unwind", "$total");

		// Agrupo por surcursal y obra social
		DBObject group_id = new BasicDBObject("sucursal", "$sucursal");
		group_id.put("obra_social", "$obra_social");
		fields = new BasicDBObject("_id", group_id);
		fields.put("total", new BasicDBObject("$sum", "$total"));
		DBObject venta = new BasicDBObject("total", "$total");
		venta.put("detalle", "$detalle");
		fields.put("ventas", new BasicDBObject("$addToSet", venta));
		DBObject groupDos = new BasicDBObject("$group", fields);

		List<DBObject> myList = new ArrayList<DBObject>();
		myList.add(match);
		myList.add(unwindItems);
		myList.add(project);
		myList.add(groupUno);
		myList.add(unwindTotal);
		myList.add(groupDos);

		List<DBObject> pipeline = myList;
		AggregationOutput output = collection.aggregate(pipeline);
		JsonElement jsonElement = new JsonParser().parse(output.results().toString());
		return gson.toJson(jsonElement);
	}

	// Consulta 4
	public static String porSucursalYTipo(LocalDate fechaDesde, LocalDate fechaHasta) {
		// Filtro entre fechas
		DBObject cond = BasicDBObjectBuilder.start().add("$gt", fechaDesde.toString())
				.add("$lt", fechaHasta.toString()).get();
		DBObject fields = new BasicDBObject("fecha", cond);
		DBObject match = new BasicDBObject("$match", fields);

		// Unwind de items
		DBObject unwindItems = new BasicDBObject("$unwind", "$items");

		// Project de la informacion que me interesa
		DBObject detalle = new BasicDBObject("producto", "$items.producto.descripcion");
		detalle.put("tipo", "items.producto.tipo.nombre");
		detalle.put("precio", "$items.producto.precio");
		detalle.put("cantidad", "$items.cantidad");
		fields = new BasicDBObject("ticket", "$nroTicket");
		fields.put("sucursal", "$sucursal.nombre");
		fields.put("total", "$total");
		fields.put("obra_social", "$cliente.obraSocial.nombre");
		fields.put("detalle", detalle);
		DBObject project = new BasicDBObject("$project", fields);

		// Reagrupo con lo que interesa
		DBObject group_id = new BasicDBObject("sucursal", "$sucursal");
		group_id.put("ticket", "$ticket");
		fields = new BasicDBObject("_id", group_id);
		fields.put("total", new BasicDBObject("$addToSet", "$total"));
		fields.put("detalle", new BasicDBObject("$addToSet", "$detalle"));
		DBObject groupUno = new BasicDBObject("$group", fields);

		// Unwind de los totales
		DBObject unwindTotal = new BasicDBObject("$unwind", "$total");

		// Agrupo por sucursales
		fields = new BasicDBObject("_id", new BasicDBObject("sucursal", "$sucursal"));
		fields.put("total", new BasicDBObject("$sum", "$total"));
		DBObject ventas = new BasicDBObject("total", "$total");
		ventas.put("detalle", "$detalle");
		fields.put("ventas", ventas);
		DBObject groupDos = new BasicDBObject("$group", fields);

		List<DBObject> myList = new ArrayList<DBObject>();
		myList.add(match);
		myList.add(unwindItems);
		myList.add(project);
		myList.add(groupUno);
		myList.add(unwindTotal);
		myList.add(groupDos);

		List<DBObject> pipeline = myList;
		AggregationOutput output = collection.aggregate(pipeline);
		JsonElement jsonElement = new JsonParser().parse(output.results().toString());
		return gson.toJson(jsonElement);
	}

	// Consulta 6
	public static String rankingProductoPorCantidad(LocalDate fechaDesde, LocalDate fechaHasta) {
		// Filtro entre fechas
		DBObject cond = BasicDBObjectBuilder.start().add("$gt", fechaDesde.toString())
				.add("$lt", fechaHasta.toString()).get();
		DBObject fields = new BasicDBObject("fecha", cond);
		DBObject match = new BasicDBObject("$match", fields);

		// Unwind de items
		DBObject unwindItems = new BasicDBObject("$unwind", "$items");

		// Agrupo y sumo la cantidad
		DBObject group_id = new BasicDBObject("sucursal", "$sucursal.nombre");
		group_id.put("producto", "$items.producto.descripcion");
		fields = new BasicDBObject("_id", group_id);
		fields.put("cantidad", new BasicDBObject("$sum", "$items.cantidad"));
		DBObject groupUno = new BasicDBObject("$group", fields);

		// Agrupo por cada producto
		fields = new BasicDBObject("_id", new BasicDBObject("producto", "$_id.producto"));
		fields.put("total", new BasicDBObject("$sum", "$cantidad"));
		DBObject cantidadPorSucursal = new BasicDBObject("sucursal", "$_id.sucursal");
		cantidadPorSucursal.put("cantidad", "$cantidad");
		fields.put("cantidad_por_sucursal", new BasicDBObject("$addToSet", cantidadPorSucursal));
		DBObject groupDos = new BasicDBObject("$group", fields);

		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("total", 1));

		List<DBObject> myList = new ArrayList<DBObject>();
		myList.add(match);
		myList.add(unwindItems);
		myList.add(groupUno);
		myList.add(groupDos);
		myList.add(sort);

		List<DBObject> pipeline = myList;
		AggregationOutput output = collection.aggregate(pipeline);
		JsonElement jsonElement = new JsonParser().parse(output.results().toString());
		return gson.toJson(jsonElement);
	}

	// Consulta 8
	public static String rankingClientePorCantidad(LocalDate fechaDesde, LocalDate fechaHasta) {
		// Filtro entre fechas
		DBObject cond = BasicDBObjectBuilder.start().add("$gt", fechaDesde.toString())
				.add("$lt", fechaHasta.toString()).get();
		DBObject fields = new BasicDBObject("fecha", cond);
		DBObject match = new BasicDBObject("$match", fields);

		// Unwind de items
		DBObject unwindItems = new BasicDBObject("$unwind", "$items");

		// Agrupo y sumo la cantidad
		DBObject group_id = new BasicDBObject("sucursal", "$sucursal.nombre");
		group_id.put("cliente", "$cliente.dni");
		fields = new BasicDBObject("_id", group_id);
		fields.put("cantidad", new BasicDBObject("$sum", "$items.cantidad"));
		DBObject groupUno = new BasicDBObject("$group", fields);

		// Agrupo por cada cliente
		fields = new BasicDBObject("_id", new BasicDBObject("cliente", "$_id.cliente"));
		fields.put("total", new BasicDBObject("$sum", "$cantidad"));
		DBObject cantidadPorSucursal = new BasicDBObject("sucursal", "$_id.sucursal");
		cantidadPorSucursal.put("cantidad", "$cantidad");
		fields.put("cantidad_por_sucursal", new BasicDBObject("$addToSet", cantidadPorSucursal));
		DBObject groupDos = new BasicDBObject("$group", fields);

		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("total", 1));

		List<DBObject> myList = new ArrayList<DBObject>();
		myList.add(match);
		myList.add(unwindItems);
		myList.add(groupUno);
		myList.add(groupDos);
		myList.add(sort);

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
		projectFields.put("cliente DNI", "$_id.cliente.dni");
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

	/**
	 * ranking de productos por monto para la cadena completa entre fechas
	 **/
	public static String rankingDeProductosPorMonto(LocalDate fechaDesde, LocalDate fechaHasta) {
		return rankingDeProductosPorMonto(fechaDesde, fechaHasta, null);
	}

	/**
	 * ranking de productos por monto por sucursal entre fechas
	 **/
	public static String rankingDeProductosPorMonto(LocalDate fechaDesde, LocalDate fechaHasta, Sucursal sucursal) {
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
		groupfields.put("monto", new BasicDBObject("$sum",
				new BasicDBObject("$multiply", new String[] { "$items.producto.precio", "$items.cantidad" })));
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

	/**
	 * Detalles y totales de ventas para la cadena completa
	 **/
	public static String detallesYTotalesDeVentas(LocalDate fechaDesde, LocalDate fechaHasta) {
		return detallesYTotalesDeVentas(fechaDesde, fechaHasta, null);
	}

	/**
	 * Detalles y totales de ventas por sucursal entre fechas
	 **/
	public static String detallesYTotalesDeVentas(LocalDate fechaDesde, LocalDate fechaHasta, Sucursal sucursal) {
		// Filtro entre fechas y sucursal
		DBObject matchFields = new BasicDBObject();
		matchFields.put("fecha", BasicDBObjectBuilder.start().add("$gt", fechaDesde.toString())
				.add("$lt", fechaHasta.toString()).get());
		matchFields.put("sucursal.codigo",
				(sucursal == null) ? (new BasicDBObject("$exists", true)) : sucursal.getCodigo());
		DBObject match = new BasicDBObject("$match", matchFields);
		// Divido los documentos por cada item
		DBObject unwind = new BasicDBObject("$unwind", "$items");
		// Agrupo por sucursal, nroticket y total y enlisto sus detalles
		// Creo el _id del grupo
		DBObject groupId = new BasicDBObject("nroTicket", "$nroTicket");
		groupId.put("total", "$total");
		// Creo el set de detalles
		DBObject detallesSetFields = new BasicDBObject("producto", "$items.producto.descripcion");
		detallesSetFields.put("precio", "$items.producot.precio");
		detallesSetFields.put("cantidad", "$items.cantidad");
		DBObject detallesSet = new BasicDBObject("$addToSet", detallesSetFields);
		// Creo los campos del grupo
		DBObject groupfields = new BasicDBObject("_id", groupId);
		groupfields.put("detalles", detallesSet);
		DBObject group = new BasicDBObject("$group", groupfields);
		// Elijo lo que se va a mostrar
		DBObject projectFields = new BasicDBObject("_id", 0);
		projectFields.put("nroTicket", "$_id.nroTicket");
		projectFields.put("total", "$_id.total");
		projectFields.put("detalles", "$detalles");
		DBObject project = new BasicDBObject("$project", projectFields);
		// Realiza la consulta de agregacion
		List<DBObject> pipeline = Arrays.asList(match, unwind, group, project);
		AggregationOutput output = collection.aggregate(pipeline);
		JsonElement jsonElement = new JsonParser().parse(output.results().toString());
		return gson.toJson(jsonElement);
	}

	/**
	 * Detalles y totales de ventas para la cadena completa
	 **/
	public static String detallesYTotalesDeVentasPorMedioDePago(LocalDate fechaDesde, LocalDate fechaHasta) {
		return detallesYTotalesDeVentasPorMedioDePago(fechaDesde, fechaHasta, null);
	}

	/**
	 * Detalles y totales de ventas por sucursal entre fechas
	 **/
	public static String detallesYTotalesDeVentasPorMedioDePago(LocalDate fechaDesde, LocalDate fechaHasta,
			Sucursal sucursal) {
		// Filtro entre fechas y sucursal
		DBObject matchFields = new BasicDBObject();
		matchFields.put("fecha", BasicDBObjectBuilder.start().add("$gt", fechaDesde.toString())
				.add("$lt", fechaHasta.toString()).get());
		matchFields.put("sucursal.codigo",
				(sucursal == null) ? (new BasicDBObject("$exists", true)) : sucursal.getCodigo());
		DBObject match = new BasicDBObject("$match", matchFields);
		// Divido los documentos por cada item
		DBObject unwind = new BasicDBObject("$unwind", "$items");
		// Agrupo por sucursal, nroticket y total y enlisto sus detalles
		// Creo el _id del grupo
		DBObject groupId = new BasicDBObject("nroTicket", "$nroTicket");
		groupId.put("total", "$total");
		groupId.put("medio_de_pago", "$formaDePago");
		// Creo el set de detalles
		DBObject detallesSetFields = new BasicDBObject("producto", "$items.producto.descripcion");
		detallesSetFields.put("precio", "$items.producot.precio");
		detallesSetFields.put("cantidad", "$items.cantidad");
		DBObject detallesSet = new BasicDBObject("$addToSet", detallesSetFields);
		// Creo los campos del grupo
		DBObject groupfields = new BasicDBObject("_id", groupId);
		groupfields.put("detalles", detallesSet);
		DBObject group = new BasicDBObject("$group", groupfields);

		// Agrupo por medio de pago y enlisto sus detalles
		// Creo el _id del grupo
		DBObject groupId2 = new BasicDBObject("medio_de_pago", "$_id.medio_de_pago.nombre");
		// Creo el set de detalles
		DBObject detallesSetFields2 = new BasicDBObject("nroTicket", "$_id.nroTicket");
		detallesSetFields2.put("total", "$_id.total");
		detallesSetFields2.put("detalles", "$detalles");
		DBObject detallesSet2 = new BasicDBObject("$addToSet", detallesSetFields2);
		// Creo los campos del grupo
		DBObject groupfields2 = new BasicDBObject("_id", groupId2);
		groupfields2.put("detalles", detallesSet2);
		DBObject group2 = new BasicDBObject("$group", groupfields2);
		// Elijo lo que se va a mostrar
		DBObject projectFields = new BasicDBObject("_id", 0);
		projectFields.put("medio_de_pago", "$_id.medio_de_pago");
		projectFields.put("ventas", "$detalles");
		DBObject project = new BasicDBObject("$project", projectFields);
		// Realiza la consulta de agregacion
		List<DBObject> pipeline = Arrays.asList(match, unwind, group, group2, project);
		AggregationOutput output = collection.aggregate(pipeline);
		JsonElement jsonElement = new JsonParser().parse(output.results().toString());
		return gson.toJson(jsonElement);
	}

}