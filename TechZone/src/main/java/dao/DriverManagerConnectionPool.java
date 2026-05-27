package dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DriverManagerConnectionPool {

    private static DataSource dataSource;

    static {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/TechZoneDB");
        } catch (NamingException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static synchronized Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    public static synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null) connection.close();
    }
}
