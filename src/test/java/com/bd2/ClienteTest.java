package com.bd2;

import java.net.UnknownHostException;

import datos.Cliente;
import negocio.ClienteABM;

public class ClienteTest {

	public static void main(String[] args) {
		try {
			ClienteABM.getInstance();
			Cliente nuevo = new Cliente(39159961, "Cristian", "Villafañe", 39159961, null, null);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		
	}

}
