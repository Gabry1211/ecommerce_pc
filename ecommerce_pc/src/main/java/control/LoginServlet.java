package control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cf = request.getParameter("codiceFiscale");
        String pw = request.getParameter("password");

        try (Connection conn = model.DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Login WHERE Codice_Fiscale_Cliente = ? AND Password = ?");
            ps.setString(1, cf);
            ps.setString(2, pw);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("cliente", cf);
                response.sendRedirect("clienteHome.jsp");
            } else {
                response.getWriter().write("Credenziali non valide.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Errore login.");
        }
	}

}
