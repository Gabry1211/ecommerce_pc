package control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import java.sql.*;
import javax.naming.*;

import javax.sql.DataSource;

@WebServlet("/LoginVenditoreServlet")
public class LoginVenditoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 int idVenditore = Integer.parseInt(request.getParameter("idVenditore"));
	        String password = request.getParameter("password");

	        try {
	            Context initContext = new InitialContext();
	            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/ecommerceDB");
	            Connection conn = ds.getConnection();

	            String query = "SELECT * FROM Login WHERE ID_Venditore = ? AND Password = ?";
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setInt(1, idVenditore);
	            stmt.setString(2, password);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                HttpSessionw session = request.getSession();
	                session.setAttribute("venditore", idVenditore);
	                response.sendRedirect("venditoreHome.jsp");
	            } else {
	                request.setAttribute("erroreLogin", "Credenziali errate");
	                request.getRequestDispatcher("loginVenditore.jsp").forward(request, response);
	            }

	            conn.close();
	        } catch (Exception e) {
	            throw new ServletException("Errore login venditore", e);
	        }
	}

}
