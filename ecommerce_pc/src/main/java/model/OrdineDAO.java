package model;

import java.sql.*;
import java.util.Date;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrdineDAO {
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

}
