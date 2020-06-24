package cargaBD;

import java.util.logging.Logger;

import com.bd2.ConnectorDB;

import dao.ClienteDao;
import dao.EmpleadoDao;
import dao.FormaDePagoDao;
import dao.LaboratorioDao;
import dao.ObraSocialDao;
import dao.ProductoDao;
import dao.SucursalDao;
import dao.TipoDeProductoDao;
import datos.Cliente;
import datos.Domicilio;
import datos.Empleado;
import datos.FormaDePago;
import datos.Laboratorio;
import datos.ObraSocial;
import datos.Producto;
import datos.TipoDeProducto;
import datos.Sucursal;

public class CargaBD {

	//private final static Logger LOGGER = Logger.getLogger(CargaBD.class.getName());
	
	// Método de carga de la BD con los datos de prueba
	public static void main(String[] args) {
		try {
			ConnectorDB.getDatabase("prueba").dropDatabase();
			/*
			 * Instancia DAOs
			 */
			ObraSocialDao.getInstance();
			ClienteDao.getInstance();
			EmpleadoDao.getInstance();
			LaboratorioDao.getInstance();
			TipoDeProductoDao.getInstance();
			ProductoDao.getInstance();
			FormaDePagoDao.getInstance();
			SucursalDao.getInstance();
			/*
			 *  Obras sociales
			 */
			ObraSocial ospm = new ObraSocial("30598615825", "OSPM");
			ObraSocial ospf = new ObraSocial("33648104389", "OSPF");
			ObraSocial osmedica = new ObraSocial("30707746463", "OSMEDICA");
			ObraSocialDao.agregar(ospm);
			ObraSocialDao.agregar(ospf);
			ObraSocialDao.agregar(osmedica);
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
					new Domicilio("Mitre", 501, "Quilmes", "Buenos Aires"), osmedica);
			Cliente c5 = new Cliente(10000005, "Elena", "Epo", 2, 
					new Domicilio("Av. Nestor Kirchner", 125, "Lanus", "Buenos Aires"), osmedica);
			Cliente c6 = new Cliente(10000006, "Francisco", "Figueroa", 3, 
					new Domicilio("Pje. Cuenca", 212, "Lanus", "Buenos Aires"), osmedica);
			Cliente c7 = new Cliente(10000007, "Gustavo", "Guimenez", 4, 
					new Domicilio("Pje. Cuenca", 216, "Lanus", "Buenos Aires"), ospm);
			Cliente c8 = new Cliente(10000008, "Horacio", "Heredia", 6, 
					new Domicilio("Pje. Valle", 1001, "Lanus", "Buenos Aires"), osmedica);
			Cliente c9 = new Cliente(10000009, "Ignacio", "Iriarte", 7, 
					new Domicilio("Pje. Valle", 1001, "Lanus", "Buenos Aires"), ospm);
			Cliente c10 = new Cliente(10000010, "Jiovanna", "Juarez", 8, 
					new Domicilio("12 de Octubre", 110, "Quilmes", "Buenos Aires"), ospf);
			Cliente [] clientes = {c1,c2,c3,c4,c5,c6,c7,c8,c9,c10};
			ClienteDao.agregar(clientes);
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
			Empleado [] empleados = {e1,e2,e3,e4,e5,e6,e7,e8,e9};
			EmpleadoDao.agregar(empleados);
			/*
			 *  Laboratorios
			 */
			Laboratorio bayer = new Laboratorio("10300000011", "Bayer");
			Laboratorio novartis = new Laboratorio("10300000021", "Novartis");
			Laboratorio avon = new Laboratorio("10300000031", "Avon");
			Laboratorio natura = new Laboratorio("10300000041", "Natura");
			Laboratorio [] laboratorios = {bayer, novartis, avon, natura};
			LaboratorioDao.agregar(laboratorios);
			/*
			 *  Tipos de Productos
			 */
			TipoDeProducto farmacia = new TipoDeProducto(1, "Farmacia");
			TipoDeProducto perfumeria = new TipoDeProducto(2, "Perfumeria");
			TipoDeProductoDao.agregar(farmacia);
			TipoDeProductoDao.agregar(perfumeria);
			/*
			 *  Productos
			 */
			Producto p1 = new Producto(1, "Bayaspirina", 60.0f, bayer, farmacia);
			Producto p2 = new Producto(2, "Ibuprofeno", 80.0f, bayer, farmacia);
			Producto p3 = new Producto(3, "Actron", 100.0f, bayer, farmacia);
			Producto p4 = new Producto(4, "Loratadina", 110.0f, novartis, farmacia);
			Producto p5 = new Producto(5, "Calcium", 200.0f, novartis, farmacia);
			Producto p6 = new Producto(6, "Misoprostol", 500.0f, novartis, farmacia);
			Producto p7 = new Producto(7, "Clonazepam", 450.0f, novartis, farmacia);
			Producto p8 = new Producto(8, "Jabon Eko", 300.0f, avon, perfumeria);
			Producto p9 = new Producto(9, "Shampoo", 250.0f, avon, perfumeria);
			Producto p10 = new Producto(10, "Cepillo", 100.0f, natura, perfumeria);
			Producto [] productos = {p1,p2,p3,p4,p5,p6,p7,p8,p9,p10};
			ProductoDao.agregar(productos);
			/*
			 *  Formas de Pago
			 */
			FormaDePago efectivo = new FormaDePago(1, "EFECTIVO");
			FormaDePago credito = new FormaDePago(2, "CREDITO");
			FormaDePago debito = new FormaDePago(3, "DEBITO");
			FormaDePagoDao.agregar(efectivo);
			FormaDePagoDao.agregar(credito);
			FormaDePagoDao.agregar(debito);
			/*
			 *  Sucursal (falta: empleados y ventas)
			 */
			Sucursal s1 = new Sucursal(1, "Sucursal Quilmes", new Domicilio("Av. Mitre", 555, "Quilmes", "Buenos Aires"));
			Sucursal s2 = new Sucursal(2, "Sucursal La Plata", new Domicilio("Av. 10", 10, "La Plata", "Buenos Aires"));
			Sucursal s3 = new Sucursal(3, "Sucursal Once", new Domicilio("Av. Puerreydon", 12, "CABA", "CABA"));
			SucursalDao.agregar(s1);
			SucursalDao.agregar(s2);
			SucursalDao.agregar(s3);		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
