package daos;
import java.sql.SQLException;
import java.util.List;

import products.*;

public interface PersonaDao {

	public void insert(int id, String name, int years) throws SQLException;
	public void update();
	public void delete();
	public List<Persona> select();
	
}
