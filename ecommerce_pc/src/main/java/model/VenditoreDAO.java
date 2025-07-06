package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VenditoreDAO {
	public void registraVenditore(Venditore v) throws SQLException {
        String sql = "INSERT INTO Venditore (Nome, Partita_IVA, Codice_Fiscale) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getNome());
            ps.setString(2, v.getPartitaIVA());
            ps.setString(3, v.getCodiceFiscale());

            ps.executeUpdate();
        }
    }
	
	public Venditore autenticaVenditore(String partitaIVA, String codiceFiscale) throws SQLException {
	    String sql = "SELECT * FROM Venditore WHERE Partita_IVA = ? AND Codice_Fiscale = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, partitaIVA);
	        ps.setString(2, codiceFiscale);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                Venditore v = new Venditore();
	                v.setIdVenditore(rs.getInt("ID_Venditore"));
	                v.setNome(rs.getString("Nome"));
	                v.setPartitaIVA(rs.getString("Partita_IVA"));
	                v.setCodiceFiscale(rs.getString("Codice_Fiscale"));
	                return v;
	            }
	        }
	    }

	    return null;
	}

}
