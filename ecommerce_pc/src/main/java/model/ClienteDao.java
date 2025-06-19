package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDao {
		public void registraCliente(Cliente c, String password) throws SQLException {
	        Connection conn = DBConnection.getConnection();

	        String sql1 = "INSERT INTO Cliente (Codice_Fiscale, Nome, Data_Di_Nascita, Email, Indirizzo) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement ps1 = conn.prepareStatement(sql1);
	        ps1.setString(1, c.get_codiceFiscale());
	        ps1.setString(2, c.get_nome());
	        ps1.setDate(3, c.get_dataDiNascita());
	        ps1.setString(4, c.get_email());
	        ps1.setString(5, c.get_indirizzo());
	        ps1.executeUpdate();

	        String sql2 = "INSERT INTO Login (Password, Nome, Data, Ora, Codice_Fiscale_Cliente) VALUES (?, ?, CURRENT_DATE, CURRENT_TIME, ?)";
	        PreparedStatement ps2 = conn.prepareStatement(sql2);
	        ps2.setString(1, password);
	        ps2.setString(2, c.get_nome());
	        ps2.setString(3, c.get_codiceFiscale());
	        ps2.executeUpdate();

	        conn.close();
	
	}
}
