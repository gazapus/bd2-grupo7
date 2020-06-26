package com.bd2;

import dao.SucursalDao;
import datos.Sucursal;

public class PruebaDaos {

	public static void main(String[] args) {
		try {			
			SucursalDao.getInstance();
			// Trae la sucursal con codigo 1 de la DB como una instancia pojo
			Sucursal suc1 = SucursalDao.traer(1);	
			// modifica el nombre a la sucursal y lo guarda en la DB
			suc1.setNombre("Sucursal Lanus Oeste");	
			boolean sucursalModificada = SucursalDao.modificar(suc1);
			// muestra el resultado de la operación
			if(sucursalModificada) {
				System.out.println("Sucursal modificada:\n" + SucursalDao.traer(1).getNombre());
			} else {
				System.out.println("No se pudo modificar");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}