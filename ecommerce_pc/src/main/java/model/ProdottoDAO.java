package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
		
		public List<Prodotto> doRetrieveAll() {
	        List<Prodotto> prodotti = new ArrayList<>();

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto")) {

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Prodotto p = new Prodotto(0, null, 0, null, null, null, 0);
	                p.setIdProdotto(rs.getInt("id"));
	                p.setDescrizione(rs.getString("descrizione"));
	                p.setPrezzo(rs.getDouble("prezzo"));
	                p.setTipo(rs.getString("tipo"));
	                prodotti.add(p);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return prodotti;
	    }
		
		public void doSave(Prodotto p) {
		    try (Connection con = DBConnection.getConnection();
		         PreparedStatement ps = con.prepareStatement(
		             "INSERT INTO prodotto (descrizione, prezzo, tipo) VALUES (?, ?, ?)")) {

		        ps.setString(1, p.getDescrizione());
		        ps.setDouble(2, p.getPrezzo());
		        ps.setString(3, p.getTipo());

		        ps.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	}
