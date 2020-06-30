package consultas;

import java.time.LocalDate;

import dao.VentaDao;
import pojos.Sucursal;

public class Consulta5 {
     public static void main(String[] args) {
          // Se traen solamente la descripcion de los productos y su monto, para favorecer la legibilidad del resultado
          LocalDate fechaDesde = LocalDate.of(2020, 1, 1);
          LocalDate fechaHasta = LocalDate.now();
          try {
               VentaDao.getInstance();
               // toda la cadena
               String respuesta = VentaDao.rankingDeProductosPorMonto(fechaDesde, fechaHasta);
               System.out.println("RANKING DE PRODUCTOS POR MONTO EN TODA LA CADENA");
               System.out.println(respuesta);
               // sucursal 1 (debería traerla desde la base de datos pero no está contemplado en este TP)
               Sucursal sucursal1 = new Sucursal();  
               sucursal1.setCodigo(1);
               String respuesta2 = VentaDao.rankingDeProductosPorMonto(fechaDesde, fechaHasta, sucursal1);
               System.out.println("\nRANKING DE PRODUCTOS POR MONTO EN SUCURSAL 1");
               System.out.println(respuesta2);
          } catch (Exception e) {
               e.printStackTrace();
          }
     }
}