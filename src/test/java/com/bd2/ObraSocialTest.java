package com.bd2;

import java.net.UnknownHostException;

import dao.ObraSocialDao;
import datos.ObraSocial;

public class ObraSocialTest {

	public static void main(String[] args) {
		try {
			ObraSocialDao.getInstance();/*
			ObraSocial os1 = new ObraSocial("30598615825", "OSPM");
			ObraSocial os2 = new ObraSocial("33648104389", "OSPF");
			ObraSocial os3 = new ObraSocial("30707746463", "OSMEDICA");
			//Agregar
			System.out.println(ObraSocialDao.agregar(os1));
			System.out.println(ObraSocialDao.agregar(os2));
			System.out.println(ObraSocialDao.agregar(os3));*/
			// Traer
			ObraSocial os = ObraSocialDao.traer("33648104389");
			System.out.println("ObraSocial traida:" + os);
			//Modificar/*
			/*os.setNombre("OSPF de los medicos");
			System.out.println(ObraSocialDao.modificar(os));*/
			//Eliminar
			System.out.println(ObraSocialDao.eliminar(os));

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
