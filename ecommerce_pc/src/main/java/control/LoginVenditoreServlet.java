package control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Venditore;
import model.VenditoreDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LoginVenditoreServlet")
public class LoginVenditoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String partitaIVA = request.getParameter("partitaIVA");
	        String codiceFiscale = request.getParameter("codiceFiscale");

	        VenditoreDAO dao = new VenditoreDAO();

	        try {
	            Venditore v = dao.autenticaVenditore(partitaIVA, codiceFiscale);

	            if (v != null) {
	                HttpSession session = request.getSession();
	                session.setAttribute("venditore", v);
	                response.sendRedirect("venditoreHome.jsp");
	            } else {
	                response.sendRedirect("loginVenditore.jsp?errore=1");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.sendRedirect("errore.jsp");
	        }
	}

}
