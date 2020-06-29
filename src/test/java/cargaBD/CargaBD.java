package cargaBD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import pojos.Cliente;
import pojos.Domicilio;
import pojos.Empleado;
import pojos.FormaDePago;
import pojos.Item;
import pojos.Laboratorio;
import pojos.ObraSocial;
import pojos.Producto;
import pojos.Sucursal;
import pojos.TipoDeProducto;
import pojos.Venta;
import util.LocalDateAdapter;

public class CargaBD {

	/**
	 * Genera un numero entero aleatorio entero en un rango
	 * 
	 * @param desde numero desde el cual se podrá generar (incluido)
	 * @param hasta numero hasta el cual se generará (excluido)
	 * @return numero aleatorio
	 */
	public static int numeroAleatorio(int desde, int hasta) {
		return (int) Math.floor(Math.random() * hasta + desde);
	}

	/**
	 * Genera una fecha aleatoria entre dos fechas
	 * 
	 * @param desde fecha desde la cual se genera
	 * @param hasta fecha hasta la cual se genera
	 * @return una fecha que se encuentra en el rango recibido
	 */
	public static LocalDate generarFechaAleatoria(LocalDate desde, LocalDate hasta) {
		long minDay = desde.toEpochDay();
		long maxDay = hasta.toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		return LocalDate.ofEpochDay(randomDay);
	}

	/**
	 * Genera una lista de ventas aleatorias para una sucursal
	 * 
	 * @param sucursal     sucursal para la cual se generan las ventas
	 * @param clientes     clientes que efecturan las compras
	 * @param formasDePago medios de pagos con los que se pagarán las compras
	 * @param productos    lista de productos que se venderan
	 * @param desde        fecha desde la cual se generan las ventas
	 * @param hasta        fecha hasta la cual se generan las ventas
	 * @param cantidad     cantidad de ventas a generar
	 * @return nueva lista de ventas
	 **/
	public static List<Venta> generarVentas(Sucursal sucursal, Cliente[] clientes, FormaDePago[] formasDePago,
			Producto[] productos, LocalDate desde, LocalDate hasta, int cantidadDeVentas) {
		List<Venta> ventas = new ArrayList<Venta>();
		for (int i = 0; i < cantidadDeVentas; i++) {
			Empleado atencion = sucursal.getEmpleados().get(numeroAleatorio(0, sucursal.getEmpleados().size()));
			Empleado cobro = sucursal.getEmpleados().get(numeroAleatorio(0, sucursal.getEmpleados().size()));
			Cliente cliente = clientes[numeroAleatorio(0, clientes.length)];
			FormaDePago formaDePago = formasDePago[numeroAleatorio(0, formasDePago.length)];
			LocalDate fecha = generarFechaAleatoria(desde, hasta);
			List<Item> items = new ArrayList<Item>();
			// Genera desde 1 hasta 3 items por ventas
			for (int j = 0; j < numeroAleatorio(1, 4); j++) {
				Producto producto = productos[numeroAleatorio(0, productos.length)];
				int cantidadDeProducto = numeroAleatorio(1, 4); // carga desde 1 hasta 3 unidades por producto
				items.add(new Item(producto, cantidadDeProducto));
			}
			String nroDeTicket = String.valueOf(sucursal.getCodigo()) + "-" + String.valueOf(i);
			ventas.add(new Venta(nroDeTicket, fecha, formaDePago, atencion, cobro, sucursal, cliente, items));
		}
		return ventas;
	}

