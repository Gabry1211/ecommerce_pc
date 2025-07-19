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
		         PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto");
		         ResultSet rs = ps.executeQuery()) {

		        while (rs.next()) {
		            Prodotto p = new Prodotto();
		            p.setIdProdotto(rs.getInt("ID_Prodotto"));
		            p.setDescrizione(rs.getString("Descrizione"));
		            p.setPrezzo(rs.getDouble("Prezzo"));
		            p.setTipo(rs.getString("Tipo"));
		            p.setImmagine(rs.getString("Immagine"));
		            p.setQuantita(rs.getInt("Quantita"));
		            p.setIdVenditore(rs.getInt("ID_Venditore"));
		            p.setCfAdmin(rs.getString("Codice_Fiscale_Amministratore"));
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

		    String query = "INSERT INTO Prodotto (Descrizione, Prezzo, Tipo, ID_Venditore, Percorso_Immagine, Quantita) " +
		                   "VALUES (?, ?, ?, ?, ?, ?)";

		    try {
		        con = DBConnection.getConnection();
		        ps = con.prepareStatement(query);

		        ps.setString(1, prodotto.getDescrizione());
		        ps.setDouble(2, prodotto.getPrezzo());
		        ps.setString(3, prodotto.getTipo());
		        ps.setInt(4, prodotto.getIdVenditore());
		        ps.setString(5, prodotto.getImmagine());
		        ps.setInt(6, prodotto.getQuantita());

		        ps.executeUpdate();
		    } finally {
		        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
		        if (con != null) try { con.close(); } catch (SQLException ignored) {}
		    }
		}


		
		public void doUpdate(Prodotto p) {
		    try (Connection con = DBConnection.getConnection();
		         PreparedStatement ps = con.prepareStatement(
		             "UPDATE prodotto SET Descrizione = ?, Prezzo = ?, Tipo = ?, Quantita = ? WHERE ID_Prodotto = ?")) {

		        ps.setString(1, p.getDescrizione());
		        ps.setDouble(2, p.getPrezzo());
		        ps.setString(3, p.getTipo());
		        ps.setInt(4, p.getQuantita());    // aggiunto per aggiornare la quantità
		        ps.setInt(5, p.getIdProdotto());

		        ps.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

		
		public void doDelete(int idProdotto) {
			try (Connection con = DBConnection.getConnection()) {
		        PreparedStatement ps = con.prepareStatement("DELETE FROM prodotto WHERE ID_Prodotto = ?");
		        ps.setInt(1, idProdotto);
		        ps.executeUpdate();
		    } catch (SQLException e) {
		        throw new RuntimeException(e);
		    }
		}
		
		public List<Prodotto> doRetrieveByVenditore(int idVenditore) {
		    List<Prodotto> prodotti = new ArrayList<>();
		    Connection con = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    String query = "SELECT * FROM Prodotto WHERE ID_Venditore = ?";

		    try {
		        con = DBConnection.getConnection();
		        ps = con.prepareStatement(query);
		        ps.setInt(1, idVenditore);
		        rs = ps.executeQuery();

		        while (rs.next()) {
		            Prodotto p = new Prodotto();
		            p.setIdProdotto(rs.getInt("ID_Prodotto"));
		            p.setDescrizione(rs.getString("Descrizione"));
		            p.setPrezzo(rs.getDouble("Prezzo"));
		            p.setTipo(rs.getString("Tipo"));
		            p.setIdVenditore(rs.getInt("ID_Venditore"));
		            p.setImmagine(rs.getString("Percorso_Immagine")); // ✅ IMPORTANTE
		            p.setQuantita(rs.getInt("Quantita")); // se usi anche la quantità
		            prodotti.add(p);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try { if (rs != null) rs.close(); } catch (SQLException e) {}
		        try { if (ps != null) ps.close(); } catch (SQLException e) {}
		        try { if (con != null) con.close(); } catch (SQLException e) {}
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
		            Prodotto p = new Prodotto();
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
		
		public Prodotto doRetrieveById(int id) {
		    Prodotto prodotto = null;
		    String query = "SELECT * FROM prodotto WHERE ID_Prodotto = ?";
		    
		    try (Connection con = DBConnection.getConnection();
		         PreparedStatement ps = con.prepareStatement(query)) {
		        
		        ps.setInt(1, id);
		        ResultSet rs = ps.executeQuery();
		        
		        if (rs.next()) {
		            prodotto = new Prodotto();
		            prodotto.setIdProdotto(rs.getInt("ID_Prodotto"));
		            prodotto.setDescrizione(rs.getString("Descrizione"));
		            prodotto.setPrezzo(rs.getDouble("Prezzo"));
		            prodotto.setTipo(rs.getString("Tipo"));
		            prodotto.setQuantita(rs.getInt("Quantita"));
		            prodotto.setImmagine(rs.getString("Percorso_Immagine")); // se c'è questo campo
		            prodotto.setIdVenditore(rs.getInt("ID_Venditore")); // se ti serve
		            // aggiungi altri campi se ce ne sono
		        }
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    return prodotto;
		}

		public void aggiornaQuantita(int idProdotto, int nuovaQuantita) throws SQLException {
		    String sql = "UPDATE Prodotto SET Quantita = ? WHERE ID_Prodotto = ?";
		    try (Connection conn = DBConnection.getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {
		        ps.setInt(1, nuovaQuantita);
		        ps.setInt(2, idProdotto);
		        ps.executeUpdate();
		    }
		}
		
		public int getQuantitaById(int idProdotto) throws SQLException {
		    String sql = "SELECT Quantita FROM Prodotto WHERE ID_Prodotto = ?";
		    try (Connection conn = DBConnection.getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {
		        ps.setInt(1, idProdotto);
		        ResultSet rs = ps.executeQuery();
		        if (rs.next()) {
		            return rs.getInt("Quantita");
		        }
		    }
		    return 0; // o eccezione se preferisci
		}
		
		public List<Prodotto> doRetrieveUltimi(int n) {
		    List<Prodotto> prodotti = new ArrayList<>();

		    String sql = "SELECT * FROM prodotto ORDER BY ID_Prodotto DESC LIMIT ?";

		    try (Connection con = DBConnection.getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setInt(1, n);
		        try (ResultSet rs = ps.executeQuery()) {
		            while (rs.next()) {
		                Prodotto p = new Prodotto();
		                p.setIdProdotto(rs.getInt("ID_Prodotto"));
		                p.setDescrizione(rs.getString("Descrizione"));
		                p.setPrezzo(rs.getDouble("Prezzo"));
		                p.setTipo(rs.getString("Tipo"));
		                p.setQuantita(rs.getInt("Quantita"));
		                p.setImmagine(rs.getString("Percorso_Immagine"));
		                p.setIdVenditore(rs.getInt("ID_Venditore"));
		                p.setCfAdmin(rs.getString("Codice_Fiscale_Amministratore"));

		                prodotti.add(p);
		            }
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return prodotti;
		}

		public void aggiornaQuantitaDisponibile(int idProdotto, int nuovaQuantita) {
		    String sql = "UPDATE Prodotto SET quantita = ? WHERE ID_Prodotto = ?";

		    try (Connection con = DBConnection.getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setInt(1, nuovaQuantita);
		        ps.setInt(2, idProdotto);
		        ps.executeUpdate();

		    } catch (SQLException e) {
		        throw new RuntimeException(e);
		    }
		}



	}
