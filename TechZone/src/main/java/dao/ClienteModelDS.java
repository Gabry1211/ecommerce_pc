package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.ClienteBean;
import model.ClienteModel;

public class ClienteModelDS implements ClienteModel {

    @Override
    public synchronized void doSave(ClienteBean cliente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement loginStatement = null;

        String insertClienteSQL = "INSERT INTO Cliente (Codice_Fiscale, Nome, Data_Di_Nascita, Email, Indirizzo) VALUES (?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertClienteSQL);
            preparedStatement.setString(1, cliente.getCodiceFiscale());
            preparedStatement.setString(2, cliente.getNome());
            preparedStatement.setDate(3, cliente.getDataDiNascita());
            preparedStatement.setString(4, cliente.getEmail());
            preparedStatement.setString(5, cliente.getIndirizzo());
            preparedStatement.executeUpdate();

            loginStatement = connection.prepareStatement("INSERT INTO Login (Password, Nome, Codice_Fiscale_Cliente) VALUES (?, ?, ?)");
            loginStatement.setString(1, cliente.getPassword());
            loginStatement.setString(2, cliente.getNome());
            loginStatement.setString(3, cliente.getCodiceFiscale());
            loginStatement.executeUpdate();

            connection.commit();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (loginStatement != null) loginStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public synchronized ClienteBean doRetrieveByEmail(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ClienteBean bean = null;

        String selectSQL = "SELECT * FROM Cliente WHERE Email = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                bean = new ClienteBean();
                bean.setCodiceFiscale(rs.getString("Codice_Fiscale"));
                bean.setNome(rs.getString("Nome"));
                bean.setDataDiNascita(rs.getDate("Data_Di_Nascita"));
                bean.setEmail(rs.getString("Email"));
                bean.setIndirizzo(rs.getString("Indirizzo"));
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
    public synchronized ClienteBean doRetrieveByKey(String codiceFiscale) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ClienteBean bean = null;

        String selectSQL = "SELECT * FROM Cliente WHERE Codice_Fiscale = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, codiceFiscale);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                bean = new ClienteBean();
                bean.setCodiceFiscale(rs.getString("Codice_Fiscale"));
                bean.setNome(rs.getString("Nome"));
                bean.setDataDiNascita(rs.getDate("Data_Di_Nascita"));
                bean.setEmail(rs.getString("Email"));
                bean.setIndirizzo(rs.getString("Indirizzo"));
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
    public synchronized boolean checkLogin(String email, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL1 = "SELECT L.Password FROM Login L JOIN Cliente C ON L.Codice_Fiscale_Cliente = C.Codice_Fiscale WHERE C.Email = ?";
        String selectSQL2 = "SELECT Password FROM Cliente WHERE Email = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();

            preparedStatement = connection.prepareStatement(selectSQL1);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("Password");
                if (dbPassword != null && dbPassword.equals(password)) {
                    return true;
                }
            }

            if (preparedStatement != null) preparedStatement.close();
            preparedStatement = connection.prepareStatement(selectSQL2);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("Password");
                if (dbPassword != null && dbPassword.equals(password)) {
                    return true;
                }
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
