package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.sql.Date;
import model.OrdineBean;
import model.OrdineModel;
import model.ProdottoBean;
import model.ProductModel;

public class OrdineModelDS implements OrdineModel {

    @Override
    public synchronized void doSave(OrdineBean ordine) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO Ordine (Lista_Prodotti, Tot_Ordine, Codice_Fiscale, Data_Ordine) VALUES (?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, ordine.getListaProdotti());
            preparedStatement.setBigDecimal(2, ordine.getTotOrdine());
            preparedStatement.setString(3, ordine.getCodiceFiscale());
            preparedStatement.setDate(4, new java.sql.Date(System.currentTimeMillis()));

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
    public synchronized Collection<OrdineBean> doRetrieveByCliente(String codiceFiscale) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<OrdineBean> ordini = new LinkedList<OrdineBean>();

        String selectSQL = "SELECT * FROM Ordine WHERE Codice_Fiscale = ? ORDER BY Data_Ordine DESC";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, codiceFiscale);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrdineBean bean = new OrdineBean();
                bean.setIdOrdine(rs.getInt("ID_Ordine"));
                bean.setListaProdotti(rs.getString("Lista_Prodotti"));
                bean.setTotOrdine(rs.getBigDecimal("Tot_Ordine"));
                bean.setCodiceFiscale(rs.getString("Codice_Fiscale"));
                bean.setDataOrdine(rs.getDate("Data_Ordine"));
                ordini.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return ordini;
    }

    @Override
    public synchronized Collection<OrdineBean> doRetrieveAll(Date from, Date to, String codiceFiscale) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<OrdineBean> ordini = new LinkedList<OrdineBean>();

        StringBuilder selectSQL = new StringBuilder("SELECT * FROM Ordine WHERE 1=1");
        if (codiceFiscale != null && !codiceFiscale.isEmpty()) {
            selectSQL.append(" AND Codice_Fiscale = ?");
        }
        if (from != null) {
            selectSQL.append(" AND Data_Ordine >= ?");
        }
        if (to != null) {
            selectSQL.append(" AND Data_Ordine <= ?");
        }

        selectSQL.append(" ORDER BY Data_Ordine DESC");

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL.toString());

            int paramIndex = 1;
            if (codiceFiscale != null && !codiceFiscale.isEmpty()) {
                preparedStatement.setString(paramIndex++, codiceFiscale);
            }
            if (from != null) {
                preparedStatement.setDate(paramIndex++, from);
            }
            if (to != null) {
                preparedStatement.setDate(paramIndex++, to);
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrdineBean bean = new OrdineBean();
                bean.setIdOrdine(rs.getInt("ID_Ordine"));
                bean.setListaProdotti(rs.getString("Lista_Prodotti"));
                bean.setTotOrdine(rs.getBigDecimal("Tot_Ordine"));
                bean.setCodiceFiscale(rs.getString("Codice_Fiscale"));
                bean.setDataOrdine(rs.getDate("Data_Ordine"));
                ordini.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return ordini;
    }

    @Override
    public synchronized Collection<OrdineBean> doRetrieveByVenditore(int idVenditore) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<OrdineBean> ordini = new LinkedList<OrdineBean>();

        ProductModel productModel = new ProductModelDS();
        Collection<ProdottoBean> sellerProducts = productModel.doRetrieveByVenditore(idVenditore);

        if (sellerProducts.isEmpty()) return ordini;

        StringBuilder selectSQL = new StringBuilder("SELECT * FROM Ordine WHERE ");
        int count = 0;
        for (ProdottoBean p : sellerProducts) {
            if (count > 0) selectSQL.append(" OR ");
            selectSQL.append("Lista_Prodotti LIKE ?");
            count++;
        }
        selectSQL.append(" ORDER BY Data_Ordine DESC");

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL.toString());

            int paramIndex = 1;
            for (ProdottoBean p : sellerProducts) {
                preparedStatement.setString(paramIndex++, "%" + p.getDescrizione() + "%");
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrdineBean bean = new OrdineBean();
                bean.setIdOrdine(rs.getInt("ID_Ordine"));
                bean.setListaProdotti(rs.getString("Lista_Prodotti"));
                bean.setTotOrdine(rs.getBigDecimal("Tot_Ordine"));
                bean.setCodiceFiscale(rs.getString("Codice_Fiscale"));
                bean.setDataOrdine(rs.getDate("Data_Ordine"));
                ordini.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return ordini;
    }
}
