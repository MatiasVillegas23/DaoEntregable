package implementsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import daos.ProductoDao;
import products.Producto;

public class ProductoDaoImpl implements ProductoDao {
	private Connection conn;

	public ProductoDaoImpl (Connection conn) {
		this.conn = conn;
	}
	
	//metodo con el codigo sql para ingresar los datos del CSVParser y parsear los strings a sus respectivos atributos
	public void insert(CSVParser productos) throws SQLException {
		String insert = "INSERT INTO producto(idProducto, nombre, valor) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(insert);
		for(CSVRecord row: productos) {
			int	idProducto = Integer.parseInt(row.get("idProducto"));
			double valor = Double.parseDouble(row.get("valor"));
			ps.setInt(1,idProducto);
			ps.setString(2,row.get("nombre"));
			ps.setDouble(3,valor); 
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
	public List<Producto> select() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addFacturasYproductos(CSVParser facturasYproductos) throws SQLException {
		String insert = "INSERT INTO factura_producto(idProducto,idFactura,cantidad) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(insert);
		for(CSVRecord row: facturasYproductos) {
			int idProducto = Integer.parseInt(row.get("idProducto"));
			int idFactura = Integer.parseInt(row.get("idFactura"));
			int cantidad = Integer.parseInt(row.get("cantidad"));
			ps.setInt(1,idProducto);
			ps.setInt(2,idFactura);
			ps.setInt(3,cantidad);
			ps.executeUpdate();
		}
		conn.commit();
	}

	//retorna el producto que mas dinero en ventas genero
	public Producto getMasRecaudo() {
		Producto p1;
		try {
			
			conn.setCatalog("jdbs");
			conn.setAutoCommit(false);

			String select = "select p.idProducto, p.nombre, p.valor " + 
					"from factura_producto fp " + 
					"inner join producto p on (fp.idProducto = p.idProducto) " + 
					"GROUP by fp.idProducto " + 
					"ORDER by sum(cantidad * p.valor) DESC " + 
					"LIMIT 1";
					
			PreparedStatement ps = conn.prepareStatement(select);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				p1 = new Producto(rs.getInt(1),rs.getString(2),rs.getDouble(3));
				return p1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
