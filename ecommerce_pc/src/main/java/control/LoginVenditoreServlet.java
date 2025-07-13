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
		HttpSession session = request.getSession();
		
		String partitaIVA = request.getParameter("partitaIVA");
		String password = request.getParameter("password");

		VenditoreDAO dao = new VenditoreDAO();
		Venditore venditore = dao.getVenditoreByPartitaIVAAndPassword(partitaIVA, password);

		if (venditore != null) {
		    session.setAttribute("venditore", venditore);
		    response.sendRedirect("venditoreHome.jsp");
		} else {
		    request.setAttribute("errore", "Credenziali non valide");
		    request.getRequestDispatcher("loginVenditore.jsp").forward(request, response);
		}

	}

}
