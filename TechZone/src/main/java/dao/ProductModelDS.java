package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import model.ProdottoBean;
import model.ProductModel;

public class ProductModelDS implements ProductModel {

    @Override
    public synchronized void doSave(ProdottoBean product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO Prodotto (Descrizione, Prezzo, Tipo, Percorso_Immagine, ID_Venditore, Quantita) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, product.getDescrizione());
            preparedStatement.setBigDecimal(2, product.getPrezzo());
            preparedStatement.setString(3, product.getTipo());
            preparedStatement.setString(4, product.getPercorsoImmagine());
            if (product.getIdVenditore() != null && product.getIdVenditore() != 0) {
                preparedStatement.setInt(5, product.getIdVenditore());
            } else {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }
            preparedStatement.setInt(6, product.getQuantita());

            preparedStatement.executeUpdate();

            connection.commit();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public synchronized boolean doDelete(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM Prodotto WHERE ID_Prodotto = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, code);

            result = preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return (result != 0);
    }

    @Override
    public synchronized ProdottoBean doRetrieveByKey(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ProdottoBean bean = new ProdottoBean();

        String selectSQL = "SELECT * FROM Prodotto WHERE ID_Prodotto = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                bean.setIdProdotto(rs.getInt("ID_Prodotto"));
                bean.setDescrizione(rs.getString("Descrizione"));
                bean.setPrezzo(rs.getBigDecimal("Prezzo"));
                bean.setTipo(rs.getString("Tipo"));
                bean.setPercorsoImmagine(rs.getString("Percorso_Immagine"));
                bean.setIdVenditore((Integer) rs.getObject("ID_Venditore"));
                bean.setQuantita(rs.getInt("Quantita"));
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return bean;
    }

    @Override
    public synchronized Collection<ProdottoBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProdottoBean> products = new LinkedList<ProdottoBean>();

        String selectSQL = "SELECT * FROM Prodotto";

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProdottoBean bean = new ProdottoBean();

                bean.setIdProdotto(rs.getInt("ID_Prodotto"));
                bean.setDescrizione(rs.getString("Descrizione"));
                bean.setPrezzo(rs.getBigDecimal("Prezzo"));
                bean.setTipo(rs.getString("Tipo"));
                bean.setPercorsoImmagine(rs.getString("Percorso_Immagine"));
                bean.setIdVenditore((Integer) rs.getObject("ID_Venditore"));
                bean.setQuantita(rs.getInt("Quantita"));
                products.add(bean);
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return products;
    }

    @Override
    public synchronized Collection<ProdottoBean> doRetrieveByVenditore(int idVenditore) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProdottoBean> products = new LinkedList<ProdottoBean>();

        String selectSQL = "SELECT * FROM Prodotto WHERE ID_Venditore = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idVenditore);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProdottoBean bean = new ProdottoBean();
                bean.setIdProdotto(rs.getInt("ID_Prodotto"));
                bean.setDescrizione(rs.getString("Descrizione"));
                bean.setPrezzo(rs.getBigDecimal("Prezzo"));
                bean.setTipo(rs.getString("Tipo"));
                bean.setPercorsoImmagine(rs.getString("Percorso_Immagine"));
                bean.setIdVenditore((Integer) rs.getObject("ID_Venditore"));
                bean.setQuantita(rs.getInt("Quantita"));
                products.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return products;
    }

    @Override
    public synchronized void doUpdate(ProdottoBean product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE Prodotto SET Descrizione = ?, Prezzo = ?, Tipo = ?, Percorso_Immagine = ?, Quantita = ?, ID_Venditore = ? WHERE ID_Prodotto = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, product.getDescrizione());
            preparedStatement.setBigDecimal(2, product.getPrezzo());
            preparedStatement.setString(3, product.getTipo());
            preparedStatement.setString(4, product.getPercorsoImmagine());
            preparedStatement.setInt(5, product.getQuantita());
            if (product.getIdVenditore() != null && product.getIdVenditore() != 0) {
                preparedStatement.setInt(6, product.getIdVenditore());
            } else {
                preparedStatement.setNull(6, java.sql.Types.INTEGER);
            }
            preparedStatement.setInt(7, product.getIdProdotto());

            preparedStatement.executeUpdate();

            connection.commit();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public synchronized Collection<ProdottoBean> doRetrieveByQuery(String query) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProdottoBean> products = new LinkedList<ProdottoBean>();

        String selectSQL = "SELECT * FROM Prodotto WHERE Descrizione LIKE ? OR Tipo LIKE ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProdottoBean bean = new ProdottoBean();
                bean.setIdProdotto(rs.getInt("ID_Prodotto"));
                bean.setDescrizione(rs.getString("Descrizione"));
                bean.setPrezzo(rs.getBigDecimal("Prezzo"));
                bean.setTipo(rs.getString("Tipo"));
                bean.setPercorsoImmagine(rs.getString("Percorso_Immagine"));
                bean.setIdVenditore((Integer) rs.getObject("ID_Venditore"));
                bean.setQuantita(rs.getInt("Quantita"));
                products.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return products;
    }
}
