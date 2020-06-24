package com.bd2;

import dao.SucursalDao;
import datos.Sucursal;
import datos.Venta;

public class PruebaDaos {

	public static void main(String[] args) {
		try {			
			SucursalDao.getInstance();
			Sucursal s1 = SucursalDao.traer(1);
			System.out.println(s1.getCodigo());
			System.out.println(s1.getNombre());
			System.out.println(s1.getDomicilio());
			System.out.println(s1.getEmpleados());
			System.out.print("\n\nVENTAS:\n");
			for(Venta v: s1.getVentas()) {
				System.out.println("\n\t" + v.getNroTicket());
				System.out.println("\t" + v.getFecha());
				System.out.println("\t" + v.getCliente());
				System.out.println("\t" + v.getFormaDePago());
				System.out.println("\t" + v.getEmpleadoAtencion());
				System.out.println("\t" + v.getEmpleadoCobro());
				System.out.println("\t" + v.getItems());
				System.out.println("\t" + v.getTotal());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
