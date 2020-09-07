package implementsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	//metodo con el codigo sql para ingresar los datos del CSVParser y parsear los strings a sus respectivos atributos
	public void insert(CSVParser clientes) throws SQLException {
		String insert = "INSERT IGNORE INTO cliente(idCliente, nombre, email) VALUES (?,?,?)";
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

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void delete() {
		// TODO Auto-generated method stub
		
	}

	public List<Cliente> select() {
		
		return null;
	}
	
	
	//se ejecuta  codigo sql para retornar la lista de clientes ordenada por facturacion 
	public List<Cliente> getClientesPorFacturacion() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		Cliente c;
		try {			
			conn.setCatalog("jdbs");
			conn.setAutoCommit(false);
			String select = "SELECT C.idCliente, C.nombre, C.email " + 
					"from cliente C " + 
					"inner join factura F on (C.IdCliente = F.IdCliente) " + 
					"inner join Factura_Producto FP on (F.IdFactura = FP.IdFactura) " + 
					"inner join Producto P on (FP.IdProducto = P.IdProducto) " + 
					"GROUP by c.idCliente " + 
					"ORDER by  SUM(FP.cantidad * P.valor) DESC";
					
			PreparedStatement ps = conn.prepareStatement(select);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				c = new Cliente(rs.getInt(1),rs.getString(2),rs.getString(3));
				clientes.add(c);
			}
			return clientes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
