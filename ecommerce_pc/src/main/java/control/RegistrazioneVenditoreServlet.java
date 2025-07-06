package control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DBConnection;

import java.io.IOException;

import java.sql.*;
import javax.naming.*;

import javax.sql.DataSource;


@WebServlet("/RegistrazioneVenditoreServlet")
public class RegistrazioneVenditoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
        String partitaIva = request.getParameter("partitaIva");
        String codiceFiscale = request.getParameter("codiceFiscale");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommercepc?useSSL=false", "root", "password");

            String sql = "INSERT INTO Venditore (Nome, Partita_IVA, Codice_Fiscale) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, partitaIva);
            stmt.setString(3, codiceFiscale);

            stmt.executeUpdate();
            response.sendRedirect("login.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Registrazione fallita.");
            request.getRequestDispatcher("registrazioneVenditore.jsp").forward(request, response);
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
	}

}
