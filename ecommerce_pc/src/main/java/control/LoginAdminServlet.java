package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Amministratore;
import model.AmministratoreDAO;

import java.io.IOException;
import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;

@WebServlet("/LoginAdminServlet")
public class LoginAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String codiceFiscale = request.getParameter("codiceFiscale");
        String password = request.getParameter("password");

        try {
            AmministratoreDAO dao = new AmministratoreDAO();
            Amministratore admin = dao.doLogin(codiceFiscale, password);

            if (admin != null) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                response.sendRedirect("adminHome.jsp");
            } else {
                request.setAttribute("erroreLogin", "Credenziali errate");
                request.getRequestDispatcher("loginAdmin.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Errore durante il login dell'amministratore", e);
        }
	}

}
