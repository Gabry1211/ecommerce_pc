package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VenditoreDAO {
	public void registraVenditore(Venditore v) throws SQLException {
        String sql = "INSERT INTO Venditore (Nome, Partita_IVA, Codice_Fiscale, Password) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getNome());
            ps.setString(2, v.getPartitaIVA());
            ps.setString(3, v.getCodiceFiscale());
            ps.setString(4, v.getPassword());

            ps.executeUpdate();
        }
    }
	
	public Venditore getVenditoreByPartitaIVAAndPassword(String partitaIVA, String password) {
	    Venditore venditore = null;
	    try (Connection con = DBConnection.getConnection()) {
	        PreparedStatement ps = con.prepareStatement(
	            "SELECT * FROM Venditore WHERE Partita_IVA = ? AND Password = ?");
	        ps.setString(1, partitaIVA);
	        ps.setString(2, password);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            venditore = new Venditore();
	            venditore.setIdVenditore(rs.getInt("ID_Venditore"));
	            venditore.setNome(rs.getString("Nome"));
	            venditore.setPartitaIVA(partitaIVA);
	            venditore.setCodiceFiscale(rs.getString("Codice_Fiscale"));
	            venditore.setPassword(rs.getString("Password"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return venditore;
	}


}
