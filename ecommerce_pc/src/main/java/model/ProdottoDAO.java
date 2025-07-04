package model;

import java.sql.*;

public class ProdottoDAO {
		public void aggiungiProdotto(Prodotto p) throws SQLException {
	        Connection conn = DBConnection.getConnection();

	        String sql = "INSERT INTO Prodotto (Descrizione, Prezzo, Tipo, Percorso_Immagine, Codice_Fiscale_Amministratore, ID_Venditore, Data_Acquisto, Ora_Acquisto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, p.getIdProdotto());
	        ps.setString(2, p.getDescrizione());
	        ps.setDouble(3, p.getPrezzo());
	        ps.setString(4, p.getTipo());
	        ps.setString(5, p.getImmagine());
	        ps.setString(6, p.getCfAdmin());
	        ps.setObject(7, p.getIdVenditore() > 0 ? p.getIdVenditore() : null, Types.INTEGER);
	        ps.setDate(8, p.getDataAcquisto());
	        ps.setTime(9, p.getOraAcquisto());

	        ps.executeUpdate();
	        conn.close();
	    }
	}
