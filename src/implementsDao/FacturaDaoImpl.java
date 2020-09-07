package implementsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import daos.FacturaDao;
import products.Factura;


public class FacturaDaoImpl implements FacturaDao{
private Connection conn;
	
	public FacturaDaoImpl (Connection conn) {
		this.conn = conn;
	}

	//metodo con el codigo sql para ingresar los datos del CSVParser y parsear los strings a sus respectivos atributos
	public void insert(CSVParser facturas) throws SQLException {
		String insert = "INSERT IGNORE INTO factura(idCliente,idFactura) VALUES (?,?)";
		PreparedStatement ps = conn.prepareStatement(insert);
		for(CSVRecord row: facturas) {
		int	idCliente = Integer.parseInt(row.get("idCliente"));
		int	idFactura = Integer.parseInt(row.get("idFactura"));
			ps.setInt(1,idCliente);
			ps.setInt(2,idFactura);
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
	public List<Factura> select() {
		// TODO Auto-generated method stub
		return null;
	}
}
