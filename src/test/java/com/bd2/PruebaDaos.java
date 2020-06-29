package com.bd2;

import java.util.ArrayList;

import dao.VentaDao;
import datos.Venta;

public class PruebaDaos {

	public static void main(String[] args) {
		try {			
			VentaDao.getInstance();
			ArrayList<Venta> ventas = VentaDao.traer();
			System.out.println("Numero de ventas: " + ventas.size());
			if(ventas != null){
				for(Venta v: ventas) {
					System.out.println("\n\t" + v.getNroTicket());
					System.out.println("\t" + v.getFecha());
					System.out.println("\t" + v.getCliente());
					System.out.println("\t" + v.getFormaDePago());
					System.out.println("\t" + v.getEmpleadoAtencion());
					System.out.println("\t" + v.getEmpleadoCobro());
					System.out.println("\t" + v.getSucursal());
					System.out.println("\t" + v.getItems());
					System.out.println("\t" + v.getTotal());
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}