	// Método de carga de la DB con los datos de farmacia
	public static void main(String[] args) {
		// Obras sociales
		ObraSocial ospm = new ObraSocial("30598615825", "OSPM");
		ObraSocial ospf = new ObraSocial("33648104389", "OSPF");
		ObraSocial osmedica = new ObraSocial("30707746463", "OSMEDICA");
		ObraSocial privado = new ObraSocial("0", "PRIVADO");
		// Clientes
		Cliente c1 = new Cliente(10000001, "Angel", "Alvarez", 142,
				new Domicilio("Calle 114", 10, "La Plata", "Buenos Aires"), ospm);
		Cliente c2 = new Cliente(10000002, "Bartolomeo", "Bisagra", 143,
				new Domicilio("Calle 22", 55, "La Plata", "Buenos Aires"), ospm);
		Cliente c3 = new Cliente(10000003, "Carlos", "Caceres", 144,
				new Domicilio("Calle 22", 13, "La Plata", "Buenos Aires"), privado);
		Cliente c4 = new Cliente(10000004, "Daniela", "Diaz", 145, new Domicilio("Mitre", 501, "CABA", "CABA"),
				osmedica);
		Cliente c5 = new Cliente(10000005, "Elena", "Epo", 2,
				new Domicilio("Av. Nestor Kirchner", 125, "CABA", "CABA"), osmedica);
		Cliente c6 = new Cliente(10000006, "Francisco", "Figueroa", 3,
				new Domicilio("Pje. Cuenca", 212, "CABA", "CABA"), privado);
		Cliente c7 = new Cliente(10000007, "Gustavo", "Guimenez", 4,
				new Domicilio("Pje. Cuenca", 216, "Quilmes", "Buenos Aires"), ospm);
		Cliente c8 = new Cliente(10000008, "Horacio", "Heredia", 6,
				new Domicilio("Pje. Valle", 1001, "Quilmes", "Buenos Aires"), osmedica);
		Cliente c9 = new Cliente(10000009, "Ignacio", "Iriarte", 7,
				new Domicilio("Pje. Valle", 1001, "Quilmes", "Buenos Aires"), ospm);
		Cliente c10 = new Cliente(10000010, "Jiovanna", "Juarez", 8,
				new Domicilio("12 de Octubre", 110, "Lanus", "Buenos Aires"), ospf);
		// Empleados: 3 empleados por sucursal
		Empleado e1 = new Empleado(20000001, "Aurelio", "Alvarez", 499,
				new Domicilio("Calle 113", 12, "La Plata", "Buenos Aires"), ospf, "10200000011", false);
		Empleado e2 = new Empleado(20000002, "Benito", "Barbaro", 200,
				new Domicilio("Calle 123", 122, "La Plata", "Buenos Aires"), ospf, "10200000021", false);
		Empleado e3 = new Empleado(20000003, "Celeste", "Cirez", 123,
				new Domicilio("Calle 113", 11, "Quilmes", "Buenos Aires"), ospf, "10200000031", true);
		Empleado e4 = new Empleado(20000004, "Damian", "Alvarez", 111,
				new Domicilio("Calle 43", 10, "Quilmes", "Buenos Aires"), ospf, "10200000041", false);
		Empleado e5 = new Empleado(20000005, "Elias", "Estevez", 99,
				new Domicilio("Calle 198", 33, "La Plata", "Buenos Aires"), ospf, "10200000051", false);
		Empleado e6 = new Empleado(20000006, "Fatima", "Fernandez", 88, new Domicilio("Libertad", 12, "CABA", "CABA"),
				ospf, "10200000061", true);
		Empleado e7 = new Empleado(20000007, "Gustavo", "Garcia", 1232,
				new Domicilio("Calle 123", 12, "Lanus", "Buenos Aires"), ospf, "10200000071", false);
		Empleado e8 = new Empleado(20000008, "Hilda", "Heredia", 221,
				new Domicilio("Calle 123", 12, "Lanus", "Buenos Aires"), ospf, "10200000081", false);
		Empleado e9 = new Empleado(20000009, "Isidro", "Ibañez", 983, new Domicilio("Sarmiento", 12, "CABA", "CABA"),
				osmedica, "10200000091", true);
		// Laboratorios
		Laboratorio bayer = new Laboratorio("10300000011", "Bayer");
		Laboratorio novartis = new Laboratorio("10300000021", "Novartis");
		Laboratorio avon = new Laboratorio("10300000031", "Avon");
		Laboratorio natura = new Laboratorio("10300000041", "Natura");
		// Tipos de productos
		TipoDeProducto farmacia = new TipoDeProducto(1, "Farmacia");
		TipoDeProducto perfumeria = new TipoDeProducto(2, "Perfumeria");
		// Productos
		Producto p1 = new Producto(1, "Bayaspirina", 100.0f, bayer, farmacia);
		Producto p2 = new Producto(2, "Ibuprofeno", 200.0f, bayer, farmacia);
		Producto p3 = new Producto(3, "Actron", 300.0f, bayer, farmacia);
		Producto p4 = new Producto(4, "Loratadina", 400.0f, novartis, farmacia);
		Producto p5 = new Producto(5, "Calcium", 500.0f, novartis, farmacia);
		Producto p6 = new Producto(6, "Misoprostol", 100.0f, novartis, farmacia);
		Producto p7 = new Producto(7, "Clonazepam", 200.0f, novartis, farmacia);
		Producto p8 = new Producto(8, "Jabon Eko", 300.0f, avon, perfumeria);
		Producto p9 = new Producto(9, "Shampoo", 400.0f, avon, perfumeria);
		Producto p10 = new Producto(10, "Cepillo", 500.0f, natura, perfumeria);
		Producto[] productos = { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10 };
		// Formas de pago
		FormaDePago efectivo = new FormaDePago(1, "EFECTIVO");
		FormaDePago credito = new FormaDePago(2, "CREDITO");
		FormaDePago debito = new FormaDePago(3, "DEBITO");
		FormaDePago[] formasDePago = { efectivo, credito, debito };
		// Sucursales
		Sucursal s1 = new Sucursal(1, "Sucursal La Plata",
				new Domicilio("Calle 110", 123, "La Plata", "Buenos Aires"), Arrays.asList(e1, e2, e3));
		Sucursal s2 = new Sucursal(2, "Sucursal Quilmes", new Domicilio("Mitre", 200, "Quilmes", "Buenos Aires"),
				Arrays.asList(e4, e5, e6));
		Sucursal s3 = new Sucursal(3, "Sucursal Once", new Domicilio("Av. Puerreydon", 12, "CABA", "CABA"),
				Arrays.asList(e7, e8, e9));
		// Ventas: van desde enero del 2020 hasta la fecha actual
		List<Venta> ventas = new ArrayList<Venta>();
		// 30 ventas de sucursal 1
		ventas.addAll(generarVentas(s1, new Cliente[] { c1, c2, c3, c10 }, formasDePago, productos,
				LocalDate.of(2020, 01, 01), LocalDate.now(), 30));
		// 25 ventas de sucursal 2
		ventas.addAll(generarVentas(s2, new Cliente[] { c4, c5, c6, c10 }, formasDePago, productos,
				LocalDate.of(2020, 01, 01), LocalDate.now(), 25));
		// 35 ventas de sucursal 3
		ventas.addAll(generarVentas(s3, new Cliente[] { c7, c8, c9, c10 }, formasDePago, productos,
				LocalDate.of(2020, 01, 01), LocalDate.now(), 35));

		// Guarda las ventas como una colección en la base de datos
		try {
			String uri = "mongodb://localhost:27017";
			MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
			mongoClient.dropDatabase("farmacia"); // Elimina la base de datos si existe
			MongoDatabase db = mongoClient.getDatabase("farmacia");
			MongoCollection <Document> collection = db.getCollection("ventas");
			Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();			
			for(Venta venta: ventas) {
			String jsonVenta = gson.toJson(venta);
			Document docVenta = Document.parse(jsonVenta);
			collection.insertOne(docVenta);
			}
			System.out.println("\nCarga completa");
		} 
		catch (Exception e) {
			System.out.println("\nError al cargar los datos");
			e.printStackTrace();
		}
	}
}
