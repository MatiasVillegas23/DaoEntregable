package daos;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.csv.CSVParser;

import products.Cliente;

public interface ClienteDao {
	public void insert(CSVParser clientes) throws SQLException;
	public void update();
	public void delete();
	public List<Cliente> select();
}
