package implementsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import daos.ClienteDao;
import products.Cliente;
import products.Producto;

public class ClienteDaoImpl implements ClienteDao {
	private Connection conn;
	
	public ClienteDaoImpl (Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(CSVParser clientes) throws SQLException {
		String insert = "INSERT INTO cliente(idCliente, nombre, email) VALUES (?,?,?)";
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

	public Cliente getMasRecaudo() {
		Cliente c1;
		try {
			
			conn.setCatalog("jdbs");
			conn.setAutoCommit(false);

			String select = "";
					
			PreparedStatement ps = conn.prepareStatement(select);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				c1 = new Cliente(rs.getInt(1),rs.getString(2),rs.getString(3));
				return c1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
