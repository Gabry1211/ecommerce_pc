package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AmministratoreDAO {
	public Amministratore doLogin(String codiceFiscale, String password) throws Exception {
	    Amministratore a = null;
	    Connection con = DBConnection.getConnection();

	    String sql = "SELECT * FROM Amministratore WHERE Codice_Fiscale = ? AND Password = ?";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1, codiceFiscale);
	    ps.setString(2, password);
	    ResultSet rs = ps.executeQuery();

	    if (rs.next()) {
	        a = new Amministratore();
	        a.setCodiceFiscale(rs.getString("Codice_Fiscale"));
	        a.setNome(rs.getString("Nome"));
	        a.setIndirizzo(rs.getString("Indirizzo"));
	        a.setEta(rs.getInt("Eta"));
	        a.setEmail(rs.getString("Email"));
	        a.setDataDiNascita(rs.getDate("Data_Di_Nascita"));
	        a.setPassword(rs.getString("Password"));
	    }

	    rs.close();
	    ps.close();
	    con.close();
	    return a;
	}

}
