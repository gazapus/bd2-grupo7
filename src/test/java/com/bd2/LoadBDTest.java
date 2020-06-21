package com.bd2;

import datos.Cliente;
import negocio.ClienteABM;

public class LoadBDTest {
	
	public static void main(String[] args) {
		try
		{
			ClienteABM.getInstance();
			//Cliente c = ClienteABM.agregar(39159962, "Andres");
			//Cliente c = ClienteABM.traer("5eee8c8df3a1106008637ba3");
			//System.out.println(c);
		}
		catch(java.net.UnknownHostException  e)
		{
			e.printStackTrace();
		}
	}
}
