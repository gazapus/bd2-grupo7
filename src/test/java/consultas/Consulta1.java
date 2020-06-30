package consultas;

import java.time.LocalDate;

import dao.VentaDao;
import pojos.Sucursal;

public class Consulta1 {
     public static void main(String[] args) {
          LocalDate fechaDesde = LocalDate.of(2020, 6, 12);
          LocalDate fechaHasta = LocalDate.now();
          try {
               VentaDao.getInstance();
               // toda la cadena
               String respuesta = VentaDao.detallesYTotalesDeVentas(fechaDesde, fechaHasta);
               System.out.println("Totales y detalles de ventas para toda la cadena");
               System.out.println(respuesta);
               // sucursal 1 (debería traerla desde la base de datos pero no está contemplado en este TP)
               Sucursal sucursal1 = new Sucursal();  
               sucursal1.setCodigo(1);
               String respuesta2 = VentaDao.detallesYTotalesDeVentas(fechaDesde, fechaHasta, sucursal1);
               System.out.println("\nTotales y detalles de ventas para la sucursal 1");
               System.out.println(respuesta2);
          } catch (Exception e) {
               e.printStackTrace();
          }
     }
}