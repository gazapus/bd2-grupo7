package com.bd2;

import java.net.UnknownHostException;

import com.mongodb.DB;

public class ConnectDBTest {

	public static void main(String[] args)  {
		try {		
			DB db = ConnectorDB.getDatabase("test");
			System.out.println("Conectado a " + db.getName());
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
	}

}
