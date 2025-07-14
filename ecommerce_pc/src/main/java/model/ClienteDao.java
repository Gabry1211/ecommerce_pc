package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDao {
		public void registraCliente(Cliente c, String password) throws SQLException {
	        Connection conn = DBConnection.getConnection();

	        String sql1 = "INSERT INTO Cliente (Codice_Fiscale, Nome, Data_Di_Nascita, Email, Indirizzo, Password) VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement ps1 = conn.prepareStatement(sql1);
	        ps1.setString(1, c.get_codiceFiscale());
	        ps1.setString(2, c.get_nome());
	        ps1.setDate(3, c.get_dataDiNascita());
	        ps1.setString(4, c.get_email());
	        ps1.setString(5, c.get_indirizzo());
	        ps1.setString(6, c.get_password());
	        ps1.executeUpdate();

	        String sql2 = "INSERT INTO Login (Password, Nome, Data, Ora, Codice_Fiscale_Cliente) VALUES (?, ?, CURRENT_DATE, CURRENT_TIME, ?)";
	        PreparedStatement ps2 = conn.prepareStatement(sql2);
	        ps2.setString(1, password);
	        ps2.setString(2, c.get_nome());
	        ps2.setString(3, c.get_codiceFiscale());
	        ps2.executeUpdate();

	        conn.close();
	
	}
		
		public Cliente getClienteByEmailAndPassword(String email, String password) {
		    Cliente cliente = null;
		    try (Connection con = DBConnection.getConnection()) {
		        PreparedStatement ps = con.prepareStatement("SELECT * FROM Cliente WHERE Email = ? AND Password = ?");
		        ps.setString(1, email);
		        ps.setString(2, password);
		        ResultSet rs = ps.executeQuery();
		        if (rs.next()) {
		            cliente = new Cliente("", "", null, "", "", "");
		            cliente.set_codiceFiscale(rs.getString("Codice_Fiscale"));
		            cliente.set_nome(rs.getString("Nome"));
		            cliente.set_email(rs.getString("Email"));
		            cliente.set_indirizzo(rs.getString("Indirizzo"));
		            cliente.set_password(rs.getString("Password"));
		            // ... altri campi
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return cliente;
		}

}
