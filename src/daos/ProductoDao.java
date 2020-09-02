package daos;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.csv.CSVParser;

import products.Producto;



public interface ProductoDao {
	public void insert(CSVParser productos) throws SQLException;
	public void update();
	public void delete();
	public List<Producto> select();
}
