package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.VenditoreBean;
import model.VenditoreModel;

public class VenditoreModelDS implements VenditoreModel {

    @Override
    public synchronized void doSave(VenditoreBean venditore) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertVenditoreSQL = "INSERT INTO Venditore (Nome, Partita_IVA, Codice_Fiscale, Password) VALUES (?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertVenditoreSQL);
            preparedStatement.setString(1, venditore.getNome());
            preparedStatement.setString(2, venditore.getPartitaIva());
            preparedStatement.setString(3, venditore.getCodiceFiscale());
            preparedStatement.setString(4, venditore.getPassword());
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
    public synchronized VenditoreBean doRetrieveByEmail(String email) throws SQLException {
        return null;
    }

    @Override
    public synchronized VenditoreBean doRetrieveByKey(String codiceFiscale) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        VenditoreBean bean = null;

        String selectSQL = "SELECT * FROM Venditore WHERE Codice_Fiscale = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, codiceFiscale);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                bean = new VenditoreBean();
                bean.setIdVenditore(rs.getInt("ID_Venditore"));
                bean.setNome(rs.getString("Nome"));
                bean.setPartitaIva(rs.getString("Partita_IVA"));
                bean.setCodiceFiscale(rs.getString("Codice_Fiscale"));
                bean.setPassword(rs.getString("Password"));
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
    public synchronized java.util.Collection<VenditoreBean> doRetrieveAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        java.util.Collection<VenditoreBean> venditori = new java.util.LinkedList<VenditoreBean>();

        String selectSQL = "SELECT * FROM Venditore";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                VenditoreBean bean = new VenditoreBean();
                bean.setIdVenditore(rs.getInt("ID_Venditore"));
                bean.setNome(rs.getString("Nome"));
                bean.setPartitaIva(rs.getString("Partita_IVA"));
                bean.setCodiceFiscale(rs.getString("Codice_Fiscale"));
                venditori.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return venditori;
    }

    @Override
    public synchronized boolean checkLogin(String cf, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT Password FROM Venditore WHERE Codice_Fiscale = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, cf);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                return storedPassword != null && storedPassword.equals(password);
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return false;
    }
}
