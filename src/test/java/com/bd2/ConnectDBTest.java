package com.bd2;

import java.net.UnknownHostException;

import com.bd2.ConnectorDB;
import com.mongodb.DB;

public class ConnectDBTest {
	
	/* Test de conexion a la base de datos 'farmacia */
	public static void main(String[] args)  {
		try {		
			DB db = ConnectorDB.getDatabase("farmacia");
			System.out.println("Conectado a " + db.getName());
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
	}

}
