package main;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import implementsDao.*;

public class Main {

	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {

		String uri = "jdbc:Mysql://localhost/jdbs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try {
			Connection conn = DriverManager.getConnection(uri, "root", "");
			conn.setAutoCommit(false);
			createTables(conn);
			CSVParser productos = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/productos.csv"));
			CSVParser clientes = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/clientes.csv"));
			CSVParser facturas = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/facturas.csv"));
			CSVParser facturasYproductos = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/facturas-productos.csv"));

			ClienteDaoImpl cliente = new ClienteDaoImpl(conn);
			//cliente.insert(clientes);

			FacturaDaoImpl factura = new FacturaDaoImpl(conn);
			//factura.insert(facturas);

			ProductoDaoImpl producto = new ProductoDaoImpl (conn);
			//producto.insert(productos);
			producto.insert(facturasYproductos);
			
			System.out.println(producto.getMasRecaudo());
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void createTables(Connection conn) throws SQLException {

		String tablaProductos = "CREATE TABLE IF NOT EXISTS producto(" + 
				"idProducto INT," + 
				"nombre VARCHAR(45)," +
				"valor DOUBLE," + 
				"PRIMARY KEY(idProducto))";
		
		String tablaClientes = "CREATE TABLE IF NOT EXISTS cliente(" + 
				"idCliente INT," + 
				"nombre VARCHAR(500)," +
				"email VARCHAR(150)," + 
				"PRIMARY KEY(idCliente))";
		
		String tablaFacturas = "CREATE TABLE IF NOT EXISTS factura(" + 
				"idCliente INT," + 
				"idFactura INT," +
				"PRIMARY KEY(idFactura))";
		
		String tablaFacturasProductos = "CREATE TABLE IF NOT EXISTS factura_producto(" + 
				"idProducto INT," + 
				"idFactura INT," +
				"cantidad INT," +
				"FOREIGN KEY (idProducto) references producto (idProducto)," +
				"FOREIGN KEY (idFactura) references factura (idFactura)," + 
				"PRIMARY KEY (idProducto,idFactura))";
		
		conn.prepareCall(tablaProductos).execute();
		conn.prepareCall(tablaClientes).execute();
		conn.prepareCall(tablaFacturas).execute();
		conn.prepareCall(tablaFacturasProductos).execute();

		conn.commit();
	}
}
