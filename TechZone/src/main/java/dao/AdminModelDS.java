package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.AdminModel;
import model.AmministratoreBean;

public class AdminModelDS implements AdminModel {

    @Override
    public synchronized boolean checkLogin(String email, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL1 = "SELECT L.Password FROM Login L JOIN Amministratore A ON L.Codice_Fiscale_Amministratore = A.Codice_Fiscale WHERE A.Email = ?";
        String selectSQL2 = "SELECT Password FROM Amministratore WHERE Email = ?";

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

    @Override
    public synchronized AmministratoreBean doRetrieveByEmail(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        AmministratoreBean bean = null;

        String selectSQL = "SELECT * FROM Amministratore WHERE Email = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                bean = new AmministratoreBean();
                bean.setCodiceFiscale(rs.getString("Codice_Fiscale"));
                bean.setNome(rs.getString("Nome"));
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
}
