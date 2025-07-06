package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

}
