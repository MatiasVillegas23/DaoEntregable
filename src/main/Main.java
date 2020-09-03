package main;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import implementsDao.*;
import products.*;

public class Main {

	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {

		String uri = "jdbc:Mysql://localhost/jdbs";
		try {
			Connection conn = DriverManager.getConnection(uri, "root", "");
			conn.setAutoCommit(false);
			//createTables(conn);
			CSVParser productos = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/productos.csv"));
			CSVParser clientes = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/clientes.csv"));
			CSVParser facturas = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/facturas.csv"));
			CSVParser facturasYproductos = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/facturas-productos.csv"));
			
			ClienteDaoImpl cliente = new ClienteDaoImpl(conn);
			cliente.insert(clientes);
			
			FacturaDaoImpl factura = new FacturaDaoImpl(conn);
			factura.insert(facturas);
			
			ProductoDaoImpl producto = new ProductoDaoImpl (conn);
			producto.insert(productos);
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
