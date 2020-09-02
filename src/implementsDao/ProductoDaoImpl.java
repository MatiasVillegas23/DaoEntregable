package implementsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	@Override
	public void insert(CSVParser productos) throws SQLException {
		String insert = "INSERT INTO productos(idProducto, nombre, valor) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(insert);
		for(CSVRecord row: productos) {
		int	idProducto = Integer.parseInt(row.get("idProducto"));
		double valor = Integer.parseInt(row.get("valor"));
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
	
}
