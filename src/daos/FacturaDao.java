package daos;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.csv.CSVParser;

import products.Factura;


public interface FacturaDao {
	public void insert(CSVParser facturas) throws SQLException;
	public void update();
	public void delete();
	public List<Factura> select();
}
