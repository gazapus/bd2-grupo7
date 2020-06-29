package cargaBD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bd2.ConnectorDB;

import dao.VentaDao;
import datos.Cliente;
import datos.Domicilio;
import datos.Empleado;
import datos.FormaDePago;
import datos.Item;
import datos.Laboratorio;
import datos.ObraSocial;
import datos.Producto;
import datos.TipoDeProducto;
import datos.Venta;
import datos.Sucursal;

public class CargaBD {

	/**
     * Genera un numero entero aleatorio en un rango
     * @param desde numero desde el cual se podrá generar (incluido)
     * @param hasta numero hasta el cual se generará (excluido)
     * @return numero aleatorio 
     */
	public static int aleatorioEntre(int desde, int hasta) {
		return (int) Math.floor(Math.random()*hasta + desde);
	}

	/**
     * Genera ventas de una sucursal
     * @param empleados lista de empleados de la sucursal que haran las ventas
     * @param clientes lista de clientes que efecturan la venta
     * @param formasDePago lista de medios de pagos que dispondrá la sucursal
     * @param productos lista de productos que vende en la sucursal
     * @param ventasMensuales cantidad de ventas por mes a generar
     * @param meses cantidad de meses, partiendo del mes 1 (enero)
     * @param nroTicketActual ultimo numero de ticket vendido
     * @return una nueva lista de ventas generará aletoriamente
    **/
     public static void generarVentas(
		 	Sucursal sucursal,
			List<Empleado> empleados, 
			Cliente[] clientes, 
			FormaDePago[] formasDePago,
			Producto [] productos,
			int ventasMensuales,
			int meses,
			int nroTicketActual)
	{
		for(int mes=1; mes<=meses; mes++) {
			for(int j=0; j<ventasMensuales; j++) {
				nroTicketActual++;
				LocalDate fecha = LocalDate.of(2020, mes, aleatorioEntre(1, 28));
				FormaDePago pago = formasDePago[ aleatorioEntre(0, formasDePago.length)];
				Empleado vendedor = empleados.get( aleatorioEntre(0, empleados.size()) );
				Empleado cobrador = empleados.get( aleatorioEntre(0, empleados.size()) );
				Cliente cliente = clientes[ aleatorioEntre(0, clientes.length) ];
				List<Item> items = new ArrayList<Item>();
				int cantidadItems = aleatorioEntre(1, 4);	// Se cargarán de 1 a 3 items por venta
				for(int k=0; k<cantidadItems; k++) {
					Producto producto = productos[ aleatorioEntre(0, productos.length) ];
					items.add(new Item(producto, aleatorioEntre(1,4) ));	// Se cargarán hasta 3 unidades por producto
				}
				try {
					VentaDao.agregar( new Venta(nroTicketActual, fecha, pago, vendedor, cobrador, cliente, sucursal, items));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}	
	}

	// Método de carga de la BD con los datos de farmacia
	public static void main(String[] args) {
		try {
			ConnectorDB.getDatabase("farmacia").dropDatabase();
			/*
			 * Instancia DAOs
			 */
			VentaDao.getInstance();
			/*
			 *  Obras sociales
			 */
			ObraSocial ospm = new ObraSocial("30598615825", "OSPM");
			ObraSocial ospf = new ObraSocial("33648104389", "OSPF");
			ObraSocial osmedica = new ObraSocial("30707746463", "OSMEDICA");
			/*
			 *  Clientes
			 */
			Cliente c1 = new Cliente(10000001, "Angel", "Alvarez", 142, 
					new Domicilio("Calle 114", 10, "La Plata", "Buenos Aires"), ospm);
			Cliente c2 = new Cliente(10000002, "Bartolomeo", "Bisagra", 143, 
					new Domicilio("Calle 22", 55, "La Plata", "Buenos Aires"), ospm);
			Cliente c3 = new Cliente(10000003, "Carlos", "Caceres", 144, 
					new Domicilio("Calle 22", 13, "La Plata", "Buenos Aires"), ospm);
			Cliente c4 = new Cliente(10000004, "Daniela", "Diaz", 145, 
					new Domicilio("Mitre", 501, "CABA", "CABA"), osmedica);
			Cliente c5 = new Cliente(10000005, "Elena", "Epo", 2, 
					new Domicilio("Av. Nestor Kirchner", 125, "CABA", "CABA"), osmedica);
			Cliente c6 = new Cliente(10000006, "Francisco", "Figueroa", 3, 
					new Domicilio("Pje. Cuenca", 212, "CABA", "CABA"), osmedica);
			Cliente c7 = new Cliente(10000007, "Gustavo", "Guimenez", 4, 
					new Domicilio("Pje. Cuenca", 216, "Quilmes", "Buenos Aires"), ospm);
			Cliente c8 = new Cliente(10000008, "Horacio", "Heredia", 6, 
					new Domicilio("Pje. Valle", 1001, "Quilmes", "Buenos Aires"), osmedica);
			Cliente c9 = new Cliente(10000009, "Ignacio", "Iriarte", 7, 
					new Domicilio("Pje. Valle", 1001, "Quilmes", "Buenos Aires"), ospm);
			Cliente c10 = new Cliente(10000010, "Jiovanna", "Juarez", 8, 
					new Domicilio("12 de Octubre", 110, "Lanus", "Buenos Aires"), ospf);
			/*
			 *  Empleados
			 */
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
			Empleado e6 = new Empleado(20000006, "Fatima", "Fernandez", 88, 
					new Domicilio("Libertad", 12, "CABA", "CABA"), ospf, "10200000061", true);
			Empleado e7 = new Empleado(20000007, "Gustavo", "Garcia", 1232, 
					new Domicilio("Calle 123", 12, "Lanus", "Buenos Aires"), ospf, "10200000071", false);
			Empleado e8 = new Empleado(20000008, "Hilda", "Heredia", 221, 
					new Domicilio("Calle 123", 12, "Lanus", "Buenos Aires"), ospf, "10200000081", false);
			Empleado e9 = new Empleado(20000009, "Isidro", "Ibañez", 983, 
					new Domicilio("Sarmiento", 12, "CABA", "CABA"), osmedica, "10200000091", true);
			/*
			 *  Laboratorios
			 */
			Laboratorio bayer = new Laboratorio("10300000011", "Bayer");
			Laboratorio novartis = new Laboratorio("10300000021", "Novartis");
			Laboratorio avon = new Laboratorio("10300000031", "Avon");
			Laboratorio natura = new Laboratorio("10300000041", "Natura");
			/*
			 *  Tipos de Productos
			 */
			TipoDeProducto farmacia = new TipoDeProducto(1, "Farmacia");
			TipoDeProducto perfumeria = new TipoDeProducto(2, "Perfumeria");
			/*
			 *  Productos
			 */
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
			Producto [] productos = {p1,p2,p3,p4,p5,p6,p7,p8,p9,p10};
			/*
			 *  Formas de Pago
			 */
			FormaDePago efectivo = new FormaDePago(1, "EFECTIVO");
			FormaDePago credito = new FormaDePago(2, "CREDITO");
			FormaDePago debito = new FormaDePago(3, "DEBITO");
			FormaDePago [] formasDePago = {efectivo, credito, debito};			
			/*
			 *  Sucursales con sus ventas
			 */
			
			 // Sucursal 1:
			List<Empleado> empleadosSuc1 = Arrays.asList(e1, e2, e3);
			
			Sucursal s1 = new Sucursal(1, "Sucursal La Plata", 
					new Domicilio("Calle 110", 123, "La Plata", "Buenos Aires"), empleadosSuc1);
			generarVentas(s1, empleadosSuc1, new Cliente[] {c1, c2, c3, c10}, formasDePago, productos, 10, 3, 0);
			
			// Sucursal 2:
			List<Empleado> empleadosSuc2 = Arrays.asList(e4, e5, e6);
			
			Sucursal s2 = new Sucursal(2, "Sucursal Quilmes", new Domicilio("Mitre", 200, "Quilmes", "Buenos Aires"),
					empleadosSuc2);
			generarVentas(s2, empleadosSuc2, new Cliente[] {c4, c5, c6, c10}, formasDePago, productos, 8, 3, 30);
			
			// Sucursal 2:
			List<Empleado> empleadosSuc3 = Arrays.asList(e7, e8, e9);
			Sucursal s3 = new Sucursal(3, "Sucursal Once", new Domicilio("Av. Puerreydon", 12, "CABA", "CABA"),
					empleadosSuc3);
			generarVentas(s3, empleadosSuc3, new Cliente[] {c7, c8, c9, c10}, formasDePago, productos, 12, 3, 54);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
