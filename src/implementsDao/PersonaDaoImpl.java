package implementsDao;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daos.*;
import products.*;

public class PersonaDaoImpl implements PersonaDao{
	private String uri;
	
	public PersonaDaoImpl (String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void createTable() {
		String driver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
			Connection conn = DriverManager.getConnection(this.uri, "root", "");
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS nuevaDB");
			ps.executeUpdate();
			ps.close();
			conn.setCatalog("nuevaDB"); 
			this.createTable(conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createTable(Connection conn) throws SQLException {
		
		String table = "CREATE TABLE IF NOT EXISTS persona ("+
		"id INT,"+
		"nombre VARCHAR(500),"+
		"edad INT,"+
		"PRIMARY KEY(id))";
		conn.prepareStatement(table).execute();
		conn.commit();
	}
	
	@Override
	public void insert(int id, String name, int years) throws SQLException{
		Connection conn = DriverManager.getConnection(this.uri, "root", "");
		conn.setCatalog("nuevaDB");
		conn.setAutoCommit(false);
		
		String insert = "INSERT INTO persona (id, nombre, edad) VALUES (?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(insert);
		ps.setInt(1, id);
		ps.setString(2, name);
		ps.setInt(3, years);
		ps.executeUpdate();
		ps.close();
		conn.commit();
		conn.close();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Persona> select() {
		List<Persona> personas = new ArrayList<Persona>();
		try {
			Connection conn = DriverManager.getConnection(this.uri, "root", "");
			conn.setCatalog("nuevaDB");
			conn.setAutoCommit(false);
			
			String select = "SELECT * FROM persona";
			PreparedStatement ps = conn.prepareStatement(select);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Persona p1 = new Persona(rs.getInt(1),rs.getString(2),rs.getInt(3));
				personas.add(p1);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personas;
	}

}
