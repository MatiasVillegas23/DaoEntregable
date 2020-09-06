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
			//LOS INSERT FUNCIONAN SOLO UNA VEZ, YA QUE NO CONTROLAMOS KEYS DUPLICADAS
			Connection conn = DriverManager.getConnection(uri, "root", "");
			conn.setAutoCommit(false);
			
			//se crean todas las tablas con el siguiente metodo si que es que no existen
			createTables(conn);
			//se parsean los datos de los csv en sus respectivas variables
			CSVParser productos = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/productos.csv"));
			CSVParser clientes = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/clientes.csv"));
			CSVParser facturas = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/facturas.csv"));
			CSVParser facturasYproductos = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./csv/facturas-productos.csv"));
			
	        //se crean los Daos de las tablas a utilizar y se llama a los metodos para cargar los datos
			ClienteDaoImpl cliente = new ClienteDaoImpl(conn);
			//cliente.insert(clientes);
			
			FacturaDaoImpl factura = new FacturaDaoImpl(conn);
			//factura.insert(facturas);
			
			ProductoDaoImpl producto = new ProductoDaoImpl (conn);
			//producto.insert(productos);
			//producto.addFacturasYproductos(facturasYproductos);
			
			//se llaman a los metodos necesarios para los puntos 3 y 4
			System.out.println("El producto que mas recaudo es: "+producto.getMasRecaudo());
			
			//Cambiar para imprimir la lista de clientes
			//System.out.println("Lista de clientes ordenados por facturacion: "+ cliente.getClientesPorFacturacion());
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
	//Cada tabla con sus claves primarias y foreing keys 
	//es creada con un codigo sql dentro de un string
	//que es ejecutado con el query 
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
