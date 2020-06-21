package com.bd2;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import datos.Cliente;
import negocio.ClienteABM;

public class TestGSON {
	
	private final static Logger LOGGER = Logger.getLogger(TestGSON.class.getName());

	public static void main(String[] args) throws UnknownHostException {
		//AGREGAR
		ClienteABM.getInstance();
		/*Cliente c1 = new Cliente(14200225, "Horacio");
		Cliente c2 = new Cliente(39159961, "Cristian");
		Cliente c3 = new Cliente(92611950, "Leiza");
		ClienteABM.agregar(c1);
		ClienteABM.agregar(c2);
		ClienteABM.agregar(c3);*/
		// TRAER
		Cliente cc = ClienteABM.traer(39159971);
		if(cc != null) {
			LOGGER.info("Cliente traido" + cc.toString());
		} else {
			LOGGER.info("No hay cliente");
		}

		/*boolean result = ClienteABM.eliminar(cc);
		LOGGER.info(String.valueOf(result));*/
	}

}
