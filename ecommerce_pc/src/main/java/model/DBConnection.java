package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/sito?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "mysqlmerda";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC non trovato", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    }