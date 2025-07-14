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
	                Prodotto p = new Prodotto(0, null, null, 0, null, null, null, 0);
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
		
		public void doSave(Prodotto prodotto) throws SQLException {
		    Connection con = null;
		    PreparedStatement ps = null;

		    String query = "INSERT INTO Prodotto (Descrizione, Prezzo, Tipo, ID_Venditore, Percorso_Immagine) " +
		                   "VALUES (?, ?, ?, ?, ?)";

		    try {
		        con = DBConnection.getConnection();
		        ps = con.prepareStatement(query);

		        ps.setString(1, prodotto.getDescrizione());
		        ps.setDouble(2, Double.valueOf(prodotto.getPrezzo()));
		        ps.setString(3, prodotto.getTipo());
		        ps.setInt(4, prodotto.getIdVenditore());
		        ps.setString(5, prodotto.getImmagine());

		        ps.executeUpdate();
		    } finally {
		        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
		        if (con != null) try { con.close(); } catch (SQLException ignored) {}
		    }
		}

		
		public void doUpdate(Prodotto p) {
		    try (Connection con = DBConnection.getConnection();
		         PreparedStatement ps = con.prepareStatement(
		             "UPDATE prodotto SET descrizione = ?, prezzo = ?, tipo = ? WHERE id = ?")) {

		        ps.setString(1, p.getDescrizione());
		        ps.setDouble(2, p.getPrezzo());
		        ps.setString(3, p.getTipo());
		        ps.setInt(4, p.getIdProdotto());

		        ps.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		public void doDelete(int idProdotto) {
		    try (Connection con = DBConnection.getConnection();
		         PreparedStatement ps = con.prepareStatement(
		             "DELETE FROM prodotto WHERE id = ?")) {

		        ps.setInt(1, idProdotto);
		        ps.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		public List<Prodotto> doRetrieveByVenditore(int idVenditore) {
		    List<Prodotto> prodotti = new ArrayList<>();

		    try (Connection con = DBConnection.getConnection()) {
		        PreparedStatement ps = con.prepareStatement(
		            "SELECT * FROM prodotto WHERE id_venditore = ?");
		        ps.setInt(1, idVenditore);

		        ResultSet rs = ps.executeQuery();
		        while (rs.next()) {
		            Prodotto p = new Prodotto(idVenditore, null, null, idVenditore, null, null, null, idVenditore);
		            p.setIdProdotto(rs.getInt("id_prodotto"));
		            p.setDescrizione(rs.getString("descrizione"));
		            p.setPrezzo(rs.getDouble("prezzo"));
		            p.setTipo(rs.getString("quantita"));
		            prodotti.add(p);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return prodotti;
		}
		
		public List<Prodotto> cercaProdotti(String keyword, String categoria, double minPrezzo, double maxPrezzo) {
		    List<Prodotto> prodotti = new ArrayList<>();

		    try (Connection con = DBConnection.getConnection()) {
		        String query = "SELECT * FROM prodotto WHERE nome LIKE ? AND prezzo BETWEEN ? AND ?";
		        if (categoria != null && !categoria.isEmpty())
		            query += " AND categoria = ?";

		        PreparedStatement ps = con.prepareStatement(query);
		        ps.setString(1, "%" + keyword + "%");
		        ps.setDouble(2, minPrezzo);
		        ps.setDouble(3, maxPrezzo);

		        if (categoria != null && !categoria.isEmpty())
		            ps.setString(4, categoria);

		        ResultSet rs = ps.executeQuery();
		        while (rs.next()) {
		            Prodotto p = new Prodotto(0, query, query, maxPrezzo, query, query, query, 0);
		            p.setIdProdotto(rs.getInt("id"));
		            p.setNome(rs.getString("nome"));
		            p.setPrezzo(rs.getDouble("prezzo"));
		            p.setDescrizione(rs.getString("descrizione"));
		            // Aggiungi altri campi se servono
		            prodotti.add(p);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return prodotti;
		}


	}
