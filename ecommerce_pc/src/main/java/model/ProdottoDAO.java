package model;

import java.sql.*;

public class ProdottoDAO {
		public void aggiungiProdotto(Prodotto p) throws SQLException {
	        Connection conn = DBConnection.getConnection();

	        String sql = "INSERT INTO Prodotto (Descrizione, Prezzo, Tipo, Percorso_Immagine, Codice_Fiscale_Amministratore, ID_Venditore, Data_Acquisto, Ora_Acquisto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, p.getDescrizione());
	        ps.setDouble(2, p.getPrezzo());
	        ps.setString(3, p.getTipo());
	        ps.setString(4, p.getImmagine());
	        ps.setString(5, p.getCfAdmin());
	        ps.setObject(6, p.getIdVenditore() > 0 ? p.getIdVenditore() : null, Types.INTEGER);
	        ps.setDate(7, p.getDataAcquisto());
	        ps.setTime(8, p.getOraAcquisto());

	        ps.executeUpdate();
	        conn.close();
	    }
	}
