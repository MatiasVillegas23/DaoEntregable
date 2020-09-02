package main;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import implementsDao.*;
import products.*;

public class Main {

	public static void main(String[] args) throws SQLException {

		String uri = "jdbc:mysql://localhost:3306/";
		List<Persona> personas = new ArrayList<Persona>();
		
		PersonaDaoImpl pDao1 = new PersonaDaoImpl(uri);
		pDao1.createTable();
		//anda solo una vez, el insert debe chequear las id para agregar o chilla el catch de sql
		pDao1.insert(1, "Juan", 20);
		pDao1.insert(2, "Pedro", 30);
		pDao1.insert(3, "Maria", 18);
		personas = pDao1.select();
		
		for (Persona persona : personas) {
			System.out.println(persona.toString());
		}
		
	}

}
