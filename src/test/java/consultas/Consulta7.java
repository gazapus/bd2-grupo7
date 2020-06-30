package consultas;

import java.time.LocalDate;

import dao.VentaDao;
import pojos.Sucursal;

public class Consulta7 {
     public static void main(String[] args) {
          // Se traen solamente los DNI de los clientes y su monto acumulado, para favorecer la legibilidad del resultado
          LocalDate fechaDesde = LocalDate.of(2020, 1, 1);
          LocalDate fechaHasta = LocalDate.now();
          try {
               VentaDao.getInstance();
               // toda la cadena
               String respuesta = VentaDao.rankingDeClientesPorMonto(fechaDesde, fechaHasta);
               System.out.println("RANKING DE CLIENTES POR MONTO EN TODA LA CADENA");
               System.out.println(respuesta);
               // sucursal 1
               Sucursal sucursal1 = new Sucursal();    // Debería traerla desde la bd pero no es contemplado en este trabajo
               sucursal1.setCodigo(1);
               String respuesta2 = VentaDao.rankingDeClientesPorMonto(fechaDesde, fechaHasta, sucursal1);
               System.out.println("\nRANKING DE CLIENTES POR MONTO EN SUCURSAL 1");
               System.out.println(respuesta2);
          } catch (Exception e) {
               e.printStackTrace();
          }
     }
}