package main;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import implementsDao.*;
import products.*;

public class Main {

	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {

		String uri = "jdbc:Mysql://localhost/jdbs";
		try {
			Connection conn = DriverManager.getConnection(uri, "root", "");
			conn.setAutoCommit(false);
			createTables(conn);
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
			
			addFacturasYproductos(facturasYproductos,conn);
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private static void createTables(Connection conn) throws SQLException {
		
		String tablaProductos = "CREATE TABLE IF NOT EXISTS productos(" + 
	"idProducto INT," + 
	"nombre VARCHAR(45)," +
	"valor DOUBLE," + 
				"PRIMARY KEY(idProducto))";
		
	   String tablaClientes = "CREATE TABLE IF NOT EXISTS clientes(" + 
				"idCliente INT," + 
				"nombre VARCHAR(500)," +
				"email VARCHAR(150)," + 
							"PRIMARY KEY(idCliente))";
	   
	   String tablaFacturas = "CREATE TABLE IF NOT EXISTS facturas(" + 
				"idCliente INT," + 
				"idFactura INT," +
							"PRIMARY KEY(idFactura))";
	   
	   String tablaFacturasProductos = "CREATE TABLE IF NOT EXISTS facturasYproductos(" + 
			   	"idProducto INT," + 
				"idFactura INT," +
				"cantidad INT," +
				"FOREIGN KEY (idProducto) references productos (idProducto)," +
				"FOREIGN KEY (idFactura) references facturas (idFactura)," + 
				"PRIMARY KEY (idProducto,idFactura))";
	   
		conn.prepareCall(tablaProductos).execute();
		conn.prepareCall(tablaClientes).execute();
		conn.prepareCall(tablaFacturas).execute();
		conn.prepareCall(tablaFacturasProductos).execute();
		
		conn.commit();
	}
	
	
	public static void addFacturasYproductos(CSVParser facturasYproductos, Connection conn) throws SQLException {	
		String insert = "INSERT INTO facturasYproductos(idProducto,idFactura,cantidad) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(insert);
		for(CSVRecord row: facturasYproductos) {
		int	idProducto = Integer.parseInt(row.get("idProducto"));
		int	idFactura = Integer.parseInt(row.get("idFactura"));
		int	cantidad = Integer.parseInt(row.get("cantidad"));
			ps.setInt(1,idProducto);
			ps.setInt(2,idFactura);
			ps.setInt(3,cantidad);
			ps.executeUpdate();
			}
		ps.close();
		conn.commit();
	}
}
