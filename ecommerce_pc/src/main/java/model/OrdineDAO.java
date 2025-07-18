package model;

import java.sql.*;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.sql.Time;

public class OrdineDAO {
	
	public OrdineDAO() {
		
	}
	
	public OrdineDAO(Connection conn) throws SQLException {
		conn = DBConnection.getConnection();
    }

    public void salvaOrdine(Ordine ordine) throws SQLException {
        String insertOrdineSQL = "INSERT INTO Ordine (Lista_Prodotti, Tot_Ordine, ID_Assistenza, Codice_Fiscale) VALUES (?, ?, NULL, ?)";
        // Lista_Prodotti sarà una stringa con formato: "idProdotto1:quantita1,idProdotto2:quantita2,..."
        StringBuilder listaProdottiStr = new StringBuilder();
        for (Map.Entry<Prodotto, Integer> entry : ordine.getProdottiQuantita().entrySet()) {
            listaProdottiStr.append(entry.getKey().getIdProdotto())
                            .append(":")
                            .append(entry.getValue())
                            .append(",");
        }
        if (listaProdottiStr.length() > 0) {
            listaProdottiStr.deleteCharAt(listaProdottiStr.length() - 1); // rimuovi ultima virgola
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertOrdineSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, listaProdottiStr.toString());
            ps.setDouble(2, ordine.getTotaleOrdine());
            ps.setString(3, ordine.getCodiceFiscaleCliente());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserimento ordine fallito, nessuna riga inserita.");
            }

            // Recupera l'idOrdine generato
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idOrdine = generatedKeys.getInt(1);
                    ordine.setIdOrdine(idOrdine);
                } else {
                    throw new SQLException("Inserimento ordine fallito, nessun ID ottenuto.");
                }
            }
        }
    }
    
    public List<Ordine> getOrdiniByCodiceFiscale(String codiceFiscale) {
        List<Ordine> lista = new ArrayList<>();
        String sql = "SELECT * FROM Ordine WHERE Codice_Fiscale = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codiceFiscale);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ordine o = new Ordine();
                o.setIdOrdine(rs.getInt("ID_Ordine"));
                o.setListaProdotti(rs.getString("Lista_Prodotti"));
                o.setTotale(rs.getDouble("Tot_Ordine"));
                o.setIdAssistenza(rs.getInt("ID_Assistenza"));
                o.setCodiceFiscaleCliente(rs.getString("Codice_Fiscale"));
                lista.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<Ordine> doRetrieveAll() {
        List<Ordine> ordini = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Ordine");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ordini.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordini;
    }

    public List<Ordine> doRetrieveByCliente(Cliente cliente) {
        List<Ordine> ordini = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Ordine WHERE Codice_Fiscale = ?");
            ps.setString(1, cliente.get_codiceFiscale());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ordini.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordini;
    }

    public List<Ordine> doRetrieveByData(String from, String to) {
        List<Ordine> ordini = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM Ordine WHERE Data_Assistenza BETWEEN ? AND ?");
            ps.setString(1, from);
            ps.setString(2, to);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ordini.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordini;
    }

    private Ordine mapRow(ResultSet rs) throws SQLException {
        Ordine o = new Ordine();
        o.setIdOrdine(rs.getInt("ID_Ordine"));
        o.setListaProdotti(rs.getString("Lista_Prodotti"));
        o.setTotale(rs.getDouble("Tot_Ordine"));
        o.setIdAssistenza(rs.getInt("ID_Assistenza"));
        o.setCodiceFiscaleCliente(rs.getString("Codice_Fiscale"));
        return o;
    }
    
    public int doSave(Ordine ordine) {
        String sql = "INSERT INTO Ordine (Codice_Fiscale, data_ordine, ora_ordine, Tot_Ordine, indirizzo_spedizione, citta, cap, metodo_pagamento, numero_carta, scadenza_carta, cvv, email_paypal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ordine.getCodiceFiscaleCliente());
            ps.setDate(2, (Date) ordine.getDataOrdine());
            ps.setTime(3, ordine.getOraOrdine());
            ps.setDouble(4, ordine.getTotaleOrdine());
            ps.setString(5, ordine.getIndirizzoSpedizione());
            ps.setString(6, ordine.getCitta());
            ps.setString(7, ordine.getCap());
            ps.setString(8, ordine.getMetodoPagamento());
            ps.setString(9, ordine.getNumeroCarta());
            ps.setString(10, ordine.getScadenzaCarta());
            ps.setString(11, ordine.getCvv());
            ps.setString(12, ordine.getEmailPaypal());
            

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public void salvaDettaglioOrdine(int idOrdine, Prodotto prodotto, int quantita, double prezzo) {
        String sql = "INSERT INTO DettaglioOrdine (ID_Ordine, idProdotto, quantita, prezzo) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOrdine);
            ps.setInt(2, prodotto.getIdProdotto());
            ps.setInt(3, quantita);
            ps.setDouble(4, prezzo);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
