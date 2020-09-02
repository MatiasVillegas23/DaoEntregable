package implementsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import daos.ClienteDao;
import products.Cliente;

public class ClienteDaoImpl implements ClienteDao {
	private Connection conn;
	
	public ClienteDaoImpl (Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(CSVParser clientes) throws SQLException {
		String insert = "INSERT INTO clientes(idCliente, nombre, email) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(insert);
		for(CSVRecord row: clientes) {
		int	idCliente = Integer.parseInt(row.get("idCliente"));
			ps.setInt(1,idCliente);
			ps.setString(2,row.get("nombre"));
			ps.setString(3,row.get("email"));
			ps.executeUpdate();
			}
		ps.close();
		conn.commit();		
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
	public List<Cliente> select() {
		
		return null;
	}
	
	public List<Cliente> clientesPorFacturacion() {
		return null;
	}

}
