package control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.*;
import javax.naming.*;

import javax.sql.DataSource;


@WebServlet("/RegistrazioneVenditoreServlet")
public class RegistrazioneVenditoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String nome = request.getParameter("nome");
	        String cognome = request.getParameter("cognome");
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");

	        try {
	            Context initContext = new InitialContext();
	            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/ecommerceDB");
	            Connection conn = ds.getConnection();

	            String sql = "INSERT INTO Venditore (Nome, Cognome, Email) VALUES (?, ?, ?)";
	            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            ps.setString(1, nome);
	            ps.setString(2, cognome);
	            ps.setString(3, email);
	            ps.executeUpdate();

	            ResultSet generatedKeys = ps.getGeneratedKeys();
	            int idVenditore = 0;
	            if (generatedKeys.next()) {
	                idVenditore = generatedKeys.getInt(1);
	            }

	            String sqlLogin = "INSERT INTO Login (ID_Venditore, Password) VALUES (?, ?)";
	            PreparedStatement psLogin = conn.prepareStatement(sqlLogin);
	            psLogin.setInt(1, idVenditore);
	            psLogin.setString(2, password);
	            psLogin.executeUpdate();

	            conn.close();
	            response.sendRedirect("login.jsp");

	        } catch (Exception e) {
	            throw new ServletException("Errore registrazione venditore", e);
	        }
	}

}
